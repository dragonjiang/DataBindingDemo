package com.dragonjiang.databindingdemo.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dragonjiang.databindingdemo.R;
import com.dragonjiang.databindingdemo.databinding.ActivityEventHandlingBinding;
import com.dragonjiang.databindingdemo.handler.EventHandler;
import com.dragonjiang.databindingdemo.model.User;

/**
 * Created by dragonjiang on 17/4/19.
 */

public class EventHandlingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityEventHandlingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_event_handling);

        User user = new User("firstName", "lastName");
        binding.setUser(user);
        binding.setHandler(new EventHandler(binding));
    }

}
