package com.teratotech.dropto;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.ParseException;
import com.parse.SaveCallback;
import java.util.Random;


public class DroptoCreateFolder extends Activity {

    private FrameLayout cancelButton;
    private DropToFolder dropToF;
    private FrameLayout saveButton;
    private TextView FolderName;

    public  static String[] randomNumbers=new String[4];
    public static byte[] fourDigits=new byte[4];
    private  static boolean status=true;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropto_create_folder);

        //final Random myRandom = new Random();
        genRandomNumbers();
        while (status) ;

        for (String i : randomNumbers) {
            System.out.println(i);
        }
        System.out.println("four digit random number : " + new String(fourDigits));

      final TextView textGenerateNumber = (TextView)findViewById(R.id.generatenumber);
        textGenerateNumber.setText(String.valueOf(getFourDigitsRandom()));
    }

    private int getFourDigitsRandom() {
        final Random myRandom = new Random();
        while(true) {
            int random =  myRandom.nextInt(10000);
            if(random > 999) {
                return random;
            }
        }
    }

    public  void genRandomNumbers() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Random random=new Random();
                while(true) {

                    for(int i=0; i<4 ;i++) {
                        randomNumbers[i]= String.valueOf(random.nextDouble());
                        fourDigits[i]= (byte) random.nextDouble();
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
        final TextView textGenerateNumber = (TextView)findViewById(R.id.generatenumber);

        saveButton = ((FrameLayout) findViewById(R.id.action_save));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dropToF = new DropToFolder();
                // When the user clicks "Save," upload the foldername to Parse / Add data to the dropto object:
                dropToF.setname(FolderName.getText().toString());
                dropToF.setCode(textGenerateNumber.getText().toString());

                //getting unique id for device
                String id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                dropToF.setDeviceId(id);
                progressDialog = ProgressDialog.show(DroptoCreateFolder.this, "", "Loading...", true);
                // Save the DroptoFolder file and return
                dropToF.saveInBackground(new SaveCallback() {

                    @Override
                    public void done(ParseException e) {
                        if (e == null ) {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Folder is saved. ", Toast.LENGTH_SHORT).show();

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
