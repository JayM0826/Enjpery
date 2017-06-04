/*
 * Copyright (c) 2017.
 *       created by J.
 *  不须放屁。待看天地翻覆。
 */

package com.j.enjpery.app.ui.loginandregister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.j.enjpery.R;
import com.j.enjpery.app.base.BaseActivity;
import com.j.enjpery.app.util.CommonUtil;
import com.j.enjpery.app.util.SnackbarUtil;
import com.j.enjpery.app.util.StringUtils;
import com.j.enjpery.core.loginandregister.LoginAndRegister;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class SignUpActivity extends BaseActivity {
    @BindView(R.id.verify_number)
    EditText verifyNumber;
    @BindView(R.id.btn_get_verify_number)
    AppCompatButton btnGetVerifyNumber;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        if (!CommonUtil.isNetworkAvailable(this)) {
            SnackbarUtil.show(loginLink, "请检查网络连接");
            Timber.i(TAG, "无网络");
        }
    }

    @Override
    public void initToolBar() {

    }

    private static final String TAG = "SignupActivity";


    @BindView(R.id.input_mobile)
    EditText mobileText;

    @BindView(R.id.btn_signup)
    Button signupButton;
    @BindView(R.id.link_login)
    TextView loginLink;

    @OnClick(R.id.btn_signup)
    protected void doSignUp() {
        signup();
    }

    @OnClick(R.id.link_login)
    protected void gotoLogin() {
        // Finish the registration screen and return to the Login activity
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }


    public void signup() {
        Timber.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();
        // TODO: Implement your own signup logic here.
        AVUser user = new AVUser();
        user.put("mobilePhoneNumber", mobileText);
        user.signUpInBackground(new SignUpCallback() {
            public void done(AVException e) {
                progressDialog.dismiss();
                if (e == null) {
                    onSignupSuccess();
                } else {
                    onSignupFailed();
                }
            }
        });
    }


    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        SnackbarUtil.show(loginLink, "注册失败,请重试");
        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        String mobile = mobileText.getText().toString();

        if (StringUtils.isBlank(mobile) || StringUtils.length(mobile) != 11) {
            mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            mobileText.setError(null);
        }
        return valid;
    }

    @OnClick(R.id.btn_get_verify_number)
    public void onViewClicked() {
    }
}
