package com.popsun.reflection.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 吴志祥
 * @create 2020-03-05 15:23
 */
public class Log {
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    public static void info(String info) {
        Date currentTime = Calendar.getInstance().getTime();
        System.out.println(SDF.format(currentTime) + ":" + info);
    }
}
