package com.j.enjpery.app.ui.mainactivity.mainfragment.timelinefragment_widget;

import android.content.Context;
import android.widget.PopupWindow;

import com.avos.avoscloud.AVStatus;
import com.avos.avoscloud.AVUser;
import com.j.enjpery.app.util.Constants;
import com.j.enjpery.model.FavoriteList;
import com.j.enjpery.model.FavoriteListModel;
import com.j.enjpery.model.FavoriteListModelImp;
import com.j.enjpery.model.FriendShipModel;
import com.j.enjpery.model.FriendShipModelImp;
import com.j.enjpery.model.StatusList;
import com.j.enjpery.model.StatusListModel;
import com.j.enjpery.model.StatusListModelImp;

/**
 * Created by J on 2017/8/6.
 */

public class WeiBoArrowPresenterImp implements WeiBoArrowPresent {
    private StatusListModel statusListModel;
    private FriendShipModel friendShipModel;
    private FavoriteListModel favoriteListModel;
    private TimelineAdapter mAdapter;
    private Context mContext;
    private PopupWindow mPopupWindows;

    public WeiBoArrowPresenterImp(PopupWindow popupWindow) {
        statusListModel = new StatusListModelImp();
        friendShipModel = new FriendShipModelImp();
        favoriteListModel = new FavoriteListModelImp();
        this.mPopupWindows = popupWindow;
    }

    public WeiBoArrowPresenterImp(PopupWindow popupWindow, TimelineAdapter adapter) {
        statusListModel = new StatusListModelImp();
        friendShipModel = new FriendShipModelImp();
        favoriteListModel = new FavoriteListModelImp();
        this.mPopupWindows = popupWindow;
        this.mAdapter = adapter;
    }

    public WeiBoArrowPresenterImp(TimelineAdapter adapter) {
        statusListModel = new StatusListModelImp();
        friendShipModel = new FriendShipModelImp();
        favoriteListModel = new FavoriteListModelImp();
        this.mAdapter = adapter;
    }


    /**
     * 删除一条微博
     *
     * @param id
     * @param context
     */
    public void weibo_destroy(long id, Context context, final int position, final String weiboGroup) {
        mContext = context;
        mPopupWindows.dismiss();
        statusListModel.weibo_destroy(id, context, new StatusListModel.OnRequestListener() {
            @Override
            public void onSuccess() {
                //内存删除
                mAdapter.removeDataItem(position);
                //显示动画效果
                mAdapter.notifyItemRemoved(position);
                //notifyItemRangeChanged：对于被删掉的位置及其后range大小范围内的view进行重新onBindViewHolder
                mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount() - (1 + position));
                //本地删除
                updateLocalFile(weiboGroup, position);
            }

            @Override
            public void onError(String error) {

            }
        });
    }


    public void updateLocalFile(String weiboGroup, int position) {
        String response = null;
        switch (weiboGroup) {
            case Constants.DELETE_WEIBO_TYPE1:
                // response = SDCardUtil.get(mContext, SDCardUtil.getSDCardPath() + "/weiSwift/home", "全部微博" + AccessTokenKeeper.readAccessToken(mContext).getUid() + ".txt");
                break;
            case Constants.DELETE_WEIBO_TYPE2:
                // response = SDCardUtil.get(mContext, SDCardUtil.getSDCardPath() + "/weiSwift/profile", "我的全部微博" + AccessTokenKeeper.readAccessToken(mContext).getUid() + ".txt");
                break;
            case Constants.DELETE_WEIBO_TYPE3:
                // response = SDCardUtil.get(mContext, SDCardUtil.getSDCardPath() + "/weiSwift/profile", "我的原创微博" + AccessTokenKeeper.readAccessToken(mContext).getUid() + ".txt");
                break;
            case Constants.DELETE_WEIBO_TYPE4:
                // response = SDCardUtil.get(mContext, SDCardUtil.getSDCardPath() + "/weiSwift/profile", "我的图片微博" + AccessTokenKeeper.readAccessToken(mContext).getUid() + ".txt");
                break;
            case Constants.DELETE_WEIBO_TYPE5:
                // response = SDCardUtil.get(mContext, SDCardUtil.getSDCardPath() + "/weiSwift/home", "好友圈" + AccessTokenKeeper.readAccessToken(mContext).getUid() + ".txt");
                break;
            case Constants.DELETE_WEIBO_TYPE6:
                // response = SDCardUtil.get(mContext, SDCardUtil.getSDCardPath() + "/weiSwift/profile", "我的收藏" + AccessTokenKeeper.readAccessToken(mContext).getUid() + ".txt");
                FavoriteList favoriteList = FavoriteList.parse(response);
                if (favoriteList != null && favoriteList.favorites.size() > 0 && position < favoriteList.favorites.size()) {
                    favoriteList.favorites.remove(position);
                    // SDCardUtil.put(mContext, SDCardUtil.getSDCardPath() + "/weiSwift/profile", "我的收藏" + AccessTokenKeeper.readAccessToken(mContext).getUid() + ".txt", new Gson().toJson(favoriteList));
                }
                return;
        }

        StatusList statusList = StatusList.parse(response);
        if (statusList != null && statusList.statuses.size() > 0 && position < statusList.statuses.size()) {
            statusList.statuses.remove(position);
            switch (weiboGroup) {
                case Constants.DELETE_WEIBO_TYPE1:
                   // SDCardUtil.put(mContext, SDCardUtil.getSDCardPath() + "/weiSwift/home", "全部微博" + AccessTokenKeeper.readAccessToken(mContext).getUid() + ".txt", new Gson().toJson(statusList));
                    break;
                case Constants.DELETE_WEIBO_TYPE2:
                    // SDCardUtil.put(mContext, SDCardUtil.getSDCardPath() + "/weiSwift/profile", "我的全部微博" + AccessTokenKeeper.readAccessToken(mContext).getUid() + ".txt", new Gson().toJson(statusList));
                    break;
                case Constants.DELETE_WEIBO_TYPE3:
                    // SDCardUtil.put(mContext, SDCardUtil.getSDCardPath() + "/weiSwift/profile", "我的原创微博" + AccessTokenKeeper.readAccessToken(mContext).getUid() + ".txt", new Gson().toJson(statusList));
                    break;
                case Constants.DELETE_WEIBO_TYPE4:
                   // SDCardUtil.put(mContext, SDCardUtil.getSDCardPath() + "/weiSwift/profile", "我的图片微博" + AccessTokenKeeper.readAccessToken(mContext).getUid() + ".txt", new Gson().toJson(statusList));
                    break;
                case Constants.DELETE_WEIBO_TYPE5:
                    // SDCardUtil.put(mContext, SDCardUtil.getSDCardPath() + "/weiSwift/home", "好友圈" + AccessTokenKeeper.readAccessToken(mContext).getUid() + ".txt", new Gson().toJson(statusList));
                    break;
            }
        }
    }

    /**
     * 取消关注某一用户
     *
     * @param context
     */
    public void user_destroy(AVUser user, Context context) {
        mContext = context;
        mPopupWindows.dismiss();
        friendShipModel.user_destroy(user, context, new FriendShipModel.OnRequestListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(String error) {

            }
        }, false);
    }

    @Override
    public void user_create(AVUser user, Context context) {
        mContext = context;
        mPopupWindows.dismiss();
        friendShipModel.user_create(user, context, new FriendShipModel.OnRequestListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(String error) {

            }
        }, false);
    }

    /**
     * 收藏一条微博
     *
     * @param status
     * @param context
     */
    public void createFavorite(AVStatus status, Context context) {
        mContext = context;
        mPopupWindows.dismiss();
        favoriteListModel.createFavorite(status, context, new FavoriteListModel.OnRequestUIListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(String error) {

            }
        });
    }

    /**
     * 取消收藏一条微博
     *
     * @param status
     * @param context
     */
    @Override
    public void cancalFavorite(final int position, AVStatus status, Context context, final boolean deleteAnimation) {
        mContext = context;
        if (mPopupWindows != null) {
            mPopupWindows.dismiss();
        }
        favoriteListModel.cancelFavorite(status, context, new FavoriteListModel.OnRequestUIListener() {
            @Override
            public void onSuccess() {
                if (deleteAnimation) {
                    //内存删除
                    mAdapter.removeDataItem(position);
                    //显示动画效果
                    mAdapter.notifyItemRemoved(position);
                    //对于被删掉的位置及其后range大小范围内的view进行重新onBindViewHolder
                    mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount() - (1 + position));
                    //本地删除
                    updateLocalFile(Constants.DELETE_WEIBO_TYPE6, position);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

}

