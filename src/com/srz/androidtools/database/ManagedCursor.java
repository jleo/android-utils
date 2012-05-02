package com.srz.androidtools.database;

import android.database.Cursor;

public class ManagedCursor {
    private Cursor cursor;

    public ManagedCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public void each(EachCursor ec) {
        int i = 0;
        try {
            while (cursor.moveToNext()) {
                ec.call(cursor, i++);
            }
        } finally {
            cursor.close();
        }
    }
}