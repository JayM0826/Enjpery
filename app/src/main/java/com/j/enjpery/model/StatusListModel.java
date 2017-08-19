package com.j.enjpery.model;

import android.content.Context;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by J on 2017/8/6.
 */

public interface StatusListModel {

    interface OnDataFinishedListener {
        void noMoreData();

        void noDataInFirstLoad(String error);

        void getNewWeiBo(int num);

        void onDataFinish(List<AVStatus> statuslist);

        void onError(String error);
    }

    interface OnRequestListener {
        void onSuccess();

        void onError(String error);
    }

    public void timeline(long groundId, Context context, OnDataFinishedListener onDataFinishedListener) ;

    public void friendsTimeline(Context context, OnDataFinishedListener onDataFinishedListener) throws AVException;

    public void bilateralTimeline(Context context, OnDataFinishedListener onDataFinishedListener);

    public void weibo_destroy(long id, Context context, OnRequestListener onRequestListener);

    public void friendsTimelineNextPage(Context context, OnDataFinishedListener onDataFinishedListener);

    public void bilateralTimelineNextPage(Context context, OnDataFinishedListener onDataFinishedListener);

    public void timelineNextPage(long groundId, Context context, OnDataFinishedListener onDataFinishedListener);

    public void setRefrshFriendsTimelineTask();

    public void cancelTimer();

    public boolean cacheLoad(long groupType, Context context, OnDataFinishedListener onDataFinishedListener);

    public void cacheSave(long groupType, Context context, StatusList statusList);


}
