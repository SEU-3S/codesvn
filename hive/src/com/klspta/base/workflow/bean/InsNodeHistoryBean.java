package com.klspta.base.workflow.bean;

import java.util.Date;

public class InsNodeHistoryBean {
    private String user = "";
    private Date startTime = null;
    private Date endTime = null;
    private String name = "";
    
    public InsNodeHistoryBean(String user, String name, Date startTime, Date endTime){
        this.user = user;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    public String getUser() {
        return user;
    }
    public Date getStartTime() {
        return startTime;
    }
    public Date getEndTime() {
        return endTime;
    }
    public String getName() {
        return name;
    }

}
