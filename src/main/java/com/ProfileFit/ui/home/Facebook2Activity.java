package com.software.ProfileFit.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.software.ProfileFit.Editor;
import com.software.ProfileFit.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class Facebook2Activity extends AppCompatActivity {

    ImageView mImageView;
    Button mChooseBtn;
    Button mChooseBtn2;
    Button instabuton1;
    Button instabuton2;
    Button whatsbuton1;
    Button whatsbuton2;
    Button twitbuton1;
    Button twitbuton2;

    boolean three_fou = false;
    boolean four_fiv = false;
    boolean six_nin = false;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook2);

        //VIEWS
        mChooseBtn = findViewById(R.id.choose_image_btn);
        mChooseBtn2 = findViewById(R.id.button5);
        instabuton1 = findViewById(R.id.buttonInst1);
        instabuton2 = findViewById(R.id.buttonInst2);
        whatsbuton1 = findViewById(R.id.buttonWhats1);
        whatsbuton2 = findViewById(R.id.buttonWhats2);
        twitbuton1 = findViewById(R.id.buttonTwitt1);
        twitbuton2 = findViewById(R.id.buttonTwitt2);

        AdView adView = (AdView)findViewById(R.id.adViewBanner);
        MobileAds.initialize(this, "ca-app-pub-6822014320677335~3807512134");
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        //handle button click
        mChooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        //permission not granted, request it.
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else {
                        //permission already granted
                        pickImageFromGallery();

                    }
                }
                else {
                    //system os is less then marshmallow
                    pickImageFromGallery();

                }

            }

        });
        mChooseBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//check runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        //permission not granted, request it.
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else {
                        //permission already granted
                        three_fou = true;
                        pickImageFromGallery();

                    }
                }
                else {
                    //system os is less then marshmallow
                    three_fou = true;
                    pickImageFromGallery();

                }
            }
        });

        instabuton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        //permission not granted, request it.
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else {
                        //permission already granted
                        pickImageFromGallery();

                    }
                }
                else {
                    //system os is less then marshmallow
                    pickImageFromGallery();

                }

            }

        });

        instabuton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        //permission not granted, request it.
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else {
                        //permission already granted
                        four_fiv = true;
                        pickImageFromGallery();

                    }
                }
                else {
                    //system os is less then marshmallow
                    four_fiv = true;
                    pickImageFromGallery();

                }

            }

        });
        whatsbuton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        //permission not granted, request it.
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else {
                        //permission already granted
                        pickImageFromGallery();

                    }
                }
                else {
                    //system os is less then marshmallow
                    pickImageFromGallery();

                }

            }

        });
        whatsbuton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        //permission not granted, request it.
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else {
                        //permission already granted
                        four_fiv = true;
                        pickImageFromGallery();

                    }
                }
                else {
                    //system os is less then marshmallow
                    four_fiv = true;
                    pickImageFromGallery();

                }

            }

        });
        twitbuton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        //permission not granted, request it.
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else {
                        //permission already granted
                        pickImageFromGallery();

                    }
                }
                else {
                    //system os is less then marshmallow
                    pickImageFromGallery();

                }

            }

        });
        twitbuton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        //permission not granted, request it.
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else {
                        //permission already granted
                        six_nin = true;
                        pickImageFromGallery();

                    }
                }
                else {
                    //system os is less then marshmallow
                    six_nin = true;
                    pickImageFromGallery();

                }

            }

        });


    }
    private void pickImageFromGallery() {
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);


    }

    //handle result of runtime permission

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length >0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    //permission was granted
                    pickImageFromGallery();
                }
                else {
                    //permission was denied
                    Toast.makeText(this, "Permission denied..!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    //handle result of picked image

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            //set image to view
            Uri uri = data.getData();
            if(three_fou){
                Intent i = new Intent(Facebook2Activity.this, Editor.class);
                i.putExtra("imageUri", uri.toString());
                i.putExtra("four_three",true);
                Facebook2Activity.this.startActivity(i);
            }
            else if (four_fiv){
                Intent i = new Intent(Facebook2Activity.this, Editor.class);
                i.putExtra("imageUri", uri.toString());
                i.putExtra("four_five",true);
                Facebook2Activity.this.startActivity(i);
            }
            else if (six_nin){
                Intent i = new Intent(Facebook2Activity.this, Editor.class);
                i.putExtra("imageUri", uri.toString());
                i.putExtra("six_nine",true);
                Facebook2Activity.this.startActivity(i);
            }
            else{
                Intent i = new Intent(Facebook2Activity.this, Editor.class);
                i.putExtra("imageUri", uri.toString());
                Facebook2Activity.this.startActivity(i);
            }



        }
    }

}
