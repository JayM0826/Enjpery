package com.j.enjpery.model;

import android.content.Context;

/**
 * Created by J on 2017/8/6.
 */

public interface FriendShipModel {

    interface OnRequestListener {
        void onSuccess();

        void onError(String error);
    }

    public void user_destroy(User user, Context context, OnRequestListener onRequestListener, boolean updateCache);

    public void user_create(User user, Context context, OnRequestListener onRequestListener, boolean updateCache);

}