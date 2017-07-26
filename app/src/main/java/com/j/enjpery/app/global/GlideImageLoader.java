package com.j.enjpery.app.global;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.j.enjpery.R;
import com.lzy.imagepicker.loader.ImageLoader;

import java.io.File;

/**
 * Created by J on 2017/7/25.
 */

public class GlideImageLoader implements ImageLoader {

    private RequestOptions options = new RequestOptions()
            .centerCrop()
            .placeholder(R.mipmap.default_image)
            .error(R.mipmap.default_image)
            .priority(Priority.HIGH)
            .diskCacheStrategy(DiskCacheStrategy.ALL);

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity)
                .load(Uri.fromFile(new File(path)))
                .apply(options.override(width, height))
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
