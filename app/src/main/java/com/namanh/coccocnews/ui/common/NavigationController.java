package com.namanh.coccocnews.ui.common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProviders;

import com.namanh.coccocnews.R;
import com.namanh.coccocnews.ui.main.MainAppViewModel;
import com.namanh.coccocnews.ui.main.ScreenModel;
import com.namanh.coccocnews.utils.LogUtil;

import java.util.List;

public class NavigationController implements LifecycleObserver {

    private static final String TAG = "NavigationController";

    private static NavigationController sController;
    private final int mContainerIdMain;

    private AppCompatActivity mActivity;
    private FragmentManager mFragmentManager;
    private MainAppViewModel mMainAppViewModel;

    public void init(AppCompatActivity appCompatActivity) {
        LogUtil.i(TAG, "init");
        this.mActivity = appCompatActivity;
        this.mFragmentManager = mActivity.getSupportFragmentManager();
        registerListenerScreen();
    }

    private NavigationController() {
        LogUtil.i(TAG, "create instance");
        this.mContainerIdMain = R.id.container_main;
    }

    public static NavigationController getInstance() {
        if (sController == null) {
            synchronized (NavigationController.class) {
                if (sController == null) {
                    sController = new NavigationController();
                }
            }
        }
        return sController;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        LogUtil.i(TAG, "onDestroy");
        mActivity = null;
        mFragmentManager = null;
    }

    private void registerListenerScreen() {
        LogUtil.i(TAG, "registerListenerScreen");
        if (mActivity == null) return;
        this.mMainAppViewModel = ViewModelProviders.of(mActivity).get(MainAppViewModel.class);
        mMainAppViewModel.getAppScreen().observe(mActivity, screenModel -> {
                    if (screenModel == null) {
                        LogUtil.throwEx(TAG, "Can't listener with screenModel is null");
                    }
                    LogUtil.d(TAG, "screen change: " + screenModel.screenName);
                    navigationFragment(screenModel);
                }
        );
    }

    public void setScreen(ScreenModel screenModel) {
        if (mMainAppViewModel == null) return;
        if (screenModel == null) {
            LogUtil.throwEx(TAG, "Can't set with screenModel is null");
        }
        LogUtil.d(TAG, "set screen: " + screenModel.screenName);
        mMainAppViewModel.setAppScreen(screenModel);
    }

    private void navigationFragment(ScreenModel screenModel) {
        if (mFragmentManager == null) return;
        LogUtil.d(TAG, "navigationFragment - screenName: " + screenModel.screenName);

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment fragment = FragmentFactory.create(screenModel.screenName, mFragmentManager, screenModel.bundleData);
        List<Fragment> listFragment = mFragmentManager.getFragments();

        if (screenModel.isAnimation) {
            fragmentTransaction.setCustomAnimations(R.anim.enter_right_to_left, R.anim.exit_right_to_left, R.anim.enter_left_to_right, R.anim.exit_left_to_right);
        }

        if (!screenModel.isAdd) {
            LogUtil.d(TAG, "navigationFragment - replace fragment: ");
            fragmentTransaction.replace(mContainerIdMain, fragment, screenModel.screenName);
        } else {
            for (int i = 0; i < listFragment.size(); i++) {
                fragmentTransaction.hide(listFragment.get(i));
            }
            if (!fragment.isAdded()) {
                LogUtil.d(TAG, "navigationFragment: fragment not added!!! - Todo Add");
                fragmentTransaction.add(mContainerIdMain, fragment, screenModel.screenName);
            } else {
                LogUtil.d(TAG, "navigationFragment: fragment added!!! - Todo show");
                fragmentTransaction.show(fragment);
            }
        }

        if (screenModel.isBackStack) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
        mFragmentManager.executePendingTransactions();
    }

    public void popAllBackStack() {
        for (int i = 0; i < mFragmentManager.getBackStackEntryCount(); ++i) {
            mFragmentManager.popBackStack();
        }
    }

}
