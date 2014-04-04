package com.klspta.base.rest;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sun.misc.BASE64Encoder;

import com.klspta.base.rest.security.SecurityPassable;
import com.klspta.base.util.UtilFactory;

/**
 * 
 * <br>Title:rest入口类
 * <br>Description:前台的rest请求的入口类
 * <br>Author:王瑛博
 * <br>Date:2011-5-3
 */
@Controller
@RequestMapping("/rest")
public class RestRequest extends ApplicationObjectSupport{
	
	private static String md5str = "";
	public void createKey(){
        String itisme = "192.168.11.22&2010-12-31$";
        MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
	        BASE64Encoder base64en = new BASE64Encoder();
	        md5str = base64en.encode(md5.digest(itisme.getBytes("utf-8")));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/{bean}/{method}", method = RequestMethod.POST)
    public void serviceAsPost(HttpServletRequest request, HttpServletResponse response, @PathVariable("bean") String bean, @PathVariable("method") String method){
		request(request, response, bean, method);
    }
    
    @RequestMapping(value = "/{bean}/{method}", method = RequestMethod.GET)
    public void serviceAsGet(HttpServletRequest request, HttpServletResponse response, @PathVariable("bean") String bean, @PathVariable("method") String method){
    	request(request, response, bean, method);
    }
	
    @RequestMapping(value = "/{bean}/{method}/{parameters}", method = RequestMethod.POST)
    public void serviceAsPost(HttpServletRequest request, HttpServletResponse response, @PathVariable("bean") String bean, @PathVariable("method") String method, @PathVariable("parameters") String parameters){
    	parsParameters(parameters, request, response);
    	request(request, response, bean, method);
    }
    
    @RequestMapping(value = "/{bean}/{method}/{parameters}", method = RequestMethod.GET)
    public void serviceAsGet(HttpServletRequest request, HttpServletResponse response, @PathVariable("bean") String bean, @PathVariable("method") String method, @PathVariable("parameters") String parameters){
    	parsParameters(parameters, request, response);
    	request(request, response, bean, method);
    }
    
    private void parsParameters(String parameters, HttpServletRequest request, HttpServletResponse response){
    	if(parameters != null && !parameters.equals("")){
    		String[] pars = parameters.split("&");
    		for(int i = 0; i < pars.length; i++){
    			try{
    				request.setAttribute(pars[i].split("=")[0], pars[i].split("=")[1]);
    			}catch(Exception e){
    			    	PrintWriter out = null;
    			    	try {
    						String msg = UtilFactory.getJSONUtil().objectToJSON("{}");
    			            response.setContentType("text/html;charset=utf-8"); 
    			            out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));   
    			            out.println(msg); 
    					} catch (Exception e1) {
    						e1.printStackTrace();
    					} finally{
    			            if(out != null){
    			            	try{
    			            		out.close();
    			            	}catch(Exception e2){
    			            		
    			            	}
    			            }
    			        }
    			    }
    			}
    		}
    }
    
    ApplicationContext factory = null;
    private void request(HttpServletRequest request, HttpServletResponse response, String bean, String method){
    	String itisme = request.getParameter("_$sid");
    	itisme = md5str;
    	createKey();
    	if(SecurityPassable.getInstance().isPassable(itisme)){
        	ApplicationContext ctx = getApplicationContext();
        	if(ctx.containsBean(bean)){
        	    doit(ctx, bean, request, response, method);
        	}else{
        	    if(factory == null){
                    String s = UtilFactory.getConfigUtil().getApppath() + "../conf/web";
                    String name = ProjectInfo.PROJECT_NAME;
                    System.out.println(s + "/applicationContext-" + name + ".xml");
                    factory = new FileSystemXmlApplicationContext(s + "/applicationContext-" + name + ".xml"); 
        	    }
                if(factory.containsBean(bean)){
                    doit(factory, bean, request, response, method);
                }else{
                    cannotdoit(response, bean);
                }
        	}
    	}else{
    		System.out.println("sid不存在" + itisme);
    	}
    }
    
    private void doit(ApplicationContext ctx, String bean, HttpServletRequest request, HttpServletResponse response, String method){
        ICommand command = (ICommand)ctx.getBean(bean);
        try {
            command.dispatchMethod(request, response, method);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void cannotdoit(HttpServletResponse response, String bean){
        try {
            response.getWriter().write("no rest named :" + bean);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
