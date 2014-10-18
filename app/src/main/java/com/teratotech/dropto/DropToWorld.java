package com.teratotech.dropto;

/**
 * Created by nurimelia on 10/17/14.
 */
public class DropToWorld {

    private String fileName;
    private String expiryDate;
    private String file;


    public String getTitle() {
        return fileName;
    }

    public void setTitle(String fileName) {
        this.fileName = fileName;
    }

    public String getRating() {
        return expiryDate;
    }

    public void setRating(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getPhotoFile() {
        return file;
    }

    public void setPhotoFile(String file) {
        this.file = file;
    }
}
