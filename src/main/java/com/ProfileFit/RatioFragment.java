package com.software.ProfileFit;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.software.ProfileFit.Adapter.RatioAdapter;

public class RatioFragment extends Fragment implements RatioAdapter.RatioAdapterListener {

    RecyclerView recycler_ratio;

    RatioAdapter.RatioAdapterListener listener;


    int background_selected = -1;

    static FiltersListFragment instance;
    //static RatioFragment instance;
    static Bitmap bitmap;

    public static FiltersListFragment getInstance(Bitmap bitmapSave) {
        bitmap = bitmapSave;

        if(instance == null)
        {
            instance = new FiltersListFragment();
        }

        return instance;
    }

    public void setListener(RatioAdapter.RatioAdapterListener listener) {
        this.listener = listener;
    }

    public RatioFragment() {
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
        View itemView = inflater.inflate(R.layout.fragment_ratio, container, false);

        recycler_ratio = (RecyclerView) itemView.findViewById(R.id.recycler_ratio);


        recycler_ratio.setHasFixedSize(true);
        recycler_ratio.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recycler_ratio.setAdapter(new RatioAdapter(getContext(),this));


        return  itemView;
    }

    @Override
    public void onRatioSelected(int ratio) {
        if(listener != null)
            listener.onRatioSelected(ratio);
        else
            listener.onRatioSelected(ratio);
    }
}
