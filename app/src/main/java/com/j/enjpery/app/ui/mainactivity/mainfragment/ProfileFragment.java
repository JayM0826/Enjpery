package com.j.enjpery.app.ui.mainactivity.mainfragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.j.enjpery.R;
import com.j.enjpery.app.base.BaseFragment;
import com.j.enjpery.app.ui.userinfo.UserInfoActivity;
import com.j.enjpery.app.util.SnackbarUtil;
import com.jakewharton.rxbinding2.view.RxView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment {

    @BindView(R.id.head)
    ImageView headImage;

    @BindView(R.id.view_user)
    RelativeLayout viewUserLayout;

    Unbinder unbinder;

    @Override
    public void fetchData() {

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

        Timber.i("初始化头像地址" + ((AVFile)AVUser.getCurrentUser().get("headImage")).getUrl());
        Glide.with(getActivity()).load(((AVFile)AVUser.getCurrentUser().get("headImage")).getUrl()).into(headImage);

        RxView.clicks(viewUserLayout).subscribe(aVoid -> {
            Intent intent = new Intent(getActivity(), UserInfoActivity.class);
            startActivity(intent);
        });
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
