package com.jaumard.owt.viewmodels.adapters;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jaumard.owt.BuildConfig;
import com.jaumard.owt.R;

public final class ImageViewBindingAdapter {

    private ImageViewBindingAdapter() {
    }

    @BindingAdapter("android:src")
    public static void setImage(ImageView imageView, String source) {
        Glide.with(imageView.getContext()).load(BuildConfig.BASE_IMAGE_URL + source)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.ic_error_outline_24dp)
                .into(imageView);
    }

}
