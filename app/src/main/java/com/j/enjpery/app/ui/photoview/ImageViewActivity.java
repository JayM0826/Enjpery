package com.j.enjpery.app.ui.photoview;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.j.enjpery.R;
import com.j.enjpery.app.base.BaseActivity;
import com.j.enjpery.app.ui.customview.SaveImageDialog;
import com.j.enjpery.app.ui.photoview.animation.ZoomOutPageTransformer;
import com.j.enjpery.app.util.StatusBarUtils;
import com.jakewharton.rxbinding2.support.v4.view.RxViewPager;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageViewActivity extends BaseActivity implements ViewPagerAdapter.OnSingleTagListener {

    @BindView(R.id.viewpagerId)
    ImageDetailViewPager viewPager;
    @BindView(R.id.imageTopBar)
    ImageDetailTopBar imageTopBar;
    private ArrayList<String> mDatas;
    private int mPosition;
    private int mImgNum;
    private ViewPagerAdapter mAdapter;
    private PhotoViewAttacher.OnPhotoTapListener mPhotoTapListener = (view, v, v1) -> finish();

    @Override
    public int getLayoutId() {
        return R.layout.home_weiboitem_imagedetails;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mDatas = this.getIntent().getStringArrayListExtra("imageUrls");
        mPosition = getIntent().getIntExtra("image_position", 0);
        mImgNum = mDatas.size();
        mAdapter = new ViewPagerAdapter(mDatas, this);
        mAdapter.setOnSingleTagListener(this);
        viewPager.setAdapter(mAdapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        if (mImgNum == 1) {
            imageTopBar.setPageNumVisible(View.GONE);
        } else {
            imageTopBar.setPageNum((mPosition + 1) + "/" + mImgNum);
        }
        viewPager.setCurrentItem(mPosition);
        RxViewPager.pageSelections(viewPager).compose(bindToLifecycle())
                .subscribe(aVoid -> {
                    imageTopBar.setPageNum((viewPager.getCurrentItem() + 1) + "/" + mImgNum);
                });
        RxView.clicks(imageTopBar).compose(bindToLifecycle())
                .subscribe(aVoid -> {
                    SaveImageDialog.showDialog(mDatas.get(viewPager.getCurrentItem()), context);
                });

        //只有小米，或者魅族手机，或者6.0的手机，才适配状态栏
        if (Build.MANUFACTURER.equalsIgnoreCase("xiaomi") || Build.MANUFACTURER.equalsIgnoreCase("meizu") || Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            StatusBarUtils.from(this)
                    .setTransparentStatusbar(true)
                    .setStatusBarColor(getResources().getColor(R.color.black))
                    .process(this);
        }
    }


    @Override
    public void onTag() {
        finish();
    }

}