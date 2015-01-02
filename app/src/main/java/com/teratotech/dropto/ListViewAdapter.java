package com.teratotech.dropto;


import java.util.Date;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class ListViewAdapter extends ArrayAdapter<Item> implements StickyListHeadersAdapter {

    // Declare Variables
    Context context;
    ImageLoader imageLoader;

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;
    private List<Long> headers;

    public ListViewAdapter(Context context, List<Item> objects, List<Long> headers) {
        super(context, R.layout.listview_item, objects);
        this.context = context;
        imageLoader = new ImageLoader(context);
        this.headers = headers;
    }

    public class ViewHolder {
        TextView fileName;
        TextView expiryDate;
        ImageView fileW;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;

        int rowType = getItemViewType(position);

        if (view == null) {

            holder = new ViewHolder();
            switch (rowType) {
                case TYPE_ITEM:

            view = LayoutInflater.from(getContext()).inflate(R.layout.listview_item, parent, false);
            // Locate the TextViews in listview_item.xml
            holder.fileName = (TextView) view.findViewById(R.id.file_name);
            holder.expiryDate = (TextView) view.findViewById(R.id.expiryDate);
            holder.fileW = (ImageView) view.findViewById(R.id.file);
                    break;
                case TYPE_SEPARATOR:
                    view = LayoutInflater.from(getContext()).inflate(R.layout.listview_item, parent, false);
                    holder.fileName = (TextView) view.findViewById(R.id.textSeparator);
                    break;

        }view.setTag(holder);
        } else
        {
            holder = (ViewHolder) view.getTag();
        }
        final Item item = getItem(position);

        Date d = item.getDate();
        holder.fileName.setText(item.getName());
        String c = item.getCode();
            // Set the results into ImageView
        item.setImage(imageLoader, holder.fileW);

        if (d != null) {
            holder.expiryDate.setText(d.toString());
        } else {
            holder.expiryDate.setText(c.toString());
        }
        return view;
    }

    @Override
    public View getHeaderView(int position, View view, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.header, parent, false);
            holder.fileName = (TextView) view.findViewById(R.id.textSeparator);
            view.setTag(holder);
        }else
        {
            holder = (ViewHolder) view.getTag();
        }


        String id = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        Item item = getItem(position);

        if (item.getDeviceId().equals(id)) {
            holder.fileName.setText("Seeder" );

        } else {
            holder.fileName.setText("Other" );
        }
        //final Item item = getItem(position);
       //holder.fileName.setText("Header "+ headers.get(position));
           // holder.fileName.setText(item.getDeviceId());
            return view; // the thing to display
    }

    @Override
    public long getHeaderId(int i) {
        // sample: items in group a returns 1
        // items in group b returns 2

        //getting unique id for device
        String id = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        Item item = getItem(i);

        if (item.getDeviceId().equals(id)) {
            return 1;
        } else {
            return 3;
        }

        //return item.getDeviceId().hashCode();


    }

}