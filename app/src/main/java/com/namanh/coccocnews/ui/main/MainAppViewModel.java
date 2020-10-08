package com.namanh.coccocnews.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class MainAppViewModel extends AndroidViewModel {

    private MutableLiveData<ScreenModel> mAppScreen;

    public MainAppViewModel(@NonNull Application application) {
        super(application);
        mAppScreen = new MutableLiveData<>();
    }

    public MutableLiveData<ScreenModel> getAppScreen() {
        return mAppScreen;
    }

    public void setAppScreen(ScreenModel appScreen) {
        this.mAppScreen.setValue(appScreen);
    }

}
