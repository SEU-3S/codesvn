package com.klspta.console.map;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import com.klspta.base.AbstractBaseBean;

public class MapTreeBean extends AbstractBaseBean {

    public static final String MAP_TREE_CHECKED = "1";
    public static final String MAP_TREE_UNCHECKED = "0";
    private String key = "";
    private String treeId = "";
    private String treeName = "";
    private String serverId = "";
    private String layerId = "";
    private String type = "";
    private boolean checked = false;
    private String checkedStr = MAP_TREE_UNCHECKED;
    private double opacity = 0;
    private double sort = 0;
    private String leafFlag = "";
    private String flag = "";
    private String parentTreeId = "";
    private String kind = "";
    private String featureType = "";
    private String queryFields = "";
    private String queryFieldsInfo = "";
    private String mapFunction = "";
    private String layerName = "";
    private String fieldName = "";

    public MapTreeBean(String key, Map<String, Object> map) {
        this.key = key;
        if (String.valueOf(map.get("checked")).equals("1")) {
            this.checked = true;
            this.checkedStr = MAP_TREE_CHECKED;
        } else {
            this.checked = false;
            this.checkedStr = MAP_TREE_UNCHECKED;
        }
        this.kind = String.valueOf(map.get("kind"));
        this.layerId = String.valueOf(map.get("layerId"));
        this.leafFlag = String.valueOf(map.get("leaf_flag"));
        if (map.get("opacity") != null) {
            this.opacity = Double.parseDouble("" + map.get("opacity"));
        } else {
            this.opacity = 0;
        }
        this.serverId = String.valueOf(map.get("serverId"));
        this.parentTreeId = String.valueOf(map.get("parentTreeId"));
        this.sort = Integer.parseInt("" + (BigDecimal) map.get("sort"));
        this.treeId = String.valueOf(map.get("treeId"));
        this.treeName = String.valueOf(map.get("treeName"));
        this.type = String.valueOf(map.get("type"));
        this.featureType = String.valueOf(map.get("featureType"));
    }
    
    public Map<String, Object> build(){
        Map<String, Object> ret = new HashMap<String, Object>();
        if(leafFlag.equals("0")){
            ret.put("text", treeName);
            ret.put("checked", checked);
            ret.put("leaf", 0);
            ret.put("id", treeId);
            ret.put("children", getChildren(treeId));
        }else{
            ret.put("text", treeName);
            ret.put("checked", checked);
            ret.put("leaf", 1);
            ret.put("id", treeId);
            ret.put("parentId", parentTreeId);
            ret.put("type", type);
            ret.put("serverid", serverId);
            ret.put("layerid", layerId);
        }
        return ret;
    }
    
    private Vector<Map<String, Object>> getChildren(String treeId){
        Vector<Map<String, Object>> ret = new Vector<Map<String, Object>>();
        Vector<MapTreeBean> vect = MapManager.getInstance().getTreeBeansByTreeID(treeId);
        Iterator<MapTreeBean> iter = vect.iterator();
        while(iter.hasNext()){
            MapTreeBean mtb = iter.next();
            ret.add(mtb.build());
        }
        return ret;
    }

    public void iAmYourOwner(String key) {
        this.key = key;
    }

    public boolean add() {
        if (MapManager.getInstance().isMyOwner(key)) {
            String sql = "insert into gis_maptree(treeid,  sort, leaf_flag, flag, opacity, parenttreeid) values (?,?,?,'1','0',?)";
            Object[] args = { treeId, sort, leafFlag, parentTreeId };
            update(sql, CORE, args);
        } else {

        }
        return false;
    }

    public boolean delete() {
        if (MapManager.getInstance().isMyOwner(key)) {
            String sql2 = "delete map_role_maptree t where t.treeid=?";
            Object[] args2 = { treeId };
            update(sql2, CORE, args2);
            if (leafFlag.equals("0")) {
                String sql = "delete gis_maptree t where t.treeid=? or t.parenttreeid=?";
                Object[] args = { treeId, parentTreeId };
                // Globals.getCoreJdbcTemplate().update(sql, args);
                update(sql, CORE, args);
            } else {
                // deleteByTreeId(treeId);
            }
        }
        return false;
    }

    public boolean update() {
        if (MapManager.getInstance().isMyOwner(key)) {
            String sql = "update gis_maptree set treename = ?,serverid =?,layerid = ?,type = ?,checked = ?,opacity = ?,sort = ?,kind = ?,featuretype = ? where treeid =?";
            String treeid[] = { treeId };
            // 如果图层名称为空,则删除这笔数据
            if (treeName.equals("") || treeName == null) {
                String sql2 = "update gis_maptree set flag='0' where treeid=?";
                update(sql2, CORE, treeid);
            } else {
                String sql2 = "update gis_maptree set flag='1' where treeid=?";
                update(sql2, CORE, treeid);
                Object[] args = { treeName, serverId, layerId, type, checked, opacity, sort, kind, featureType, treeId };
                update(sql, CORE, args);
            }
        }
        return false;
    }

    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getLayerId() {
        return layerId;
    }

    public void setLayerId(String layerId) {
        this.layerId = layerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCheckedStr() {
        return checkedStr;
    }

    public void setCheckedStr(String checked) {
        if (checked.equals(MAP_TREE_CHECKED)) {
            this.checked = true;
        } else {
            this.checked = false;
        }
        this.checkedStr = checked;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        if (checked) {
            this.checkedStr = MAP_TREE_CHECKED;
        } else {
            this.checkedStr = MAP_TREE_UNCHECKED;
        }
    }

    public double getOpacity() {
        return opacity;
    }

    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }

    public double getSort() {
        return sort;
    }

    public void setSort(double sort) {
        this.sort = sort;
    }

    public String getLeafFlag() {
        return leafFlag;
    }

    public void setLeafFlag(String leafFlag) {
        this.leafFlag = leafFlag;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getParentTreeId() {
        return parentTreeId;
    }

    public void setParentTreeId(String parentTreeId) {
        this.parentTreeId = parentTreeId;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getFeatureType() {
        return featureType;
    }

    public void setFeatureType(String featureType) {
        this.featureType = featureType;
    }

    public String getQueryFields() {
        return queryFields;
    }

    public void setQueryFields(String queryFields) {
        this.queryFields = queryFields;
    }

    public String getQueryFieldsInfo() {
        return queryFieldsInfo;
    }

    public void setQueryFieldsInfo(String queryFieldsInfo) {
        this.queryFieldsInfo = queryFieldsInfo;
    }

    public String getMapFunction() {
        return mapFunction;
    }

    public void setMapFunction(String mapFunction) {
        this.mapFunction = mapFunction;
    }

    public String getLayerName() {
        return layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
