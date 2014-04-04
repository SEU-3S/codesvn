/**
 * Create Date:2009-7-29
 */
package com.klspta.base.util.api;

/**
 * <br>Title:配置管理工具
 * <br>Description:获取配置信息
 * <br>Author:陈强峰
 * <br>Date:2013-6-5
 */
public interface IConfigUtil {

    /**
     * <br>Description:shape文件临时目录
     * <br>Author:陈强峰
     * <br>Date:2013-6-5
     * @return
     */
    public String getShapefileTempPathFloder();

    /**
     * <br>Description:安全认证
     * <br>Author:陈强峰
     * <br>Date:2013-6-5
     * @return
     */
    public boolean isSecurityUseable();

    /**
     * <br>Description:验证安全URL
     * <br>Author:陈强峰
     * <br>Date:2013-6-5
     * @return
     */
    public String getSecurityVerifyURL();

    /**
     * <br>Description:可通行IP
     * <br>Author:陈强峰
     * <br>Date:2013-6-5
     * @return
     */
    public String getSecurityPassIPs();

    /**
     * <br>Description:配置文件
     * <br>Author:陈强峰
     * <br>Date:2013-6-5
     * @param key
     * @return
     */
    public String getConfig(String key);

    /**
     * <br>Description:配置的double值
     * <br>Author:陈强峰
     * <br>Date:2013-6-5
     * @param key
     * @return
     */
    
    public double getConfigDouble(String key);
    
    /**
     * <br>Description:根据异常码获取中文描述
     * <br>Author:陈强峰
     * <br>Date:2013-6-5
     * @param key
     * @return
     */
    public String getExceptionDescribe(String key);

    /**
     * <br>Description:获取URL路径
     * <br>Author:陈强峰
     * <br>Date:2013-6-5
     * @return
     */
    public String getApppath();
}
