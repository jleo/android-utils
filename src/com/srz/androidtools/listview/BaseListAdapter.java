package com.srz.androidtools.listview;
 
import java.util.List;
 

import android.app.Activity;
import android.content.Context; 
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup; 
import android.widget.ArrayAdapter; 
import android.widget.ListView;

public abstract class BaseListAdapter extends ArrayAdapter<Object> implements
        IViewHolderManager {
    public BaseListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);

    }

    public BaseListAdapter(Activity activity, List<Object> listItem,
            ListView listView, int listViewItemResId) {
        super(activity, 0, listItem);
        this.listViewItemResId = listViewItemResId;
    }

    private int listViewItemResId;

    public int getListViewItemResId() {
        return listViewItemResId;
    }

    public void setListViewItemResId(int listViewItemResId) {
        this.listViewItemResId = listViewItemResId;
    }

    private LayoutInflater mInflater;

    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(getListViewItemResId(), null);
            holder = creatViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.installData(position);
        return convertView;

    }
}
