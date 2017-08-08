package com.j.enjpery.app.ui.mainactivity.mainfragment.timelinefragment_widget;

import android.content.Context;

import com.j.enjpery.model.Group;
import com.j.enjpery.model.GroupListModel;
import com.j.enjpery.model.GroupListModelImp;

import java.util.ArrayList;

/**
 * Created by J on 2017/8/8.
 */

public class GroupListPresenterImp implements GroupListPresenter {

    private GroupPopWindowView mGroupPopView;
    private GroupListModel mGroupListModel;
    private HomeFragmentView mHomeFragmentView;

    public GroupListPresenterImp(GroupPopWindowView groupPopView) {
        this.mGroupPopView = groupPopView;
        this.mGroupListModel = new GroupListModelImp();
    }

    @Override
    public void updateGroupList(final Context context) {
        mGroupListModel.groupsOnlyOnce(context, new GroupListModel.OnGroupListFinishedListener() {
            @Override
            public void noMoreDate() {

            }

            @Override
            public void onDataFinish(ArrayList<Group> groupslist) {
                mGroupPopView.updateListView(groupslist);
            }

            @Override
            public void onError(String error) {
                mGroupPopView.showErrorMessage(error);
            }

        });
    }


}
