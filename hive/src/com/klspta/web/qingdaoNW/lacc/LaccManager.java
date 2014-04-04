package com.klspta.web.qingdaoNW.lacc;



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
        String sql = "select t.yw_guid,t.bh as ajbh ,t.ay,t.dwmc,t.ajly,t.grxm ,to_char(t.slrq,'yyyy-MM-dd hh24:mi:ss') as slrq,j.activity_name_ as bazt,j.wfInsId,to_char(j.create_ ,'yyyy-MM-dd hh24:mi:ss') as jssj,j.wfinsid from lacpb t join workflow.v_active_task j on t.yw_guid=j.yw_guid where j.assignee_=?";
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
        String sql = "select t.yw_guid,t.bh as ajbh,t.qy ,t.ay,t.dwmc,t.ajly,t.grxm ,to_char(t.slrq,'yyyy-MM-dd hh24:mi:ss') as slrq,j.activity_name_ as bazt,to_char(j.create_ ,'yyyy-MM-dd hh24:mi:ss') as jssj,j.assignee_,j.wfinsid from lacpb t join workflow.v_active_task j on t.yw_guid=j.yw_guid";
        if (keyWord != null) {
            keyWord = UtilFactory.getStrUtil().unescape(keyWord);
            sql += " and (upper(t.bh)||upper(t.qy)||upper(t.ay)||upper(t.ajly)||upper(j.assignee_)||upper(t.slrq)||upper(j.create_)||upper(j.activity_name_) like '%"
                    + keyWord + "%')";
        }
        sql += " order by j.create_";
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
        String sql = "select t.yw_guid,t.bh as ajbh ,t.ay,t.dwmc,t.ajly,t.grxm ,to_char(t.slrq,'yyyy-MM-dd hh24:mi:ss') as slrq,j.activityname as bazt,j.wfInsId,to_char(j.create_ ,'yyyy-MM-dd hh24:mi:ss') as jssj,to_char(j.end_,'yyyy-MM-dd hh24:mi:ss') as yjsj,j.wfinsid from lacpb t join workflow.v_hist_task j on t.yw_guid=j.yw_guid where j.assignee_=?";
        if (keyWord != null) {
            keyWord = UtilFactory.getStrUtil().unescape(keyWord);
            sql += " and (upper(t.bh)||upper(t.qy)||upper(t.ay)||upper(t.ajly)||upper(t.grxm)||upper(t.slrq)||upper(j.activityname)||upper(j.end_) like '%"
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
            map.put("CREATE_", map.get("jssj"));
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
        String sql = "select t.yw_guid,t.bh as ajbh ,t.ay,t.dwmc,t.ajly,t.grxm ,to_char(t.slrq,'yyyy-MM-dd hh24:mi:ss') as slrq,j.wfInsId,to_char(j.end_,'yyyy-MM-dd hh24:mi:ss') as yjsj,j.wfinsid from lacpb t join workflow.v_end_wfins j on t.yw_guid=j.yw_guid";
        if (keyWord != null) {
            keyWord = UtilFactory.getStrUtil().unescape(keyWord);
            sql += " and (upper(t.bh)||upper(t.ay)||upper(t.ajly)||upper(t.grxm)||upper(t.slrq)||upper(j.end_) like '%"
                    + keyWord + "%')";
        }
        sql += " order by t.slrq";
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
     * <br>Description:保存立案呈批表的案由到其他表单中
     * <br>Author:王雷
     * <br>Date:2013-6-21
     */
    public void saveBhAy() {
        String yw_guid = request.getParameter("yw_guid");
        String sjfgjzrq = request.getParameter("sjfgjzrq");
        String bh = UtilFactory.getStrUtil().unescape(request.getParameter("bh"));
        String ay = UtilFactory.getStrUtil().unescape(request.getParameter("ay"));

        String sql = "update cljdcpb set ay='" + ay + "', bh='" + bh + "',lasj=to_date('" + sjfgjzrq
                + "', 'YYYY-MM-DD HH24:MI:SS') where yw_guid = ?";
        update(sql, YW, new Object[] { yw_guid });

        String sql2 = "update jacpb set ay=?, bh=? ,lasj=to_date('" + sjfgjzrq
                + "', 'YYYY-MM-DD HH24:MI:SS') where yw_guid = ?";
        update(sql2, YW, new Object[] { ay, bh, yw_guid });

        String sql3 = "update flwscpb set ay=? where yw_guid = ?";
        update(sql3, YW, new Object[] { ay, yw_guid });
        response("true");
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