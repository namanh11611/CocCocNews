package com.namanh.coccocnews.network;

import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RssClient {

    private static final String BASE_URL = "https://vnexpress.net/rss/";

    private static Retrofit MY_RETROFIT = null;

    public static Retrofit getClient() {
        if (MY_RETROFIT == null) {
            synchronized (RssClient.class) {
                if (MY_RETROFIT == null) {
                    MY_RETROFIT = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(SimpleXmlConverterFactory.create(new Persister(new AnnotationStrategy())))
                            .client(httpClient(createLogging()))
                            .build();
                }
            }
        }
        return MY_RETROFIT;
    }

    private static HttpLoggingInterceptor createLogging() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    private static OkHttpClient httpClient(HttpLoggingInterceptor logging) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        httpClient.addInterceptor(chain -> {
//            Request original = chain.request();
//            Request request = getRequest(original);
//            return chain.proceed(request);
//        });
        httpClient.addInterceptor(logging);
        httpClient.connectTimeout(120, TimeUnit.SECONDS);
        return httpClient.build();
    }

    private static Request getRequest(Request original) {
        return original.newBuilder()
                .method(original.method(), original.body())
                .build();
    }

    public static RssService getService() {
        return getClient().create(RssService.class);
    }

}
