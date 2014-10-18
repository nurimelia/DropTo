package com.teratotech.dropto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageView;

public class SingleItemView extends Activity {
    // Declare Variables

    String fileName;
    String file;
    ImageLoader imageLoader = new ImageLoader(this);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.singleitemview);

        Intent i = getIntent();
        // Get the result of fileNAme
        fileName = i.getStringExtra("fileName");

        // Get the result of file
        file = i.getStringExtra("file");

        // Locate the TextViews in singleitemview.xml
        TextView txtfileName = (TextView) findViewById(R.id.txtfileName);

        // Locate the ImageView in singleitemview.xml
        ImageView imgfile = (ImageView) findViewById(R.id.file);

        // Set results to the TextViews
        txtfileName.setText(fileName);

        // Capture position and set results to the ImageView
        // Passes file images URL into ImageLoader.class
        imageLoader.DisplayImage(file, imgfile);
    }

    }
