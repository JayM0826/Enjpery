package com.j.enjpery.app.ui.customview;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by J on 2017/7/9.
 */

public class EmojiTextView extends AppCompatTextView {
    private final Context context;

    public EmojiTextView(Context context) {
        super(context, null);
        this.context = context;
    }

    public EmojiTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }
}
