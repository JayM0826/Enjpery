package com.j.enjpery.app.ui.mainactivity.mainfragment.timelinefragment_widget;

import android.content.Context;

import com.avos.avoscloud.AVException;

/**
 * Created by J on 2017/8/8.
 */

public interface HomeFragmentPresent {


    public void firstLoadData(Context context, boolean firstStart) throws AVException;

    public void pullToRefreshData(long groupId, Context context) throws AVException;

    public void requestMoreData(long groupId, Context context);

    public void cancelTimer();

}