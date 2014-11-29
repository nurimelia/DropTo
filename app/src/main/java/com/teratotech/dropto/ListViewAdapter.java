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

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

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

        int rowType = getItemViewType(position);

        if (view == null) {

            holder = new ViewHolder();
            switch (rowType) {
                case TYPE_ITEM:

            view = LayoutInflater.from(getContext()).inflate(R.layout.listview_item, parent, false);
            // Locate the TextViews in listview_item.xml
           // holder.fileName = (TextView) view.findViewById(R.id.textSeparator);
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