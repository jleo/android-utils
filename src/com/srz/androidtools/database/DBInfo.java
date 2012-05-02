package com.srz.androidtools.database;

/**
 * Created by IntelliJ IDEA.
 * User: jleo
 * Date: 12-2-21
 * Time: 下午4:27
 * To change this template use File | Settings | File Templates.
 */
public class DBInfo {
    private String databaseName;
    private int databaseRawFile;

    public DBInfo(String databaseName, int databaseRawFile) {

        this.databaseName = databaseName;
        this.databaseRawFile = databaseRawFile;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public int getDatabaseRawFile() {
        return databaseRawFile;
    }
}
