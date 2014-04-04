package com.klspta.base.util.api;

public interface IStrUtil {
    /**
     * 
     * <br>Description:字节到字符串
     * <br>Author:王瑛博
     * <br>Date:2011-5-3
     * @see com.klspta.base.util.api.IStrUtil#byte2String(byte[])
     */
    public String byte2String(byte[] b);

    /**
     * 
     * <br>Description:转换
     * <br>Author:王瑛博
     * <br>Date:2011-5-3
     * @see com.klspta.base.util.api.IStrUtil#unescape(java.lang.String)
     */
    public String unescape(String src);

    /**
     * 
     * <br>Description:字符转换
     * <br>Author:王瑛博
     * <br>Date:2011-5-3
     * @see com.klspta.base.util.api.IStrUtil#escape(java.lang.String)
     */
    public String escape(String src);

    /**
     * <br>Description:获取guid
     * <br>Author:陈强峰
     * <br>Date:2013-6-5
     * @return
     */
    public String getGuid();

    /**
     * <br>Description:美化关键字
     * <br>Author:陈强峰
     * <br>Date:2013-6-5
     * @param inString
     * @param keywords
     * @return
     */
    public String changeKeyWord(String inString, String keywords);

    /*
     * 返回指定字符串
     */
    public String manageStr(int source, int digit);
}
