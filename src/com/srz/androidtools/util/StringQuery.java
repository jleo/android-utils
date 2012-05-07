package com.srz.androidtools.util;

import android.content.Context;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

public class StringQuery {
    private String url;
    private Context context;

    public StringQuery(Context context, String url) {
        this.url = url;
        this.context = context;
    }


    public String invoke() {
        AjaxCallback<String> cb = new AjaxCallback<String>();
        cb.url(url).type(String.class);

        AQuery aQuery = new AQuery(context);
        aQuery.sync(cb);

        String result = cb.getResult();
        AjaxStatus status = cb.getStatus();
        if (status.getCode() > 299 || status.getCode() < 200)
            return null;

        return result;
    }
}