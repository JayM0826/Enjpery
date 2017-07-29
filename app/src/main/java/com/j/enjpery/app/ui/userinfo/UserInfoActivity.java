package com.j.enjpery.app.ui.userinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.j.enjpery.R;
import com.j.enjpery.app.base.BaseActivity;
import com.j.enjpery.app.ui.mainactivity.MainActivity;
import com.j.enjpery.app.util.SnackbarUtil;
import com.jakewharton.rxbinding2.view.RxView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import top.zibin.luban.Luban;

public class UserInfoActivity extends BaseActivity {
    private static final int IMAGE_PICKER = 100;
    private String editInfoTitle;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.headImage)
    ImageView headImage;
    @BindView(R.id.headImageLayout)
    RelativeLayout headImageLayout;
    @BindView(R.id.account)
    TextView account;
    @BindView(R.id.accountLayout)
    RelativeLayout accountLayout;
    @BindView(R.id.nickname1)
    TextView nickname1;
    @BindView(R.id.signatureLayout)
    RelativeLayout signatureLayout;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.sexLayout)
    RelativeLayout sexLayout;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.locationLayout)
    RelativeLayout locationLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Glide.with(this).load(((AVFile) AVUser.getCurrentUser().get("headImage")).getUrl()).into(headImage);

        RxView.clicks(headImageLayout).subscribe(aVoid -> {
            ImagePicker.getInstance().setSelectLimit(1);
            Intent intent = new Intent(this, ImageGridActivity.class);
            startActivityForResult(intent, IMAGE_PICKER);
        });
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick({R.id.accountLayout, R.id.signatureLayout, R.id.sexLayout, R.id.locationLayout})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.accountLayout:
                intent = new Intent(this, EditUserInfoActivity.class);
                intent.putExtra("title", "更改账号");
                break;
            case R.id.signatureLayout:
                intent = new Intent(this, EditUserInfoActivity.class);
                intent.putExtra("title", "更改签名");
                break;
            case R.id.sexLayout:
                intent = new Intent(this, EditUserInfoActivity.class);
                intent.putExtra("title", "选择性别");
                break;
            case R.id.locationLayout:
                intent = new Intent(this, EditUserInfoActivity.class);
                intent.putExtra("title", "更改地址");
                break;
        }
        startActivity(intent);
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
                    File imgFile = new File(images.get(0).path);
                    compressWithRx(imgFile);
                }
            }
        }

    }


    private void compressWithRx(File file) {
        Flowable.just(file)
                .observeOn(Schedulers.io())
                .map(new Function<File, File>() {
                    @Override
                    public File apply(@NonNull File file) throws Exception {
                        return Luban.with(UserInfoActivity.this).load(file).get();
                    }
                })
                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(@NonNull File file) throws Exception {
                        Timber.i("压缩", file.getAbsolutePath());
                        uploadHeadImage(file);
                    }
                });
    }

    /**
     * 更新用户头像
     * @param file 图片压缩后的文件描述符
     */
    private void uploadHeadImage(File file) {
        try {
            AVFile fileInfo = AVFile.withAbsoluteLocalPath(file.getName(), file.getAbsolutePath());
            AVUser avUser = AVUser.getCurrentUser();
            avUser.put("headImage", fileInfo);
            avUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        AVFile avFile = (AVFile) avUser.get("headImage");
                        Timber.i("上传成功" + avFile.getUrl());
                        SnackbarUtil.show(headImage, "上传成功");
                        Glide.with(UserInfoActivity.this).load(avFile.getUrl()).into(headImage);
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
