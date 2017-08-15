package com.j.enjpery.app.ui.publishtimeline;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.j.enjpery.R;

/**
 * Created by J on 2017/8/13.
 */

public class BaseSwipeActivity extends SwipeBackActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSwipeBackLayout().setSwipeMode(SwipeBackLayout.FULL_SCREEN_LEFT);
        getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        getSwipeBackLayout().setSensitivity(BaseSwipeActivity.this, 0.3f);
    }
    public ProgressDialog progressDialog;
    public void showProgressDialog(String message) {
        if (progressDialog == null){
            progressDialog = new ProgressDialog(this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(message);
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    public void showProgressDialog(int id) {
        showProgressDialog(getResources().getString(id));
    }

    public void hideProgressDialog() {
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }
}
