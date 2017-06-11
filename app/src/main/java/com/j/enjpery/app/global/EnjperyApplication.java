/*
 * Copyright (c) 2017.
 *       created by J.
 *  不须放屁。待看天地翻覆。
 */

package com.j.enjpery.app.global;

import android.app.Application;
import android.util.Log;

import com.avos.avoscloud.AVOSCloud;
import com.facebook.stetho.Stetho;
import com.j.enjpery.BuildConfig;

import timber.log.Timber;

import static com.j.enjpery.app.util.Constants4Enjpery.LeanCloudAppId;
import static com.j.enjpery.app.util.Constants4Enjpery.LeanCloudAppKey;


/**
 * Created by luoyong on 2017/5/25/0025.
 */

public class EnjperyApplication extends Application {


    public static EnjperyApplication mInstance;

    public static EnjperyApplication getInstance() {
        if (mInstance == null){
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
        AVOSCloud.initialize(this,LeanCloudAppId,LeanCloudAppKey);
        AVOSCloud.setDebugLogEnabled(false);

    }




    private void initLog(){
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

}
