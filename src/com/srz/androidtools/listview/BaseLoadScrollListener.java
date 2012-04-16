package com.srz.androidtools.listview;
 
import android.content.Context;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

public    class BaseLoadScrollListener implements OnScrollListener{
    protected int page = 1 ;
    protected boolean fireLoad = false;
    protected int PAGECOUNT ;
    protected boolean notLoading = true;
    protected int  total_number;
    private BaseListAdapter adapter;
    private Context context;
    private ListView listView;
    private ILoader listener;
    
    public BaseLoadScrollListener(ILoader listener, BaseListAdapter adapter, ListView listView) {
        this.context = listView.getContext();
        this.adapter = adapter;
        this.listView = listView;
        this.listener = listener;
        this.listener.init(this);
 
    }
    public void onScroll(AbsListView view, int firstVisibleItem,
            int visibleItemCount, int totalItemCount) { 
        if (firstVisibleItem + visibleItemCount >= totalItemCount
                && totalItemCount < total_number && totalItemCount != 0) {
            fireLoad = true;
        } else {
            fireLoad = false;
        } 
        
        //TODO:按照屏幕visibleItemCount 和  PAGECOUNT 以及当前 位置与  totalItemCount的关系 选择fireload状态 并且调整  PAGECOUNT
      
        if (firstVisibleItem + visibleItemCount >= (totalItemCount - PAGECOUNT/2)
                && totalItemCount < total_number && totalItemCount != 0) {
            if (notLoading) {
                notLoading = false;
                page += 1; 
                this.listener.load(); 

            }
        }
    } 
    public void onScrollStateChanged(AbsListView view, int scrollState) {
 
        int first = view.getFirstVisiblePosition();
        int count = view.getChildCount(); 
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE ||(first + count >= view.getAdapter().getCount())) {
            if (fireLoad && notLoading) {
                notLoading = false;
                page += 1; 
                this.listener.load(); 
 
            }
        }
    }
    
   


 
}
 