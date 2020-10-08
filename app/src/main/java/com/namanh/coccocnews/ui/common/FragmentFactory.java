package com.namanh.coccocnews.ui.common;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.namanh.coccocnews.ui.category.CategoryFragment;
import com.namanh.coccocnews.ui.news.NewsFragment;
import com.namanh.coccocnews.utils.LogUtil;

public class FragmentFactory {

    private static final String TAG = "FragmentFactory";

    public static final class SCREEN {
        public static final String CATEGORY = "category";
        public static final String NEWS = "news";
    }

    public static Fragment create(String tag, FragmentManager fragmentManager, Bundle bundle) {
        LogUtil.d(TAG, "createFragment: " + "tag - " + tag);
        if (fragmentManager == null || tag == null) return new Fragment();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) return createFragmentByTag(tag, bundle);
        return fragment;
    }

    private static Fragment createFragmentByTag(String tag, Bundle bundle) {
        LogUtil.d(TAG, "createFragmentByTag: " + tag);
        switch (tag) {
            case SCREEN.CATEGORY:
                return CategoryFragment.create(bundle);
            case SCREEN.NEWS:
                return NewsFragment.create(bundle);
            default:
                LogUtil.throwEx(TAG, "Can't create fragment with tag: " + tag);
        }
        return null;
    }

}
