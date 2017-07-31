package com.j.enjpery.app.ui.userinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.j.enjpery.R;
import com.j.enjpery.app.base.BaseActivity;
import com.j.enjpery.app.util.SnackbarUtil;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditUserInfoActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.editInfo)
    TextInputEditText editInfo;
    @BindView(R.id.editInfoLayout)
    TextInputLayout editInfoLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_user_info;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Intent intent = getIntent();
        tvDescription.setText(intent.getStringExtra("title"));

        RxView.clicks(btnBack).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(aVoid -> onBackPressed());

        btnOk.setVisibility(View.VISIBLE);
        RxView.clicks(btnOk).compose(bindToLifecycle())
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(aVoid->{
                    onBackPressed();
                });
    }
}
