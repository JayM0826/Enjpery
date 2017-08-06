package com.j.enjpery.app.ui.customview;

import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;
import android.view.View;

/**
 * Created by J on 2017/8/6.
 */

public abstract class ClickableImageSpan extends ImageSpan {
    public ClickableImageSpan(Drawable b, int verticalAlignment) {
        super(b, verticalAlignment);
    }

    public abstract void onClick(View view);
}