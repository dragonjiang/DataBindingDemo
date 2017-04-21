package com.dragonjiang.databindingdemo.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dragonjiang.databindingdemo.R;
import com.dragonjiang.databindingdemo.databinding.ActivityObservableObjectBinding;
import com.dragonjiang.databindingdemo.model.ObservableUser;

/**
 * Created by dragonjiang on 17/4/21.
 */

public class ObservableObjectActivity extends AppCompatActivity {
    ObservableUser mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityObservableObjectBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_observable_object);
        mUser = new ObservableUser("bai", "li");
        binding.setUser(mUser);
    }

    public void onClick(View view) {
        mUser.setFirstName("pu");
        mUser.setLastName("du");
    }
}
