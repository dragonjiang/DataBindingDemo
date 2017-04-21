package com.dragonjiang.databindingdemo.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;

import com.dragonjiang.databindingdemo.R;
import com.dragonjiang.databindingdemo.databinding.ActivityBindingDataBinding;
import com.dragonjiang.databindingdemo.databinding.ActivityViewStubBinding;
import com.dragonjiang.databindingdemo.databinding.IncludeBinding;
import com.dragonjiang.databindingdemo.model.User;

/**
 * Created by dragonjiang on 17/4/19.
 */

public class ViewStubActivity extends AppCompatActivity {

    private ActivityViewStubBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_view_stub);
        mBinding.viewStub.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub stub, View inflated) {
                IncludeBinding binding = DataBindingUtil.bind(inflated);
                binding.setUser(new User("bai", "li"));
            }
        });
    }

    public void onClick(View view) {
        if (!mBinding.viewStub.isInflated()) {
            mBinding.viewStub.getViewStub().inflate();
        }
    }
}
