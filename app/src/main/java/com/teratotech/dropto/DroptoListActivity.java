package com.teratotech.dropto;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.ListActivity;
import android.content.Intent;

import com.parse.ParseQueryAdapter;
//import com.teratotech.ParseQueryAdapter;




public class DroptoListActivity extends ListActivity {

    private ParseQueryAdapter<DropTo> mainAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropto_list);

        mainAdapter = new ParseQueryAdapter<DropTo>(this, DropTo.class);
        mainAdapter.setImageKey("photo");

        //subclass of ParseQueryAdapter


        // Default view is all meals
        setListAdapter(mainAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_refresh: {
                updateDroptoList();
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void updateDroptoList() {
        mainAdapter.loadObjects();
        setListAdapter(mainAdapter);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // If a new post has been added, update
            // the list of posts
            updateDroptoList();
        }
    }

}
