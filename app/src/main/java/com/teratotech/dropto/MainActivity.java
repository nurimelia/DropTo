package com.teratotech.dropto;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import android.widget.ImageView;

import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import java.util.ArrayList;
import com.parse.ParseFile;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class MainActivity extends Activity {

    // Declare Variables
    StickyListHeadersListView listview;
    List<DropTo> ob;
    List<DropToFolder> ab;
    ProgressDialog mProgressDialog;
    ListViewAdapter adapter;
    private List<DropTo> dropToList = null;
    private List<DropToFolder> folderList = null;

    public final static String EXTRA_MESSAGE = "com.teratotech.dropto.MESSAGE";
    private static final String tag = "MainActivity";
    private int width;
    private WindowManager.LayoutParams params;
    private ProgressDialog progressDialog;
    CharSequence[] options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ParseObject.registerSubclass(DropTo.class);
        ParseObject.registerSubclass(DropToFolder.class);
        Parse.initialize(this,"ZJMejxcTIMNyuWWviiP3eQkELIZ1mZ7dqQVIolpV", "ZGejA1DwbSu6cQRoooA4yCBcglC0AYoxEi2ouHNU");
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

        listview = (StickyListHeadersListView) findViewById(R.id.listview);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Item item = adapter.getItem(i);

                if (item instanceof FileItem) {
                    // file
                    selectDownload(item);
                }
                if (item instanceof Folder) {
                    // folder
                    Intent intent = new Intent(MainActivity.this, FilesInFolder.class);
                    Bundle a = new Bundle();
                    a.putString("objectId", item.getId());
                    intent.putExtras(a);
                    startActivity(intent);
                }
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

    private void selectDownload(final Item pitem) {
        final String id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);


        CharSequence[] o1 = {"Download", "ReUpload","Remove"};
        CharSequence[] o2 = {"Download"};
        if (pitem.getDeviceId().equals(id)) {
           // return 1;
           options = o1;
        } else {
           // return 3;
           options = o2;
        }
     //   final CharSequence[] options = {"Download", "ReUpload","Remove"};

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Download!");

        builder.setItems(options,new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Download")) {

                    progressDialog = ProgressDialog.show(MainActivity.this, "", "Downloading File...", true);
                    ParseQuery<DropTo> query = new ParseQuery<DropTo>("File");
                    // Locate the objectId from the class
                    query.getInBackground(pitem.getId(), new GetCallback<DropTo>() {

                        public void done(DropTo object, ParseException e) {
                            // Locate the column named "Image Name" and set the string
                            final ParseFile image = (ParseFile) object.get("file");
                            final String fileType = (String) object.get("fileType");
                            image.getDataInBackground(new GetDataCallback() {

                                public void done(byte[] data, ParseException e) {
                                    if (e == null) {

                                        Save saveFile = new Save();
                                        // saveFile.SaveImage(MainActivity.this, bitmap);
                                        saveFile.saveFile(MainActivity.this, data, fileType);
                                        // Close progress dialog
                                        progressDialog.dismiss();

                                    } else {
                                        Log.d("test", "There was a problem downloading the data.");
                                    }
                                }
                            });
                        }
                    });
                } else if (options[item].equals("ReUpload")) {

                    Intent intent = new Intent(MainActivity.this, ReUpload.class);
                    Bundle b = new Bundle();
                    b.putString("objectId", pitem.getId());
                    intent.putExtras(b);
                    startActivity(intent);

                } else if (options[item].equals("Remove")) {

                    DropTo d = ((FileItem) pitem).dropto;
                    Log.d("MainActivity", "deleting " + d.getString("fileName"));
                    d.deleteInBackground(new DeleteCallback() {

                        @Override
                        public void done(ParseException e) {
                            Log.d("MainActivity", "deleted");
                            adapter.notifyDataSetChanged();
                            reloadAllData();
                        }
                    });
                }
            }
        });
        builder.show();
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
            dropToList = new ArrayList<DropTo>();
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
                    Log.d("dt-ma", "folder in obj: " + file.getParseObject("folderId"));
                    dropToList.add(file);
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
            listview = (StickyListHeadersListView) findViewById(R.id.listview);
            List<Item> list =getItemList();
            Collections.sort(list, new Comparator<Item>() {
                @Override
                public int compare(Item item, Item item2) {
                    Log.i("item 1 ","" + item.getName() + ", " + item.getDeviceId());
                    Log.i("item 2 ","" + item2.getName() + ", " + item2.getDeviceId());
                    return item.getDeviceId().compareTo(item2.getDeviceId());
                }
            });

            List<Long> headers = new LinkedList<Long>();
            long header = 0;
            String currentDeviceId, prevDeviceId = null;
            for(int i = 0; i < list.size(); i++) {
                currentDeviceId = list.get(i).getDeviceId();
                if(prevDeviceId != null && currentDeviceId.equalsIgnoreCase(currentDeviceId)) {

                } else {
                    header++;
                }

                headers.add(header);
                prevDeviceId = currentDeviceId;
            }
            adapter  = new ListViewAdapter(MainActivity.this, list, headers);
            // Pass the results into ListViewAdapter.java
            // Binds the Adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }

    private List<Item> getItemList() {
        List<Item> itemList = new ArrayList<Item>();

        Bundle abc = getIntent().getExtras();
        String value = "";

        if (abc != null) {
            value = abc.getString("code");
        }

        // All the items
        Date n = new Date();

        for (DropTo d : dropToList) {
            FileItem fileItem = new FileItem();
            fileItem.name = d.getString("fileName");
            fileItem.dropto = d;

            Log.d("dt-mainactivity", "fin:" + fileItem.name);
            DropToFolder fff = (DropToFolder) d.getParseObject("folderId");

            if (fff == null && d.getDate().compareTo(n) > 0) {
                itemList.add(fileItem);
            }
        }
        // All the folders
        for(int i = 0; i < folderList.size(); i++) {
            Folder folder = new Folder();
            folder.name = folderList.get(i).getString("folderName");
            folder.dropto = folderList.get(i);
            Log.d("dt-mainactivity", "fn:" + folderList.get(i).getString("deviceId"));

            String hhh = folderList.get(i).getString("deviceId");
            String code = folderList.get(i).getString("code");

            //getting unique id for device
            String id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

            if (code.equals(value) || hhh.equals(id)) {
                itemList.add(folder);
            }
        }
        return itemList;
    }
    // * helper to show what happens when all data is new
    private void reloadAllData(){

        new RemoteDataTask().execute();
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




