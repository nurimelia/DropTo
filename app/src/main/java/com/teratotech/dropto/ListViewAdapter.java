package com.teratotech.dropto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    private List<DropTo> dropToWorldList = null;//
    private ArrayList<DropTo> arraylist;//

    public ListViewAdapter(Context context, List<DropTo> dropToWorldList) { //
        this.context = context;
        this.dropToWorldList = dropToWorldList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<DropTo>();
        this.arraylist.addAll(dropToWorldList);
        imageLoader = new ImageLoader(context);
    }

    public class ViewHolder {
        TextView fileName;
        TextView expiryDate;
        ImageView fileW;

    }

    @Override
    public int getCount() {
        return dropToWorldList.size();
    }

    @Override
    public Object getItem(int position) {
        return dropToWorldList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item, null);
            // Locate the TextViews in listview_item.xml
            holder.fileName = (TextView) view.findViewById(R.id.file_name);
            holder.expiryDate = (TextView) view.findViewById(R.id.expiryDate);

            // Locate the ImageView in listview_item.xml
            holder.fileW = (ImageView) view.findViewById(R.id.file);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.fileName.setText(dropToWorldList.get(position).getTitle());

       Date d = dropToWorldList.get(position).getDate();
       holder.expiryDate.setText(d.toString());

        // Set the results into ImageView
      imageLoader.DisplayImage(dropToWorldList.get(position).getPhotoFileW(),holder.fileW);
        // Listen for ListView Item Click
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                Intent intent = new Intent(context, SingleItemView.class);
                // Pass all data File Name
                intent.putExtra("fileName",(dropToWorldList.get(position).getTitle()));

                // Pass all data Expiry Date
                intent.putExtra("expiryDate",(dropToWorldList.get(position).getDate()));

                // Pass all data Folder Name
                //intent.putExtra("folderName",(dropToWorldList.get(position).getTitle()));

                // Pass all data file
                intent.putExtra("file",(dropToWorldList.get(position).getPhotoFileW()));


                // Start SingleItemView Class
                context.startActivity(intent);
            }
        });
        return view;
    }

}