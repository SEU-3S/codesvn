package com.klspta.console.maptree;

import java.util.Map;

public class MapServiceBean {
	
	
	private String serverId = "";
	/**
	 * 序号
	 */
	private String id="";
	/**
	 * 别名
	 */
	private String alias="";
	
	private String url="";
	/**
	 * 类型:dynamic、tiled、wms、wfs、wfs_t
	 */
	private String type="";
	/**
	 * 透明度
	 */
	private String opacity="";
	
	private String defaulton=""; 
	/**
	 * 格式
	 */
	private String format="";
	/**
	 * 启用标志0停用1启用
	 */
	private String flag="";
	/**
	 * 顺序标识
	 */
	private String ranking="";
	
	/**
	 * 
	 * <br>Description:构造函数，根据数据库中表GIS_MAPSERVICES获取
	 * <br>Author:黎春行
	 * <br>Date:2012-8-7
	 * @param map
	 */
	public MapServiceBean(Map<String, Object> map){
		
		this.alias = String.valueOf(map.get("alias"));
		this.defaulton = String.valueOf(map.get("defaulton"));
		this.flag = String.valueOf(map.get("flag"));
		this.format = String.valueOf(map.get("format"));
		this.id = String.valueOf(map.get("id"));
		this.opacity = String.valueOf(map.get("opacity"));
		this.ranking = String.valueOf(map.get("ranking"));
		this.type = String.valueOf(map.get("type"));
		this.url = String.valueOf(map.get("url"));
		this.serverId = String.valueOf(map.get("serverid"));
	}
	
	/**
	 * 
	 * <br>Description:根据数据库中表GIS_MAPSERVICES获取
	 * <br>Author:黎春行
	 * <br>Date:2012-8-7
	 * @param map
	 */
	public void buildBean(Map<String, Object> map){
		
		this.alias = String.valueOf(map.get("alias"));
		this.defaulton = String.valueOf(map.get("defaulton"));
		this.flag = String.valueOf(map.get("flag"));
		this.format = String.valueOf(map.get("format"));
		this.id = String.valueOf(map.get("id"));
		this.opacity = String.valueOf(map.get("opacity"));
		this.ranking = String.valueOf(map.get("ranking"));
		this.type = String.valueOf(map.get("type"));
		this.url = String.valueOf(map.get("url"));
		this.serverId = String.valueOf(map.get("serverid"));
	}
	
	/**
	 * 
	 * <br>Description:无参数构造函数
	 * <br>Author:黎春行
	 * <br>Date:2012-8-7
	 */
	public MapServiceBean(){
		super();
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		if(id != null && !id.equals("null"))
			this.id = id;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		if(alias != null && !alias.equals("null"))
			this.alias = alias;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		if(url != null && !url.equals("null"))
			this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		if(type != null && !type.equals("null"))
			this.type = type;
	}
	public String getOpacity() {
		return opacity;
	}
	public void setOpacity(String opacity) {
		if(opacity != null && !opacity.equals("null"))	
			this.opacity = opacity;
	}
	public String getDefaulton() {
		return defaulton;
	}
	public void setDefaulton(String defaulton) {
		if(defaulton != null && !defaulton.equals("null"))
			this.defaulton = defaulton;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		if(format != null && !format.equals("null"))
			this.format = format;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		if(flag != null && !flag.equals("null"))
			this.flag = flag;
	}
	public String getRanking() {
		return ranking;
	}
	public void setRanking(String ranking) {
		if(ranking != null && !ranking.equals("null"))
			this.ranking = ranking;
	}
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

}
