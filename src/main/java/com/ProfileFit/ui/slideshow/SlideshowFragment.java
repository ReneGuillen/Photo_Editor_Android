package com.software.ProfileFit.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.software.ProfileFit.R;
import com.software.ProfileFit.ui.home.Facebook2Activity;

public class SlideshowFragment extends Fragment {

    public SlideshowFragment(){

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);
        Intent intent = new Intent(getActivity(), Facebook2Activity.class);
        startActivity(intent);

        Button button = (Button) view.findViewById(R.id.button8);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Facebook2Activity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}