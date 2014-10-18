package com.teratotech.dropto;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;
import android.app.Application;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Add your initialization code here
       // Parse.initialize(this, ZJMejxcTIMNyuWWviiP3eQkELIZ1mZ7dqQVIolpV, ZGejA1DwbSu6cQRoooA4yCBcglC0AYoxEi2ouHNU);

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);
    }

}