package com.j.enjpery.app.ui.mainactivity.mainfragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVStatus;
import com.j.enjpery.R;
import com.j.enjpery.app.base.BaseFragment;
import com.j.enjpery.app.ui.customview.LoadingFooter;
import com.j.enjpery.app.ui.mainactivity.mainfragment.timelinefragment_widget.HeaderAndFooterRecyclerViewAdapter;
import com.j.enjpery.app.ui.mainactivity.mainfragment.timelinefragment_widget.HomeFragmentPresent;
import com.j.enjpery.app.ui.mainactivity.mainfragment.timelinefragment_widget.HomeFragmentView;
import com.j.enjpery.app.ui.mainactivity.mainfragment.timelinefragment_widget.TimelineAdapter;
import com.j.enjpery.app.util.Constants;
import com.j.enjpery.app.util.DensityUtil;
import com.j.enjpery.app.util.RecyclerViewStateUtils;
import com.j.enjpery.app.util.ScreenUtil;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

public class TimelineFragment extends BaseFragment implements HomeFragmentView {

    @BindView(R.id.weiboRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.toast_msg)
    TextView mToastTv;
    @BindView(R.id.toast_bg)
    RelativeLayout mToastBg;
    @BindView(R.id.errorMessage)
    TextView mErrorMessage;
    @BindView(R.id.refreshTip)
    TextView refreshTip;
    @BindView(R.id.emptydeault_layout)
    LinearLayout mEmptyLayout;
    private List<AVStatus> mDatas;

    public TimelineAdapter mAdapter;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    private HomeFragmentPresent mHomePresent;
    private long mCurrentGroup = Constants.GROUP_TYPE_ALL;
    private boolean mComeFromAccoutActivity;

    /**
     * 手指滑动距离多少个像素点的距离，才隐藏bar
     */
    private static int sHideThreshold;
    /**
     * 记录手指滑动的距离
     */
    private int mScrolledDistance = 0;
    /**
     * 记录bar是否显示或者隐藏
     */
    private boolean mControlsVisible = true;
    private onButtonBarListener mOnBottonBarListener;

    @Override
    public void refreshData() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_timeline;
    }

    @Override
    public void initCreateView(Bundle state) {
        Timber.i("开始创建时间线");
        mHomePresent = new HomeFragmentPresentImp(this);
        // mComeFromAccoutActivity = getArguments().getBoolean("comeFromAccoutActivity", false);
        sHideThreshold = DensityUtil.dp2px(context, 20);
        RxView.clicks(refreshTip).subscribe(aVoid -> {
            showLoadingIcon();
        });
        initRecyclerView();
        initRefreshLayout();
        mSwipeRefreshLayout.post(() -> {
            try {
                if (mComeFromAccoutActivity) {
                    mHomePresent.firstLoadData(context, true);
                } else {
                    mHomePresent.firstLoadData(context, activity.getIntent().getBooleanExtra("fisrtstart", false));
                }
            } catch (AVException a) {
                Timber.e(a.getMessage());
            }
        });
    }

    public TimelineFragment() {
        Timber.i("走的是默认函数");
    }

    public void initRecyclerView() {
        mDatas = new ArrayList<>();
        mAdapter = new TimelineAdapter(mDatas, context) {
            @Override
            public void arrowClick(AVStatus status, int position, Bitmap bitmap) {
                TimelineArrowWindow arrowDialog = new TimelineArrowWindow(context, mDatas.get(position), mAdapter, position, bitmap);
                arrowDialog.show();
                int width = ScreenUtil.getScreenWidth(context) - DensityUtil.dp2px(context, 80);
                arrowDialog.getWindow().setLayout(width, (ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        };
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        // 真正的adapter
        mRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
    }


    private void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            try {
                mHomePresent.pullToRefreshData(mCurrentGroup, context);
            } catch (AVException e) {
                Timber.e(e.getMessage());
            }
        });
        mSwipeRefreshLayout.setProgressViewOffset(false, DensityUtil.dp2px(context, 10), DensityUtil.dp2px(context, 10 + 65));
    }

    /**
     * 把列表滑动到顶部，refreshDrata为true的话，会同时获取更新的数据
     *
     * @param refreshData
     */
    @Override
    public void scrollToTop(boolean refreshData) {
        mRecyclerView.scrollToPosition(0);
        if (refreshData) {
            mRecyclerView.post(() -> {
                try {
                    mHomePresent.pullToRefreshData(mCurrentGroup, context);
                } catch (AVException e) {
                    Timber.d(e.getMessage());
                }
            });
        }
    }

    @Override
    public void showRecyclerView() {
        if (mSwipeRefreshLayout.getVisibility() != View.VISIBLE) {
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideRecyclerView() {
        if (mSwipeRefreshLayout.getVisibility() != View.GONE) {
            mSwipeRefreshLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showEmptyBackground(String text) {
        if (mEmptyLayout.getVisibility() != View.VISIBLE) {
            mEmptyLayout.setVisibility(View.VISIBLE);
            mErrorMessage.setText(text);
        }
    }

    @Override
    public void hideEmptyBackground() {
        if (mEmptyLayout.getVisibility() != View.GONE) {
            mEmptyLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public void popWindowsDestory() {
       /* if (mPopWindow != null) {
            mPopWindow.onDestory();
        }*/
    }

    @Override
    public void showOrangeToast(final int num) {
        Animation animation = new AlphaAnimation(0.5f, 1.0f);
        animation.setDuration(2000);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mToastTv.setVisibility(View.VISIBLE);
                mToastTv.setText(num + "条新微博");

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mToastTv.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mToastBg.startAnimation(animation);
    }

    @Override
    public void hideOrangeToast() {
        mToastTv.setVisibility(View.GONE);
    }


    @Override
    public void updateListView(List<AVStatus> statuselist) {
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mDatas = statuselist;
        mAdapter.setData(statuselist);
        mHeaderAndFooterRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoadingIcon() {
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
        });
    }

    @Override
    public void hideLoadingIcon() {

        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(false);
        });
    }


    @Override
    public void showLoadFooterView() {
        RecyclerViewStateUtils.setFooterViewState(activity, mRecyclerView, mDatas.size(), LoadingFooter.State.Loading, null);
    }

    @Override
    public void hideFooterView() {
        RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.Normal);
    }

    @Override
    public void showEndFooterView() {
        RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.TheEnd);
    }

    @Override
    public void showErrorFooterView() {
        RecyclerViewStateUtils.setFooterViewState(mRecyclerView, LoadingFooter.State.NetWorkError);
    }

    private static final int SHOW_THRESHOLD = 80;

    public EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);
            if (mDatas != null && mDatas.size() > 0) {
                showLoadFooterView();
                mHomePresent.requestMoreData(mCurrentGroup, context);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            //手指向上滑动
            if (mScrolledDistance > sHideThreshold && mControlsVisible) {
                if (mOnBottonBarListener != null) {
                    // hideTopBar();
                    mOnBottonBarListener.hideButtonBar();
                }
                mControlsVisible = false;
                mScrolledDistance = 0;
            }
            //手指向下滑动
            else if (mScrolledDistance < -SHOW_THRESHOLD && !mControlsVisible) {
                if (mOnBottonBarListener != null) {
                    // showTopBar();
                    mOnBottonBarListener.showButtonBar();
                }
                mControlsVisible = true;
                mScrolledDistance = 0;
            }
            if ((mControlsVisible && dy > 0) || (!mControlsVisible && dy < 0)) {
                mScrolledDistance += dy;
            }
        }
    };

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            // showTopBar();
            mOnBottonBarListener.showButtonBar();
            mControlsVisible = true;
        }
    }

    /**
     * 设置实现
     *
     * @param onBarListener
     */
    public void setOnBarListener(onButtonBarListener onBarListener) {
        this.mOnBottonBarListener = onBarListener;
    }

    /**
     * 因为ButotnBar的布局并不在fragment中，而是在MainActivity中，所有隐藏和显示底部导航栏的工作要交给MainActivity去做
     */
    public interface onButtonBarListener {
        void showButtonBar();

        void hideButtonBar();
    }
}


