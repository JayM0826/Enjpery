package com.j.enjpery.app.ui.userinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;
import com.j.enjpery.R;
import com.j.enjpery.app.base.BaseActivity;

import butterknife.BindView;

public class EditUserInfoActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private String toolBarTitle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_user_info;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Intent intent = getIntent();
        toolBarTitle = intent.getStringExtra("title");
        toolbar.setTitle(toolBarTitle);
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*Android中可以重写activity的两个方法进行创建菜单：
    onPrepareOptionsMenu（Menu menu），onCreateOptionsMenu。

    两种方法的区别是，前者是每次点击menu键都会重新调用，所以，
    如果菜单需要更新的话，就用此方法。而后者只是在activity创建的时候执行一次。*/

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.edituserinfo_menu, menu);
        return super.onPrepareOptionsMenu(menu);
    }
}
