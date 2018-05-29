package com.skysearch.itm.skysearch.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;

public class DBHandler_casting {
    private DBHelper helper;
    private SQLiteDatabase db;

    private DBHandler_casting(Context ctx) {
        this.helper = new DBHelper(ctx);
        this.db = helper.getWritableDatabase();
    }

    public static DBHandler_casting open(Context ctx) throws SQLException {
        DBHandler_casting handler = new DBHandler_casting(ctx);
        return handler;
    }

    public void close() {
        helper.close();
    }

    public long insert(String day, int time, String option, String group, String email) {
        ContentValues values = new ContentValues();
        //value 입력
        values.put("day", day);

        return db.insert("디비명", null, values);
    }

    public long update(String day, int time, String option, String group, String email) {
        ContentValues values = new ContentValues();
        values.put("option", option);

        String where = "day = '" + day + "'"+" AND "+"time = '"+time+"'"+" AND "+"name = '"+group+"'"+" AND "+"email = '"+email+"'";
        return db.update("디비명", values, where, null);
    }

    public long delete(String email) {
        String where = "email = '" + email + "'";
        return db.delete("디비명", where, null);
    }


    public Cursor select(int id) throws SQLException {
        Cursor cursor = db.query(true, "schedule", new String[]{"_id", "date", "time", "option"},
                "_id" + "=" + id, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    public Cursor select(String group, String email) throws SQLException {
        Log.i("Schedule","select::"+group+email);
        Cursor cursor = db.query(true, "schedule", null,
                "email='" + email + "'" + " AND " + "name='" + group + "'", null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

}