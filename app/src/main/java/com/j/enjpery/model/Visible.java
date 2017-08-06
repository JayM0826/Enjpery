package com.j.enjpery.model;

/**
 * Created by J on 2017/8/6.
 */

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Status可见性结构体。
 */
public class Visible implements Parcelable {

    public static final int VISIBLE_NORMAL = 0;
    public static final int VISIBLE_PRIVACY = 1;
    public static final int VISIBLE_GROUPED = 2;
    public static final int VISIBLE_FRIEND = 3;

    /**
     * type 取值，0：普通微博，1：私密微博，3：指定分组微博，4：密友微博
     */
    public int type;
    /**
     * 分组的组号
     */
    public int list_id;

    public static Visible parse(JSONObject jsonObject) {
        if (null == jsonObject) {
            return null;
        }

        Visible visible = new Visible();
        visible.type = jsonObject.optInt("type", 0);
        visible.list_id = jsonObject.optInt("list_id", 0);

        return visible;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeInt(this.list_id);
    }

    public Visible() {
    }

    protected Visible(Parcel in) {
        this.type = in.readInt();
        this.list_id = in.readInt();
    }

    public static final Creator<Visible> CREATOR = new Creator<Visible>() {
        @Override
        public Visible createFromParcel(Parcel source) {
            return new Visible(source);
        }

        @Override
        public Visible[] newArray(int size) {
            return new Visible[size];
        }
    };
}