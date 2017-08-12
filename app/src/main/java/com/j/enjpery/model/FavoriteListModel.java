package com.j.enjpery.model;

import android.content.Context;

import com.avos.avoscloud.AVStatus;

import java.util.ArrayList;

/**
 * Created by J on 2017/8/6.
 */
public interface FavoriteListModel {

    interface OnRequestUIListener {
        void onSuccess();

        void onError(String error);
    }

    interface OnDataFinishedListener {
        void noMoreDate();

        void onDataFinish(ArrayList<AVStatus> statuslist);

        void onError(String error);

    }


    public void createFavorite(AVStatus status, Context context, OnRequestUIListener onRequestUIListener);

    public void cancelFavorite(AVStatus status, Context context, OnRequestUIListener onRequestUIListener);

    public void favorites(Context context, OnDataFinishedListener onDataFinishedListener);

    public void favoritesNextPage(Context context, OnDataFinishedListener onDataFinishedListener);

    public void cacheSave(Context context, String response);

    public void cacheLoad(Context context, OnDataFinishedListener onDataFinishedListener);

}