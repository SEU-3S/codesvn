package com.klspta.web.sanya.lacc;

import com.klspta.base.AbstractBaseBean;

public class TjfxManager extends AbstractBaseBean{

	/**
	 *根据登陆人的行政区划来构建区域的tree
	 * @author 李国明
	 * 2013-03-29
    */
	public String getQyTreeByXzqh(String userID){
		String qyTree = "";
		/*
		String xzqhNames="";
		//根据用户id得到角色列表（一个用户可能有多个角色）
	    List<Role> roleList;
		try {
			roleList = RoleManager.getInstance("NEW WITH MANAGER FACTORY!").getRoleWithUserID(userID);
	
	    //接收行政区划编码字符串（得到用户角色列表的行政区划）
		String Strxzqh = "";
		for(int i=0;i<roleList.size();i++){
			Strxzqh = Strxzqh + roleList.get(i).getXzqh() + ",";
		}
		String[] xzqh_array = Strxzqh.split(",");
		//处理行政区划时的标志位（默认是xian）
		String flag = "xian";
		//用于去掉数组中可能存在的重复的行政区划编码
		HashSet<String> hs = new HashSet<String>();
		for(int j=0;j<xzqh_array.length;j++){
			if(xzqh_array[j].equals("320300")){//如果这个用户的角色的行政区划编码有370100（济南）则将标志位修改
				flag = "xuzhou";
			}
			//将数组中的数据放到set中去掉重复项
			hs.add(xzqh_array[j]);
		}

		if(flag.equals("xian")){
			 xzqhNames = "";//UtilFactory.getXzqhUtil().getXzqhNamesByCodes(hs);
			 String xzqhArray[]=xzqhNames.split(",");
			 StringBuffer sbuff = new StringBuffer();
			 sbuff.append("[{text:'本辖区',src:'sxq',checked:false,leaf:0,id:'3203',children:[");
			 int n=0;
			 for (int i = 0; i < xzqhArray.length; i++) {
				 if(n>0){
					 sbuff.append(",");
				 }
				 sbuff.append("{text:'"+xzqhArray[i]+"',checked:false,leaf:1,id:'3203"+(i+1)+"',parentId:'3203'}");
				 n++;
			}
			 sbuff.append("]}]");
			 qyTree=sbuff.toString();
		}
		if(flag.equals("xuzhou")){
		*/
			qyTree="[{text:'徐州市',src:'sxq',checked:false,leaf:0,id:'3200',"+
							 "children:["+
								"	{text:'鼓楼区',checked:false,leaf:1,id:'320302',parentId:'3200'},"+
								"	{text:'云龙区',checked:false,leaf:1,id:'320303',parentId:'3200'},"+
								"	{text:'泉山区',checked:false,leaf:1,id:'320311',parentId:'3200'},"+
								"	{text:'贾汪区',checked:false,leaf:1,id:'320313',parentId:'3200'},"+
								"	{text:'铜山区',checked:false,leaf:1,id:'320314',parentId:'3200'},"+
							    "   {text:'邳州市',checked:false,leaf:1,id:'320310',parentId:'3200'},"+
                                "   {text:'新沂市',checked:false,leaf:1,id:'320309',parentId:'3200'},"+
                                "   {text:'睢宁县',checked:false,leaf:1,id:'320308',parentId:'3200'},"+
                                "   {text:'沛 县',checked:false,leaf:1,id:'320307',parentId:'3200'},"+
                                "   {text:'丰 县',checked:false,leaf:1,id:'320306',parentId:'3200'}"+
							"]}]";		
		/*	}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return qyTree;
	}

}
