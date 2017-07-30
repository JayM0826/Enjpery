package com.j.enjpery.app.util;

import android.os.Handler;

import com.avos.avoscloud.AVException;

import java.io.File;

/**
 * Created by J on 2017/7/30.
 */

/**
 * 对压缩完后的图片进行回掉
 */
public interface CompressCompletedCB{
    public abstract void done(File file);
}
