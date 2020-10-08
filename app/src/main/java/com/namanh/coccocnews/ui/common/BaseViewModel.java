package com.namanh.coccocnews.ui.common;

import androidx.lifecycle.ViewModel;

import com.namanh.coccocnews.application.AppExecutors;

public class BaseViewModel extends ViewModel {

    private final AppExecutors mAppExecutors;

    public BaseViewModel(AppExecutors appExecutors) {
        this.mAppExecutors = appExecutors;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public AppExecutors getAppExecutors() {
        return mAppExecutors;
    }
}
