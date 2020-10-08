package com.namanh.coccocnews.ui.main;

import android.os.Bundle;

public class ScreenModel {

    public String screenName;
    public Bundle bundleData;
    public boolean isAdd;
    public boolean isBackStack;
    public boolean isAnimation;

    public ScreenModel(String screenName, Bundle bundleData, boolean isAdd, boolean isBackStack, boolean isAnimation) {
        this.screenName = screenName;
        this.bundleData = bundleData;
        this.isAdd = isAdd;
        this.isBackStack = isBackStack;
        this.isAnimation = isAnimation;
    }

    public static ScreenModel createScreen(String screenName, Bundle bundleData, boolean isAdd, boolean isBackStack, boolean isAnimation) {
        return new ScreenModel(screenName, bundleData, isAdd, isBackStack, isAnimation);
    }

    public void setBundleData(Bundle bundleData) {
        this.bundleData = bundleData;
    }

}
