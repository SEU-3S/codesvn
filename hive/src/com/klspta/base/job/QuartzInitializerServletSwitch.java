package com.klspta.base.job;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

import com.klspta.base.rest.ProjectInfo;
import com.klspta.base.util.UtilFactory;

public class QuartzInitializerServletSwitch extends HttpServlet {
    /**
     * <br>Description:自定义任务启动servlet
     * <br>Author:王瑛博
     * <br>Date:2011-7-18
     */
    private static final long serialVersionUID = 2067695971684104617L;

    public static final String QUARTZ_FACTORY_KEY = "org.quartz.impl.StdSchedulerFactory.KEY";

    private boolean performShutdown = true;

    private Scheduler scheduler = null;

    public void init(ServletConfig cfg) throws ServletException {
        String initJob = UtilFactory.getConfigUtil().getConfig("USERJOB");
        if(!initJob.equals("true")){
            System.out.println("任务模块未加载（config.properties中USERJOB为关闭状态）。");
            return;
        }
        super.init(cfg);
        log("初始化任务.....");
        try {
            String configFile = cfg.getInitParameter("config-file");
            String shutdownPref = cfg.getInitParameter("shutdown-on-unload");
            if (shutdownPref != null){
                performShutdown = Boolean.valueOf(shutdownPref).booleanValue();
            }
            StdSchedulerFactory factory;
            if (configFile != null){
                factory = new StdSchedulerFactory(initProp(configFile));
            }else{
                factory = new StdSchedulerFactory();
            }
            String startOnLoad = cfg.getInitParameter("start-scheduler-on-load");
            if (startOnLoad == null || Boolean.valueOf(startOnLoad).booleanValue()) {
                scheduler = factory.getScheduler();
                scheduler.start();
                log("任务模块初始化完成。");
            } else{
                log("Scheduler has not been started. Use scheduler.start()");
            }
            cfg.getServletContext().setAttribute("org.quartz.impl.StdSchedulerFactory.KEY", factory);
        } catch (Exception e) {
            log("任务模块初始化失败，失败原因是: " + e.toString());
            throw new ServletException(e);
        }
    }

    public void destroy() {
        if (performShutdown) {
            try {
                if (scheduler != null){
                    scheduler.shutdown();
                }
            } catch (Exception e) {
                log("任务模块关闭失败，失败原因是: " + e.toString());
                e.printStackTrace();
            }
            log("任务模块成功关闭。");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        response.sendError(403);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        response.sendError(403);
    }
     
    //自定义属性读取
    private Properties initProp(String fileName){
        InputStream is = null;
        Properties props = new Properties();
        is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        try{
          if (is != null) {
              is = new BufferedInputStream(is);
          } else {
              is = new BufferedInputStream(new FileInputStream(fileName));
          }
          props.load(is);
          props.put("org.quartz.plugin.jobInitializer.fileName", "/com/klspta/web/"+ProjectInfo.PROJECT_NAME+"/job/job.xml");
          return props;
        } catch (IOException ioe) {
        	return null;
        }
    }
}
