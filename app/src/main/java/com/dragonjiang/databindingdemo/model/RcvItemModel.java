package com.dragonjiang.databindingdemo.model;

/**
 * Created by dragonjiang on 17/4/19.
 */

public class RcvItemModel {
    public String name;
    public Class activityClass;

    public RcvItemModel(String name, Class activityClass) {
        this.name = name;
        this.activityClass = activityClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getActivityClass() {
        return activityClass;
    }

    public void setActivityClass(Class activityClass) {
        this.activityClass = activityClass;
    }
}
