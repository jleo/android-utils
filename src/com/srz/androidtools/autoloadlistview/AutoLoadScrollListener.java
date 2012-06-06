package com.srz.androidtools.autoloadlistview;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 5/10/11
 * Time: 11:35 AM
 * To change this template use FileType | Settings | FileType Templates.
 */
public class AutoLoadScrollListener implements AbsListView.OnScrollListener {
    int lastItem;
    boolean fireLoad = false;
    private OnLoadListener onLoadListener;
    private AutoLoadArrayAdapter adapter;
    Handler handler = new Handler();
    private ListView listView;
    private OnNothingLoaded onNothingLoadedListener;

    public AutoLoadScrollListener(OnLoadListener listener, AutoLoadArrayAdapter adapter, ListView listView, OnNothingLoaded nothingLoadedListener) {
        this.onLoadListener = listener;
        this.onNothingLoadedListener = nothingLoadedListener;
        this.adapter = adapter;
        this.listView = listView;
//        listView.setFastScrollEnabled(true);
    }

    private boolean isLastItemVisible() {
        final int count = this.listView.getCount();
        if (count == 0) {
            return true;
        } else if (listView.getLastVisiblePosition() >= count - 1) {
            return true;
        } else {
            return false;
        }
    }

    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem + visibleItemCount >= totalItemCount
                && totalItemCount != 0) {
            fireLoad = true;
        } else {
            fireLoad = false;
        }
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        int first = view.getFirstVisiblePosition();
        int count = view.getChildCount();
        Log.d("jleoo", scrollState + "");
        if (scrollState == SCROLL_STATE_IDLE || (first + count >= adapter.getCount())) {
            if ((fireLoad || isLastItemVisible()) && !adapter.isLoadingData() && !adapter.isNoMoreToLoad()) {
                load(true);
            } else {
                fireLoad = false;
                triggerHeavyUIOperation();
            }
        }
    }

    public synchronized void triggerHeavyUIOperation() {
        int first = listView.getFirstVisiblePosition();
        int count = listView.getChildCount();
        for (int i = 0; i < count; i++) {
            View childAt = listView.getChildAt(i);
            if (childAt instanceof HeavyUIOperater) {
                HeavyUIOperater articleView = (HeavyUIOperater) childAt;
                articleView.heavyUIOperation();
            }
        }
    }


    public void load(final boolean showLoading) {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                if (showLoading) {
                    adapter.setLoading(true);
                    handler.post(new Runnable() {
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
                List loadedItems = null;
                try {
                    loadedItems = onLoadListener.load();

                } catch (NoSuchPageException e) {
                    adapter.setNoMoreToLoad(true);
                }
                final List toAdd = loadedItems;
                adapter.setLoading(false);
                handler.post(new Runnable() {
                    public void run() {
                        adapter.addItems(toAdd);
                        adapter.notifyDataSetChanged();
                        if ((toAdd == null || toAdd.size() == 0) && adapter.getActualItemCount() == 0) {
                            if (onNothingLoadedListener != null)
                                onNothingLoadedListener.onNothingLoaded();
                        }
                    }
                });
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        triggerHeavyUIOperation();
                    }
                }, 300);
            }
        });
        thread.start();
        if (!showLoading) {
            try {
                thread.join();
            } catch (InterruptedException e) {

            }
        }
    }
}

