package com.dragonjiang.databindingdemo.ui;

import android.content.Context;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dragonjiang.databindingdemo.model.User;

/**
 * Created by dragonjiang on 17/4/21.
 */

public class UserView extends AppCompatTextView {
    public UserView(Context context) {
        super(context);
        init(context);
    }

    public UserView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public UserView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
    }

    public void setUser(User user) {
        this.setText(user.toString());
    }


}
