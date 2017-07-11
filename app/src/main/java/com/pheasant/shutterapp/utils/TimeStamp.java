package com.pheasant.shutterapp.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by Peszi on 2017-06-16.
 */

public class TimeStamp {
    public static Date getTimeDate(String time) {
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            format.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getLiveTime(Date date) {
        Date currentTime = new Date();
        long difference = currentTime.getTime() - date.getTime();
        long days = TimeUnit.MILLISECONDS.toDays(difference);
        if (days <= 0)
            return "last 24h";
//        if (days <= 1)
//        return "1d ago";
//        if (days == 2)
//            return "yesterday";
        return days + "d";
    }
}
