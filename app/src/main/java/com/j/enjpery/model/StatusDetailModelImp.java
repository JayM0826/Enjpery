package com.j.enjpery.model;

import android.content.Context;
import android.text.TextUtils;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVStatus;
import com.j.enjpery.app.global.NewFeature;
import com.j.enjpery.app.ui.mainactivity.mainfragment.timelinefragment_widget.RequestListener;
import com.j.enjpery.app.util.Constants;
import com.j.enjpery.app.util.ToastUtil;

import java.util.ArrayList;

/**
 * Created by J on 2017/8/6.
 */
public class StatusDetailModelImp implements StatusDetailModel {
    private ArrayList<AVStatus> mRepostList = new ArrayList<>();
    private ArrayList<Comment> mCommentList = new ArrayList<>();
    private boolean mRefrshAll = true;
    private int mCurrentGroup;
    public static final int COMMENT_PAGE = 0x1;
    public static final int REPOST_PAGE = 0x2;

    @Override
    public void comment(int groupType, AVStatus status, final Context context, final OnCommentCallBack onCommentCallBack) {
        /*CommentsAPI commentsAPI = new CommentsAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        commentsAPI.show(Long.valueOf(status.id), 0, 0, NewFeature.GET_COMMENT_ITEM, 1, 0, new RequestListener() {
            @Override
            public void onComplete(String response) {
                ArrayList<Comment> temp = CommentList.parse(response).comments;
                if (temp != null && temp.size() > 0) {
                    if (mCommentList != null) {
                        mCommentList.clear();
                    }
                    mCommentList = temp;
                    onCommentCallBack.onDataFinish(mCommentList);
                } else {
                    onCommentCallBack.noMoreDate();
                }
                mRefrshAll = false;
            }

            @Override
            public void onWeiboException(WeiboException e) {
                ToastUtil.showShort(context, e.getMessage());
                onCommentCallBack.onError(e.getMessage());
            }
        });*/
    }

    @Override
    public void commentNextPage(int groupType, AVStatus status, final Context context, final OnCommentCallBack onCommentCallBack) {
        /*CommentsAPI commentsAPI = new CommentsAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        String maxId = "";
        if (mCommentList.size() == 0) {
            maxId = "0";
        } else {
            maxId = mCommentList.get(mCommentList.size() - 1).id;
        }
        commentsAPI.show(Long.valueOf(status.id), 0, Long.valueOf(maxId), NewFeature.GET_COMMENT_ITEM, 1, 0, new RequestListener() {
            @Override
            public void onComplete(String response) {
                if (!TextUtils.isEmpty(response)) {
                    ArrayList<Comment> temp = CommentList.parse(response).comments;
                    if (temp.size() == 1) {
                        onCommentCallBack.noMoreDate();
                    } else if (temp.size() > 1) {
                        temp.remove(0);
                        mCommentList.addAll(temp);
                        onCommentCallBack.onDataFinish(mCommentList);
                    } else {
                        ToastUtil.showShort(context, "数据异常");
                        onCommentCallBack.onError("数据异常");
                    }
                } else {
                    onCommentCallBack.noMoreDate();
                }
            }

            @Override
            public void onWeiboException(AVException e) {
                onCommentCallBack.onError(e.getMessage());
            }
        });*/
    }

    @Override
    public void repost(int groupType, AVStatus status, final Context context, final OnRepostCallBack onRepostCallBack) {
        /*StatusesAPI statusesAPI = new StatusesAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        statusesAPI.repostTimeline(Long.valueOf(status.id), 0, 0, NewFeature.GET_COMMENT_ITEM, 1, 0, new RequestListener() {
            @Override
            public void onComplete(String response) {
                ArrayList<Status> temp = RetweetList.parse(response).reposts;
                if (temp != null && temp.size() > 0) {
                    if (mRepostList != null) {
                        mRepostList.clear();
                    }
                    mRepostList = temp;
                    onRepostCallBack.onDataFinish(mRepostList);
                } else {
                    onRepostCallBack.noMoreDate();
                }
                mRefrshAll = false;
            }

            @Override
            public void onWeiboException(AVException e) {
                ToastUtil.showShort(context, e.getMessage());
                onRepostCallBack.onError(e.getMessage());
            }
        });*/
    }

    @Override
    public void repostNextPage(int groupType, AVStatus status, final Context context, final OnRepostCallBack onRepostCallBack) {
        /*StatusesAPI statusesAPI = new StatusesAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        String maxId;
        if (mRepostList.size() == 0) {
            maxId = "0";
        } else {
            maxId = mRepostList.get(mRepostList.size() - 1).id;
        }
        statusesAPI.repostTimeline(Long.valueOf(status.id), 0, Long.valueOf(maxId), NewFeature.GET_COMMENT_ITEM, 1, 0, new RequestListener() {
            @Override
            public void onComplete(String response) {
                if (!TextUtils.isEmpty(response)) {
                    ArrayList<Status> temp = RetweetList.parse(response).reposts;
                    if (temp.size() == 1) {
                        onRepostCallBack.noMoreDate();
                    } else if (temp.size() > 1) {
                        temp.remove(0);
                        mRepostList.addAll(temp);
                        onRepostCallBack.onDataFinish(mRepostList);
                    } else {
                        ToastUtil.showShort(context, "数据异常");
                        onRepostCallBack.onError("数据异常");
                    }
                } else {
                    LoadedToast.showToast(context, "0条新微博");
                    onRepostCallBack.noMoreDate();
                }
            }

            @Override
            public void onWeiboException(WeiboException e) {
                onRepostCallBack.onError(e.getMessage());
            }
        });*/
    }
}
