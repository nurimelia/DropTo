package com.teratotech.dropto;

import android.widget.ImageView;
import com.parse.ParseFile;
import java.util.Date;

/**
 * Created by nurimelia on 11/4/14.
 */
public class FileItem extends Item{
    public DropTo dropto;

    @Override
    public void setImage(ImageLoader imageLoader, ImageView imageView) {
        if (dropto == null) return;
        ParseFile img = dropto.getParseFile("file");
        if (img == null) return;

        imageLoader.DisplayImage(img.getUrl(), imageView);
    }

    @Override
    public String getImage(){
        return dropto.getString("file");
    }

    @Override
    public Date getDate() {
        return dropto.getDate("expiryDate");
    }

    @Override
    public String getName() {
        return dropto.getString("fileName");
    }

    @Override
    public String getDeviceId() {
        return dropto.getString("deviceId");
    }

    @Override
    public String getId() {
        return dropto.getObjectId();
    }

    @Override
    public String getCode() {
        return null;

    }

}
