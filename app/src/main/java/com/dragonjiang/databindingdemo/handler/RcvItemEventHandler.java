package com.dragonjiang.databindingdemo.handler;

import android.content.Intent;
import android.view.View;

import com.dragonjiang.databindingdemo.model.RcvItemModel;

/**
 * Created by dragonjiang on 17/4/19.
 */

public class RcvItemEventHandler {
    public void onClickEvent(View view, RcvItemModel model) {
        Intent intent = new Intent(view.getContext(), model.activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        view.getContext().startActivity(intent);
    }
}
