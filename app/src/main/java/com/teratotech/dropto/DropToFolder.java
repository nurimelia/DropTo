package com.teratotech.dropto;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.List;

/**
 * Created by nurimelia on 10/23/14.
 */
@ParseClassName("Folder")
public class DropToFolder extends ParseObject {
    public List<DropTo> fileList;
    public String name;
    public String id;
    public String code;
    public String deviceId;

    public DropToFolder() {

    }

    public void name(String s) {
        this.name = s;
    }

    public void code(String s) {
        this.code = s;
    }

    public void deviceId(String id) {
    }

    public String getname() {
        return getString("folderName");
    }

    public void setname(String foldername) {
        put("folderName", foldername);

    }

    public String getCode() {
        return getString("code");
    }
    public void setCode(String code){
        put("code", code);
    }
    public String getDeviceid(){
        return getString("deviceId");
    }
    public void setDeviceId(String deviceId){
        put("deviceId", deviceId);
    }

}

