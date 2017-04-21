package com.dragonjiang.databindingdemo.handler;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.dragonjiang.databindingdemo.databinding.ActivityEventHandlingBinding;
import com.dragonjiang.databindingdemo.model.User;

/**
 * Created by dragonjiang on 17/4/19.
 */

public class EventHandler {
    ActivityEventHandlingBinding mBinding;

    public EventHandler(ActivityEventHandlingBinding binding) {
        this.mBinding = binding;
    }

    /**
     * 方法引用
     *
     * @param view
     */
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "MethodHandler, onClick", Toast.LENGTH_SHORT).show();
        mBinding.setUser(new User("bai", "li"));
    }

    /**
     * 事件绑定
     *
     * @param user
     */
    public void onClickEvent(User user) {
        User u = new User("pu", "du");
        mBinding.setUser(u);
        Toast.makeText(mBinding.getRoot().getContext(),
                "old name:" + user.toString() + "; new name:" + u.toString(),
                Toast.LENGTH_SHORT).show();
    }

    /**
     * 事件绑定，带两个参数
     *
     * @param view
     * @param user
     */
    public void onClickEventWithParams(View view, User user) {
        User u = new User("shi", "su");
        mBinding.setUser(u);
        Toast.makeText(view.getContext(),
                "old name:" + user.toString() + "; new name:" + u.toString(),
                Toast.LENGTH_SHORT).show();
    }

    /**
     * 事件绑定
     *
     * @param cb
     * @param isChecked
     */
    public void onCheckBoxChanged(CompoundButton cb, boolean isChecked) {
        Toast.makeText(mBinding.getRoot().getContext(), "isChecked:" + isChecked, Toast.LENGTH_SHORT).show();
    }
}
