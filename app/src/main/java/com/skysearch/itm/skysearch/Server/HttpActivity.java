package com.skysearch.itm.skysearch.Server;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.skysearch.itm.skysearch.DB.DBHandler_prog;
import com.skysearch.itm.skysearch.DB.DBHandler_schd;
import com.skysearch.itm.skysearch.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;


public class HttpActivity extends AppCompatActivity {

    private TextView tv_outPut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);


        // 위젯에 대한 참조.
        tv_outPut = (TextView) findViewById(R.id.tv_outPut);

        // URL 설정.
        String url = "http://211.211.54.158:3000/schd";
        NetworkThread nt2 = new NetworkThread(url, null, "schd");
        Log.i("schd","create");
        nt2.start();
        Log.i("schd","run");
        url = "http://211.211.54.158:3000/prog";
        // AsyncTask를 통해 HttpURLConnection 수행.
        //NetworkTask networkTask = new NetworkTask(url, null, "prog");
        //networkTask.execute();
        NetworkThread nt1 = new NetworkThread(url,null,"prog");
        Log.i("prog","create");
        nt1.start();
        Log.i("prog","run");

        //NetworkTask networkTask2 = new NetworkTask(url, null, "schd");
        //networkTask2.execute();
        Toast.makeText(this, "Next", Toast.LENGTH_SHORT).show();
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;
        private String table_name;

        private static final String TABLE_TAG_PROG="prog";
        private static final String TABLE_TAG_SCHD="schd";

        public NetworkTask(String url, ContentValues values, String table_name) {
            this.url = url;
            this.values = values;
            this.table_name = table_name;
        }
        public void doJSONParser(String str) {
            StringBuffer sb = new StringBuffer();
            if(this.table_name==TABLE_TAG_PROG) {
                DBHandler_prog dbHandler_prog;

                try {
                    dbHandler_prog = DBHandler_prog.open(getApplicationContext());
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

                }
                    Log.i("prog","done");
                } catch (JSONException e) {
                    e.printStackTrace();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else if(this.table_name == TABLE_TAG_SCHD){
                DBHandler_schd dbHandler_schd;

                try {
                    dbHandler_schd = DBHandler_schd.open(getApplicationContext());
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

                        dbHandler_schd.insert(CH_id, EP_id,CH_NAME, ACTN,TITLE, ST_TIME, EN_TIME);
                        Log.i("schd","ing");
                    }
                    Log.i("schd","done");
                    Toast.makeText(getApplicationContext(),"done",Toast.LENGTH_LONG );
                } catch (JSONException e) {
                    e.printStackTrace();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.

            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();

            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            doJSONParser(s);
            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            //tv_outPut.setText(s);
        }
    }

    class NetworkThread extends Thread{
        private String url;
        private ContentValues values;
        private String table_name;

        private static final String TABLE_TAG_PROG="prog";
        private static final String TABLE_TAG_SCHD="schd";

        public NetworkThread(String url, ContentValues values, String table_name){
            super();
            this.url = url;
            this.values = values;
            this.table_name = table_name;
        }
        public void run(){
            NetworkTask networkTask = new NetworkTask(this.url, null, this.table_name);
            networkTask.execute();
        }
    }
}
