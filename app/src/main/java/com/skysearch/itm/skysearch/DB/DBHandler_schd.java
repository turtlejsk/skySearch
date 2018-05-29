package com.skysearch.itm.skysearch.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DBHandler_schd {
    private DBHelper helper;
    private SQLiteDatabase db;

    private DBHandler_schd(Context ctx) {
        this.helper = new DBHelper(ctx);
        this.db = helper.getWritableDatabase();
    }

    public static DBHandler_schd open(Context ctx) throws SQLException {
        DBHandler_schd handler = new DBHandler_schd(ctx);
        return handler;
    }

    public void close() {
        helper.close();
    }

    public long insert(String CH_id, String EP_id, String CH_NAME, String ACTN, String TITLE, String ST_TIME, String EN_TIME) {
        ContentValues values = new ContentValues();
        //value 입력
        values.put("CH_id", Integer.parseInt(CH_id));
        values.put("EP_id", Integer.parseInt(EP_id));
        values.put("CH_NAME", CH_NAME);
        values.put("ACT", ACTN);
        values.put("TITLE", TITLE);
        values.put("ST_TIME", ST_TIME);
        values.put("EN_TIME", EN_TIME);
        return db.insert("schd", null, values);
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


    public Cursor select(int ch_id, String st_date) throws SQLException {
        Log.i("Schedule","select:: channel:"+ch_id+", st_date:"+st_date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Date date= null;
        try {
            date = sdf.parse(st_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 2);
        date = c.getTime();
        date.toString();
        String aft_st_date = sdf.format(date);

        String rawQ = "SELECT CH_id, EP_id, CH_NAME, ACT, TITLE, ST_TIME FROM schd WHERE ST_TIME between '"+st_date+"' AND '"+aft_st_date+"'";
        Cursor cursor = db.rawQuery(rawQ,null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;


    }

}