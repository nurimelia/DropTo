package com.teratotech.dropto;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import java.util.Date;


public class ReUpload extends Activity {

    private FrameLayout saveButton;
    private FrameLayout cancelButton;
    ListViewAdapter adapter;

    private Spinner dropToDate;

    private long[] durations = {
            1 * 60 * 60 * 1000,
            12 * 60 * 60 * 1000,
            24 * 60 * 60 * 1000,
            48 * 60 * 60 * 1000,
            72 * 60 * 60 * 1000
    };

    private DropTo dropTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_upload);


        // The droptoRating spinner lets people assign durations of files they've upload.
        dropToDate = ((Spinner) findViewById(R.id.rating_spinner));
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.expiryDate_array,
                android.R.layout.simple_spinner_dropdown_item);
        dropToDate.setAdapter(spinnerAdapter);

        saveButton = ((FrameLayout) findViewById(R.id.action_save));
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Add the rating (the duraction )
                int pos = dropToDate.getSelectedItemPosition();
                long currentms = System.currentTimeMillis();
                long newms = currentms + durations[pos];
                Date expiry = new Date();
                expiry.setTime(newms);
                dropTo.setDate(expiry);

                // Save the Dropto file and return
               // dropTo.saveInBackground(new SaveCallback() {


                    ParseQuery<DropTo> query = new ParseQuery<DropTo>("File");
                    //Retrieve the object by id
                    query.getInBackground("nLBx9v0K6A",new GetCallback<DropTo>(){

                   // @Override
                    public void done(DropTo update,ParseException e) {
                        if (e == null ){
                            update.put("expiryDate", 123);
                                Toast.makeText(getApplicationContext(),"File is update.",Toast.LENGTH_SHORT).show();

                                finish();
                            } else {
                                Toast.makeText(
                                        getApplicationContext(),"Error update: " + e.getMessage(),
                                        Toast.LENGTH_SHORT).show();

                            }
                        //dropTo.saveInBackground();
                    }
                });
            }
        });


        cancelButton = ((FrameLayout) findViewById(R.id.action_discard));
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
        getMenuInflater().inflate(R.menu.re_upload, menu);
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