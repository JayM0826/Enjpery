package com.j.enjpery.app.ui.mainactivity.mainfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.j.enjpery.R;
import com.j.enjpery.app.base.BaseFragment;
import com.yalantis.phoenix.PullToRefreshView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimelineFragment extends BaseFragment {

/*@BindView(R.id.statusRecyclerView)
    RecyclerView statusRecyclerView;
    @BindView(R.id.pullToRefresh)
    PullToRefreshView pullToRefresh;
    @BindView(R.id.fab_item1)
    FloatingActionButton fabItem1;
    @BindView(R.id.fab_item2)
    FloatingActionButton fabItem2;
    @BindView(R.id.fab)
    FloatingActionMenu fab;
    private Runnable runnable;*/

    @Override
    public void fetchData() {

    }


    @Override
    public int getLayoutResId() {
        return R.layout.wxfragment_profile;
    }

    public TimelineFragment() {
        // Required empty public constructor
    }


    @Override
    public void initCreateView(Bundle state) {
        /*pullToRefresh.setOnRefreshListener(()-> {
            runnable = () -> {
                pullToRefresh.setRefreshing(false);
            };
            pullToRefresh.postDelayed(runnable, 1000);
        });*/
    }

}
