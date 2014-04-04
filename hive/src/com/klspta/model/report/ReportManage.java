package com.klspta.model.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
/**
 * 需要在conf下的applicationContext-bean.xml中，增加配置信息：
 * <bean name="simpleExample" class="com.klspta.model.SimpleExample" scope="prototype"/>
 * @author wang
 *
 */
public class ReportManage extends AbstractBaseBean {
   
   private static ReportManage instance = null;
   private HashMap<String, ReportBean> reportmap = new HashMap<String, ReportBean>();
   public ReportManage(){
	  
   }
   public HashMap<String, ReportBean> getReportMap(String keyWord){
	   String sql = "";
	   List<Map<String, Object>> list;
	   //输入关键字时
	   if(keyWord != null && !"".equals(keyWord)){
		   keyWord = UtilFactory.getStrUtil().unescape(keyWord);
		    sql = "select t.reportid,t.reportname,t.jasperpath,t.querypath,t.ishavechart,t.remark,t.leaf_flag,t.parentid,t.data_type,t.data_generation_time,t.id from report_resource t where t.LEAF_FLAG='1' and t.reportname  like '%"+keyWord+"%'"+" or t.remark like '%"+keyWord+"%'";
		     list = query(sql, CORE); 
		     //根据输入的关键字，查询结果list为空时，查询所有数据
		     if(list.size() ==0){
		    	 sql = "select t.reportid,t.reportname,t.jasperpath,t.querypath,t.ishavechart,t.remark,t.leaf_flag,t.parentid,t.data_type,t.data_generation_time,t.id from report_resource t ";
				   list = query(sql, CORE); 
			  	   Map<String, Object> map = null;
			  	   for (int i = 0; i < list.size(); i++) {
						map = list.get(i);
						ReportBean report = new ReportBean(map);
						reportmap.put(report.getReportId(), report);
			  	 } 
		     }else{//根据关键字，查到结果时
		    	 Map<String, Object> map = null;
		    	 Set<String> parentIds = new HashSet<String>();
		    	 for (int i = 0; i < list.size(); i++) {
		    		 map = list.get(i);
		    		 ReportBean report = new ReportBean(map);
		    		 reportmap.put(report.getReportId(), report);
		    		 //查到报表对应的类别id
		    		 parentIds.add(report.getParentid());
		    	 }
		    	 parentIds.remove("0");
		    	 //System.out.println(parentIds);
		    	 //查询报表对应的类别
		    	 Iterator<String> it = parentIds.iterator();
		    	 while(it.hasNext()){
		    		 String sql1 = "select t.reportid,t.reportname,t.jasperpath,t.querypath,t.ishavechart,t.remark,t.leaf_flag,t.parentid,t.data_type,t.data_generation_time,t.id from report_resource t  where t.reportid ='"+it.next()+"'";
		    		 List<Map<String, Object>> list1 = query(sql1, CORE); 
		    		 map = list1.get(0);
		    		 ReportBean report = new ReportBean(map);
		    		 reportmap.put(report.getReportId(), report);
		    	 }
		     }
	   }
	   //未输入关键字时
	   else{
		   sql = "select t.reportid,t.reportname,t.querypath,t.ishavechart,t.remark,t.leaf_flag,t.parentid,t.data_type,t.data_generation_time,t.id from report_resource t ";
		   list = query(sql, CORE); 
	  	   Map<String, Object> map = null;
	  	   for (int i = 0; i < list.size(); i++) {
				map = list.get(i);
				ReportBean report = new ReportBean(map);
				reportmap.put(report.getReportId(), report);
	  	   }
	   }
  	   return reportmap;
   }
   public static ReportManage getInstance(String key) throws Exception {
		if ("NEW WITH MANAGER FACTORY!".equals(key)) {
			if (instance == null)
				instance = new ReportManage();
			return instance;
		} else
			throw new Exception("请从ManagerFacoory获取工具.");
	}
   
     /**
      * <br>Description:得到报表的信息
      * <br>Author:李亚栓
      * <br>Date:2012-7-2
      */
     public void getReportInfo(){
		}
     /**
      * 
      * <br>Description:构建报表树形结构
      * <br>Author:李亚栓
      * <br>Date:2012-7-10
      * @return
      */
     public Map<String,List<ReportBean>> getReportTreeMap(String keyWord)
     {
     	Map<String,List<ReportBean>> map = new HashMap<String, List<ReportBean>>();
     	reportmap = this.getReportMap(keyWord);
     	String key="";
     	for(ReportBean report:reportmap.values())
     	{
     		key=report.getParentid();
     		if(!map.containsKey(key))
     			map.put(key, new ArrayList<ReportBean>());
     		map.get(key).add(report);
     	}
     	return map;
     }
     /**
      * 
      * <br>Description:获取report列表的json
      * <br>Author:李亚栓
      * <br>Date:2012-7-10
      * @return
      */
     public void getReportListExtJson()
     {
    	 String keyWord = request.getParameter("keyWord");
    	 String root=request.getParameter("root");
    	 if(root==""||root==null)
    		 root="0";
    	 if(keyWord == null){
    		 keyWord = "";
    	 }
     	Map<String,List<ReportBean>> map=getReportTreeMap(keyWord);
     	List<ReportBean> list=map.get(root);
     	String json=addReportElementJson(list, map);
     	//System.out.println(json);
     	response(json);
     }
     /**
      * 
      * <br>Description:拼接report Json数据
      * <br>Author:李亚栓
      * <br>Date:2012-7-10
      * @param list
      * @param map
      * @return
      */
     private String addReportElementJson(List<ReportBean> list,Map<String,List<ReportBean>> map)
     {
     	StringBuffer extJsonCode = new StringBuffer("[");
 		List<ReportBean> childList=null;
 		for (ReportBean reprot : list) {
 			if (extJsonCode.length() > 1)
 				extJsonCode.append(",");
 			extJsonCode.append("\n{name:'");// text
 			extJsonCode.append(reprot .getReportName()+"'");
 			
 			childList =map.get(reprot.getReportId());
 			if(!"0".equals(reprot.getParentid())){
 				extJsonCode.append(",reportid:'"+"RPT"+reprot.getReportId()+"'");// id
 				extJsonCode.append(",data_type:'"+reprot.getData_type()+"'");
 				extJsonCode.append(",remark:'"+reprot.getRemark()+"'");
 				extJsonCode.append(",data_generarion_time:'"+reprot.getData_generation_time()+"'");
 				extJsonCode.append(",parentid:'"+reprot.getParentid()+"'");
 				extJsonCode.append(",id:'"+reprot.getId()+"'");
 			}
 			if (childList != null) {
 				extJsonCode.append(",children:");// children
 				extJsonCode.append(addReportElementJson(childList,map));
 			}
 			extJsonCode.append("}");
 		}
 		extJsonCode.append("]");
 		return extJsonCode.toString();
 	}
     /**
      * 
      * <br>Description: 取得报表类别名称
      * <br>Author:李亚栓
      * <br>Date:2012-7-13
      * @param parentId
      * @return
      */
     public String getParentName(String parentId){
    	String sql = "select t.reportname from report_resource t where t.reportid ='"+parentId+"'";
    	List<Map<String, Object>> list = query(sql, CORE);
    	String panentName = (String)list.get(0).get("reportname");
    	 return panentName; 
     }
}
