package com.software.ProfileFit;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.software.ProfileFit.Interface.AdjustImageFragmentListener;

public class AdjustImageFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {

    private AdjustImageFragmentListener listener;
    private SeekBar seek_brightness, seek_constrant, seek_saturation;
    private TextView brigness_text, constrain_text, saturation_text;

    //Event Listener when onTouch is applied.
    public void setListener(AdjustImageFragmentListener listener) {
        this.listener = listener;
    }

    public AdjustImageFragment() {
        // Required empty public constructor
    }
    
    //Set savedInstance to create.
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //Set the initial view and adjust brightness, contrast and saturation bar.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_adjust_image, container, false);

        //Increase/Decrease brightness, contrast or saturation on the image.
        seek_brightness = (SeekBar) itemView.findViewById(R.id.seekbar_brightness);
        seek_constrant = (SeekBar) itemView.findViewById(R.id.seekbar_constraint);
        seek_saturation = (SeekBar) itemView.findViewById(R.id.seekbar_saturation);

        //Adjust text to show what percentage is currently being applied.
        brigness_text = (TextView) itemView.findViewById(R.id.brightness_text);
        constrain_text = (TextView) itemView.findViewById(R.id.constrain_text);
        saturation_text = (TextView) itemView.findViewById(R.id.saturation_text);

        //Set brightness when increasing/decreasing amount on bar.
        seek_brightness.getProgressDrawable().setColorFilter(Color.CYAN, PorterDuff.Mode.SRC_IN);
        seek_brightness.getThumb().setColorFilter(Color.CYAN, PorterDuff.Mode.SRC_IN);

        //Set contrast when increasing/decreasing amount on bar.
        seek_constrant.getProgressDrawable().setColorFilter(Color.CYAN, PorterDuff.Mode.SRC_IN);
        seek_constrant.getThumb().setColorFilter(Color.CYAN, PorterDuff.Mode.SRC_IN);
        
        //Set saturation when increasing/decreasing amount on bar.
        seek_saturation.getProgressDrawable().setColorFilter(Color.CYAN, PorterDuff.Mode.SRC_IN);
        seek_saturation.getThumb().setColorFilter(Color.CYAN, PorterDuff.Mode.SRC_IN);
        
        //Max amount of brightness and percentage.
        seek_brightness.setMax(200);
        seek_brightness.setProgress(100);
        brigness_text.setText("100%");

        //Max amount of contrast and percentage.
        seek_constrant.setMax(20);
        seek_constrant.setProgress(0);
        constrain_text.setText("0%");

        //Max amount of saturation and percentage.
        seek_saturation.setMax(30);
        seek_saturation.setProgress(10);
        saturation_text.setText("10%");

        seek_saturation.setOnSeekBarChangeListener(this);
        seek_constrant.setOnSeekBarChangeListener(this);
        seek_brightness.setOnSeekBarChangeListener(this);

        return itemView;

    }

    //Adjust text onScreen when bar point is change.
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(listener != null)
        {
            //For brightness.
            if(seekBar.getId() == R.id.seekbar_brightness)
            {
                brigness_text.setText("" + progress + "%");
                listener.onBrightnessChanged(progress-100);

            }
            //for contrast.
            else if(seekBar.getId() == R.id.seekbar_constraint)
            {
                constrain_text.setText("" + progress + "%");
                progress +=10;
                float value = .10f*progress;
                listener.onConstrantChanged(value);

            }
            //for saturation.
            else if(seekBar.getId() == R.id.seekbar_saturation)
            {
                saturation_text.setText("" + progress + "%");
                float value = .10f*progress;
                listener.onSaturationChanged(value);

            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if(listener != null)
            listener.onEditStarted();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if(listener != null)
            listener.onEditCompleted();
    }

    //Method to reset controllers to its original point.
    public void resetControls()
    {
        seek_brightness.setProgress(100);
        seek_constrant.setProgress(0);
        seek_saturation.setProgress(10);
    }

}
