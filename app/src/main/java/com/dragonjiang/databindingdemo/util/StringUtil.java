package com.dragonjiang.databindingdemo.util;

import android.text.TextUtils;

/**
 * Created by dragonjiang on 17/4/21.
 */

public class StringUtil {

    public static final String capitalize(String input) {
        if (TextUtils.isEmpty(input)) {
            return "";
        }

        return String.valueOf(input.charAt(0)).toUpperCase() + input.substring(1);
    }
}
