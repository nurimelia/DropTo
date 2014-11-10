package com.teratotech.dropto;

/**
 * Created by nurimelia on 10/13/14.
 */
import android.graphics.Bitmap;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.Date;


@ParseClassName("File")
public class DropTo extends ParseObject {


    private String folderName;
    private String fileW;
    public String imageUrl;
    public String fileName;
    public String fileType;
    public String deviceId;
    public Date date;
    public ParseFile file;
    public ParseGeoPoint location;

    public DropTo() {

    }

    public void imageUrl(String url) {
        this.imageUrl = url;
    }

    public void date(Date expiryDate) {
        this.date = expiryDate;
    }

    public void fileName(String s) {
        this.fileName = s;
    }
    public void fileType(String jpg) {
    }

    public void file(ParseFile parseFile) {
    }

    public void location(ParseGeoPoint pgp) {
    }

    public void deviceId(String id) {
    }

    public String getfileName(){
        return getString("fileName");
    }
    public void setfileName(String filename) {
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


    public String getImage() {
        return fileW;
    }

    public void setImage(String file) {
        this.fileW = file;
    }

    public String getTitleF() {
        return folderName;
    }

    public void setTitleF(String folderName) {
        this.folderName = folderName;
    }

}
