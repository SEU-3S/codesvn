package com.klspta.base;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.klspta.base.util.UtilFactory;

/**
 * 
 * <br>
 * Title: 抽象rest类，留着以后扩展用 <br>
 * Description:rest入口三层过滤之一，目前无具体内容，用于以后扩展 <br>
 * Author:王瑛博 <br>
 * Date:2011-5-3
 */
public abstract class AbstractBaseBean extends AbstractRestRequestSupport {
    public void responseException(Object obj, String methodName, String exceptionCode, Exception e) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        StringBuffer buffer = new StringBuffer();
        // 错误编码库信息获取
        StackTraceElement[] stackTrace = e.getStackTrace();
        for (int i = 0; i < stackTrace.length; i++) {
            buffer.append(" \n " + stackTrace[i]);
        }
        String plain = UtilFactory.getConfigUtil().getExceptionDescribe(exceptionCode);
        if (plain == null || plain.equals("")) {
            plain = "系统出错，请稍后重试！";
        }
        error(obj, plain);
        // 异常详细信息
        String classSite = obj.getClass().getName() + "." + methodName;
        String secret = classSite + "\n" + e.getMessage() + buffer.toString();
        map.put("Exception", "error");
        map.put("plain", plain);
        map.put("secret", secret);
        list.add(map);
        response(list);
    }
}
