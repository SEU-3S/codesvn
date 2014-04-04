package com.klspta.base.rest.security;

import com.klspta.base.util.UtilFactory;

public class SecurityPassable {
    private SecurityPassable(){}
    
    private static SecurityPassable instance;
    
    public static SecurityPassable getInstance(){
    	if(instance == null){
    		return new SecurityPassable();
    	}else{
    		return instance;
    	}
    }
    
    public boolean isPassable(String key){
    	if(UtilFactory.getConfigUtil().isSecurityUseable()){
        	if(key == null){
        		return false;
        	}
    		if(key.indexOf(UtilFactory.getConfigUtil().getSecurityPassIPs()) > 0){
    			return true;
    		}else{
    			return verifyURL(key);
    		}
    	}else{
    		return true;
    	}
    }
    
    public boolean verifyURL(String key){
    	
    	return true;
    }
}
