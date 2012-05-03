package com.srz.androidtools.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DBManager {
    private static final int BUFFER_SIZE = 1048576;

    private static Map<String, SQLiteDatabase> dbMap = new HashMap<String, SQLiteDatabase>();
    private static DBManager dbManager;

    public static final String DB_INFO_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() +"/";


    public static synchronized SQLiteDatabase getDatabase(String databaseName,Context context) {
//          if (dbMap.containsKey(databaseName) && dbMap.get(databaseName).isOpen()) {     //
//              return dbMap.get(databaseName);                                            //
//          } else {                                                                       //
            SQLiteDatabase sqLiteDatabase = openDatabase(getFilePath(context, databaseName));
//             dbMap.put(databaseName, sqLiteDatabase);                                    //
            return sqLiteDatabase;
//        }                                                                                 //
    }

    public static boolean initialized(Context context, String databaseName) {
        return new File(getFilePath(context, databaseName)).exists() ;//  && new File(dbInfoPath.get(DB_NAME_MAP)).exists(); //
    }

    private static String getFilePath(Context context, String databaseName) {
        return DB_INFO_PATH +context.getPackageName()+ "/databases/" + databaseName;
    }

    public static void initialize(DBInitializedListener dbInitializedListener, Context context, int resourceID, String databaseName) {
        try {
            initialize(databaseName, context,resourceID);
        } catch (Exception e) {
            dbInitializedListener.onFailedToInitialzed();
            return;
        }
        dbInitializedListener.onInitialized();
    }

    private static void initialize(String databaseName, Context context, int resourceID) {
        int databaseRawFileId = resourceID;
        FileOutputStream fos = null;
        InputStream is = null;
        
        try {
            String dbFilePath = getFilePath(context,databaseName);
            if (!new File(dbFilePath).exists()) {
                 
               if( !new File(new File(dbFilePath).getParent()).exists()   ) {
                   new File(new File(dbFilePath).getParent()).mkdirs();
                 
               } 
                
                is = context.getResources().openRawResource(
                        databaseRawFileId);
                fos = new FileOutputStream(dbFilePath);
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count); 
                }

            }
        } catch (FileNotFoundException e) {
            Log.e("Database", "File not found!");
        } catch (IOException e) {
            Log.e("Database", "IO exception");
        } finally {
            try {
                if (fos != null)
                    fos.close();
                if (is != null)
                    is.close();
            } catch (Exception e) {

            }
        }

    }

    private static SQLiteDatabase openDatabase(String dbFilePath) {
        return SQLiteDatabase.openOrCreateDatabase(dbFilePath,
                null);
    }
}