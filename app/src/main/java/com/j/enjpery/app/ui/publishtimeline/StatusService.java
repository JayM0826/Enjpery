package com.j.enjpery.app.ui.publishtimeline;

import android.content.Context;
import android.graphics.Bitmap;

import com.avos.avoscloud.AVACL;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVStatus;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;
import com.j.enjpery.app.global.EnjperyApplication;
import com.j.enjpery.app.ui.mainactivity.eventbus.ShowProgressBarEvent;
import com.j.enjpery.app.util.SnackbarUtil;
import com.j.enjpery.app.util.ToastUtil;
import com.lzy.imagepicker.bean.ImageItem;

import org.greenrobot.eventbus.EventBus;
import org.intellij.lang.annotations.Flow;
import org.reactivestreams.Publisher;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import top.zibin.luban.Luban;


/**
 * Created by J on 2017/8/14.
 */

public class StatusService {

    private static List<String> fileUrlList = new ArrayList<>();

    private static int count = 0;

    public static boolean filterException(Context ctx, Exception e) {
        if (e != null) {
            ToastUtil.showShort(ctx, e.getMessage());
            return false;
        } else {
            return true;
        }
    }

    public static void sendStatus(final String text, List<ImageItem> imageItemList, final SaveCallback saveCallback) {
        if (imageItemList != null && !imageItemList.isEmpty()) {

            EnjperyApplication.getInstance().compressImage(imageItemList, file -> {
                updateImage(file);
            });
            long startTime = System.currentTimeMillis();
            long lastTime, temp = 0;
            while (fileUrlList.size() != imageItemList.size()) {
                // 死循环等着上传图片完成
                lastTime = System.currentTimeMillis();
                if ((temp = lastTime - startTime) > 10000) {
                    break;
                }
            }
            if (temp > 15000) {  // 上传失败
                Timber.d("上传失败");
            } else {
                sendRealStatus(text, fileUrlList, saveCallback);
            }
        } else {
            sendStatus(text, saveCallback);
        }
    }


    /**
     * 单张图片或者无图片的情况
     *
     * @param text
     * @param saveCallback
     */
    public static void sendStatus(final String text, final SaveCallback saveCallback) {
        AVStatus status = new AVStatus();
        status.setMessage(text);
        AVStatus.sendStatusToFollowersInBackgroud(status, saveCallback);
    }

    /**
     * 多张图片的情况
     *
     * @param text
     * @param urls
     * @param saveCallback
     */
    public static void sendRealStatus(final String text, final List<String> urls, final SaveCallback saveCallback) {
        AVStatus status = new AVStatus();
        status.setMessage(text);

        List<String> urlList = new ArrayList<>();

        new Thread(() -> {
            final CountDownLatch latch = new CountDownLatch(urls.size());
            Flowable.just(urls)
                    .subscribeOn(Schedulers.io())  // 后台传输数据
                    .flatMap(new Function<List<String>, Publisher<String>>() {
                        @Override
                        public Publisher<String> apply(List<String> strings) throws Exception {
                            return Flowable.fromIterable(strings);
                        }
                    })
                    .map(new Function<String, File>() {
                        @Override
                        public File apply(@NonNull String file) throws Exception {
                            return new File(file);
                        }
                    })
                    .subscribe(file -> {
                        Timber.i("压缩名字" + file.getAbsolutePath());
                        AVFile avFile = AVFile.withAbsoluteLocalPath(file.getName(), file.getAbsolutePath());
                        avFile.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) { // 上传成功
                                    latch.countDown();
                                    if (urlList != null){
                                        urlList.add(avFile.getUrl());
                                    }
                                }else {
                                    // 目前不做操作
                                }
                            }
                        });
                    });
            try {
                latch.await();
                Timber.i("所有图片上传完毕");
                postStatus(status ,urlList, saveCallback);

            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                Timber.e("线程异常");
            }
        }).start();


    }

    private static void postStatus(AVStatus status, List<String> urlList, final SaveCallback saveCallback) {
        status.put("multiImages", urlList);

        AVStatus.sendStatusToFollowersInBackgroud(status, saveCallback);
    }

    /**
     * 多图
     *
     * @param text
     * @param urls
     * @param saveCallback
     */
    public static void sendStatus(final String text, final String[] urls, final SaveCallback saveCallback) {
        List<String> imageList = Arrays.asList(urls);
        sendRealStatus(text, imageList, saveCallback);
    }

    private static void updateImage(File file) {
        try {
            AVFile fileInfo = AVFile.withAbsoluteLocalPath(file.getName(), file.getAbsolutePath());
            fileInfo.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        String temp = fileInfo.getUrl();
                        fileUrlList.add(temp);
                    } else {
                        Timber.e("上传失败");
                    }
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Timber.e("文件未找到");
        }
    }
}
