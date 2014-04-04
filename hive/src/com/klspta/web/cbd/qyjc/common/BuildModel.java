package com.klspta.web.cbd.qyjc.common;

import java.util.List;
import java.util.Map;

public class BuildModel {
    public static  String []  Month={"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};
    
    public String  getTitle(String [] year){
        StringBuffer buffer = new StringBuffer();
        buffer.append("<tr class='tr11'>");
        buffer.append("<td  width='40px'  class='tr01'><h3>序号</h3></td><td  width='295px' class='tr01'><h3>写字楼名称</h3></td>");
        for(int i=0;i<year.length;i++){
           for(int j=1;j<13;j++){
               buffer.append("<td  width='74px' class='tr01'><h3>"+year[i]+"年"+j+"月</h3></td>");
           }
        }
        buffer.append("</tr >");
        return  buffer.toString();
    }
    
    /*****
     * 
     * <br>Description:一年查看状态
     * <br>Author:朱波海
     * <br>Date:2014-1-2
     */
    public String  build_One_year(List<Map<String, Object>> list,String type,String year){
        StringBuffer buffer = new StringBuffer();
        buffer.append("<table>");
        String []years={year};
        String title = getTitle(years);
        buffer.append(title);
        for(int i=0;i<list.size();i++){
            buffer.append("<tr><td>");
            buffer.append( delNull(String.valueOf(list.get(i).get("BH"))));
            buffer.append("</td><td> ");
            buffer.append( delNull(String.valueOf(list.get(i).get("XZLMC"))));
            buffer.append("</td><td> ");
            buffer.append( delNull(String.valueOf(list.get(i).get("YY"))));
            buffer.append("</td><td> ");
            buffer.append( delNull(String.valueOf(list.get(i).get("EY"))));
            buffer.append("</td><td> ");
            buffer.append( delNull(String.valueOf(list.get(i).get("SY"))));
            buffer.append("</td><td> ");
            buffer.append( delNull(String.valueOf(list.get(i).get("SIY"))));
            buffer.append("</td><td> ");
            buffer.append( delNull(String.valueOf(list.get(i).get("WY"))));
            buffer.append("</td><td> ");
            buffer.append( delNull(String.valueOf(list.get(i).get("LY"))));
            buffer.append("</td><td> ");
            buffer.append( delNull(String.valueOf(list.get(i).get("QY"))));
            buffer.append("</td><td> ");
            buffer.append( delNull(String.valueOf(list.get(i).get("BAY"))));
            buffer.append("</td><td> ");
            buffer.append( delNull(String.valueOf(list.get(i).get("JY"))));
            buffer.append("</td><td> ");
            buffer.append( delNull(String.valueOf(list.get(i).get("SHIY"))));
            buffer.append("</td><td> ");
            buffer.append( delNull(String.valueOf(list.get(i).get("SYY"))));
            buffer.append("</td><td> ");
            buffer.append( delNull(String.valueOf(list.get(i).get("SRY"))));
            buffer.append("</td><td> </tr>");
        }
        buffer.append("</table>");
        return  buffer.toString();
    }
  
    /***
     * 
     * <br>Description:录入展现
     * <br>Author:朱波海
     * <br>Date:2014-1-3
     * @return
     */
    public String getMode1(List<Map<String, Object>> list,List<Map<String, Object>> list2,String []year,List<Map<String, Object>> cont1 ,List<Map<String, Object>> cont2){
        StringBuffer buffer = new StringBuffer();
        buffer.append("<table id='firstTable' width='2100px' >");
        String title = getTitle(year);
        buffer.append(title);

        for(int i=0;i<list.size();i++){
            buffer.append("<tr><td  class='tr04'>");
            buffer.append( delNull(String.valueOf(list.get(i).get("BH"))));
            buffer.append("</td><td  class='tr04'>");
            buffer.append( delNull(String.valueOf(list.get(i).get("XZLMC"))));
            buffer.append("</td><td class='tr03'>");
            buffer.append( delNull(String.valueOf(list.get(i).get("YY"))));
            buffer.append("</td><td  class='tr03'>");
            buffer.append( delNull(String.valueOf(list.get(i).get("EY"))));
            buffer.append("</td><td  class='tr03'>");
            buffer.append( delNull(String.valueOf(list.get(i).get("SY"))));
            buffer.append("</td><td  class='tr03'>");
            buffer.append( delNull(String.valueOf(list.get(i).get("SIY"))));
            buffer.append("</td><td  class='tr03'>");
            buffer.append( delNull(String.valueOf(list.get(i).get("WY"))));
            buffer.append("</td><td  class='tr03'>");
            buffer.append( delNull(String.valueOf(list.get(i).get("LY"))));
            buffer.append("</td><td  class='tr03'>");
            buffer.append( delNull(String.valueOf(list.get(i).get("QY"))));
            buffer.append("</td><td  class='tr03'>");
            buffer.append( delNull(String.valueOf(list.get(i).get("BAY"))));
            buffer.append("</td><td  class='tr03'>");
            buffer.append( delNull(String.valueOf(list.get(i).get("JY"))));
            buffer.append("</td><td  class='tr03'>");
            buffer.append( delNull(String.valueOf(list.get(i).get("SHIY"))));
            buffer.append("</td><td  class='tr03'>");
            buffer.append( delNull(String.valueOf(list.get(i).get("SYY"))));
            buffer.append("</td><td  class='tr03'>");
            buffer.append( delNull(String.valueOf(list.get(i).get("SRY"))));
            buffer.append("</td><td class='tr02'>");
          
            buffer.append( delNull(String.valueOf(list2.get(i).get("YY"))));
            buffer.append("</td><td  class='tr02'>");
            buffer.append( delNull(String.valueOf(list2.get(i).get("EY"))));
            buffer.append("</td><td  class='tr02'>");
            buffer.append( delNull(String.valueOf(list2.get(i).get("SY"))));
            buffer.append("</td><td  class='tr02'>");
            buffer.append( delNull(String.valueOf(list2.get(i).get("SIY"))));
            buffer.append("</td><td  class='tr02'>");
            buffer.append( delNull(String.valueOf(list2.get(i).get("WY"))));
            buffer.append("</td><td  class='tr02'>");
            buffer.append( delNull(String.valueOf(list2.get(i).get("LY"))));
            buffer.append("</td><td  class='tr02'>");
            buffer.append( delNull(String.valueOf(list2.get(i).get("QY"))));
            buffer.append("</td><td  class='tr02'>");
            buffer.append( delNull(String.valueOf(list2.get(i).get("BAY"))));
            buffer.append("</td><td class='tr02'>");
            buffer.append( delNull(String.valueOf(list2.get(i).get("JY"))));
            buffer.append("</td><td  class='tr02'>");
            buffer.append( delNull(String.valueOf(list2.get(i).get("SHIY"))));
            buffer.append("</td><td  class='tr02'>");
            buffer.append( delNull(String.valueOf(list2.get(i).get("SYY"))));
            buffer.append("</td><td  class='tr02'>");
            buffer.append( delNull(String.valueOf(list2.get(i).get("SRY"))));
            buffer.append("</td></tr>");
        }
        if(cont1.size()==3&&cont2.size()==3){
            for (int j = 0; j <cont1.size(); j++) {
                title="";
                if(j==0)
                    title="平均售价（元/天·㎡）";  
                if(j==1)
                    title="环比增长（%）";
                if(j==2)
                    title="总增长（%）";
            buffer.append("<tr><td  colspan='2' class='tr04'>");
            buffer.append(title);
            buffer.append("</td><td >");
            buffer.append( delNull(String.valueOf(cont1.get(j).get("YY"))));
            buffer.append("</td><td >");
            buffer.append( delNull(String.valueOf(cont1.get(j).get("EY"))));
            buffer.append("</td><td >");
            buffer.append( delNull(String.valueOf(cont1.get(j).get("SY"))));
            buffer.append("</td><td >");
            buffer.append( delNull(String.valueOf(cont1.get(j).get("SIY"))));
            buffer.append("</td><td >");
            buffer.append( delNull(String.valueOf(cont1.get(j).get("WY"))));
            buffer.append("</td><td>");
            buffer.append( delNull(String.valueOf(cont1.get(j).get("LY"))));
            buffer.append("</td><td >");
            buffer.append( delNull(String.valueOf(cont1.get(j).get("QY"))));
            buffer.append("</td><td >");
            buffer.append( delNull(String.valueOf(cont1.get(j).get("BAY"))));
            buffer.append("</td><td >");
            buffer.append( delNull(String.valueOf(cont1.get(j).get("JY"))));
            buffer.append("</td><td >");
            buffer.append( delNull(String.valueOf(cont1.get(j).get("SHIY"))));
            buffer.append("</td><td>");
            buffer.append( delNull(String.valueOf(cont1.get(j).get("SYY"))));
            buffer.append("</td><td >");
            buffer.append( delNull(String.valueOf(cont1.get(j).get("SRY"))));
            buffer.append("</td><td>");
          
            buffer.append( delNull(String.valueOf(cont2.get(j).get("YY"))));
            buffer.append("</td><td >");
            buffer.append( delNull(String.valueOf(cont2.get(j).get("EY"))));
            buffer.append("</td><td >");
            buffer.append( delNull(String.valueOf(cont2.get(j).get("SY"))));
            buffer.append("</td><td >");
            buffer.append( delNull(String.valueOf(cont2.get(j).get("SIY"))));
            buffer.append("</td><td >");
            buffer.append( delNull(String.valueOf(cont2.get(j).get("WY"))));
            buffer.append("</td><td >");
            buffer.append( delNull(String.valueOf(cont2.get(j).get("LY"))));
            buffer.append("</td><td>");
            buffer.append( delNull(String.valueOf(cont2.get(j).get("QY"))));
            buffer.append("</td><td >");
            buffer.append( delNull(String.valueOf(cont2.get(j).get("BAY"))));
            buffer.append("</td><td >");
            buffer.append( delNull(String.valueOf(cont2.get(j).get("JY"))));
            buffer.append("</td><td >");
            buffer.append( delNull(String.valueOf(cont2.get(j).get("SHIY"))));
            buffer.append("</td><td >");
            buffer.append( delNull(String.valueOf(cont2.get(j).get("SYY"))));
            buffer.append("</td><td >");
            buffer.append( delNull(String.valueOf(cont2.get(j).get("SRY"))));
            buffer.append("</td></tr>");
            
            
            
            }  
        }
        buffer.append(" </table>");
        return buffer.toString();
    }
    
    public String delNull(String str){
        if(str.equals("null")||str.equals("0")){
            return "";
        }else {
            return str;
        }
        
        
    }
   
 /******
  * 
  * <br>Description:租金情况展现 修改-保存
  * <br>Author:朱波海
  * <br>Date:2014-1-6
  */
  public String getZjqkTable(List<Map<String, Object>> list){
      StringBuffer buffer = new StringBuffer();
      buffer.append("<table align='center' width='100%'  id='firstTable'><tr id='title' class='tr01'><td  width='50px' class='tr01'>编号</td><td  width='180px' class='tr01'>写字楼名称</td><td width='180px' class='tr01'>地址</td><td width='50px' class='tr01'>城区</td><td  width='50px' class='tr01'>商圈</td><td width='50px' class='tr01'>地铁</td><td  width='54px' class='tr01'>出租价格</td><td width='50px' class='tr01'>售价</td><td width='50px' class='tr01'>租售比</td><td width='160px'  class='tr01'>可租售面积</td> <td width='200px' class='tr01'>信息</td></tr>");
     for(int i=0;i<list.size();i++){
       buffer.append("<tr>");
       buffer.append("<td class='tr04'> ");
       buffer.append(delNull(String.valueOf(list.get(i).get("BH"))));
       buffer.append("</td> ");
       buffer.append("<td class='tr04'> ");
       buffer.append(delNull(String.valueOf(list.get(i).get("XZLMC"))));
       buffer.append("</td> ");
       buffer.append("<td> <input style='width: 180px' id='"+list.get(i).get("yw_guid")+"_DZ' onchange='chang(this)' value=' ");
       buffer.append(delNull(String.valueOf(list.get(i).get("DZ"))));
       buffer.append("' /></td> ");
       buffer.append("<td> <input style='width:54px' id='"+list.get(i).get("yw_guid")+"_CQ' onchange='chang(this)' value='  ");
       buffer.append(delNull(String.valueOf(list.get(i).get("CQ")).trim()));
       buffer.append("' /></td> ");
       buffer.append("<td>  <input style='width:50px' id='"+list.get(i).get("yw_guid")+"_SQ' onchange='chang(this)' value='");
       buffer.append(delNull(String.valueOf(list.get(i).get("SQ")).trim()));
       buffer.append("' /></td> ");
       buffer.append("<td>  <input style='width: 50px' id='"+list.get(i).get("yw_guid")+"_DT' onchange='chang(this)' value='");
       buffer.append(delNull(String.valueOf(list.get(i).get("DT"))));
       buffer.append("' /></td> ");
       buffer.append("<td>  <input  style='width:50px'  id='"+list.get(i).get("yw_guid")+"_CZJG' onchange='chang(this)' value='");
       buffer.append(delNull(String.valueOf(list.get(i).get("CZJG"))));
       buffer.append("'/></td> ");
       buffer.append("<td>  <input style='width:50px'  id='"+list.get(i).get("yw_guid")+"_SJ' onchange='chang(this)' value='");
       buffer.append(delNull(String.valueOf(list.get(i).get("SJ"))));
       buffer.append("' /></td> ");
       buffer.append("<td>  <input style='width:50px'  id='"+list.get(i).get("yw_guid")+"_ZSB' onchange='chang(this)' value='");
       buffer.append(delNull(String.valueOf(list.get(i).get("ZSB"))));
       buffer.append("' /></td> ");
       buffer.append("<td>  <input style='width:160px'  id='"+list.get(i).get("yw_guid")+"_KZSMJ' onchange='chang(this)' value='");
       buffer.append(delNull(String.valueOf(list.get(i).get("KZSMJ"))));
       buffer.append("' /></td> ");
       buffer.append("<td>  <input style='width:200px'  id='"+list.get(i).get("yw_guid")+"_XX'  onchange='chang(this)' value='");
       buffer.append(delNull(String.valueOf(list.get(i).get("XX"))));
       buffer.append("' /></td> ");
       buffer.append("</tr>");
     }
      
      buffer.append("</table>");
      return  buffer.toString();
      
  }
  /****
   * 
   * <br>Description:录入信息
   * <br>Author:朱波海
   * <br>Date:2014-1-7
   * @param list
   * @param list2
   * @return
   */
  public String getZjqkNd(List<Map<String, Object>> list,List<Map<String, Object>> list2,List<Map<String, Object>> cont1 ,List<Map<String, Object>> cont2){
      StringBuffer buffer = new StringBuffer();
      buffer.append("<table id='firstTable'><tr id='title' class='tr01'><td width='50px' class='tr01'>编号</td><td width='150px' colspan='2' class='tr01'>写字楼名称</td><td width='50px' class='tr01'> 一月</td><td width='50px' class='tr01'>二月</td><td width='50px' class='tr01'>三月</td><td width='50px' class='tr01'>四月</td><td width='50px' class='tr01'>五月</td><td  width='50px' class='tr01'>六月</td><td width='50px' class='tr01'>七月</td><td  width='50px' class='tr01'>八月</td> <td width='50px' class='tr01'>九月</td><td width='50px' class='tr01'>十月</td><td width='50px' class='tr01'>十一月</td><td width='50px' class='tr01'>十二月</td></tr>");
      for(int i=0;i<list.size();i++){
          buffer.append("<tr>");
          buffer.append("<td rowspan='2' class='tr04'> ");
          buffer.append(delNull(String.valueOf(list.get(i).get("BH"))));
          buffer.append("</td> ");
          buffer.append("<td rowspan='2' class='tr04'> ");
          buffer.append(delNull(String.valueOf(list.get(i).get("XZLMC"))));
          buffer.append("</td> ");
          buffer.append("<td class='tr04'>售价</td>");
          buffer.append("<td> <input id='"+list.get(i).get("yw_guid")+"_YY' onchange='chang(this)' value=' ");
          buffer.append(delNull(String.valueOf(list.get(i).get("YY"))));
          buffer.append("'/></td> ");
          buffer.append("<td> <input id='"+list.get(i).get("yw_guid")+"_EY' onchange='chang(this)' value='  ");
          buffer.append(delNull(String.valueOf(list.get(i).get("EY"))));
          buffer.append("'/></td> ");
          buffer.append("<td>  <input id='"+list.get(i).get("yw_guid")+"_SY' onchange='chang(this)' value='");
          buffer.append(delNull(String.valueOf(list.get(i).get("SY"))));
          buffer.append("'/></td> ");
          buffer.append("<td>  <input id='"+list.get(i).get("yw_guid")+"_SIY' onchange='chang(this)' value='");
          buffer.append(delNull(String.valueOf(list.get(i).get("SIY"))));
          buffer.append("'/></td> ");
          buffer.append("<td>  <input id='"+list.get(i).get("yw_guid")+"_WY' onchange='chang(this)' value='");
          buffer.append(delNull(String.valueOf(list.get(i).get("WY"))));
          buffer.append("'/></td> ");
          buffer.append("<td>  <input id='"+list.get(i).get("yw_guid")+"_LY' onchange='chang(this)' value='");
          buffer.append(delNull(String.valueOf(list.get(i).get("LY"))));
          buffer.append("'/></td> ");
          buffer.append("<td>  <input id='"+list.get(i).get("yw_guid")+"_QY' onchange='chang(this)' value='");
          buffer.append(delNull(String.valueOf(list.get(i).get("QY"))));
          buffer.append("'/></td> ");
          buffer.append("<td>  <input id='"+list.get(i).get("yw_guid")+"_BAY' onchange='chang(this)' value='");
          buffer.append(delNull(String.valueOf(list.get(i).get("BAY"))));
          buffer.append("'/></td> ");
          buffer.append("<td>  <input id='"+list.get(i).get("yw_guid")+"_JY'  onchange='chang(this)' value='");
          buffer.append(delNull(String.valueOf(list.get(i).get("JY"))));
          buffer.append("'/></td> ");
          buffer.append("<td>  <input id='"+list.get(i).get("yw_guid")+"_SHIY'  onchange='chang(this)' value='");
          buffer.append(delNull(String.valueOf(list.get(i).get("SHIY"))));
          buffer.append("'/></td> ");
          buffer.append("<td>  <input id='"+list.get(i).get("yw_guid")+"_SYY'  onchange='chang(this)' value='");
          buffer.append(delNull(String.valueOf(list.get(i).get("SYY"))));
          buffer.append("'/></td> ");
          buffer.append("<td>  <input id='"+list.get(i).get("yw_guid")+"_SEY'  onchange='chang(this)' value='");
          buffer.append(delNull(String.valueOf(list.get(i).get("SEY"))));
          buffer.append("'/></td> ");
          buffer.append("</tr>");
          
          buffer.append("<tr>");
          buffer.append("<td class='tr04'>租金</td>");
          buffer.append("<td> <input id='"+list2.get(i).get("yw_guid")+"_YY' onchange='cha(this)' value=' ");
          buffer.append(delNull(String.valueOf(list2.get(i).get("YY"))));
          buffer.append("'/></td> ");
          buffer.append("<td> <input id='"+list2.get(i).get("yw_guid")+"_EY' onchange='cha(this)' value='  ");
          buffer.append(delNull(String.valueOf(list2.get(i).get("EY"))));
          buffer.append("'/></td> ");
          buffer.append("<td>  <input id='"+list2.get(i).get("yw_guid")+"_SY' onchange='cha(this)' value='");
          buffer.append(delNull(String.valueOf(list2.get(i).get("SY"))));
          buffer.append("'/></td> ");
          buffer.append("<td>  <input id='"+list2.get(i).get("yw_guid")+"_SIY' onchange='cha(this)' value='");
          buffer.append(delNull(String.valueOf(list2.get(i).get("SIY"))));
          buffer.append("'/></td> ");
          buffer.append("<td>  <input id='"+list2.get(i).get("yw_guid")+"_WY' onchange='cha(this)' value='");
          buffer.append(delNull(String.valueOf(list2.get(i).get("WY"))));
          buffer.append("'/></td> ");
          buffer.append("<td>  <input id='"+list2.get(i).get("yw_guid")+"_LY' onchange='cha(this)' value='");
          buffer.append(delNull(String.valueOf(list2.get(i).get("LY"))));
          buffer.append("'/></td> ");
          buffer.append("<td>  <input id='"+list2.get(i).get("yw_guid")+"_QY' onchange='cha(this)' value='");
          buffer.append(delNull(String.valueOf(list2.get(i).get("QY"))));
          buffer.append("'/></td> ");
          buffer.append("<td>  <input id='"+list2.get(i).get("yw_guid")+"_BAY' onchange='cha(this)' value='");
          buffer.append(delNull(String.valueOf(list2.get(i).get("BAY"))));
          buffer.append("'/></td> ");
          buffer.append("<td>  <input id='"+list2.get(i).get("yw_guid")+"_JY'  onchange='cha(this)' value='");
          buffer.append(delNull(String.valueOf(list2.get(i).get("JY"))));
          buffer.append("'/></td> ");
          buffer.append("<td>  <input id='"+list2.get(i).get("yw_guid")+"_SHIY'  onchange='cha(this)' value='");
          buffer.append(delNull(String.valueOf(list2.get(i).get("SHIY"))));
          buffer.append("'/></td> ");
          buffer.append("<td>  <input id='"+list2.get(i).get("yw_guid")+"_SYY'  onchange='cha(this)' value='");
          buffer.append(delNull(String.valueOf(list2.get(i).get("SYY"))));
          buffer.append("'/></td> ");
          buffer.append("<td>  <input id='"+list2.get(i).get("yw_guid")+"_SEY'  onchange='cha(this)' value='");
          buffer.append(delNull(String.valueOf(list2.get(i).get("SEY"))));
          buffer.append("'/></td> ");
          
          buffer.append("</tr>");
        }
      String title="";
      if(cont1.size()==3&&cont2.size()==3){
          for (int j = 0; j <cont1.size(); j++) {
              title="";
              if(j==0)
                  title="平均售价（元/天·㎡）";  
              if(j==1)
                  title="环比增长（%）";
              if(j==2)
                  title="总增长（%）";
          buffer.append("<tr><td  colspan='3' class='tr04'>");
          buffer.append(title);
          buffer.append("</td><td >");
          buffer.append( delNull(String.valueOf(cont1.get(j).get("yy"))));
          buffer.append("</td><td >");
          buffer.append( delNull(String.valueOf(cont1.get(j).get("ey"))));
          buffer.append("</td><td >");
          buffer.append( delNull(String.valueOf(cont1.get(j).get("sy"))));
          buffer.append("</td><td >");
          buffer.append( delNull(String.valueOf(cont1.get(j).get("siy"))));
          buffer.append("</td><td >");
          buffer.append( delNull(String.valueOf(cont1.get(j).get("wy"))));
          buffer.append("</td><td>");
          buffer.append( delNull(String.valueOf(cont1.get(j).get("ly"))));
          buffer.append("</td><td >");
          buffer.append( delNull(String.valueOf(cont1.get(j).get("qy"))));
          buffer.append("</td><td >");
          buffer.append( delNull(String.valueOf(cont1.get(j).get("bay"))));
          buffer.append("</td><td >");
          buffer.append( delNull(String.valueOf(cont1.get(j).get("jy"))));
          buffer.append("</td><td >");
          buffer.append( delNull(String.valueOf(cont1.get(j).get("shiy"))));
          buffer.append("</td><td>");
          buffer.append( delNull(String.valueOf(cont1.get(j).get("syy"))));
          buffer.append("</td><td >");
          buffer.append( delNull(String.valueOf(cont1.get(j).get("sey"))));
          buffer.append("</td></tr>");  
          }  
      }
      if(cont2.size()==3){
          for (int j = 0; j <cont2.size(); j++) {
              title="";
              if(j==0)
                  title="平均租金（元/天·㎡）";  
              if(j==1)
                  title="环比增长（%）";
              if(j==2)
                  title="总增长（%）";
              buffer.append("<tr><td  colspan='3' class='tr04'>");
              buffer.append(title);
              buffer.append("</td><td >");
              buffer.append( delNull(String.valueOf(cont2.get(j).get("yy"))));
              buffer.append("</td><td >");
              buffer.append( delNull(String.valueOf(cont2.get(j).get("ey"))));
              buffer.append("</td><td >");
              buffer.append( delNull(String.valueOf(cont2.get(j).get("sy"))));
              buffer.append("</td><td >");
              buffer.append( delNull(String.valueOf(cont2.get(j).get("siy"))));
              buffer.append("</td><td >");
              buffer.append( delNull(String.valueOf(cont2.get(j).get("wy"))));
              buffer.append("</td><td >");
              buffer.append( delNull(String.valueOf(cont2.get(j).get("ly"))));
              buffer.append("</td><td>");
              buffer.append( delNull(String.valueOf(cont2.get(j).get("qy"))));
              buffer.append("</td><td >");
              buffer.append( delNull(String.valueOf(cont2.get(j).get("bay"))));
              buffer.append("</td><td >");
              buffer.append( delNull(String.valueOf(cont2.get(j).get("jy"))));
              buffer.append("</td><td >");
              buffer.append( delNull(String.valueOf(cont2.get(j).get("shiy"))));
              buffer.append("</td><td >");
              buffer.append( delNull(String.valueOf(cont2.get(j).get("syy"))));
              buffer.append("</td><td >");
              buffer.append( delNull(String.valueOf(cont2.get(j).get("sey"))));
              buffer.append("</td></tr>");
          }
      }
         buffer.append("</table>");
         return  buffer.toString();
         
  }
    
    
    

}
