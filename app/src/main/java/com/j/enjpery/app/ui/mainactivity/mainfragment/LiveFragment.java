package com.j.enjpery.app.ui.mainactivity.mainfragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.j.enjpery.R;
import com.j.enjpery.app.base.BaseFragment;
import com.j.enjpery.app.ui.mainactivity.eventbus.NetworkEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveFragment extends BaseFragment {


    @BindView(R.id.networkInfo)
    Button networkInfo;
    Unbinder unbinder;

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
        return R.layout.fragment_live;
    }

    public LiveFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkEvent(NetworkEvent networkEvent){
        networkInfo.setText(networkEvent.getNetworkInfo());
    }

    @Override
    public void initCreateView(Bundle state) {
        // setNeedRegister();
    }
    /*public static LiveFragment newInstance(String title){
        LiveFragment fragment = new LiveFragment();
        Bundle args = new Bundle();
        args.putString("key_fragment_title", title);
        fragment.setArguments(args);
        return fragment;
    }

    private String title;
    private TextView tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString("key_fragment_title");
    }

    @Override
    public void onResume() {
        super.onResume();
    }




    @Override
    public void fetchData() {
        tv.setText(title);
        *//** * 在这里请求网络。 *//*
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_live;
    }*/

}
