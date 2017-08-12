package com.j.enjpery.app.ui.userinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.j.enjpery.R;
import com.j.enjpery.app.base.BaseActivity;
import com.j.enjpery.app.util.SnackbarUtil;
import com.jakewharton.rxbinding2.view.RxView;

import org.greenrobot.eventbus.EventBus;

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
        int title = intent.getIntExtra("title", 0);
        tvDescription.setText(getResources().getString(title));

        switch (title){
            case R.string.editID:
                editInfoLayout.setHint("换个ID，换个心情");
                break;
            case R.string.editSignature:
                editInfoLayout.setHint("新的签名，新的感悟");
                break;
            case R.string.editAddr:
                editInfoLayout.setHint("新的方位，新的视角，新的生活");
                break;
            case R.string.selectSex:
                editInfoLayout.setHint("选择请慎重");
                break;
                default:break;
        }

        RxView.clicks(btnBack).throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(aVoid -> onBackPressed());

        RxView.clicks(btnOk).compose(bindToLifecycle())
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(aVoid->{
                    String string = editInfo.getText().toString();
                    switch (title){
                        case R.string.editID:
                            AVUser.getCurrentUser().put("account", string);
                            break;
                        case R.string.editSignature:
                            AVUser.getCurrentUser().put("signature", string);
                            break;
                        case R.string.editAddr:
                            AVUser.getCurrentUser().put("address", string);
                            break;
                        case R.string.selectSex:
                            AVUser.getCurrentUser().put("gender", string);
                            break;
                        default:break;
                    }
                    AVUser.getCurrentUser().saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null){
                                EventBus.getDefault().post(new EditUserInfoEvent(true));
                            }else {
                                EventBus.getDefault().post(new EditUserInfoEvent(false));
                            }
                        }
                    });
                    onBackPressed();
                });
        editInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                btnOk.setVisibility(View.VISIBLE);
                btnOk.setEnabled(true);
            }
        });

    }
}
