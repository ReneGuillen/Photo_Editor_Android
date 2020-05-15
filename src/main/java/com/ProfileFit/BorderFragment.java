package com.software.ProfileFit;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.software.ProfileFit.Adapter.BackgroundAdapter;
import com.software.ProfileFit.Adapter.BorderAdapter;
import com.software.ProfileFit.FiltersListFragment;
import com.software.ProfileFit.Interface.BorderFragmentListerner;
import com.software.ProfileFit.Interface.EditImageFragmentListener;
import com.software.ProfileFit.R;


public class BorderFragment extends Fragment implements BorderAdapter.BorderAdapterListener {



    RecyclerView recycler_border;

    BorderAdapter.BorderAdapterListener listener;


    int background_selected = -1;

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

    public void setListener(BorderAdapter.BorderAdapterListener listener) {
        this.listener = listener;
    }

    public BorderFragment() {
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
        View itemView = inflater.inflate(R.layout.fragment_border, container, false);

        recycler_border = (RecyclerView) itemView.findViewById(R.id.recycler_border);


        recycler_border.setHasFixedSize(true);
        recycler_border.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recycler_border.setAdapter(new BorderAdapter(getContext(),this));

        return  itemView;
    }

    @Override
    public void onBorderSelected(int border) {
        if(listener != null)
            listener.onBorderSelected(border);
        else
            listener.onBorderSelected(border);
    }
}
