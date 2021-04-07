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

//Filter Class Fragment.
public class FiltersListFragment extends Fragment implements FirltersListFragmentListener {
    private static RecyclerView recyclerView;
    private static ThumbnailAdapter adapter;
    private static List<ThumbnailItem> thumbnailItems;
    private static FirltersListFragmentListener listener;
    private static FiltersListFragment instance;
    private static Bitmap bitmap;

    //Get initial instace from the saved bitmap.
    public static FiltersListFragment getInstance(Bitmap bitmapSave) {
        bitmap = bitmapSave;
        if(instance == null)
        {
            instance = new FiltersListFragment();
        }

        return instance;
    }

    //Main listener set to the filters list fragment.
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

    //Create the new view from filters list, recycle unused elements and display thumnail.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_filters_list, container, false);
        thumbnailItems = new ArrayList<>();
        adapter = new ThumbnailAdapter(thumbnailItems, this, getActivity());
        recyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(adapter);
        displayThumbnail(bitmap);
        //Return the new item view.
        return  itemView;
    }

   //Method to display a thumbnail based on the parameter bitmap passed.
    public void displayThumbnail(final Bitmap bitmap) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Bitmap thumbImg;
                if(bitmap == null){
                    thumbImg = BitmapUtils.decodeSampledBitmapFromResource(getResources(),R.drawable.filter_image, 50,50);
                }
                else {
                    thumbImg = BitmapUtils.decodeSampledBitmapFromResource(getResources(),R.drawable.filter_image, 50,50);
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

    //Handle filter selection when a new filter is selected.
    @Override
    public void onFrilterSelected(Filter filter) {
        if(listener != null)
            listener.onFrilterSelected(filter);
    }
}
