package com.teratotech.dropto;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

import com.parse.ParseFile;
import com.parse.ParseRelation;

public class MainActivity extends Activity {

    // Declare Variables
    ListView listview;
    List<DropTo> ob;
    List<DropToFolder> ab;
    ProgressDialog mProgressDialog;
    ListViewAdapter adapter;
    private List<DropTo> dropToworldList = null;

    private List<DropToFolder> folderList = null;

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


        ListView list = (ListView) findViewById(R.id.listview);


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
            dropToworldList = new ArrayList<DropTo>();
            folderList = new ArrayList<DropToFolder>();
            try {
                // Locate the class table named "File" in Parse.com
                ParseQuery<DropTo> query = new ParseQuery<DropTo>("File");

                ParseQuery<DropToFolder> folderQuery = new ParseQuery<DropToFolder>("Folder");

                folderQuery.orderByAscending("folderName");
                ab = folderQuery.find();
                for (DropToFolder obj : ab){
                    Log.d("dt-mainactivity", "added folder: " + obj.getString("folderName"));
                    folderList.add(obj);
                }

                query.orderByAscending("fileName");
                query.include("folderId");
                ob = query.find();
                for (DropTo file : ob) {
                    // Locate images in flag column
                   // ParseFile image = (ParseFile) file.get("file");

                    Log.d("dt-ma", "folder in obj: " + file.getParseObject("folderId"));

                    dropToworldList.add(file);
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

            adapter  = new ListViewAdapter(MainActivity.this, getItemList());
            // Pass the results into ListViewAdapter.java
            // Binds the Adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }

    private List<Item> getItemList() {
        List<Item> itemList = new ArrayList<Item>();

        // All the items

        Date n = new Date();

        for (DropTo d : dropToworldList) {
            File file = new File();
            file.name = d.getString("fileName");
            file.dropto = d;
            Log.d("dt-mainactivity", "fin:" + file.name);

            DropToFolder fff = (DropToFolder) d.getParseObject("folderId");

            if (fff == null && d.getDate().compareTo(n) > 0) {
                itemList.add(file);
            }
        }

        // All the folders
        for(int i = 0; i < folderList.size(); i++) {
            Folder folder = new Folder();
            folder.name = folderList.get(i).getString("folderName");
            Log.d("dt-mainactivity", "fn:" + folderList.get(i).getString("folderName"));
            //folder.
            itemList.add(folder);
        }

        return itemList;
    }


    /**
     * helper to show what happens when all data is new
     */
    private void reloadAllData(){
    adapter.clear();
    adapter.addAll(getItemList());

        // fire the event
        adapter.notifyDataSetChanged();
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


        switch (item.getItemId()){
            case R.id.action_refresh:
                Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
                //adapter = new ListViewAdapter(MainActivity.this);
                listview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                reloadAllData();
                break;
            case R.id.action_folder:
                Toast.makeText(this, "Private Folder", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), RQprivateFolder.class));

                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);





    }






}


