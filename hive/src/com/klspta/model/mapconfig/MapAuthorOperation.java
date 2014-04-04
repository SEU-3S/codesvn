package com.klspta.model.mapconfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.console.map.MapManager;
import com.klspta.console.maptree.MapServiceBean;
import com.klspta.console.maptree.MapServiceManager;
import com.klspta.console.maptree.MapTreeBean;
import com.klspta.console.maptree.MapTreeManager;

/**
 * 
 * <br>Title:TODO 类标题
 * <br>Description:地图授权和图层管理
 * <br>Author:黎春行
 * <br>Date:2012-7-10
 */
public class MapAuthorOperation extends AbstractBaseBean {
    
    /**
     * 
     * <br>Description:地图授权
     * <br>Author:黎春行
     * <br>Date:2012-7-10
     */
    public void addMapAuthor(){
        String roleId = request.getParameter("roleId");
        String treeidlist = request.getParameter("treeIdList");
        String msg = "{success:true,msg:true}";
        //MapTreeAuthorityUtil.getInstance().save(roleId, treeidlist);
        MapTreeManager.getInstance().save(roleId, treeidlist);
        //MapTreeUtil.getInstance().save(roleId, treeidlist);
        
        response(msg);
    }
    
    /**
     * 
     * <br>Description:添加图层树节点
     * <br>Author:黎春行
     * <br>Date:2012-7-12
     */
    public void addMapTreeNode(){
        String mapTreeId = request.getParameter("treeId");
        String leafFlag = request.getParameter("leafFlag");
        String parentTreeId = request.getParameter("parentTreeId");
        MapTreeBean mapTreeBean = new MapTreeBean();
        mapTreeBean.setTreeId(mapTreeId);
        mapTreeBean.setLeaf_flag(leafFlag);
        mapTreeBean.setParentTreeId(parentTreeId);
        mapTreeBean.setSort(0); //新增节点时默认排序号为0
        MapTreeManager.getInstance().add(mapTreeBean);
        String msg = "{success:true,msg:true}";
        response(msg);
    }
    
    
    public void deleteMapTreeNode(){
        String mapTreeId = request.getParameter("treeId");
        mapTreeId=MapTreeManager.getInstance().treeIdFilter(mapTreeId);
        String leafFlag = request.getParameter("leafFlag");
        String parentTreeId = request.getParameter("parentTreeId");
        MapTreeBean mapTreeBean = new MapTreeBean();
        mapTreeBean.setTreeId(mapTreeId);
        mapTreeBean.setLeaf_flag(leafFlag);
        mapTreeBean.setParentTreeId(parentTreeId);
        MapTreeManager.getInstance().delete(mapTreeBean);
        //RoleMapAuthorManager.getInstance().deleteByTreeId(mapTreeId);
    }
    
    public void saveMapTreeNode(){
         MapTreeBean mapTreeBean=new MapTreeBean();
           String treeId= MapTreeManager.getInstance().treeIdFilter(request.getParameter("treeId"));
           mapTreeBean.setTreeId(treeId);
           mapTreeBean.setTreeName(request.getParameter("treeName"));
           mapTreeBean.setSort(Integer.parseInt(request.getParameter("sort")));
           mapTreeBean.setServerId(request.getParameter("serverId"));
           mapTreeBean.setLayerId(request.getParameter("layerId"));
           mapTreeBean.setType(request.getParameter("type"));
           mapTreeBean.setFeatureType(request.getParameter("featureType"));
           mapTreeBean.setKind(request.getParameter("kind"));
           mapTreeBean.setOpacity(Double.parseDouble(request.getParameter("opacity")));
           mapTreeBean.setChecked(request.getParameter("checked"));
           String msg = MapTreeManager.getInstance().save(mapTreeBean);;
           response(msg);
    }
    /**
     * 
     * <br>Description:通过服务id获取json串
     * <br>Author:黎春行
     * <br>Date:2012-7-16
     */
    public void getLayerIds(){
        String str = MapServiceManager.getInstance().getJSON(request.getParameter("map_serverId"));
        response(str);
    }
    
    /**
     * 
     * <br>Description:根据userid获取mapTree
     * <br>Author:黎春行
     * <br>Date:2012-7-16
     */
/*    public void getExtTreeByUserid(){
        String userid = request.getParameter("userid");
        String tree = "";
        tree = MapTreeManager.getInstance().getExtTreeByMapTreeList(MapTreeManager.getInstance().getMemoMapTreeBeanListByUserId(userid));
        if(tree==null){
            tree="";
        }
        response(tree);
    }*/
    
    public void getExtTreeByUserid(){
        String userid = request.getParameter("userid");
        Vector<Map<String, Object>> tree = null;
        try {
            tree = MapManager.getInstance().getExtTree(userid);
        } catch (Exception e) {
            responseException(this, "getExtTreeByUserid", "200004", e);
        }
        if(tree != null){
            response(tree);
        }
    }
    
    /**
     * 
     * <br>Description:根据roleid获取mapTree
     * <br>Author:黎春行
     * <br>Date:2012-7-16
     */
    public void getExtTreeByRoleid(){
        String roleid = request.getParameter("roleid");
        String tree = MapTreeManager.getInstance().getExtTreeByMapTreeList(MapTreeManager.getInstance().getAllMapTreeBeanListByRoleId(roleid));
        response(tree);
    }
    
    /**
     * 
     * <br>Description:获取所有MapTree
     * <br>Author:黎春行
     * <br>Date:2012-7-16
     */
    public void getAllExtTree(){
        String tree = MapTreeManager.getInstance().getExtTreeByMapTreeList(MapTreeManager.getInstance().maptreeList);
        response(tree);
    }
    
    /**
     * 
     * <br>Description:获取mapservice
     * <br>Author:黎春行
     * <br>Date:2012-7-16
     */
    public void getMapServiceIds(){
        String serviceIds = MapServiceManager.getInstance().getMapService();
        response(serviceIds);
    }
    
    /**
     * 
     * <br>Description:获取指点服务别名
     * <br>Author:黎春行
     * <br>Date:2012-7-16
     */
    public void getAliasByServerid(){
        String serverId = request.getParameter("serverid");
        String alias = MapServiceManager.getInstance().getAlias(serverId);
        response(alias);
    }
    
    /**
     * 
     * <br>Description:获取所有的mapservice
     * <br>Author:黎春行
     * <br>Date:2012-7-17
     */
    @SuppressWarnings("unchecked")
    public void getAllMapService(){
        List<MapServiceBean> list = MapServiceManager.getInstance().getAlLMapService();
        //将mapserviceBean改为list
         List allRows = new ArrayList();
          for (int i = 0; i < list.size(); i++) {
            List oneRow = new ArrayList();
            MapServiceBean mapServiceBean=list.get(i);
            oneRow.add(mapServiceBean.getId());
            oneRow.add(mapServiceBean.getAlias());
            oneRow.add(mapServiceBean.getUrl());
            oneRow.add(mapServiceBean.getType());
            oneRow.add(mapServiceBean.getOpacity());
            oneRow.add(mapServiceBean.getDefaulton());
            oneRow.add(mapServiceBean.getFormat());
            oneRow.add(mapServiceBean.getFlag());
            oneRow.add(mapServiceBean.getRanking());
            oneRow.add(mapServiceBean.getServerId());
            oneRow.add(mapServiceBean.getServerId());
            //oneRow.add(i);
            allRows.add(oneRow);
        }
        response(allRows);
    }
    
    /**
     * 
     * <br>Description:删除固定Id的mapservice
     * <br>Author:黎春行
     * <br>Date:2012-7-17
     */
    public void deleteMapServiceById(){
        MapServiceBean mapServiceBean = new MapServiceBean();
        String id =  request.getParameter("id");
        mapServiceBean.setServerId(id);
        int result = MapServiceManager.getInstance().deleteMapService(mapServiceBean);
        response(String.valueOf(result));
    }
    
    public void getMapServiceById(){
        MapServiceBean mapServiceBean = new MapServiceBean();
        String id = request.getParameter("id");
        mapServiceBean = MapServiceManager.getInstance().getMapServiceById(id);
        List oneRow = new ArrayList();
        oneRow.add(mapServiceBean.getId());
        oneRow.add(mapServiceBean.getAlias());
        oneRow.add(mapServiceBean.getUrl());
        oneRow.add(mapServiceBean.getType());
        oneRow.add(mapServiceBean.getOpacity());
        oneRow.add(mapServiceBean.getDefaulton());
        oneRow.add(mapServiceBean.getFormat());
        oneRow.add(mapServiceBean.getFlag());
        oneRow.add(mapServiceBean.getRanking());
        response(oneRow);
    }
    
    public void addMapService(){
        MapServiceBean mapServiceBean = new MapServiceBean();
        mapServiceBean.setAlias(request.getParameter("alias"));
        mapServiceBean.setDefaulton(request.getParameter("defaulton"));
        mapServiceBean.setFlag(request.getParameter("flag"));
        mapServiceBean.setFormat(request.getParameter("format"));
        mapServiceBean.setId(request.getParameter("id"));
        mapServiceBean.setOpacity(request.getParameter("opacity"));
        mapServiceBean.setRanking(request.getParameter("ranking"));
        mapServiceBean.setType(request.getParameter("type"));
        mapServiceBean.setUrl(request.getParameter("url"));
        int i = MapServiceManager.getInstance().addMapService(mapServiceBean);
        String msg = "{success:true,msg:true}";
        if(-1 == i){
            msg = "{failure:true,msg:true}";
        }
        response(msg);
    }
    
    public void updateMapService(){
        MapServiceBean mapServiceBean = new MapServiceBean();
        String beforeId = request.getParameter("beforeid");
        String msg = "{success:true,msg:true}";
        if (beforeId == null) {
            msg = "{failure:true,msg:true}";
            response(msg);
            return;
        }
        mapServiceBean.setAlias(request.getParameter("alias"));
        mapServiceBean.setDefaulton(request.getParameter("defaulton"));
        mapServiceBean.setFlag(request.getParameter("flag"));
        mapServiceBean.setFormat(request.getParameter("format"));
        mapServiceBean.setId(request.getParameter("id"));
        mapServiceBean.setOpacity(request.getParameter("opacity"));
        mapServiceBean.setRanking(request.getParameter("ranking"));
        mapServiceBean.setType(request.getParameter("type"));
        mapServiceBean.setUrl(request.getParameter("url"));
        int i = MapServiceManager.getInstance().updateMapService(mapServiceBean, beforeId);
        if(-1 == i){
            msg = "{failure:true,msg:true}";
        }
        response(msg);
    }

}
