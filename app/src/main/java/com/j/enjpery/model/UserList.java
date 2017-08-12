package com.j.enjpery.model;

import android.text.TextUtils;

import com.avos.avoscloud.AVUser;
import com.google.gson.Gson;
import com.j.enjpery.app.ui.mainactivity.mainfragment.timelinefragment_widget.FillContentHelper;

import java.util.ArrayList;

/**
 * Created by J on 2017/8/6.
 */
public class UserList {

    public ArrayList<AVUser> users = new ArrayList<AVUser>();
    public boolean hasvisible;
    public String previous_cursor;
    public String next_cursor;
    public int total_number;
    public int display_total_number;


    public static UserList parse(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }
        UserList userList = new Gson().fromJson(jsonString, UserList.class);

        //对status中的本地私有字段进行赋值
        for (AVUser user : userList.users) {
            //提取微博来源的关键字
            /*if (user.status != null) {
                //服务器并没有返回我们单张图片的随机尺寸，这里我们手动需要随机赋值
                FillContentHelper.setSingleImgSizeType(user.status);
                //提取微博来源的关键字
                FillContentHelper.setSource(user.status);
                //设置三种类型图片的url地址
                FillContentHelper.setImgUrl(user.status);

                //user的status字段中，不再包含有retweet_status字段了，所以不再进行处理
            }*/
        }
        return userList;
    }

}
