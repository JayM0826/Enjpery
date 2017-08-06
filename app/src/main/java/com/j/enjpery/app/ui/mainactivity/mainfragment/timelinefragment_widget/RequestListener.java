package com.j.enjpery.app.ui.mainactivity.mainfragment.timelinefragment_widget;

import com.avos.avoscloud.AVException;

/**
 * Created by J on 2017/8/6.
 */

public interface RequestListener {
    void onComplete(String var1);

    void onWeiboException(AVException var1);
}