package com.teratotech.dropto;

import java.util.ArrayList;
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
    private List<DropToWorld> dropToWorldList = null;
    private ArrayList<DropToWorld> arraylist;

    public ListViewAdapter(Context context,
                           List<DropToWorld> dropToWorldList) {
        this.context = context;
        this.dropToWorldList = dropToWorldList;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<DropToWorld>();
        this.arraylist.addAll(dropToWorldList);
        imageLoader = new ImageLoader(context);
    }

    public class ViewHolder {
        TextView fileName;

        //TextView PhotoFile;
        ImageView file;
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

            // Locate the ImageView in listview_item.xml
            holder.file = (ImageView) view.findViewById(R.id.file);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.fileName.setText(dropToWorldList.get(position).getTitle());
        // Set the results into ImageView
        imageLoader.DisplayImage(dropToWorldList.get(position).getPhotoFile(),
                holder.file);
        // Listen for ListView Item Click
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                Intent intent = new Intent(context, SingleItemView.class);
                // Pass all data File Name
                intent.putExtra("fileName",
                        (dropToWorldList.get(position).getTitle()));

                // Pass all data file
                intent.putExtra("file",
                        (dropToWorldList.get(position).getPhotoFile()));
                // Start SingleItemView Class
                context.startActivity(intent);
            }
        });
        return view;
    }

}