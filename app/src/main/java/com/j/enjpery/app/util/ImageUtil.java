package com.j.enjpery.app.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;

import java.io.File;

/**
 * Created by J on 2017/8/6.
 */
public class ImageUtil {


    public static boolean isLongImg(File file, Bitmap bitmap) {
        //TODO file.length()的判断，需要根据OS的版本号做动态调整大小
        if (file == null || file.length() == 0) {
            return false;
        }
        if (bitmap.getHeight() > bitmap.getWidth() * 3 || file.length() >= 0.5 * 1024 * 1024) {
            return true;
        }
        return false;
    }

    public static void saveImg(final Context context, final String url) {
        Glide.with(context).download(url);
        DisplayImageOptions saveOpition = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .build();
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
        ImageLoader.getInstance().loadImage(url, saveOpition, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                File imgFile = DiskCacheUtils.findInCache(url, ImageLoader.getInstance().getDiskCache());
                if (imgFile == null) {
                    ToastUtil.showShort(context, "保存文件失败，请检查SD卡是否已满！");
                    return;
                }
                if (url.endsWith(".gif")){
                    SaveImgUtil.create(context).saveImage(imgFile,".gif");
                }else {
                    SaveImgUtil.create(context).saveImage(imgFile,"jpg");
                }
            }
        });
    }


}