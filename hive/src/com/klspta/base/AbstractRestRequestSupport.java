package com.klspta.base;


import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.klspta.base.rest.ICommand;
import com.klspta.base.util.UtilFactory;

/**
 * 
 * <br>Title:REST请求三层过滤之一
 * <br>Description:提供rest请求支持
 * <br>Author:王瑛博
 * <br>Date:2011-5-3
 */
public abstract class AbstractRestRequestSupport extends AbstractDataBaseSupport implements ICommand {
    
    /**
     * 传入的request参数
     */
    public HttpServletRequest request;
    /**
     * 传入的response参数
     */
    public HttpServletResponse response;
    

    
    /**
     * 返回参数容器
     */
    private Vector<Object> vector = new Vector<Object>();
    

    /**
     * clazz定义
     */
    private Class<? extends AbstractRestRequestSupport> clazz = null;
    //private Class[] types = null;
    
    /**
     * 
     * <br>Description:构造，初始化本类的clazz
     * <br>Author:王瑛博
     * <br>Date:2011-5-3
     */
    public AbstractRestRequestSupport() {
        clazz = getClass();
        //types = (new Class[] {javax.servlet.http.HttpServletRequest.class, javax.servlet.http.HttpServletResponse.class});
    }

    /**
     * 
     * <br>Description:派发传入的方法
     * <br>Author:王瑛博
     * <br>Date:2011-5-3
     * @see com.klspta.request.ICommand#dispatchMethod(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String)
     */
    public void dispatchMethod(HttpServletRequest request, HttpServletResponse response, String method){
    	this.request = request;
    	this.response = response;
        Method methodObject = null;
        try {
            methodObject = clazz.getMethod(method);
            methodObject.invoke(this);
        } catch (NoSuchMethodException e) {
            sendFaultResult(e);
        } catch (ClassCastException e) {
            sendFaultResult(e);
        } catch (IllegalAccessException e) {
            sendFaultResult(e);
        } catch (InvocationTargetException e) {
            Throwable t = e.getTargetException();
            if (t instanceof Exception) {
                sendFaultResult(e);
            } else {
                sendFaultResult(e);
            }
        }
    }
    
    /**
     * 
     * <br>Description:请求不成功时，返回预定义错误
     * <br>Author:王瑛博
     * <br>Date:2011-5-3
     * @param e
     */
    private void sendFaultResult(Exception e){
    	e.printStackTrace();
    	Map<String, String> vv = new HashMap<String, String>();
    	vv.put("result", "fault");
    	vv.put("messag", e.toString());
    	putParameter(vv);
    	response();
    }
    
    /**
     * 
     * <br>Description:设置返回参数方法
     * <br>Author:王瑛博
     * <br>Date:2011-5-3
     * @param msg
     */
    public void putParameter(Object msg) {
        vector.add(msg);
    }
    
    /**
     * 
     * <br>Description:清理参数容器
     * <br>Author:王瑛博
     * <br>Date:2011-5-3
     */
    public void clearParameter(){
    	vector.clear();
    }
    
    public void redirect(String url){
        PrintWriter out = null;
        try {
            response.setContentType("text/html;charset=utf-8"); 
            out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
            StringBuffer sb = new StringBuffer("<script> location.href('");
            out.write(sb.append(url).append("')</script>").toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(out != null){
                try{
                    out.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    
    }
    
    /**
     * 
     * <br>Description:返回请求
     * <br>Author:王瑛博
     * <br>Date:2011-5-3
     */
    public void response(){
    	String msg = "";
		try {
			msg = UtilFactory.getJSONUtil().objectToJSON(vector);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	response(msg);
    }
    
    /**
     * 
     * <br>Description:返回带参数的请求，此msg需要满足json格式要求
     * <br>Author:王瑛博
     * <br>Date:2011-5-3
     * @param msg
     */
    public void response(String msg){
    	PrintWriter out = null;
    	try {
            response.setContentType("text/html;charset=utf-8"); 
            out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));   
            out.write(msg);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
            if(out != null){
            	try{
            		out.close();
            	}catch(Exception e){
            		e.printStackTrace();
            	}
            }
        }
    }
    
    /**
     * 
     * <br>Description:返回带参数的请求，此msg需要满足json格式要求
     * <br>Author:王瑛博
     * <br>Date:2011-5-3
     * @param msg
     */
    public void response(List<Map<String, Object>> vector){
        PrintWriter out = null;
        try {
            String msg = UtilFactory.getJSONUtil().objectToJSON(vector);
            response.setContentType("text/html;charset=utf-8"); 
            out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));   
            out.write(msg);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(out != null){
                try{
                    out.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
