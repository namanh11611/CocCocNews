package com.namanh.coccocnews.ui.category;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.namanh.coccocnews.BR;
import com.namanh.coccocnews.R;
import com.namanh.coccocnews.databinding.FragmentCategoryBinding;
import com.namanh.coccocnews.helper.DarkModePreference;
import com.namanh.coccocnews.model.Article;
import com.namanh.coccocnews.ui.common.BaseFragment;
import com.namanh.coccocnews.utils.LogUtil;
import com.namanh.coccocnews.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends BaseFragment<FragmentCategoryBinding, CategoryViewModel> implements View.OnClickListener {

    private static final String TAG = "CategoryFragment";

    private List<Article> mArticleList;
    private ArticleAdapter adapter;

    public static CategoryFragment create(Bundle bundle) {
        CategoryFragment categoryFragment = new CategoryFragment();
        categoryFragment.setArguments(bundle);
        return categoryFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getBindingView().buttonReconnect.setOnClickListener(this);
        getBindingView().buttonDarkMode.setOnClickListener(this);
        getBindingView().buttonDarkMode.setIcon(getResources().getDrawable(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES ? R.drawable.ic_light : R.drawable.ic_dark));

        checkConnection();
        setupArticleList();
        subscribeArticleList();
    }

    private void checkConnection() {
        boolean isConnected = NetworkUtil.isNetworkConnected(getContext());
        getBindingView().listArticle.setVisibility(isConnected ? View.VISIBLE : View.GONE);
        getBindingView().buttonDarkMode.setVisibility(isConnected ? View.VISIBLE : View.GONE);
        getBindingView().textNoConnect.setVisibility(isConnected ? View.GONE : View.VISIBLE);
        getBindingView().buttonReconnect.setVisibility(isConnected ? View.GONE : View.VISIBLE);
        if (isConnected)
            getViewModel().getRssNewsList();
        else
            Toast.makeText(getContext(), R.string.notification_no_connect, Toast.LENGTH_SHORT).show();
    }

    private void setupArticleList() {
        mArticleList = new ArrayList<>();
        adapter = new ArticleAdapter(getContext(), mArticleList);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        getBindingView().listArticle.setLayoutManager(layout);
        getBindingView().listArticle.setAdapter(adapter);
    }

    private void subscribeArticleList() {
        LogUtil.d(TAG, "subscribeArticleList");
        getViewModel().getArticleList().observe(getViewLifecycleOwner(), articleList -> {
            if (articleList == null || articleList.isEmpty()) return;
            LogUtil.d(TAG, "list article change = " + articleList.size());
            if (mArticleList == null) mArticleList = new ArrayList<>();
            mArticleList.clear();
            mArticleList.addAll(articleList);
            if (adapter != null)
                adapter.notifyDataSetChanged();
        });
    }

    @Override
    protected String getFragmentName() {
        return CategoryFragment.class.getSimpleName();
    }

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_category;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    protected Class<CategoryViewModel> getViewModelClass() {
        return CategoryViewModel.class;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_dark_mode) {
            int nightMode = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES ?
                    AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES;
            AppCompatDelegate.setDefaultNightMode(nightMode);
            DarkModePreference.getInstance(getContext()).putDarkMode(nightMode);
            getActivity().recreate();
        }
        if (view.getId() == R.id.button_reconnect) {
            checkConnection();
        }
    }
}
