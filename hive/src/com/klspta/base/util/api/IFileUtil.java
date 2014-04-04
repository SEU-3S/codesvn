package com.klspta.base.util.api;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * <br>Title:文件上传工具
 * <br>Description:
 * <br>Author:郭润沛
 * <br>Date:2011-4-20
 */
public interface IFileUtil {
    /**
     * 
     * <br>Description:文件上传
     * <br>Author:郭润沛
     * <br>Date:2011-4-20
     * @param request：前台传递的request
     * @param sizeMaxKb：最大文件限制，传递0表示采用默认设置
     * @param sizeThresholdKb：缓冲区大小，传递0表示采用默认设置
     * @return：上传成功的文件在服务器中的保存路径
     * @throws Exception
     */
    public List<String> upload(HttpServletRequest request, int sizeMaxKb, int sizeThresholdKb)
            throws Exception;

    /**
     * 
     * <br>Description:如果是文件夹，则递归删除当前文件夹下所有文件；如果是文件，则直接删除
     * <br>Author:郭润沛
     * <br>Date:2011-4-20
     * @param filePath
     */
    public void deleteFile(File file);
}
