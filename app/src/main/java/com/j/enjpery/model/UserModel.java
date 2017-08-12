package com.j.enjpery.model;

import android.content.Context;

import com.avos.avoscloud.AVStatus;
import com.avos.avoscloud.AVUser;

import java.util.ArrayList;

/**
 * Created by J on 2017/8/8.
 */

public interface UserModel {

    interface OnUserDetailRequestFinish {
        void onComplete(AVUser user);

        void onError(String error);
    }


    interface OnUserListRequestFinish {
        void noMoreDate();

        void onDataFinish(ArrayList<AVUser> userlist);

        void onError(String error);
    }

    interface OnStatusListFinishedListener {
        void noMoreDate();

        void onDataFinish(ArrayList<AVStatus> statuslist);

        void onError(String error);
    }

    interface OnUserDeleteListener {
        void onSuccess(ArrayList<AVUser> userlist);

        void onEmpty();

        void onError(String error);
    }


    public void show(long uid, Context context, OnUserDetailRequestFinish onUserDetailRequestFinish);

    public void show(String screenName, Context context, OnUserDetailRequestFinish onUserDetailRequestFinish);

    public AVUser showUserDetailSync(long uid, Context context);

    public void userTimeline(long uid, int groupId, Context context, OnStatusListFinishedListener onStatusFinishedListener);

    public void userTimeline(String screenName, int groupId, Context context, OnStatusListFinishedListener onStatusFinishedListener);

    public void userPhoto(String screenName, int groupId, Context context, OnStatusListFinishedListener onStatusFinishedListener);

    public void userTimelineNextPage(long uid, int groupId, Context context, OnStatusListFinishedListener onStatusFinishedListener);

    public void userTimelineNextPage(String screenName, int groupId, Context context, OnStatusListFinishedListener onStatusFinishedListener);

    public void userPhotoNextPage(String screenName, int groupId, Context context, OnStatusListFinishedListener onStatusFinishedListener);

    public void followers(long uid, Context context, OnUserListRequestFinish onUserListRequestFinish);

    public void followersNextPage(long uid, Context context, OnUserListRequestFinish onUserListRequestFinish);

    public void friends(long uid, Context context, OnUserListRequestFinish onUserListRequestFinish);

    public void friendsNextPage(long uid, Context context, OnUserListRequestFinish onUserListRequestFinish);

    public void getUserDetailList(Context context, OnUserListRequestFinish onUserListRequestFinish);

    public void deleteUserByUid(long uid, Context context, OnUserDeleteListener onUserDeleteListener);

    public void cacheSave_statuslist(int groupType, Context context, String response);

    public void cacheLoad_statuslist(int groupType, Context context, OnStatusListFinishedListener onStatusListFinishedListener);

    public void cacheSave_user(Context context, String response);

    public void cacheLoad_user(Context context, OnUserDetailRequestFinish onUserDetailRequestFinish);

    public void cacheSave_userlist(int groupType, Context context, String response);

    public void cacheLoad_userlist(int groupType, Context context, OnUserListRequestFinish onUserListRequestFinish);

}

