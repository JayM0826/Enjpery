package com.j.enjpery.app.ui.mainactivity.mainfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.j.enjpery.R;
import com.j.enjpery.app.base.BaseFragment;
import com.j.enjpery.app.ui.mainactivity.eventbus.NetworkEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends BaseFragment {




    @Override
    public void fetchData() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkEvent(NetworkEvent networkEvent){

    }

    @Override
    public int getLayoutResId() {
        return R.layout.wxfragment_profile;
    }

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public void initCreateView(Bundle state) {
        // setNeedRegister();
    }

}
