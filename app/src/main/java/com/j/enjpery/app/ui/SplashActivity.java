/*
 * Copyright (c) 2017.
 *       created by J.
 *  不须放屁。待看天地翻覆。
 */

package com.j.enjpery.app.ui;

import android.content.Intent;
import android.os.Bundle;

import com.j.enjpery.R;
import com.j.enjpery.app.ui.loginandregister.LoginActivity;
import com.j.enjpery.app.ui.mainactivity.MainActivity;
import com.j.enjpery.app.util.Constants4Enjpery;
import com.j.enjpery.app.util.PreferenceUtil;
import com.j.enjpery.app.util.SystemUiVisibilityUtil;
import com.trello.rxlifecycle2.components.RxActivity;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class SplashActivity extends RxActivity {
    private Unbinder bind;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        bind = ButterKnife.bind(this);
        SystemUiVisibilityUtil.hideStatusBar(getWindow(), true);
        setUpSplash();
    }


    private void setUpSplash() {

        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    finishTask();
                });
    }


    private void finishTask() {

        boolean isLogin = PreferenceUtil.getBoolean(Constants4Enjpery.KEY, false);
        if (isLogin) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }

        SplashActivity.this.finish();
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
        bind.unbind();
    }
}