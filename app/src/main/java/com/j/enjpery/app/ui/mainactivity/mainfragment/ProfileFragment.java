package com.j.enjpery.app.ui.mainactivity.mainfragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.j.enjpery.R;
import com.j.enjpery.app.base.BaseFragment;
import com.j.enjpery.app.ui.userinfo.UserInfoActivity;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment {

    @BindView(R.id.head)
    ImageView headImage;

    @BindView(R.id.userLayout)
    RelativeLayout userLayout;

    @Override
    public void fetchData() {

    }

    @Override
    public void onStart() {
        super.onStart();
        // Glide默认使用内存缓存和磁盘缓存
        Glide.with(getActivity()).load(((AVFile)AVUser.getCurrentUser().get("headImage")).getUrl()).into(headImage);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.wxfragment_profile;
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void initCreateView(Bundle state) {
        RxView.clicks(userLayout)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(aVoid -> {
            Intent intent = new Intent(getActivity(), UserInfoActivity.class);
            startActivity(intent);
        });
    }

}
