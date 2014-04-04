package com.klspta.base.util.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.api.IConfigUtil;

public final class ConfigUtil extends AbstractBaseBean implements IConfigUtil {
    private static String SHAPE_FILE_TEMP_FLODER;

    private static String SECURITY_USEABLE;

    private static String SECURITY_verifyURL;

    private static String SECURITY_passIPs;

    private static String app_path;

    public String getApppath() {
        return app_path;
    }

    Properties confprops = null;

    Properties codeprops = null;

    private ConfigUtil() {
    	initConfig();
    	initExceptionCode();
    }
    
    private void initConfig(){
        try {
            InputStream basepath = getClass().getResourceAsStream("/config.properties");
            app_path = getClass().getResource("/").getPath();
            confprops = new Properties();
            confprops.load(basepath);
            SHAPE_FILE_TEMP_FLODER = confprops.getProperty("SHAPEFILE_PATH");
            File file = new File(SHAPE_FILE_TEMP_FLODER);
            if (!file.exists()) {
                file.mkdirs();
            }
            SECURITY_USEABLE = confprops.getProperty("SECURITY_USEABLE");
            SECURITY_verifyURL = confprops.getProperty("SECURITY_verifyURL");
            SECURITY_passIPs = confprops.getProperty("SECURITY_passIPs");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            responseException(this,"initConfig","100002",e);
        } catch (IOException e) {
           responseException(this,"initConfig","100002",e);
            e.printStackTrace();
        }
    }
    private void initExceptionCode(){
        try {
            InputStream basepath = getClass().getResourceAsStream("/exceptionCode.properties");
            app_path = getClass().getResource("/").getPath();
            codeprops = new Properties();
            codeprops.load(basepath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            responseException(this,"initExceptionCode","100002",e);
        } catch (IOException e) {
            e.printStackTrace();
            responseException(this,"initExceptionCode","100002",e);
        }
    }

    private static IConfigUtil instance;

    public static IConfigUtil getInstance(String key) throws Exception {
        if (!key.equals("NEW WITH UTIL FACTORY!")) {
            throw new Exception("请从UtilFacoory获取工具.");
        }
        if (instance == null) {
            return new ConfigUtil();
        } else {
            return instance;
        }
    }

    public String getShapefileTempPathFloder() {
        return SHAPE_FILE_TEMP_FLODER;
    }

    public boolean isSecurityUseable() {
        return SECURITY_USEABLE.equals("true") ? true : false;
    }

    public String getSecurityVerifyURL() {
        return SECURITY_verifyURL;
    }

    public String getSecurityPassIPs() {
        return SECURITY_passIPs;
    }

    public String getExceptionDescribe(String code) {
        return codeprops.getProperty(code);
    }

    public String getConfig(String key) {
        return confprops.getProperty(key);
    }

    public double getConfigDouble(String key) {
        try {
            return Double.parseDouble(confprops.getProperty(key));
        } catch (Exception e) {
        	 responseException(this,"getConfigDouble","100002",e);
            e.printStackTrace();
        }
        return 0.0;
    }
}
