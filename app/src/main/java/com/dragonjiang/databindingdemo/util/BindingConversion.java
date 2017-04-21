package com.dragonjiang.databindingdemo.util;

import android.graphics.drawable.ColorDrawable;

/**
 * Created by dragonjiang on 17/4/21.
 */

public class BindingConversion {
    @android.databinding.BindingConversion
    public static ColorDrawable convertColorToDrawable(int color) {
        return new ColorDrawable(color);
    }
}
