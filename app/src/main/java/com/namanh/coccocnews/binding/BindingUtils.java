package com.namanh.coccocnews.binding;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.namanh.coccocnews.R;
import com.namanh.coccocnews.helper.GlideHelper;

public class BindingUtils {

    @BindingAdapter({"imageRoundRadiusUrl"})
    public static void loadRoundRadiusImage(ImageView view, String urlImage) {
        GlideHelper.loadRoundRadiusImage(view.getContext(), view, urlImage, view.getContext().getResources().getDimensionPixelSize(R.dimen.article_image_radius));
    }

}
