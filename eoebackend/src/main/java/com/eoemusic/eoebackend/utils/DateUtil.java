package com.eoemusic.eoebackend.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: some desc
 * @author: Xiaoyu Wu
 * @email: wu.xiaoyu@northeastern.edu
 * @date: 09/02/23 4:55 PM
 */
@Slf4j
public class DateUtil {

  private static final ThreadLocal<DateFormat> threadlocalDetailFormat = ThreadLocal
      .withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));

  private static final ThreadLocal<DateFormat> threadlocalMonthFormat = ThreadLocal
      .withInitial(() -> new SimpleDateFormat("yyyy.MM"));

  public static String transformDetailTime(Date resourceDate) {
    return threadlocalDetailFormat.get().format(resourceDate);
  }

  public static int[] parseYearMonth(String resourceYearMonth) {
    int[] yearMonth = new int[2];
    try {
      Date date = threadlocalMonthFormat.get().parse(resourceYearMonth);
      Calendar cal = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
      cal.setTime(date);
      yearMonth[0] = cal.get(Calendar.YEAR);
      yearMonth[1] = cal.get(Calendar.MONTH) + 1;
    } catch (ParseException e) {
      log.error("parse year and month failed, {}", e);
    }
    return yearMonth;
  }

  public static void clear() {
    threadlocalDetailFormat.remove();
    threadlocalMonthFormat.remove();
  }
}
