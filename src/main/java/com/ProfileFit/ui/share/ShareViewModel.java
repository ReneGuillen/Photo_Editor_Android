package com.software.ProfileFit.ui.share;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShareViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ShareViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("You always have the option to remove ads and support the app for $2.99" );
    }

    public LiveData<String> getText() {
        return mText;
    }
}