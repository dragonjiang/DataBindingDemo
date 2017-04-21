package com.dragonjiang.databindingdemo.util;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by dragonjiang on 17/4/21.
 */

public class BindingAdapter {

    @android.databinding.BindingAdapter("android:paddingLeft")
    public static void setPaddingLeft(View view, int padding) {
        view.setPadding(padding,
                view.getPaddingTop(),
                view.getPaddingRight(),
                view.getPaddingBottom());
    }

    @android.databinding.BindingAdapter("android:paddingLeft")
    public static void setPaddingLeft(View view, int oldPadding, int padding) {
        if (oldPadding != padding) {
            view.setPadding(padding,
                    view.getPaddingTop(),
                    view.getPaddingRight(),
                    view.getPaddingBottom());
        }
    }

    @android.databinding.BindingAdapter({"imageUrl", "error"})
    public static void loadImage(ImageView view, String url, Drawable error) {
        Glide.with(view.getContext()).load(url).error(error).into(view);
    }
}
