package com.namanh.coccocnews.application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.namanh.coccocnews.ui.category.CategoryViewModel;
import com.namanh.coccocnews.ui.news.NewsViewModel;

public class ViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppExecutors mAppExecutors;

    public ViewModelProviderFactory(AppExecutors appExecutors) {
        this.mAppExecutors = appExecutors;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CategoryViewModel.class)) {
            return (T) new CategoryViewModel(mAppExecutors);
        }
        if (modelClass.isAssignableFrom(NewsViewModel.class)) {
            return (T) new NewsViewModel(mAppExecutors);
        }
        return super.create(modelClass);
    }
}
