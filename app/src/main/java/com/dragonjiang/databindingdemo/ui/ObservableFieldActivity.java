package com.dragonjiang.databindingdemo.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dragonjiang.databindingdemo.R;
import com.dragonjiang.databindingdemo.databinding.ActivityObservableFieldBinding;
import com.dragonjiang.databindingdemo.databinding.ActivityObservableObjectBinding;
import com.dragonjiang.databindingdemo.model.ObservableFieldUser;
import com.dragonjiang.databindingdemo.model.ObservableUser;

/**
 * Created by dragonjiang on 17/4/21.
 */

public class ObservableFieldActivity extends AppCompatActivity {
    private ObservableFieldUser mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityObservableFieldBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_observable_field);
        mUser = new ObservableFieldUser("bai", "li");
        binding.setUser(mUser);
    }

    public void onClick(View view) {
        mUser.firstName.set("pu");
        mUser.lastName.set("du");
    }
}
