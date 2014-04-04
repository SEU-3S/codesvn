package com.klspta.console.maptree;

import java.math.BigDecimal;
import java.util.Map;


/**
 * 
 * <br>
 * Title:图层节点定义POJO <br>
 * Description: <br>
 * Author:郭润沛 <br>
 * Date:2011-1-26
 */
public class MapTreeBean{
	/**
	 * 图层id
	 */
	private String treeId;

	/**
	 * 图层显示名称
	 */
	private String treeName;

	/**
	 * 地图服务id
	 */
	private String serverId;

	/**
	 * 地图服务中对应的图层id
	 */
	private String layerId;

	/**
	 * 类型 : tiled切片dynamic矢量
	 */
	private String type;

	/**
	 * 默认是否选中 0否1是
	 */
	private String checked;

	/**
	 * 透明度0到1任意值
	 */
	private double opacity;

	/**
	 * 排序号
	 */
	private int sort;

	/**
	 * 是否叶子节点 0否1是
	 */
	private String leaf_flag;

	/**
	 * 父节点id
	 */
	private String parentTreeId;

	/**
	 * 数据分类：0现状图1规划图
	 */
	private String kind;
	/**
	 * 矢量类型：line、point、polygon
	 */
	private String featureType;

	/**
	 * 
	 * <br>Description:构造函数，从数据库map_tree中获取参数
	 * <br>Author:黎春行
	 * <br>Date:2012-8-7
	 * @param map
	 */
	public MapTreeBean(Map<String, Object> map) {
		this.checked = String.valueOf(map.get("checked"));
		this.kind = String.valueOf(map.get("kind"));
		this.layerId = String.valueOf(map.get("layerId"));
		this.leaf_flag = String.valueOf(map.get("leaf_flag"));
		if(map.get("opacity")!= null){
			this.opacity = Double.parseDouble(""+(BigDecimal)map.get("opacity"));
	    }else {
	    	this.opacity = 0;
	    }
		this.serverId = String.valueOf(map.get("serverId"));
		this.parentTreeId = String.valueOf(map.get("parentTreeId"));
		this.sort = Integer.parseInt(""+(BigDecimal)map.get("sort"));
		this.treeId = String.valueOf(map.get("treeId"));
		this.treeName = String.valueOf(map.get("treeName"));
		this.type = String.valueOf(map.get("type"));
		this.featureType = String.valueOf(map.get("featureType"));
		
	}
	
	/**
	 * 
	 * <br>Description:构造函数，无参数
	 * <br>Author:黎春行
	 * <br>Date:2012-8-7
	 */
	public MapTreeBean(){
		super();
	}

	/**
	 * 
	 * <br>
	 * Description:获取图层ID <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @return
	 */
	public String getTreeId() {
		return treeId;
	}

	/**
	 * 
	 * <br>
	 * Description:设置图层ID <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @param treeId
	 */
	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	/**
	 * 
	 * <br>
	 * Description:获取图层显示名称 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @return
	 */
	public String getTreeName() {
		if (treeName == null || "null".equals(treeName)) {
			treeName = "";
		}
		return treeName;
	}

	/**
	 * 
	 * <br>
	 * Description:获取图层显示名称 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @param treeName
	 */
	public void setTreeName(String treeName) {
		this.treeName = treeName;
	}

	/**
	 * 
	 * <br>
	 * Description:获取图层服务ID <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @return
	 */
	public String getServerId() {
		if (serverId == null || "null".equals(serverId)) {
			serverId = "";
		}
		return serverId;
	}

	/**
	 * 
	 * <br>
	 * Description:设置图层服务ID <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @param serverId
	 */
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	/**
	 * 
	 * <br>
	 * Description:获取地图服务中对应的图层id <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @return
	 */
	public String getLayerId() {
		if (layerId == null || "null".equals(layerId)) {
			layerId = "";
		}
		return layerId;
	}

	/**
	 * 
	 * <br>
	 * Description:设置地图服务中对应的图层id <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @param layerId
	 */
	public void setLayerId(String layerId) {
		this.layerId = layerId;
	}

	/**
	 * 
	 * <br>
	 * Description:获取tiled切片dynamic矢量 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @return
	 */
	public String getType() {
		if (type == null || "null".equals(type)) {
			type = "";
		}
		return type;
	}

	/**
	 * 
	 * <br>
	 * Description:设置tiled切片dynamic矢量 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 * <br>
	 * Description:获取是否选中0否1是 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @return
	 */
	public String getChecked() {
		if (checked == null || "null".equals(checked)) {
			checked = "";
		}
		return checked;
	}

	/**
	 * 
	 * <br>
	 * Description:设置是否选中0否1是 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @return
	 */
	public void setChecked(String checked) {
		this.checked = checked;
	}

	/**
	 * 
	 * <br>
	 * Description:获取透明度 0到1任意值 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @return
	 */
	public double getOpacity() {

		return opacity;
	}

	/**
	 * 
	 * <br>
	 * Description:设置透明度 0到1任意值 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @param opacity
	 */
	public void setOpacity(double opacity) {
		this.opacity = opacity;
	}

	/**
	 * 
	 * <br>
	 * Description:获取排序号 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @return
	 */
	public int getSort() {

		return sort;
	}

	/**
	 * 
	 * <br>
	 * Description:设置排序号 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @param sort
	 */
	public void setSort(int sort) {
		this.sort = sort;
	}

	/**
	 * 
	 * <br>
	 * Description:获取是否是叶子节点 0否1是 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @return
	 */
	public String getLeaf_flag() {
		return leaf_flag;
	}

	/**
	 * 
	 * <br>
	 * Description:设置是否叶子节点 0否1是 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @param leaf_flag
	 */
	public void setLeaf_flag(String leaf_flag) {
		this.leaf_flag = leaf_flag;
	}

	/**
	 * 
	 * <br>
	 * Description:获取父节点ID <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @return
	 */
	public String getParentTreeId() {
		return parentTreeId;
	}

	/**
	 * 
	 * <br>
	 * Description:设置父节点ID <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @param parentTreeId
	 */
	public void setParentTreeId(String parentTreeId) {
		this.parentTreeId = parentTreeId;
	}

	/**
	 * 
	 * <br>
	 * Description:获取数据类型 0现状图1规划图 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @return
	 */
	public String getKind() {
		if (kind == null || "null".equals(kind)) {
			kind = "";
		}
		return kind;
	}

	/**
	 * 
	 * <br>
	 * Description:设置数据类型 0现状图1规划图 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @param kind
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}

	/**
	 * 
	 * <br>
	 * Description:获取矢量类型: line、point、polygon <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @return
	 */
	public String getFeatureType() {
		if (featureType == null || "null".equals(featureType)) {
			featureType = "";
		}
		return featureType;
	}

	/**
	 * 
	 * <br>
	 * Description:设置矢量类型:line、point、polygon <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @param featureType
	 */
	public void setFeatureType(String featureType) {
		this.featureType = featureType;
	}

}
