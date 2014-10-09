package com.teratotech.dropto;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    public final static String EXTRA_MESSAGE = "com.teratotech.dropto.MESSAGE";
    private static final String tag = "MainActivity";
    private int width;
    private WindowManager.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actionsheet);

        TextView tvNewButton = (TextView) findViewById(R.id.tvUpload);
        tvNewButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View item){
                Toast.makeText(getApplicationContext(),"Upload file", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), DroptoUploadActivity.class));
            }
        });

        TextView tvCreateFolder = (TextView) findViewById(R.id.tvCreateFolder);

        tvCreateFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View item) {
                startActivity(new Intent(getApplicationContext(), DroptoCreateFolder.class));
                overridePendingTransition(R.anim.slide_up, R.anim.slide_up);
            }
        });

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        Log.i(tag, "Height : " +metrics.heightPixels+ " Width : " +width);

        params = getWindow().getAttributes();
        params.width = width;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        if (id == R.id.action_upload) {
            RelativeLayout actionsheet = (RelativeLayout) findViewById(R.id.rlActionSheet);
            int v = actionsheet.getVisibility();
Log.d("mainactivity", "actionsheet v: " + v);
            if (v == View.GONE || v == View.INVISIBLE) {
                actionsheet.setVisibility(View.VISIBLE);
            } else {
                actionsheet.setVisibility(View.GONE);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);

    }


    //Called when the user clicks the Send button
    public void sendToUpload (View view){
        Button button = (Button) view;
        startActivity(new Intent(getApplicationContext(), DroptoUploadActivity.class));

    }

}


