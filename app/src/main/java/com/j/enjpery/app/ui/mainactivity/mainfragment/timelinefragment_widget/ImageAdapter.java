package com.j.enjpery.app.ui.mainactivity.mainfragment.timelinefragment_widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.avos.avoscloud.AVStatus;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.j.enjpery.R;
import com.j.enjpery.app.global.NewFeature;
import com.j.enjpery.app.util.ScreenUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by J on 2017/8/6.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private ArrayList<String> mData;
    private Context mContext;

    /**
     * 用于加载微博列表图片的配置，进行安全压缩，尽可能的展示图片细节
     */
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .build();
    /**
     * 用于加载微博列表图片的配置，进行安全压缩，尽可能的展示图片细节
     */

    public ImageAdapter(AVStatus status, Context context) {
        /*if (NewFeature.timeline_img_quality == NewFeature.thumbnail_quality) {
            this.mData = status.thumbnail_pic_urls;
        } else if (NewFeature.timeline_img_quality == NewFeature.bmiddle_quality) {
            this.mData = status.bmiddle_pic_urls;
        } else {
            this.mData = status.origin_pic_urls;
        }*/
        this.mContext = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.mainfragment_weiboitem_imageitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        //设置加载中的图片样式
        if (NewFeature.timeline_img_quality != NewFeature.thumbnail_quality) {
            setImgSize(mData, mContext, viewHolder.norImg, viewHolder.longImg, viewHolder.gifImg);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        FillContent.fillImageList(mContext,options, mData, position, holder.longImg, holder.norImg, holder.gifImg, holder.imageLabel);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(ArrayList<String> data) {
        this.mData = data;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.longImg)
        SubsamplingScaleImageView longImg;
        @BindView(R.id.norImg)
        ImageView norImg;
        @BindView(R.id.gifView)
        GifImageView gifImg;
        @BindView(R.id.imageType)
        ImageView imageLabel;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 根据图片的数量，设置不同的尺寸
     *
     * @param datas
     * @param context
     * @param norImg
     * @param longImg
     * @param gifImg
     */
    private static void setImgSize(List<String> datas, Context context, ImageView norImg, SubsamplingScaleImageView longImg, GifImageView gifImg) {
        if (datas == null || datas.size() == 0) {
            return;
        }
        setThreeImgSize(context, norImg, longImg, gifImg);

//        if (datas.size() == 1) {
//            setSingleImgSize(context, norImg, longImg, gifImg);
//        } else if (datas.size() == 2 || datas.size() == 4) {
//            setDoubleImgSize(context, norImg, longImg, gifImg);
//        } else if (datas.size() == 3 || datas.size() >= 5) {
//            setThreeImgSize(context, norImg, longImg, gifImg);
//        }
    }

    private static void setDoubleImgSize(Context context, ImageView norImg, SubsamplingScaleImageView longImg, GifImageView gifImg) {
        FrameLayout.LayoutParams norImgLayout = (FrameLayout.LayoutParams) norImg.getLayoutParams();
        FrameLayout.LayoutParams longImgLayout = (FrameLayout.LayoutParams) longImg.getLayoutParams();
        FrameLayout.LayoutParams gifImgLayout = (FrameLayout.LayoutParams) gifImg.getLayoutParams();
        longImgLayout.width = ScreenUtil.getScreenWidth(context) / 2;
        norImgLayout.width = ScreenUtil.getScreenWidth(context) / 2;
        gifImgLayout.width = ScreenUtil.getScreenWidth(context) / 2;
        norImgLayout.height = ScreenUtil.getScreenWidth(context) / 2;
        longImgLayout.height = ScreenUtil.getScreenWidth(context) / 2;
        gifImgLayout.height = ScreenUtil.getScreenWidth(context) / 2;
    }

    private static void setSingleImgSize(Context context, ImageView norImg, SubsamplingScaleImageView longImg, GifImageView gifImg) {
        FrameLayout.LayoutParams norImgLayout = (FrameLayout.LayoutParams) norImg.getLayoutParams();
        FrameLayout.LayoutParams longImgLayout = (FrameLayout.LayoutParams) longImg.getLayoutParams();
        FrameLayout.LayoutParams gifImgLayout = (FrameLayout.LayoutParams) gifImg.getLayoutParams();
        longImgLayout.width = ScreenUtil.getScreenWidth(context);
        norImgLayout.width = ScreenUtil.getScreenWidth(context);
        gifImgLayout.width = ScreenUtil.getScreenWidth(context);
        norImgLayout.height = (int) (ScreenUtil.getScreenWidth(context) * 0.7);
        longImgLayout.height = (int) (ScreenUtil.getScreenWidth(context) * 0.7);
        gifImgLayout.height = (int) (ScreenUtil.getScreenWidth(context) * 0.7);
    }

    private static void setThreeImgSize(Context context, ImageView norImg, SubsamplingScaleImageView longImg, GifImageView gifImg) {
        FrameLayout.LayoutParams norImgLayout = (FrameLayout.LayoutParams) norImg.getLayoutParams();
        FrameLayout.LayoutParams longImgLayout = (FrameLayout.LayoutParams) longImg.getLayoutParams();
        FrameLayout.LayoutParams gifImgLayout = (FrameLayout.LayoutParams) gifImg.getLayoutParams();
        longImgLayout.width = ScreenUtil.getScreenWidth(context) / 3;
        norImgLayout.width = ScreenUtil.getScreenWidth(context) / 3;
        gifImgLayout.width = ScreenUtil.getScreenWidth(context) / 3;
        norImgLayout.height = ScreenUtil.getScreenWidth(context) / 3;
        longImgLayout.height = ScreenUtil.getScreenWidth(context) / 3;
        gifImgLayout.height = ScreenUtil.getScreenWidth(context) / 3;
    }

}
