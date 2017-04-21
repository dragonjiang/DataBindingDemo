package com.dragonjiang.databindingdemo.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.dragonjiang.databindingdemo.R;
import com.dragonjiang.databindingdemo.model.RcvItemModel;
import com.dragonjiang.databindingdemo.adapter.RecycleAdapter;
import com.dragonjiang.databindingdemo.databinding.ActivityMainBinding;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
        binding.recycleView.setItemAnimator(new DefaultItemAnimator());
        binding.recycleView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        binding.recycleView.setAdapter(new RecycleAdapter(Arrays.asList(
                new RcvItemModel("01BindingData", BindingDataActivity.class),
                new RcvItemModel("02EventHandling", EventHandlingActivity.class),
                new RcvItemModel("03LayoutDetails", LayoutDetailsActivity.class),
                new RcvItemModel("04ObservableObject", ObservableObjectActivity.class),
                new RcvItemModel("05ObservableField", ObservableFieldActivity.class),
                new RcvItemModel("06ObservableCollection", ObservableCollectionActivity.class),
                new RcvItemModel("07ViewStub", ViewStubActivity.class),
                new RcvItemModel("08Attribute", AttributeActivity.class)
        )));
    }
}
