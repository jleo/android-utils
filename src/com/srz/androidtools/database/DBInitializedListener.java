package com.srz.androidtools.database;

/**
 * Created by IntelliJ IDEA.
 * User: jleo
 * Date: 12-2-21
 * Time: 下午8:44
 * To change this template use File | Settings | File Templates.
 */
public interface DBInitializedListener {
    public void onInitialized();
    public void onFailedToInitialzed();
}
