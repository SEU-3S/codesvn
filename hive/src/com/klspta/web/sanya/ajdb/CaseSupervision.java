package com.klspta.web.sanya.ajdb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

public class CaseSupervision extends AbstractBaseBean {
    /**
     * 
     * <br>Description:获取信访督办列表
     * <br>Author:王雷
     * <br>Date:2013-9-9
     */
    public void getXfdbList() {
        String keyWord = request.getParameter("keyWord");
        String sql = "select t.yw_guid,t.xfsx,t.createdate cjrq,t.xflx,t.blsx,t.blks,t.blzt,t.blqk,t.zhblr from xfajdjb t where t.blzt='未处理'";
        if (keyWord != null) {
            keyWord = UtilFactory.getStrUtil().unescape(keyWord);
            sql += " and upper(t.yw_guid)||upper(t.xfsx)||upper(t.xflx)||upper(t.zhblr)||upper(t.blsx)||upper(t.blks)||upper(t.blzt)||upper(t.blqk) like '%"
                    + keyWord + "%'";
        }

        List<Map<String, Object>> list = query(sql, YW);
        if (list != null && list.size() > 0) {
            for (Map<String, Object> map : list) {
                map.put("YJ", getWorkaDayAmount((String) map.get("blsx")));
                map.put("SYTS", getWorkaDayAmount((String) map.get("blsx")));
            }
            //list = sortBySYTS(list);
            list = setIndex(list);
        }
        response(list);
    }

    /**
     * 
     * <br>Description:获取立案督办列表
     * <br>Author:王雷
     * <br>Date:2013-9-9
     */
    public void getLadbList() {
        String keyWord = request.getParameter("keyWord");
        String sql = "select t.yw_guid,t.bh,t.ay,(case when t.dwmc is not null then t.dwmc when t.grxm is not null then t.grxm end) wfdw,t.ajly,to_char(t.tbrq,'yyyy-mm-dd') tbrq,to_char(t.jzrq,'yyyy-mm-dd') jzrq,t.zywfss,j.activity_name_ blzt,j.wfInsId from lacpb t join workflow.v_active_task j on t.yw_guid=j.yw_guid";
        if (keyWord != null) {
            keyWord = UtilFactory.getStrUtil().unescape(keyWord);
            sql += " where upper(t.yw_guid)||upper(t.bh)||upper(t.ay)||upper(t.dwmc)||upper(t.grxm)||upper(t.ajly)||upper(t.tbrq)||upper(t.zywfss) like '%"
                    + keyWord + "%'";
        }
        List<Map<String, Object>> list = query(sql, YW);
        if (list != null && list.size() > 0) {
            for (Map<String, Object> map : list) {
                if (map.get("jzrq") == null) {
                    map.put("YJ", "365");
                    map.put("SYTS", "?天");
                } else {
                    map.put("YJ", getWorkaDayAmount(map.get("jzrq").toString()));
                    map.put("SYTS", getWorkaDayAmount(map.get("jzrq").toString()) + "天");
                }
            }
            //list = sortBySYTS(list);
            list = setIndex(list);
        }
        response(list);
    }

    /**
     * 
     * <br>Description:获取文件督办列表
     * <br>Author:王雷
     * <br>Date:2013-9-9
     */
    public void getWjdbList() {
        String keyWord = request.getParameter("keyWord");
        String sql = "select t.yw_guid,t.wjspsx,t.wjlx,t.blsx,t.wjsq,t.blqk,t.createdate,t.zhblr from wjspdjb t where t.blqk='未处理'";
        if (keyWord != null) {
            keyWord = UtilFactory.getStrUtil().unescape(keyWord);
            sql += " and upper(t.yw_guid)||upper(t.wjspsx)||upper(t.zhblr)||upper(t.wjlx)||upper(t.blsx)||upper(t.wjsq)||upper(t.blqk)||upper(t.createdate) like '%"
                    + keyWord + "%'";
        }
        List<Map<String, Object>> list = query(sql, YW);
        if (list != null && list.size() > 0) {
            for (Map<String, Object> map : list) {
                map.put("YJ", getWorkaDayAmount(map.get("blsx").toString()));
                map.put("SYTS", getWorkaDayAmount(map.get("blsx").toString()));
            }
           // list = sortBySYTS(list);
            list = setIndex(list);
        }
        response(list);
    }

    /**
     * 
     * <br>Description:按剩余天数从小到大排序
     * <br>Author:王雷
     * <br>Date:2013-9-9
     * @param list
     * @return
     */
    private List<Map<String, Object>> sortBySYTS(List<Map<String, Object>> list) {
    	
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                Map<String, Object> map1 = list.get(i);
                Map<String, Object> map2 = list.get(j);
                Map<String, Object> tempMap;
                if (Integer.parseInt((String) map1.get("YJ")) > Integer.parseInt((String) map2.get("YJ"))) {
                    tempMap = map1;
                    map1 = map2;
                    map2 = tempMap;
                    list.set(i, map1);
                    list.set(j, map2);
                }
            }
        }
        return list;
    }

    /**
     * 
     * <br>Description:给list添加索引
     * <br>Author:王雷
     * <br>Date:2013-9-9
     * @param list
     * @return
     */
    private List<Map<String, Object>> setIndex(List<Map<String, Object>> list) {
        int i = 1;
        for (Map<String, Object> map : list) {
            map.put("INDEX", i++);
        }
        return list;
    }

    /**
     * 
     * <br>Description:获取剩余工作天数
     * <br>Author:王雷
     * <br>Date:2013-9-9
     * @param blqx
     * @return
     */
    private String getWorkaDayAmount(String blqx) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式

        String systemDate = df.format(new Date());//系统时间
        
        if(blqx.length() < 13){
        	blqx += " 00:00";
        }
        
        
        Calendar startTime = Calendar.getInstance();

        Calendar endTime = Calendar.getInstance();

        try {
            startTime.setTime(df.parse(systemDate));
            
            endTime.setTime(df.parse(blqx));

        } catch (ParseException e) {

            e.printStackTrace();

        }

        long xzsj = getDaysBetween(startTime, endTime);
        if(xzsj < 0){
        	return "已超时";
        }
        long days = xzsj/(1000*24*60*60);
        long hour = xzsj%(1000*24*60*60)/(1000*60*60);
        long min = xzsj%(1000*24*60*60)%(1000*60*60)/(1000*60);
        String diffTime = days + "天" + hour + "时" + min + "分";
        return diffTime;

    }
    
    /**
     * 
     * <br>Description:获取两个日期之间的工作日天数
     * <br>Author:王雷
     * <br>Date:2013-9-9
     * @param d1
     * @param d2
     * @return
     */
    public long getWorkingDay(java.util.Calendar d1, java.util.Calendar d2) {
        long result = -1;
        if (d1.after(d2)) {
        } else {
        	/*
            int charge_start_date = 0;//开始日期的日期偏移量
            int charge_end_date = 0;//结束日期的日期偏移量
            // 日期不在同一个日期内
            int stmp;
            int etmp;
            stmp = 7 - d1.get(Calendar.DAY_OF_WEEK);
            etmp = 7 - d2.get(Calendar.DAY_OF_WEEK);
            if (stmp != 0 && stmp != 6) {// 开始日期为星期六和星期日时偏移量为0
                charge_start_date = stmp - 1;
            }
            if (etmp != 0 && etmp != 6) {// 结束日期为星期六和星期日时偏移量为0
                charge_end_date = etmp - 1;
            }
            */
            result = getDaysBetween(this.getNextMonday(d1), this.getNextMonday(d2)); 
        }
        return result;
    }
    
    /**
     * 
     * <br>Description:获取两个日期之间的间隔
     * <br>Author:王雷
     * <br>Date:2013-9-9
     * @param d1
     * @param d2
     * @return
     */
    private long getDaysBetween(java.util.Calendar d1, java.util.Calendar d2) {
        //int days = d2.get(java.util.Calendar.DAY_OF_YEAR) - d1.get(java.util.Calendar.DAY_OF_YEAR); 
        //int hours = d2.get(java.util.Calendar.HOUR_OF_DAY) - d1.get(java.util.Calendar.HOUR_OF_DAY);
        //int minutes = d2.get(java.util.Calendar.MINUTE) - d1.get(java.util.Calendar.MINUTE);
 
        long days = 0;
        int y2 = d2.get(java.util.Calendar.YEAR);
        if (d1.get(java.util.Calendar.YEAR) != y2) {
            d1 = (java.util.Calendar) d1.clone();
            do {
                days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
                d1.add(java.util.Calendar.YEAR, 1);
            } while (d1.get(java.util.Calendar.YEAR) != y2);
        }
        long diff = d2.getTimeInMillis() - d1.getTimeInMillis();
        diff += days*1000*24*60*60;
       // days += diff/(1000*24*60*60);
       // long hour = diff%(1000*24*60*60)/(1000*60*60);
       // long min = diff%(1000*24*60*60)%(1000*60*60)/(1000*60);
       // String diffTime = days + "天" + hour + "时" + min + "分";
        return diff;
    }
    
    /**
     * 
     * <br>Description:获取下一个星期一的日期
     * <br>Author:王雷
     * <br>Date:2013-9-9
     * @param date
     * @return
     */
    private Calendar getNextMonday(Calendar date) {
        Calendar result = null;
        result = date;
        do {
            result = (Calendar) result.clone();
            result.add(Calendar.DATE, 1);
        } while (result.get(Calendar.DAY_OF_WEEK) != 2);
        return result;
    }

    /**
     * 
     * <br>Description:根据类型获取督办时间限制
     * <br>Author:黎春行
     * <br>Date:2013-9-9
     * @param type
     * @return
     */
    public  String getDbDateByType(String type){
        String sql = "select t.limit, t.hour, t.minuts from noticeset_ t where t.type = ?";
        List<Map<String, Object>> dateLimit = query(sql, YW, new Object[]{type});
        if(dateLimit.size() < 1){
            return "0";
        }else{
            return String.valueOf(dateLimit.get(0).get("limit") + "天" + dateLimit.get(0).get("hour") + "时" + dateLimit.get(0).get("minuts") + "分");
        }
    }
}
