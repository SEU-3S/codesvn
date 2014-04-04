package com.klspta.web.cbd.yzt.kgzb;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
/*****
 * 
 * <br>Title:拼写前台table类
 * <br>Description:
 * <br>Author:朱波海
 * <br>Date:2014-1-15
 */
public class BuilTable {
	
	public StringBuffer getTitle(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("<tr><td class='tr01'>序号</td><td class='tr01'>地块名称 </td><td class='tr01' colspan='2'>用地性质 </td><td class='tr01'>建设用地面积（公顷）</td><td class='tr01'>容积率 </td><td class='tr01'>规划建筑规模（万㎡） </td><td class='tr01'>建筑控制高度（米） </td><td class='tr01'>建筑密度</td><td class='tr01'>绿化率 </td><td class='tr01'>南北纵深（米） </td><td class='tr01'>东西面宽（米） </td><td class='tr01'>规划数据来源 </td><td class='tr01'  width='80px'>备注 </td><td class='tr01'  width='80px'>操作</td></tr>");
		return  buffer;
	}
	
	public StringBuffer getQyTr(List<Map<String, Object>> list,List<Map<String, Object>> sumList){
		StringBuffer buffer = new StringBuffer();
		return buffer;
	}

	public StringBuffer getQyTr(List<Map<String, Object>> list){
		StringBuffer buffer = new StringBuffer();
		for(int i=0;i<list.size();i++){
			int index=1+i;
			buffer.append("<tr id='"+list.get(i).get("yw_guid")+"' onclick='showMap(this); return false;' ondblclick='editMap(this); return false;'><td id='' onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>");
			buffer.append(index);
			buffer.append("</td><td  onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>");
			buffer.append(delNull(String.valueOf(list.get(i).get("DKMC"))));
			buffer.append("</td><td  onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>");
			buffer.append(delNull(String.valueOf(list.get(i).get("YDXZ"))));
			buffer.append("</td><td  onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>");
			buffer.append(delNull(String.valueOf(list.get(i).get("YDXZDH"))));
			buffer.append("</td><td  onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>");
			buffer.append(delNull(String.valueOf(list.get(i).get("JSYDMJ"))));
			buffer.append("</td><td  onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>");
			buffer.append(delNull(String.valueOf(list.get(i).get("RJL"))));
			buffer.append("</td><td  onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>");
			buffer.append(delNull(String.valueOf(list.get(i).get("GHJZGM"))));
			buffer.append("</td><td  onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>");
			buffer.append(delNull(String.valueOf(list.get(i).get("JZKZGD"))));
			buffer.append("</td><td  onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>");
			buffer.append(delNull(String.valueOf(list.get(i).get("JZMD"))));
			buffer.append("</td><td  onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>");
			buffer.append(delNull(String.valueOf(list.get(i).get("LHL"))));
			buffer.append("</td><td  onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'> ");
			buffer.append(delNull(String.valueOf(list.get(i).get("DBZS"))));
			buffer.append("</td><td v>");
			buffer.append(delNull(String.valueOf(list.get(i).get("DXMK"))));
			buffer.append("</td><td  onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>");
			buffer.append(delNull(String.valueOf(list.get(i).get("GHSJLY"))));
			buffer.append("</td><td  onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>");
			buffer.append(delNull(String.valueOf(list.get(i).get("BZ"))));
			buffer.append("</td><td> <img  width='40px' height='40px' src='web/cbd/yzt/kgzb/image/m.png' onclick=\"modify1('"+list.get(i).get("yw_guid").toString()+"')\"></img><img  width='40px' height='40px' src='web/cbd/yzt/kgzb/image/d.png' onclick=\"delet('"+list.get(i).get("yw_guid").toString()+"')\"></img></td></tr>");
		}
		return buffer;
	}
   public StringBuffer getQyTr_sum(List<Map<String, Object>> list){
	   StringBuffer buffer = new StringBuffer();
		if(list.size()>0){
			buffer.append("<tr><td colspan='2' class='tr01'>");
			buffer.append(list.get(0).get("qy").toString());
			buffer.append("合计</td><td class='tr01'>----</td><td class='tr01'>----");
			buffer.append("</td><td class='tr01'>");
			buffer.append(delNull(String.valueOf(list.get(0).get("JSYDMJ"))));
			buffer.append("</td><td class='tr01' >");
			buffer.append(getOpration(list.get(0).get("GHJZGM").toString(),list.get(0).get("JSYDMJ").toString()));
			buffer.append("</td><td class='tr01'>");
			buffer.append(delNull(String.valueOf(list.get(0).get("GHJZGM"))));
			buffer.append("</td><td class='tr01'>----");
			buffer.append("</td><td class='tr01'>----");
			buffer.append("</td><td class='tr01'>----");
			buffer.append("</td><td class='tr01'>");
			buffer.append("</td><td class='tr01'>");
			buffer.append("</td><td class='tr01'>");
			buffer.append("</td><td class='tr01'>----");
			buffer.append("</td><td class='tr01'>----");
			buffer.append("</td></tr>");
			
		}
		return buffer;
	}
	
 public static String delNull(String str){
	      if(str.equals("null")||str.equals("0")){
	          return "";
	      }else {
	        return str;
	    }
	  }

 public double getOpration(String Str1, String str2) {
	 double doub1 = Double.parseDouble(Str1);
	 double doub2 = Double.parseDouble(str2);
     if (doub2 <1) {
         return 0.0000;
     } else {
         BigDecimal   b   =   new   BigDecimal((doub1 / doub2) );  
         double   f1   =   b.setScale(4,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
         return f1;
     }
 }
}
