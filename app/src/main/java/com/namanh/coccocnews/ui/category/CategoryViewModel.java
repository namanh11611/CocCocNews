package com.namanh.coccocnews.ui.category;

import androidx.lifecycle.MutableLiveData;

import com.namanh.coccocnews.application.AppExecutors;
import com.namanh.coccocnews.model.Article;
import com.namanh.coccocnews.model.RssFeed;
import com.namanh.coccocnews.network.RssClient;
import com.namanh.coccocnews.ui.common.BaseViewModel;
import com.namanh.coccocnews.utils.LogUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryViewModel extends BaseViewModel {

    private static final String TAG = "CategoryViewModel";

    private MutableLiveData<List<Article>> mArticleList;

    public CategoryViewModel(AppExecutors appExecutors) {
        super(appExecutors);

        mArticleList = new MutableLiveData<>();
    }

    public MutableLiveData<List<Article>> getArticleList() {
        return mArticleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.mArticleList.postValue(articleList);
    }

    public void getRssNewsList() {
        LogUtil.i(TAG, "getRssNewsList");
        RssClient.getService().getRssNews().enqueue(new Callback<RssFeed>() {
            @Override
            public void onResponse(Call<RssFeed> call, Response<RssFeed> response) {
                LogUtil.d(TAG, "onResponse " + response);
                if (response.isSuccessful()) {
                    RssFeed rssFeed = response.body();
                    setArticleList(rssFeed.getChannel().getItemList());
                } else {
                    LogUtil.e(TAG, "Request error");
                }
            }

            @Override
            public void onFailure(Call<RssFeed> call, Throwable t) {
                LogUtil.e(TAG, "Network error " + t.getMessage());
            }
        });
    }

}
