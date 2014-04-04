package com.klspta.web.cbd.xmgl.zjgl;

import java.util.List;
import java.util.Map;
/*****
 * 
 * <br>Title:工厂类
 * <br>Description:根据数据提供前台展现页面
 * <br>Author:朱波海
 * <br>Date:2013-12-26
 */
public class TrFactory {
    static ZjglData zjglData= new ZjglData();
   
    /******
     * 资金指出-入口1-old
     */
//   public static StringBuffer getmodel(List<Map<String, Object>> list, String yw_guid, String type,String year,String rolename) {
//        StringBuffer buffer = new StringBuffer();
//        if(type.equals("ZJZC")){
//            List<Map<String, Object>> li = zjglData.getZC_sum(yw_guid,year);
//            StringBuffer stringBuffer = ZjglBuild.buildZjzc_father_sum(li);
//            buffer.append(stringBuffer);
//            return buffer;
//        }else if(type.equals("YJKFZC")){
//            List<Map<String, Object>> ls = zjglData.getZC_YJZC_sum(yw_guid,year);
//            StringBuffer stringBuffer = ZjglBuild.buildZjzc_father_sum(ls);
//            buffer.append(stringBuffer);
//            return buffer;
//        }else{
//        if (list != null||list.size()>0) {
//            StringBuffer fatehr = buildFather(yw_guid, type,year ,rolename);
//            StringBuffer chaild = buildChild(yw_guid, list, type,year,rolename);
//            buffer.append(fatehr);
//            buffer.append(chaild);
//            return buffer;
//        } else {
//            StringBuffer fatehr = buildFather(yw_guid, type,year,rolename);
//            buffer.append(fatehr);
//            return buffer;
//        }
//        }
//    }
   /******
    * 资金指出-入口2-new 查看
    */
  public static StringBuffer getmodel_view(List<Map<String, Object>> list, String yw_guid, String type,String year,String rolename) {
       StringBuffer buffer = new StringBuffer();
       if(type.equals("ZJZC")){
           List<Map<String, Object>> li = zjglData.getZC_sum(yw_guid,year);
           StringBuffer stringBuffer = ZjglBuild.buildZjzc_father_sum(li);
           buffer.append(stringBuffer);
           return buffer;
       }else if(type.equals("YJKFZC")){
           List<Map<String, Object>> ls = zjglData.getZC_YJZC_sum(yw_guid,year);
           StringBuffer stringBuffer = ZjglBuild.buildZjzc_father_sum(ls);
           buffer.append(stringBuffer);
           return buffer;
       }else{
       if (list != null||list.size()>0) {
           StringBuffer fatehr = buildFather_view(yw_guid, type,year);
           StringBuffer chaild = buildChild_view(yw_guid, list, type,year);
           buffer.append(fatehr);
           buffer.append(chaild);
           return buffer;
       } else {
           StringBuffer fatehr = buildFather_view(yw_guid, type,year);
           buffer.append(fatehr);
           return buffer;
       }
       }
   }
  /******
   * 资金指出-入口2-new编辑
   */
 public static StringBuffer getmodel_editor(List<Map<String, Object>> list, String yw_guid, String type,String year,String rolename) {
      StringBuffer buffer = new StringBuffer();
      if(type.equals("ZJZC")){
          List<Map<String, Object>> li = zjglData.getZC_sum(yw_guid,year);
          StringBuffer stringBuffer = ZjglBuild.buildZjzc_father_sum(li);
          buffer.append(stringBuffer);
          return buffer;
      }else if(type.equals("YJKFZC")){
          List<Map<String, Object>> ls = zjglData.getZC_YJZC_sum(yw_guid,year);
          StringBuffer stringBuffer = ZjglBuild.buildZjzc_father_sum(ls);
          buffer.append(stringBuffer);
          return buffer;
      }else{
      if (list != null||list.size()>0) {
          StringBuffer fatehr = buildFather_editor(yw_guid, type,year,rolename);
          StringBuffer chaild = buildChild_editor(yw_guid, list, type,year,rolename);
          buffer.append(fatehr);
          buffer.append(chaild);
          return buffer;
      } else {
          StringBuffer fatehr = buildFather_editor(yw_guid, type,year,rolename);
          buffer.append(fatehr);
          return buffer;
      }
      }
  }
   /******
    * 资金流入-入口-编辑
    */
   public static StringBuffer getmod(String yw_guid,String year,String rolename){
        StringBuffer buffer = new StringBuffer();
        //总计 20140221 delete by lichunxing 
        List<Map<String, Object>> query=zjglData.getLR_sum(yw_guid,year);
//        List<Map<String, Object>> list = zjglData. getZJGL_ZJLR(yw_guid,year);
        StringBuffer stringBufZJ = ZjglBuild.buildZjlr_sum(query);
        buffer.append(stringBufZJ);
//        StringBuffer stringBuffer = ZjglBuild.buildZjlr(list);
//        buffer.append(stringBuffer);
        List<Map<String, Object>> list = zjglData. getZJGL_ZJLR(yw_guid,year);
        buffer.append(ZjglBuild.buildZjlrTR(list, "ZJLR", "write").get(0));
        return buffer;
        
    }
   /******
    * 资金流入-入口-查看
    */
   public static StringBuffer getmod_view(String yw_guid,String year,String rolename){
        StringBuffer buffer = new StringBuffer();
        //总计
        List<Map<String, Object>> query=zjglData.getLR_sum(yw_guid,year);
//        List<Map<String, Object>> list = zjglData. getZJGL_ZJLR(yw_guid,year);
        StringBuffer stringBufZJ = ZjglBuild.buildZjlr_sum(query);
        buffer.append(stringBufZJ);
//        StringBuffer stringBuffer = ZjglBuild.buildZjlr_sum(list);
//        buffer.append(stringBuffer);
        List<Map<String, Object>> list = zjglData. getZJGL_ZJLR(yw_guid,year);
        buffer.append(ZjglBuild.buildZjlrTR(list, "ZJLR", "read").get(0));
        return buffer;
        
    }
   /*****
    * 
    * <br>Description:构建父类old
    * <br>Author:朱波海
    * <br>Date:2013-12-18
    */
   public static StringBuffer buildFather(String yw_guid, String type ,String year,String rolename) {
       List<Map<String, Object>> list = zjglData. getZJGL_father(yw_guid, type,year);
       StringBuffer stringBuffer = ZjglBuild.buildZjzc_father(list,rolename);
       return stringBuffer;
   }

   /*****
    * 
    * <br>Description:构建父类查看
    * <br>Author:朱波海
    * <br>Date:2013-12-18
    */
   public static StringBuffer buildFather_view(String yw_guid, String type ,String year) {
       List<Map<String, Object>> list = zjglData. getZJGL_father(yw_guid, type,year);
       StringBuffer stringBuffer = ZjglBuild.buildZjzc_father_view(list);
       return stringBuffer;
   }
   /*****
    * 
    * <br>Description:构建父类编辑
    * <br>Author:朱波海
    * <br>Date:2013-12-18
    */
   public static StringBuffer buildFather_editor(String yw_guid, String type ,String year,String rolename) {
       List<Map<String, Object>> list = zjglData. getZJGL_father(yw_guid, type,year);
       StringBuffer stringBuffer = ZjglBuild.buildZjzc_father(list,rolename);
       return stringBuffer;
   }

   /*****
    * 
    * <br>Description:构建子类old
    * <br>Author:朱波海
    * <br>Date:2013-12-18
    */
   public static StringBuffer buildChild(String yw_guid, List<Map<String, Object>> list, String type,String year,String rolename) {
       StringBuffer buffer = new StringBuffer();
       if (list != null) {
           for (int i = 0; i < list.size(); i++) {
               String tree_name = list.get(i).get("tree_name").toString();
               List<Map<String, Object>> query = zjglData. getZJGL_child(yw_guid,tree_name,type,year);
               StringBuffer stringBuffer = ZjglBuild.buildZjzc_child(query,rolename);
               buffer.append(stringBuffer);
           }
       }
       return buffer;
   }

   /*****
    * 
    * <br>Description:构建子类编辑
    * <br>Author:朱波海
    * <br>Date:2013-12-18
    */
   public static StringBuffer buildChild_editor(String yw_guid, List<Map<String, Object>> list, String type,String year,String rolename) {
       StringBuffer buffer = new StringBuffer();
       if (list != null) {
           for (int i = 0; i < list.size(); i++) {
               String tree_name = list.get(i).get("tree_name").toString();
               List<Map<String, Object>> query = zjglData. getZJGL_child(yw_guid,tree_name,type,year);
               StringBuffer stringBuffer = ZjglBuild.buildZjzc_child(query,rolename);
               buffer.append(stringBuffer);
           }
       }
       return buffer;
   }
   
   /*****
    * 
    * <br>Description:构建子类查看
    * <br>Author:朱波海
    * <br>Date:2013-12-18
    */
   public static StringBuffer buildChild_view(String yw_guid, List<Map<String, Object>> list, String type,String year) {
       StringBuffer buffer = new StringBuffer();
       if (list != null) {
           for (int i = 0; i < list.size(); i++) {
               String tree_name = list.get(i).get("tree_name").toString();
               List<Map<String, Object>> query = zjglData. getZJGL_child(yw_guid,tree_name,type,year);
               StringBuffer stringBuffer = ZjglBuild.buildZjzc_child_view(query);
               buffer.append(stringBuffer);
           }
       }
       return buffer;
   }
   
}
