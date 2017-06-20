package com.j.enjpery.app.ui.mainactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.j.enjpery.R;
import com.j.enjpery.app.base.BaseActivity;
import com.j.enjpery.app.ui.teaminfo.TeamInfoActivity;
import com.j.enjpery.app.util.AppManager;
import com.j.enjpery.app.util.SnackbarUtil;
import com.j.enjpery.core.loginandregister.LoginAndRegister;
import com.jakewharton.rxbinding2.view.RxView;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.sephiroth.android.library.bottomnavigation.BadgeProvider;
import it.sephiroth.android.library.bottomnavigation.BottomBehavior;
import it.sephiroth.android.library.bottomnavigation.BottomNavigation;
import timber.log.Timber;

import static android.util.Log.INFO;
import static it.sephiroth.android.library.bottomnavigation.MiscUtils.log;

public class MainActivity extends BaseActivity implements BottomNavigation.OnMenuItemSelectionListener {
    @BindView(R.id.message)
    TextView message;
    @BindView(R.id.fab)
    FloatingActionMenu fabMenu;
    @BindView(R.id.BottomNavigation)
    BottomNavigation BottomNavigation;

    private SystemBarTintManager mSystemBarTint;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        RxView.clicks(fabMenu.findViewById(R.id.fab_item1)).subscribe(aVoid->{
            SnackbarUtil.show(fabMenu, "上面的SnackBar");
        });

        RxView.clicks(fabMenu.findViewById(R.id.fab_item2)).subscribe(aVoid->{
            LoginAndRegister.doLogOut();
            AppManager.AppExit(getApplicationContext());
        });


        RxView.clicks(message).subscribe(aVoid -> {
            startActivity(new Intent(MainActivity.this, TeamInfoActivity.class));
        });


        if (null == savedInstanceState) {
            BottomNavigation.setDefaultSelectedIndex(0);
            ((BottomBehavior) BottomNavigation.getBehavior()).setOnExpandStatusChangeListener(
                    new BottomBehavior.OnExpandStatusChangeListener() {
                        @Override
                        public void onExpandStatusChanged(final boolean expanded, final boolean animate) {

                        }
                    });

            final BadgeProvider provider = BottomNavigation.getBadgeProvider();
            provider.show(R.id.bbn_item3);
            provider.show(R.id.bbn_item4);
        }
    }

    @Override
    public void initToolBar() {

    }


    @Override
    public void onMenuItemSelect(int i, int i1, boolean b) {
        System.out.println("为啥子出不来呢");

    }

    // @Override
    public void onMenuItemReselect(int i, int i1, boolean b) {
       System.out.println("为啥子出不来呢");
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

    public int getNavigationBarHeight() {
        return getSystemBarTint().getConfig().getNavigationBarHeight();
    }

    public SystemBarTintManager getSystemBarTint() {
        if (null == mSystemBarTint) {
            mSystemBarTint = new SystemBarTintManager(this);
        }
        return mSystemBarTint;
    }

}
