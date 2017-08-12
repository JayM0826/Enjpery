package com.j.enjpery.app.ui.mainactivity.mainfragment;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVStatus;
import com.j.enjpery.R;
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

import timber.log.Timber;


/**
 * A simple {@link Fragment} subclass.
 */
/*public class TimelineFragment extends BaseFragment  {

    private boolean firstVisible = true;
    @BindView(R.id.timelineRecyclerView)
    RecyclerView timelineRecyclerView;
    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout pullToRefresh;
    @BindView(R.id.fabSearch)
    FloatingActionButton fabSearch;
    @BindView(R.id.fabNew)
    FloatingActionButton fabNew;
    @BindView(R.id.fabMenu)
    FloatingActionMenu fabMenu;
    @BindView(R.id.refreshTip)
    TextView refreshTip;

    private ArrayList<Status> mDatas;
    public Context mContext;
    public Activity mActivity;
    public TimelineAdapter mAdapter;



    */

/**
 * 默认只运行一次，除非强制刷新
 *//*
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



        RxView.clicks(fabSearch).subscribe(aVoid -> {
            SnackbarUtil.show(fabMenu, "点击了搜索按钮");
        });

        RxView.clicks(fabMenu.findViewById(R.id.fabNew)).subscribe(aVoid -> {
            SnackbarUtil.show(fabMenu, "点击了新建按钮");
        });

        initRecyclerView();
        initRefreshLayout();

    }

    @Override
    protected void initRecyclerView() {
        super.initRecyclerView();
        mDatas = new ArrayList<>();
        mAdapter = new TimelineAdapter(mDatas, mContext) {
            @Override
            public void arrowClick(Status status, int position, Bitmap bitmap) {
                TimelineArrowWindow arrowDialog = new TimelineArrowWindow(mContext, mDatas.get(position), mAdapter, position, mUserNameTextView.getText().toString(), bitmap);
                arrowDialog.show();
                int width = ScreenUtil.getScreenWidth(mContext) - DensityUtil.dp2px(mContext, 80);
                arrowDialog.getWindow().setLayout(width, (ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        };
    }

    private void initRefreshLayout() {

        pullToRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        RxSwipeRefreshLayout.refreshes(pullToRefresh)
                .compose(bindToLifecycle())
                .throttleFirst(1, TimeUnit.SECONDS)
                .debounce(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid->{
                    prepareFetchData(true);
                });

        pullToRefresh.setProgressViewOffset(false, DensityUtil.dp2px(mContext, 10), DensityUtil.dp2px(mContext, 10 + 65));
    }
}*/
public class TimelineFragment extends Fragment implements HomeFragmentView {

    private ArrayList<AVStatus> mDatas;
    public Context mContext;
    public Activity mActivity;
    public View mView;

    public RecyclerView mRecyclerView;
    public TextView mErrorMessage;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    public TimelineAdapter mAdapter;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    private HomeFragmentPresent mHomePresent;
    private long mCurrentGroup = Constants.GROUP_TYPE_ALL;
    private LinearLayout mEmptyLayout;
    private boolean mComeFromAccoutActivity;
    private TextView refreshTip;

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

    private TextView mToastTv;
    private RelativeLayout mToastBg;

    private onButtonBarListener mOnBottonBarListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        mContext = getContext();
        mHomePresent = new HomeFragmentPresentImp(this);
        // mComeFromAccoutActivity = getArguments().getBoolean("comeFromAccoutActivity", false);
        sHideThreshold = DensityUtil.dp2px(mContext, 20);
        mView = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.weiboRecyclerView);
        mEmptyLayout = (LinearLayout) mView.findViewById(R.id.emptydeault_layout);
        mErrorMessage = (TextView) mView.findViewById(R.id.errorMessage);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_refresh_widget);
        mToastTv = (TextView) mView.findViewById(R.id.toast_msg);
        mToastBg = (RelativeLayout) mView.findViewById(R.id.toast_bg);
        refreshTip = (TextView) mView.findViewById(R.id.refreshTip);
        RxView.clicks(refreshTip).subscribe(aVoid -> {
            showLoadingIcon();
        });
        initRecyclerView();
        initRefreshLayout();
        mSwipeRefreshLayout.post(() -> {
            if (mComeFromAccoutActivity) {
                mHomePresent.firstLoadData(mContext, true);
            } else {
                mHomePresent.firstLoadData(mContext, mActivity.getIntent().getBooleanExtra("fisrtstart", false));
            }
        });
        return mView;
    }

    @Override
    public void onDestroyView() {
        mHomePresent.cancelTimer();
        super.onDestroyView();
    }

    public TimelineFragment() {
        Timber.i("走的是默认函数");
    }

    public void initRecyclerView() {
        mDatas = new ArrayList<>();
        mAdapter = new TimelineAdapter(mDatas, mContext) {
            @Override
            public void arrowClick(AVStatus status, int position, Bitmap bitmap) {
                TimelineArrowWindow arrowDialog = new TimelineArrowWindow(mContext, mDatas.get(position), mAdapter, position, bitmap);
                arrowDialog.show();
                int width = ScreenUtil.getScreenWidth(mContext) - DensityUtil.dp2px(mContext, 80);
                arrowDialog.getWindow().setLayout(width, (ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        };
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        // 真正的adapter
        mRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
    }


    private void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mHomePresent.pullToRefreshData(mCurrentGroup, mContext);
        });
        mSwipeRefreshLayout.setProgressViewOffset(false, DensityUtil.dp2px(mContext, 10), DensityUtil.dp2px(mContext, 10 + 65));
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
                mHomePresent.pullToRefreshData(mCurrentGroup, mContext);
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
    public void updateListView(ArrayList<AVStatus> statuselist) {
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
        RecyclerViewStateUtils.setFooterViewState(mActivity, mRecyclerView, mDatas.size(), LoadingFooter.State.Loading, null);
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
                mHomePresent.requestMoreData(mCurrentGroup, mContext);
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


