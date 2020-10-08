package com.namanh.coccocnews.ui.common;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.namanh.coccocnews.application.AppExecutors;
import com.namanh.coccocnews.application.ViewModelProviderFactory;
import com.namanh.coccocnews.ui.main.MainAppViewModel;
import com.namanh.coccocnews.utils.LogUtil;

public abstract class BaseFragment<T extends ViewDataBinding, M extends ViewModel> extends Fragment {

    private MainAppViewModel mMainAppViewModel;
    private AutoClearedValue<T> mBindingView;
    private M mViewModel;
    private AppExecutors mAppExecutors;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(getFragmentName(), "onCreate");
        mAppExecutors = AppExecutors.getInstance();
        mViewModel = new ViewModelProvider(this, new ViewModelProviderFactory(mAppExecutors)).get(getViewModelClass());
        if (getActivity() == null) return;
        mMainAppViewModel = new ViewModelProvider(getActivity()).get(MainAppViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.i(getFragmentName(), "onCreateView");

        T dataBinding = DataBindingUtil.inflate(inflater, getIdLayout(), container, false);
        mBindingView = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();
    }

    protected abstract String getFragmentName();

    @LayoutRes
    protected abstract int getIdLayout();

    public abstract int getBindingVariable();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.i(getFragmentName(), "onActivityCreated");
        getBindingView().setVariable(getBindingVariable(), mViewModel);
        getBindingView().setLifecycleOwner(this);
        getBindingView().executePendingBindings();
    }

    protected abstract Class<M> getViewModelClass();

    public T getBindingView() {
        return this.mBindingView.getValue();
    }

    public M getViewModel() {
        return this.mViewModel;
    }

    public MainAppViewModel getMainAppViewModel() {
        return this.mMainAppViewModel;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i(getFragmentName(), "onDestroy");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogUtil.d(getFragmentName(), "onConfigurationChanged: " + newConfig);
    }

}
