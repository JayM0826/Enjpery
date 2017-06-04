/*
 * Copyright (c) 2017.
 *       created by J.
 *  不须放屁。待看天地翻覆。
 */

package com.j.enjpery.app.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;


import com.j.enjpery.app.util.AppManager;

import butterknife.ButterKnife;



public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity {

    protected String TAG = getClass().getSimpleName() + ": %s";

    private boolean isNeedRegister = false;

    protected void setNeedRegister() {
        this.isNeedRegister = true;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AppManager.addActivity(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);ButterKnife.bind(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
