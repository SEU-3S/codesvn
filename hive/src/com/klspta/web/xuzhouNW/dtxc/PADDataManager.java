﻿package com.klspta.web.xuzhouNW.dtxc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.klspta.base.AbstractBaseBean;

import com.klspta.base.util.UtilFactory;
import com.klspta.base.wkt.Polygon;
import com.klspta.console.ManagerFactory;
import com.klspta.model.projectinfo.ProjectInfo;
import com.klspta.web.xuzhouNW.wpzf.WpzfHandler;

/**
 * 
 * <br>
 * Title:Pad功能类 <br>
 * Description:对数据库中PAD表操作 <br>
 * Author:陈强峰 <br>
 * Date:2011-7-22
 */
public class PADDataManager extends AbstractBaseBean {
	
    /**
     * 
     * <br>Description:获取外业成果列表
     * <br>Author:陈强峰
     * <br>Date:2013-6-19
     */
    public void getQueryData() {
        String userid = request.getParameter("userId");
        String keyword = request.getParameter("keyWord");
        String status = request.getParameter("status");
        String likeCondition = " 1=1";
        String xzqh;
        try {
            xzqh = ManagerFactory.getUserManager().getUserWithId(userid).getXzqh();
            String xzqbm = UtilFactory.getXzqhUtil().getBeanById(xzqh).getCatoncode();
            if(xzqbm.endsWith("00")){
                xzqbm=xzqbm.substring(0,xzqbm.lastIndexOf("00"));
            }
            likeCondition = " t.impxzqbm like '" + xzqbm + "%'";
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sql = "select t.readflag,t.guid,t.xzqmc,t.xmmc,t.rwlx,t.sfwf,t.xcr,t.xcrq,t.cjzb,t.jwzb,t.imgname from v_pad_data_xml t where "
                + likeCondition;
        if (keyword != null) {
            keyword = UtilFactory.getStrUtil().unescape(keyword);
            sql = "select t.readflag,t.guid,t.xzqmc,t.xmmc,t.rwlx,t.sfwf,t.xcr,t.xcrq,t.cjzb,t.jwzb,t.imgname from v_pad_data_xml t where (upper(guid)||upper(xzqmc)||upper(xmmc)||upper(rwlx)||upper(sfwf)||upper(xcr)||upper(xcrq) like '%"
                    + keyword + "%') and" + likeCondition;
        }
        sql += " and t.islian = '" + status + "'";
        List<Map<String, Object>> query = query(sql, YW);
        for (int i = 0; i < query.size(); i++) {
            query.get(i).put("XIANGXI", i);
            query.get(i).put("DELETE", i);
        }
        response(query);
    }

    /**
     * 
     * <br>Description: 删除指定的编号的成果信息
     * <br>Author:姚建林
     * <br>Date:2012-11-19
     * @return
     */
    public String delData() {
        String guid = request.getParameter("yw_guid");
        if (guid != null) {
            String sql = "delete from dc_ydqkdcb where yw_guid='" + guid + "'";
            update(sql, YW);
            sql = "select file_id,file_path from atta_accessory where yw_guid=? ";
            List<Map<String, Object>> list = query(sql, CORE, new Object[] { guid });
            for (int i = 0; i < list.size(); i++) {
                String ftpFileName = list.get(i).get("file_path").toString();
                String fileId = list.get(i).get("file_id").toString();
                UtilFactory.getFtpUtil().deleteFile(ftpFileName);
                sql = "delete from atta_accessory where file_id=?";
                update(sql, CORE, new Object[] { fileId });
            }
        }
        return null;
    }

    /**
     * 
     * <br>Description:获取指定编号的80坐标wkt串
     * <br>Author:陈强峰
     * <br>Date:2012-8-16
     * @param rwbh
     * @return
     */
    public String getCjzb(String rwbh) {
        String zblx = "pmzb";
        String flag = ProjectInfo.getInstance().getFlag();
        if(flag.toLowerCase().indexOf("ww")!=-1){
            zblx = "jwzb";
        }
        String sql = "select "+zblx+" from dc_ydqkdcb where yw_guid=?";
        List<Map<String, Object>> list = query(sql, YW, new Object[] { rwbh });
        if (list.size() > 0) {
            Object zb = list.get(0).get(zblx);
            if (zb == null) {
                return null;
            } else {
                String allzb = zb.toString();
                String[] zbs = allzb.split(";");
                List<String> listzb = new ArrayList<String>();
                for (int i = 0; i < zbs.length; i++) {
                    listzb.add(zbs[i]);
                }
                if (!listzb.get(0).equals(listzb.get(listzb.size() - 1))) {
                    listzb.add(zbs[0] + "," + zbs[1]);
                }
                sql = "select t.*, t.rowid from gis_extent t where t.flag = '1'";
                List<Map<String, Object>> mapConfigList = query(sql, CORE);
                BigDecimal wkid = (BigDecimal) mapConfigList.get(0).get("wkid");
                Polygon polygon = new Polygon(listzb, wkid.intValue(), true);
                return polygon.toJson();
            }
        } else {
            return null;
        }
    }

    /**
     * 
     * <br>Description:根据yw_guid查询单个成果填写信息
     * <br>Author:陈强峰
     * <br>Date:2013-6-23
     * @param yw_guid
     * @return
     */
    public Map<String, Object> getXckcqkData(String yw_guid) {
        String sql = "select * from dc_ydqkdcb t where t.yw_guid=?";
        List<Map<String, Object>> result = query(sql, YW, new String[] { yw_guid });
        return result.get(0);
    }
    
    /**
     * 
     * <br>Description:将对应任务编号的巡查成果设为立案
     * <br>Author:黎春行
     * <br>Date:2013-9-9
     */
    public void setStatus(){
    	String yw_guid = request.getParameter("yw_guid");
    	String sql = "update dc_ydqkdcb t set t.islian = '1' where t.yw_guid = ?";
    	update(sql, YW, new Object[]{yw_guid});
    }
    
    /**
     * 
     * <br>Description:根据卫片图斑编号获取卫片核查成果
     * <br>Author:黎春行
     * <br>Date:2013-9-15
     * @param yw_guid
     * @return
     */
    public Map<String, Object> getWphcData(String yw_guid){
    	//根据卫片的objectid获取jctb
    	String wpName = WpzfHandler.WP_FORM;
    	String jcbhsql = "select ( xzb ||','|| yzb ) as zb  from "+ wpName+" t where t.objectid=?";
    	List<Map<String, Object>> resultList = query(jcbhsql, GIS, new Object[]{yw_guid});	
    	String zb = resultList.get(0).get("zb").toString();
    	String sql = "select * from dc_ydqkdcb t where t.zb =? and yw_guid like 'WP%'";
    	List<Map<String, Object>> result = query(sql, YW, new Object[]{zb});
    	if(result.size() > 0){
    		return result.get(0);
    	}else{
    		return null;
    	}
    }
    
    /**
     * 
     * <br>Description:判断卫片编号是否有卫片核查成果
     * <br>Author:黎春行
     * <br>Date:2013-9-16
     */
    public void getIsExist(){
    	String objectId = request.getParameter("objectid");
    	Map<String, Object> wphcMap = getWphcData(objectId);
    	if(null == wphcMap){
    		response("no");
    	}else{
    		response("yes");
    	}
    }
}
