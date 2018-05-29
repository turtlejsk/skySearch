package com.skysearch.itm.skysearch.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;

public class DBHandler_prog {
    private DBHelper helper;
    private SQLiteDatabase db;

    private DBHandler_prog(Context ctx) {
        this.helper = new DBHelper(ctx);
        this.db = helper.getWritableDatabase();
    }

    public static DBHandler_prog open(Context ctx) throws SQLException {
        DBHandler_prog handler = new DBHandler_prog(ctx);
        return handler;
    }

    public void close() {
        helper.close();
    }

    public long insert(String SCHD_id, String PROG_NAME, String CP, String GENRE, String PROG_DATE, String VOD_EXST, String PSTR, String PD, String PROG_DESCR, String PROG_AIR) {
        ContentValues values = new ContentValues();
        //value 입력
        values.put("SCHD_id", Integer.parseInt(SCHD_id));
        values.put("PROG_NAME", PROG_NAME);
        values.put("CP", CP);
        values.put("GENRE", GENRE);
        values.put("PROG_DATE", PROG_DATE);
        values.put("VOD_EXST", VOD_EXST);
        values.put("PSTR", PSTR);
        values.put("PD", PD);
        values.put("PROG_DESCR", PROG_DESCR);
        //values.put("PROG_AIR", Integer.parseInt(PROG_AIR));

        return db.insert("prog", null, values);
    }

    public long update(String day, int time, String option, String group, String email) {
        ContentValues values = new ContentValues();
        values.put("option", option);

        String where = "day = '" + day + "'"+" AND "+"time = '"+time+"'"+" AND "+"name = '"+group+"'"+" AND "+"email = '"+email+"'";
        return db.update("prog", values, where, null);
    }

    public long delete(String email) {
        String where = "email = '" + email + "'";
        return db.delete("prog", where, null);
    }


    public Cursor select(int id) throws SQLException {
        Cursor cursor = db.query(true, "prog", new String[]{"_id", "date", "time", "option"},
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