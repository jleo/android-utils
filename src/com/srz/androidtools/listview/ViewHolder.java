package com.srz.androidtools.listview;

import android.view.View;

public  abstract class ViewHolder implements IAsyncLoader {

    public abstract void bindingResLayout(View convertView) ;  
    public abstract void installData(int position);
    public IAsyncLoader asyncLoader;
    public   abstract  ViewHolder createAndBinding(View convertView) ;
    public abstract void clearCache() ;

}
