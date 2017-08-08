package com.j.enjpery.app.ui.mainactivity.mainfragment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.j.enjpery.R;

/**
 * Created by J on 2017/8/8.
 */

public class HomeHeadView extends RelativeLayout {

    public HomeHeadView(Context context) {
        super(context);
        init(context);
    }

    public HomeHeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HomeHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        inflate(context, R.layout.headview_homefragment, this);
    }
}
