package com.skysearch.itm.skysearch.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ä�� on 2015-07-16.
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "SJS.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String casting = "CREATE TABLE IF NOT EXISTS casting (\n" +
                "PROG_id INTEGER NOT NULL,\n" +
                "PRSN_id INTEGER DEFAULT NULL,\n" +
                "CHAR_LV INTEGER DEFAULT NULL,\n" +
                "LEAD_LV INTEGER DEFAULT NULL,\n" +
                "PRIMARY KEY (PROG_id)"+
                "); ";
        db.execSQL(casting);

        String ch = "CREATE TABLE IF NOT EXISTS ch (\n" +
                "CH_id INTEGER NOT NULL,\n" +
                "CH_NAME TEXT DEFAULT NULL,\n" +
                "CH_NUM INTEGER DEFAULT NULL,\n" +
                "CH_CATEG TEXT DEFAULT NULL,\n" +
                "CH_DESCR TEXT DEFAULT NULL,\n" +
                "PRIMARY KEY (CH_id)\n" +
                ");";
        db.execSQL(ch);

        String ep = "CREATE TABLE IF NOT EXISTS ep (\n" +
                "EP_id INTEGER NOT NULL,\n" +
                "EP_NUM INTEGER DEFAULT NULL,\n" +
                "EP_DATE datetime DEFAULT NULL,\n" +
                "PROG_id INTEGER DEFAULT NULL,\n" +
                "EP_VOD_EXST INTEGER DEFAULT NULL,\n" +
                "EP_DESCR TEXT DEFAULT NULL,\n" +
                "PRIMARY KEY (EP_id)\n"+
                ");";
        db.execSQL(ep);

        String movie = "CREATE TABLE IF NOT EXISTS movie (\n" +
                "  MV_id INTEGER NOT NULL,\n" +
                "  MV_TITLE TEXT DEFAULT NULL,\n" +
                "  MV_ADMIT INTEGER DEFAULT NULL,\n" +
                "  MV_RUN INTEGER DEFAULT NULL,\n" +
                "  MV_PSTR TEXT DEFAULT NULL,\n" +
                "  MV_GENRE TEXT DEFAULT NULL,\n" +
                "  MV_OPEN date DEFAULT NULL,\n" +
                "  PRIMARY KEY (MV_id)\n" +
                ");";
        db.execSQL(movie);


        String my_ch ="CREATE TABLE IF NOT EXISTS my_ch (\n" +
                "  MYCH_id INTEGER NOT NULL,\n" +
                "  CH_id INTEGER NOT NULL,\n" +
                "  USER_id INTEGER NOT NULL,\n" +
                "  MYCHorNOT INTEGER NOT NULL,\n" +
                "  PRIMARY KEY (MYCH_id)\n" +
                ");";
        db.execSQL(my_ch);

        String prog = "CREATE TABLE IF NOT EXISTS prog (\n" +
                "PROG_id INTEGER NOT NULL,\n" +
                "SCHD_id INTEGER NOT NULL,\n" +
                "PROG_NAME TEXT DEFAULT NULL,\n" +
                "CP TEXT NOT NULL,\n" +
                "GENRE TEXT DEFAULT NULL,\n" +
                "PROG_DATE date DEFAULT NULL,\n" +
                "VOD_EXST TEXT DEFAULT NULL,\n" +
                "PSTR TEXT DEFAULT NULL,\n" +
                "PD TEXT DEFAULT NULL,\n" +
                "PROG_DESCR varchar(100) DEFAULT NULL,\n" +
                "PROG_AIR INTEGER DEFAULT NULL,\n" +
                "PRIMARY KEY (PROG_id)\n" +
                ");";
        db.execSQL(prog);

        String prsn = "CREATE TABLE IF NOT EXISTS prsn (\n" +
                "PRSN_id INTEGER NOT NULL,\n" +
                "PRSN_NAME TEXT DEFAULT NULL,\n" +
                "PRSN_PIC TEXT DEFAULT NULL,\n" +
                "PRSN_BIRTH date DEFAULT NULL,\n" +
                "PRSN_GRP TEXT DEFAULT NULL,\n" +
                "PRIMARY KEY (PRSN_id)\n" +
                ");";
        db.execSQL(prsn);

        String rsrv_mgmt = "CREATE TABLE IF NOT EXISTS rsrv_mgmt (\n" +
                "USER_id INTEGER NOT NULL,\n" +
                "SCHD_id INTEGER NOT NULL,\n" +
                "STATE INTEGER DEFAULT NULL,\n" +
                "PRIMARY KEY (USER_id,SCHD_id)\n" +
                ");";
        db.execSQL(rsrv_mgmt);

        String schd = "CREATE TABLE IF NOT EXISTS schd (\n" +
                "SCHD_id INTEGER NOT NULL,\n" +
                "CH_id INTEGER DEFAULT NULL,\n" +
                "EP_id INTEGER DEFAULT NULL,\n" +
                "CH_NAME TEXT DEFAULT NULL,\n" +
                "ACT TEXT DEFAULT NULL,\n" +
                "TITLE TEXT DEFAULT NULL,\n" +
                "ST_TIME datetime DEFAULT NULL,\n" +
                "EN_TIME datetime DEFAULT NULL,\n" +
                "PRIMARY KEY (SCHD_id)\n" +
                ");";
        db.execSQL(schd);

        String srch_his = "CREATE TABLE IF NOT EXISTS srch_his (\n" +
                "USER_id INTEGER NOT NULL,\n" +
                "SRCH_KEY TEXT DEFAULT NULL,\n" +
                "SRCH_DATE datetime DEFAULT NULL,\n" +
                "PRIMARY KEY (USER_id)\n" +
                ");";
        db.execSQL(srch_his);

        String vod_his = "CREATE TABLE IF NOT EXISTS vod_his (\n" +
                "USER_id INTEGER NOT NULL,\n" +
                "PROG_id INTEGER NOT NULL,\n" +
                "WTCH_DATE datetime DEFAULT NULL,\n" +
                "PRIMARY KEY (USER_id)\n" +
                ");";
        db.execSQL(vod_his);

        String wtch_his = "CREATE TABLE IF NOT EXISTS wtch_his (\n" +
                "  USER_id INTEGER NOT NULL,\n" +
                "  PROG_id INTEGER NOT NULL,\n" +
                "  SCHD_id INTEGER NOT NULL,\n" +
                "  PRIMARY KEY (USER_id)\n" +
                ");";
        db.execSQL(wtch_his);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
