package com.teratotech.dropto;


import java.util.Date;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends ArrayAdapter<Item> {

    // Declare Variables
    Context context;
    ImageLoader imageLoader;

    public ListViewAdapter(Context context, List<Item> objects) {
        super(context, R.layout.listview_item, objects);
        this.context = context;
        imageLoader = new ImageLoader(context);
    }

    public class ViewHolder {
        TextView fileName;
        TextView expiryDate;
        ImageView fileW;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;

        if (view == null){

            holder = new ViewHolder();
            view = LayoutInflater.from(getContext()).inflate(R.layout.listview_item, parent, false);
            // Locate the TextViews in listview_item.xml
           // holder.fileName = (TextView) view.findViewById(R.id.separator);
            holder.fileName = (TextView) view.findViewById(R.id.file_name);
            holder.expiryDate = (TextView) view.findViewById(R.id.expiryDate);

            // Locate the ImageView in listview_item.xml
            holder.fileW = (ImageView) view.findViewById(R.id.file);
            view.setTag(holder);
        } else
        {
            holder = (ViewHolder) view.getTag();
        }
        final Item item = getItem(position);

        Date d = item.getDate();
        holder.fileName.setText(item.getName());

            // Set the results into ImageView
        item.setImage(imageLoader, holder.fileW);

        if (d != null) {
            holder.expiryDate.setText(d.toString());
        } else {
            holder.expiryDate.setText("Folder");
        }
        return view;

    }

}