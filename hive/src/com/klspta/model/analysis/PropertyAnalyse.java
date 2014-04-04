package com.klspta.model.analysis;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.wkt.Point;

public class PropertyAnalyse extends AbstractBaseBean {
    DecimalFormat df=new DecimalFormat("0.##"); 
    public void showInfo(){
        String zb = request.getParameter("point");
        String x = zb.split(",")[0];
        String y = zb.split(",")[1];
        Point p = new Point(x,y);
        //ICoordinateChangeUtil coor = new CoordinateChangeUtil();
        //Point point = coor.changePoint(p, ICoordinateChangeUtil.BL80_TO_PLAIN80);     
        String wkt = p.toWKT();
        StringBuffer ms = new StringBuffer("<table cellpadding='0' cellspacing='0'>");        
        String sql = "select t.shape.srid srid from dlgzspr t where rownum =1 ";
        List<Map<String, Object>> ls = query(sql,GIS);
        String srid = "";
        if(ls.size()>0){
            srid = (ls.get(0)).get("srid").toString();
        }                
        sql = "select * from dlgzspr t where sde.st_contains (t.shape,sde.st_geometry(?,?))=1";       
        ls = query(sql,GIS,new Object[]{wkt,srid});  
        ms.append("<tr><td>审批情况：</td>");
        if (ls.size() > 0) {
            Map<String, Object> map = ls.get(0);
            ms.append("<td><SPAN style='COLOR:green'>已审批:").append(map.get("xmmc")).append("</SPAN></td></tr>");
        } else {
            ms.append("<td><SPAN style='COLOR:red'>未审批</SPAN>").append("</td></tr>");
        }       
        sql = "select * from dlgzgdr t where sde.st_contains (t.shape,sde.st_geometry(?,?))=1";       
        ls = query(sql,GIS,new Object[]{wkt,srid});
        ms.append("<tr><td>供地情况：</td>");
        if (ls.size() > 0) {
            Map<String, Object> map = ls.get(0);
            ms.append("<td><SPAN style='COLOR:green'>已供地:").append(map.get("xmmc")).append("</SPAN></td></tr>");
        } else {
            ms.append("<td><SPAN style='COLOR:red'>未供地</SPAN>").append("</td></tr>");
        }               
        sql = "select * from dlgztdytqr t where sde.st_contains (t.shape,sde.st_geometry(?,?))=1 and t.tdytqlxdm ='010'";       
        ls = query(sql,GIS,new Object[]{wkt,srid});    
        ms.append("<tr><td>规划情况：</td>");
        if (ls.size() > 0) {
            ms.append("<td><SPAN style='COLOR:red'>占用基本农田</SPAN>").append("</td></tr>");
        } else {
            ms.append("<td><SPAN style='COLOR:green'>未占用基本农田</SPAN>").append("</td></tr>");
        }             
        sql = "select t.tbbh from dlgzyswftb_zfjc_2012r t where sde.st_contains (t.shape,sde.st_geometry(?,?))=1";
        ls = query(sql,GIS,new Object[]{wkt,srid});
        ms.append("<tr><td>卫片情况：</td>");
        if (ls.size() > 0) {
            Map<String, Object> map = ls.get(0);
            ms.append("<td><SPAN style='COLOR:green'>发现2012年度卫片执法检查").append(
                    "</SPAN></td></tr>");
        }else {
            ms.append("<td><SPAN style='COLOR:green'>年度卫片执法检查未发现</SPAN></td></tr>");
        }
        ms.append("</table>");
        response(ms.toString());    
    }    
    public void fenxi(){
        String points = request.getParameter("points");
        String flag = request.getParameter("flag");
        Djfx djfx = new Djfx();
        String wkt = djfx.getWkt(points,flag);
        Analysis analysis = new Analysis();
        //现状
        List<Map<String,Object>> xzList =analysis.analysis("dlgzxzr", "TBBH,QSDWMC,DLBM,DLMC", wkt);
        double nydmj = 0;
        double gengdmj = 0;
        double jsydmj = 0;
        double wlydmj = 0;
        String nydbm = "011,012,013,021,022,023,031,032,033,041,042,043,104,114,117,123";
        String gengdbm = "011,012,013";
        String jsydbm = "051,052,053,054,061,062,063,071,072,081,082,083,084,085,086,087,088,091,092,093,094,095,101,102,103,105,106,107,113,118,121,201,202,203,204,205";
        String wlydbm = "111,112,115,116,119,122,124,125,126,127";        
        for(int i=0;i<xzList.size();i++){
            Map<String,Object> xzMap = xzList.get(i);
            String dlbm = (String)xzMap.get("dlbm");
            double xzdlmj = 0;
            if(xzMap.get("area")!=null){
                xzdlmj = Double.parseDouble(xzMap.get("area").toString());
            }
            if (nydbm.indexOf(dlbm) >= 0)
            {
                nydmj += xzdlmj;
            }
            if (gengdbm.indexOf(dlbm) >= 0)
            {
                gengdmj += xzdlmj;
            }
            if (jsydbm.indexOf(dlbm) >= 0)
            {
                jsydmj += xzdlmj;
            }
            if (wlydbm.indexOf(dlbm) >= 0)
            {
                wlydmj += xzdlmj;
            }
        }
        String nydmianji = String.valueOf(df.format(nydmj*0.0015))+"亩";
        String gengdmianji = String.valueOf(df.format(gengdmj*0.0015))+"亩";
        String jsydmianji = String.valueOf(df.format(jsydmj*0.0015))+"亩";
        String wlydmianji = String.valueOf(df.format(wlydmj*0.0015))+"亩";
       
        
        StringBuffer xzsb = new StringBuffer("<table border='1' cellpadding='0' cellspacing='0' width='330'  style='text-align:center; vertical-align:middle;font-family: 宋体, Arial; font-size: 12px;border-collapse:collapse;border:1px #000 solid;' >");      
        xzsb.append("<tr><td>农用地面积</td><td>耕地面积</td><td>建设用地面积</td><td>未利用地面积</td></tr>"); 
        xzsb.append("<tr><td>"+nydmianji+"</td><td>"+gengdmianji+"</td><td>"+jsydmianji+"</td><td>"+wlydmianji+"</td></tr>").append("</table>");
        xzsb.append("<br/><table border='1' cellpadding='0' cellspacing='0' width='330'  style='text-align:center; vertical-align:middle;font-family: 宋体, Arial; font-size: 12px;border-collapse:collapse;border:1px #000 solid;' >");
        xzsb.append("<tr><td>编号</td><td>权属单位</td><td>地类名称</td><td>面积</td></tr>");
        for(int i=0;i<xzList.size();i++){
            Map<String,Object> xzMap = xzList.get(i);
            double xzdlmj = 0;
            String area = "&nbsp;";
            if(xzMap.get("area")!=null){
                xzdlmj = Double.parseDouble(xzMap.get("area").toString());
                area = String.valueOf(df.format(xzdlmj*0.0015))+"亩";
            }  
            xzsb.append("<tr><td>").append(xzMap.get("tbbh")==null?"&nbsp;":xzMap.get("tbbh").toString())
            .append("</td><td>").append(xzMap.get("qsdwmc")==null?"&nbsp;":xzMap.get("qsdwmc").toString())
            .append("</td><td>").append(xzMap.get("dlmc")==null?"&nbsp;":xzMap.get("dlmc").toString())
            .append("</td><td>").append(area).append("</td></tr>");
            
        }
        xzsb.append("</table>");        
        
        //规划
        List<Map<String,Object>> ghList = analysis.analysis("dlgztdytqr", "TDYTQLXDM", wkt);
        double fhghmj = 0;
        double bfhghmj = 0;
        double zyjbntmj = 0;
        String fhghdm = "030,040,050";
        String zyjbntdm = "010";        
        
        for(int i=0;i<ghList.size();i++){
            Map<String,Object> ghMap = ghList.get(i);
            String tdytqlxdm = (String)ghMap.get("tdytqlxdm");            
            double ghdlmj = 0;
            if(ghMap.get("area")!=null){
                ghdlmj = Double.parseDouble(ghMap.get("area").toString());              
            }
            if (fhghdm.indexOf(tdytqlxdm) >= 0)
            {
                fhghmj += ghdlmj;
            }
            else
            {
                bfhghmj += ghdlmj;
                if (zyjbntdm.equals(tdytqlxdm))
                {
                    zyjbntmj += ghdlmj;
                }
            }          
        }
        String fhghmianji = String.valueOf(df.format(fhghmj*0.0015))+"亩";
        String bfhghmianji = String.valueOf(df.format(bfhghmj*0.0015))+"亩";
        String zyjbntmianji = String.valueOf(df.format(zyjbntmj*0.0015))+"亩";
        
        StringBuffer ghsb = new StringBuffer("<table border='1' cellpadding='0' cellspacing='0' width='330'  style='text-align:center; vertical-align:middle;font-family: 宋体, Arial; font-size: 12px;border-collapse:collapse;border:1px #000 solid;' >");    
        ghsb.append("<tr><td>符合规划面积</td><td>不符合规划面积</td><td>其中基本农田面积</td></tr>");
        ghsb.append("<tr><td>"+fhghmianji+"</td><td>"+bfhghmianji+"</td><td>"+zyjbntmianji+"</td></tr>").append("</table>");
         
        String msg = (xzsb.append("@").append(ghsb)).toString();
        response(msg);
        
    }    
    
    public void fenxiproanalyse(){
        
        
    }
}
