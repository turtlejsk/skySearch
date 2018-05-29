package com.skysearch.itm.skysearch.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;

public class DBHandler_ch {
    private DBHelper helper;
    private SQLiteDatabase db;

    private DBHandler_ch(Context ctx) {
        this.helper = new DBHelper(ctx);
        this.db = helper.getWritableDatabase();
    }

    public static DBHandler_ch open(Context ctx) throws SQLException {
        DBHandler_ch handler = new DBHandler_ch(ctx);
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


    public Cursor select() throws SQLException {
        String rawQ = "SELECT CH_NAME, CH_NUM, CH_CATEG, CH_DESCR FROM ch ORDER BY CH_NUM ASC";
        Cursor cursor = db.rawQuery(rawQ,null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    public Cursor select_asc(int ch_num) throws SQLException {
        String rawQ = "SELECT CH_NAME, CH_NUM, CH_CATEG, CH_DESCR FROM ch WHERE CH_NUM LIKE '%"+ch_num+"%' ORDER BY CH_NUM ASC";
        Cursor cursor = db.rawQuery(rawQ,null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
}
