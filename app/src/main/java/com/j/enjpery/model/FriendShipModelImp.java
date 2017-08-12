package com.j.enjpery.model;

import android.content.Context;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.google.gson.Gson;
import com.j.enjpery.app.global.NewFeature;
import com.j.enjpery.app.ui.mainactivity.mainfragment.timelinefragment_widget.RequestListener;
import com.j.enjpery.app.util.Constants;
import com.j.enjpery.app.util.SDCardUtil;
import com.j.enjpery.app.util.ToastUtil;

import java.util.ArrayList;

/**
 * Created by J on 2017/8/6.
 */

public class FriendShipModelImp implements FriendShipModel {

    private OnRequestListener mOnRequestUIListener;
    private Context mContext;

    @Override
    public void user_destroy(final AVUser user, Context context, OnRequestListener onRequestListener, boolean updateCache) {
        /*FriendshipsAPI friendshipsAPI = new FriendshipsAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        mContext = context;
        mOnRequestUIListener = onRequestListener;
        friendshipsAPI.destroy(Long.valueOf(user.id), user.screen_name, new RequestListener() {
            @Override
            public void onComplete(String s) {
                ToastUtil.showShort(mContext, "取消关注成功");
                //更新内存
                user.following = false;
                //更新本地缓存
                updateCache(mContext, user);
                NewFeature.refresh_profileLayout = true;
                mOnRequestUIListener.onSuccess();
            }

            @Override
            public void onWeiboException(AVException e) {
                ToastUtil.showShort(mContext, "取消关注失败");
                ToastUtil.showShort(mContext, e.getMessage());
                mOnRequestUIListener.onError(e.getMessage());
            }
        });*/
    }

    @Override
    public void user_create(final AVUser user, Context context, OnRequestListener onRequestListener, boolean updateCache) {
        /*FriendshipsAPI friendshipsAPI = new FriendshipsAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        mContext = context;
        mOnRequestUIListener = onRequestListener;
        friendshipsAPI.create(Long.valueOf(user.id), user.screen_name, new RequestListener() {
            @Override
            public void onComplete(String s) {
                ToastUtil.showShort(mContext, "关注成功");
                user.following = true;
                NewFeature.refresh_profileLayout = true;
                mOnRequestUIListener.onSuccess();
            }

            @Override
            public void onWeiboException(AVException e) {
                ToastUtil.showShort(mContext, "关注失败");
                ToastUtil.showShort(mContext, e.getMessage());
                mOnRequestUIListener.onError(e.getMessage());
            }
        });*/
    }

    /**
     * @param context
     */
    private void updateCache(Context context, AVUser usertoUpdate) {
        /*String follerResponse = SDCardUtil.get(context, SDCardUtil.getSDCardPath() + "/weiSwift/profile", "我的粉丝列表" + AccessTokenKeeper.readAccessToken(context).getUid() + ".txt");
        if (follerResponse != null) {
            UserList userList = UserList.parse(follerResponse);
            ArrayList<User> usersList = userList.users;
            for (User user : usersList) {
                if (user.id.equals(usertoUpdate.id)) {
                    user.following = usertoUpdate.following;
                    break;
                }
            }
            userList.users = usersList;*/
        // SDCardUtil.put(context, SDCardUtil.getSDCardPath() + "/weiSwift/profile", "我的粉丝列表" + AccessTokenKeeper.readAccessToken(context).getUid() + ".txt", new Gson().toJson(userList));

    }
}
