package com.dragonjiang.databindingdemo.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.dragonjiang.databindingdemo.R;
import com.dragonjiang.databindingdemo.databinding.ActivityLayoutDetailsBinding;
import com.dragonjiang.databindingdemo.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dragonjiang on 17/4/21.
 */

public class LayoutDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityLayoutDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_layout_details);

        binding.setUser(new User("bai", "li"));


        List<User> userList = new ArrayList<>();
        userList.add(new User("bai", "li"));
        userList.add(new User("pu", "du"));
        binding.setUserList(userList);

        SparseArray<String> sa = new SparseArray<>();
        sa.put(0, "hello");
        sa.put(1, "world");
        binding.setSparse(sa);

        Map<String, String> map = new HashMap<>();
        map.put("key1", "hello");
        map.put("key2", "world");
        binding.setMap(map);

        binding.setIndex(1);
        binding.setKey("key1");
        binding.setLarge(true);

        binding.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LayoutDetailsActivity.this, binding.tvName.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
