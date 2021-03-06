package com.j.enjpery.app.ui.mainactivity.mainfragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.AVStatus;
import com.j.enjpery.app.ui.mainactivity.mainfragment.timelinefragment_widget.TimelineAdapter;

/**
 * Created by J on 2017/8/8.
 */

/**
 * 箭头的上下文菜单
 */
public class TimelineArrowWindow extends ArrowDialog implements WeiBoArrowView {

    public TimelineArrowWindow(Context context, AVStatus status, TimelineAdapter weiboAdapter, int position, String groupName) {
        super(context, status, weiboAdapter, position, groupName);
    }

    public TimelineArrowWindow(Context context, AVStatus status) {
        super(context, status);
    }

    public TimelineArrowWindow(Context mContext, AVStatus status,  TimelineAdapter mAdapter, int position, Bitmap bitmap) {
        super(mContext, status, mAdapter, position, bitmap);
    }


    @Override
    public void initContent() {
        this.setFavoriteTextContext(mStatus, mFavoriteTextView);
        this.setFriendShipContext(mStatus, mFriendShipTextView);
        this.setDeleteViewContent(mStatus, mDeleteTextView);
        super.setShareViewContent(mStatus,mShareTv);
    }

    /**
     * 设置收藏的TextView的内容，如果收藏了此微博，则显示取消收藏，如果没有收藏，则显示收藏
     */
    @Override
    public void setFavoriteTextContext(final AVStatus status, TextView textView) {
        /*if (status.favorited) {
            textView.setText("取消收藏");
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mWeiBoArrowPresent.cancalFavorite(mItemPosition, status, mContext, false);
                }
            });
        } else {
            textView.setText("收藏");
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mWeiBoArrowPresent.createFavorite(status, mContext);
                }
            });
        }*/
    }

    /**
     * 设置朋友的关系内容，如果已经关注，则显示取消关注，如果没有关注，则显示关注
     */
    @Override
    public void setFriendShipContext(final AVStatus status, TextView textView) {
        /*if (status.user.id.equals(AccessTokenKeeper.readAccessToken(mContext).getUid())) {
            mFollerLayout.setVisibility(View.GONE);
        }*/
        /*if (status.user.following) {
            textView.setText("取消关注 " + status.user.name);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mWeiBoArrowPresent.user_destroy(status.user, mContext);
                }
            });
        } else {
            textView.setText("关注 " + status.user.name);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mWeiBoArrowPresent.user_create(status.user, mContext);
                }
            });
        }*/
    }

    /**
     * 设置是否显示删除按钮，如果不是自己的微博，要隐藏他
     */
    @Override
    public void setDeleteViewContent(final AVStatus status, final TextView textView) {
      /*  if (status.user.id.equals(AccessTokenKeeper.readAccessToken(mContext).getUid())) {
            textView.setVisibility(View.VISIBLE);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mWeiBoArrowPresent.weibo_destroy(Long.valueOf(status.id), mContext, mItemPosition, mGroupName);
                }
            });
        } else {
            mDeleteLayout.setVisibility(View.GONE);
            mFriendShipTextView.setBackgroundResource(R.drawable.home_weiboitem_arrow_pop_bottomitem_bg_auto);
        }*/
    }






}
