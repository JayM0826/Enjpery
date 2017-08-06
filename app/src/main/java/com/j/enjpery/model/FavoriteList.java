package com.j.enjpery.model;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.j.enjpery.app.ui.mainactivity.mainfragment.timelinefragment_widget.FillContentHelper;

import java.util.ArrayList;

/**
 * Created by J on 2017/8/6.
 */
public class FavoriteList {

    /**
     * 微博列表
     */
    public ArrayList<Favorite> favorites;
    public int total_number;

    public static FavoriteList parse(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }
        FavoriteList favorites = new Gson().fromJson(jsonString, FavoriteList.class);
        //对status中的本地私有字段进行赋值
        for (Favorite favorite : favorites.favorites) {
            //服务器并没有返回我们单张图片的随机尺寸，这里我们手动需要随机赋值
            FillContentHelper.setSingleImgSizeType(favorite.status);
            //提取微博来源的关键字
            FillContentHelper.setSource(favorite.status);
            //设置三种类型图片的url地址
            FillContentHelper.setImgUrl(favorite.status);

            if (favorite.status.retweeted_status != null) {
                //服务器并没有返回我们单张图片的随机尺寸，这里我们手动需要随机赋值
                FillContentHelper.setSingleImgSizeType(favorite.status.retweeted_status);
                //提取微博来源的关键字
                FillContentHelper.setSource(favorite.status.retweeted_status);
                //设置三种类型图片的url地址
                FillContentHelper.setImgUrl(favorite.status.retweeted_status);
            }
        }


        return favorites;
    }

}
