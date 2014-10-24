package com.teratotech.dropto;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Random;


public class DroptoCreateFolder extends Activity {

    private FrameLayout cancelButton;
    private DropToFolder dropToF;
    private FrameLayout saveButton;
    private TextView FolderName;
    private TextView Code;


    public  static int[] randomNumbers=new int[4];
    public static byte[] fourDigits=new byte[4];
    private  static boolean status=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropto_create_folder);


        final Random myRandom = new Random();
        genRandomNumbers();
        while (status) ;

        for (int i : randomNumbers) {
            System.out.println(i);
        }
        System.out.println("four digit random number : " + new String(fourDigits));

      final TextView textGenerateNumber = (TextView)findViewById(R.id.generatenumber);
        textGenerateNumber.setText(String.valueOf(myRandom.nextInt(10000)));
    }

    public  void genRandomNumbers() {
        new Thread(new Runnable() {

            @Override
            public void run() {

                Random random=new Random();
                while(true) {

                    for(int i=0; i<4 ;i++) {
                        randomNumbers[i]=random.nextInt();
                        fourDigits[i]= (byte) random.nextInt();
                    }
                    status=false;
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        }).start();


        FolderName = ((EditText) findViewById(R.id.droptocreate_title));
       // Code = ((EditText) findViewById(R.id.generatenumber));
        final TextView textGenerateNumber = (TextView)findViewById(R.id.generatenumber);

        saveButton = ((FrameLayout) findViewById(R.id.action_save));
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dropToF = new DropToFolder();
                // When the user clicks "Save," upload the foldername to Parse / Add data to the dropto object:
                dropToF.setTitleF(FolderName.getText().toString());

                dropToF.setCode(textGenerateNumber.getText().toString());

                // Save the DroptoFolder file and return
                dropToF.saveInBackground(new SaveCallback() {

                    @Override
                    public void done(ParseException e) {
                        if (e == null ) {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Folder is saved. ", Toast.LENGTH_SHORT).show();
                            //setResult(Activity.RESULT_CANCELED);
                            finish();
                        }else {
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Error saving: " + e.getMessage(),
                                        Toast.LENGTH_SHORT).show();

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


  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dropto_create_folder, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }
}
