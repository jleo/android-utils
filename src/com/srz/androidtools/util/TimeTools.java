package com.srz.androidtools.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeTools {
    
    //时间输入时均为GMT+8的时间，输出时按手机local时区
    public static String handleTimeWithTimezone(String timeString) {
        //  System.out.println(timeString);
          SimpleDateFormat df = new SimpleDateFormat("yyyy MM-dd HH:mm");
          df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
          try {
              Date date = df.parse(timeString);
              df.setTimeZone(TimeZone.getDefault());
              return df.format(date);
          } catch (ParseException e) {
              e.printStackTrace();
              return null;
          }  
      }
}
