package com.j.enjpery.model;

/**
 * Created by J on 2017/8/6.
 */

import android.content.Context;

import com.avos.avoscloud.AVStatus;

import java.util.ArrayList;
public interface StatusDetailModel {

    interface OnCommentCallBack {
        void noMoreDate();

        void onDataFinish(ArrayList<Comment> commentlist);

        void onError(String error);
    }


    interface OnRepostCallBack {
        void noMoreDate();

        void onDataFinish(ArrayList<AVStatus> commentlist);

        void onError(String error);
    }

    public void comment(int groupType, AVStatus status, Context context, OnCommentCallBack onCommentCallBack);

    public void commentNextPage(int groupType, AVStatus status, Context context, OnCommentCallBack onCommentCallBack);


    public void repost(int groupType, AVStatus status, Context context, OnRepostCallBack onRepostCallBack);

    public void repostNextPage(int groupType, AVStatus status, Context context, OnRepostCallBack onRepostCallBack);

}