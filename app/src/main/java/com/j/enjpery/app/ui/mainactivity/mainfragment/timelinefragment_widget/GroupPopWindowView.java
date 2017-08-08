package com.j.enjpery.app.ui.mainactivity.mainfragment.timelinefragment_widget;

import com.j.enjpery.model.Group;

import java.util.ArrayList;

/**
 * Created by J on 2017/8/8.
 */

public interface GroupPopWindowView {

    /**
     * 将网络请求返回的数据，添加到ListView上,需要返回返回
     */
    public void updateListView(ArrayList<Group> datas);


    /**
     * Toast显示网络请求失败的错误信息
     *
     * @param error
     */
    public void showErrorMessage(String error);
}
