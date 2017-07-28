package com.j.enjpery.app.ui.mainactivity;

import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.j.enjpery.R;
import com.j.enjpery.app.base.BaseActivity;
import com.j.enjpery.app.ui.mainactivity.eventbus.NetworkEvent;
import com.j.enjpery.app.ui.mainactivity.mainfragment.LiveFragment;
import com.j.enjpery.app.ui.mainactivity.mainfragment.MessageFragment;
import com.j.enjpery.app.ui.mainactivity.mainfragment.ProfileFragment;
import com.j.enjpery.app.ui.mainactivity.mainfragment.TimelineFragment;
import com.j.enjpery.app.ui.teaminfo.TeamInfoActivity;
import com.j.enjpery.app.util.AppManager;
import com.j.enjpery.app.util.SnackbarUtil;
import com.j.enjpery.core.loginandregister.LoginAndRegister;
import com.jakewharton.rxbinding2.support.v4.view.RxViewPager;
import com.jakewharton.rxbinding2.view.RxView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import it.sephiroth.android.library.bottomnavigation.BadgeProvider;
import it.sephiroth.android.library.bottomnavigation.BottomBehavior;
import it.sephiroth.android.library.bottomnavigation.BottomNavigation;
import timber.log.Timber;

public class MainActivity extends BaseActivity {
    /*@BindView(R.id.fab)
    FloatingActionMenu fabMenu;*/
    @BindView(R.id.bottomNavigation)
    BottomNavigation bottomNavigation;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

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

        /*RxView.clicks(fabMenu.findViewById(R.id.fab_item1)).subscribe(aVoid -> {
            startActivity(new Intent(MainActivity.this, TeamInfoActivity.class));
        });

        RxView.clicks(fabMenu.findViewById(R.id.fab_item2)).subscribe(aVoid -> {
            LoginAndRegister.doLogOut();
            AppManager.AppExit(getApplicationContext());
        });*/

        if (null == savedInstanceState) {
            bottomNavigation.setDefaultSelectedIndex(0);
            ((BottomBehavior) bottomNavigation.getBehavior()).setOnExpandStatusChangeListener(
                    (expanded, animate)-> {
                        // 书写监听事件
                        Toast.makeText(MainActivity.this, "setOnExpandStatusChangeListener 点击", Toast.LENGTH_SHORT).show();
                    });
            bottomNavigation.setOnMenuItemClickListener(new BottomNavigation.OnMenuItemSelectionListener() {
                @Override
                public void onMenuItemSelect(int i, int i1, boolean b) {
                    // i代表该item在R中的值，i1代表第几个item，从0开始计数，viewpager
                    // 也是从0开始计数

                    Timber.i("不要在类上实现接口，要使用匿名类");
                    // Timber.i("MainActivity 点击了第" + i1 + "个item");
                    viewPager.setCurrentItem(i1);
                }

                @Override
                public void onMenuItemReselect(int i, int i1, boolean b) {
                    Timber.i("bottombar的监听器要使用匿名类");
                    // Timber.i("MainActivity 再次点击了第" + i1 + "个item");
                }
            });

            final BadgeProvider provider = bottomNavigation.getBadgeProvider();
            provider.show(R.id.bbn_item3);
            provider.show(R.id.bbn_item4);

            PagerAdapter adapter = new SegmentPageAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);


            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    bottomNavigation.setSelectedIndex(position, true);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }
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

    private class SegmentPageAdapter extends FragmentPagerAdapter {


        private String[] fragments = {MessageFragment.class.getName(), LiveFragment.class.getName(),
                TimelineFragment.class.getName(), ProfileFragment.class.getName()};

        public SegmentPageAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int pos) {
            Log.v("MainActivity", getClass().getName() + "------>call getItem(), pos = " + pos);
            return Fragment.instantiate(getApplicationContext(), fragments[pos]);
        }

        @Override
        public int getCount() {
            return 4;
        }

    }

}
