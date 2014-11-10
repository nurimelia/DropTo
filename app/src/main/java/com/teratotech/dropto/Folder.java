package com.teratotech.dropto;

import android.widget.ImageView;

import java.util.Date;

/**
 * Created by nurimelia on 11/4/14.
 */
public class Folder extends Item{
    public DropTo dropto;
    @Override
    public void setImage(ImageLoader imageLoader, ImageView image) {
        image.setImageResource(R.drawable.seedericon);
    }
    @Override
    public String getImage(){
        return null;
    }

    @Override
    public Date getDate() {

        return null;
    }

    //@Override
  //  public String getPhotoFileW() {
      //  return null;
  //  }
}
