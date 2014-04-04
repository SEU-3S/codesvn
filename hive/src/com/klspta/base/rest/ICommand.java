package com.klspta.base.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * <br>Title:TODO 类标题
 * <br>Description:TODO 类功能描述
 * <br>Author:王瑛博
 * <br>Date:2011-5-3
 */
public interface ICommand {
    /**
     * 
     * <br>Description:TODO 方法功能描述
     * <br>Author:王瑛博
     * <br>Date:2011-5-3
     * @param request
     * @param response
     * @param method
     * @throws Exception
     */
    void dispatchMethod(HttpServletRequest request, HttpServletResponse response, String method) throws Exception;
}
