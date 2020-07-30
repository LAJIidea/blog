package com.kingkiller.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    public static String formateString(Date date,String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String str = sdf.format(date);
        return str;
    }
}
