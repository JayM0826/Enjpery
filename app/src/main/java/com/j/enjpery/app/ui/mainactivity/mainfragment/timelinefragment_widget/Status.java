package com.j.enjpery.app.ui.mainactivity.mainfragment.timelinefragment_widget;

import com.avos.avoscloud.AVStatus;

/**
 * Created by J on 2017/7/9.
 */
public class Status extends AVStatus{
    public String getUserHeadImageURL() {
        return userHeadImageURL;
    }

    public void setUserHeadImageURL(String userHeadImageURL) {
        this.userHeadImageURL = userHeadImageURL;
    }

    private String userHeadImageURL;
}