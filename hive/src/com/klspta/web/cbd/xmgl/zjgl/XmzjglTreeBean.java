package com.klspta.web.cbd.xmgl.zjgl;

import java.util.Map;

public class XmzjglTreeBean {
	private String tree_id = "";
	private String yw_guid = "";
	private String parent_id = "";
	private String common = "";
	private String tree_name = "";
	private String sort = "";
	private String rq = "";
	
	public XmzjglTreeBean(Map<String, Object> map) {
		super();
		setTree_id(getValue(map, "tree_id"));
		setYw_guid(getValue(map, "yw_guid"));
		setParent_id(getValue(map, "parent_id"));
		setCommon(getValue(map, "common"));
		setTree_name(getValue(map, "tree_name"));
		setSort(getValue(map, "sort"));
		setRq(getValue(map, "rq"));
	}
	
	private String getValue(Map<String, Object> map, String name){
		String value = String.valueOf(map.get(name));
		value = ("null".equals(value))?"":value;
		return value;
	}
	
	public String getTree_id() {
		return tree_id;
	}
	public void setTree_id(String treeId) {
		tree_id = treeId;
	}
	public String getYw_guid() {
		return yw_guid;
	}
	public void setYw_guid(String ywGuid) {
		yw_guid = ywGuid;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parentId) {
		parent_id = parentId;
	}
	public String getCommon() {
		return common;
	}
	public void setCommon(String common) {
		this.common = common;
	}
	public String getTree_name() {
		return tree_name;
	}
	public void setTree_name(String treeName) {
		tree_name = treeName;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getRq() {
		return rq;
	}
	public void setRq(String rq) {
		this.rq = rq;
	}
	
}
