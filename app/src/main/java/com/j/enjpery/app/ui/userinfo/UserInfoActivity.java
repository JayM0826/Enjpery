package com.j.enjpery.app.ui.userinfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.j.enjpery.R;
import com.j.enjpery.app.base.BaseActivity;
import com.j.enjpery.app.ui.mainactivity.eventbus.FragmentVisibleEvent;
import com.j.enjpery.app.ui.mainactivity.eventbus.ShowProgressBarEvent;
import com.j.enjpery.app.ui.teaminfo.TeamInfoActivity;
import com.j.enjpery.app.util.CompressCompletedCB;
import com.j.enjpery.app.util.SnackbarUtil;
import com.j.enjpery.core.loginandregister.LoginAndRegister;
import com.jakewharton.rxbinding2.view.RxView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.netopen.hotbitmapgg.library.view.RingProgressBar;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import top.zibin.luban.Luban;

import static android.view.View.GONE;

public class UserInfoActivity extends BaseActivity {
    private static final int IMAGE_PICKER = 100;

    @BindView(R.id.headImage)
    ImageView headImage;
    @BindView(R.id.headImageLayout)
    RelativeLayout headImageLayout;
    @BindView(R.id.account)
    TextView account;
    @BindView(R.id.accountLayout)
    RelativeLayout accountLayout;
    @BindView(R.id.nickname)
    TextView nickname;
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
    @BindView(R.id.aboutLayout)
    RelativeLayout aboutLayout;
    @BindView(R.id.logoutLayout)
    RelativeLayout logoutLayout;
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_description)
    TextView tvDescription;

    @BindView(R.id.signature)
    TextView signature;

    @BindView(R.id.progress_bar)
    RingProgressBar progressBar;

    private AVFile fileInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNeedRegister();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_info;
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        tvDescription.setText(R.string.userinfo);
        Glide.with(this).load(AVUser.getCurrentUser().get("headImage")).into(headImage);
        signature.setText((String)AVUser.getCurrentUser().get("signature"));
        RxView.clicks(headImageLayout).subscribe(aVoid -> {
            ImagePicker.getInstance().setSelectLimit(1);
            Intent intent = new Intent(this, ImageGridActivity.class);
            ImagePicker.getInstance().setMultiMode(false);
            startActivityForResult(intent, IMAGE_PICKER);
        });

        RxView.clicks(aboutLayout).subscribe(aVoid -> {
            Intent intent = new Intent(this, TeamInfoActivity.class);
            startActivity(intent);
        });

        RxView.clicks(logoutLayout)
                .compose(bindToLifecycle())
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(aVoid -> {
                    LoginAndRegister.doLogOut(getApplicationContext());
                });

        RxView.clicks(btnBack).compose(bindToLifecycle())
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(aVoid -> {
                    onBackPressed();
                });

        progressBar.setVisibility(GONE);
        progressBar.setOnProgressListener(()->{
            SnackbarUtil.show(progressBar, "更新头像完毕");
            Glide.with(UserInfoActivity.this).load(fileInfo.getUrl()).into(headImage);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // 只要返回回来就进行刷新
        if (fileInfo != null){
            // fileInfo.cancel();
            Timber.d("取消上传图片");
        }
        EventBus.getDefault().post(new ShowProgressBarEvent(false));
        EventBus.getDefault().post(new FragmentVisibleEvent(true));
    }

    @OnClick({R.id.accountLayout, R.id.signatureLayout, R.id.sexLayout, R.id.locationLayout})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.accountLayout:
                intent = new Intent(this, EditUserInfoActivity.class);
                intent.putExtra("title", R.string.editID);
                break;
            case R.id.signatureLayout:
                intent = new Intent(this, EditUserInfoActivity.class);
                intent.putExtra("title", R.string.editSignature);
                break;
            case R.id.sexLayout:
                intent = new Intent(this, EditUserInfoActivity.class);
                intent.putExtra("title", R.string.selectSex);
                break;
            case R.id.locationLayout:
                intent = new Intent(this, EditUserInfoActivity.class);
                intent.putExtra("title", R.string.editAddr);
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
                    Timber.i("选择的原始图片路径为：" + images.get(0).path);
                    File imgFile = new File(images.get(0).path);
                    Timber.i("开始压缩");
                    compressWithRx(imgFile, file -> {
                        // 上传图片
                        Timber.i("开始完毕，开始上传头像");
                        // AVUser.getCurrentUser().saveAvatar(file);
                        updateHeadImage(file);
                    });
                }
            }
        }

    }

    private void updateHeadImage(File file) {
        try {
            fileInfo = AVFile.withAbsoluteLocalPath(file.getName(), file.getAbsolutePath());
            AVUser avUser = AVUser.getCurrentUser();
           EventBus.getDefault().post(new ShowProgressBarEvent(true));
            fileInfo.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        String temp = fileInfo.getUrl();
                        avUser.put("headImage", temp);
                        avUser.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null){
                                    EventBus.getDefault().post(new ShowProgressBarEvent(false));
                                    Timber.i("上传成功" + temp);
                                }else {
                                    SnackbarUtil.show(progressBar, "头像上传失败");
                                }
                                EventBus.getDefault().post(new ShowProgressBarEvent(false));
                            }
                        });
                    } else {
                        EventBus.getDefault().post(new ShowProgressBarEvent(false));
                        Timber.e("上传失败");
                        SnackbarUtil.show(headImage, "上传失败");
                    }
                }
            }, new ProgressCallback() {
                @Override
                public void done(Integer integer) {
                    if (progressBar != null){
                        progressBar.setProgress(integer);
                    }
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Timber.e("文件未找到");
        }
    }




    /**
     * 压缩图片，返回压缩后的文件描述符
     *
     * @param file                原图片的文件描述符
     * @param compressCompletedCB 压缩完成后进行的回掉
     */

    private void compressWithRx(File file, final CompressCompletedCB compressCompletedCB) {
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
                        Timber.i("压缩名字" + file.getAbsolutePath());
                        // 上传照片
                        compressCompletedCB.done(file);
                    }
                });
    }





    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ShowProgressBarEvent showProgressBarEvent) {
        if (showProgressBarEvent.isVisible()) {
            if (progressBar.getVisibility() == GONE)
                progressBar.setVisibility(View.VISIBLE);
        } else {
            if (progressBar.getVisibility() == View.VISIBLE)
                progressBar.setVisibility(GONE);
        }
    }


}
