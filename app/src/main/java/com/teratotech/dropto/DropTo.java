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

    private String fileW;
    public Date date;
    public ParseFile file;
    public ParseGeoPoint location;

    public DropTo() {
    }

    public void setfileName(String filename) {
        put("fileName", filename);
    }

    public void setFileType(String fileType) {
        put("fileType", fileType);
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

    public void setFile(ParseFile file) {
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

    public void setFolderId(String folderId) {
        put("folderId", DropToFolder.createWithoutData("Folder", folderId));
    }

}
