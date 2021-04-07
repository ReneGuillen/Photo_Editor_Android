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
    
    private static RecyclerView recycler_border;
    private static BorderAdapter.BorderAdapterListener listener;
    private static int background_selected = -1;
    private static FiltersListFragment instance;
    private static Bitmap bitmap;

    //Method to filter bitmap based on list fragment.
    public static FiltersListFragment getInstance(Bitmap bitmapSave) {
        bitmap = bitmapSave;
        if(instance == null)
            instance = new FiltersListFragment();
        return instance;
    }
    
    //Initialize listener to borderAdapter.
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

    //Create initial view when changed to this screen and set values.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_border, container, false);
        recycler_border = (RecyclerView) itemView.findViewById(R.id.recycler_border);
        //Recycle elemts after their use.
        recycler_border.setHasFixedSize(true);
        recycler_border.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recycler_border.setAdapter(new BorderAdapter(getContext(),this));
        //return the new itemView after recycling.
        return  itemView;
    }

    //Set the new border selected onTouched.
    @Override
    public void onBorderSelected(int border) {
        if(listener != null)
            listener.onBorderSelected(border);
        else
            listener.onBorderSelected(border);
    }
}
