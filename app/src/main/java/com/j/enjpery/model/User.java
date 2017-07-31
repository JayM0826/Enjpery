/*
 * Copyright (c) 2017.
 *       created by J.
 *  不须放屁。待看天地翻覆。
 */

package com.j.enjpery.model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.FollowCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.j.enjpery.app.ui.mainactivity.eventbus.UpdateImageEvent;
import com.j.enjpery.app.util.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

/**
 * Created by luoyong on 2017/6/7/0007.
 */

public class User{

    


    /*public static final String AVATAR = "avatar";
    private String avatarUrl;
    public static final String LOCATION = "location";
    public static final String INSTALLATION = "installation";

    public static String getCurrentUserId () {
        User currentUser = getCurrentUser(User.class);
        return (null != currentUser ? currentUser.getObjectId() : null);
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() {
        if (!StringUtils.isBlank(avatarUrl)){
            return avatarUrl;
        }else{
            AVFile avatar = getAVFile(AVATAR);
            if (avatar != null) {
                setAvatarUrl(avatar.getUrl());
                return avatar.getUrl();
            }else return null;
        }
    }


    public void saveAvatar(File filePath) {
        try {
            final AVFile file = AVFile.withAbsoluteLocalPath(filePath.getName(), filePath.getAbsolutePath());
            User.getCurrentUser().put(AVATAR, file);
            User.getCurrentUser().saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (null == e) {
                        AVFile avatar = (AVFile) getCurrentUser().get(AVATAR);
                        setAvatarUrl(avatar.getUrl());
                        Timber.i("上传成功" + User.getCurrentUser().getAvatarUrl());
                        EventBus.getDefault().post(new UpdateImageEvent(true));
                    } else {
                        Timber.e("上传失败" + e.getCode() + "   " + e.getMessage());
                        EventBus.getDefault().post(new UpdateImageEvent(e, false));
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User getCurrentUser() {
        return getCurrentUser(User.class);
    }

    public void updateUserInfo() {
        AVInstallation installation = AVInstallation.getCurrentInstallation();
        if (installation != null) {
            put(INSTALLATION, installation);
            saveInBackground();
        }
    }

    public AVGeoPoint getGeoPoint() {
        return getAVGeoPoint(LOCATION);
    }

    public void setGeoPoint(AVGeoPoint point) {
        put(LOCATION, point);
    }

    public static void signUpByNameAndPwd(String name, String password, SignUpCallback callback) {
        User user = new User();
        user.setUsername(name);
        user.setPassword(password);
        user.signUpInBackground(callback);
    }

    public void removeFriend(String friendId, final SaveCallback saveCallback) {
        unfollowInBackground(friendId, new FollowCallback() {
            @Override
            public void done(AVObject object, AVException e) {
                if (saveCallback != null) {
                    saveCallback.done(e);
                }
            }
        });
    }

    public void findFriendsWithCachePolicy(AVQuery.CachePolicy cachePolicy, FindCallback<User>
            findCallback) {
        AVQuery<User> q = null;
        try {
            q = followeeQuery(User.class);
        } catch (Exception e) {
        }
        q.setCachePolicy(cachePolicy);
        q.setMaxCacheAge(TimeUnit.MINUTES.toMillis(1));
        q.findInBackground(findCallback);
    }*/
}
