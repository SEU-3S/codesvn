package com.klspta.web.cbd.jtfx.ztt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;

/**
 * 
 * <br>Title:专题图参数获取
 * <br>Description:
 * <br>Author:陈强峰
 * <br>Date:2013-11-6
 */
public class ZttDeal  extends AbstractBaseBean{
    private static ZttDeal instance=new ZttDeal();
    private static Map<String,String> map;
    private ZttDeal(){
        init();
    }
    public static ZttDeal getInstance()
    {
        return instance;
    }
    public void init(){
        String sql = "select t.child_id, t.child_name from public_code t where t.id='TZZ'";
        List<Map<String, Object>> list = query(sql, YW);
        int count=list.size();
        if(count>0){
            map=new HashMap<String, String>(); 
            Map<String,Object> single;
            for(int i=0;i<count;i++){
                single=list.get(i);
                map.put(single.get("child_id").toString(),single.get("child_name").toString());   
            }
        }
    }
    
    public String getMapURL(String bh){
        return map.get(bh);
    }
}
