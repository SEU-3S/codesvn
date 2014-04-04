package com.klspta.web.cbd.cbsycs.tjsj;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import com.klspta.base.AbstractBaseBean;

public class StatisReport extends AbstractBaseBean {
    //住宅征收规模
    public static final String ZZZSGM = "zzzsgmfx.xml"; 
    //供应规模
    public static final String GYGM = "gygmfx.xml";
    //安置房用情况
    public static final String AZFYQK = "azfyqkfx.xml";
    //资金使用情况
    public static final String ZJSYQK = "zjsyqkfx.xml";
    //资金风险
    public static final String ZJFX = "zjfxfx.xml";
    //融资需求
    public static final String RZXQ = "rzxqfx.xml";
    //负债规模
    public static final String FZGM = "fzgmfx.xml";
    //投资比例
    public static final String TZBL = "tzblfx.xml";
    //写字楼租金单个楼
    public static final String XZLZJ = "xzlzj.xml";
  //写字楼租金所有楼
    public static final String XZLZJ_ALL = "xzlall.xml";
    //二手房租金
    public static final String ESFZJ = "esfzj.xml";
  //二手房租金
    public static final String ESFSJ = "esfsj.xml";
    //写字楼售价
    public static final String XZLSJ = "xzlsj.xml";
    //年度数组
    static String[] array = null;// new String[9];
    static String yw_guid = null;
    static{
        /*
        for(int i=0;i<array.length;i++){
            array[i]=(i+2013)+"";
        }
        */
    }
    public StatisReport(){
       if(array == null){
           getYears();
       }
    }

	private String[] getYears(){
        String sql = "select distinct(t.nd) nd from hx_sx t order by to_number(t.nd)";
        List<Map<String,Object>> list = query(sql,YW);
        Map<String,Object> map = null;
        if(list!=null && list.size()>0){
            array = new String[list.size()];
            for(int i=0;i<list.size();i++){
                map = list.get(i);
                array[i] = (String)map.get("nd");
            }
        }       
        return array;
    }
    
    /**
     * 
     * <br>Description:根据参数调用不同的获取报表数据的方法
     * <br>Author:王雷
     * <br>Date:2013-8-27
     */
    public void getReportData(){
        String xml = request.getParameter("xml");
        try{
        	yw_guid = request.getParameter("yw_guid");
        }catch (Exception e) {
			// TODO: handle exception
		}
        if(ZZZSGM.equals(xml)){
            getZzzsgm();
        }else if(GYGM.equals(xml)){
            getGygm();
        }else if(AZFYQK.equals(xml)){
            getAzfyqk();
        }else if(ZJSYQK.equals(xml)){
            getZjsyqk();
        }else if(ZJFX.equals(xml)){
            getZjfx();
        }else if(RZXQ.equals(xml)){
            getRzxq();
        }else if(FZGM.equals(xml)){
            getFzgm();
        }else if(TZBL.equals(xml)){
            getTzbl();
        }else if(XZLZJ.equals(xml)){
        	getXzlzj();
        }else if(XZLZJ_ALL.equals(xml)){
        	getXzlzj();
        }else if(ESFZJ.equals(xml)){
        	getEsfzj();
        }else if(XZLSJ.equals(xml)){
        	getXzlsj();
        }else if(ESFSJ.equals(xml)){
        	getEsfsj();
        }
    }
    
    private void getEsfzj() {
    	String[] names = {"租金"};
        String[] colors = {"yellow"};
        String[] alias = {"zj"}; 
        String[] column = new String[12];
        String[] months = new String[] {"YY","EY","SY","SIY","WY","LY","QY","BAY","JY","SHIY","SYY","SEY"};
        int[] array1 = null;
        int[] array2 = null;
        
        int month = Calendar.getInstance().get(Calendar.MONTH)+1;
        if(month==12){
        	array1 = new int[12];
        	for(int i=1;i<=12;i++){
        		array1[i-1] = i;       		
        	}
        	column = months;
        }else {
        	int j=0;
        	array1 = new int[12-month];
        	array2 = new int[month];
        	for(int i = month % 12 + 1; i<=12;i++ ){
        		array1[j]= i;	
        		column[j] = months[i-1];
        		j++;
        	}
        	int z=0;
        	for( int i=1;i<=month;i++){
        		array2[z]= i;
        		column[j] = months[i-1];
        		z++;
        		j++;
        	}
        }
        
        String year = Calendar.getInstance().get(Calendar.YEAR)+"";
        String sql1 = "select avg(k.czfjj) as zj  from esf_zsxx k where k.year='"+year+"' and k.month=? ";
        generateReportESF(names,colors,alias,array1,array2,sql1,"esfzj","Line"); 
	}

	/**
     * 
     * <br>Description:二手房租金售价分析图
     * <br>Author:李国明
     * <br>Date:2013-12-12
     */
    private void getEsfsj() {
    	String[] names = {"售价"};
        String[] colors = {"yellow"};
        String[] alias = {"sj"}; 
        String[] column = new String[12];
        String[] months = new String[] {"YY","EY","SY","SIY","WY","LY","QY","BAY","JY","SHIY","SYY","SEY"};
        int[] array1 = null;
        int[] array2 = null;
        
        int month = Calendar.getInstance().get(Calendar.MONTH)+1;
        if(month==12){
        	array1 = new int[12];
        	for(int i=1;i<=12;i++){
        		array1[i-1] = i;       		
        	}
        	column = months;
        }else {
        	int j=0;
        	array1 = new int[12-month];
        	array2 = new int[month];
        	for(int i = month % 12 + 1; i<=12;i++ ){
        		array1[j]= i;	
        		column[j] = months[i-1];
        		j++;
        	}
        	int z=0;
        	for( int i=1;i<=month;i++){
        		array2[z]= i;
        		column[j] = months[i-1];
        		z++;
        		j++;
        	}
        }
        
        String year = Calendar.getInstance().get(Calendar.YEAR)+"";
        String sql1 = "select avg(k.esfjj) as sj  from esf_zsxx k where k.year='"+year+"' and k.month=? ";
        generateReportESF(names,colors,alias,array1,array2,sql1,"esfsj","Line"); 
    }

    
    /**
     * 
     * <br>Description:写字楼租金售价分析图
     * <br>Author:李国明
     * <br>Date:2013-12-12
     */
	private void getXzlzj() {
    	String[] names = {"租金","保本点"};
        String[] colors = {"yellow","red"};
        String[] alias = {"zj","bbd"}; 
        String[] column = new String[12];
        String[] months = new String[] {"YY","EY","SY","SIY","WY","LY","QY","BAY","JY","SHIY","SYY","SEY"};
        int[] array1 = null;
        int[] array2 = null;
        
        int month = Calendar.getInstance().get(Calendar.MONTH)+1;
        if(month==12){
        	array1 = new int[12];
        	for(int i=1;i<=12;i++){
        		array1[i-1] = i;       		
        	}
        	column = months;
        }else {
        	int j=0;
        	array1 = new int[12-month];
        	array2 = new int[month];
        	for(int i = month % 12 + 1; i<=12;i++ ){
        		array1[j]= i;	
        		column[j] = months[i-1];
        		j++;
        	}
        	int z=0;
        	for( int i=1;i<=month;i++){
        		array2[z]= i;
        		column[j] = months[i-1];
        		z++;
        		j++;
        	}
        }
        generateReport(names,colors,alias,array1,array2,months,"xzlzj","Line"); 
	}

	/**
     * 
     * <br>Description:获取写字楼租金及售价报表数据
     * <br>Author:李国明
     * <br>Date:2013-12-10
     */
     private void getXzlsj() {
    	 String[] names = {"售价"};
         String[] colors = {"yellow"};
         String[] alias = {"sj"}; 
         String[] column = new String[12];
         String[] months = new String[] {"YY","EY","SY","SIY","WY","LY","QY","BAY","JY","SHIY","SYY","SEY"};
         int[] array1 = null;
         int[] array2 = null;
         
         int month = Calendar.getInstance().get(Calendar.MONTH)+1;
         if(month==12){
         	array1 = new int[12];
         	for(int i=1;i<=12;i++){
         		array1[i-1] = i;       		
         	}
         	column = months;
         }else {
         	int j=0;
         	array1 = new int[12-month];
         	array2 = new int[month];
         	for(int i = month % 12 + 1; i<=12;i++ ){
         		array1[j]= i;	
         		column[j] = months[i-1];
         		j++;
         	}
         	int z=0;
         	for( int i=1;i<=month;i++){
         		array2[z]= i;
         		column[j] = months[i-1];
         		z++;
         		j++;
         	}
         }
         generateReportSJ(names,colors,alias,array1,array2,months,"xzlsj","Line"); 
	}

	/**
      * 
      * <br>Description:获取住宅征收规模报表数据
      * <br>Author:王雷
      * <br>Date:2013-8-27
      */
     private void getZzzsgm(){
         String[] names = {"年度征收规模(流)","征收控制规模"};
         String[] colors = {"yellow","red"};
         String[] alias={"hs","kz"};
         String sql1="select  sum(k.征收户数) hs,1000 as kz from  v_开发体量 k where k.年度 = ?";
         generateReport(names,colors,alias,sql1,"zzzsgmfx","Line");
     }
     
     /**
      * 
      * <br>Description:获取供应规模报表数据
      * <br>Author:王雷
      * <br>Date:2013-11-4
      */
     private void getGygm(){
         String[] names = {"年度供应规模(流)","净地可出让规模(存)","完成开发规模(流)"};
         String[] colors = {"yellow","red","blue"};
         String[] alias = {"ndgygm","jdkcrgm","wckfgm"}; 
         String sql1="select sum(t.供应规模) ndgygm,(sum(t.供应规模)+sum(t.储备库库存)) jdkcrgm from v_供应体量 t  where t.年度=?";
         List<Object> allList = new ArrayList<Object>();
         List<Map<String,Object>> list1 = null;
         String sql2 ="select sum(t.完成开发规模) wckfgm from v_开发体量 t where t.年度=?";
         List<Map<String,Object>> list2 = null;
         for(int i=0;i<array.length;i++){
             list1 = query(sql1,YW,new Object[]{array[i]});
             list2 = query(sql2,YW,new Object[]{array[i]});
             Map<String,Object> map1 = list1.get(0);
             Map<String,Object> map2 = list2.get(0);
             map1.put("WCKFGM", map2.get("wckfgm"));
             allList.add(list1);
         } 
         List<Object> list = parseXml("gygmfx");
         String xmlPath = (String)list.get(0);
         Document doc = (Document)list.get(1);      
         Element data = (Element)list.get(2);
         Element e = null;
         Element e1 = null;
         List<Map<String,Object>> list3 = null;
         Map<String,Object> map = null;
         for(int i=0;i<names.length;i++){
             e = new Element("series");
             e.setAttribute("id", i+"");
             e.setAttribute("name", names[i]);
             e.setAttribute("type", "Line");
             e.setAttribute("color", colors[i]);
             for(int j=0;j<array.length;j++){
                 list3 = (List<Map<String,Object>>)allList.get(j); 
                 map = list3.get(0);
                 e1 = new Element("point");
                 e1.setAttribute("name", array[j]);
                 e1.setAttribute("y", map.get(alias[i])==null?"0":map.get(alias[i]).toString());  
                 e.addContent(e1);
             }
             data.addContent(e);
         }       
         generateXml(doc,xmlPath);          
     }
     
     /**
      * 
      * <br>Description:获取安置房规模报表数据
      * <br>Author:王雷
      * <br>Date:2013-11-5
      */
     private void getAzfyqk(){
         String[] names = {"安置房需求规模(流)","安置房可使用规模(存)"};
         String[] colors = {"yellow","red"};
         String[] alias = {"azfxqgm","azfksygm"}; 
         String sql1="select sum(t.azfsyl) azfxqgm,(sum(t.azfsyl)+sum(t.azfcl)) azfksygm from hx_sx t where t.nd=?";
         generateReport(names,colors,alias,sql1,"azfyqkfx","Line");           
     }
     
    /**
     * 
     * <br>Description:获取资金使用情况报表数据
     * <br>Author:王雷
     * <br>Date:2013-11-5
     */
    private void getZjsyqk(){
        String[] names = {"年度支出(流)","年度收入(流)"};
        String[] colors = {"yellow","red"};
        String[] alias = {"ndzc","ndsr"}; 
        String sql1 = "select (sum(t.bqtzxq)+sum(t.bqhkxq)) ndzc,(sum(t.bqhlcb)+sum(t.bqrzxq)+sum(t.qyxzjzr)) ndsr from hx_sx t where t.nd=?";
        String sql2 = "select sum(t.bqzmye) bqzmye from hx_sx t where t.nd=?";
        generateReport(names,colors ,alias,sql1,sql2,"ndsr","bqzmye","zjsyqkfx","Line");               
    }
     
    /**
     * 
     * <br>Description:获取资金风险报表数据
     * <br>Author:王雷
     * <br>Date:2013-11-21
     */
    private void getZjfx(){
        String[] names = {"非债务资金流入规模(流)","债务性资金还款规模(流)","年度投资规模(流)"};
        String[] colors = {"yellow","red","blue"};
        String[] alias = {"fzwzjlrgm","zwxzjhkgm","ndtzgm"}; 
        String sql1 = "select (sum(t.qyxzjzr)+sum(t.bqhlcb)) fzwzjlrgm,sum(t.bqhkxq) zwxzjhkgm,sum(t.bqtzxq) ndtzgm from hx_sx t where t.nd=?";
        String sql2 = "select sum(t.bqzmye) bqzmye from hx_sx t where t.nd=?";
        generateReport(names,colors ,alias,sql1,sql2,"fzwzjlrgm","bqzmye","zjfxfx","Line");               
    }
    
    /**
     * 
     * <br>Description:获取融资需求分析报表数据
     * <br>Author:王雷
     * <br>Date:2013-11-21
     */
    private void getRzxq(){
        String[] names = {"年度融资规模(流)","负债余额(存)","年度投资(流)"};
        String[] colors = {"yellow","red","blue"};
        String[] alias = {"ndrzgm","fzye","ndtz"}; 
        String sql1 = "select sum(t.bqtzxq) ndrzgm,sum(t.fzye) fzye,sum(t.bqtzxq) ndtz from hx_sx t where t.nd=?";
        generateReport(names,colors,alias,sql1,"rzxqfx","Line");                  
    }    
    
    
    /**
     * 
     * <br>Description:获取负债规模分析报表数据
     * <br>Author:王雷
     * <br>Date:2013-11-21
     */
    private void getFzgm(){
        String[] names = {"负债余额(存)","储备库融资能力(存)"};
        String[] colors = {"yellow","red"};
        String[] alias = {"fzye","cbkrznl"}; 
        String sql1 = "select sum(t.fzye) fzye,sum(t.cbkrznl) cbkrznl from hx_sx t where t.nd=?";
        generateReport(names,colors,alias,sql1,"fzgmfx","Line");           
    }
    /**
     * 
     * <br>Description:获取投资比例分析报表数据
     * <br>Author:王雷
     * <br>Date:2013-11-21
     */
    private void getTzbl(){
        String[] names = {"自有资金使用规模","债务资金使用规模"};
        String[] colors = {"yellow","red"};
        String[] alias = {"zyzjsygm","zwzjsygm"}; 
        String sql1 = "select (sum(t.bqtzxq)-sum(t.bqrzxq)) zyzjsygm,sum(t.bqrzxq) zwzjsygm from hx_sx t where t.nd=?";
        generateReport(names,colors,alias,sql1,"tzblfx","Bar");              
    }
    
    /**
     * 
     * <br>Description:TODO 方法功能描述
     * <br>Author:王雷
     * <br>Date:2013-11-21
     * @param names
     * @param colors
     * @param alias
     * @param sql1
     * @param xml
     * @param type
     */
    private void generateReport(String[] names,String[] colors ,String[] alias,String sql1,String xml,String type){
        List<Object> allList = new ArrayList<Object>();
        List<Map<String,Object>> list1 = null;
        for(int i=0;i<array.length;i++){
            list1 = query(sql1,YW,new Object[]{array[i]});
            allList.add(list1);
        }         
        List<Object> list = parseXml(xml);
        String xmlPath = (String)list.get(0);
        Document doc = (Document)list.get(1);      
        Element data = (Element)list.get(2);
        Element e = null;
        Element e1 = null;
        List<Map<String,Object>> list2 = null;
        Map<String,Object> map = null;
        for(int i=0;i<names.length;i++){
            e = new Element("series");
            e.setAttribute("id", i+"");
            e.setAttribute("name", names[i]);
            e.setAttribute("type", type);
            e.setAttribute("color", colors[i]);
            for(int j=0;j<array.length;j++){
                list2 = (List<Map<String,Object>>)allList.get(j); 
                map = list2.get(0);
                e1 = new Element("point");
                e1.setAttribute("name", array[j]);
                e1.setAttribute("y", map.get(alias[i])==null?"0":map.get(alias[i]).toString());  
                e.addContent(e1);
            }
            data.addContent(e);
        }        
        generateXml(doc,xmlPath);             
    }
    
    /**
     * 
     * <br>Description:二手房租金售价分析图
     * <br>Author:李国明
     * <br>Date:2013-12-12
     */
    private void generateReportESF(String[] names,String[] colors ,String[] alias,int[] array1,int[] array2,String sql1,String xml,String type){
    	 List<Object> allList = new ArrayList<Object>();
         List<Map<String,Object>> list1 = null;
         List<Map<String,Object>> list2 = null;
         int year = Calendar.getInstance().get(Calendar.YEAR);
         if(array1[0]==1){
	         for(int i=0;i<array1.length;i++){
	             list1 = query(sql1,YW,new Object[]{array1[i]});
	             allList.add(list1);
	         }         
         }else {
        	 for(int i=0;i<array1.length;i++){
	             list1 = query(sql1,YW,new Object[]{array1[i]});
	             allList.add(list1);
	         } 
        	 for(int i=0;i<array2.length;i++){
	             list2 = query(sql1,YW,new Object[]{array2[i]});
	             allList.add(list2);
	         } 
         }
         List<Object> list = parseXml(xml);
         String xmlPath = (String)list.get(0);
         Document doc = (Document)list.get(1);      
         Element data = (Element)list.get(2);
         Element e = null;
         Element e1 = null;
         List<Map<String,Object>> list3 = null;
         Map<String,Object> map = null;
         e = new Element("series");
         e.setAttribute("id", "0");
         e.setAttribute("name", names[0]);
         e.setAttribute("type", type);
         e.setAttribute("color", colors[0]);
         if(array1[0]==1){
	         for(int j=0;j<array1.length;j++){
	             list3 = (List<Map<String,Object>>)allList.get(j); 
	             map = list3.get(0);
	             e1 = new Element("point");
	             e1.setAttribute("name", year+"年"+array1[j]+"月");
	             e1.setAttribute("y", map.get(alias[0])==null?"0":map.get(alias[0]).toString());  
	             e.addContent(e1);
	         }
         }else {
        	 int j ;
        	 for( j = 0;j<array1.length;j++){
	             list3 = (List<Map<String,Object>>)allList.get(j); 
	             map = list3.get(0);
	             e1 = new Element("point");
	             e1.setAttribute("name", year+"年"+array1[j]+"月");
	             e1.setAttribute("y", map.get(alias[0])==null?"0":map.get(alias[0]).toString());  
	             e.addContent(e1);
	         }
        	 j--;
        	 for(int t=0;t<array2.length;t++){
	             list3 = (List<Map<String,Object>>)allList.get(j++); 
	             map = list3.get(0);
	             e1 = new Element("point");
	             e1.setAttribute("name", year+"年"+array2[t]+"月");
	             e1.setAttribute("y", map.get(alias[0])==null?"0":map.get(alias[0]).toString());  
	             e.addContent(e1);
	         }
         }
         data.addContent(e);       
         generateXml(doc,xmlPath);       
    }
    
    /**
     * 
     * <br>Description: 写字楼租金
     * <br>Author:李国明
     * <br>Date:2013-12-12
     */
    private void generateReport(String[] names,String[] colors ,String[] alias,int[] array1,int[] array2,String[] column,String xml,String type){
    	 List<Object> allListzj = new ArrayList<Object>();
    	 List<Object> allList = new ArrayList<Object>();
         List<Map<String,Object>> listzj1 = null;
         List<Map<String,Object>> listzj2 = null;
         int year = Calendar.getInstance().get(Calendar.YEAR);
         String sql1 = "select ";
         String sql2 = "select ";
         if(array1[0]==1){
        	 for(int i=0;i<11;i++){
        		 sql1+= "avg("+column[i]+") as "+column[i]+",";
        	 }
        	 sql1 += "avg("+column[11]+") as "+column[11]+" from xzlzjqknd_pjzj where rq=?";
        	 listzj1 = query(sql1, YW,new Object[]{year+""});
        	 allListzj.add(listzj1);
         }else {
        	 for(int i=0;i<array1.length-1;i++){
        		 sql1+= "avg("+column[array1[i]-1]+") as "+column[array1[i]-1]+",";
        	 }
        	 sql1 += "avg("+column[array1[array1.length-1]-1]+") as "+column[array1[array1.length-1]-1]+" from xzlzjqknd_pjzj where rq=?";
        	 listzj1 = query(sql1, YW,new Object[]{year-1+""});
        	 allListzj.add(listzj1);
        	 for(int i=0;i<array2.length-1;i++){
        		 sql2+= "avg("+column[array2[i]-1]+") as "+column[array2[i]-1]+",";
        	 }
        	 sql2 += "avg("+column[array2[array2.length-1]-1]+") as "+column[array2[array2.length-1]-1]+" from xzlzjqknd_pjzj where rq=?";
        	 listzj2 = query(sql2, YW,new Object[]{year+""});
        	 allListzj.add(listzj2);
        	 allList.add(allListzj);
         }       
         List<Object> list = parseXml(xml);
         String xmlPath = (String)list.get(0);
         Document doc = (Document)list.get(1);      
         Element data = (Element)list.get(2);
         Element e = null;
         Element e1 = null;
         Map<String,Object> map = null;
         
             e = new Element("series");
             e.setAttribute("id", 0+"");
             e.setAttribute("name", names[0]);
             e.setAttribute("type", type);
             e.setAttribute("color", colors[0]);
             if(array2==null){
	             for(int j=0;j<array1.length;j++){
	                 map = ((List<Map<String,Object>>)allListzj.get(0)).get(0);
	                 e1 = new Element("point");
	                 e1.setAttribute("name", year+"年"+array1[j]+"月");
	                 e1.setAttribute("y", map.get(column[array1[j]-1])==null?"0":map.get(column[array1[j]-1]).toString());  
	                 e.addContent(e1);
	             }
             }else {
            	 allListzj = (List<Object>)allList.get(0);
            	 for(int j=0;j<array1.length;j++){
                     map = ((List<Map<String,Object>>)allListzj.get(0)).get(0);
                     e1 = new Element("point");
                     e1.setAttribute("name", year-1+"年"+array1[j]+"月");
                     e1.setAttribute("y", map.get(column[array1[j]-1])==null?"0":map.get(column[array1[j]-1]).toString());  
                     e.addContent(e1);
                 }
	             for(int j=0;j<array2.length;j++){
	                 map = ((List<Map<String,Object>>)allListzj.get(1)).get(0);
	                 e1 = new Element("point");
	                 e1.setAttribute("name", year+"年"+array2[j]+"月");
	                 e1.setAttribute("y", map.get(column[array2[j]-1])==null?"0":map.get(column[array2[j]-1]).toString());  
	                 e.addContent(e1);
	             }
             }
             data.addContent(e);
             
             
             List<Map<String, Object>> result = null;
 			String sql = "select sum(j.kfcb) as kfcb ,round(sum(j.kfcb)/sum(j.jzgm),1)*10000 as lmcb ,round(sum(j.kfcb)/sum(j.jsyd),1)*10000 as dmcb from jc_jiben j where j.ssqy in(?,?,?)";
 			result = query(sql, YW,new Object[]{"产业功能改造区","民生改善区","城市形象提升区"});
 			
 			map.put("KFCB",result.get(0).get("KFCB")==null?"0":result.get(0).get("KFCB").toString());
 			map.put("LMCB",result.get(0).get("LMCB")==null?"0":result.get(0).get("LMCB").toString());
 			map.put("DMCB",result.get(0).get("DMCB")==null?"0":result.get(0).get("DMCB").toString());
 			sql = "select b.bbd as bbd from sys_parameter s,bbdfxjg b where b.lmcb=? and s.hsq = b.tzhsq";
 			result = query(sql, YW,new Object[]{map.get("LMCB").toString()});
             e = new Element("series");
             e.setAttribute("id", 1+"");
             e.setAttribute("name", names[1]);
             e.setAttribute("type", type);
             e.setAttribute("color", colors[1]);
             if(array2==null){
	             for(int j=0;j<array1.length;j++){
	                 map = result.get(0);
	                 e1 = new Element("point");
	                 e1.setAttribute("name", year+"年"+array1[j]+"月");
	                 e1.setAttribute("y", map.get("bbd")==null?"0":map.get("bbd").toString());  
	                 e.addContent(e1);
	             }
             }else {
            	 for(int j=0;j<array1.length;j++){
                     map = result.get(0);
                     e1 = new Element("point");
                     e1.setAttribute("name", year-1+"年"+array1[j]+"月");
                     e1.setAttribute("y", map.get("bbd")==null?"0":map.get("bbd").toString());  
                     e.addContent(e1);
                 }
	             for(int j=0;j<array2.length;j++){
	                 map = result.get(0);
	                 e1 = new Element("point");
	                 System.out.println(map.get(column[array1[j]-1]));
	                 e1.setAttribute("name", year+"年"+array2[j]+"月");
	                 e1.setAttribute("y", map.get("bbd")==null?"0":map.get("bbd").toString());  
	                 e.addContent(e1);
	             }
             }
             data.addContent(e);
         generateXml(doc,xmlPath);       
    }
    
    
    /**
     * 
     * <br>Description: 写字楼售价
     * <br>Author:李国明
     * <br>Date:2013-12-12
     */
    private void generateReportSJ(String[] names,String[] colors ,String[] alias,int[] array1,int[] array2,String[] column,String xml,String type){
    	 List<Object> allListsj = new ArrayList<Object>();
         List<Map<String,Object>> listsj1 = null;
         List<Map<String,Object>> listsj2 = null;
         int year = Calendar.getInstance().get(Calendar.YEAR);
         String sql1 = "select ";
         String sql2 = "select ";
         if(array1[0]==1){
        	 sql1 = "select ";
        	 for(int i=0;i<11;i++){
        		 sql1+= "avg("+column[i]+") as "+column[i]+",";
        	 }
        	 sql1 += "avg("+column[11]+") as "+column[11]+" from xzlzjqknd_pjlm where rq=?";
        	 listsj1 = query(sql1, YW,new Object[]{year+""});
        	 allListsj.add(listsj1);
         }else {
        	 sql1 = "select ";
             sql2 = "select ";
             for(int i=0;i<array1.length-1;i++){
        		 sql1+= "avg("+column[array1[i]-1]+") as "+column[array1[i]-1]+",";
        	 }
        	 sql1 += "avg("+column[array1[array1.length-1]-1]+") as "+column[array1[array1.length-1]-1]+" from xzlzjqknd_pjlm where rq=?";
        	 listsj1 = query(sql1, YW,new Object[]{year-1+""});
        	 allListsj.add(listsj1);
        	 for(int i=0;i<array2.length-1;i++){
        		 sql2+= "avg("+column[array2[i]-1]+") as "+column[array2[i]-1]+",";
        	 }
        	 sql2 += "avg("+column[array2[array2.length-1]-1]+") as "+column[array2[array2.length-1]-1]+" from xzlzjqknd_pjlm where rq=?";
        	 listsj2 = query(sql2, YW,new Object[]{year+""});
        	 allListsj.add(listsj2);
         }       
         List<Object> list = parseXml(xml);
         String xmlPath = (String)list.get(0);
         Document doc = (Document)list.get(1);      
         Element data = (Element)list.get(2);
         Element e = null;
         Element e1 = null;
         Map<String,Object> map = null;
         
             e = new Element("series");
             e.setAttribute("id", 0+"");
             e.setAttribute("name", names[0]);
             e.setAttribute("type", type);
             e.setAttribute("color", colors[0]);
             if(array2==null){
	             for(int j=0;j<array1.length;j++){
	                 map = ((List<Map<String,Object>>)allListsj.get(0)).get(0);
	                 e1 = new Element("point");
	                 e1.setAttribute("name", year+"年"+array1[j]+"月");
	                 e1.setAttribute("y", map.get(column[array1[j]-1])==null?"0":map.get(column[array1[j]-1]).toString());  
	                 e.addContent(e1);
	             }
             }else {
            	 for(int j=0;j<array1.length;j++){
                     map = ((List<Map<String,Object>>)allListsj.get(0)).get(0);
                     e1 = new Element("point");
                     e1.setAttribute("name", year-1+"年"+array1[j]+"月");
                     e1.setAttribute("y", map.get(column[array1[j]-1])==null?"0":map.get(column[array1[j]-1]).toString());  
                     e.addContent(e1);
                 }
	             for(int j=0;j<array2.length;j++){
	                 map = ((List<Map<String,Object>>)allListsj.get(1)).get(0);
	                 e1 = new Element("point");
	                 System.out.println(map.get(column[array1[j]-1]));
	                 e1.setAttribute("name", year+"年"+array2[j]+"月");
	                 e1.setAttribute("y", map.get(column[array2[j]-1])==null?"0":map.get(column[array2[j]-1]).toString());  
	                 e.addContent(e1);
	             }
             }
             data.addContent(e);
         generateXml(doc,xmlPath);       
    }
    
    /**
     * 
     * <br>Description:TODO 方法功能描述
     * <br>Author:王雷
     * <br>Date:2013-11-21
     * @param names
     * @param colors
     * @param alias
     * @param sql1
     * @param sql2
     * @param property1
     * @param property2
     * @param xml
     * @param type
     */
    private void generateReport(String[] names,String[] colors ,String[] alias,String sql1,String sql2,String property1,String property2,String xml,String type){
        List<Object> allList = new ArrayList<Object>();
        List<Map<String,Object>> list1 = null;
        List<Map<String,Object>> list2 = null;
        for(int i=0;i<array.length;i++){
            list1 = query(sql1,YW,new Object[]{array[i]});
            list2 = query(sql2,YW,new Object[]{array[i]});
            Map<String,Object> map1 = list1.get(0);
            Map<String,Object> map2 = list2.get(0);
            double d = Double.parseDouble(map1.get(property1)==null?"0":map1.get(property1).toString())+Double.parseDouble(map2.get(property2)==null?"0":map2.get(property2).toString());
            map1.remove(property1.toUpperCase());
            map1.put(property1.toUpperCase(), d+"");             
            allList.add(list1);
        } 
        List<Object> list = parseXml(xml);
        String xmlPath = (String)list.get(0);
        Document doc = (Document)list.get(1);      
        Element data = (Element)list.get(2);
        Element e = null;
        Element e1 = null;
        List<Map<String,Object>> list3 = null;
        Map<String,Object> map = null;
        for(int i=0;i<names.length;i++){
            e = new Element("series");
            e.setAttribute("id", i+"");
            e.setAttribute("name", names[i]);
            e.setAttribute("type", type);
            e.setAttribute("color", colors[i]);
            for(int j=0;j<array.length;j++){
                list3 = (List<Map<String,Object>>)allList.get(j); 
                map = list3.get(0);
                e1 = new Element("point");
                e1.setAttribute("name", array[j]);
                e1.setAttribute("y", map.get(alias[i])==null?"0":map.get(alias[i]).toString());  
                e.addContent(e1);
            }
            data.addContent(e);
        }    
        generateXml(doc,xmlPath); 
    }
    
     /**
      * 
      * <br>Description:根据传入的文件名解析对应的xml并返回值
      * <br>Author:王雷
      * <br>Date:2013-8-27
      * @param fileName
      * @return
      */
     private List<Object> parseXml(String fileName){
         List<Object> list = new ArrayList<Object>();
         String classPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
         String filePath = classPath.substring(0,classPath.lastIndexOf("WEB-INF/classes"));
         String xmlPath = filePath+"web/cbd/tjbb/xml/"+fileName+".xml";
         SAXBuilder builder = new SAXBuilder();
         Document doc = null;
         try {
             doc = builder.build(new java.io.File(xmlPath));
         } catch (JDOMException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }
         Element root = doc.getRootElement(); 
         
         Element data = root.getChild("charts").getChild("chart").getChild("data");         
         if(data!=null){
             data.removeContent();            
         }
         list.add(xmlPath);
         list.add(doc);
         list.add(data);       
         return list;
     }
     
     /**
      * 
      * <br>Description:生成最终的xml文件
      * <br>Author:王雷
      * <br>Date:2013-8-27
      * @param doc
      * @param xmlPath
      */
     private void generateXml(Document doc, String xmlPath ){
         Format format = Format.getPrettyFormat();
         XMLOutputter outer = new XMLOutputter(format);
         try {
             outer.output(doc, new FileOutputStream(xmlPath));
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
}
