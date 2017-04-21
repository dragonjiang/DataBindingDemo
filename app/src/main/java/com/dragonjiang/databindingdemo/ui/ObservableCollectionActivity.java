package com.dragonjiang.databindingdemo.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableArrayMap;
import android.databinding.ObservableList;
import android.databinding.ObservableMap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dragonjiang.databindingdemo.R;
import com.dragonjiang.databindingdemo.databinding.ActivityObservableCollectionBinding;
import com.dragonjiang.databindingdemo.databinding.ActivityObservableFieldBinding;
import com.dragonjiang.databindingdemo.model.ObservableFieldUser;
import com.dragonjiang.databindingdemo.model.User;

/**
 * Created by dragonjiang on 17/4/21.
 */

public class ObservableCollectionActivity extends AppCompatActivity {
    private ObservableMap<String, String> mUserMap;
    private ObservableList<User> mUserList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityObservableCollectionBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_observable_collection);

        mUserMap = new ObservableArrayMap<>();
        mUserMap.put("firstName", "bai");
        mUserMap.put("lastName", "li");

        mUserList = new ObservableArrayList<>();
        mUserList.add(new User("bai", "li"));

        binding.setUserList(mUserList);
        binding.setUserMap(mUserMap);
    }

    public void onClick(View view) {
        mUserMap.put("firstName", "pu");
        mUserMap.put("lastName", "du");

        mUserList.clear();
        mUserList.add(new User("pu", "du"));
    }
}
