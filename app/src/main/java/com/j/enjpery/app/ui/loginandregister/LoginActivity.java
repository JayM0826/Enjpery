/*
 * Copyright (c) 2017.
 *       created by J.
 *  不须放屁。待看天地翻覆。
 */

package com.j.enjpery.app.ui.loginandregister;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.j.enjpery.R;
import com.j.enjpery.app.base.BaseActivity;
import com.j.enjpery.app.global.EnjperyApplication;
import com.j.enjpery.app.ui.mainactivity.MainActivity;
import com.j.enjpery.app.util.CircularAnim;
import com.j.enjpery.app.util.CommonUtil;
import com.j.enjpery.app.util.SnackbarUtil;
import com.j.enjpery.core.loginandregister.LoginAndRegister;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

public class LoginActivity extends BaseActivity {
    private String email;
    private String password;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.bt_go)
    Button btGo;
    @BindView(R.id.cv)
    CardView cv;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        instance = this;
        if (EnjperyApplication.state != NetworkInfo.State.CONNECTED) {
            SnackbarUtil.show(btGo, "请先检查网络连接");
            Timber.i("无网络");
        }

        RxView.clicks(btGo)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(aVoid -> {
                    if (!validate()) {
                        Exception e = new Exception("用户名或者密码不符合规范");
                        onFailCallBack(e);
                        return;
                    }
                    // 进行真正的登录请求
                    showProgressDialog(R.string.login_dialog);
                    LoginAndRegister.doLogin(email, password, instance);
                });
        RxView.clicks(fab)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(aVoid -> {
                    // Activity之间的切换没有任何动画
                    getWindow().setExitTransition(null);
                    getWindow().setEnterTransition(null);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptions options =
                                ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
                        startActivity(new Intent(this, SignUpActivity.class), options.toBundle());
                    } else {
                        startActivity(new Intent(this, SignUpActivity.class));
                    }
                });
    }


    @Override
    public void onSuccessCallBack() {
        super.onSuccessCallBack();
        Explode explode = new Explode();
        explode.setDuration(500);

        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);
        ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        Intent i2 = new Intent(this, MainActivity.class);
        startActivity(i2, oc2.toBundle());
        finish();
        // overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onFailCallBack(Exception e) {
        super.onFailCallBack(e);
        SnackbarUtil.show(etPassword, "登录失败,请重新登录");
        btGo.setEnabled(true);
    }


    public boolean validate() {

        boolean valid = true;

        email = etUsername.getText().toString();
        password = etPassword.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etUsername.setError("输入的邮箱无效");
            valid = false;
        } else {
            etUsername.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 16) {
            etPassword.setError("密码长度为5-16");
            valid = false;
        } else {
            etPassword.setError(null);
        }

        return valid;
    }

}
