package com.klspta.console.maptree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.klspta.base.AbstractBaseBean;

/**
 * <br>
 * Description:获取图层的id、name <br>
 * Author:李如意 <br>
 * Date:2011-08-24
 * 
 * @return 返回json格式的字符串
 */
public class MapServiceManager extends AbstractBaseBean {
    /**
     * 图层管理类实例
     */
    public static MapServiceManager instance;

    /**
     * 
     * <br>
     * Description:单例获取 <br>
     * Author:陈强峰 <br>
     * Date:2012-2-7
     * 
     * @return
     */
    public static MapServiceManager getInstance() {
        if (instance == null) {
            instance = new MapServiceManager();
        }
        return instance;
    }

    /**
     * 
     * <br>
     * Description:通过URL获取HTML <br>
     * Author:陈强峰 <br>
     * Date:2012-2-7
     * 
     * @param urlString
     * @return
     */
    public String getHtml(String urlString) {
        try {
            StringBuffer html = new StringBuffer();
            java.net.URL url = new java.net.URL(urlString);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            java.io.InputStreamReader isr = new java.io.InputStreamReader(conn.getInputStream(), "UTF-8");
            java.io.BufferedReader br = new java.io.BufferedReader(isr);
            String temp;
            String str;
            while ((temp = br.readLine()) != null) {
                if (!temp.trim().equals("")) {
                    html.append(temp);
                }
            }
            br.close();
            isr.close();
            str = trimAll(html.toString());
            /*
             * str = str.replaceAll("\\{", "["); str = str.replaceAll("\\}",
             * "]");
             */
            str = str.replaceAll("\\'", " ");
            return str;
        } catch (Exception e) {
        	responseException(this, "getHtml", "200001", e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 
     * <br>
     * Description:删除空格 <br>
     * Author:陈强峰 <br>
     * Date:2012-2-7
     * 
     * @param str
     * @return
     */
    public String trimAll(String str) {
        str = str.replaceAll(" ", "");
        return str;
    }

    /**
     * 
     * <br>
     * Description:通过服务id获取json串 <br>
     * Author:陈强峰 <br>
     * Date:2012-2-7
     * 
     * @param serverId
     * @return
     */
    public String getJSON(String serverId) {
        String content = "";
        if (serverId != "" && !serverId.equals("")) {
            String sql = "select t.URL from GIS_MAPSERVICES t where t.id='" + serverId + "'";
                //ResultSet rs = ResultSetManager.getInstance().getResult(sql, CORE, null);
            	List<Map<String, Object>> resultList = query(sql,CORE);
            	String url = "";
                if (resultList.size() >= 1) {
                	Map<String, Object> resultMap = resultList.get(0);
                    //url = rs.getString("URL");
                	url = String.valueOf(resultMap.get("URL"));
                }
                String http = url + "?f=pjson";
                content = getHtml(http);
        }
        if(content == null)
        	content = "";
        return content;
    }

    /**
     * 
     * <br>
     * Description:获取指点服务别名 <br>
     * Author:陈强峰 <br>
     * Date:2012-2-7
     * 
     * @param serverId
     * @return
     */
    public String getAlias(String serverId) {
        String alias = "";
        if (serverId != "" && !serverId.equals("")) {
            String sql = "select t.ALIAS from GIS_MAPSERVICES t where t.id='" + serverId + "'";
            List<Map<String, Object>> resultList = query(sql, CORE);
            if (resultList.size() >= 1) {
            	Map<String, Object> resultMap = resultList.get(0);
                //url = rs.getString("URL");
            	alias = String.valueOf(resultMap.get("ALIAS"));
            }
        }
        return alias;
    }

    /**
     * GIS_MAPSERVICES 获取地图服务ID、ALIAS
     * 
     * @return
     */
    public String getMapService() {
        String sql = "select t.ID,t.ALIAS from GIS_MAPSERVICES t ";
        List<List<Object>> allRows = new ArrayList<List<Object>>();
        List<Map<String, Object>> resultList = query(sql, CORE);
        for(Map<String, Object> iterMap : resultList){
        	List<Object> oneRow = new ArrayList<Object>();
        	oneRow.add(iterMap.get("ID"));
        	oneRow.add(iterMap.get("ALIAS"));
        	allRows.add(oneRow);
        }
        return JSONArray.fromObject(allRows).toString();
    }
    
    /**
     * 
     * <br>Description:添加mapServcie
     * <br>Author:黎春行
     * <br>Date:2012-7-17
     * @param mapServiceBean
     * @return
     */
    public int addMapService(MapServiceBean mapServiceBean){
    	String sql = "insert into GIS_MAPSERVICES(id, alias, url, type, opacity, defaulton, format, flag, ranking, serverid) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    	String args[]= {mapServiceBean.getId(), mapServiceBean.getAlias(), mapServiceBean.getUrl(), mapServiceBean.getType(), mapServiceBean.getOpacity(), mapServiceBean.getDefaulton(), mapServiceBean.getFormat(), mapServiceBean.getFlag(), mapServiceBean.getRanking(), mapServiceBean.getServerId()};
    	int result = update(sql, CORE, args);
    	return result;
    }
    
    /**
     * 
     * <br>Description:添加mapServcie
     * <br>Author:黎春行
     * <br>Date:2012-7-17
     * @param mapServiceBean
     * @return
     */
    public int updateMapService(MapServiceBean mapServiceBean, String id){
    	String sql = "update GIS_MAPSERVICES t set t.id = ?, t.alias=?, t.url=?, t.type=?, t.opacity=?, t.defaulton=?, t.format=?, t.flag=?, t.ranking=? where t.serverid=?";
    	String args[]= {mapServiceBean.getId(), mapServiceBean.getAlias(), mapServiceBean.getUrl(), mapServiceBean.getType(), mapServiceBean.getOpacity(), mapServiceBean.getDefaulton(), mapServiceBean.getFormat(), mapServiceBean.getFlag(), mapServiceBean.getRanking(),id};
    	int result = update(sql, CORE, args);
    	return result;
    }
    
    /**
     * 
     * <br>Description:删除mapService
     * <br>Author:黎春行
     * <br>Date:2012-7-17
     * @param mapServiceBean
     * @return
     */
    public int deleteMapService(MapServiceBean mapServiceBean){
    	String sql = "delete GIS_MAPSERVICES t where t.serverid=? ";
    	String args[] = {mapServiceBean.getServerId()};
    	int result = update(sql, CORE, args);
    	return result;
    }
    
    /**
     * 
     * <br>Description:获取所有的mapService
     * <br>Author:黎春行
     * <br>Date:2012-7-17
     * @return
     */
    public List<MapServiceBean> getAlLMapService(){
    	List<MapServiceBean> allRows = new ArrayList<MapServiceBean>();
    	String sql = "select * from GIS_MAPSERVICES";
    	List<Map<String, Object>> resultList = query(sql, CORE);
    	for(int i=0;i<resultList.size();i++){
    		MapServiceBean mapServiceBean = new MapServiceBean();
    		
    		allRows.add(mapServiceBean);
    	}
    	return allRows;
    }
    
    /**
     * 
     * <br>Description:根据Id返回mapservice, 如果不存在，生成一个mapservice
     * <br>Author:黎春行
     * <br>Date:2012-7-17
     * @param id
     * @return
     */
    public MapServiceBean getMapServiceById(String id){
    	MapServiceBean mapServiceBean = new MapServiceBean();
    	mapServiceBean.setServerId(id);
    	String sql = "select * from GIS_MAPSERVICES t where t.serverid=?";
    	Object args[] = {id};
    	List<Map<String, Object>> resultList = query(sql, CORE, args);
    	if(resultList.size() >= 1){
    		Map<String, Object> resultMap = resultList.get(0);
    		mapServiceBean.buildBean(resultMap);
    	}else{
    		addMapService(mapServiceBean);
    	}
    	return mapServiceBean;
    }
    
}
