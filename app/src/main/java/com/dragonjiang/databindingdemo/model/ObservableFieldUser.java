package com.dragonjiang.databindingdemo.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;

import com.android.databinding.library.baseAdapters.BR;

/**
 * Created by dragonjiang on 17/4/19.
 */

public class ObservableFieldUser extends BaseObservable {
    public final ObservableField<String> firstName = new ObservableField<>();
    public final ObservableField<String> lastName = new ObservableField<>();

    public ObservableFieldUser(String firstName, String lastName) {
        this.firstName.set(firstName);
        this.lastName.set(lastName);
    }
}
