package com.klspta.console.maptree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.console.ManagerFactory;
import com.klspta.console.role.Role;

public class MapTreeManager extends AbstractBaseBean {

    private static MapTreeManager instance;

    public static List<MapTreeBean> maptreeList = new ArrayList<MapTreeBean>();

    public static MapTreeManager getInstance() {
        if (instance == null) {
            instance = new MapTreeManager();
            instance.flush();
        }
        return instance;
    }

    private void flush() {
        String sql = "select * from gis_maptree where flag='1' order by sort";
        List<Map<String, Object>> rs = query(sql, CORE);
        maptreeList.clear();
        for (int i = 0; i < rs.size(); i++) {
            Map<String, Object> map = rs.get(i);
            MapTreeBean t = new MapTreeBean(map);
            maptreeList.add(t);
        }
    }

    /**
     * <br>Description:新增
     * <br>Author:郭润沛
     * <br>Date:2011-3-22
     * @param mapTreeBean
     */
    public void add(MapTreeBean mapTreeBean) {
        String sql = "insert into gis_maptree(treeid,  sort, leaf_flag, flag, opacity, parenttreeid) values (?,?,?,'1','0',?)";
        Object[] args = { mapTreeBean.getTreeId(), mapTreeBean.getSort(), mapTreeBean.getLeaf_flag(),
                mapTreeBean.getParentTreeId() };
        //Globals.getCoreJdbcTemplate().update(sql, args);
        update(sql, CORE, args);
        flush();
    }

    /**
     * <br>Description:删除
     * <br>Author:郭润沛
     * <br>Date:2011-3-22
     * @param mapTreeBean
     */
    public void delete(MapTreeBean mapTreeBean) {
        String sql2 = "delete map_role_maptree t where t.treeid=?";
        Object[] args2 = { mapTreeBean.getTreeId() };
        update(sql2, CORE, args2);
        if (mapTreeBean.getLeaf_flag().equals("0")) {
            String sql = "delete gis_maptree t where t.treeid=? or t.parenttreeid=?";
            Object[] args = { mapTreeBean.getTreeId(), mapTreeBean.getParentTreeId() };
            //Globals.getCoreJdbcTemplate().update(sql, args);
            update(sql, CORE, args);
        } else {
            deleteByTreeId(mapTreeBean.getTreeId());
        }
        flush();
    }

    /**
     * <br>Description:根据treeid删除
     * <br>Author:郭润沛
     * <br>Date:2011-3-22
     * @param mapTreeId
     */
    public void deleteByTreeId(String mapTreeId) {
        String sql = "delete gis_maptree t where t.treeid=?";
        Object[] args = { mapTreeId };
        //Globals.getCoreJdbcTemplate().update(sql, args);
        update(sql, CORE, args);
    }

    /**
     * <br>Description:保存
     * <br>Author:郭润沛
     * <br>Date:2011-3-22
     * @param mapTreeBean
     */
    public String save(MapTreeBean mapTreeBean) {
        String sql = "update gis_maptree set treename = ?,serverid =?,layerid = ?,type = ?,checked = ?,opacity = ?,sort = ?,kind = ?,featuretype = ? where treeid =?";
        String message = "{success:true,msg:true}";
        String treeid[] = { mapTreeBean.getTreeId() };
        //如果图层名称为空,则删除这笔数据
        if (mapTreeBean.getTreeName().equals("") || mapTreeBean.getTreeName() == null) {
            mapTreeBean.setLeaf_flag("0");
            //delete(mapTreeBean);
            String sql2 = "update gis_maptree set flag='0' where treeid=?";
            update(sql2, CORE, treeid);
            message = "{failure:true, msg:true}";
        } else {
            String sql2 = "update gis_maptree set flag='1' where treeid=?";
            update(sql2, CORE, treeid);
            Object[] args = { mapTreeBean.getTreeName(), mapTreeBean.getServerId(), mapTreeBean.getLayerId(),
                    mapTreeBean.getType(), mapTreeBean.getChecked(), mapTreeBean.getOpacity(),
                    mapTreeBean.getSort(), mapTreeBean.getKind(), mapTreeBean.getFeatureType(),
                    mapTreeBean.getTreeId() };
            update(sql, CORE, args);
        }

        //Globals.getCoreJdbcTemplate().update(sql, args);
        flush();
        return message;

    }

    public String getExtMapTree(String roleid) {
        return getExtTreeByMapTreeList(getMapTreeBeanListByRoleId(roleid));
    }

    /**
     * 
     * <br>Description:根据roleId获取MapTree
     * <br>Author:黎春行
     * <br>Date:2012-7-12
     * @param roleId
     * @return
     */
    public List<MapTreeBean> getMapTreeBeanListByRoleId(String roleId) {
        List<MapTreeBean> maptreeList = MapTreeManager.getInstance().getMaptreeList();
        List<MapTreeBean> list = new ArrayList<MapTreeBean>();
        String sql = "select p.treeid from map_role_maptree p where p.roleid=? order by treeid";
        String args[] = { roleId };
        List<Map<String, Object>> resultList = query(sql, CORE, args);
        List<String> treeidList = new ArrayList<String>();
        for (Map<String, Object> resultMap : resultList) {
            treeidList.add(String.valueOf(resultMap.get("treeid")));
        }
        for (MapTreeBean mapTreeBean : maptreeList) {
            for (String treeid : treeidList) {
                if (treeid.equals(mapTreeBean.getTreeId())) {
                    //如果该节点不是父节点，且父节点没有保存则添加父节点
                    String parentTreeid = mapTreeBean.getParentTreeId();
                    if (!parentTreeid.equals("0")) {
                        boolean isExist = false;
                        for (MapTreeBean existMapTreeBean : list) {
                            if (existMapTreeBean.getTreeId().equals(parentTreeid)) {
                                isExist = true;
                            }
                        }
                        if (!isExist) {
                            MapTreeBean parentMapTreeBean = getMapTreeBeanByTreeid(parentTreeid);
                            list.add(parentMapTreeBean);
                        }
                    }
                    list.add(mapTreeBean);
                }
            }
        }
        return list;
    }

    /**
     * 
     * <br>Description:获取所有mapTreeBean 并确定roleId是否有权限
     * <br>Author:黎春行
     * <br>Date:2012-7-12
     * @param roleId
     * @return
     */
    public List<MapTreeBean> getAllMapTreeBeanListByRoleId(String roleId) {
        List<MapTreeBean> maptreeList = MapTreeManager.getInstance().getMaptreeList();
        List<MapTreeBean> list = new ArrayList<MapTreeBean>();
        String sql = "select p.treeid from map_role_maptree p where p.roleid=?";
        String args[] = { roleId };
        List<Map<String, Object>> resultList = query(sql, CORE, args);
        List<String> treeidList = new ArrayList<String>();
        for (Map<String, Object> resultMap : resultList) {
            treeidList.add(String.valueOf(resultMap.get("treeid")));
        }
        for (MapTreeBean mapTreeBean : maptreeList) {
            MapTreeBean newTreeBean = new MapTreeBean();
            newTreeBean.setFeatureType(mapTreeBean.getFeatureType());
            newTreeBean.setKind(mapTreeBean.getKind());
            newTreeBean.setLayerId(mapTreeBean.getLayerId());
            newTreeBean.setLeaf_flag(mapTreeBean.getLeaf_flag());
            newTreeBean.setOpacity(mapTreeBean.getOpacity());
            newTreeBean.setParentTreeId(mapTreeBean.getParentTreeId());
            newTreeBean.setServerId(mapTreeBean.getServerId());
            newTreeBean.setSort(mapTreeBean.getSort());
            newTreeBean.setTreeId(mapTreeBean.getTreeId());
            newTreeBean.setTreeName(mapTreeBean.getTreeName());
            newTreeBean.setType(mapTreeBean.getType());
            newTreeBean.setChecked("0");
            for (String treeid : treeidList) {
                if (treeid.equals(mapTreeBean.getTreeId())) {
                    newTreeBean.setChecked("1");
                }
            }
            list.add(newTreeBean);
        }
        return list;
    }

    /**
     * 
     * <br>Description:修改支持3级菜单
     * <br>Author:朱波海
     * <br>Date:2013-11-17
     * @param mapTreeBeanList
     * @return
     */
public String  getExtTreeByMapTreeList(List<MapTreeBean> mapTreeBeanList){
	 String MapTreeStr = null;
     StringBuffer sb = new StringBuffer("[");
     for (int i = 0; i < mapTreeBeanList.size(); i++) {
         MapTreeBean t = (MapTreeBean) mapTreeBeanList.get(i);
         String leaf_flag = t.getLeaf_flag();
         String checked = t.getChecked();
         if ("1".equals(checked)) {
             checked = "true";
         } else {
             checked = "false";
         }
         if ("0".equals(leaf_flag) && "0".equals(t.getParentTreeId())) { 
             if (sb.length() > 2) {
                 sb.append(",");
             }
             sb.append("\n{text:'" + t.getTreeName() + "',checked:" + checked + ", leaf: 0,id:'"
                     + t.getTreeId() + "'}");
         }
     }
     for (int i = 0; i < mapTreeBeanList.size(); i++) {
    	  MapTreeBean t = (MapTreeBean) mapTreeBeanList.get(i);
          String leaf_flag = t.getLeaf_flag();
          String checked = t.getChecked();
          if ("1".equals(checked)) {
              checked = "true";
          } else {
              checked = "false";
          }
          String parentId = t.getParentTreeId();
          if(!("0".equals(leaf_flag) && "0".equals(t.getParentTreeId()))){
          //父节点  leaf: 0,id:'20'}
          int prosition_parent = sb.indexOf("id:'" + parentId + "',children:");
          int prosition_parent_child = sb.indexOf("id:'" + parentId + "'}");
          int prositon_child = sb.lastIndexOf(",parentId:'" + t.getParentTreeId() + "'");//最后一位兄弟节点
          int prositon_child_folder = sb.lastIndexOf(",parentId:'" + t.getParentTreeId() + "',id");
          if (leaf_flag.equals("0")) {
              if (prosition_parent >= 0) {//是子文件夹的且存在兄弟节点
                  if (prositon_child_folder >= 0) {
                      String sb_child_folder = ",{text:'" + t.getTreeName() + "',checked:" + checked
                              + ", leaf: 0,parentId:'" + t.getParentTreeId() + "',id:'" + t.getTreeId()
                              + "'}";
                      sb.insert(sb.indexOf("}", prositon_child_folder) + 1, sb_child_folder);
                      continue;
                  }
                  String sb_child_folder = ",{text:'" + t.getTreeName() + "',checked:" + checked
                          + ", leaf: 0,parentId:'" + t.getParentTreeId() + "',id:'" + t.getTreeId()
                          + "'}";
                  sb.insert(prositon_child + (",parentId:'" + t.getParentTreeId() + "'}").length(),
                          sb_child_folder);
              } else {
                  String sb_child_folder = ",children: [{text:'" + t.getTreeName() + "',checked:"
                          + checked + ",leaf: 0,parentId:'" + t.getParentTreeId() + "',id:'"
                          + t.getTreeId() + "'}]";
                  sb.insert(prosition_parent_child + ("id:'" + parentId + "'").length(),
                          sb_child_folder);
              }
              continue;
          }

          if (prosition_parent >= 0 && prositon_child_folder > 0) {
              String sb_child = ",{text:'" + t.getTreeName() + "',"
                      + getIcon(t.getType(), t.getFeatureType()) + "serverid:'" + t.getServerId()
                      + "',layerid:'" + t.getLayerId() + "',type:'" + t.getType() + "',checked:"
                      + checked + ", leaf: 1,id:'" + t.getTreeId() + "@" + t.getServerId() + "@"
                      + t.getLayerId() + "',parentId:'" + t.getParentTreeId() + "'}";
              sb.insert(sb.indexOf("}]}", prositon_child_folder) + 3, sb_child);
              continue;
          }

          if (prosition_parent >= 0) {// 存在兄弟节点
              String sb_child = ",{text:'" + t.getTreeName() + "',"
                      + getIcon(t.getType(), t.getFeatureType()) + "serverid:'" + t.getServerId()
                      + "',layerid:'" + t.getLayerId() + "',type:'" + t.getType() + "',checked:"
                      + checked + ", leaf: 1,id:'" + t.getTreeId() + "@" + t.getServerId() + "@"
                      + t.getLayerId() + "',parentId:'" + t.getParentTreeId() + "'}";
              sb.insert(prositon_child + (",parentId:'" + t.getParentTreeId() + "'}").length(),
                      sb_child);

          } else if (prosition_parent_child >= 0) {// 不存在兄弟节点
              String sb_child = ",children: [{text:'" + t.getTreeName() + "',"
                      + getIcon(t.getType(), t.getFeatureType()) + "serverid:'" + t.getServerId()
                      + "',layerid:'" + t.getLayerId() + "',type:'" + t.getType() + "',checked:"
                      + checked + ", leaf: 1,id:'" + t.getTreeId() + "@" + t.getServerId() + "@"
                      + t.getLayerId() + "',parentId:'" + t.getParentTreeId() + "'}]";
              sb.insert(prosition_parent_child + ("id:'" + parentId + "'").length(), sb_child);
          } else {
              System.out.println("图层树配置错误：   " + t.getTreeName() + "   不存在父级节点。");
          }
          }
     }
     if (sb.length() > 1) {
         sb.append("]");
         MapTreeStr = sb.toString();
         System.out.println(MapTreeStr);
     }
     return MapTreeStr;
}
    
    
    
    private static String getIcon(String mapServiceType, String featureType) {
        // if (mapServiceType != null && "tiled".equals(mapServiceType)) {
        // return "icon:'gisapp/images/tiled.png',";
        // } else if (mapServiceType != null &&
        // mapServiceType.equals("dynamic")) {
        // if (featureType == null) {
        // return "";//默认
        // } else {
        // return "icon:'gisapp/images/" + featureType + ".png',";
        // }
        // }
        return "";
    }

    public List<MapTreeBean> getMemoMapTreeBeanListByUserId(String userId) {
        List<Role> roleList;
        try {
            roleList = ManagerFactory.getUserManager().getUserWithId(userId).getRoleList();
            //根据角色获取MapTree
            List<String> roleidList = getRoleIdListByRoleList(roleList);
            //List<RoleMapAuthorBean> cRoleMapAuthorList=getMemoRoleMapAuthorBeanListByRoleIdList(roleidList, roleMapAuthorList);
            List<MapTreeBean> cMapTreeList = getMapTreeListByRoleList(roleidList);
            return cMapTreeList;
        } catch (Exception e) {
        	responseException(this, "getMemoMapTreeBeanListByUserId", "200001", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <br>Description:从比较所得的userRoleMapBeanList中获得RoleIdList
     * <br>Author:戎银华
     * <br>Date:2011-3-24
     * @param list
     * @return
     */
    public List<String> getRoleIdListByRoleList(List<Role> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        List<String> returnList = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            returnList.add(list.get(i).getRoleid());
        }
        return returnList;
    }

    /**
     * 
     * <br>Description:根据roleidList获取MapTree
     * <br>Author:黎春行
     * <br>Date:2012-7-11
     * @param roleidList
     * @return
     */
    public List<MapTreeBean> getMapTreeListByRoleList(List<String> roleidList) {
        List<MapTreeBean> list = new ArrayList<MapTreeBean>();
        for (String roleid : roleidList) {
            List<MapTreeBean> roleList = getMapTreeBeanListByRoleId(roleid);
            //list.addAll(roleList);     //需要查重，不能直接相加
            for (MapTreeBean roleTree : roleList) {
                boolean isExist = false;
                for (MapTreeBean haveBean : list) {
                    if (roleTree.getTreeId().equals(haveBean.getTreeId())) {
                        isExist = true;
                    }
                }
                if (!isExist) {
                    list.add(roleTree);
                }
            }
        }
        return list;
    }

    /**
     * 
     * <br>Description:保存权限持久化
     * <br>Author:黎春行
     * <br>Date:2012-7-12
     * @param roleId
     * @param mapTreeIds
     */
    public void save(String roleId, String mapTreeIds) {
        //MapTreeManager.getInstance().save(roleId, mapTreeIds);
        //RoleMapAuthorManager.getInstance().save(roleId, mapTreeIds);
        String[] treeIdArray = MapTreeManager.getArrayByStr(mapTreeIds);
        //先全部删除roleId相关的记录，
        String sql = "delete map_role_maptree t where  t.roleid=?";
        Object[] args = { roleId };
        //Globals.getCoreJdbcTemplate().update(sql, args);
        update(sql, CORE, args);
        //然后再更新
        if (treeIdArray != null && treeIdArray.length != 0) {
            String sql1 = "insert into map_role_maptree (roleid, treeid) (";
            Object[] args1 = new Object[treeIdArray.length * 2];
            for (int i = 0; i < treeIdArray.length; i++) {
                if (treeIdArray[i] == null) {
                    continue;
                }
                if (i > 0) {
                    sql1 += " union all ";
                }
                sql1 += "select ?, ?  from dual ";
                args1[i * 2] = roleId;
                args1[i * 2 + 1] = treeIdArray[i];
            }
            sql1 += ")";
            //Globals.getCoreJdbcTemplate().update(sql1, args1);
            if (args1 != null && args1.length != 0) {
                update(sql1, CORE, args1);
            }
        }

    }

    /**
     * 
     * <br>Description:根据Treeid获取Bean
     * <br>Author:黎春行
     * <br>Date:2012-7-12
     * @param treeId
     * @return
     */
    public MapTreeBean getMapTreeBeanByTreeid(String treeId) {
        treeId = this.treeIdFilter(treeId);
        List<MapTreeBean> mapTreeBeanList = MapTreeManager.getInstance().getMaptreeList();
        MapTreeBean mapTreeBean = null;
        for (int i = 0; i < mapTreeBeanList.size(); i++) {
            if (mapTreeBeanList.get(i).getTreeId().equals(treeId)) {
                mapTreeBean = mapTreeBeanList.get(i);
                break;
            }
        }
        return mapTreeBean;
    }

    /**
     * <br>Description:如果传回来的treeid为子节点，则过滤掉@后面的信息
     * <br>Author:戎银华
     * <br>Date:2011-3-25
     * @return
     */
    public String treeIdFilter(String treeId) {
        String[] strs = treeId.split("@");
        return strs[0];
    }

    /**
     * <br>Description:将用逗号隔开的字符串处理获得treeId数组
     * <br>Author:戎银华
     * <br>Date:2011-3-22
     * @param resourceStr：用逗号隔开的字符串（前台上传）
     * @return
     */
    private static String[] getArrayByStr(String resourceStr) {
        String[] tempArray = resourceStr.split(",");
        String[] strs = new String[tempArray.length];
        if (tempArray != null || tempArray.length > 0 || "".equals(resourceStr)) {
            for (int i = 0; i < tempArray.length; i++) {
                String[] temp2 = tempArray[i].split("@");
                if (temp2 != null || temp2.length > 0) {
                    strs[i] = temp2[0].trim();
                }
            }
        } else {
            strs = null;
        }
        return strs;
    }
public List<MapTreeBean> getMaptreeList(){
	return maptreeList;
}
}
