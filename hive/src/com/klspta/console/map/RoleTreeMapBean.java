package com.klspta.console.map;

import java.util.Map;

public class RoleTreeMapBean {
    private String roleid = "";
    private String treeid = "";
    private String id = "";
    
    public RoleTreeMapBean(Map<String, Object> m){
         roleid = String.valueOf(m.get("ROLEID"));
         treeid = String.valueOf(m.get("TREEID"));
         id = String.valueOf(m.get("ID"));
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getTreeid() {
        return treeid;
    }

    public void setTreeid(String treeid) {
        this.treeid = treeid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
