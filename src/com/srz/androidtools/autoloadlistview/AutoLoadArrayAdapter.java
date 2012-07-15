package com.srz.androidtools.autoloadlistview;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: lsha6086
 * Date: 4/2/11
 * Time: 4:23 PM
 * To change this template use FileType | Settings | FileType Templates.
 */
public abstract class AutoLoadArrayAdapter<T> extends ArrayAdapter implements AdapterView.OnItemClickListener {
    protected List<Comparable> items;
    private AutoLoadScrollListener autoLoadScrollListener;
    private int layoutId;
    private boolean noMoreToLoad;
    private static LayoutInflater inflater;
    ListView listView;

    public int getActualItemCount() {
        if (items == null)
            return 0;

        return items.size();
    }

    public AutoLoadArrayAdapter(Activity activity, ListView listView, int layoutId, int progressDrawableResourceId, List items, int nodataview, View.OnClickListener noitemListener, OnNothingLoaded onNothingLoadedListener) {
        super(activity, layoutId, items);
        this.items = items;
        this.layoutId = layoutId;
        isLoadingData = false;
        this.listView = listView;
        inflater = LayoutInflater.from(activity);
        this.progressView = inflater.inflate(progressDrawableResourceId,
                listView, false);
        if (nodataview != -1)
            setNoDataView(nodataview, noitemListener);
        //if (listView.getOnItemClickListener() == null)
        listView.setOnItemClickListener(this);


        autoLoadScrollListener = new AutoLoadScrollListener(new OnLoadListener() {
            public List load() throws NoSuchPageException {
                return AutoLoadArrayAdapter.this.load();
            }
        }, this, listView, onNothingLoadedListener);
        listView.setOnScrollListener(autoLoadScrollListener);
        listView.setAdapter(this);
    }

    public void setNoDataView(int nodataview, View.OnClickListener listener) {
        this.nodataitem = inflater.inflate(nodataview,
                listView, false);
        setNoDataOnClickListener(nodataitem, listener);
    }

    protected abstract void setNoDataOnClickListener(View nodataitem, View.OnClickListener listener);

    public boolean isLoadingData() {
        return isLoadingData;
    }

    private boolean isLoadingData;
    private View progressView;
    private View nodataitem;

    public void addItems(List items) {

        if (items != null) {
            Set itemSet = new HashSet();
            itemSet.addAll(this.items);
            itemSet.addAll(items);

            this.items.clear();

            this.items.addAll(itemSet);
            Collections.sort(this.items);
        }
    }

    public void forceLoad(boolean showLoading) {
        autoLoadScrollListener.load(showLoading);
    }

    public void forceLoad() {
        forceLoad(true);
    }

    public int getCount() {
        int size = 0;
        if (items.size() == 1) {
            if (items.get(0) instanceof String) {
                return 0;
            }
        }
        if (items != null) {
            size += items.size();
        }
        if (items.size() >= 2)
            size += 1;

        if (items.size() == 0)
            return 1;

        return size;
    }

    protected boolean isLastItem(int position) {
        return position == items.size();
    }


    public void setLoading(boolean loading) {
        this.isLoadingData = loading;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (isLastItem(position)) {
            if (items.size() == 0 && isLoadingData)
                return progressView;
            if (nodataitem != null && (items.size() == 0 || noMoreToLoad) && !isLoadingData)
                return nodataitem;

            return progressView;
        }
        return buildViewFromItem(position, convertView, parent);
    }

    public View buildViewFromItem(int position, View convertView, ViewGroup parent) {
        Comparable di = items.get(position);

        ItemView view = null;
        view = (ItemView) inflater.inflate(layoutId, null);
        view.render(di);
        return view;
    }

    public abstract List load() throws NoSuchPageException;

    public abstract void reset();


    public void setNoMoreToLoad(boolean noMoreToLoad) {
        this.noMoreToLoad = noMoreToLoad;
    }

    public boolean isNoMoreToLoad() {
        return this.noMoreToLoad;
    }
}
