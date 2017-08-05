package com.j.enjpery.app.ui.mainactivity.mainfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.j.enjpery.R;
import com.j.enjpery.app.base.BaseFragment;

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
    public void refreshData() {

    }


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_timeline;
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
