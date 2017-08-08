package com.j.enjpery.app.ui.mainactivity.mainfragment.timelinefragment_widget;

import android.content.Context;

/**
 * Created by J on 2017/8/8.
 */

public interface HomeFragmentPresent {

    public void refreshUserName(Context context);

    public void firstLoadData(Context context, boolean firstStart);

    public void pullToRefreshData(long groupId, Context context);

    public void requestMoreData(long groupId, Context context);

    public void cancelTimer();

}