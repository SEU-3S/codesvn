package com.klspta.base.workflow.bean;

import java.util.HashMap;
import java.util.Map;

public class WorkItemBean {
    
    private String owner = "";
    private String receiveid = "";
    private String name = "";
    private String no = "";
    private String typeOne = "";
    private String typeTwo = "";
    
    public WorkItemBean(String owner, String receiveid, String name, String no, String typeOne, String typeTwo){
        this.owner = owner;
        this.receiveid = receiveid;
        this.name = name;
        this.no = no;
        this.typeOne = typeOne;
        this.typeTwo = typeTwo;
    }
    
    public String getOwner() {
        return owner;
    }

    public String getReceiveid() {
        return receiveid;
    }

    public String getName() {
        return name;
    }

    public String getNo() {
        return no;
    }
    
    public String getTypeOne() {
        return typeOne;
    }
    
    public String getTypeTwo() {
        return typeTwo;
    }

    public Map<String, Object> changeToMap(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("owner", owner);
        map.put("receiveid", receiveid);
        map.put("name", name);
        map.put("no", no);
        map.put("typeOne", typeOne);
        map.put("typeTwo", typeTwo);
        return map;
    }
}
