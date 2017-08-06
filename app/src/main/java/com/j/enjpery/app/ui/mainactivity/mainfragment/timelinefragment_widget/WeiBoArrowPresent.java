package com.j.enjpery.app.ui.mainactivity.mainfragment.timelinefragment_widget;

import android.content.Context;

import com.j.enjpery.model.Status;
import com.j.enjpery.model.User;

/**
 * Created by J on 2017/8/6.
 */

public interface WeiBoArrowPresent {

    public void weibo_destroy(long id, Context context, int position, String weiboGroup);

    public void user_destroy(User user, Context context);

    public void user_create(User user, Context context);

    public void createFavorite(Status status, Context context);

    public void cancalFavorite(int position, Status status, Context context, boolean deleteAnimation);

    //public void cancalFavorite(int position, Status status, Context context);

}