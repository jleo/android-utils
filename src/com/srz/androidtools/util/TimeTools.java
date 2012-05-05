package com.srz.androidtools.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeTools {
    public static String TIMEFORMATSTRING =  "yyyy MM-dd HH:mm";
    
    //时间输入时均为GMT+8的时间，输出时按手机local时区
    public static String handleTimeWithTimezone(String timeString) {
        //  System.out.println(timeString);
          SimpleDateFormat df = new SimpleDateFormat(TIMEFORMATSTRING);
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
    
    //时间输入时为date ，输出时按手机local时区
    public static String handleTimeWithTimezone(Date date) {
        //  System.out.println(timeString);
          SimpleDateFormat df = new SimpleDateFormat(TIMEFORMATSTRING);
          df.setTimeZone(TimeZone.getDefault());
          return df.format(date);
          
      }
}
