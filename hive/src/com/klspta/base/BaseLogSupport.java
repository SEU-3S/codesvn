package com.klspta.base;

import org.apache.log4j.Logger;

public class BaseLogSupport {
    public void log(Object obj, String msg){
    	getLogger(obj).info(preMsg(msg));
    }
    
    public void debug(Object obj, String tag, String msg){
    	debug(obj, "==============" + tag + "=============" + msg);
    }
    
    public void debug(Object obj, String msg){
    	getLogger(obj).debug(preMsg(msg));
    }
    
    public void warn(Object obj, String msg){
    	getLogger(obj).warn(preMsg(msg));
    }
    
    public void fatal(Object obj, String msg){
    	getLogger(obj).fatal(preMsg(msg));
    }
    
    public void error(Object obj, String msg){
    	getLogger(obj).error(msg);
    }
    
    private String preMsg(String msg){
    	return msg;
    }
    
    private Logger getLogger(Object obj){
    	Logger logger = Logger.getLogger(obj.getClass().getName());
    	return logger;
    }
    
    
}
