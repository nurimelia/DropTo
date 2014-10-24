package com.teratotech.dropto;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by nurimelia on 10/23/14.
 */
@ParseClassName("Folder")
public class DropToFolder extends ParseObject {

    public DropToFolder() {

    }

    public String getTitleF(){
        return getString("folderName");
    }
    public void setTitleF(String foldername){
        put("folderName", foldername);
    }

    public String getCode(){
        return getString("code");
    }
    public void setCode(String code){
        put("code", code);
    }
}

