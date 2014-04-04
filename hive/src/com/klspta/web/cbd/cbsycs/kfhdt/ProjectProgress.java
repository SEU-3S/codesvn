package com.klspta.web.cbd.cbsycs.kfhdt;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import com.klspta.base.AbstractBaseBean;

public class ProjectProgress extends AbstractBaseBean {
    
    /**
     * 
     * <br>Description:初始化方法
     * <br>Author:王雷
     * <br>Date:2013-8-20
     */
    public void init(){ 
        String xmSql="select distinct(t.xmmc) xmmc from hx_kftl t";
        
        String kfSql="select t.xmmc,t.jd,t.nd from hx_kftl t where t.xmmc=? order by t.nd,t.jd";
        
        List<Map<String,Object>> xmList = query(xmSql,YW);  
        Map<String,Object> map = null;
        
        List<Object> allList = new ArrayList<Object>();
        
        List<Map<String,Object>> kfList = null;
        
        if(xmList!=null && xmList.size()>0){
            for(int i=0;i<xmList.size();i++){
                map = xmList.get(i);
                kfList = query(kfSql,YW,new Object[]{map.get("xmmc")});
                allList.add(kfList);
            }            
            generateXml(xmList,allList);           
        }
        
        //重定向
        redirect();
    }
    
    /**
     * 
     * <br>Description:生成甘特图对应的xml
     * <br>Author:王雷
     * <br>Date:2013-8-20
     * @param list
     */
    private void generateXml(List<Map<String,Object>> xmList,List<Object> allList){
        String classPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
        String filePath = classPath.substring(0,classPath.lastIndexOf("WEB-INF/classes"));
        String xmlPath = filePath+"web/cbd/anyChart/anygantt.xml";
        SAXBuilder builder = new SAXBuilder();
        org.jdom.Document doc = null;
        try {
            doc = builder.build(new java.io.File(xmlPath));
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element root = doc.getRootElement();
        
        Element resource = root.getChild("resource_chart").getChild("resources");
        if(resource!=null){
            root.getChild("resource_chart").getChild("resources").removeContent();           
        }       
        for(int i=0;i<xmList.size();i++){
            Map<String,Object> map = xmList.get(i);
            Element e = new Element("resource");
            e.setAttribute("id", (i+1)+"");
            e.setAttribute("name", (String)map.get("xmmc")); 
            root.getChild("resource_chart").getChild("resources").addContent(e);            
            
        }
        
        Element period = root.getChild("resource_chart").getChild("periods");
        if(period!=null){
            root.getChild("resource_chart").getChild("periods").removeContent();           
        }         
        for(int i=0;i<allList.size();i++){            
            List<Map<String,Object>> childList = (List<Map<String,Object>>)allList.get(i);                                   
            Map<String,Object> map1 = childList.get(0);
            Map<String,Object> map2 = childList.get(childList.size()-1);
            String startPeriod = getPeriod((String)map1.get("jd"),(String)map1.get("nd"),"start");                              
            String endPeriod = getPeriod((String)map2.get("jd"),(String)map2.get("nd"),"end");   
            String prePeriod = getPrePeriod(startPeriod);
            String nexPeriod = getNexPeriod(endPeriod);
            
            Element e1 = new Element("period");
            e1.setAttribute("resource_id", (i+1)+"");
            e1.setAttribute("start",prePeriod);
            e1.setAttribute("end",startPeriod);
            e1.setAttribute("style","yellow");
            root.getChild("resource_chart").getChild("periods").addContent(e1);
            
            Element e2 = new Element("period");
            e2.setAttribute("resource_id", (i+1)+"");
            e2.setAttribute("start",startPeriod);
            e2.setAttribute("end",endPeriod);
            e2.setAttribute("style","red");
            root.getChild("resource_chart").getChild("periods").addContent(e2);
            
            Element e3 = new Element("period");
            e3.setAttribute("resource_id", (i+1)+"");
            e3.setAttribute("start",endPeriod);
            e3.setAttribute("end",nexPeriod);
            e3.setAttribute("style","blue");                           
            root.getChild("resource_chart").getChild("periods").addContent(e3);
        }
            XMLOutputter outer = new XMLOutputter();
        try {
            outer.output(doc, new FileOutputStream(xmlPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * <br>Description:两日期相隔的天数
     * <br>Author:王雷
     * <br>Date:2013-8-20
     * @param beginDateStr
     * @param endDateStr
     * @return
     */
    private long getDaySub(String beginDateStr,String endDateStr){
        long day=0;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");    
        java.util.Date beginDate;
        java.util.Date endDate;
        try
        {
            beginDate = format.parse(beginDateStr);
            endDate= format.parse(endDateStr);    
            day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);       
        } catch (ParseException e)
        {
            e.printStackTrace();
        }   
        return day;
    }
    
    /**
     * 
     * <br>Description:重定向
     * <br>Author:王雷
     * <br>Date:2013-8-20
     */
    private void redirect(){
        String basePath = request.getScheme() + "://" + request.getServerName()
        + ":" + request.getServerPort() + request.getContextPath()
        + "/";
        StringBuffer url = new StringBuffer();
        url.append(basePath);
        url.append("/web/cbd/anyChart/anygantt.html");
        redirect(url.toString());  
    }
    
    
    private String getPeriod(String jd,String year,String flag){
        String period = null;
        if("1".equals(jd)){
            if("end".equals(flag)){
                period = year + "/04/01";
            }else if("start".equals(flag)){
                period = year + "/01/01";               
            }                       
        }else if("2".equals(jd)){            
            if("end".equals(flag)){
                period = year + "/07/01";
            }else if("start".equals(flag)){
                period = year + "/04/01";               
            }            
        }else if("3".equals(jd)){
            if("end".equals(flag)){
                period = year + "/10/01";
            }else if("start".equals(flag)){
                period = year + "/07/01";               
            }               
        }else if("4".equals(jd)){
            if("end".equals(flag)){
                period = (Integer.parseInt(year)+1) + "/01/01";
            }else if("start".equals(flag)){
                period = year + "/10/01";               
            }
        }
        return period;
    }
    
    private String getPrePeriod(String strDate){
        String prePeriod = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date date = sdf.parse(strDate);
            int month =date.getMonth();
            date.setMonth(month-9);
            prePeriod=sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }        
        return prePeriod;        
    }
    
    private String getNexPeriod(String strDate){
        String nexPeriod = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date date = sdf.parse(strDate);
            int month =date.getMonth();
            date.setMonth(month+24);
            nexPeriod=sdf.format(date);           
        } catch (ParseException e) {
            e.printStackTrace();
        }        
        return nexPeriod;        
    }    
    
}
