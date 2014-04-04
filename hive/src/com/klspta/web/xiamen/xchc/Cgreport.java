package com.klspta.web.xiamen.xchc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SimpleTimeZone;

import com.klspta.base.AbstractBaseBean;
import com.klspta.model.CBDReport.bean.TDBean;
import com.klspta.model.CBDReport.bean.TRBean;
import com.klspta.model.CBDReport.dataClass.IDataClass;

public class Cgreport extends AbstractBaseBean implements IDataClass {
	
    public static String[][] showXCList = new String[][]{{"YDXMMC","用地项目名称"},{"YDZT","用地主体"},{"YDWZ","用地位置"},{"ZDMJ","占地面积"},{"GDMJ","耕地面积"},{"JZMJ","建筑面积"},{"JZXZ","建筑现状"},{"YT","用途"},{"FHGH","是否符合土地利用总体规划"},{"FXSJ","发现时间"},{"ZZQK","制止情况"},{"ZZTZSBH","制止通知书编号"},{"WJZZHJXZZ","违建制止后继续制止"},{"YYDSPQCZ","有用地审批且超占"}};
	public static String[][] showHCList = new String[][]{{"YDDW","用地单位"},{"ydsj","用地时间"},{"ydqk","用地情况"},{"mj","面积"},{"jsqk","建设情况"},{"dfccqk","地方查处情况"},{"wfwglx","违法违规类型"},{"sfwf","是否违法"},{"wflx","违法类型"},{"xcqkms","现场情况描述"}};
	private String form_name = "DC_YDQKDCB";
	
	@Override
	public Map<String, TRBean> getTRBeans(Object[] obj, TRBean trBean) {
        String xzq = (String)obj[0];
        String begindate = (String)obj[1];
        String enddate = (String)obj[2];
        Date begintime= null;
        Date endtime= null;     
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'UTC 0800' yyyy",Locale.ENGLISH);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        StringBuffer query = new StringBuffer();
        if (xzq != null && !("".equals(xzq))) {
            query.append(" and t.impxzqbm = '").append(xzq).append("'");
        }       
        if(begindate !=null && !("".equals(begindate))){
            try {
                begintime = sdf.parse(begindate);
                begindate = sdf1.format(begintime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            query.append(" and to_date(t.begindate,'YYYY-MM-DD') > ").append("to_date('").append(begindate).append("','YYYY-MM-DD')");
        }
        if(enddate !=null && !("".equals(enddate))){
            try {         
                endtime = sdf.parse(enddate);
                enddate = sdf1.format(endtime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            query.append(" and to_date(t.begindate,'YYYY-MM-DD') <").append("to_date('").append(enddate).append("','YYYY-MM-DD')");
        }	    	           
        
		Map<String, TRBean> trbeans = new LinkedHashMap<String, TRBean>();		
        List<TRBean> titleList1 = getTitle1();
        for(int i=0;i<titleList1.size();i++){
            trbeans.put(i+"a", titleList1.get(i));
        }      
        List<TRBean> trbeanList1 = getBody1(query.toString());
        for(int i=0;i<trbeanList1.size();i++){
            trbeans.put(i+"b", trbeanList1.get(i));
        }  
        /*
        TRBean tr = new TRBean();
        tr.setCssStyle("trsingle");
        TDBean tdb = new TDBean("&nbsp;", "800", "30");
        tdb.setColspan("15");
        tr.addTDBean(tdb);   
        trbeans.put("e", tr);
        */
        List<TRBean> titleList2 = getTitle2();
        for(int i=0;i<titleList2.size();i++){
            trbeans.put(i+"c", titleList2.get(i));
        }
      
		List<TRBean> trbeanList2 = getBody2(query.toString());		
        for(int i=0;i<trbeanList2.size();i++){
            trbeans.put(i+"d", trbeanList2.get(i));
        }   
       
		return trbeans;
	}
	
	
    private List<TRBean> getTitle1(){
        List<TRBean> list = new ArrayList<TRBean>();     
        TRBean title = new TRBean();
        title.setCssStyle("title");
        TDBean tdb = new TDBean("动态巡查", "800", "30");
        tdb.setColspan("15");
        title.addTDBean(tdb);
        list.add(title);
        TRBean titleBean1 = new TRBean();
        titleBean1.setCssStyle("title");      
        TDBean tdb11 = new TDBean("序号", "20", "60");
        tdb11.setRowspan("2");
        titleBean1.addTDBean(tdb11);       
        TDBean tdb12 = new TDBean("用地项目名称", "70", "60");
        tdb12.setRowspan("2");
        titleBean1.addTDBean(tdb12);                 
        TDBean tdb13 = new TDBean("用地主体", "70", "60");
        tdb13.setRowspan("2");
        titleBean1.addTDBean(tdb13);                
        TDBean tdb14 = new TDBean("用地位置", "100", "60");
        tdb14.setRowspan("2");
        titleBean1.addTDBean(tdb14);        
        TDBean tdb15 = new TDBean("占地面积", "70", "30");
        tdb15.setColspan("2");
        titleBean1.addTDBean(tdb15);                 
        TDBean tdb16 = new TDBean("建筑面积(m²)", "70", "60");
        tdb16.setRowspan("2");
        titleBean1.addTDBean(tdb16);         
        TDBean tdb17 = new TDBean("建筑现状", "70", "60");
        tdb17.setRowspan("2");
        titleBean1.addTDBean(tdb17);        
        TDBean tdb18 = new TDBean("用途", "70", "60");
        tdb18.setRowspan("2");
        titleBean1.addTDBean(tdb18);                
        TDBean tdb19 = new TDBean("是否符合土地利用总体规划", "70", "60");
        tdb19.setRowspan("2");
        titleBean1.addTDBean(tdb19);               
        TDBean tdb20 = new TDBean("发现时间", "70", "60");
        tdb20.setRowspan("2");
        titleBean1.addTDBean(tdb20);         
        TDBean tdb21 = new TDBean("制止情况", "70", "60");
        tdb21.setRowspan("2");
        titleBean1.addTDBean(tdb21);                
        TDBean tdb22 = new TDBean("制止通知书编号", "70", "60");
        tdb22.setRowspan("2");
        titleBean1.addTDBean(tdb22);                
        TDBean tdb23 = new TDBean("违建制止后继续制止", "70", "60");
        tdb23.setRowspan("2");
        titleBean1.addTDBean(tdb23);                 
        TDBean tdb24 = new TDBean("有用地审批且超占", "70", "60");
        tdb24.setRowspan("2");
        titleBean1.addTDBean(tdb24);                 
        list.add(titleBean1);       
        TRBean titleBean2 = new TRBean();
        titleBean2.setCssStyle("title");               
        TDBean tdb25 = new TDBean("&nbsp;", "35", "30");
        titleBean2.addTDBean(tdb25);  
        TDBean tdb26 = new TDBean("耕地面积", "35", "30");
        titleBean2.addTDBean(tdb26);  
        list.add(titleBean2);                        
        return list;        
    }	
	
	private List<TRBean> getBody1(String where){
	    List<TRBean> list = new ArrayList<TRBean>();          
        String sql = "select rownum xh,t.ydxmmc,t.ydzt,t.ydwz,t.zdmj,t.gdmj,t.jzmj,t.jzxz,t.yt,t.fhgh,t.fxsj,t.zzqk,t.zztzsbh,t.wjzzhjxzz,t.yydspqcz from dc_ydqkdcb t " +
                     "where t.yw_guid like 'XC%' "; 
        sql += where;
        List<Map<String,Object>> result = query(sql,YW);
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
	
	private List<TRBean> getTitle2(){    
        List<TRBean> list = new ArrayList<TRBean>();     
        TRBean title = new TRBean();
        title.setCssStyle("title");
        TDBean tdb = new TDBean("外业核查", "800", "30");
        tdb.setColspan("15");
        title.addTDBean(tdb);
        list.add(title);
        TRBean titleBean1 = new TRBean();
        titleBean1.setCssStyle("title");      
        TDBean tdb11 = new TDBean("序号", "20", "30");
        titleBean1.addTDBean(tdb11);       
        TDBean tdb12 = new TDBean("用地单位", "70", "30");
        titleBean1.addTDBean(tdb12);                 
        TDBean tdb13 = new TDBean("用地时间", "70", "30");
        titleBean1.addTDBean(tdb13);                
        TDBean tdb14 = new TDBean("用地情况", "100", "30");
        titleBean1.addTDBean(tdb14);        
        TDBean tdb15 = new TDBean("面积", "70", "30");
        tdb15.setColspan("2");
        titleBean1.addTDBean(tdb15);                 
        TDBean tdb16 = new TDBean("建设情况", "140", "30");
        tdb16.setColspan("2");
        titleBean1.addTDBean(tdb16);         
        TDBean tdb17 = new TDBean("地方查处情况", "140", "30");
        tdb17.setColspan("2");
        titleBean1.addTDBean(tdb17);        
        TDBean tdb18 = new TDBean("违法违规类型", "70", "30");
        titleBean1.addTDBean(tdb18);                
        TDBean tdb19 = new TDBean("是否违法", "70", "30");
        titleBean1.addTDBean(tdb19);               
        TDBean tdb20 = new TDBean("违法类型", "70", "30");
        titleBean1.addTDBean(tdb20);         
        TDBean tdb21 = new TDBean("现场情况描述", "140", "30");
        tdb21.setColspan("2");
        titleBean1.addTDBean(tdb21);                                 
        list.add(titleBean1);                                      
        return list;   	    
	}
	
	private List<TRBean> getBody2(String where){
		List<TRBean> list = new ArrayList<TRBean>();
	       String sql = "select rownum xh,t.yddw,t.ydsj,t.ydqk,t.mj,t.jsqk,t.dfccqk,t.wfwglx,t.sfwf,t.wflx,t.xcqkms from dc_ydqkdcb t " +
           "where t.yw_guid not like 'XC%' and t.yw_guid not like 'PHJG%'"; 
            sql += where;
            List<Map<String,Object>> result = query(sql,YW);
            for(int i=0;i<result.size();i++){
              TRBean tr = new TRBean();
              tr.setCssStyle("trsingle");            
              Map<String,Object> map = result.get(i);           
              TDBean td1 = new TDBean(filterNull(map.get("xh").toString()),"20","30");
              tr.addTDBean(td1);
              TDBean td2 = new TDBean(filterNull((String)map.get("yddw")),"70","30");
              tr.addTDBean(td2);
              TDBean td3 = new TDBean(filterNull((String)map.get("ydsj")),"70","30");
              tr.addTDBean(td3);
              TDBean td4 = new TDBean(filterNull((String)map.get("ydqk")),"100","30");
              tr.addTDBean(td4);
              TDBean td5 = new TDBean(filterNull((String)map.get("mj")),"70","30");
              td5.setColspan("2");
              tr.addTDBean(td5);
              TDBean td6 = new TDBean(filterNull((String)map.get("jsqk")),"140","30");
              td6.setColspan("2");
              tr.addTDBean(td6);
              TDBean td7 = new TDBean(filterNull((String)map.get("dfccqk")),"140","30");
              td7.setColspan("2");
              tr.addTDBean(td7);
              TDBean td8 = new TDBean(filterNull((String)map.get("wfwglx")),"70","30");
              tr.addTDBean(td8);
              TDBean td9 = new TDBean(filterNull((String)map.get("sfwf")),"70","30");
              tr.addTDBean(td9);
              TDBean td10 = new TDBean(filterNull((String)map.get("wflx")),"70","30");
              tr.addTDBean(td10);
              TDBean td11 = new TDBean(filterNull((String)map.get("xcqkms")),"140","30");
              td11.setColspan("2");
              tr.addTDBean(td11);
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
