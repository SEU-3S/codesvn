package com.klspta.web.jizeNW.dtxc.cgdr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.rmi.server.UID;
import java.util.List;
import java.util.Map;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.springframework.stereotype.Component;
import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

/**
 * 需要在conf下的applicationContext-bean.xml中，增加配置信息：
 * <bean name="simpleExample" class="com.klspta.model.SimpleExample" scope="prototype"/>
 * @author wang
 *
 */
@Component
public class ResultExp extends AbstractBaseBean {
    /**
     * 临时位置
     */
    private String tempPath;

    public ResultExp() {
        tempPath = getTemp();
    }

    /**
     * 
     * <br>Description:任务成果导出
     * <br>Author:陈强峰
     * <br>Date:2012-8-13
     */
    public void expResult() {
        String ids = request.getParameter("ids");
        String[] ywids;
        if (ids.contains("@")) {
            ywids = ids.split("@");
        } else {
            ywids = new String[] { ids };
        }
        String file_id = new UID().toString().replaceAll(":", "-");
        String filepath = tempPath + file_id + "//exp";
        File dirs = new File(filepath);
        if (!dirs.isFile()) {
            dirs.mkdirs();
        }
        generateXML(filepath, ywids);
        resultToZip(tempPath + file_id, tempPath + file_id);
        response(tempPath + file_id + ".zip");
    }
    
    /**
     * 
     * <br>Description:生成xml文件
     * <br>Author:王雷
     * <br>Date:2013-9-23
     * @param filepath
     * @param guids
     */
    private void generateXML(String filepath, String[] guids){
        //基础信息sql
        String baseSql = "select t.yw_guid,t.ydsj,t.yddw,t.mj,t.zb,t.jsqk,t.wfwglx,t.dfccqk,t.xcms,t.hcrq,t.spsj,t.spxmmc,t.spwh,t.gdsj,t.gdxmmc,t.gdwh,t.ydqk,t.status,t.ygspmj,t.ygspbl,t.yggdmj,t.yggdbl,t.nyd,t.gengd,t.jsyd,t.wlyd,t.fhgh,t.bfhgh,t.zyjbnt,t.xmmc,t.pfwh,t.pzsj,t.yxjsq,t.ytjjsq,t.xzjsq,t.jzjsq,t.xcr,t.xcdw,t.ordertime,t.jwzb,t.pmzb,t.padid,t.shi,t.xian from dc_ydqkdcb t where t.yw_guid=?";
        //基础信息节点
        String[] baseElements = {"yw_guid","ydsj","yddw","mj","zb","jsqk","wfwglx","dfccqk","xcms","hcrq","spsj","spxmmc","spwh","gdsj","gdxmmc","gdwh","ydqk","status","ygspmj","ygspbl","yggdmj","yggdbl","nyd","gengd","jsyd","wlyd","fhgh","bfhgh","zyjbnt","xmmc","pfwh","pzsj","yxjsq","ytjjsq","xzjsq","jzjsq","xcr","xcdw","ordertime"};
        //现状sql
        String xzSql = "select t.yw_guid,t.tbbh,t.qsdwmc,t.dlmc,t.mj from xz_xxdl t where t.yw_guid=?";
        //现状节点
        String[] xzElements = {"id","yw_guid","tbbh","qsdwmc","dlmc","mj"};
        //规划sql
        String ghSql = "select t.yw_guid,t.tdytfqdm,t.ghdlmc,t.xzqmc,t.mj from gh_xxdl t where t.yw_guid=?";
        //规划节点
        String[] ghElements = {"id","yw_guid","tdytfqdm","ghdlmc","xzqmc","mj"};
        //审批sql
        String spSql = "select t.yw_guid,t.spxmmc,t.spwh,t.spsj,t.spygbl,t.spygmj from sp_xxxm t where t.yw_guid=?";
        //审批节点
        String[] spElements = {"id","yw_guid","spxmmc","spwh","spsj","spygbl","spygmj"};
        //供地sql
        String gdSql = "select t.yw_guid,t.gdxmmc,t.gdwh,t.gdsj,t.gdygbl,t.gdygmj from gd_xxxm t where t.yw_guid=?";
        //供地节点
        String[] gdElements = {"id","yw_guid","gdxmmc","gdwh","gdsj","gdygbl","gdygmj"};
        //坐标节点
        String[] zbElements = {"id","yw_guid","jwzb","pmzb"};
        for(int i=0;i<guids.length;i++){
            String folder = filepath + "//" + guids[i];
            File dir = new File(folder);
            dir.mkdirs();          
            List<Map<String, Object>> baseList = query(baseSql, YW, new Object[] { guids[i] });
            Map<String,Object> baseMap = baseList.get(0);
            Element root = new Element("WYHC");    
            Document Doc = new Document(root);   
            //base节点
            Element base = new Element("base");             
            for(int j=0;j<baseElements.length;j++){
               Element e = new Element(baseElements[j].toUpperCase());
               e.setText((String)baseMap.get(baseElements[j])); 
               base.addContent(e);
            }            
            root.addContent(base);
            //xz节点           
            Element xz = new Element("xz");
            List<Map<String, Object>> xzList = query(xzSql, YW, new Object[] { guids[i] });        
            if(xzList != null && xzList.size() > 0){
                for(int j=0;j<xzList.size();j++){
                    Map<String,Object> xzMap = xzList.get(j);
                    xzMap.put("id", j+"");
                    Element e = new Element("num"+j);
                    for(int k=0;k<xzElements.length;k++){
                        Element ee = new Element(xzElements[k].toUpperCase());
                        ee.setText((String)xzMap.get(xzElements[k])); 
                        e.addContent(ee);
                     }   
                    xz.addContent(e);
                }                  
            }
            root.addContent(xz);
            //gh节点
            Element gh = new Element("gh");
            List<Map<String, Object>> ghList = query(ghSql, YW, new Object[] { guids[i] });        
            if(ghList != null && ghList.size() > 0){
                for(int j=0;j<ghList.size();j++){
                    Map<String,Object> ghMap = ghList.get(j);
                    ghMap.put("id", j+"");
                    Element e = new Element("num"+j);
                    for(int k=0;k<ghElements.length;k++){
                        Element ee = new Element(ghElements[k].toUpperCase());
                        ee.setText((String)ghMap.get(ghElements[k])); 
                        e.addContent(ee);
                     }   
                    gh.addContent(e);
                }                  
            }
            root.addContent(gh);                       
            //sp节点
            Element sp = new Element("sp");
            List<Map<String, Object>> spList = query(spSql, YW, new Object[] { guids[i] });        
            if(spList != null && spList.size() > 0){
                for(int j=0;j<spList.size();j++){
                    Map<String,Object> spMap = spList.get(j);
                    spMap.put("id", j+"");
                    Element e = new Element("num"+j);
                    for(int k=0;k<spElements.length;k++){
                        Element ee = new Element(spElements[k].toUpperCase());
                        ee.setText((String)spMap.get(spElements[k])); 
                        e.addContent(ee);
                     }   
                    sp.addContent(e);
                }                  
            }
            root.addContent(sp);               
            //gd节点
            Element gd = new Element("gd");
            List<Map<String, Object>> gdList = query(gdSql, YW, new Object[] { guids[i] });        
            if(gdList != null && gdList.size() > 0){
                for(int j=0;j<gdList.size();j++){
                    Map<String,Object> gdMap = gdList.get(j);
                    gdMap.put("id", j+"");
                    Element e = new Element("num"+j);
                    for(int k=0;k<gdElements.length;k++){
                        Element ee = new Element(gdElements[k].toUpperCase());
                        ee.setText((String)gdMap.get(gdElements[k])); 
                        e.addContent(ee);
                     }   
                    gd.addContent(e);
                }                  
            }
            root.addContent(gd);                  
            //zb节点
            Element zb = new Element("zb");
            baseMap.put("id", i+"");
            for(int j=0;j<zbElements.length;j++){
                Element e = new Element(zbElements[j].toUpperCase());
                e.setText((String)baseMap.get(zbElements[j])); 
                zb.addContent(e);                
            }
            root.addContent(zb);
            //pad节点
            Element pad = new Element("pad");
            pad.setText((String)baseMap.get("padid"));
            root.addContent(pad);
            //shi节点
            Element shi = new Element("shi");
            shi.setText((String)baseMap.get("shi"));
            root.addContent(shi);              
            //xian节点
            Element xian = new Element("xian");
            xian.setText((String)baseMap.get("xian"));
            root.addContent(xian);            
            try {
                Format format = Format.getPrettyFormat(); 
                XMLOutputter XMLOut = new XMLOutputter(format); 
                XMLOut.output(Doc, new FileOutputStream(folder + "//" + guids[i] + ".xml"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }   
            //生成对应的巡查日志xml文件
            generateXCRZXML(folder,guids[i]);
            //附件下载
            downloadByGuid(guids[i], folder + "//");   
            //改变状态
            changeStatus(guids[i]); 
        }
        
    }
    
    /**
     * 
     * <br>Description:生成对应的巡查日志xml文件
     * <br>Author:王雷
     * <br>Date:2013-9-24
     * @param folder
     * @param yw_guid
     */
    private void generateXCRZXML(String folder,String yw_guid){        
        String sql = "select x.yw_guid,x.xcbh,x.xcdw,x.xcrq,x.xcqy,x.xcry,x.xclx,x.sfywf,x.clyj,x.bz,x.writerxzqh,x.userid,to_char(x.writedate) writedate,x.spqk,x.allnum from xcrz x where x.yw_guid=(select t.yw_guid from xcrz_cg t where t.ywguid=?)";
        String[] elements = {"yw_guid","xcbh","xcdw","xcrq","xcqy","xcry","xclx","sfywf","clyj","bz","writerxzqh","userid","writedate","spqk","allnum"};
        List<Map<String,Object>> list = query(sql,YW,new Object[]{yw_guid});
        if(list.size()>0){
            Map<String,Object> map = list.get(0);
            Element root = new Element("XCRZ");    
            Document Doc = new Document(root); 
            //base节点
            Element base = new Element("base");             
            for(int i=0;i<elements.length;i++){
               Element e = new Element(elements[i].toUpperCase());
               e.setText((String)map.get(elements[i])); 
               base.addContent(e);
            }            
            root.addContent(base);
            try {
                Format format = Format.getPrettyFormat(); 
                XMLOutputter XMLOut = new XMLOutputter(format); 
                XMLOut.output(Doc, new FileOutputStream(folder + "//" + map.get("yw_guid") + ".xml"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }              
            
        }
        
    }
    
    
    /**
     * 
     * <br>Description:改变导出状态：由未导出改为已导出
     * <br>Author:王雷
     * <br>Date:2013-9-24
     * @param yw_guid
     */
    private void changeStatus(String yw_guid){
        String sql = "update dc_ydqkdcb t set t.isexp='1' where t.yw_guid=?";
        update(sql,YW,new Object[]{yw_guid});
    }
    
    
    /**
     * 
     * <br>Description:根据guid 下载附件
     * <br>Author:陈强峰
     * <br>Date:2012-8-13
     * @param guid
     * @param ftpmap
     * @param folder
     */
    private void downloadByGuid(String guid, String folder) {
        String sql = "select file_id,file_name,file_path from atta_accessory where yw_guid=?";
        List<Map<String, Object>> list = query(sql, CORE, new Object[] { guid });
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> accessory = list.get(i);
            String remoteFileName = accessory.get("file_path").toString();
            String file_name = accessory.get("file_name").toString();
            try {
                UtilFactory.getFtpUtil().downloadFile(remoteFileName, folder + "//" + file_name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 
     * <br>Description:成果压缩
     * <br>Author:陈强峰
     * <br>Date:2012-8-13
     * @param zipPath
     * @param path
     */
    private void resultToZip(String zipPath, String path) {
        UtilFactory.getZIPUtil().zip(zipPath + ".zip", path);
    }

    /**
     * 
     * <br>
     * Description:获取临时路径 <br>
     * Author:陈强峰 <br>
     * Date:2012-2-24
     * 
     * @return
     */
    private String getTemp() {
        String temppath = "";
        String sql = "select t.child_name from public_code t where t.id='TEMPFILEPATH' and t.in_flag='1'";
        List<Map<String, Object>> list = query(sql, YW);
        if (list.size() > 0) {
            temppath = list.get(0).get("child_name").toString();
        }
        return temppath;
    }
}
