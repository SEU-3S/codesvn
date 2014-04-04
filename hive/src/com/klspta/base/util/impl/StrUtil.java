package com.klspta.base.util.impl;

import java.rmi.server.UID;
import java.util.regex.Pattern;

import com.klspta.base.util.api.IStrUtil;

/**
 * 
 * <br>Title:字符串工具类
 * <br>Description:提供全工程范围内的字符串操作方法
 * <br>Author:王瑛博
 * <br>Date:2011-5-3
 */
public class StrUtil implements IStrUtil {

    /**
     * 字符串工具实例
     */
    private static StrUtil instance = null;

    /**
     * 字节调整参数
     */
    private static final int BYTEADD = 0XFF;

    /**
     * 
     * <br>Description:构造方法私有化
     * <br>Author:王瑛博
     * <br>Date:2011-5-3
     */
    private StrUtil() {
    }

    /**
     * 
     * <br>Description:取得字符串工具实例
     * <br>Author:王瑛博
     * <br>Date:2011-5-3
     * @param key
     * @return
     * @throws Exception
     */
    public static StrUtil getInstance(String key) throws Exception {
        if (!key.equals("NEW WITH UTIL FACTORY!")) {
            throw new Exception("请从UtilFacory获取工具实例.");
        }
        if (instance == null) {
        	instance = new StrUtil();
        } 
        return instance;
    }

    /**
     * 
     * <br>Description:字节到字符串
     * <br>Author:王瑛博
     * <br>Date:2011-5-3
     * @see com.klspta.base.util.api.IStrUtil#byte2String(byte[])
     */
    public String byte2String(byte[] b) {
        StringBuffer hs = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & BYTEADD));
            if (stmp.length() == 1) {
                hs.append("0" + stmp);
            } else {
                hs.append(stmp);
            }
        }
        return hs.toString();
    }

    /**
     * 
     * <br>Description:转换
     * <br>Author:王瑛博
     * <br>Date:2011-5-3
     * @see com.klspta.base.util.api.IStrUtil#unescape(java.lang.String)
     */
    public String unescape(String src) {
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length());
        int lastPos = 0;
        int pos = 0;
        char ch;
        while (lastPos < src.length()) {
            pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (src.charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                } else {
                    ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            } else {
                if (pos == -1) {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                } else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();
    }

    /**
     * 
     * <br>Description:字符转换
     * <br>Author:王瑛博
     * <br>Date:2011-5-3
     * @see com.klspta.base.util.api.IStrUtil#escape(java.lang.String)
     */
    public String escape(String src) {
        int i;
        char j;
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length() * 6);
        for (i = 0; i < src.length(); i++) {
            j = src.charAt(i);
            if ((Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))) {
                tmp.append(j);
            } else if (j < 256) {
                tmp.append("%");
                if (j < 16) {
                    tmp.append("0");
                }
                tmp.append(Integer.toString(j, 16));
            } else {
                tmp.append("%u");
                tmp.append(Integer.toString(j, 16));
            }
        }
        return tmp.toString();
    }

    public String getGuid() {
        return new UID().toString().replaceAll(":", "-");
    }

    public String changeKeyWord(String inString, String keywords) {
        if (inString == null || inString.length() == 0 || keywords == null || keywords.trim().length() == 0) {
            return inString;
        }
        keywords = keywords.trim();
        while (keywords.indexOf("  ") > 0) {//循环去掉多个空格，所有字符中间只用一个空格间隔
            keywords = keywords.replace("  ", " ");
        }
        String keyWord[] = keywords.split(" ");//转换正则
        for (int i = 0; i < keyWord.length; i++) {
            inString = inString.replaceAll(Pattern.quote(keyWord[i]), "~"
                    + (Pattern.quote(keyWord[i]).substring(2, Pattern.quote(keyWord[i]).length() - 2))
                    + "</B>");
        }
        return inString.replaceAll("~", "<B style='color:black;background-color:#009900'>");
    }

    @Override
    public String manageStr(int source, int digit) {
        int source_digit = (source + "").length();
        if (source_digit >= digit) {
            return source + "";
        } else {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digit - source_digit; i++) {
                sb.append("0");
            }
            return sb.toString() + source;
        }
    }
}
