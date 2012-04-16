package com.srz.androidtools.listview;

 

public interface IAsyncLoader<T> {
    public void setAsyncLoader(AsyncLoaderBase<T> asyncLoader); 
    public AsyncLoaderBase<T> getAsyncLoader() ;
    
}
