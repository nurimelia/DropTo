package com.teratotech.dropto;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.util.Date;
import java.util.List;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import java.util.ArrayList;
import java.util.Random;


import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class MainActivity extends Activity {

    // Declare Variables
    ListView listview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    ListViewAdapter adapter;
    private List<DropToWorld> dropToworldList = null;

    public final static String EXTRA_MESSAGE = "com.teratotech.dropto.MESSAGE";
    private static final String tag = "MainActivity";
    private int width;
    private WindowManager.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ParseObject.registerSubclass(DropTo.class);
        ParseObject.registerSubclass(DropToFolder.class);
        Parse.initialize(this, "ZJMejxcTIMNyuWWviiP3eQkELIZ1mZ7dqQVIolpV", "ZGejA1DwbSu6cQRoooA4yCBcglC0AYoxEi2ouHNU");
        ParseAnalytics.trackAppOpened(getIntent());

        super.onCreate(savedInstanceState);
        // Get the view from actionsheet.xml
        setContentView(R.layout.actionsheet);


        TextView tvNewButton = (TextView) findViewById(R.id.tvUpload);
        tvNewButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View item) {
                Toast.makeText(getApplicationContext(), "Upload file", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), DroptoUploadActivity.class));
            }
        });

        TextView tvCreateFolder = (TextView) findViewById(R.id.tvCreateFolder);
        tvCreateFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View item) {
                startActivity(new Intent(getApplicationContext(), DroptoCreateFolder.class));
            }
        });

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
        Log.i(tag, "Height : " + metrics.heightPixels + " Width : " + width);

        params = getWindow().getAttributes();
        params.width = width;

        // Execute RemoteDataTask AsyncTask
        new RemoteDataTask().execute();
    }



    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(MainActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("DropTo files");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
// Create the array
            dropToworldList = new ArrayList<DropToWorld>();
            try {
                // Locate the class table named "File" in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "File");

               /* ParseQuery<ParseObject> fileQuery = new ParseQuery<ParseObject>(
                   //     "File");

               // ParseQuery<ParseObject> folderQuery = new ParseQuery<ParseObject>(
                //        "Folder");

               // ArrayList<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
              //  queries.add(fileQuery);
              //  queries.add(folderQuery);

               // ParseQuery<ParseObject> query = ParseQuery.or(queries);*/

         //**       // Locate the column named "fileName" in Parse.com and order list by ascending
                query.orderByAscending("fileName");
                ob = query.find();
                for (ParseObject file : ob) {
                    // Locate images in flag column
                    ParseFile image = (ParseFile) file.get("file");

                    DropToWorld map = new DropToWorld();
                    map.setTitle((String) file.get("fileName"));
                   // map.setTitle((String) file.get("folderName"));
                    map.setPhotoFile(image.getUrl());
                    dropToworldList.add(map);
                }


            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml
            listview = (ListView) findViewById(R.id.listview);
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter(MainActivity.this, dropToworldList);
            // Binds the Adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
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
    public void sendToUpload(View view) {
        Button button = (Button) view;
        startActivity(new Intent(getApplicationContext(), DroptoUploadActivity.class));

    }



}


