/*
 * Copyright (c) 2017.
 *       created by J.
 *  不须放屁。待看天地翻覆。
 */

package com.j.enjpery.app.util;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by luoyong on 2017/6/4/0004.
 */

public final class SnackbarUtil {
    public static void show(View view, String message){
        Snackbar.make(view, message,Snackbar.LENGTH_SHORT).show();
    }
}
