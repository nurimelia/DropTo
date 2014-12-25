package com.teratotech.dropto;

import android.widget.ImageView;

import java.util.Date;

/**
 * Created by nurimelia on 11/4/14.
 */
public abstract class Item {

    public String name;
    public String fileW;
    public String date;
    public String code;
    public String deviceId;

    public abstract void setImage(ImageLoader imageLoader, ImageView image);

    public String getImage() {
        return fileW;
    }

    public abstract Date getDate();

    public  String getName() {
        return name;
    }

    public  String getDeviceId() {
        return deviceId;
    }

    public abstract String getId();

    public abstract String getCode();


}
