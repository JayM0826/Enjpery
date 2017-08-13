package com.j.enjpery.app.ui.publishtimeline;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.j.enjpery.R;
import com.lzy.imagepicker.bean.ImageItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by J on 2017/8/13.
 */

/*getItemViewType(获取显示类型，返回值可在onCreateViewHolder中拿到，以决定加载哪种ViewHolder)
onCreateViewHolder(加载ViewHolder的布局)
onViewAttachedToWindow（当Item进入这个页面的时候调用）
onBindViewHolder(将数据绑定到布局上，以及一些逻辑的控制就写这啦)
onViewDetachedFromWindow（当Item离开这个页面的时候调用）
onViewRecycled(当Item被回收的时候调用)*/

public class ImgListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ImageItem> mDatas = new ArrayList<>();
    private Context mContext;
    private View mView;
    private OnFooterViewClickListener onFooterViewClickListener;

    private static final int ITEM_TYPE_IMAGE = 1;
    private static final int ITEM_TYPE_DELETE = 2;


    public ImgListAdapter(ArrayList<ImageItem> datas, Context context) {
        this.mDatas = datas;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_IMAGE) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.compose_idea_img_footerview, parent, false);
            FooterViewHoder footerHolder = new FooterViewHoder(mView);
            return footerHolder;


        } else if (viewType == ITEM_TYPE_DELETE) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.compose_idea_img_item, parent, false);
            ImgViewHolder viewHolder = new ImgViewHolder(mView);
            return viewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ImgViewHolder) {
            if (mDatas != null && mDatas.size() >= 9){
                Glide.with(mContext).load(mDatas.get(position).path).into(((ImgViewHolder) holder).imageView);
            }else {
                Glide.with(mContext).load(mDatas.get(position - 1).path).into(((ImgViewHolder) holder).imageView);
            }

            ((ImgViewHolder) holder).delteImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyItemRemoved(position);
                    mDatas.remove(position);
                    notifyItemRangeChanged(position, mDatas.size() + 1);
                    EventBus.getDefault().post(new DeleteImageEvent(true));
                }
            });
        } else if (holder instanceof FooterViewHoder) {
            if (mDatas.size() >= 9) {
                ((FooterViewHoder) holder).addpic.setVisibility(View.GONE);
            } else {
                ((FooterViewHoder) holder).addpic.setVisibility(View.VISIBLE);
                ((FooterViewHoder) holder).addpic.setOnClickListener(v -> {
                    {
                        onFooterViewClickListener.OnFooterViewClick();
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mDatas != null && mDatas.size() > 0 && mDatas.size() < 9) {
            return mDatas.size() + 1;
        } else if (mDatas != null && mDatas.size() >= 9) {
            return 9;
        } else {
            return 0;
        }
    }


    @Override
    public int getItemViewType(int position) {

        if (mDatas.size() > 0 && position == 0 && mDatas.size() < 9) {
            return ITEM_TYPE_IMAGE;
        } else {
            return ITEM_TYPE_DELETE;
        }

    }

    public void setData(ArrayList<ImageItem> data) {
        this.mDatas = data;
    }

    protected class ImgViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public ImageView delteImg;

        public ImgViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageitem);
            delteImg = (ImageView) view.findViewById(R.id.deleteitem);

        }
    }

    protected class FooterViewHoder extends RecyclerView.ViewHolder {

        public ImageView addpic;

        public FooterViewHoder(View view) {
            super(view);
            addpic = (ImageView) view.findViewById(R.id.addpic);
        }
    }

    public interface OnFooterViewClickListener {
        public void OnFooterViewClick();
    }

    public void setOnFooterViewClickListener(OnFooterViewClickListener onFooterViewClickListener) {
        this.onFooterViewClickListener = onFooterViewClickListener;
    }

}

