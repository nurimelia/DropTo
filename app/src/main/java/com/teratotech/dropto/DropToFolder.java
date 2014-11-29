package com.teratotech.dropto;

import com.parse.ParseClassName;
import com.parse.ParseObject;

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

    public DropToFolder() {
    }

    public void name(String s) {
        this.name = s;
    }

    public void code(String s) {
        this.code = s;
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
    public void setDeviceId(String deviceId){
        put("deviceId", deviceId);
    }

}

