package com.j.enjpery.app.ui.mainactivity.mainfragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.j.enjpery.R;
import com.j.enjpery.app.base.BaseFragment;
import com.j.enjpery.app.ui.mainactivity.eventbus.NetworkEvent;
import com.jakewharton.rxbinding2.view.RxView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveFragment extends BaseFragment {

    @Override
    public void fetchData() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setNeedRegister();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.wxfragment_profile;
    }

    public LiveFragment() {
        // Required empty public constructor
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkEvent(NetworkEvent networkEvent) {
        // networkInfo.setText(networkEvent.getNetworkInfo());
        Toast.makeText(getContext(), networkEvent.getNetworkInfo(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initCreateView(Bundle state) {
        // setNeedRegister();
    }



}
