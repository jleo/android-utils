//package com.srz.androidtools.autoloadlistview;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ListView;
//import com.srz.androidtools.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by IntelliJ IDEA.
// * User: janexie
// * Date: 12-1-26
// * Time: 下午8:05
// * To change this template use File | Settings | File Templates.
// */
//public abstract class NoMoreDataSupportDefaultAdapter<T> extends PaginationLoaderAdapter {
//    protected Context context;
//    private boolean loaded;
//
//    public NoMoreDataSupportDefaultAdapter(Activity activity, ListView listView, int progressDrawableResourceId, int layoutId, PaginationLoaderService loaderService, List<T> detailInfo, int nodataview, View.OnClickListener noitemListener) {
//        super(activity, listView, layoutId, progressDrawableResourceId, detailInfo, nodataview, loaderService, noitemListener);
//        this.context = activity;
//    }
//
//    public NoMoreDataSupportDefaultAdapter(Activity activity, ListView listView, int progressDrawableResourceId, int layoutId, PaginationLoaderService loaderService, int nodataview, View.OnClickListener noitemListener) {
//        this(activity, listView, progressDrawableResourceId, layoutId, loaderService, new ArrayList(), nodataview, noitemListener);
//    }
//
//    public NoMoreDataSupportDefaultAdapter(Activity activity, ListView listView, int progressDrawableResourceId, int layoutId, PaginationLoaderService loaderService) {
//        this(activity, listView, progressDrawableResourceId, layoutId, loaderService, new ArrayList(), -1, null);
//    }
//
//    @Override
//    protected void setNoDataOnClickListener(View nodataitem, View.OnClickListener listener) {
//        nodataitem.findViewById(R.id.addmore).setOnClickListener(listener);
//    }
//
//    public boolean isLoaded() {
//        return loaded;
//    }
//
//    public void setLoaded(boolean loaded) {
//        this.loaded = loaded;
//    }
//
//    @Override
//    public void forceLoad() {
//        super.forceLoad();
//        loaded = true;
//    }
//
//
//}
