/*
 * Copyright (c) 2017.
 *       created by J.
 *  不须放屁。待看天地翻覆。
 */

package com.j.enjpery.app.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Window;

import com.j.enjpery.R;
import com.j.enjpery.app.util.AppManager;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity extends RxAppCompatActivity {
    private Unbinder bind;
    public  BaseActivity instance;
    private boolean isNeedRegister = false;
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        //设置布局内容
        setContentView(getLayoutId());
        //初始化黄油刀控件绑定框架
        bind = ButterKnife.bind(this);
        //初始化控件
        initViews(savedInstanceState);
        //初始化TopBar
        initTopBar();
        AppManager.addActivity(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (isNeedRegister){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (isNeedRegister){
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        bind.unbind();

    }

    protected void setNeedRegister() {
        this.isNeedRegister = true;
    }


    public abstract int getLayoutId();

    public abstract void initViews(Bundle savedInstanceState);

    public void initTopBar(){};


    // 回掉函数
    public void onSuccessCallBack(){
        hideProgressDialog();
    };
    // 回掉函数
    public void onFailCallBack(Exception e){
        hideProgressDialog();
    };

    public void loadData() {}


    public void showProgressDialog(String message) {
        if (progressDialog == null){
            progressDialog = new ProgressDialog(this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(message);
        }

        progressDialog.show();
    }

    public void showProgressDialog(int id) {
       showProgressDialog(getResources().getString(id));
    }


    public void hideProgressDialog() {
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }


    public void initRecyclerView() {}


    public void initRefreshLayout() {}


    public void finishTask() {}

}
