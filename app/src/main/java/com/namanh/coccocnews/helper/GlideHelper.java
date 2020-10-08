package com.namanh.coccocnews.helper;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.namanh.coccocnews.utils.GlideApp;

public class GlideHelper {

    public static void loadRoundRadiusImage(Context context, ImageView view, String urlImage, int radius) {
        if (context == null || view == null || urlImage == null || urlImage.trim().isEmpty())
            return;
        GlideApp.with(context)
                .load(urlImage)
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(radius)))
                .into(view);
    }

}
