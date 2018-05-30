package com.skysearch.itm.skysearch.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateParser {

    public static int compare(String input){
        DateFormat formatter =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date= null;
        try {
            date = formatter.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar c = Calendar.getInstance();
        String now = "2018-02-15T19:00:00.000Z";
        Date dateNow=null;
        try {
            dateNow = formatter.parse(now);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(date.after(dateNow)){
            return 0;
        }else if(date.before(dateNow)){
            return 2;
        }else{
        return 1;
        }
    }

    public static Date toDate(String input){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date= null;
        try {
            date = sdf.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String setLocale(String input){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        formatter.setTimeZone(TimeZone.getTimeZone("KST"));
        String result = formatter.format(toDate(input));
        //Log.d("setLocale",result);

        return result;
    }
}
