package com.j.enjpery.model;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by J on 2017/8/8.
 */

public interface GroupListModel {

    interface OnGroupListFinishedListener {
        void noMoreDate();

        void onDataFinish(ArrayList<Group> groupslist);

        void onError(String error);
    }


    public void groupsOnlyOnce(Context context, OnGroupListFinishedListener onDataFinishedListener);

    public void cacheLoad(Context context, OnGroupListFinishedListener onGroupListFinishedListener);

    public void cacheSave(Context context, String response);


}