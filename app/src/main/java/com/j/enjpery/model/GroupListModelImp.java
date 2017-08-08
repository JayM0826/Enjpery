package com.j.enjpery.model;

import android.content.Context;

import com.avos.avoscloud.AVException;
import com.j.enjpery.app.ui.mainactivity.mainfragment.timelinefragment_widget.RequestListener;
import com.j.enjpery.app.util.SDCardUtil;

import java.util.ArrayList;

/**
 * Created by J on 2017/8/8.
 */

public class GroupListModelImp implements GroupListModel {
    private ArrayList<Group> mGroupList;
    private boolean mFirstGetGroup = true;
    private Context mContext;
    private OnGroupListFinishedListener mOnGroupListFinishedListener;

    public void groupsOnlyOnce(Context context, OnGroupListFinishedListener onGroupListFinishedListener) {
        if (mFirstGetGroup) {
            groups(context, onGroupListFinishedListener);
        } else {
            cacheLoad(context, onGroupListFinishedListener);
        }
    }


    private void groups(final Context context, final OnGroupListFinishedListener onGroupListFinishedListener) {
       /* GroupAPI groupAPI = new GroupAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        mContext = context;
        mOnGroupListFinishedListener = onGroupListFinishedListener;
        groupAPI.groups(mGroupRequestListener);*/
    }

    @Override
    public void cacheLoad(Context context, OnGroupListFinishedListener onGroupListFinishedListener) {
        /*String response = SDCardUtil.get(context, SDCardUtil.getSDCardPath() + "/weiSwift/other", "我的分组列表" + AccessTokenKeeper.readAccessToken(context).getUid() + ".txt");
        if (response != null) {
            mGroupList = GroupList.parse(response).lists;
            onGroupListFinishedListener.onDataFinish(mGroupList);
        }*/
    }

    @Override
    public void cacheSave(Context context, String response) {
        // SDCardUtil.put(context, SDCardUtil.getSDCardPath() + "/weiSwift/other", "我的分组列表" + AccessTokenKeeper.readAccessToken(context).getUid() + ".txt", response);
    }

    public RequestListener mGroupRequestListener = new RequestListener() {
        @Override
        public void onComplete(String response) {
            mFirstGetGroup = false;
            cacheSave(mContext, response);
            ArrayList<Group> groupslist = GroupList.parse(response).lists;
            mOnGroupListFinishedListener.onDataFinish(groupslist);
        }

        @Override
        public void onWeiboException(AVException e) {
            mOnGroupListFinishedListener.onError(e.getMessage());
            cacheLoad(mContext, mOnGroupListFinishedListener);
        }
    };
}

