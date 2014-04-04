package com.klspta.base.util.impl;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.klspta.base.util.api.IDateUtil;

public class DateUtil implements IDateUtil {
    private static DateUtil instance;
    
    public static IDateUtil getInstance(String key) throws Exception {
        if (!key.equals("NEW WITH UTIL FACTORY!")) {
            throw new Exception("请通过UtilFactory获取实例.");
        }
        if (instance == null) {
        	instance = new DateUtil();
        }
        return instance;
    }

    public String getChineseDate(Date d) {
        return getFormatDate(FORMAT_CHINESE, d);
    }

    public String getCurrentChineseDate() {
        Calendar calendar = Calendar.getInstance();
        return getChineseDate(calendar.getTime());
    }

    public String getSimpleDate(Date d) {
    	return getFormatDate(FORMAT_SIMPLE, d);
    }
    
    public String getFormatDate(String _format, Date d){
        if (d == null) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat(_format, new DateFormatSymbols());
        return df.format(d);
    }
}
