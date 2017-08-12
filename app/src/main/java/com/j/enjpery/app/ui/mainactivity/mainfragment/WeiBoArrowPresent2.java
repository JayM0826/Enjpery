package com.j.enjpery.app.ui.mainactivity.mainfragment;

import android.content.Context;

import com.avos.avoscloud.AVStatus;
import com.avos.avoscloud.AVUser;

/**
 * Created by J on 2017/8/8.
 */

public interface WeiBoArrowPresent2 {

    public void weibo_destroy(long id, Context context, int position, String weiboGroup);

    public void user_destroy(AVUser user, Context context);

    public void user_create(AVUser user, Context context);

    public void createFavorite(AVStatus status, Context context);

    public void cancalFavorite(int position, AVStatus status, Context context, boolean deleteAnimation);

    //public void cancalFavorite(int position, Status status, Context context);

}