package com.teratotech.dropto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

public class RQprivateFolder extends Activity {

    private FrameLayout searchButton;
    private FrameLayout cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rqprivate_folder);

        searchButton = ((FrameLayout) findViewById(R.id.search));
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                            EditText editText = (EditText) findViewById(R.id.generatenumber);
                            String code= editText.getText().toString();

                            Intent intent = new Intent(RQprivateFolder.this, MainActivity.class);
                            Bundle abc = new Bundle();
                            abc.putString("code", code);
                            intent.putExtras(abc);
                            startActivity(intent);
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
