/*
 * Copyright (c) 2017.
 *       created by J.
 *  不须放屁。待看天地翻覆。
 */

package com.j.enjpery.app.ui.loginandregister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.j.enjpery.R;
import com.j.enjpery.app.base.BaseActivity;
import com.j.enjpery.app.util.CommonUtil;
import com.j.enjpery.app.util.SnackbarUtil;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.input_email)
    EditText account;
    @BindView(R.id.input_password) 
    EditText passwordText;
    @BindView(R.id.btn_login)
    Button loginButton;
    @BindView(R.id.link_signup)
    TextView signUpLink;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void initToolBar() {

    }
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;



   @OnClick(R.id.btn_login)
   protected void login() {
       Timber.d(TAG, "Login");
       // 先去检测网络
       if (!CommonUtil.isNetworkAvailable(this)){
           SnackbarUtil.show(loginButton, "请先检查网络连接");
           Timber.i(TAG,"无网络");
           return;
       }

       if (!validate()) {
           onLoginFailed();
           return;
       }

       loginButton.setEnabled(false);

       final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
               R.style.AppTheme_Dark_Dialog);
       progressDialog.setIndeterminate(true);
       progressDialog.setMessage("Authenticating...");
       progressDialog.show();


       // TODO: Implement your own authentication logic here.

       new Handler().postDelayed(
               new Runnable() {
                   public void run() {
                       // On complete call either onLoginSuccess or onLoginFailed
                       onLoginSuccess();
                       // onLoginFailed();
                       progressDialog.dismiss();
                   }
               }, 3000);
   }


   @OnClick(R.id.link_signup) protected void gotoSignUp() {
       Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
       startActivityForResult(intent, REQUEST_SIGNUP);
       finish();
       overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
   }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        SnackbarUtil.show(loginButton, "登录失败");
        loginButton.setEnabled(true);
    }

    public boolean validate() {

        boolean valid = true;

        String email = account.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            account.setError("enter a valid email address");
            valid = false;
        } else {
            account.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

}
