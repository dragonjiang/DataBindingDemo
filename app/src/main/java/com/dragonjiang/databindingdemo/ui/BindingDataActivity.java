package com.dragonjiang.databindingdemo.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dragonjiang.databindingdemo.R;
import com.dragonjiang.databindingdemo.databinding.ActivityBindingDataBinding;
import com.dragonjiang.databindingdemo.model.User;

/**
 * Created by dragonjiang on 17/4/19.
 */

public class BindingDataActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBindingDataBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_binding_data);
        //这种写法也可以
//        ActivityBindingDataBinding binding = ActivityBindingDataBinding.inflate(getLayoutInflater());

        User user = new User("firstName", "lastName");
        binding.setUser(user);
    }
}
