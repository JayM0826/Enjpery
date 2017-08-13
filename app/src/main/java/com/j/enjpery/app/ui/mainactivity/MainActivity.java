package com.j.enjpery.app.ui.mainactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.j.enjpery.R;
import com.j.enjpery.app.base.BaseActivity;
import com.j.enjpery.app.ui.mainactivity.mainfragment.LiveFragment;
import com.j.enjpery.app.ui.mainactivity.mainfragment.MessageFragment;
import com.j.enjpery.app.ui.mainactivity.mainfragment.ProfileFragment;
import com.j.enjpery.app.ui.mainactivity.mainfragment.TimelineFragment;
import com.j.enjpery.app.ui.publishtimeline.PublishStatusActivity;
import com.j.enjpery.app.util.SnackbarUtil;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.sephiroth.android.library.bottomnavigation.BadgeProvider;
import it.sephiroth.android.library.bottomnavigation.BottomBehavior;
import it.sephiroth.android.library.bottomnavigation.BottomNavigation;
import timber.log.Timber;

import static com.j.enjpery.app.util.Constants.POST_SERVICE_CREATE_WEIBO;

public class MainActivity extends BaseActivity {
    @BindView(R.id.bottomNavigation)
    BottomNavigation bottomNavigation;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.fab1_item1)
    FloatingActionButton fab1Item1;
    @BindView(R.id.fab1_item2)
    FloatingActionButton fab1Item2;
    @BindView(R.id.fab1)
    FloatingActionMenu fab1;
    @BindView(R.id.fab2_item1)
    FloatingActionButton fab2Item1;
    @BindView(R.id.fab2_item2)
    FloatingActionButton fab2Item2;
    @BindView(R.id.fab2)
    FloatingActionMenu fab2;
    @BindView(R.id.fab3_item1)
    FloatingActionButton fab3Item1;
    @BindView(R.id.fab3_item2)
    FloatingActionButton fab3Item2;
    @BindView(R.id.fab3)
    FloatingActionMenu fab3;
    @BindView(R.id.fab4_item1)
    FloatingActionButton fab4Item1;
    @BindView(R.id.fab4_item2)
    FloatingActionButton fab4Item2;
    @BindView(R.id.fab4)
    FloatingActionMenu fab4;


    // private SystemBarTintManager mSystemBarTint;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        // 注册EventBus,发布者不需要注册，只要注册就要订阅
        // setNeedRegister();

        if (null == savedInstanceState) {
            bottomNavigation.setDefaultSelectedIndex(0);
            ((BottomBehavior) bottomNavigation.getBehavior()).setOnExpandStatusChangeListener(
                    (expanded, animate) -> {
                        // 书写监听事件
                        Toast.makeText(MainActivity.this, "setOnExpandStatusChangeListener 点击", Toast.LENGTH_SHORT).show();
                    });
            bottomNavigation.setOnMenuItemClickListener(new BottomNavigation.OnMenuItemSelectionListener() {
                @Override
                public void onMenuItemSelect(int i, int i1, boolean b) {
                    // i代表该item在R中的值，i1代表第几个item，从0开始计数，viewpager
                    // 也是从0开始计数

                    // Timber.i("不要在类上实现接口，要使用匿名类");
                    Timber.i("MainActivity 点击了第" + i1 + "个item");
                    // 切换fragment时关闭平滑滚动，并不是去掉动画
                    viewPager.setCurrentItem(i1, false);
                    animateFab(i1);
                }

                @Override
                public void onMenuItemReselect(int i, int i1, boolean b) {
                    Timber.i("bottombar的监听器要使用匿名类");
                    // Timber.i("MainActivity 再次点击了第" + i1 + "个item");
                }
            });
            animateFab(0);
            final BadgeProvider provider = bottomNavigation.getBadgeProvider();
            provider.show(R.id.bbn_item3);
            provider.show(R.id.bbn_item4);

            PagerAdapter adapter = new SegmentPageAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);
           /* 参数	解释
            reverseDrawingOrder	boolean值，true表示提供的PageTransformer画view时是倒序，false则是正序
            transformer	将修改每一页动画属性的PageTransformer*/
            // viewPager.setPageTransformer(true, new AccordionTransformer());
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    bottomNavigation.setSelectedIndex(position, true);
                    animateFab(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });

            initFabs();
        }
    }

    private void initFabs() {
        RxView.clicks(fab1.findViewById(R.id.fab1_item1))
                .compose(bindToLifecycle())
                .subscribe(Avoid->{
                    SnackbarUtil.show(bottomNavigation, "点击了添加");
                });
        RxView.clicks(fab1.findViewById(R.id.fab1_item2))
                .compose(bindToLifecycle())
                .subscribe(Avoid->{
                    SnackbarUtil.show(bottomNavigation, "点击了搜索");
                });
        RxView.clicks(fab3.findViewById(R.id.fab3_item1))
                .compose(bindToLifecycle())
                .subscribe(Avoid->{
                    Intent intent = new Intent(MainActivity.this, PublishStatusActivity.class);
                    intent.putExtra("type",POST_SERVICE_CREATE_WEIBO);
                    startActivity(intent);
                });

        RxView.clicks(fab3.findViewById(R.id.fab3_item2))
                .compose(bindToLifecycle())
                .subscribe(Avoid->{
                    SnackbarUtil.show(bottomNavigation, "点击了搜索");
                });
    }

    public static class FabBehavior extends CoordinatorLayout.Behavior<FloatingActionMenu> {
        public FabBehavior() {
            super();
        }

        public FabBehavior(Context context, AttributeSet attrs) {
            super(context, attrs);
        }


        @Override
        public boolean layoutDependsOn(final CoordinatorLayout parent, final FloatingActionMenu child, final View dependency) {
            if (BottomNavigation.class.isInstance(dependency)) {
                return true;
            } else if (Snackbar.SnackbarLayout.class.isInstance(dependency)) {
                return true;
            }
            return super.layoutDependsOn(parent, child, dependency);
        }

        @Override
        public boolean onDependentViewChanged(
                final CoordinatorLayout parent, final FloatingActionMenu child, final View dependency) {

            final List<View> list = parent.getDependencies(child);
            int bottomMargin = ((ViewGroup.MarginLayoutParams) child.getLayoutParams()).bottomMargin;

            float t = 0;
            boolean result = false;

            for (View dep : list) {
                if (Snackbar.SnackbarLayout.class.isInstance(dep)) {
                    t += dep.getTranslationY() - dep.getHeight();
                    result = true;
                } else if (BottomNavigation.class.isInstance(dep)) {
                    BottomNavigation navigation = (BottomNavigation) dep;
                    t += navigation.getTranslationY() - navigation.getHeight() + bottomMargin;
                    result = true;
                }
            }

            child.setTranslationY(t);
            return result;
        }

        @Override
        public void onDependentViewRemoved(
                final CoordinatorLayout parent, final FloatingActionMenu child, final View dependency) {
            super.onDependentViewRemoved(parent, child, dependency);
        }
    }

    /*public int getNavigationBarHeight() {
        return getSystemBarTint().getConfig().getNavigationBarHeight();
    }

    public SystemBarTintManager getSystemBarTint() {
        if (null == mSystemBarTint) {
            mSystemBarTint = new SystemBarTintManager(this);
        }
        return mSystemBarTint;
    }*/

    /*使用 FragmentPagerAdapter 时，ViewPager 中的所有 Fragment 实例常驻内存，
    当 Fragment 变得不可见时仅仅是视图结构的销毁，即调用了 onDestroyView 方法。
    由于 FragmentPagerAdapter 内存消耗较大，所以适合少量静态页面的场景。

    使用 FragmentStatePagerAdapter 时，当 Fragment 变得不可见，不仅视图层次销毁，
    实例也被销毁，即调用了 onDestroyView 和 onDestroy 方法，
    仅仅保存 Fragment 状态。相比而言， FragmentStatePagerAdapter 内存占用较小，
    所以适合大量动态页面，比如我们常见的新闻列表类应用。*/
    private class SegmentPageAdapter extends FragmentPagerAdapter {
        private String[] fragments = {MessageFragment.class.getName(), LiveFragment.class.getName(),
                TimelineFragment.class.getName(), ProfileFragment.class.getName()};

        public SegmentPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            Timber.v("MainActivity", getClass().getName() + "------>call getItem(), pos = " + pos);
            return Fragment.instantiate(getApplicationContext(), fragments[pos]);
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    }


    //int[] colorIntArray = {android.R.color.holo_purple, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, R.color.bottombar_item4};
    // int[] iconIntArray = {R.drawable.ic_profile_music, R.drawable.ic_profile_note, R.drawable.ic_profile_video, R.drawable.ic_profile_settings};

    private void animateFab(int position) {
        switch (position) {
            case 0:
                if (fab1.isMenuHidden()) {
                    fab1.showMenu(false);
                }
                fab2.hideMenu(false);
                fab3.hideMenu(false);
                fab4.hideMenu(false);
                break;
            case 1:
                if (fab2.isMenuHidden()) {
                    fab2.showMenu(false);
                }
                fab1.hideMenu(false);
                fab3.hideMenu(false);
                fab4.hideMenu(false);
                break;
            case 2:
                if (fab3.isMenuHidden()) {
                    fab3.showMenu(false);
                }
                fab1.hideMenu(false);
                fab2.hideMenu(false);
                fab4.hideMenu(false);
                break;

            case 3:
                if (fab4.isMenuHidden()) {
                    fab4.showMenu(false);
                }
                fab1.hideMenu(false);
                fab2.hideMenu(false);
                fab4.hideMenu(false);
                break;

            default:
                if (fab1.isMenuHidden()) {
                    fab1.showMenu(false);
                }
                fab2.hideMenu(false);
                fab3.hideMenu(false);
                fab4.hideMenu(false);
                break;
        }
    }

}
