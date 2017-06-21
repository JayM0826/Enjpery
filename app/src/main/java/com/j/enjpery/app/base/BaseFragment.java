package com.j.enjpery.app.base;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by luoyong on 2017/6/3/0003.
 */

public abstract class BaseFragment extends RxFragment {

    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;
    private FragmentActivity activity;

    private View parentView;

    private Unbinder bind;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    public abstract void fetchData();

    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            onVisible();
            isDataInitiated = true;
            return true;
        }
        onInvisible();
        return false;
    }


    public abstract @LayoutRes int getLayoutResId();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        parentView = inflater.inflate(getLayoutResId(), container, false);
        activity = getSupportActivity();
        return parentView;
    }

    public FragmentActivity getSupportActivity() {

        return super.getActivity();
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        bind = ButterKnife.bind(this, view);
        finishCreateView(savedInstanceState);
    }

    public abstract void finishCreateView(Bundle state);

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        bind.unbind();
    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);
        this.activity = (FragmentActivity) activity;
    }

    @Override
    public void onDetach() {

        super.onDetach();
        this.activity = null;
    }

    public ActionBar getSupportActionBar() {

        return getSupportActivity().getActionBar();
    }

    public Context getApplicationContext() {

        return this.activity == null
                ? (getActivity() == null ? null :
                getActivity().getApplicationContext())
                : this.activity.getApplicationContext();
    }

    protected void onVisible() {

        lazyLoad();
    }


    protected void lazyLoad() {}


    protected void onInvisible() {}

    protected void showProgressBar() {}


    protected void hideProgressBar() {}


    protected void initRecyclerView() {}

    /*

    protected void loadData() {}

    protected void initRefreshLayout() {}


    protected void finishTask() {}


    @SuppressWarnings("unchecked")
    public <T extends View> T $(int id) {

        return (T) parentView.findViewById(id);
    }*/
}
