package com.namanh.coccocnews.ui.news;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

import com.namanh.coccocnews.BR;
import com.namanh.coccocnews.R;
import com.namanh.coccocnews.databinding.FragmentNewsBinding;
import com.namanh.coccocnews.ui.common.BaseFragment;

public class NewsFragment extends BaseFragment<FragmentNewsBinding, NewsViewModel> {

    private static final String TAG = "NewsFragment";

    private WebView mWebView;
    private WebSettings mWebSettings;
    private String mUrl;

    public static NewsFragment create(Bundle bundle) {
        NewsFragment newsFragment = new NewsFragment();
        newsFragment.setArguments(bundle);
        return newsFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mWebView = getBindingView().webView;

        loadNews();

        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK) && AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            WebSettingsCompat.setForceDark(mWebView.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
        }
    }

    private void loadNews() {
        mUrl = getArguments().getString("url");
        mWebView.loadUrl(mUrl);
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
    }

    @Override
    protected String getFragmentName() {
        return NewsFragment.class.getSimpleName();
    }

    @Override
    protected int getIdLayout() {
        return R.layout.fragment_news;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    protected Class<NewsViewModel> getViewModelClass() {
        return NewsViewModel.class;
    }
}
