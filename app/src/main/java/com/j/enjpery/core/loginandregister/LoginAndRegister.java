/*
 * Copyright (c) 2017.
 *       created by J.
 *  不须放屁。待看天地翻覆。
 */

package com.j.enjpery.core.loginandregister;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;
import com.j.enjpery.app.base.BaseActivity;

/**
 * Created by luoyong on 2017/6/4/0004.
 */

public final class LoginAndRegister {
    public static void doLogin(String username, String password, BaseActivity activity){
        AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null) {
                    activity.onSuccessCallBack();
                } else {
                    activity.onFailCallBack(e);
                }
            }
        });
    }

    public static void doRegister(String username, String password, BaseActivity activity){
        AVUser user = new AVUser();// 新建 AVUser 对象实例
        user.setUsername(username);// 设置用户名
        user.setPassword(password);// 设置密码
        user.setEmail(username);//设置邮箱
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // 注册成功，把用户对象赋值给当前用户 AVUser.getCurrentUser()
                    activity.onSuccessCallBack();
                } else {
                    activity.onFailCallBack(e);
                }
            }
        });
    }

    public static void doLogOut(){
        AVUser.logOut();// 清除缓存用户对象
    }
}
