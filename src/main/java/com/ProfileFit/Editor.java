package com.software.ProfileFit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.software.ProfileFit.Adapter.BorderAdapter;
import com.software.ProfileFit.Adapter.RatioAdapter;
import com.software.ProfileFit.Adapter.ViewPagerAdapter;
import com.software.ProfileFit.Interface.AdjustImageFragmentListener;
import com.software.ProfileFit.Interface.EditImageFragmentListener;
import com.software.ProfileFit.Interface.FirltersListFragmentListener;
import com.software.ProfileFit.Utils.BitmapUtils;
import com.google.android.material.tabs.TabLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.yalantis.ucrop.UCrop;
import com.zomato.photofilters.imageprocessors.Filter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import com.jgabrielfreitas.core.BlurImageView;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;


import static android.os.Environment.DIRECTORY_PICTURES;

public class Editor extends AppCompatActivity implements FirltersListFragmentListener, EditImageFragmentListener, RatioAdapter.RatioAdapterListener, AdjustImageFragmentListener, BorderAdapter.BorderAdapterListener {

    public static final int PERMISSION_PICK_IMAGE = 1000;

    BlurImageView img_preview;
    TabLayout tabLayout;
    ViewPager viewPager;
    CoordinatorLayout coordinatorLayout;
    Bitmap bitmap;
    ImageView imageView1, border_main;
    RelativeLayout imgSection1;
    Bitmap blurBitmap2;

    boolean onebone = false;
    boolean fourbthree = false;
    boolean fourbfive = false;
    boolean sixbnine = false;
    boolean threebfour = false;
    boolean ninebsix = false;

    Bitmap colored;
    boolean edited = false;

    Bitmap backbit;

    Bitmap blury;

    Bitmap originalBitmap, filteredBitmap, finalBitmap;

    FiltersListFragment filtersListFragment;
    EditImageFragment editImageFragment;
    RatioFragment ratioFragment;
    AdjustImageFragment adjustImageFragment;
    BorderFragment borderFragment;

    RecyclerView recycler_background;

    boolean name = true;

    Bitmap heybit;
    boolean crop_bit = false;

    boolean primero = false;
    boolean segundo = false;
    boolean tercero = false;
    Uri image_selected_uri;

    int brightnessFinal = 0;
    float saturationFinal = 1.0f;
    float constrantFinal = 1.0f;

    boolean adjusting = false;

    boolean fliprule = false;
    boolean rotaterule = false;

    //Load native image filters lib
    static{
        System.loadLibrary("NativeImageProcessor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("ProfileFit ");

        //View
        img_preview = (BlurImageView) findViewById(R.id.image_preview);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        imageView1 = (ImageView) findViewById(R.id.imageViewFinal);
        imgSection1 = (RelativeLayout) findViewById(R.id.imgSection);
        recycler_background = (RecyclerView) findViewById(R.id.recycler_background);
        border_main = (ImageView) findViewById(R.id.border_main);


        AdView adView = (AdView)findViewById(R.id.adBannerEditor);
        MobileAds.initialize(this, "ca-app-pub-6822014320677335~3807512134");
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        primero = getIntent().getExtras().getBoolean("four_three");
        segundo = getIntent().getExtras().getBoolean("four_five");
        tercero = getIntent().getExtras().getBoolean("six_nine");

        try {
            loadImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(8);
        tabLayout.setupWithViewPager(viewPager);

        final TabLayout.Tab croptab = tabLayout.newTab().setText("CROP");
        final TabLayout.Tab fliptab = tabLayout.newTab().setText("FLIP");
        final TabLayout.Tab rotatetab = tabLayout.newTab().setText("ROTATE");
        tabLayout.addTab(croptab);
        tabLayout.addTab(fliptab);
        tabLayout.addTab(rotatetab);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 5:
                        startCrop(image_selected_uri);
                        break;

                    case 6:
                        if(fliprule) {
                            imageView1.setScaleX(1);
                            fliprule = false;
                        }
                        else {
                            imageView1.setScaleX(-1);
                            fliprule = true;
                        }
                        break;

                    case 7:
                        if(rotaterule){
                            imageView1.setScaleY(1);
                            rotaterule = false;
                        }
                        else {
                            imageView1.setScaleY(-1);
                            rotaterule = true;
                        }
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 5:
                        startCrop(image_selected_uri);
                        break;

                    case 6:
                        if(fliprule) {
                            imageView1.setScaleX(1);
                            fliprule = false;
                        }
                        else {
                            imageView1.setScaleX(-1);
                            fliprule = true;
                        }
                        break;

                    case 7:
                        if(rotaterule){
                            imageView1.setScaleY(1);
                            rotaterule = false;
                        }
                        else {
                            imageView1.setScaleY(-1);
                            rotaterule = true;
                        }
                        break;
                }
            }
        });

        tabLayout.getTabAt(1).setIcon(R.drawable.ic_filter);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_background);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_ratio);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_tune);
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_border);
        tabLayout.getTabAt(5).setIcon(R.drawable.ic_crop);
        tabLayout.getTabAt(6).setIcon(R.drawable.ic_flip);
        tabLayout.getTabAt(7).setIcon(R.drawable.ic_rotate);


        Boolean hello = getIntent().getExtras().getBoolean("open_crop");

        if (hello){
            startCrop(image_selected_uri);
            Toast.makeText(this, "Crop Opened", Toast.LENGTH_SHORT).show();
        }
    }


    private void loadImage() throws IOException {

        Intent intent = getIntent();
        Uri u = Uri.parse(intent.getStringExtra("imageUri"));
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), u);
        Bitmap bitmap2 = rotateImageIfRequired(this, bitmap, u);

        image_selected_uri = Uri.parse(intent.getStringExtra("imageUri"));

        originalBitmap = bitmap2.copy(Bitmap.Config.ARGB_8888, true);
        filteredBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        finalBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        filteredBitmap = bitmap2.createScaledBitmap(originalBitmap, 1080,1080, false);

        //getting background blur
        if (primero){
            blurBitmap2 = bitmap2.createScaledBitmap(originalBitmap, 1080,810, false);
            blurBitmap2 = BitmapUtils.blurRenderScript(this, blurBitmap2, 25);
            img_preview.setImageBitmap(blurBitmap2);
        }
        else if (segundo){
            blurBitmap2 = bitmap2.createScaledBitmap(originalBitmap, 1024,1280, false);
            blurBitmap2 = BitmapUtils.blurRenderScript(this, blurBitmap2, 25);
            img_preview.setImageBitmap(blurBitmap2);
        }
        else if (tercero) {
            blurBitmap2 = bitmap2.createScaledBitmap(originalBitmap, 1080,608, false);
            blurBitmap2 = BitmapUtils.blurRenderScript(this, blurBitmap2, 25);
            img_preview.setImageBitmap(blurBitmap2);
        }
        else {
            blurBitmap2 = bitmap2.createScaledBitmap(originalBitmap, 1080,1080, false);
            blurBitmap2 = BitmapUtils.blurRenderScript(this, blurBitmap2, 25);
            img_preview.setImageBitmap(blurBitmap2);
        }
        imageView1.setImageBitmap(originalBitmap);

        //Render selected img thumbnail
        //filtersListFragment = FiltersListFragment.getInstance(originalBitmap);
        //filtersListFragment.setListener(this);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adjustImageFragment = new AdjustImageFragment();
        adjustImageFragment.setListener(this);

        filtersListFragment = new FiltersListFragment();
        filtersListFragment.setListener(this);

        editImageFragment = new EditImageFragment();
        editImageFragment.setListener(this);

        ratioFragment = new RatioFragment();
        ratioFragment.setListener(this);

        borderFragment = new BorderFragment();
        borderFragment.setListener(this);

        adapter.addFragment(editImageFragment, "COLORS");
        adapter.addFragment(filtersListFragment, "FILTERS");
        adapter.addFragment(ratioFragment, "RATIO");
        adapter.addFragment(adjustImageFragment, "TUNE");
        adapter.addFragment(borderFragment, "Border");


        viewPager.setAdapter(adapter);

    }

    @Override
    public void onAddBackground(int background) {

        Bitmap blury = originalBitmap.copy(Bitmap.Config.ARGB_8888,true);
        Bitmap bitmap3 = BitmapUtils.decodeSampledBitmapFromResource(getResources(),background,100,100);
        Bitmap white = BitmapUtils.decodeSampledBitmapFromResource(getResources(),R.drawable.black, 100,100);

        if(bitmap3 != null){
            if (background == R.drawable.grey3){
                if (onebone){

                    blury = bitmap.createScaledBitmap(blury, 1080,1080, false);
                    blury = BitmapUtils.blurRenderScript(this, blury, 25);
                    img_preview.setImageBitmap(blury);

                    edited = false;
                    name = true;
                }
                else if (fourbthree || primero){

                    blury = bitmap.createScaledBitmap(blury, 1080,810, false);
                    blury = BitmapUtils.blurRenderScript(this, blury, 25);
                    img_preview.setImageBitmap(blury);

                    edited = false;
                    name = true;
                }
                else if (fourbfive || segundo){

                    blury = bitmap.createScaledBitmap(blury, 1024,1280, false);
                    blury = BitmapUtils.blurRenderScript(this, blury, 25);
                    img_preview.setImageBitmap(blury);

                    edited = false;
                    name = true;
                }
                else if(sixbnine || tercero) {

                    blury = bitmap.createScaledBitmap(blury, 1080,608, false);
                    blury = BitmapUtils.blurRenderScript(this, blury, 25);
                    img_preview.setImageBitmap(blury);

                    edited = false;
                    name = true;
                }
                else if(threebfour) {

                    blury = bitmap.createScaledBitmap(blury, 1080,1280, false);
                    blury = BitmapUtils.blurRenderScript(this, blury, 25);
                    img_preview.setImageBitmap(blury);

                    edited = false;
                    name = true;
                }
                else if(ninebsix) {

                    blury = bitmap.createScaledBitmap(blury, 1040,1280, false);
                    blury = BitmapUtils.blurRenderScript(this, blury, 25);
                    img_preview.setImageBitmap(blury);

                    edited = false;
                    name = true;
                }
                else{
                    blury = bitmap.createScaledBitmap(blury, 1080,1080, false);
                    blury = BitmapUtils.blurRenderScript(this, blury, 25);
                    img_preview.setImageBitmap(blury);

                    edited = false;
                    name = true;
                }



            }
            else if (background == R.drawable.negro_conbor){
                if (onebone){

                    Bitmap white1 = Bitmap.createScaledBitmap(white, 1080,1080,false);
                    img_preview.setImageBitmap(white1);

                    colored = white1.copy(Bitmap.Config.ARGB_8888, true);
                    edited = true;

                    name = false;
                }
                else if (fourbthree || primero){

                    Bitmap white1 = Bitmap.createScaledBitmap(white, 1080,810,false);
                    img_preview.setImageBitmap(white1);

                    colored = white1.copy(Bitmap.Config.ARGB_8888, true);
                    edited = true;

                    name = false;
                }
                else if (fourbfive || segundo){

                    Bitmap white1 = Bitmap.createScaledBitmap(white, 1024,1280,false);
                    img_preview.setImageBitmap(white1);

                    colored = white1.copy(Bitmap.Config.ARGB_8888, true);
                    edited = true;

                    name = false;
                }
                else if(sixbnine || tercero) {

                    Bitmap white1 = Bitmap.createScaledBitmap(white, 1080,608,false);
                    img_preview.setImageBitmap(white1);

                    colored = white1.copy(Bitmap.Config.ARGB_8888, true);
                    edited = true;

                    name = false;
                }
                else if(threebfour) {

                    Bitmap white1 = Bitmap.createScaledBitmap(white, 1080,1280,false);
                    img_preview.setImageBitmap(white1);

                    colored = white1.copy(Bitmap.Config.ARGB_8888, true);
                    edited = true;

                    name = false;
                }
                else if(ninebsix) {

                    Bitmap white1 = Bitmap.createScaledBitmap(white, 1040,1280,false);
                    img_preview.setImageBitmap(white1);

                    colored = white1.copy(Bitmap.Config.ARGB_8888, true);
                    edited = true;

                    name = false;
                }
                else{
                    Bitmap white1 = Bitmap.createScaledBitmap(white, 1080,1080,false);
                    img_preview.setImageBitmap(white1);

                    colored = white1.copy(Bitmap.Config.ARGB_8888, true);
                    edited = true;

                    name = false;
                }

            }
            else {
                if (onebone){

                    backbit = bitmap.createScaledBitmap(bitmap3, 1080,1080, false);
                    img_preview.setImageBitmap(backbit);

                    colored = backbit.copy(Bitmap.Config.ARGB_8888, true);
                    edited = true;

                    name = false;
                }
                else if (fourbthree || primero){

                    backbit = bitmap.createScaledBitmap(bitmap3, 1080,810, false);
                    img_preview.setImageBitmap(backbit);

                    colored = backbit.copy(Bitmap.Config.ARGB_8888, true);
                    edited = true;

                    name = false;
                }
                else if (fourbfive || segundo){

                    backbit = bitmap.createScaledBitmap(bitmap3, 1024,1280, false);
                    img_preview.setImageBitmap(backbit);

                    colored = backbit.copy(Bitmap.Config.ARGB_8888, true);
                    edited = true;

                    name = false;
                }
                else if(sixbnine || tercero) {

                    backbit = bitmap.createScaledBitmap(bitmap3, 1080,608, false);
                    img_preview.setImageBitmap(backbit);

                    colored = backbit.copy(Bitmap.Config.ARGB_8888, true);
                    edited = true;

                    name = false;
                }
                else if(threebfour) {

                    backbit = bitmap.createScaledBitmap(bitmap3, 1080,1280, false);
                    img_preview.setImageBitmap(backbit);

                    colored = backbit.copy(Bitmap.Config.ARGB_8888, true);
                    edited = true;

                    name = false;
                }
                else if(ninebsix) {

                    backbit = bitmap.createScaledBitmap(bitmap3, 1040,1280, false);
                    img_preview.setImageBitmap(backbit);

                    colored = backbit.copy(Bitmap.Config.ARGB_8888, true);
                    edited = true;

                    name = false;
                }
                else{
                    backbit = bitmap.createScaledBitmap(bitmap3, 1080,1080, false);
                    img_preview.setImageBitmap(backbit);

                    colored = backbit.copy(Bitmap.Config.ARGB_8888, true);
                    edited = true;

                    name = false;
                }
            }


            //finalBitmap = backbit.copy(Bitmap.Config.ARGB_8888, true);
        }
    }


    @Override
    public void onFrilterSelected(Filter filter) {
        if(adjusting) {
            resetControl();
        }

        if (crop_bit){
            finalBitmap = heybit.copy(Bitmap.Config.ARGB_8888, true);
            imageView1.setImageBitmap(filter.processFilter(finalBitmap));
        }else {
            filteredBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
            imageView1.setImageBitmap(filter.processFilter(filteredBitmap));
        }

        if (name){
            if (onebone){

                filteredBitmap = bitmap.createScaledBitmap(filteredBitmap, 1080,1080, false);
                blurBitmap2 = BitmapUtils.blurRenderScript(this, filteredBitmap, 25);

                img_preview.setImageBitmap(filter.processFilter(blurBitmap2));

                finalBitmap = filteredBitmap.copy(Bitmap.Config.ARGB_8888, true);
            }
            else if (fourbthree || primero){

                filteredBitmap = bitmap.createScaledBitmap(filteredBitmap, 1080,810, false);
                blurBitmap2 = BitmapUtils.blurRenderScript(this, filteredBitmap, 25);

                img_preview.setImageBitmap(filter.processFilter(blurBitmap2));

                finalBitmap = filteredBitmap.copy(Bitmap.Config.ARGB_8888, true);
            }
            else if (fourbfive || segundo){

                filteredBitmap = bitmap.createScaledBitmap(filteredBitmap, 1024,1280, false);
                blurBitmap2 = BitmapUtils.blurRenderScript(this, filteredBitmap, 25);

                img_preview.setImageBitmap(filter.processFilter(blurBitmap2));

                finalBitmap = filteredBitmap.copy(Bitmap.Config.ARGB_8888, true);
            }
            else if(sixbnine || tercero) {

                filteredBitmap = bitmap.createScaledBitmap(filteredBitmap, 1080,608, false);
                blurBitmap2 = BitmapUtils.blurRenderScript(this, filteredBitmap, 25);

                img_preview.setImageBitmap(filter.processFilter(blurBitmap2));

                finalBitmap = filteredBitmap.copy(Bitmap.Config.ARGB_8888, true);
            }
            else if(threebfour) {

                filteredBitmap = bitmap.createScaledBitmap(filteredBitmap, 1080,1280, false);
                blurBitmap2 = BitmapUtils.blurRenderScript(this, filteredBitmap, 25);

                img_preview.setImageBitmap(filter.processFilter(blurBitmap2));

                finalBitmap = filteredBitmap.copy(Bitmap.Config.ARGB_8888, true);
            }
            else if(ninebsix) {

                filteredBitmap = bitmap.createScaledBitmap(filteredBitmap, 1040,1280, false);
                blurBitmap2 = BitmapUtils.blurRenderScript(this, filteredBitmap, 25);

                img_preview.setImageBitmap(filter.processFilter(blurBitmap2));

                finalBitmap = filteredBitmap.copy(Bitmap.Config.ARGB_8888, true);
            }
            else{
                filteredBitmap = bitmap.createScaledBitmap(filteredBitmap, 1080,1080, false);
                blurBitmap2 = BitmapUtils.blurRenderScript(this, filteredBitmap, 25);

                img_preview.setImageBitmap(filter.processFilter(blurBitmap2));

                finalBitmap = filteredBitmap.copy(Bitmap.Config.ARGB_8888, true);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_crop){

            try{
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + "com.software.pictureresizer")));
            } catch (ActivityNotFoundException e){
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.goole.com/store/apps/details?id=")));
            }
            return true;
        }
        if(id == R.id.action_open)
        {
            openImageFromGallery();
            return true;
        }
        if(id == R.id.action_save)
        {
            saveImageToGallery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startCrop(Uri uri) {
        String destinationFileName = new StringBuilder(UUID.randomUUID().toString()).append(".jpg").toString();

        UCrop.of(uri, Uri.fromFile(new File(getCacheDir(),destinationFileName)))
                .start(Editor.this);
    }


    private void saveImageToGallery() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if(report.areAllPermissionsGranted())
                        {

                            //img_preview.buildDrawingCache(true);
                            //Bitmap background = img_preview.getDrawingCache();

                            //imageView1.buildDrawingCache(true);
                            //Bitmap foreground = imageView1.getDrawingCache();

                            Bitmap background = BitmapUtils.takescreenShot(img_preview);
                            Bitmap foreground = BitmapUtils.takescreenShot(imageView1);
                            Bitmap lines = BitmapUtils.takescreenShot(border_main);

                            //Bitmap background = ((BitmapDrawable)img_preview.getDrawable()).getBitmap();
                            //Bitmap foreground = ((BitmapDrawable)imageView1.getDrawable()).getBitmap();


                            Bitmap mergedBitmap = BitmapUtils.bitmapOverlayToCenter(background, foreground,lines);

                            //Bitmap mergedBitmap = bitmap.createScaledBitmap(bitmap,1080,1080,false);

                            File path = Environment.getExternalStoragePublicDirectory(
                                    DIRECTORY_PICTURES);
                            if (!path.exists()) {
                                path.mkdirs();
                                System.out.println("Making dirs");
                            }
                            String filename = new SimpleDateFormat("yyMMddHHmmss").format(Calendar.getInstance().getTime()) + "profile.jpg";
                            //String filename = String.format("%d.jpg", System.currentTimeMillis());
                                    //
                            File file = new File(path, filename );
                            OutputStream out;

                            try{
                                out = new FileOutputStream(file);
                                mergedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

                                out.flush();
                                out.close();

                                Toast.makeText(Editor.this, "Image Saved in Gallery!!!", Toast.LENGTH_SHORT).show();

                                ContentValues values = new ContentValues();
                                values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg"); // or image/png
                                getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                            }

                            catch (IOException e) {
                                Toast.makeText(Editor.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(Editor.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();

                    }
                })
                .check();
    }

    private void openImageFromGallery() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if(report.areAllPermissionsGranted())
                        {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(intent,PERMISSION_PICK_IMAGE);
                        }
                        else {
                            Toast.makeText(Editor.this, "Permission denied!", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
        .check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PERMISSION_PICK_IMAGE) {

            Bitmap bitmap = BitmapUtils.getBitmapFromGallery(this, data.getData(), 1080, 1080);
            Uri uri = data.getData();
            Bitmap bitmap2 = null;
            try {
                bitmap2 = rotateImageIfRequired(this, bitmap, uri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            image_selected_uri = data.getData();

            //clear bitmap memory

            finalBitmap.recycle();
            filteredBitmap.recycle();
            //originalBitmap.recycle();



            originalBitmap = bitmap2.copy(Bitmap.Config.ARGB_8888, true);
            finalBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
            filteredBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);

            //blur image/background after opening
            blurBitmap2 = bitmap2.createScaledBitmap(originalBitmap, 1080,1080, false);
            blurBitmap2 = BitmapUtils.blurRenderScript(this, blurBitmap2, 25);
            img_preview.setImageBitmap(blurBitmap2);

            //set main image
            imageView1.setImageBitmap(originalBitmap);
            bitmap.recycle();
            bitmap2.recycle();


            //Render selected img thumbnail
            //filtersListFragment = FiltersListFragment.getInstance(originalBitmap);
            //filtersListFragment.setListener(Editor.this);

            filtersListFragment.displayThumbnail(originalBitmap);

            onebone = false;
            fourbthree = false;
            fourbfive = false;
            sixbnine = false;
            threebfour = false;
            ninebsix = false;

            primero = false;
            segundo = false;
            tercero = false;

            edited = false;
            crop_bit = false;
            name = true;

        }
        else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP){
            try {
                handleCropResult(data);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if (resultCode == RESULT_OK && requestCode == UCrop.RESULT_ERROR){
            handleCropError(data);
        }
    }

    private void handleCropError(Intent data) {
        final Throwable cropError = UCrop.getError(data);
        if(cropError != null){
            Toast.makeText(this, ""+cropError.getMessage(), Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Unexpected Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleCropResult(Intent data) throws IOException {
        final Uri resultUri = UCrop.getOutput(data);
        if(resultUri != null){
            Bitmap bitmap8 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
            heybit = bitmap8.copy(Bitmap.Config.ARGB_8888, true);
            imageView1.setImageBitmap(heybit);
            crop_bit = true;
        }
        else{
            Toast.makeText(this, "Cannot retrieve crop image", Toast.LENGTH_SHORT).show();
        }
    }

    private static Bitmap rotateImageIfRequired(Context context, Bitmap img, Uri selectedImage) throws IOException {

        InputStream input = context.getContentResolver().openInputStream(selectedImage);
        ExifInterface ei;
        if (Build.VERSION.SDK_INT > 23)
            ei = new ExifInterface(input);
        else
            ei = new ExifInterface(selectedImage.getPath());

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    @Override
    public void onRatioSelected(int ratio) {
        if (edited){
            blury = colored.copy(Bitmap.Config.ARGB_8888,true);
        }else  {
            blury = originalBitmap.copy(Bitmap.Config.ARGB_8888,true);
        }
        Bitmap bitmap3 = BitmapUtils.decodeSampledBitmapFromResource(getResources(), ratio, 100,100);

        if(bitmap3 != null){
            if (ratio == R.drawable.trans_onebyone){
                blury = bitmap.createScaledBitmap(blury, 1080,1080, false);
                blury = BitmapUtils.blurRenderScript(this, blury, 25);
                Toast.makeText(this, "1:1 Ratio", Toast.LENGTH_SHORT).show();
                img_preview.setImageBitmap(blury);

                onebone = true;
                fourbthree = false;
                fourbfive = false;
                sixbnine = false;
                threebfour = false;
                ninebsix = false;

                primero = false;
                segundo = false;
                tercero = false;
            }
            else if (ratio == R.drawable.trans_fourbythree){
                blury = bitmap.createScaledBitmap(blury, 1080,810, false);
                blury = BitmapUtils.blurRenderScript(this, blury, 25);
                Toast.makeText(this, "4:3 Ratio", Toast.LENGTH_SHORT).show();
                img_preview.setImageBitmap(blury);

                onebone = false;
                fourbthree = true;
                fourbfive = false;
                sixbnine = false;
                threebfour = false;
                ninebsix = false;

                primero = false;
                segundo = false;
                tercero = false;

            }
            else if (ratio == R.drawable.trans_fourbyfive){
                blury = bitmap.createScaledBitmap(blury, 1024,1280, false);
                blury = BitmapUtils.blurRenderScript(this, blury, 25);
                Toast.makeText(this, "4:5 Ratio", Toast.LENGTH_SHORT).show();
                img_preview.setImageBitmap(blury);

                onebone = false;
                fourbthree = false;
                fourbfive = true;
                sixbnine = false;
                threebfour = false;
                ninebsix = false;

                primero = false;
                segundo = false;
                tercero = false;
            }
            else if (ratio == R.drawable.trans_sixbynine){
                blury = bitmap.createScaledBitmap(blury, 1080,608, false);
                blury = BitmapUtils.blurRenderScript(this, blury, 25);
                Toast.makeText(this, "16:9 Ratio", Toast.LENGTH_SHORT).show();
                img_preview.setImageBitmap(blury);

                onebone = false;
                fourbthree = false;
                fourbfive = false;
                sixbnine = true;
                threebfour = false;
                ninebsix = false;

                primero = false;
                segundo = false;
                tercero = false;
            }
            else if (ratio == R.drawable.trans_threebyfour){
                blury = bitmap.createScaledBitmap(blury, 1080,1280, false);
                blury = BitmapUtils.blurRenderScript(this, blury, 25);
                Toast.makeText(this, "3:4 Ratio", Toast.LENGTH_SHORT).show();
                img_preview.setImageBitmap(blury);

                onebone = false;
                fourbthree = false;
                fourbfive = false;
                sixbnine = false;
                threebfour = true;
                ninebsix = false;

                primero = false;
                segundo = false;
                tercero = false;
            }
            else if (ratio == R.drawable.trans_ninebysix){
                blury = bitmap.createScaledBitmap(blury, 1040,1280, false);
                blury = BitmapUtils.blurRenderScript(this, blury, 25);
                Toast.makeText(this, "9:16 Ratio", Toast.LENGTH_SHORT).show();
                img_preview.setImageBitmap(blury);

                onebone = false;
                fourbthree = false;
                fourbfive = false;
                sixbnine = false;
                threebfour = false;
                ninebsix = true;

                primero = false;
                segundo = false;
                tercero = false;
            }

        }
    }

    @Override
    public void onBrightnessChanged(int brightness) {
        if (crop_bit){
            brightnessFinal = brightness;
            Filter myFilter = new Filter();
            myFilter.addSubFilter(new BrightnessSubFilter(brightness));
            imageView1.setImageBitmap(myFilter.processFilter(heybit.copy(Bitmap.Config.ARGB_8888, true)));
        }
        else {
            brightnessFinal = brightness;
            Filter myFilter = new Filter();
            myFilter.addSubFilter(new BrightnessSubFilter(brightness));
            imageView1.setImageBitmap(myFilter.processFilter(originalBitmap.copy(Bitmap.Config.ARGB_8888, true)));
        }

    }

    @Override
    public void onSaturationChanged(float saturation) {
        if (crop_bit) {
            saturationFinal = saturation;
            Filter myFilter = new Filter();
            myFilter.addSubFilter(new SaturationSubfilter(saturation));
            imageView1.setImageBitmap(myFilter.processFilter(heybit.copy(Bitmap.Config.ARGB_8888, true)));

        }
        else {
            saturationFinal = saturation;
            Filter myFilter = new Filter();
            myFilter.addSubFilter(new SaturationSubfilter(saturation));
            imageView1.setImageBitmap(myFilter.processFilter(originalBitmap.copy(Bitmap.Config.ARGB_8888, true)));

        }

    }

    @Override
    public void onConstrantChanged(float constrant) {
        if (crop_bit) {
            constrantFinal = constrant;
            Filter myFilter = new Filter();
            myFilter.addSubFilter(new ContrastSubFilter(constrant));
            imageView1.setImageBitmap(myFilter.processFilter(heybit.copy(Bitmap.Config.ARGB_8888, true)));
        }
        else {
            constrantFinal = constrant;
            Filter myFilter = new Filter();
            myFilter.addSubFilter(new ContrastSubFilter(constrant));
            imageView1.setImageBitmap(myFilter.processFilter(originalBitmap.copy(Bitmap.Config.ARGB_8888, true)));
        }


    }

    @Override
    public void onEditStarted() {
        adjusting = true;
    }

    @Override
    public void onEditCompleted() {

        Bitmap bitmap = filteredBitmap.copy(Bitmap.Config.ARGB_8888, true);

        Filter myFilter = new Filter();
        myFilter.addSubFilter(new BrightnessSubFilter(brightnessFinal));
        myFilter.addSubFilter(new SaturationSubfilter(saturationFinal));
        myFilter.addSubFilter(new ContrastSubFilter(constrantFinal));

        finalBitmap = myFilter.processFilter(bitmap);
    }

    private void resetControl() {
        if(adjustImageFragment != null) {
            adjustImageFragment.resetControls();
        }
        brightnessFinal = 0;
        saturationFinal = 1.0f;
        constrantFinal = 1.0f;
    }

    @Override
    public void onBorderSelected(int border) {

        Bitmap bitmap_border = BitmapUtils.decodeSampledBitmapFromResource(getResources(),border,100,100);
        if (bitmap_border != null){
            if (border == R.drawable.whitex_trans){
                border_main.setImageDrawable(null);
            }
            if (border == R.drawable.whie_line){
                border_main.setImageResource(R.drawable.linia_blanca);
            }
            else if (border == R.drawable.lime_line){
                border_main.setImageResource(R.drawable.linia_limon);
            }
            else if (border == R.drawable.aqua_line){
                border_main.setImageResource(R.drawable.linia_aqua);
            }
            else if (border == R.drawable.blue_line){
                border_main.setImageResource(R.drawable.linia_azul);
            }
            else if (border == R.drawable.olive_line){
                border_main.setImageResource(R.drawable.linia_olive);
            }
            else if (border == R.drawable.red_line){
                border_main.setImageResource(R.drawable.linia_roja);
            }
            else if (border == R.drawable.grey_line){
                border_main.setImageResource(R.drawable.linia_gris);
            }
            else if (border == R.drawable.pink_line){
                border_main.setImageResource(R.drawable.linia_rosada);
            }
            else if (border == R.drawable.yellow_line){
                border_main.setImageResource(R.drawable.linia_amarilla);
            }
            else if (border == R.drawable.purple_line){
                border_main.setImageResource(R.drawable.linia_morada);
            }
            else if (border == R.drawable.anaranja_line){
                border_main.setImageResource(R.drawable.linia_anaranjada);
            }
            else if (border == R.drawable.brown_line){
                border_main.setImageResource(R.drawable.linia_cafe);
            }
        }
    }

}
