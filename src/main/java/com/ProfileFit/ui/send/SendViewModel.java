package com.software.ProfileFit.ui.send;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SendViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SendViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("If you enjoy the app or have any suggestions please rate the app on play store!!");
    }

    public LiveData<String> getText() {
        return mText;
    }
}