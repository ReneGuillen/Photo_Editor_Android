package com.software.ProfileFit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.software.ProfileFit.Adapter.ThumbnailAdapter;
import com.software.ProfileFit.Interface.FirltersListFragmentListener;
import com.software.ProfileFit.Utils.BitmapUtils;
import com.software.ProfileFit.Utils.SpacesItemDecoration;
import com.zomato.photofilters.FilterPack;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.utils.ThumbnailItem;
import com.zomato.photofilters.utils.ThumbnailsManager;

import java.util.ArrayList;
import java.util.List;

 //A simple {@link Fragment} subclass.

public class FiltersListFragment extends Fragment implements FirltersListFragmentListener {
    RecyclerView recyclerView;
    ThumbnailAdapter adapter;
    List<ThumbnailItem> thumbnailItems;

    FirltersListFragmentListener listener;

    static FiltersListFragment instance;
    static Bitmap bitmap;

    public static FiltersListFragment getInstance(Bitmap bitmapSave) {
        bitmap = bitmapSave;

        if(instance == null)
        {
            instance = new FiltersListFragment();
        }

        return instance;
    }

    public void setListener(FirltersListFragmentListener listener) {
        this.listener = listener;
    }

    public FiltersListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_filters_list, container, false);

        thumbnailItems = new ArrayList<>();
        adapter = new ThumbnailAdapter(thumbnailItems, this, getActivity());

        recyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        //int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8,getResources().getDisplayMetrics());
        //recyclerView.addItemDecoration(new SpacesItemDecoration(space));
        recyclerView.setAdapter(adapter);

        displayThumbnail(bitmap);

        return  itemView;
    }

    public void displayThumbnail(final Bitmap bitmap) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Bitmap thumbImg;
                if(bitmap == null){
                    //thumbImg = BitmapFactory.decodeResource(getResources(), R.drawable.filter_image);
                    thumbImg = BitmapUtils.decodeSampledBitmapFromResource(getResources(),R.drawable.filter_image, 50,50);
                }
                else {
                    //thumbImg = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
                    thumbImg = BitmapUtils.decodeSampledBitmapFromResource(getResources(),R.drawable.filter_image, 50,50);
                    //thumbImg = BitmapFactory.decodeResource(getResources(), R.drawable.filter_image);
                    //thumbImg = BitmapUtils.getBitmapFromAssets(getActivity(),Editor.pictureName, 100, 100 );
                }
                if(thumbImg == null)
                    return;
                ThumbnailsManager.clearThumbs();
                thumbnailItems.clear();

                //add normal bitmap first
                ThumbnailItem thumbnailItem = new ThumbnailItem();
                thumbnailItem.image = thumbImg;
                thumbnailItem.filterName = "normal";
                ThumbnailsManager.addThumb(thumbnailItem);

                //List<Filter> filters = FilterPack.getFilterPack(getActivity());
                List<Filter> filters = PackofFilters.getFilterPack(getActivity());

                for(Filter filter:filters)
                {
                    ThumbnailItem tI = new ThumbnailItem();
                    tI.image = thumbImg;
                    tI.filter = filter;
                    tI.filterName = "no";
                    ThumbnailsManager.addThumb(tI);
                }
                thumbnailItems.addAll(ThumbnailsManager.processThumbs(getActivity()));

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        };
        new Thread(r).start();
    }

    @Override
    public void onFrilterSelected(Filter filter) {
        if(listener != null)
            listener.onFrilterSelected(filter);
    }
}
