/*
 * Copyright (c) 2017.
 *       created by J.
 *  不须放屁。待看天地翻覆。
 */

package com.j.enjpery.app.base;

import android.os.Bundle;

import com.j.enjpery.app.util.AppManager;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity extends RxAppCompatActivity {
    private Unbinder bind;
    public  BaseActivity instance;
    private boolean isNeedRegister = false;

    protected void setNeedRegister() {
        this.isNeedRegister = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置布局内容
        setContentView(getLayoutId());
        //初始化黄油刀控件绑定框架
        bind = ButterKnife.bind(this);
        //初始化控件
        initViews(savedInstanceState);
        //初始化ToolBar
        initToolBar();
        AppManager.addActivity(this);

    }


    public abstract int getLayoutId();

    public abstract void initViews(Bundle savedInstanceState);

    public abstract void initToolBar();


    // 回掉函数
    public void onSuccessCallBack(){};
    // 回掉函数
    public void onFailCallBack(Exception e){};

    public void loadData() {}


    public void showProgressDialog() {
    }


    public void hideProgressDialog() {

    }


    public void initRecyclerView() {}


    public void initRefreshLayout() {}


    public void finishTask() {}

    @Override
    public void onStart() {
        super.onStart();
        // EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        // EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        bind.unbind();
    }
}
