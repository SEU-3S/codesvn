package com.klspta.web.cbd.xmgl.xmkgzbb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


import com.klspta.base.AbstractBaseBean;
import com.klspta.model.CBDReport.bean.TDBean;
import com.klspta.model.CBDReport.bean.TRBean;
import com.klspta.model.CBDReport.dataClass.IDataClass;

public class Xmkgzbbreport extends AbstractBaseBean implements IDataClass {
	public static String[][] showList = new String[][]{{"ROWNUM","序号"},{"DKMC","地块编号"},{"YDXZDH","用地性质代号"},{"YDXZ","用地性质"},{"JSYDMJ","用地面积"},{"RJL","容积率"},{"GHJZGM","建筑面积"},{"JZKZGD","控制高度"},{"BZ","备注"},{"YDXZLX","用地性质类型"}};
	
	@Override
	public Map<String, TRBean> getTRBeans(Object[] obj, TRBean trBean) {
		Map<String, TRBean> trbeans = new TreeMap<String, TRBean>();
		Map<String, String> queryMap = new HashMap<String, String>();
		if(obj.length > 0){
			queryMap = (Map<String, String>)obj[0];
		}
		List<TRBean> trbeanList = getBody(queryMap);
        for(int i=0;i<trbeanList.size();i++){
        	String key = String.valueOf(i);
        	if(key.length() == 1){
        		key = "0" + key;
        	}
            trbeans.put(key, trbeanList.get(i));
        }  
		return trbeans;
	}
	
	
	private List<TRBean> getBody(Map queryMap){
		StringBuffer sqlBuffer = new StringBuffer();
		String yw_guid = "";
		sqlBuffer.append("select rownum,t1.dkmc,t1.ydxzdh,t1.ydxz,t1.jsydmj,t1.rjl,t1.ghjzgm,t1.jzkzgd,t1.bz,t2.ydxzlx from  DCSJK_KGZB t1,XMKGZBB t2 where t1.dkmc=t2.dkbh and t1.dqy=t2.qy and t1.qy=t2.xqy ");
		
		if(queryMap != null && !queryMap.isEmpty()){ 
			sqlBuffer.append(" and ");
			sqlBuffer.append(String.valueOf(queryMap.get("query")));
			String arrays[]=String.valueOf(queryMap.get("query")).split("'");
			yw_guid = arrays[1];
		}
		
		StringBuffer sqlDZDL = new StringBuffer();
		sqlDZDL.append("select t.dzdlydmj,t.dzdljzmj,t.dzdlkzgd,t.dzdlbz from xmkgzbb t where t.ydxzlx='4' ");
		if(yw_guid != null){ 
			sqlDZDL.append(" and t.yw_guid = '"+yw_guid+"'");
		}
		
		List<Map<String, Object>> queryList = query(sqlBuffer.toString(), YW);
		List<Map<String, Object>> dzdllist = query(sqlDZDL.toString(), YW);
		
		List<TRBean> list = new ArrayList<TRBean>();
		double ydmj_jyxjsyd = 0;
		double jzmj_jyxjsyd = 0;
		double ydmj_fjyxjsyd = 0;
		double jzmj_fjyxjsyd = 0;
		double ydmj_dzldjsy = 0;
		double jzmj_dzldjsy = 0;
		double ydmj_dzdl = 0;
		double jzmj_dzdl = 0;
		boolean isnull = true;
		
		for(int num=0;num<queryList.size();num++){
			Map<String, Object> map = queryList.get(num);
			String ydxzlx =String.valueOf(map.get("ydxzlx"));
			if(ydxzlx.equals("1")){
				ydmj_jyxjsyd+=Double.valueOf(String.valueOf( map.get("jsydmj"))).doubleValue();
				jzmj_jyxjsyd+=Double.valueOf(String.valueOf( map.get("ghjzgm"))).doubleValue();
				isnull = false;
				
				TRBean trBean = new TRBean();
				trBean.setCssStyle("trsingle");
					for(int i = 0; i < showList.length-1; i++){
						String value = String.valueOf(map.get(showList[i][0]));
						if("null".equals(value)){
							value = "";
						}
						String width = "";
						TDBean tdBean;
						if(i==2){
							width ="100px";
						}else if(3 == i){
							width = "130px";
						}else if(0 == i){
							width = "30px";
						}else if(1 == i){
							width = "120px";
						}else if(4 == i){
							width = "140px";
						}else if(5 == i){
							width = "80px";
						}else if(6 == i){
							width = "140px";
						}else if(7 == i){
							width = "100px";
						}else if(8 == i){
							width = "300px";
						}
						tdBean = new TDBean(value, width, "","");
						trBean.addTDBean(tdBean);
					}
					if(isnull){
						for(int i = 0; i < showList.length-1; i++){
							String value = "";
							
							String width = "";
							TDBean tdBean;
							if(i==2){
								width ="100px";
							}else if(3 == i){
								width = "130px";
							}
							tdBean = new TDBean(value, width, "","true");
							trBean.addTDBean(tdBean);
						}
					}
				list.add(trBean);
			}
			
		}
		
		
		if(isnull){
			TRBean trBean = new TRBean();
			trBean.setCssStyle("trsingle");
			for(int i = 0; i < showList.length-1; i++){
				String value = "";
				
				String width = "";
				TDBean tdBean;
				if(i==2){
					width ="30px";
				}else if(3 == i){
					width = "130px";
				}else if(0 == i){
					width = "30px";
				}else if(1 == i){
					width = "120px";
				}else if(4 == i){
					width = "140px";
				}else if(5 == i){
					width = "80px";
				}else if(6 == i){
					width = "140px";
				}else if(7 == i){
					width = "100px";
				}else if(8 == i){
					width = "300px";
				}
				tdBean = new TDBean(value, width, "","");
				trBean.addTDBean(tdBean);
			}
			list.add(trBean);
		}
		
		
		TRBean trBean_jyxjsyd = new TRBean();
		trBean_jyxjsyd.setCssStyle("title");
		TDBean tdname11=new TDBean("经营性建设用地小计","310","","");
		tdname11.setColspan("4");
		trBean_jyxjsyd.addTDBean(tdname11);
		
		TDBean tdname12=new TDBean(""+ydmj_jyxjsyd,"140","","");
		trBean_jyxjsyd.addTDBean(tdname12);
		
		TDBean tdname13=new TDBean(ydmj_jyxjsyd!=0?String.format("%.2f", jzmj_jyxjsyd/ydmj_jyxjsyd):"0","80","","");
		trBean_jyxjsyd.addTDBean(tdname13);
		
		TDBean tdname14=new TDBean(""+jzmj_jyxjsyd,"140","","");
		trBean_jyxjsyd.addTDBean(tdname14);
		
		TDBean tdname15=new TDBean("","100","","");
		trBean_jyxjsyd.addTDBean(tdname15);
		TDBean tdname16=new TDBean("","300","","");
		trBean_jyxjsyd.addTDBean(tdname16);
		
		list.add(trBean_jyxjsyd);
		
		for(int num=0;num<queryList.size();num++){
			Map<String, Object> map = queryList.get(num);
			String ydxzlx =String.valueOf(map.get("ydxzlx"));
			if(ydxzlx.equals("2")){
				ydmj_fjyxjsyd+=Double.valueOf(String.valueOf( map.get("jsydmj"))).doubleValue();
				jzmj_fjyxjsyd+=Double.valueOf(String.valueOf( map.get("ghjzgm"))).doubleValue();
				
				TRBean trBean = new TRBean();
				trBean.setCssStyle("trsingle");
					for(int i = 0; i < showList.length-1; i++){
						String value = String.valueOf(map.get(showList[i][0]));
						if("null".equals(value)){
							value = "";
						}
						String width = "";
						TDBean tdBean;
						if(i==2){
							width ="30px";
						}else if(3 == i){
							width = "130px";
						}else if(0 == i){
							width = "30px";
						}else if(1 == i){
							width = "120px";
						}else if(4 == i){
							width = "140px";
						}else if(5 == i){
							width = "80px";
						}else if(6 == i){
							width = "140px";
						}else if(7 == i){
							width = "100px";
						}else if(8 == i){
							width = "300px";
						}
						tdBean = new TDBean(value, width, "","");
						trBean.addTDBean(tdBean);
					}
				list.add(trBean);
			}
			
		}
		TRBean trBean_fjyxjsyd = new TRBean();
		trBean_fjyxjsyd.setCssStyle("title");
		TDBean tdname21=new TDBean("非经营性建设用地小计","310","","");
		tdname21.setColspan("4");
		trBean_fjyxjsyd.addTDBean(tdname21);
		
		TDBean tdname22=new TDBean(""+ydmj_fjyxjsyd,"140","","");
		trBean_fjyxjsyd.addTDBean(tdname22);
		
		TDBean tdname23=new TDBean(ydmj_fjyxjsyd!=0?String.format("%.2f", jzmj_fjyxjsyd/ydmj_fjyxjsyd):"0","80","","");
		trBean_fjyxjsyd.addTDBean(tdname23);
		
		TDBean tdname24=new TDBean(""+jzmj_fjyxjsyd,"140","","");
		trBean_fjyxjsyd.addTDBean(tdname24);
		
		TDBean tdname25=new TDBean("","100","","");
		trBean_fjyxjsyd.addTDBean(tdname25);
		TDBean tdname26=new TDBean("","300","","");
		trBean_fjyxjsyd.addTDBean(tdname26);
		
		list.add(trBean_fjyxjsyd);
		
		TRBean trBean_jsyd = new TRBean();
		trBean_jsyd.setCssStyle("title");
		TDBean tdname31=new TDBean("建设用地小计","310","","");
		tdname31.setColspan("4");
		trBean_jsyd.addTDBean(tdname31);
		
		TDBean tdname32=new TDBean(""+(ydmj_fjyxjsyd+ydmj_jyxjsyd),"140","","");
		trBean_jsyd.addTDBean(tdname32);
		
		TDBean tdname33=new TDBean((ydmj_fjyxjsyd+ydmj_jyxjsyd)!=0?String.format("%.2f", (jzmj_fjyxjsyd+jzmj_jyxjsyd)/(ydmj_fjyxjsyd+ydmj_jyxjsyd)):"0","80","","");
		trBean_jsyd.addTDBean(tdname33);
		
		TDBean tdname34=new TDBean(""+(jzmj_fjyxjsyd+jzmj_jyxjsyd),"140","","");
		trBean_jsyd.addTDBean(tdname34);
		
		TDBean tdname35=new TDBean("","100","","");
		trBean_jsyd.addTDBean(tdname35);
		TDBean tdname36=new TDBean("","300","","");
		trBean_jsyd.addTDBean(tdname36);
		
		list.add(trBean_jsyd);
		
		for(int num=0;num<queryList.size();num++){
			Map<String, Object> map = queryList.get(num);
			String ydxzlx =String.valueOf(map.get("ydxzlx"));
			if(ydxzlx.equals("3")){
				ydmj_dzldjsy+=Double.valueOf(String.valueOf( map.get("jsydmj"))).doubleValue();
				jzmj_dzldjsy+=Double.valueOf(String.valueOf( map.get("ghjzgm"))).doubleValue();
				
				TRBean trBean = new TRBean();
				trBean.setCssStyle("trsingle");
					for(int i = 0; i < showList.length-1; i++){
						String value = String.valueOf(map.get(showList[i][0]));
						if("null".equals(value)){
							value = "";
						}
						String width = "";
						TDBean tdBean;
						if(i==2){
							width ="30px";
						}else if(3 == i){
							width = "130px";
						}else if(0 == i){
							width = "30px";
						}else if(1 == i){
							width = "120px";
						}else if(4 == i){
							width = "140px";
						}else if(5 == i){
							width = "80px";
						}else if(6 == i){
							width = "140px";
						}else if(7 == i){
							width = "100px";
						}else if(8 == i){
							width = "300px";
						}
						tdBean = new TDBean(value, width, "","true");
						trBean.addTDBean(tdBean);
					}
				list.add(trBean);
			}
			
		}
		
		TRBean trBean_dzldjsy = new TRBean();
		trBean_dzldjsy.setCssStyle("trsingle");
		TDBean tdname41=new TDBean("代征绿地及水域","310","","");
		tdname41.setColspan("4");
		trBean_dzldjsy.addTDBean(tdname41);
		
		TDBean tdname42=new TDBean(""+ydmj_dzldjsy,"140","","");
		trBean_dzldjsy.addTDBean(tdname42);
		
		TDBean tdname43=new TDBean("--","80","","");
		trBean_dzldjsy.addTDBean(tdname43);
		
		TDBean tdname44=new TDBean(""+jzmj_dzldjsy,"140","","");
		trBean_dzldjsy.addTDBean(tdname44);
		
		TDBean tdname45=new TDBean("","100","","");
		trBean_dzldjsy.addTDBean(tdname45);
		TDBean tdname46=new TDBean("","300","","");
		trBean_dzldjsy.addTDBean(tdname46);
		
		list.add(trBean_dzldjsy);
		
		if(dzdllist.size()==0){
			String ydxzlx = "4";
			String insertString="insert into xmkgzbb (ydxzlx,yw_guid )values(?,?)";
		    update(insertString, YW,new Object[]{ydxzlx,yw_guid});
		    dzdllist = query(sqlDZDL.toString(), YW);
		}
		Map<String, Object> dzdlmap = dzdllist.get(0);
		ydmj_dzdl = Double.valueOf(String.valueOf( dzdlmap.get("dzdlydmj"))).doubleValue();
		jzmj_dzdl = Double.valueOf(String.valueOf( dzdlmap.get("dzdljzmj"))).doubleValue();
		String dzdlbz = String.valueOf(dzdlmap.get("dzdlbz"));
		String dzdlkzgd = String.valueOf(dzdlmap.get("dzdlkzgd"));
		if("null".equals(dzdlbz)){
			dzdlbz = "";
		}if("null".equals(dzdlkzgd)){
			dzdlkzgd = "";
		}
		TRBean trBean_dzdl = new TRBean();
		trBean_dzdl.setCssStyle("trsingle");
		TDBean tdname51=new TDBean("代征道路","310","","");
		tdname51.setColspan("4");
		trBean_dzdl.addTDBean(tdname51);
		
		TDBean tdname52=new TDBean(""+ydmj_dzdl,"140","","true");
		trBean_dzdl.addTDBean(tdname52);
		
		TDBean tdname53=new TDBean("--","80","","");
		trBean_dzdl.addTDBean(tdname53);
		
		TDBean tdname54=new TDBean(""+jzmj_dzdl,"140","","true");
		trBean_dzdl.addTDBean(tdname54);
		
		TDBean tdname55=new TDBean(dzdlkzgd,"100","","true");
		trBean_dzdl.addTDBean(tdname55);
		
		TDBean tdname56=new TDBean(dzdlbz,"300","","true");
		trBean_dzdl.addTDBean(tdname56);
		
		list.add(trBean_dzdl);
		
		TRBean trBean_dzd = new TRBean();
		trBean_dzd.setCssStyle("trsingle");
		TDBean tdname61=new TDBean("代征地小计","310","","");
		tdname61.setColspan("4");
		trBean_dzd.addTDBean(tdname61);
		
		TDBean tdname62=new TDBean(""+(ydmj_dzdl+ydmj_dzldjsy),"140","","");
		trBean_dzd.addTDBean(tdname62);
		
		TDBean tdname63=new TDBean("--","80","","");
		trBean_dzd.addTDBean(tdname63);
		
		TDBean tdname64=new TDBean(""+(jzmj_dzdl+jzmj_dzldjsy),"140","","");
		trBean_dzd.addTDBean(tdname64);
		
		TDBean tdname65=new TDBean("","100","","");
		trBean_dzd.addTDBean(tdname65);
		TDBean tdname66=new TDBean("","300","","");
		trBean_dzd.addTDBean(tdname66);
		
		list.add(trBean_dzd);
		
		TRBean trBean_zj = new TRBean();
		trBean_zj.setCssStyle("title");
		TDBean tdname71=new TDBean("总计","310","","");
		tdname71.setColspan("4");
		trBean_zj.addTDBean(tdname71);
		
		TDBean tdname72=new TDBean(""+(ydmj_jyxjsyd+ydmj_fjyxjsyd+ydmj_dzldjsy+ydmj_dzdl),"140","","");
		trBean_zj.addTDBean(tdname72);
		
		TDBean tdname73=new TDBean((ydmj_jyxjsyd+ydmj_fjyxjsyd+ydmj_dzldjsy+ydmj_dzdl)!=0?String.format("%.2f", (jzmj_jyxjsyd+jzmj_fjyxjsyd+jzmj_dzldjsy+jzmj_dzdl)/(ydmj_jyxjsyd+ydmj_fjyxjsyd+ydmj_dzldjsy+ydmj_dzdl)):"0","80","","");
		trBean_zj.addTDBean(tdname73);
		
		TDBean tdname74=new TDBean(""+(jzmj_jyxjsyd+jzmj_fjyxjsyd+jzmj_dzldjsy+jzmj_dzdl),"140","","");
		trBean_zj.addTDBean(tdname74);
		
		TDBean tdname75=new TDBean("","100","","");
		trBean_zj.addTDBean(tdname75);
		TDBean tdname76=new TDBean("","300","","");
		trBean_zj.addTDBean(tdname76);
		
		list.add(trBean_zj);
		return list;
	}

}
