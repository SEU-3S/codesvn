package com.klspta.web.cbd.cbsycs.kfhdt;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import com.klspta.base.AbstractBaseBean;

public class CopyOfProjectProgress extends AbstractBaseBean {
    
    /**
     * 
     * <br>Description:初始化方法
     * <br>Author:王雷
     * <br>Date:2013-8-20
     */
    public void init(){ 
        String sql="select t.xmmc,t.kssj,t.jssj from test t";
        List<Map<String,Object>> list = query(sql,YW);
        if(list!=null && list.size()>0){
            generateXml(list);
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
    private void generateXml(List<Map<String,Object>> list){
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
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
        String dateStr = sdf.format(date);
        NumberFormat nt = NumberFormat.getPercentInstance();
        Element task = root.getChild("project_chart").getChild("tasks");
        if(task!=null){
            root.getChild("project_chart").getChild("tasks").removeContent();           
        }
        for(int i=0;i<list.size();i++){
            Map<String,Object> map = list.get(i);
            String beginDateStr = map.get("kssj").toString();
            String endDateStr = map.get("jssj").toString();       
            double progress = getDaySub(beginDateStr,dateStr);
            double count = getDaySub(beginDateStr,endDateStr);  
            String bfb = nt.format(progress/count);
            Element e = new Element("task");
            e.setAttribute("id", (i+1)+"");
            e.setAttribute("name", (String)map.get("xmmc"));
            e.setAttribute("progress",bfb);
            e.setAttribute("actual_start", map.get("kssj").toString());
            e.setAttribute("actual_end", map.get("jssj").toString());   
            root.getChild("project_chart").getChild("tasks").addContent(e);
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
    private long getDaySub(String beginDateStr,String endDateStr)
    {
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
    
}
