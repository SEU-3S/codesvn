package com.klspta.web.sanya.lacc;

import java.util.List;
import java.util.Map;
import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

/**
 * 
 * <br>Title:立案查处管理类
 * <br>Description:处理立案查处相关信息
 * <br>Author:陈强峰
 * <br>Date:2013-6-21
 */
public class LaccManager extends AbstractBaseBean {
    /**
     * <br>
     * Description:获取立案查处待办案件 <br>
     * Author:赵伟 <br>
     * Date:2012-9-7
     */

    public void getProcessList() {
        // 获取参数
        String keyWord = request.getParameter("keyWord");
        String fullName = UtilFactory.getStrUtil().unescape(request.getParameter("fullName"));
        String sql = "select t.yw_guid,t.bh as ajbh ,t.ay,t.dwmc,t.ajly,t.grxm ,to_char(t.slrq,'yyyy-MM-dd') as slrq,j.activity_name_ as bazt,j.wfInsId,to_char(j.create_ ,'yyyy-MM-dd') as jssj,j.wfinsid from lacpb t join workflow.v_active_task j on t.yw_guid=j.yw_guid where j.assignee_=?";
        if (keyWord != null) {
            keyWord = UtilFactory.getStrUtil().unescape(keyWord);
            sql += " and (upper(t.bh)||upper(t.ay)||upper(t.ajly)||upper(t.grxm)||upper(t.slrq)||upper(j.create_)||upper(j.activity_name_) like '%"
                    + keyWord + "%')";
        }
        sql += " order by j.create_ desc";
        List<Map<String, Object>> result = query(sql, YW, new String[] { fullName });

        // 调整数据格式
        int i = 0;
        for (Map<String, Object> map : result) {
            if (map.get("dwmc") == null) {
                map.put("DSR", map.get("grxm"));
            } else {
                map.put("DSR", map.get("dwmc"));
            }         
            map.put("INDEX", i++);
        }
        response(result);
    }
        
    /**
     * <br>
     * Description:获取案件查询待办案件列表 <br>
     * Author:赵伟 <br>
     * Date:2012-9-8
     * 
     * @throws Exception
     */
    public void getajglProcessList() throws Exception {
        // 获取参数
        String keyWord = request.getParameter("keyWord");
        // 获取数据
        String sql = "select t.yw_guid,t.bh as ajbh,t.qy ,t.ay,t.dwmc,t.ajly,t.grxm ,to_char(t.slrq,'yyyy-MM-dd') as slrq,j.activity_name_ as bazt,to_char(j.create_ ,'yyyy-MM-dd') as jssj,j.assignee_,j.wfinsid from lacpb t join workflow.v_active_task j on t.yw_guid=j.yw_guid";
        if (keyWord != null) {
            keyWord = UtilFactory.getStrUtil().unescape(keyWord);
            sql += " and (upper(t.bh)||upper(t.qy)||upper(t.ay)||upper(t.ajly)||upper(j.assignee_)||upper(t.slrq)||upper(j.create_)||upper(j.activity_name_) like '%"
                    + keyWord + "%')";
        }
        sql += " order by j.create_ desc";
        List<Map<String, Object>> result = null;
        result = query(sql, YW);

        // 调整数据格式
        int i = 0;
        for (Map<String, Object> map : result) {
            if (map.get("dwmc") == null) {
                map.put("DSR", map.get("grxm"));
            } else {
                map.put("DSR", map.get("dwmc"));
            }
            map.put("INDEX", i++);
        }
        response(result);
    }

    /**
     * <br>
     * Description:获取当前登陆人员已办案件 <br>
     * Author:赵伟 <br>
     * Date:2012-9-7
     * 
     * @throws Exception
     */
    public void getCompleteList() throws Exception {
        // 获取参数
        String keyWord = request.getParameter("keyWord");
        String fullName = UtilFactory.getStrUtil().unescape(request.getParameter("fullName"));
        // 获取数据
        String sql = "select t.yw_guid,t.bh as ajbh ,t.qy,t.ay,t.dwmc,t.ajly,t.grxm ,to_char(t.slrq,'yyyy-MM-dd') as slrq,a.wfInsId,to_char(a.end_,'yyyy-MM-dd') as yjsj from zfjc.lacpb t join "  
                    +" (select distinct(select t1.value_ from workflow.JBPM4_HIST_PROCINST t,workflow.JBPM4_HIST_VAR t1 where t.state_='ended' and t.id_=t1.procinstid_ and t1.varname_='receiveid') yw_guid,"
                    +" (select t1.value_ owner from workflow.JBPM4_HIST_PROCINST t,workflow.JBPM4_HIST_VAR t1 where t.state_='ended' and t.id_=t1.procinstid_ and t1.varname_='owner') owner,"
                    +" t.id_ wfinsid,t.procdefid_,t.start_,t.end_,t.duration_,t.endactivity_ from workflow.JBPM4_HIST_PROCINST t,workflow.JBPM4_HIST_VAR t1 where t.state_='ended' and t.id_=t1.procinstid_)  a  on t.yw_guid=a.yw_guid and a.owner=?";
        if (keyWord != null) {
            keyWord = UtilFactory.getStrUtil().unescape(keyWord);
            sql += " and (upper(t.bh)||upper(t.qy)||upper(t.ay)||upper(t.ajly)||upper(t.grxm)||upper(t.slrq)||upper(a.end_) like '%"
                    + keyWord + "%')";
        }
        sql += " order by t.slrq desc";
        List<Map<String, Object>> result = query(sql, YW, new String[] { fullName });

        // 调整数据格式
        int i = 0;
        for (Map<String, Object> map : result) {
            if (map.get("dwmc") == null) {
                map.put("DSR", map.get("grxm"));
            } else {
                map.put("DSR", map.get("dwmc"));
            }
            map.put("END_", map.get("yjsj"));
            map.put("SLRQ", map.get("slrq"));
            map.put("INDEX", i++);
        }
        response(result);
    }

    /**
     * <br>
     * Description:获取立案查处，案件查询已办结案件 <br>
     * Author:赵伟 <br>
     * Date:2012-9-25
     */
    public void getajdcCompleteList() {
        // 获取参数
        String keyWord = request.getParameter("keyWord");

        // 获取数据
        String sql = "select t.yw_guid,t.bh as ajbh ,t.qy ,t.ay,t.dwmc,t.ajly,t.grxm ,to_char(t.slrq,'yyyy-MM-dd') as slrq,j.wfInsId,to_char(j.end_,'yyyy-MM-dd') as yjsj,j.wfinsid from lacpb t join workflow.v_end_wfins j on t.yw_guid=j.yw_guid";
        if (keyWord != null) {
            keyWord = UtilFactory.getStrUtil().unescape(keyWord);
            sql += " and (upper(t.bh)||upper(t.qy)||upper(t.ay)||upper(t.ajly)||upper(t.grxm)||upper(t.slrq)||upper(j.end_) like '%"
                    + keyWord + "%')";
        }
        sql += " order by t.slrq desc";
        List<Map<String, Object>> result = null;
        result = query(sql, YW);

        // 调整数据格式
        int i = 0;
        for (Map<String, Object> map : result) {
            if (map.get("dwmc") == null) {
                map.put("DSR", map.get("grxm"));
            } else {
                map.put("DSR", map.get("dwmc"));
            }
            map.put("END_", map.get("yjsj"));
            map.put("SLRQ", map.get("slrq"));
            map.put("INDEX", i++);
        }
        response(result);
    }
    /**
     * 
     * <br>Description:保存立案呈批表的案由等信息到结案呈批表中
     * <br>Author:王雷
     * <br>Date:2013-6-21
     */
    public void saveBhAy() {
        String yw_guid = request.getParameter("yw_guid");
        
        String sql="update jacpb j set (j.bh,j.ay,j.lasj,j.dwmc,j.fddbr,j.dwdz,j.dwdh,j.grxm,j.grxb,j.grnl,j.grdw,j.grzw,j.grdz,j.grdh,j.ajly,j.zywfss)= "
                  +" (select t.bh,t.ay,t.zgldqmrq,t.dwmc,t.fddbr,t.dwdz,t.dwdh,t.grxm,t.grxb,t.grnl,t.grdw,t.grzw,t.grdz,t.grdh,t.ajly,t.zywfss from lacpb t where t.yw_guid=j.yw_guid and t.yw_guid=?)"
                  +" where j.yw_guid=?";  
        int i=update(sql,YW,new Object[]{yw_guid,yw_guid});
        if(i==1){
            response("true");
        }else{
            response("false");
        }
       
    }

    /**
     * 
     * <br>Description:获取指定表中相同guid的个数（法律文书呈批表）
     * <br>Author:陈强峰
     * <br>Date:2013-6-20
     * @param yw_guid
     * @param tableName
     * @return
     */
    public int getNum(String yw_guid, String tableName) {
        String sql = "select count(*) num from " + tableName + " where yw_guid =?";
        return Integer.parseInt(String.valueOf(query(sql, YW, new Object[] { yw_guid }).get(0).get("num")));
    }

}