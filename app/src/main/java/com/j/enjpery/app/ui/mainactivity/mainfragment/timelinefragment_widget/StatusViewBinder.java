package com.j.enjpery.app.ui.mainactivity.mainfragment.timelinefragment_widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.j.enjpery.R;
import com.j.enjpery.app.ui.customview.EmojiTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by J on 2017/7/9.
 */
public class StatusViewBinder extends ItemViewBinder<Status, StatusViewBinder.ViewHolder> {

    private Context context;

    public StatusViewBinder(){};

    public StatusViewBinder(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.cardview_status, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Status status) {
        Glide.with(context).load(status.getUserHeadImageURL()).into(holder.profileImg);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.profile_img)
        CircleImageView profileImg;
        @BindView(R.id.profile_verified)
        ImageView profileVerified;
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
        @BindView(R.id.favorities_delete)
        TextView favoritiesDelete;
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
        @BindView(R.id.statusCardView)
        CardView statusCardView;


        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
