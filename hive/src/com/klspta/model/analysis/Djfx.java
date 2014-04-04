package com.klspta.model.analysis;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.api.ICoordinateChangeUtil;
import com.klspta.base.util.impl.CoordinateChangeUtil;
import com.klspta.base.wkt.Point;
import com.klspta.base.wkt.Polygon;
import com.klspta.base.wkt.Ring;
import com.klspta.model.CBDReport.bean.TDBean;
import com.klspta.model.CBDReport.bean.TRBean;
import com.klspta.model.CBDReport.dataClass.IDataClass;
import com.klspta.model.analysis.Analysis;

public class Djfx extends AbstractBaseBean implements IDataClass{
    
    DecimalFormat df=new DecimalFormat("0.##");

    @Override
    public Map<String, TRBean> getTRBeans(Object[] obj, TRBean trBean) {
        String points = obj[0].toString();   
        String flag = obj[1].toString();
        String wkt = getWkt(points,flag);
        Map<String, TRBean> trbeans = new LinkedHashMap<String, TRBean>();       
        Analysis analysis = new Analysis();
        //审批
        List<TRBean> spList = getSpTRBean(analysis,wkt);      
        for(int i=0;i<spList.size();i++){
            trbeans.put(i+"1", spList.get(i));
        }
        //供地
        List<TRBean> gdList = getGdTRBean(analysis,wkt);
        for(int i=0;i<gdList.size();i++){
            trbeans.put(i+"2", gdList.get(i));
        }        
        //规划
        List<TRBean> ghList = getGhTRBean(analysis,wkt);
        for(int i=0;i<ghList.size();i++){
            trbeans.put(i+"3", ghList.get(i));
        }          
        //现状
        List<TRBean> xzList = getXzTRBean(analysis,wkt);
        for(int i=0;i<xzList.size();i++){
            trbeans.put(i+"4", xzList.get(i));
        }                  
        return trbeans;
    }
    
    /**
     * 
     * <br>Description:返回wkt格式
     * <br>Author:王雷
     * <br>Date:2013-11-10
     * @param points
     * @param flag
     * @return
     */
    public String getWkt(String points,String flag){
        String[] zbs = null;
        Point p = null;
        Polygon polygon = new Polygon();
        String wkt = "";
        if("0".equals(flag)){
            zbs =points.split(";");
            Ring ring = new Ring();          
            for(int i=0;i<zbs.length;i++){
                String[] ps = (zbs[i]).split(",");
                p =  new Point(ps[0],ps[1]);
                ring.putPoint(p);
            }
            polygon.addRing(ring);
            wkt = polygon.toWKT();  
        }else if("1".equals(flag)){
            zbs =points.split(";");
            Ring ring = new Ring();
            for(int i=0;i<zbs.length;i++){
                String[] ps = (zbs[i]).split(",");
                p =  transfer(new Point(ps[0],ps[1]));
                ring.putPoint(p);
            }
            polygon.addRing(ring);
            wkt = polygon.toWKT();  
        }   
        return wkt;
    }
    /**
     * 
     * <br>Description:将80经纬度坐标转化为80平面坐标
     * <br>Author:王雷
     * <br>Date:2013-11-10
     * @param p
     * @return
     */
    public Point transfer(Point p){
        ICoordinateChangeUtil coor = new CoordinateChangeUtil();
        Point point = coor.changePoint(p, ICoordinateChangeUtil.BL80_TO_PLAIN80);
        return point;
    }
    /**
     * 
     * <br>Description:根据编号获取坐标串
     * <br>Author:王雷
     * <br>Date:2013-11-8
     * @param yw_guid
     * @return
     */
    public String getPoints(String yw_guid){
        String sql = "select t.pmzb from dc_ydqkdcb t where t.yw_guid = ?";
        List<Map<String,Object>> list = query(sql,YW,new Object[]{yw_guid});
        if(list!=null && list.size()>0){
            return (String)list.get(0).get("pmzb");
        }
        return null;
    }
    
    /**
     * 
     * <br>Description:获取审批TRBean
     * <br>Author:王雷
     * <br>Date:2013-11-7
     * @param analysis
     * @param points
     * @return
     */
    private List<TRBean> getSpTRBean(Analysis analysis,String wkt){
        List<TRBean> list = new ArrayList<TRBean>();
        
        TRBean trb1 = new TRBean();
        trb1.setCssStyle("title");
        TDBean tdb11 = new TDBean("审批情况", "600", "20");
        tdb11.setColspan("4");
        trb1.addTDBean(tdb11);     
        list.add(trb1);
        
        TRBean trb2 = new TRBean();
        trb2.setCssStyle("trtotal");
        TDBean tdb21 = new TDBean("压盖审批总面积", "300", "20");
        tdb21.setColspan("2");
        trb2.addTDBean(tdb21);
        list.add(trb2);       
        
        TRBean trb3 = new TRBean();
        trb3.setCssStyle("trtotal");
        TDBean tdb31 = new TDBean("项目名称", "150", "20");
        trb3.addTDBean(tdb31);
        TDBean tdb32 = new TDBean("批复文号", "150", "20");
        trb3.addTDBean(tdb32);
        TDBean tdb33 = new TDBean("审批时间", "150", "20");
        trb3.addTDBean(tdb33);
        TDBean tdb34 = new TDBean("审批面积", "150", "20");
        trb3.addTDBean(tdb34);       
        list.add(trb3);         
        
        
        List<Map<String,Object>> spList = analysis.analysis("dlgzspr", "XMMC,PZWH,PZSJ", wkt);
        double mianji = 0;
        double temp = 0;
        if(spList!=null && spList.size()>0){
            for(int i=0;i<spList.size();i++){         
                TRBean trb4 = new TRBean();
                trb4.setCssStyle("trsingle");
                Map<String,Object> spMap = spList.get(i);
                double spmj = 0;
                String area = "&nbsp;";
                if(spMap.get("area")!=null){
                    spmj = Double.parseDouble(spMap.get("area").toString());
                    area = String.valueOf(df.format(spmj*0.0015))+"亩";
                }
                TDBean tdb41 = new TDBean(spMap.get("xmmc")==null?"&nbsp;":spMap.get("xmmc").toString(), "150", "20");
                trb4.addTDBean(tdb41);
                TDBean tdb42 = new TDBean(spMap.get("pzwh")==null?"&nbsp;":spMap.get("pzwh").toString(), "150", "20");
                trb4.addTDBean(tdb42);
                TDBean tdb43 = new TDBean(spMap.get("pzsj")==null?"&nbsp;":spMap.get("pzsj").toString(), "150", "20");
                trb4.addTDBean(tdb43);
                TDBean tdb44 = new TDBean(area, "150", "20");
                trb4.addTDBean(tdb44);
                list.add(trb4); 
                if(spMap.get("area")==null){
                    temp = 0;
                }else{
                    temp = Double.parseDouble(spMap.get("area").toString());
                }
                mianji += temp;
            }          
        }else{
            TRBean trb4 = new TRBean();
            trb4.setCssStyle("trsingle");
            TDBean tdb41 = new TDBean("&nbsp;", "150", "20");
            trb4.addTDBean(tdb41);
            TDBean tdb42 = new TDBean("&nbsp;", "150", "20");
            trb4.addTDBean(tdb42);
            TDBean tdb43 = new TDBean("&nbsp;", "150", "20");
            trb4.addTDBean(tdb43);
            TDBean tdb44 = new TDBean("&nbsp;", "150", "20");
            trb4.addTDBean(tdb44);               
            list.add(trb4); 
        }
        String tdb22Value = "&nbsp;";
        if(mianji!=0){
            tdb22Value = String.valueOf(df.format(mianji*0.0015))+"亩";
        }   
        TDBean tdb22 = new TDBean(tdb22Value, "300", "20");
        tdb22.setColspan("2");
        trb2.addTDBean(tdb22);
        
        return list;
    }

    /**
     * 
     * <br>Description:获取供地TRBean
     * <br>Author:王雷
     * <br>Date:2013-11-7
     * @param analysis
     * @param points
     * @return
     */
    private List<TRBean> getGdTRBean(Analysis analysis,String wkt){
        List<TRBean> list = new ArrayList<TRBean>();
        
        TRBean trb1 = new TRBean();
        trb1.setCssStyle("title");
        TDBean tdb11 = new TDBean("供地情况", "600", "20");
        tdb11.setColspan("4");
        trb1.addTDBean(tdb11);      
        list.add(trb1);
        
        TRBean trb2 = new TRBean();
        trb2.setCssStyle("trtotal");
        TDBean tdb21 = new TDBean("压盖供地总面积", "300", "20");
        tdb21.setColspan("2");
        trb2.addTDBean(tdb21);     
        list.add(trb2);        
        
        TRBean trb3 = new TRBean();
        trb3.setCssStyle("trtotal");
        TDBean tdb31 = new TDBean("项目名称", "150", "20");
        trb3.addTDBean(tdb31);
        TDBean tdb32 = new TDBean("批复文号", "150", "20");
        trb3.addTDBean(tdb32);
        TDBean tdb33 = new TDBean("供地时间", "150", "20");
        trb3.addTDBean(tdb33);
        TDBean tdb34 = new TDBean("供地面积", "150", "20");
        trb3.addTDBean(tdb34);       
        list.add(trb3);         
        
        
        List<Map<String,Object>> gdList = analysis.analysis("dlgzgdr", "XMMC,SZFPW,PZRQ", wkt);
        double mianji = 0;
        double temp = 0;
        if(gdList!=null && gdList.size()>0){
            for(int i=0;i<gdList.size();i++){      
                TRBean trb4 = new TRBean();
                trb4.setCssStyle("trsingle");
                Map<String,Object> gdMap = gdList.get(i);
                double gdmj = 0;
                String area = "&nbsp;";
                if(gdMap.get("area")!=null){
                    gdmj = Double.parseDouble(gdMap.get("area").toString());
                    area = String.valueOf(df.format(gdmj*0.0015))+"亩";
                }
                TDBean tdb41 = new TDBean(gdMap.get("xmmc")==null?"&nbsp;":gdMap.get("xmmc").toString(), "150", "20");
                trb4.addTDBean(tdb41);
                TDBean tdb42 = new TDBean(gdMap.get("szfpw")==null?"&nbsp;":gdMap.get("szfpw").toString(), "150", "20");
                trb4.addTDBean(tdb42);
                TDBean tdb43 = new TDBean(gdMap.get("pzrq")==null?"&nbsp;":gdMap.get("pzrq").toString(), "150", "20");
                trb4.addTDBean(tdb43);
                TDBean tdb44 = new TDBean(area, "150", "20");
                trb4.addTDBean(tdb44);
                list.add(trb4);
                if(gdMap.get("area")==null){
                    temp = 0;
                }else{
                    temp = Double.parseDouble(gdMap.get("area").toString());
                }
                mianji += temp;
            } 
        }else{
            TRBean trb4 = new TRBean();
            trb4.setCssStyle("trsingle");
            TDBean tdb41 = new TDBean("&nbsp;", "150", "20");
            trb4.addTDBean(tdb41);
            TDBean tdb42 = new TDBean("&nbsp;", "150", "20");
            trb4.addTDBean(tdb42);
            TDBean tdb43 = new TDBean("&nbsp;", "150", "20");
            trb4.addTDBean(tdb43);
            TDBean tdb44 = new TDBean("&nbsp;", "150", "20");
            trb4.addTDBean(tdb44);               
            list.add(trb4); 
        }
        String tdb22Value = "&nbsp;";
        if(mianji!=0){
            tdb22Value = String.valueOf(df.format(mianji*0.0015))+"亩";
        }   
        TDBean tdb22 = new TDBean(tdb22Value, "300", "20");
        tdb22.setColspan("2");
        trb2.addTDBean(tdb22);
                
        return list;
        
    }
    
    /**
     * 
     * <br>Description:获取规划TRBean
     * <br>Author:王雷
     * <br>Date:2013-11-7
     * @param analysis
     * @param points
     * @return
     */
    private List<TRBean> getGhTRBean(Analysis analysis,String wkt){
        List<TRBean> list = new ArrayList<TRBean>();
        
        TRBean trb1 = new TRBean();
        trb1.setCssStyle("title");
        TDBean tdb11 = new TDBean("规划情况", "600", "20");
        tdb11.setColspan("4");
        trb1.addTDBean(tdb11);      
        list.add(trb1);
        
        TRBean trb2 = new TRBean();
        trb2.setCssStyle("trtotal");
        TDBean tdb21 = new TDBean("符合规划", "150", "20");
        trb2.addTDBean(tdb21);
        TDBean tdb22 = new TDBean("不符合规划", "150", "20");
        trb2.addTDBean(tdb22);        
        TDBean tdb23 = new TDBean("基本农田用地", "150", "20");
        trb2.addTDBean(tdb23);
        TDBean tdb24 = new TDBean("&nbsp;", "150", "20");
        trb2.addTDBean(tdb24);       
        list.add(trb2);          
        
        
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
        String tdb31Value = "&nbsp;";
        String tdb32Value = "&nbsp;";
        String tdb33Value = "&nbsp;";
        if(fhghmj!=0){
            tdb31Value = String.valueOf(df.format(fhghmj*0.0015))+"亩";
        }
        if(bfhghmj!=0){
            tdb32Value = String.valueOf(df.format(bfhghmj*0.0015))+"亩";
        }
        if(zyjbntmj!=0){
            tdb33Value = String.valueOf(df.format(zyjbntmj*0.0015))+"亩";
        }
        TRBean trb3 = new TRBean();
        trb3.setCssStyle("trsingle");
        TDBean tdb31 = new TDBean(tdb31Value, "150", "20");
        trb3.addTDBean(tdb31);
        TDBean tdb32 = new TDBean(tdb32Value, "150", "20");
        trb3.addTDBean(tdb32);
        TDBean tdb33 = new TDBean(tdb33Value, "150", "20");
        trb3.addTDBean(tdb33);
        TDBean tdb34 = new TDBean("&nbsp;", "150", "20");
        trb3.addTDBean(tdb34);       
        list.add(trb3);           
        return list;
        
    }
    /**
     * 
     * <br>Description:获取现状TRBean
     * <br>Author:王雷
     * <br>Date:2013-11-7
     * @param analysis
     * @param points
     * @return
     */
    private List<TRBean> getXzTRBean(Analysis analysis,String wkt){
        List<TRBean> list = new ArrayList<TRBean>();
        TRBean trb1 = new TRBean();
        trb1.setCssStyle("title");
        TDBean tdb11 = new TDBean("现状情况", "600", "20");
        tdb11.setColspan("4");
        trb1.addTDBean(tdb11);      
        list.add(trb1);
        
        TRBean trb2 = new TRBean();
        trb2.setCssStyle("trtotal");
        TDBean tdb21 = new TDBean("农用地", "150", "20");
        trb2.addTDBean(tdb21);
        TDBean tdb22 = new TDBean("其中耕地", "150", "20");
        trb2.addTDBean(tdb22);        
        TDBean tdb23 = new TDBean("建设用地", "150", "20");
        trb2.addTDBean(tdb23);
        TDBean tdb24 = new TDBean("未利用地", "150", "20");
        trb2.addTDBean(tdb24);       
        list.add(trb2);         
        
        
        List<Map<String,Object>> xzList = analysis.analysis("dlgzxzr", "TBBH,QSDWMC,DLBM,DLMC", wkt);
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
        String tdb31Value = "&nbsp;";
        String tdb32Value = "&nbsp;";
        String tdb33Value = "&nbsp;";
        String tdb34Value = "&nbsp;";
        if(nydmj!=0.){
            tdb31Value = String.valueOf(df.format(nydmj*0.0015))+"亩";
        }
        if(gengdmj!=0){
            tdb32Value = String.valueOf(df.format(gengdmj*0.0015))+"亩";
        }
        if(jsydmj!=0){
            tdb33Value = String.valueOf(df.format(jsydmj*0.0015))+"亩";
        }        
        if(wlydmj!=0){
            tdb33Value = String.valueOf(df.format(wlydmj*0.0015))+"亩";
        }          
        
        TRBean trb3 = new TRBean();
        trb3.setCssStyle("trsingle");
        TDBean tdb31 = new TDBean(tdb31Value, "150", "20");
        trb3.addTDBean(tdb31);
        TDBean tdb32 = new TDBean(tdb32Value, "150", "20");
        trb3.addTDBean(tdb32);
        TDBean tdb33 = new TDBean(tdb33Value, "150", "20");
        trb3.addTDBean(tdb33);
        TDBean tdb34 = new TDBean(tdb34Value, "150", "20");
        trb3.addTDBean(tdb34);       
        list.add(trb3);           
        
        TRBean trb4 = new TRBean();
        trb4.setCssStyle("trtotal");
        TDBean tdb41 = new TDBean("编号", "150", "20");
        trb4.addTDBean(tdb41);
        TDBean tdb42 = new TDBean("权属单位", "150", "20");
        trb4.addTDBean(tdb42);
        TDBean tdb43 = new TDBean("地类名称", "150", "20");
        trb4.addTDBean(tdb43);
        TDBean tdb44 = new TDBean("面积", "150", "20");
        trb4.addTDBean(tdb44);               
        list.add(trb4);     
        
        if(xzList!=null && xzList.size()>0){
            for(int i=0;i<xzList.size();i++){
                Map<String,Object> xzMap = xzList.get(i);
                double xzdlmj = 0;
                String area = "&nbsp;";
                if(xzMap.get("area")!=null){
                    xzdlmj = Double.parseDouble(xzMap.get("area").toString());
                    area = String.valueOf(df.format(xzdlmj*0.0015))+"亩";
                }
                TRBean trb5 = new TRBean();
                trb5.setCssStyle("trsingle");
                TDBean tdb51 = new TDBean(xzMap.get("tbbh")==null?"&nbsp;":xzMap.get("tbbh").toString(), "150", "20");
                trb5.addTDBean(tdb51);
                TDBean tdb52 = new TDBean(xzMap.get("qsdwmc")==null?"&nbsp;":xzMap.get("qsdwmc").toString(), "150", "20");
                trb5.addTDBean(tdb52);
                TDBean tdb53 = new TDBean(xzMap.get("dlmc")==null?"&nbsp;":xzMap.get("dlmc").toString(), "150", "20");
                trb5.addTDBean(tdb53);
                TDBean tdb54 = new TDBean(area, "150", "20");
                trb5.addTDBean(tdb54);               
                list.add(trb5);            
            }
        }else{
            TRBean trb5 = new TRBean();
            trb5.setCssStyle("trsingle");
            TDBean tdb51 = new TDBean("&nbsp;", "150", "20");
            trb5.addTDBean(tdb51);
            TDBean tdb52 = new TDBean("&nbsp;", "150", "20");
            trb5.addTDBean(tdb52);
            TDBean tdb53 = new TDBean("&nbsp;", "150", "20");
            trb5.addTDBean(tdb53);
            TDBean tdb54 = new TDBean("&nbsp;", "150", "20");
            trb5.addTDBean(tdb54);               
            list.add(trb5);   
            
        }
        return list;
        
    }
    
}
