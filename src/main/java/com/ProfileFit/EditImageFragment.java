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

//Background class to take care of all funtions onTouched.
public class EditImageFragment extends Fragment implements BackgroundAdapter.BackgroundAdapterListener {
    private static RecyclerView recycler_background;
    private static Button button;
    private static EditImageFragmentListener listener;
    private static int background_selected = -1;
    private static FiltersListFragment instance;
    private static Bitmap bitmap;
    
    //Set initial bitmap list based on fragment.
    public static FiltersListFragment getInstance(Bitmap bitmapSave) {
        bitmap = bitmapSave;
        if(instance == null)
            instance = new FiltersListFragment();

        return instance;
    }

    //Initialize listener to fragment listerner.
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

    //Create the new view to work with the background features.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_edit_image, container, false);
        //Recycle elements that wont be used.
        recycler_background = (RecyclerView) itemView.findViewById(R.id.recycler_background);
        recycler_background.setHasFixedSize(true);
        recycler_background.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recycler_background.setAdapter(new BackgroundAdapter(getContext(),this));
        //Return new itemView after recycling.
        return  itemView;
    }

    //Method to handle background selections.
    @Override
    public void onBackgroundSelected(int background) {
        if(listener != null)
            listener.onAddBackground(background);
        else
            listener.onAddBackground(background);
    }

    //Method to change to the next fragment when slide to the next window.
    private void enterNextFragment() {
        BorderFragment borderFragment = new BorderFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        //Store the Fragment in stack
        transaction.addToBackStack(null);
        transaction.replace(R.id.background_id, borderFragment);
    }
}
