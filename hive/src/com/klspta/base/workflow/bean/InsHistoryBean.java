package com.klspta.base.workflow.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InsHistoryBean {
    private List<InsNodeHistoryBean> insNodeHistoryList = new ArrayList<InsNodeHistoryBean>();
    private Date startTime = null;
    private Date endTime = null;
    private String insId = "";
    public List<InsNodeHistoryBean> getInsNodeHistoryList() {
        return insNodeHistoryList;
    }
    public void addInsNodeHistoryBean(InsNodeHistoryBean insNodeHistoryBean) {
        this.insNodeHistoryList.add(insNodeHistoryBean);
    }
    public Date getStartTime() {
        return startTime;
    }
    public Date getEndTime() {
        return endTime;
    }
    public String getInsId() {
        return insId;
    }


}
