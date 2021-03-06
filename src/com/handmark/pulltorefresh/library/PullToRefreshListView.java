package com.handmark.pulltorefresh.library;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
import com.srz.androidtools.R;

public class PullToRefreshListView extends PullToRefreshBase<ListView> {

	public PullToRefreshListView(Context context) {
		super(context);
	}

	public PullToRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected final ListView createAdapterView(Context context, AttributeSet attrs) {
		ListView lv = new ListView(context, attrs);
		// Set it to this so it can be used in ListActivity/ListFragment
        lv.setVerticalFadingEdgeEnabled(true);
        lv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.listbg));
//        lv.setPadding(10,0,10,0);
		lv.setId(android.R.id.list);
		return lv;
	}

}
