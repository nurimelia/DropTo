package com.teratotech.dropto;

/**
 * Created by nurimelia on 10/13/14.
 */
import android.graphics.Bitmap;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import java.util.Date;


@ParseClassName("File")
public class DropTo extends ParseObject {

private String folderName;
    private String fileW;
    public DropTo() {

    }
    public String getTitle(){
        return getString("fileName");
    }
    public void setTitle(String filename) {
        put("fileName", filename);
    }

    public String getFileType(){
        return getString("fileType");
    }
    public void setFileType(String fileType) {
        put("fileType", fileType);
    }

    public String getDeviceid(){
            return getString("deviceId");
        }
    public void setDeviceId(String deviceId){
            put("deviceId", deviceId);
        }


    public Date getDate(){
        return getDate("expiryDate");
    }
    public void setDate(Date expiryDate){
        put("expiryDate", expiryDate);
    }

    public ParseFile getPhotoFile() {
        return getParseFile("file");
    }

    public void setPhotoFile(ParseFile file) {
        put("file", file);
    }

    public ParseGeoPoint getLocation(){
        return getParseGeoPoint("location");
    }
    public void setLocation(ParseGeoPoint location){
        put("location", location);
    }


    public String getPhotoFileW() {
        return fileW;
    }

    public void setPhotoFileW(String file) {
        this.fileW = file;
    }

    public String getTitleF() {
        return folderName;
    }

    public void setTitleF(String folderName) {
        this.folderName = folderName;
    }

}
