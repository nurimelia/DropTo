package com.teratotech.dropto;

/**
 * Created by nurimelia on 10/13/14.
 */
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

//import com.teratotech.ParseUser;

import java.util.Date;


@ParseClassName("File")
public class DropTo extends ParseObject {

    public DropTo() {

    }
    public String getTitle(){
        return getString("fileName");
    }
    public void setTitle(String filename){
        put("fileName", filename);
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



}
