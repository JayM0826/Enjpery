package com.j.enjpery.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by J on 2017/8/6.
 */

public class Geo implements Parcelable {

    /** 经度坐标 */
    public String longitude;
    /** 维度坐标 */
    public String latitude;
    /** 所在城市的城市代码 */
    public String city;
    /** 所在省份的省份代码 */
    public String province;
    /** 所在城市的城市名称 */
    public String city_name;
    /** 所在省份的省份名称 */
    public String province_name;
    /** 所在的实际地址，可以为空 */
    public String address;
    /** 地址的汉语拼音，不是所有情况都会返回该字段 */
    public String pinyin;
    /** 更多信息，不是所有情况都会返回该字段 */
    public String more;

    public static Geo parse(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }

        Geo geo = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            geo = parse(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return geo;
    }

    public static Geo parse(JSONObject jsonObject) {
        if (null == jsonObject) {
            return null;
        }

        Geo geo = new Geo();
        geo.longitude       = jsonObject.optString("longitude");
        geo.latitude        = jsonObject.optString("latitude");
        geo.city            = jsonObject.optString("city");
        geo.province        = jsonObject.optString("province");
        geo.city_name       = jsonObject.optString("city_name");
        geo.province_name   = jsonObject.optString("province_name");
        geo.address         = jsonObject.optString("address");
        geo.pinyin          = jsonObject.optString("pinyin");
        geo.more            = jsonObject.optString("more");

        return geo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.longitude);
        dest.writeString(this.latitude);
        dest.writeString(this.city);
        dest.writeString(this.province);
        dest.writeString(this.city_name);
        dest.writeString(this.province_name);
        dest.writeString(this.address);
        dest.writeString(this.pinyin);
        dest.writeString(this.more);
    }

    public Geo() {
    }

    protected Geo(Parcel in) {
        this.longitude = in.readString();
        this.latitude = in.readString();
        this.city = in.readString();
        this.province = in.readString();
        this.city_name = in.readString();
        this.province_name = in.readString();
        this.address = in.readString();
        this.pinyin = in.readString();
        this.more = in.readString();
    }

    public static final Creator<Geo> CREATOR = new Creator<Geo>() {
        @Override
        public Geo createFromParcel(Parcel source) {
            return new Geo(source);
        }

        @Override
        public Geo[] newArray(int size) {
            return new Geo[size];
        }
    };
}
