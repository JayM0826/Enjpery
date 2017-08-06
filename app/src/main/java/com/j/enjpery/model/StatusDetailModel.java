package com.j.enjpery.model;

/**
 * Created by J on 2017/8/6.
 */

import android.content.Context;

import java.util.ArrayList;
public interface StatusDetailModel {

    interface OnCommentCallBack {
        void noMoreDate();

        void onDataFinish(ArrayList<Comment> commentlist);

        void onError(String error);
    }


    interface OnRepostCallBack {
        void noMoreDate();

        void onDataFinish(ArrayList<Status> commentlist);

        void onError(String error);
    }

    public void comment(int groupType, Status status, Context context, OnCommentCallBack onCommentCallBack);

    public void commentNextPage(int groupType, Status status, Context context, OnCommentCallBack onCommentCallBack);


    public void repost(int groupType, Status status, Context context, OnRepostCallBack onRepostCallBack);

    public void repostNextPage(int groupType, Status status, Context context, OnRepostCallBack onRepostCallBack);

}