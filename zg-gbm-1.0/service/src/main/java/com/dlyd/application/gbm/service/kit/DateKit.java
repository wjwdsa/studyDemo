package com.dlyd.application.gbm.service.kit;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类型
 *
 * @author yangbing
 * @date 2020-06-02 11:33:38
 */
public class DateKit {
  /**
   * 时间格式(yyyyMMdd)
   */
  public final static String DATE_PATTERN = "yyyyMMdd";
  public final static String TIME_PATTERN = "HHmmss";
  public final static String DATE_TIME_PATTERN = "yyyyMMddHHmmss";


  public static String dateFormat(Date date) {
    return dateFormat(date, DATE_PATTERN);
  }

  public static String timeFormat(Date date) {
    return dateFormat(date, TIME_PATTERN);
  }

  public static String dateFormat(Date date, String pattern) {
    if (date != null) {
      SimpleDateFormat df = new SimpleDateFormat(pattern);
      return df.format(date);
    }
    return null;
  }


  public static String dateFormat(long timestamp) {
    return dateFormat(timestamp, DATE_PATTERN);
  }

  public static String dateFormat(long timestamp, String pattern) {
    Date date = new Date(timestamp);
    return dateFormat(date, pattern);
  }

  public static String dateFormat(String date, String pattern) throws ParseException {
    Date parse = parse(date, DATE_PATTERN);
    return dateFormat(parse, pattern);
  }

  public static String dateFormat(String date) throws ParseException {
    Date parse = parse(date, DATE_PATTERN);
    return dateFormat(parse, "yyyy-MM-dd");
  }


  public static String yesterday(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_MONTH, -1);
    date = calendar.getTime();
    return dateFormat(date);
  }

  public static String yesterday(Date date, String pattern) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_MONTH, -1);
    date = calendar.getTime();
    return dateFormat(date, pattern);
  }


  public static String yesterday(String date) throws ParseException {
    Date parse = parse(date, DATE_PATTERN);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(parse);
    calendar.add(Calendar.DAY_OF_MONTH, -1);
    Date currDate = calendar.getTime();
    return dateFormat(currDate, "yyyy-MM-dd");
  }

  public static String yesterday(String date, String pattern) throws ParseException {
    Date parse = parse(date, DATE_PATTERN);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(parse);
    calendar.add(Calendar.DAY_OF_MONTH, -1);
    Date currDate = calendar.getTime();
    return dateFormat(currDate, pattern);
  }

  public static String tomorrow(String date, String pattern) throws ParseException {
    Date parse = parse(date, DATE_PATTERN);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(parse);
    calendar.add(Calendar.DAY_OF_MONTH, +1);
    Date currDate = calendar.getTime();
    return dateFormat(currDate, pattern);
  }


  public static String yesterday(String date, String parsePattern, String returnPattern) throws ParseException {
    Date parse = parse(date, parsePattern);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(parse);
    calendar.add(Calendar.DAY_OF_MONTH, -1);
    Date currDate = calendar.getTime();
    return dateFormat(currDate, returnPattern);
  }

  /**
   * 判断两个日期相差的时长<br/>
   * 返回 minuend - subtrahend 的差
   *
   * @param subtrahend 减数日期
   * @param minuend    被减数日期
   * @param diffField  相差的选项：相差的天、小时
   * @return 日期差
   */
  public static long diff(Date subtrahend, Date minuend, long diffField) {
    long diff = minuend.getTime() - subtrahend.getTime();
    return diff / diffField;
  }

  public static Date parse(String dateString) throws ParseException {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_PATTERN);
    return simpleDateFormat.parse(dateString);
  }

  public static Date parse(String dateString, String pattern) throws ParseException {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    return simpleDateFormat.parse(dateString);
  }

}
