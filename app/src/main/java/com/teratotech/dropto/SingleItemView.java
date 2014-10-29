package com.teratotech.dropto;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;

public class SingleItemView extends Activity {
    // Declare Variables

    String fileName;
    String expiryDate;
    String file;
    ImageLoader imageLoader = new ImageLoader(this);

    private static final String tag = "SingleItemView";
    private int width;
    private WindowManager.LayoutParams params;

    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.singleitemview);

        Intent i = getIntent();
        // Get the result of fileNAme
        fileName = i.getStringExtra("fileName");

        // Get the result of expiryDate
        expiryDate = i.getStringExtra("expiryDate");

        // Get the result of file
        file = i.getStringExtra("file");

        // Locate the TextViews in singleitemview.xml
        TextView txtfileName = (TextView) findViewById(R.id.txtfileName);
        TextView txtfileExpiry = (TextView) findViewById(R.id.txtfileExpiry);

        // Locate the ImageView in singleitemview.xml
        ImageView imgfile = (ImageView) findViewById(R.id.file);

        // Set results to the TextViews
        txtfileName.setText(fileName);
        txtfileExpiry.setText(expiryDate);
        // Capture position and set results to the ImageView
        // Passes file images URL into ImageLoader.class
        imageLoader.DisplayImage(file, imgfile);


        TextView tvReupload = (TextView) findViewById(R.id.tvReupload);
        tvReupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View item) {
               // startActivity(new Intent(getApplicationContext(), DroptoCreateFolder.class));
            }
        });

        TextView tvRemove = (TextView) findViewById(R.id.tvRemove);
        tvRemove.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View item) {

            }
        });




        // Locate the button in main.xml
        TextView tvDownload = (TextView) findViewById(R.id.tvDownload);
        // Capture button clicks
        tvDownload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                progressDialog = ProgressDialog.show(SingleItemView.this, "",
                        "Downloading Image...", true);

                // Locate the class table named "ImageUpload" in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("File");

                // Locate the objectId from the class
                query.getInBackground("YVtLBitwF2", new GetCallback<ParseObject>() {

                    public void done(ParseObject object,
                                     ParseException e) {
                        // Locate the column named "ImageName" and set the string

                        // Locate images in flag column
                        ParseFile image = (ParseFile) object.get("file");
                        image.getDataInBackground(new GetDataCallback() {

                            public void done(byte[] data,
                                             ParseException e) {
                                if (e == null) {
                                    Log.d("test", "We've got data in data.");
                                    // Decode the Byte[] into
                                    // Bitmap
                                    Bitmap bmp = BitmapFactory
                                            .decodeByteArray(
                                                    data, 0,
                                                    data.length);


                                    // Close progress dialog
                                    progressDialog.dismiss();

                                } else {
                                    Log.d("test",
                                            "There was a problem downloading the data.");
                                }
                            }
                        });
                    }
                });
            }

        });

                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                width = metrics.widthPixels;
                Log.i(tag, "Height : " + metrics.heightPixels + " Width : " + width);

                params = getWindow().getAttributes();
                params.width = width;


            }

            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.dropto_download, menu);
                return true;
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                // Handle action bar item clicks here. The action bar will
                // automatically handle clicks on the Home/Up button, so long
                // as you specify a parent activity in AndroidManifest.xml.

                int id = item.getItemId();

                if (id == R.id.action_download) {
                    RelativeLayout SingleItemView = (RelativeLayout) findViewById(R.id.rlActionSheet);
                    int v = SingleItemView.getVisibility();
                    Log.d("singleItemView", "SingleItemView v: " + v);
                    if (v == View.GONE || v == View.INVISIBLE) {
                        SingleItemView.setVisibility(View.VISIBLE);
                    } else {
                        SingleItemView.setVisibility(View.GONE);
                    }

                    return true;
                }

                return super.onOptionsItemSelected(item);
            }


        }





