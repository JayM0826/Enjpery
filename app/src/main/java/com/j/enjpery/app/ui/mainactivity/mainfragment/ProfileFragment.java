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
import com.j.enjpery.model.User;
import com.jakewharton.rxbinding2.view.RxView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewActivity;

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
    private static final int IMAGE_PICKER = 100;
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

        RxView.clicks(headImage).subscribe(aVoid -> {
            ImagePicker.getInstance().setSelectLimit(1);
            Intent intent = new Intent(getActivity(), ImageGridActivity.class);
            startActivityForResult(intent, IMAGE_PICKER);
        });

        RxView.clicks(viewUserLayout).subscribe(aVoid -> {
            Intent intent = new Intent(getActivity(), UserInfoActivity.class);
            startActivity(intent);
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == IMAGE_PICKER) {
                List<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    Timber.i(images.get(0).path);
                    try {
                        AVFile file = AVFile.withAbsoluteLocalPath(images.get(0).name, images.get(0).path);
                        AVUser avUser = AVUser.getCurrentUser();
                        avUser.put("headImage", file);
                        avUser.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    AVFile avFile = (AVFile) avUser.get("headImage");
                                    Timber.i("上传成功" + avFile.getUrl());
                                    Glide.with(getActivity()).load(avFile.getUrl()).into(headImage);

                                } else {
                                    Timber.e("上传失败");
                                  SnackbarUtil.show(headImage, "上传失败");
                                }
                            }

                        });
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Timber.e("文件未找到");
                    }
                }
            }
        }

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
