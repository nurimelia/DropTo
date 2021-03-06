package com.teratotech.dropto;

import com.parse.ParseACL;
import com.parse.ParseUser;
import android.app.Application;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // If you would like all objects to be private by default, remove this line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);
    }

}