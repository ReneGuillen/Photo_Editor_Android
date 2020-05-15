package com.software.ProfileFit;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.software.ProfileFit.Adapter.BackgroundAdapter;
import com.software.ProfileFit.Interface.EditImageFragmentListener;


public class EditImageFragment extends Fragment implements BackgroundAdapter.BackgroundAdapterListener {



    RecyclerView recycler_background;
    Button button;

    EditImageFragmentListener listener;


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

    public void setListener(EditImageFragmentListener listener) {
        this.listener = listener;
    }

    public EditImageFragment() {
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
        View itemView = inflater.inflate(R.layout.fragment_edit_image, container, false);

        recycler_background = (RecyclerView) itemView.findViewById(R.id.recycler_background);


        recycler_background.setHasFixedSize(true);
        recycler_background.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recycler_background.setAdapter(new BackgroundAdapter(getContext(),this));


        return  itemView;
    }

    @Override
    public void onBackgroundSelected(int background) {
        if(listener != null)
            listener.onAddBackground(background);
        else
            listener.onAddBackground(background);
    }

    private void enterNextFragment() {
        BorderFragment borderFragment = new BorderFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        //Store the Fragment in stack
        transaction.addToBackStack(null);
        transaction.replace(R.id.background_id, borderFragment);
    }
}
