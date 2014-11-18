package com.teratotech.dropto;

import android.widget.ImageView;

import com.parse.ParseFile;

import java.util.Date;

/**
 * Created by nurimelia on 11/4/14.
 */
public abstract class Item {

    public String name;
    public String fileW;
    public String date;


    public abstract void setImage(ImageLoader imageLoader, ImageView image);

    public String getImage() {
        return fileW;
    }

    public abstract Date getDate();

    public  String getName() {
        return name;
    }

    public abstract String getId();


}
