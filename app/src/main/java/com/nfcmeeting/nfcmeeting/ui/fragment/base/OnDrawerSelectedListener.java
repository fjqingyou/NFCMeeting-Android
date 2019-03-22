package com.nfcmeeting.nfcmeeting.ui.fragment.base;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;



public interface OnDrawerSelectedListener {

    void onDrawerSelected(@NonNull NavigationView navView, @NonNull MenuItem item);

}
