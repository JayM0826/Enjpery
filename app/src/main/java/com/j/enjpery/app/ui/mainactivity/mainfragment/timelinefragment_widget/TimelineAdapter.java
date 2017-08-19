package com.j.enjpery.app.ui.mainactivity.mainfragment.timelinefragment_widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVStatus;
import com.j.enjpery.R;
import com.j.enjpery.app.ui.customview.EmojiTextView;
import com.j.enjpery.app.util.ToastUtil;
import com.jakewharton.rxbinding2.view.RxView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import timber.log.Timber;

/**
 * Created by J on 2017/8/6.
 */
public abstract class TimelineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ORINGIN_ITEM = 0;
    private static final int TYPE_RETWEET_ITEM = 3;


    private List<AVStatus> mDatas;
    private Context mContext;
    private View mView;

    public TimelineAdapter(List<AVStatus> datas, Context context) {
        this.mDatas = datas;
        this.mContext = context;
    }

    /**
     * 设置图片间距，注意要保证addItemDecoration只被调用一次，多次调用间距会累加
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ORINGIN_ITEM) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.original_status_layout, parent, false);
            OriginViewHolder originViewHolder = new OriginViewHolder(mView);
            originViewHolder.statusImageRecycleView.addOnScrollListener(new NewPauseOnScrollListener(ImageLoader.getInstance().getInstance(), true, true));
            return originViewHolder;
        } else if (viewType == TYPE_RETWEET_ITEM) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.retstatus_layout, parent, false);
            RetweetViewHolder retweetViewHolder = new RetweetViewHolder(mView);
            retweetViewHolder.originImageList.addOnScrollListener(new NewPauseOnScrollListener(ImageLoader.getInstance().getInstance(), true, true));
            return retweetViewHolder;
        }
        return null;
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginViewHolder) {
            //如果这条原创微博没有被删除
            if (mDatas.get(position) != null) {
                ((OriginViewHolder) holder).titlebarLayout.setVisibility(View.VISIBLE);
                ((OriginViewHolder) holder).bottombarLayout.setVisibility(View.VISIBLE);
                ((OriginViewHolder) holder).splitLine.setVisibility(View.GONE);
                FillContent.fillTitleBar(mContext, mDatas.get(position), ((OriginViewHolder) holder).profileImg,  ((OriginViewHolder) holder).profileName, ((OriginViewHolder) holder).profileTime, ((OriginViewHolder) holder).weiboComeFrom);
                FillContent.fillWeiBoContent(mDatas.get(position).getMessage(), mContext, ((OriginViewHolder) holder).statusContent);
                FillContent.fillButtonBar(mContext, mDatas.get(position), ((OriginViewHolder) holder).bottombarRetweet, ((OriginViewHolder) holder).bottombarComment, ((OriginViewHolder) holder).bottombarAttitude, ((OriginViewHolder) holder).comment, ((OriginViewHolder) holder).redirect, ((OriginViewHolder) holder).feedlike);
                FillContent.fillWeiBoImgList(mDatas.get(position), mContext, ((OriginViewHolder) holder).statusImageRecycleView);

                RxView.clicks(((OriginViewHolder) holder).bottombarLayout)
                        .subscribe(aVoid -> {
                            Timber.d("点击了bottombar");
                            ToastUtil.showShort(mContext, "点击了bottombar");
                        });

                RxView.clicks(((OriginViewHolder) holder).userActionMore)
                        .subscribe(aVoid -> {
                            Timber.d("点击了userActionMore");
                            ToastUtil.showShort(mContext, "点击了userActionMore");
                            ((OriginViewHolder) holder).originWeiboLayout.setDrawingCacheEnabled(true);
                            ((OriginViewHolder) holder).originWeiboLayout.buildDrawingCache(true);
                            arrowClick(mDatas.get(position), position, ((OriginViewHolder) holder).originWeiboLayout.getDrawingCache());
                        });

                RxView.clicks(((OriginViewHolder) holder).originWeiboLayout)
                        .subscribe(Avoid -> {
                            Toast.makeText(mContext, "点击了微博背景", Toast.LENGTH_SHORT).show();
                             /*  Intent intent = new Intent(mContext, OriginPicTextCommentDetailSwipeActivity.class);
                        intent.putExtra("weiboitem", mDatas.get(position));
                        mContext.startActivity(intent);*/
                        });
            }
            //如果这条原创微博被删除
            /*else {
                ((OriginViewHolder) holder).titlebar_layout.setVisibility(View.GONE);
                ((OriginViewHolder) holder).bottombar_layout.setVisibility(View.GONE);
                ((OriginViewHolder) holder).imageList.setVisibility(View.GONE);
                ((OriginViewHolder) holder).splitLine.setVisibility(View.VISIBLE);
                ((OriginViewHolder) holder).favoritedelete.setVisibility(View.VISIBLE);
                FillContent.fillWeiBoContent(mDatas.get(position).text, mContext, ((OriginViewHolder) holder).weibo_content);

                ((OriginViewHolder) holder).favoritedelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        WeiBoArrowPresent weiBoArrowPresent = new WeiBoArrowPresenterImp(TimelineAdapter.this);
                        weiBoArrowPresent.cancalFavorite(position, mDatas.get(position), mContext, true);
                    }
                });
            }


        } else if (holder instanceof RetweetViewHolder) {
            FillContent.fillTitleBar(mContext, mDatas.get(position), ((RetweetViewHolder) holder).profile_img, ((RetweetViewHolder) holder).profile_verified, ((RetweetViewHolder) holder).profile_name, ((RetweetViewHolder) holder).profile_time, ((RetweetViewHolder) holder).weibo_comefrom);
            FillContent.fillRetweetContent(mDatas.get(position), mContext, ((RetweetViewHolder) holder).origin_nameAndcontent);
            FillContent.fillWeiBoContent(mDatas.get(position).text, mContext, ((RetweetViewHolder) holder).retweet_content);
            FillContent.fillButtonBar(mContext, mDatas.get(position), ((RetweetViewHolder) holder).bottombar_retweet, ((RetweetViewHolder) holder).bottombar_comment, ((RetweetViewHolder) holder).bottombar_attitude, ((RetweetViewHolder) holder).comment, ((RetweetViewHolder) holder).redirect, ((RetweetViewHolder) holder).feedlike);
            FillContent.fillWeiBoImgList(mDatas.get(position).retweeted_status, mContext, ((RetweetViewHolder) holder).retweet_imageList);

            //点击转发的内容
            ((RetweetViewHolder) holder).retweetStatus_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDatas.get(position).retweeted_status.user != null) {
                        Toast.makeText(mContext, "点赞转发的内容", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mContext, OriginPicTextCommentDetailSwipeActivity.class);
                        intent.putExtra("weiboitem", mDatas.get(position).retweeted_status);
                        mContext.startActivity(intent);
                    }
                }
            });

            ((RetweetViewHolder) holder).popover_arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((RetweetViewHolder) holder).retweet_weibo_layout.setDrawingCacheEnabled(true);
                    ((RetweetViewHolder) holder).retweet_weibo_layout.buildDrawingCache(true);
                    arrowClick(mDatas.get(position), position, ((RetweetViewHolder) holder).retweet_weibo_layout.getDrawingCache());
                }
            });

            //微博背景的点击事件
            ((RetweetViewHolder) holder).retweet_weibo_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "微博背景的点击事件", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(mContext, RetweetPicTextCommentDetailSwipeActivity.class);
                    intent.putExtra("weiboitem", mDatas.get(position));
                    mContext.startActivity(intent);
                }
            });*/


        }

    }

    @Override
    public int getItemCount() {
        if (mDatas != null) {
            return mDatas.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas.get(position).get("retweetedStatus") != null) {
            return TYPE_RETWEET_ITEM;
        } else {
            return TYPE_ORINGIN_ITEM;
        }
    }

    public void setData(List<AVStatus> data) {
        this.mDatas = data;
    }

    public abstract void arrowClick(AVStatus status, int position, Bitmap bitmap);

    public void removeDataItem(int position) {
        mDatas.remove(position);
    }

    static class OriginViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.profile_img)
        CircleImageView profileImg;
        @BindView(R.id.profile_name)
        TextView profileName;
        @BindView(R.id.profile_time)
        TextView profileTime;
        @BindView(R.id.weiboComeFrom)
        TextView weiboComeFrom;
        @BindView(R.id.userActionMore)
        ImageView userActionMore;
        @BindView(R.id.titlebar_layout)
        LinearLayout titlebarLayout;
        @BindView(R.id.statusContent)
        EmojiTextView statusContent;
        @BindView(R.id.statusImageRecycleView)
        RecyclerView statusImageRecycleView;
        @BindView(R.id.splitLine)
        ImageView splitLine;
        @BindView(R.id.redirect)
        TextView redirect;
        @BindView(R.id.bottombar_retweet)
        LinearLayout bottombarRetweet;
        @BindView(R.id.comment)
        TextView comment;
        @BindView(R.id.bottombar_comment)
        LinearLayout bottombarComment;
        @BindView(R.id.feedlike)
        TextView feedlike;
        @BindView(R.id.bottombar_attitude)
        LinearLayout bottombarAttitude;
        @BindView(R.id.bottombar_layout)
        LinearLayout bottombarLayout;
        @BindView(R.id.origin_weibo_layout)
        LinearLayout originWeiboLayout;

        public OriginViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

     static class RetweetViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.profile_img)
        CircleImageView profileImg;
        @BindView(R.id.profile_name)
        TextView profileName;
        @BindView(R.id.profile_time)
        TextView profileTime;
        @BindView(R.id.weiboComeFrom)
        TextView weiboComeFrom;
        @BindView(R.id.userActionMore)
        ImageView userActionMore;
        @BindView(R.id.titlebar_layout)
        LinearLayout titlebarLayout;
        @BindView(R.id.retweet_content)
        EmojiTextView retweetContent;
        @BindView(R.id.origin_nameAndcontent)
        EmojiTextView originNameAndcontent;
        @BindView(R.id.origin_imageList)
        RecyclerView originImageList;
        @BindView(R.id.retweetStatus_layout)
        LinearLayout retweetStatusLayout;
        @BindView(R.id.redirect)
        TextView redirect;
        @BindView(R.id.bottombar_retweet)
        LinearLayout bottombarRetweet;
        @BindView(R.id.comment)
        TextView comment;
        @BindView(R.id.bottombar_comment)
        LinearLayout bottombarComment;
        @BindView(R.id.feedlike)
        TextView feedlike;
        @BindView(R.id.bottombar_attitude)
        LinearLayout bottombarAttitude;
        @BindView(R.id.bottombar_layout)
        LinearLayout bottombarLayout;
        @BindView(R.id.retweet_weibo_layout)
        LinearLayout retweetWeiboLayout;


        public RetweetViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }


}