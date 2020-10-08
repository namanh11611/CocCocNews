package com.namanh.coccocnews.ui.category;

import androidx.lifecycle.MutableLiveData;

import com.namanh.coccocnews.model.Article;

public class ArticleViewModel {

    private Article mArticle;
    private ArticleListener mListener;
    
    public MutableLiveData<String> mImageUrl;

    public ArticleViewModel(Article article, ArticleListener listener) {
        this.mArticle = article;
        this.mListener = listener;
        mImageUrl = new MutableLiveData<>();
        mImageUrl.setValue(article.getImageUrl());
    }

    public void onClickArticle() {
        mListener.onClickArticleListener(mArticle);
    }

    public interface ArticleListener {
        void onClickArticleListener(Article article);
    }
}
