package com.j.enjpery.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.avos.avoscloud.AVStatus;

import java.util.ArrayList;

/**
 * Created by J on 2017/8/6.
 */

public class Favorite implements Parcelable {

    /**
     * 我喜欢的微博信息
     */
    public AVStatus status;
    /**
     * 我喜欢的微博的 Tag 信息
     */
    public ArrayList<Tag> tags;
    /**
     * 创建我喜欢的微博信息的时间
     */
    public String favorited_time;


    public Favorite() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.status, flags);
        dest.writeList(this.tags);
        dest.writeString(this.favorited_time);
    }

    protected Favorite(Parcel in) {
        this.status = in.readParcelable(AVStatus.class.getClassLoader());
        this.tags = new ArrayList<Tag>();
        in.readList(this.tags, Tag.class.getClassLoader());
        this.favorited_time = in.readString();
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel source) {
            return new Favorite(source);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };
}
