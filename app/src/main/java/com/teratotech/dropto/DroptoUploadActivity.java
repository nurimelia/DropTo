package com.teratotech.dropto;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.SaveCallback;

public class DroptoUploadActivity extends Activity {

    GPSTracker gps; // GPSTracker class
    private FrameLayout saveButton;
    private FrameLayout cancelButton;
    private TextView FileName;
    private Spinner dropToDate;

    private String selectedGalleryFileName;

    private long[] durations = {
            1 * 60 * 60 * 1000,
            12 * 60 * 60 * 1000,
            24 * 60 * 60 * 1000,
            48 * 60 * 60 * 1000,
            72 * 60 * 60 * 1000
    };

    private DropTo dropTo;
    ImageView viewImage;
    Button b;
    Button c;

    private String folderId;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropto_upload);

        Bundle a = getIntent().getExtras();

        if (a != null) {
            folderId = a.getString("folderId");
        }
        b = (Button) findViewById(R.id.btnUploadPhoto);
        viewImage = (ImageView) findViewById(R.id.dropto_preview_image);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        c = (Button) findViewById(R.id.btnUploadVideo);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectVideo();
            }
        });

        FileName = ((EditText) findViewById(R.id.file_name));

        // The droptoRating spinner lets people assign durations of files they've upload.
        dropToDate = ((Spinner) findViewById(R.id.rating_spinner));
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
                .createFromResource(this, R.array.expiryDate_array,
                        R.layout.simple_spinner_dropdown_item);
        dropToDate.setAdapter(spinnerAdapter);

        saveButton = ((FrameLayout) findViewById(R.id.action_save));
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog = ProgressDialog.show(DroptoUploadActivity.this, "", "Uploading File...", true);
                dropTo = new DropTo();
            // When the user clicks "Save," upload the file to Parse / Add data to the dropto object:
                dropTo.setfileName(FileName.getText().toString());
                dropTo.setFileType("jpg");

                if (folderId != null) dropTo.setFolderId(folderId);

                // Add the rating (the duraction )
                int pos = dropToDate.getSelectedItemPosition();
                long currentms = System.currentTimeMillis();
                long newms = currentms + durations[pos];
                Date expiry = new Date();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap b = BitmapFactory.decodeFile(selectedGalleryFileName);
                b.compress(Bitmap.CompressFormat.PNG, 100, stream);
                dropTo.setPhotoFile(new ParseFile(stream.toByteArray()));

                // If the user added a video,
                //ByteArrayOutputStream baos = new ByteArrayOutputStream();
              //  FileInputStream fis = new FileInputStream(new FileInputStream());
               // byte[] videoBytes = baos.toByteArray(); //this is the video in bytes.

                gps = new GPSTracker(DroptoUploadActivity.this);

                     // check
                expiry.setTime(newms);
                dropTo.setDate(expiry);

                // If the user added a photo, if GPS enabled
                     double latitude = gps.getLatitude();
                     double longitude = gps.getLongitude();
                     ParseGeoPoint pgp = new ParseGeoPoint(latitude, longitude);
                     dropTo.setLocation(pgp);

                //getting unique id for device
                String id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                dropTo.setDeviceId(id);

                // Save the Dropto file and return
                dropTo.saveInBackground(new SaveCallback() {

                    @Override
                    public void done(ParseException e) {
                        if (e == null )
                            if(gps.canGetLocation()){
                            double latitude = gps.getLatitude();
                            double longitude = gps.getLongitude();
                            Toast.makeText(
                                    getApplicationContext(),
                                    "File is saved. Your Location is - \nLat: " + latitude + "\nLong: " + longitude,
                                    Toast.LENGTH_SHORT).show();
                                // Close progress dialog
                                progressDialog.dismiss();
                                finish();
                        } else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Error saving: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                                // can't get location / GPS or Network is not enabled
                                // Ask user to enable GPS/network in settings
                               gps.showSettingsAlert();
                        }
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

    private void selectImage(){
        final CharSequence[] options = {"Take Photo", "Choose from Gallery","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(DroptoUploadActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options,new DialogInterface.OnClickListener(){
            @Override
        public void onClick(DialogInterface dialog, int item){
                if(options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new
                           Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if(options[item].equals("Cancel")){
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void selectVideo(){
        final CharSequence[] options = {"Take Video", "Choose from Gallery","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(DroptoUploadActivity.this);
        builder.setTitle("Add Video!");
        builder.setItems(options,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int item){
                if(options[item].equals("Take Video"))
                {

                    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    File mediaFile = new File(android.os.Environment.getExternalStorageDirectory().getAbsolutePath(), "/myvideo.mp4");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(mediaFile));
                    startActivityForResult(intent, 3);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new
                            Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 4);
                }
                else if(options[item].equals("Cancel")){
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    viewImage.setImageBitmap(bitmap);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));

                selectedGalleryFileName = picturePath;

                Log.w("path of image from gallery......******************.........", picturePath + "");
                viewImage.setImageBitmap(thumbnail);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         //Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }
}
