/*
 * Copyright (c) 2017.
 *       created by J.
 *  不须放屁。待看天地翻覆。
 */

package com.j.enjpery.app.ui;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.j.enjpery.R;
import com.j.enjpery.app.base.BaseActivity;
import com.j.enjpery.app.ui.teaminfo.TeamInfoActivity;
import com.j.enjpery.app.util.AppManager;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class SplashActivity extends AppCompatActivity {


    @BindView(R.id.hello)
    TextView textView;

    // butterknife的方法不能是private和static,并且不能直接使用
    // OnClick，必须先绑定view
    @OnClick(R.id.hello)
    protected void gotoTeamInfo(){
        Intent intent = new Intent(this, TeamInfoActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        Timber.i("哎呦不错哦");
        AppManager.addActivity(this);

        // 测试 SDK 是否正常工作的代码
        AVObject testObject = new AVObject("TestObject");
        testObject.put("words","Hello World!");
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    Snackbar.make(textView, "上传成功", Snackbar.LENGTH_SHORT).show();
                }else {
                    Log.e("Error", "上传图片出现错误");
                }
            }
        });

        AVObject product = new AVObject("Product");
        product.put("title", "马季的测试");
        product.put("image", new AVFile("test.mp4", "http://opqxpwhr.bkt.clouddn.com/%E7%BE%8E%E5%91%A6.mp4",  new HashMap<String, Object>()));
        product.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    Snackbar.make(textView, "图片上传成功", Snackbar.LENGTH_SHORT).show();
                }else{
                    Snackbar.make(textView, "图片上传失败", Snackbar.LENGTH_SHORT).show();
                }
            }
        });



        AVUser user = new AVUser();// 新建 AVUser 对象实例
        user.setUsername("这是一个测试数据的用户名");// 设置用户名
        user.setPassword("cat!@#123");// 设置密码
        user.setEmail("tom@leancloud.cn");// 设置邮箱
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // 注册成功
                    Timber.i("注册成功");
                } else {
                    // 失败的原因可能有多种，常见的是用户名已经存在。
                    Timber.i("注册失败");
                }
            }
        });
    }

}
