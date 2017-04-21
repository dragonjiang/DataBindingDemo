package com.dragonjiang.databindingdemo.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dragonjiang.databindingdemo.R;
import com.dragonjiang.databindingdemo.databinding.ActivityAttributeBinding;
import com.dragonjiang.databindingdemo.databinding.ActivityBindingDataBinding;
import com.dragonjiang.databindingdemo.model.User;

/**
 * Created by dragonjiang on 17/4/19.
 */

public class AttributeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAttributeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_attribute);

        User user = new User("bai", "li");
        user.setAvatar("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fwww.vilogo.com%2Fwp-content%2Fuploads%2F64965070201304181125484061603230163_004.jpg&thumburl=http%3A%2F%2Fimg1.imgtn.bdimg.com%2Fit%2Fu%3D204622881%2C423822406%26fm%3D23%26gp%3D0.jpg");
        binding.setUser(user);
    }
}
