/**
 * Create Date:2009-7-29
 */
package com.klspta.base.util.api;

/**
 * <br>Title:压缩工具
 * <br>Description:压缩和解压缩
 * <br>Author:陈强峰
 * <br>Date:2013-6-5
 */
public interface IZIPUtil {

    /**
     * <br>Description:压缩
     * <br>Author:陈强峰
     * <br>Date:2013-6-5
     * @param zipFileName  压缩最终完整路径
     * @param inputFile    需要压缩的文件夹
     */
    public void zip(String zipFileName, String inputFile);

    /**
     * <br>Description:解压缩
     * <br>Author:陈强峰
     * <br>Date:2013-6-5
     * @param zipFileName     压缩文件
     * @param outputDirectory  解压缩位置
     */
    public void unZip(String zipFileName, String outputDirectory);
}
