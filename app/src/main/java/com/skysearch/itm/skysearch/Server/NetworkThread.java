package com.skysearch.itm.skysearch.Server;

import android.content.ContentValues;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.skysearch.itm.skysearch.DB.DBHandler_prog;
import com.skysearch.itm.skysearch.DB.DBHandler_schd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

public class NetworkThread extends Thread {

    private String url;
    private ContentValues values;
    private String table_name;
    private Context mContext;
    private static final String TABLE_TAG_PROG="prog";
    private static final String TABLE_TAG_SCHD="schd";
    private String result;
    public NetworkThread(String url, ContentValues values, String table_name,Context context){
        super();
        this.url = url;
        this.mContext =context;
        this.values = values;
        this.table_name = table_name;
    }



    private Handler handler = new Handler();

    // 이 메서드는 메인 GUI 스레드에서 호출된다.
    public void mainProcessing(){
        // 이는 시간이 많이 드는 작업을 자식 스레드로 옮긴다.
        Thread thread = new Thread(null, doBackgroundThreadProcessing, "Background");
        Log.i("NetworkThread","mainProcessing");
        thread.start();
    }

    // 백그라운드 처리 메서드를 실행하는 Runnable
    private Runnable doBackgroundThreadProcessing = new Runnable(){
        @Override
        public void run() {

            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();

            result = requestHttpURLConnection.request(url, values);
            Log.i("NetworkThread","doBackgroundThreadProcessing");
            backgroundThreadProcessing();
        }
    };

    // 백그라운드에서 몇 가지 처리를 수행하는 메서드
    private void backgroundThreadProcessing(){
        //[필요한 코드]
        // 처리가 끝나고 결과를 UI로 출력을 해야 할 때 아래 핸들러를 추가해서 사용한다.

        handler.post(doUpdate);
        Log.i("NetworkThread","backgroundThreadProcessing");
    }

    // GUI 업데이트 메서드를 실행하는 Runnable.
    private Runnable doUpdate = new Runnable(){
        @Override
        public void run() {
            update(result);
            Log.i("NetworkThread","doUpdate");
        }

    };

    private void update(String str) {
        // [[ 필요한 UI 코드 ]]
        Log.i("NetworkThread","update");
        StringBuffer sb = new StringBuffer();
        if(this.table_name==TABLE_TAG_PROG) {
            DBHandler_prog dbHandler_prog;

            try {
                dbHandler_prog = DBHandler_prog.open(this.mContext);
                JSONArray jarray = new JSONArray(str);   // JSONArray 생성
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);  // JSONObject 추출
                    String PROG_id = jObject.getString("PROG_id"); //int
                    String SCHD_id = jObject.getString("SCHD_id"); //int
                    String PROG_NAME = jObject.getString("PROG_NAME"); //String
                    String CP = jObject.getString("CP"); //String
                    String GENRE = jObject.getString("GENRE"); //String
                    String PROG_DATE = jObject.getString("PROG_DATE"); //Date
                    String VOD_EXST = jObject.getString("VOD_EXST"); //String
                    String PSTR = jObject.getString("PSTR"); //String
                    String PD = jObject.getString("PD"); //String
                    String PROG_DESCR = jObject.getString("PROG_DESCR"); //String
                    String PROG_AIR = jObject.getString("PROG_AIR"); //int

                    dbHandler_prog.insert(SCHD_id, PROG_NAME, CP, GENRE, PROG_DATE, VOD_EXST, PSTR, PD, PROG_DESCR, PROG_AIR);
                    Log.i("NetworkThread::prog","insert");
                }
                Log.i("NetworkThread::prog","done");
            } catch (JSONException e) {
                e.printStackTrace();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if(this.table_name == TABLE_TAG_SCHD){
            DBHandler_schd dbHandler_schd;

            try {
                dbHandler_schd = DBHandler_schd.open(this.mContext);
                JSONArray jarray = new JSONArray(str);   // JSONArray 생성
                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);  // JSONObject 추출
                    String SCHD_id = jObject.getString("SCHD_id"); //int
                    String CH_id = jObject.getString("CH_id"); //int
                    String EP_id = jObject.getString("EP_id"); //String
                    String CH_NAME = jObject.getString("CH_NAME"); //String
                    String ACTN = jObject.getString("ACTN"); //String

                    String TITLE = jObject.getString("TITLE"); //String
                    String ST_TIME = jObject.getString("ST_TIME"); //String
                    String EN_TIME = jObject.getString("EN_TIME"); //Date

                    dbHandler_schd.insert(CH_id, EP_id, CH_NAME, ACTN, TITLE, ST_TIME, EN_TIME);
                    Log.i("NetworkThread::schd","insert");
                }
                Log.i("NetworkThread::schd","done");
            } catch (JSONException e) {
                e.printStackTrace();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
