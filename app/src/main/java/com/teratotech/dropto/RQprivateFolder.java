package com.teratotech.dropto;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.HashMap;


public class RQprivateFolder extends Activity {

    private FrameLayout searchButton;
    private FrameLayout cancelButton;
    private ListView lv;

    private DropTo dropTo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rqprivate_folder);


        searchButton = ((FrameLayout) findViewById(R.id.search));
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Search the Dropto file and return
                dropTo.saveInBackground(new SaveCallback() {

                    @Override
                    public void done(ParseException e) {
                        if (e == null ){
                            Toast.makeText(getApplicationContext(),"Search Folder.",Toast.LENGTH_SHORT).show();

                            finish();
                        } else {
                            Toast.makeText(
                                    getApplicationContext(),"Error " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });


        cancelButton = ((FrameLayout) findViewById(R.id.cancel));
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        return ;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
