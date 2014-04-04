package com.klspta.web.qingdaoNW.dtxc;



import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.klspta.base.AbstractBaseBean;

public class XccgTreeOperation extends AbstractBaseBean{
	private static XccgTreeOperation xccgtree = null;
	
	String selectSQL="select substr(t.xcdw,0,3) xcq,t.xcrq from xcrz t where t.yw_guid=?";
	
	String selectCgSQL="select t.guid from v_pad_data_xml t where to_char(to_date(t.xcrq,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd') = to_char(to_date(?,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd') and t.xzqmc=?";
	
	
	public static XccgTreeOperation getInstance(){
		if(xccgtree == null){
			return new XccgTreeOperation();
		}
		return xccgtree;	
	}
	
	public String getDynamicTree(HashMap<String,Object> map){
		String yw_guid = (String)map.get("yw_guid");		
		String children="{text: '巡查成果',leaf:0,id:'1',type:'dynamic',children:[";
		List<Map<String,Object>> list = query(selectSQL,AbstractBaseBean.YW, new Object[] {yw_guid});
		String xcrq = "";
		String xcq = "";
		if(list!=null && list.size()>0){
			Map<String,Object> map1 = list.get(0);
			xcrq = (String)map1.get("xcrq");
			xcq = (String)map1.get("xcq");
		}
		
		List<Map<String,Object>> list2 = query(selectCgSQL,AbstractBaseBean.YW, new Object[]{xcrq,xcq});
		
		if(list2!=null && list2.size()>0){
			for(int i=0;i<list2.size();i++){
			  Map<String,Object> map2 = list2.get(i);  
					children+="{text:'巡查成果编号:"+(map2.get("guid"))+"',leaf:1,id:'"+i+"',src:'web/xuzhouNW/dtxc/wyxc/xccgTab.jsp?yw_guid="+map2.get("guid")+"'}";
					if(i!=(list2.size()-1)){
						children+=",";
					}
			}
			 
		}		
		children+="]}";
		System.out.println(children);
		return children;
	}
	
	
}
