package com.namanh.coccocnews.network;

import com.namanh.coccocnews.model.RssFeed;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RssService {

    @GET("tin-moi-nhat.rss")
    Call<RssFeed> getRssNews();

}
