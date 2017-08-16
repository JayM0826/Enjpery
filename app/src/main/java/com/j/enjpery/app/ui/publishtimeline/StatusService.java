package com.j.enjpery.app.ui.publishtimeline;

import android.content.Context;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVStatus;
import com.avos.avoscloud.SaveCallback;
import com.j.enjpery.app.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


/**
 * Created by J on 2017/8/14.
 */

public class StatusService {

    public static boolean filterException(Context ctx, Exception e) {
        if (e != null) {
            ToastUtil.showShort(ctx, e.getMessage());
            Timber.i("发表成功后返回异常");
            return false;
        } else {
            Timber.i("发表成功后没有返回异常");
            return true;
        }
    }


    /**
     * 无图片的情况
     *
     * @param text         状态内容
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
                    .flatMap(strings -> {
                        return Flowable.fromIterable(strings);
                    })
                    .map(file -> {
                        return new File(file);
                    })
                    .subscribe(file -> {
                        Timber.i("压缩名字" + file.getAbsolutePath());
                        AVFile avFile = AVFile.withAbsoluteLocalPath(file.getName(), file.getAbsolutePath());
                        avFile.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) { // 上传成功
                                    latch.countDown();
                                    if (urlList != null) {
                                        urlList.add(avFile.getUrl());
                                    }
                                } else {
                                    Timber.i(file.getName() + "上传失败");
                                    EventBus.getDefault().post(new PostStatusEvent(false));
                                }
                            }
                        });
                    });
            try {
                latch.await();
                Timber.i("所有图片上传完毕");
                postStatus(status, urlList, saveCallback);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
                Timber.e("线程异常");
            }finally {
                Thread.currentThread().interrupt();
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
     * @param text         状态内容
     * @param urls
     * @param saveCallback
     */
    public static void sendStatus(final String text, final String[] urls, final SaveCallback saveCallback) {
        List<String> imageList = Arrays.asList(urls);
        sendRealStatus(text, imageList, saveCallback);
    }
}
