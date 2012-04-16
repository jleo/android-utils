package com.srz.androidtools.listview;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

public abstract class AsyncLoaderBase<T> implements ObjectCallback {
    public IStatiCcacheSystem<T> statiCcacheSystem; 
    public IStatiCcacheSystem<T> getCachesystem() {
        return statiCcacheSystem;
    }
    public void setCachesystem(IStatiCcacheSystem<T> cachesystem) {
        this.statiCcacheSystem = cachesystem;
    } 
    public HashMap<String, SoftReference<T>> objectCache;  
     
    public abstract  T loadObjFromUrl(String urlString); 
    public abstract String urlToAssignString(String urlString);
    public abstract void saveObjToFile(String urlString, T obj);
    public AsyncLoaderBase() { 
        objectCache = new HashMap<String, SoftReference<T>>();   
    } 
    public AsyncLoaderBase(IStatiCcacheSystem<T> statiCcacheSystem) {
         objectCache = new HashMap<String, SoftReference< T>>();  
         setCachesystem(statiCcacheSystem);
    } 
    public void clearCache() {
        if (objectCache != null)
            objectCache.clear();
    }
    
    public  T loadObj( final  String urlString,  final  ObjectCallback ObjectCallback) { 
        if  (objectCache.containsKey(urlString)) {  
            SoftReference<T> softReference = objectCache.get(urlString);    
            T obj = softReference.get(); 
            if  (obj !=  null )  
                return  obj;  
        }       
         
        else  if(statiCcacheSystem != null) {
            T loadObjFromCache = extracted(urlString);
            return loadObjFromCache;
        }
           
         
        final  Handler handler =  new  Handler() {    
            public   void  handleMessage(Message message) {    
                ObjectCallback.objectLoaded((Drawable) message.obj, urlString);    
            }    
        };    
        new  Thread() {    
            @Override     
            public   void  run() {    
                T obj = loadObjFromUrl(urlString);    
                objectCache.put(urlString, new  SoftReference<T>(obj));   
               
                Message message = handler.obtainMessage(0 , obj);    
                handler.sendMessage(message);    
           
                saveObjToFile(urlToAssignString(urlString), obj);

            }    
        }.start();    
        return   null ;   
        
    }
    private T extracted(final String urlString) {
        return (T) statiCcacheSystem.loadObjFromCache(urlString);
    }  
     

}