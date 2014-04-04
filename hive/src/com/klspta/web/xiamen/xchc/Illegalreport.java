package com.klspta.web.xiamen.xchc;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.klspta.base.AbstractBaseBean;
import com.klspta.model.CBDReport.bean.TDBean;
import com.klspta.model.CBDReport.bean.TRBean;
import com.klspta.model.CBDReport.dataClass.IDataClass;
import com.klspta.web.xiamen.jcl.XzqHandle;

public class Illegalreport extends AbstractBaseBean implements IDataClass {

    @Override
    public Map<String, TRBean> getTRBeans(Object[] obj, TRBean trBean) {
        String userId = (String)obj[0];      
        Map<String, TRBean> trbeans = new LinkedHashMap<String, TRBean>();    
        List<TRBean> titleList = getTitle();
        for(int i=0;i<titleList.size();i++){
            trbeans.put(i+"0", titleList.get(i));
        }      
        List<TRBean> bodyList = getBody(userId);
        for(int i=0;i<bodyList.size();i++){
            trbeans.put(i+"1", bodyList.get(i));
        }               
        return trbeans;
    }
    
    private List<TRBean> getTitle(){
        List<TRBean> list = new ArrayList<TRBean>();     
        TRBean titleBean1 = new TRBean();
        titleBean1.setCssStyle("title");      
        TDBean tdb11 = new TDBean("序号", "20", "100");
        tdb11.setRowspan("2");
        titleBean1.addTDBean(tdb11);       
        TDBean tdb12 = new TDBean("用地项目名称", "70", "100");
        tdb12.setRowspan("2");
        titleBean1.addTDBean(tdb12);                 
        TDBean tdb13 = new TDBean("用地主体", "70", "100");
        tdb13.setRowspan("2");
        titleBean1.addTDBean(tdb13);                
        TDBean tdb14 = new TDBean("用地位置", "100", "100");
        tdb14.setRowspan("2");
        titleBean1.addTDBean(tdb14);        
        TDBean tdb15 = new TDBean("占地面积", "70", "50");
        tdb15.setColspan("2");
        titleBean1.addTDBean(tdb15);                 
        TDBean tdb16 = new TDBean("建筑面积(m²)", "70", "100");
        tdb16.setRowspan("2");
        titleBean1.addTDBean(tdb16);         
        TDBean tdb17 = new TDBean("建筑现状", "70", "100");
        tdb17.setRowspan("2");
        titleBean1.addTDBean(tdb17);        
        TDBean tdb18 = new TDBean("用途", "70", "100");
        tdb18.setRowspan("2");
        titleBean1.addTDBean(tdb18);                
        TDBean tdb19 = new TDBean("是否符合土地利用总体规划", "70", "100");
        tdb19.setRowspan("2");
        titleBean1.addTDBean(tdb19);               
        TDBean tdb20 = new TDBean("发现时间", "70", "100");
        tdb20.setRowspan("2");
        titleBean1.addTDBean(tdb20);         
        TDBean tdb21 = new TDBean("制止情况", "70", "100");
        tdb21.setRowspan("2");
        titleBean1.addTDBean(tdb21);                
        TDBean tdb22 = new TDBean("制止通知书编号", "70", "100");
        tdb22.setRowspan("2");
        titleBean1.addTDBean(tdb22);                
        TDBean tdb23 = new TDBean("违建制止后继续制止", "70", "100");
        tdb23.setRowspan("2");
        titleBean1.addTDBean(tdb23);                 
        TDBean tdb24 = new TDBean("有用地审批且超占", "70", "100");
        tdb24.setRowspan("2");
        titleBean1.addTDBean(tdb24);                 
        list.add(titleBean1);       
        TRBean titleBean2 = new TRBean();
        titleBean2.setCssStyle("title");               
        TDBean tdb25 = new TDBean("&nbsp;", "35", "50");
        titleBean2.addTDBean(tdb25);  
        TDBean tdb26 = new TDBean("耕地面积", "35", "50");
        titleBean2.addTDBean(tdb26);  
        list.add(titleBean2);                        
        return list;      
    }
    
    private List<TRBean> getBody(String userId){
        List<TRBean> list = new ArrayList<TRBean>();          
        List<Map<String,Object>> result = null;  
        String sql = "select rownum xh, t.ydxmmc,t.ydzt,t.ydwz,t.zdmj,t.gdmj,t.jzmj,t.jzxz,t.yt,t.fhgh,t.fxsj,t.zzqk,t.zztzsbh,t.wjzzhjxzz,t.yydspqcz from dc_ydqkdcb t " +
                     "where t.yw_guid like 'XC%' and t.state='未立案'";
        sql = XzqHandle.getXzqSql(userId, sql, "impxzqbm");
        result = query(sql,YW);
        for(int i=0;i<result.size();i++){
            TRBean tr = new TRBean();
            tr.setCssStyle("trsingle");            
            Map<String,Object> map = result.get(i);           
            TDBean td1 = new TDBean(filterNull(map.get("xh").toString()),"20","30");
            tr.addTDBean(td1);
            TDBean td2 = new TDBean(filterNull((String)map.get("ydxmmc")),"70","30");
            tr.addTDBean(td2);
            TDBean td3 = new TDBean(filterNull((String)map.get("ydzt")),"70","30");
            tr.addTDBean(td3);
            TDBean td4 = new TDBean(filterNull((String)map.get("ydwz")),"100","30");
            tr.addTDBean(td4);
            TDBean td5 = new TDBean(filterNull((String)map.get("zdmj")),"35","30");
            tr.addTDBean(td5);
            TDBean td6 = new TDBean(filterNull((String)map.get("gdmj")),"35","30");
            tr.addTDBean(td6);
            TDBean td7 = new TDBean(filterNull((String)map.get("jzmj")),"70","30");
            tr.addTDBean(td7);
            TDBean td8 = new TDBean(filterNull((String)map.get("jzxz")),"70","30");
            tr.addTDBean(td8);
            TDBean td9 = new TDBean(filterNull((String)map.get("yt")),"70","30");
            tr.addTDBean(td9);
            TDBean td10 = new TDBean(filterNull((String)map.get("fhgh")),"70","30");
            tr.addTDBean(td10);
            TDBean td11 = new TDBean(filterNull((String)map.get("fxsj")),"70","30");
            tr.addTDBean(td11);
            TDBean td12 = new TDBean(filterNull((String)map.get("zzqk")),"70","30");
            tr.addTDBean(td12);
            TDBean td13 = new TDBean(filterNull((String)map.get("zztzsbh")),"70","30");
            tr.addTDBean(td13);
            TDBean td14 = new TDBean(filterNull((String)map.get("wjzzhjxzz")),"70","30");
            tr.addTDBean(td14);
            TDBean td15 = new TDBean(filterNull((String)map.get("yydspqcz")),"70","30");
            tr.addTDBean(td15);
            list.add(tr);
        }
        return list;
    }
    
    private String filterNull(String value){
        if(value==null){
            return "&nbsp;";
        }
        return value;
    }
}
