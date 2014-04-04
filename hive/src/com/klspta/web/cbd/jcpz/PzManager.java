package com.klspta.web.cbd.jcpz;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;

public class PzManager extends AbstractBaseBean {

    public static String hsq = "";
    private static PzManager instens = new PzManager();
    public PzManager() {
        inint();
    }
    public static PzManager getInstens() {
        if (instens != null) {
            return new PzManager();
        } else {
            return instens;
        }
    }

    public  void inint(){
        String sql="select hsq from SYS_PARAMETER";
        List<Map<String, Object>> query = query(sql, YW);
        if (query.size()>0) {
            hsq= query.get(0).get("hsq").toString();
        }
    }
    public String getHsq(){
        return hsq;
    }
    public void setHsq(){
        String parameter = request.getParameter("hsq");
        String del="delete SYS_PARAMETER where hsq is not null";
        String update="insert into SYS_PARAMETER(hsq) values(?) ";
        update(del, YW);
        int i = update(update, YW,new Object []{parameter});
        if(i>0){
            inint();
            try {
                response.getWriter().write("{success:true}");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }
    
    
}
