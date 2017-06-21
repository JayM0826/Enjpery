package com.j.enjpery.app.ui.mainactivity.mainfragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.j.enjpery.R;
import com.j.enjpery.app.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveFragment extends BaseFragment {
    public static LiveFragment newInstance(String title){
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
    public void finishCreateView(Bundle state) {

    }

    @Override
    public void fetchData() {
        tv.setText(title);
        /** * 在这里请求网络。 */
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_live;
    }

}
