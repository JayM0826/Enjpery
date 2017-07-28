/*
 * Copyright (c) 2017.
 *       created by J.
 *  不须放屁。待看天地翻覆。
 */

package com.j.enjpery.app.global;

import android.app.Application;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.avos.avoscloud.AVOSCloud;
import com.facebook.stetho.Stetho;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.j.enjpery.BuildConfig;
import com.j.enjpery.R;
import com.j.enjpery.app.ui.mainactivity.eventbus.NetworkEvent;
import com.j.enjpery.app.util.SnackbarUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.j.enjpery.app.util.Constants4Enjpery.LeanCloudAppId;
import static com.j.enjpery.app.util.Constants4Enjpery.LeanCloudAppKey;


/**
 * Created by luoyong on 2017/5/25/0025.
 */

public class EnjperyApplication extends Application {
    public static Disposable networkDisposable;
    public static NetworkInfo.State state;
    private static final int maxImgCount = 9;

    public static EnjperyApplication mInstance;

    public static EnjperyApplication getInstance() {
        if (mInstance == null) {
            mInstance = new EnjperyApplication();
        }
        return mInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {

        initLog();
        //初始化Leak内存泄露检测工具
        /*if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }*/
        mInstance = this;

        // LeakCanary.install(this);
        //初始化Stetho调试工具
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());

        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this, LeanCloudAppId, LeanCloudAppKey);
        AVOSCloud.setDebugLogEnabled(false);
        initImagePicker();
        initNetworkListener();
    }

    private void initNetworkListener() {
        networkDisposable = ReactiveNetwork.observeNetworkConnectivity(getApplicationContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(connectivity -> {
                    Timber.i("监听网络的变化");
                    state = connectivity.getState();
                    switch (state) {
                        case CONNECTED:
                            Toast.makeText(this, R.string.connected, Toast.LENGTH_SHORT).show();
                            break;
                        case CONNECTING:
                            Toast.makeText(this, R.string.connecting, Toast.LENGTH_SHORT).show();
                            break;
                        case DISCONNECTED:
                            Toast.makeText(this, R.string.disconnected, Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            break;
                    }
                });
    }


    private void initLog() {
        Timber.uprootAll();
        Timber.tag("Enjpery");
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new Timber.Tree() {
                @Override
                protected void log(int priority, String tag, String message, Throwable t) {
                    if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                        return;
                    }
                }
            });
        }
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

}
