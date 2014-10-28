package com.teratotech.dropto;

/**
 * Created by nurimelia on 10/13/14.
 */
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import java.util.Date;


@ParseClassName("File")
public class DropTo extends ParseObject {

    public DropTo() {

    }
    public String getTitle(){
        return getString("fileName");
    }
    public void setTitle(String filename) {
        put("fileName", filename);
    }
        public String getDeviceid(){
            return getString("deviceId");
        }
        public void setDeviceId(String deviceId){
            put("deviceId", deviceId);
        }


    public Date getRating(){
        return getDate("expiryDate");
    }
    public void setRating(Date expiryDate){
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


}
