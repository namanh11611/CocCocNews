package com.namanh.coccocnews.ui.category;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.namanh.coccocnews.databinding.ItemArticleBinding;
import com.namanh.coccocnews.model.Article;
import com.namanh.coccocnews.ui.common.FragmentFactory;
import com.namanh.coccocnews.ui.common.NavigationController;
import com.namanh.coccocnews.ui.main.ScreenModel;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<Article> mArticleList;

    public ArticleAdapter(Context context, List<Article> articleList) {
        this.mContext = context;
        this.mArticleList = articleList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemArticleBinding mBinding = ItemArticleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ArticleViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ArticleViewHolder)
            ((ArticleViewHolder) holder).onBindView(mArticleList.get(position));
    }

    @Override
    public int getItemCount() {
        return mArticleList == null ? 0 : mArticleList.size();
    }

    private static class ArticleViewHolder extends RecyclerView.ViewHolder implements ArticleViewModel.ArticleListener {

        private ItemArticleBinding mBinding;
        private ArticleViewModel mViewModel;

        public ArticleViewHolder(ItemArticleBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void onBindView(Article article) {
            if (article == null) return;
            mViewModel = new ArticleViewModel(article, this);
            mBinding.setViewModel(mViewModel);
            mBinding.textTitle.setText(article.getTitle());
            mBinding.textDescription.setText(article.getDescription());
            mBinding.textTime.setText(article.getPubDate().substring(0, 25));
            mBinding.executePendingBindings();
        }

        @Override
        public void onClickArticleListener(Article article) {
            Bundle bundle = new Bundle();
            bundle.putString("url", article.getLink());
            ScreenModel screen = ScreenModel.createScreen(FragmentFactory.SCREEN.NEWS, bundle, true, true, true);
            NavigationController.getInstance().setScreen(screen);
        }
    }
}