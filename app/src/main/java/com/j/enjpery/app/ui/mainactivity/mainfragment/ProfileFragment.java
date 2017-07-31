package com.j.enjpery.app.ui.mainactivity.mainfragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.j.enjpery.R;
import com.j.enjpery.app.base.BaseFragment;
import com.j.enjpery.app.ui.mainactivity.eventbus.FragmentVisibleEvent;
import com.j.enjpery.app.ui.mainactivity.eventbus.NetworkEvent;
import com.j.enjpery.app.ui.userinfo.UserInfoActivity;
import com.j.enjpery.app.util.SnackbarUtil;
import com.j.enjpery.model.User;
import com.jakewharton.rxbinding2.view.RxView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment {

    private boolean firstVisible = true;
    @BindView(R.id.head)
    ImageView headImage;

    @BindView(R.id.userLayout)
    RelativeLayout userLayout;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setNeedRegister();
    }

    /**
     * 默认只运行一次，除非强制刷新
     */
    @Override
    public void fetchData() {
        Timber.i("fetchData 该UI可见");
        firstVisible = false;
        // Glide默认使用内存缓存和磁盘缓存
        // 只会请求一次网络,里面的map都是本地的
        Glide.with(getActivity()).load(((AVFile)AVUser.getCurrentUser().get("headImage")).getUrl()).into(headImage);
    }

    @Override
    protected void onInvisible() {
        super.onInvisible();
        if (isVisibleToUser == true  && !firstVisible){
            Timber.i("onInvisible 该UI其实是可见的,进行加载数据");
            prepareFetchData(true);
        }else{
            Timber.i("onInvisible 该UI不可见");
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.wxfragment_profile;
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void initCreateView(Bundle state) {
        RxView.clicks(userLayout)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(aVoid -> {
            Intent intent = new Intent(getActivity(), UserInfoActivity.class);
            startActivity(intent);
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(FragmentVisibleEvent fragmentVisibleEvent) {
        // networkInfo.setText(networkEvent.getNetworkInfo());
        Timber.i("从UserInfo页面回来强制刷新页面");
        prepareFetchData(true);
    }

}
