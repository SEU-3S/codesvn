package com.klspta.console.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.console.ManagerFactory;
import com.klspta.console.user.User;

public class MapManager extends AbstractBaseBean {

    private Map<String, MapTreeBean> treeBeans = new TreeMap<String, MapTreeBean>();
    private Map<String, Vector<RoleTreeMapBean>> roleTreeMapBeans = new HashMap<String, Vector<RoleTreeMapBean>>();

    private static final String opKey = UtilFactory.getStrUtil().getGuid();

    private static MapManager instance;
    
    public static MapManager getInstance() {
        if (instance == null) {
            instance = new MapManager();
        }
        return instance;
    }

    private MapManager() {
        initTreeBean();
    }
    
    private Vector<RoleTreeMapBean> getRoleTreeMapBeans(String roleid){
        if(roleTreeMapBeans.containsKey(roleid)){
            return roleTreeMapBeans.get(roleid);
        }else{
            initRoleBean(roleid);
            return getRoleTreeMapBeans(roleid);
        }
    }
    
    private void initRoleBean(String roleid){
        Vector<RoleTreeMapBean> vector = new Vector<RoleTreeMapBean>();
        String sql = "select * from map_role_maptree where roleid = ?";
        List<Map<String, Object>> resultList = query(sql, CORE, new Object[] {roleid});
        Iterator<Map<String, Object>> iter = resultList.iterator();
        while (iter.hasNext()) {
            Map<String, Object> m = iter.next();
            RoleTreeMapBean r = new RoleTreeMapBean(m);
            vector.add(r);
        }
        roleTreeMapBeans.put(roleid, vector);
    }

    private void initTreeBean() {
        String sql = "select * from gis_maptree order by sort desc";
        List<Map<String, Object>> resultList = query(sql, CORE);
        Iterator<Map<String, Object>> iter = resultList.iterator();
        while (iter.hasNext()) {
            Map<String, Object> m = iter.next();
            MapTreeBean r = new MapTreeBean(opKey, m);
            treeBeans.put(r.getTreeId(), r);
        }
    }

    public boolean isMyOwner(String key) {
        if (key.equals(opKey)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean add(MapTreeBean mtb) {
        mtb.iAmYourOwner(opKey);
        if (mtb.add()) {
            treeBeans.put(mtb.getTreeId(), mtb);
            return true;
        } else {
            return false;
        }
    }

    public boolean delete(MapTreeBean mtb) {
        mtb.iAmYourOwner(opKey);
        if (mtb.delete()) {
            treeBeans.remove(mtb.getTreeId());
            return true;
        } else {
            return false;
        }
    }

    public boolean update(MapTreeBean mtb) {
        mtb.iAmYourOwner(opKey);
        if (mtb.update()) {
            treeBeans.remove(mtb.getTreeId());
            treeBeans.put(mtb.getTreeId(), mtb);
            return true;
        } else {
            return false;
        }
    }

    public Vector<Map<String, Object>> getExtTree(String userid) throws Exception {
        Map<String, MapTreeBean> tree = getTreeBeans(userid);
        Vector<Map<String, Object>> root = new Vector<Map<String, Object>>();
        Iterator<MapTreeBean> iter = tree.values().iterator();
        while(iter.hasNext()){
            MapTreeBean mtb = iter.next();
            Map<String, Object> ele = mtb.build();
            root.add(ele);
        }
        return root;
    }

    public Map<String, MapTreeBean> getTreeBeans(String userid) throws Exception {
        User user = ManagerFactory.getUserManager().getUserWithId(userid);
        List<String> roleidList = user.getRoleIdListByRoleList();
        Map<String, MapTreeBean> cMapTreeList = getMapTreeListByRoleIds(roleidList);
        return cMapTreeList;
    }
    
    public Vector<MapTreeBean> getTreeBeansByTreeID(String treeId){
        Vector<MapTreeBean> vector = new Vector<MapTreeBean>();
        Iterator<MapTreeBean> iter = treeBeans.values().iterator();
        while(iter.hasNext()){
            MapTreeBean mtb = iter.next();
            if(mtb.getParentTreeId().equals(treeId)){
                vector.add(mtb);
            }
        }
        return vector;
    }

    private Map<String, MapTreeBean> getMapTreeListByRoleIds(List<String> roleidList) {
        Map<String, MapTreeBean> list = new LinkedHashMap<String, MapTreeBean>();
        Iterator<String> iterRoleid = roleidList.iterator();
        Iterator<RoleTreeMapBean> iterRoleTreeMap = null;
        while (iterRoleid.hasNext()) {
            String roleid = iterRoleid.next();
            iterRoleTreeMap = getRoleTreeMapBeans(roleid).iterator();
            while (iterRoleTreeMap.hasNext()) {
                RoleTreeMapBean rtm = iterRoleTreeMap.next();
                if(treeBeans.get(rtm.getTreeid()).getLeafFlag().equals("0")){
                    list.put(rtm.getTreeid(), treeBeans.get(rtm.getTreeid()));
                }
            }
        }
        return list;
    }
}
