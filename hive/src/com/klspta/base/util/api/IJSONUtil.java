/**
 * Create Date:2009-7-29
 */
package com.klspta.base.util.api;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <br>Title:json工具类
 * <br>Description:处理json对象的转换
 * <br>Author:陈强峰
 * <br>Date:2013-6-6
 */
public interface IJSONUtil {

    String objectToJSON(Object object) throws Exception;

    JSONObject jsonToObject(String json) throws Exception;

    JSONArray jsonToObjects(String json) throws Exception;

    String format(String json);
}
