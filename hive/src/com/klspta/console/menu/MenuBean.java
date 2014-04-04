package com.klspta.console.menu;

import java.math.BigDecimal;
import java.util.Map;


/**
 * 
 * <br>
 * Title:菜单Bean <br>
 * Description: <br>
 * Author:郭润沛 <br>
 * Date:2011-2-3
 */
public class MenuBean {
	/**
	 * 菜单ID
	 */
	private String menuId;
	/**
	 * 菜单名称
	 */
	private String menuName;
	/**
	 * 菜单类型1：一级菜单；2：二级菜单
	 */
	private String menuType;
	/**
	 * 父菜单id
	 */
	private String parentId;
	/**
	 * 序号
	 */
	private int sort;
	/**
	 * 图标
	 */
	private String icon;
	/**	
	 * url中间
	 */
	private String url_center;
	/**
	 * 中间是否被选中 0：否，1：是
	 */
	private String center;
	/**
	 * url右侧
	 */
	private String url_east;
	/**
	 * 右侧是否被选中 0：否，1：是
	 */
	private String east;
	/**
	 * 本地可执行文件路径
	 */
	private String url_local;
	/**
	 * 当前是否被选中 0：否，1：是
	 */
	private String local;
	/**
	 * 菜单点击事件
	 */
	private String handler;
	/**
	 * 菜单描述
	 */
	private String description;
	/**
	 * 是否被选中 0:否,1:是(菜单授权时使用)
	 */
	private String checked;
	
	
	public MenuBean()
	{
		
	}
	public MenuBean(Map<String, Object> map)
	{	
	 //15
		this.menuId=(String)map.get("menuId");
		this.menuName=(String)map.get("menuName");
		this.menuType=(String)map.get("menuType");
		this.parentId=(String)map.get("parentId");
		this.icon=(String)map.get("icon");
		this.handler=(String)handler;
		this.description=(String)map.get("description");
		this.sort=((BigDecimal)map.get("sort")).intValue();
		this.url_center=(String)map.get("url_center");
		this.center=(String)map.get("center");
		this.url_east=(String)map.get("url_east");
		this.east=(String)map.get("east");
		this.url_local=(String)map.get("url_local");
		this.local=(String)map.get("local");
		this.checked=(String)map.get("checked");
	}

	/**
	 * 
	 * <br>
	 * Description:获取 是否被选中 0:否,1:是(菜单授权时使用) <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @return
	 */
	public String getChecked() {
		return checked;
	}

	/**
	 * 
	 * <br>
	 * Description:设置 是否被选中 0:否,1:是(菜单授权时使用) <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @param checked
	 */
	public void setChecked(String checked) {
		this.checked = checked;
	}

	/**
	 * 
	 * <br>
	 * Description:获取菜单ID <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @return
	 */
	public String getMenuId() {
		return menuId;
	}

	/**
	 * 
	 * <br>
	 * Description:设置菜单ID <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @param menuId
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * 
	 * <br>
	 * Description:获取菜单名称 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @return
	 */
	public String getMenuName() {
		if (menuName == null || "null".equals(menuName)) {
			menuName = "";
		}
		return menuName;
	}

	/**
	 * 
	 * <br>
	 * Description:设置菜单名称 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @param menuName
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	/**
	 * 
	 * <br>
	 * Description:获取菜单类型 1：一级菜单；2：二级菜单 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @return
	 */
	public String getMenuType() {
		return menuType;
	}

	/**
	 * 
	 * <br>
	 * Description:设置菜单类型 1：一级菜单；2：二级菜单 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @param menuType
	 */
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	/**
	 * 
	 * <br>
	 * Description:获取父菜单ID <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @return
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * 
	 * <br>
	 * Description:设置父菜单ID <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @param parentId
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * 
	 * <br>
	 * Description:获取序号 <br>
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
	 * Description:设置序号 <br>
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
	 * Description:获取图标 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @return
	 */
	public String getIcon() {
		if (icon == null || "null".equals(icon)) {
			icon = "";
		}
		return icon;
	}

	/**
	 * 
	 * <br>
	 * Description:设置图标 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @param icon
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * 
	 * <br>
	 * Description:获取菜单点击事件 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @return
	 */
	public String getHandler() {
		if (handler == null || "null".equals(handler)) {
			handler = "";
		}
		return handler;
	}

	/**
	 * 
	 * <br>
	 * Description:设置菜单点击事件 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @param handler
	 */
	public void setHandler(String handler) {
		this.handler = handler;
	}

	/**
	 * 
	 * <br>
	 * Description:获取菜单描述 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * <br>
	 * Description:设置菜单描述 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * <br>
	 * Description:获取中间URL <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @return
	 */
	public String getUrl_center() {
		if (url_center == null || "null".equals(url_center)) {
			url_center = "";
		}
		return url_center;
	}

	/**
	 * 
	 * <br>
	 * Description:设置中间URL <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @param url_center
	 */
	public void setUrl_center(String url_center) {
		this.url_center = url_center;
	}

	/**
	 * 
	 * <br>
	 * Description:获取右侧URL <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @return
	 */
	public String getUrl_east() {
		if (url_east == null || "null".equals(url_east)) {
			url_east = "";
		}
		return url_east;
	}

	/**
	 * 
	 * <br>
	 * Description:设置右侧URL <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @param url_east
	 */
	public void setUrl_east(String url_east) {
		this.url_east = url_east;
	}

	/**
	 * 
	 * <br>
	 * Description:获取中间选中状态 0：否，1：是 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @return
	 */
	public String getCenter() {
		if (center == null || "null".equals(center)) {
			center = "";
		}
		return center;
	}

	/**
	 * 
	 * <br>
	 * Description:设置中间选中状态 0：否，1：是 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @param center
	 */
	public void setCenter(String center) {
		this.center = center;
	}

	/**
	 * 
	 * <br>
	 * Description:获取右侧是否被选中 0：否，1：是 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @return
	 */
	public String getEast() {
		if (east == null || "null".equals(east)) {
			east = "";
		}
		return east;
	}

	/**
	 * 
	 * <br>
	 * Description:设置右侧选中状态 0：否，1：是 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @param east
	 */
	public void setEast(String east) {
		this.east = east;
	}

	/**
	 * 
	 * <br>
	 * Description:获取本地可执行文件路径 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @return
	 */
	public String getUrl_local() {
		if (url_local == null || "null".equals(url_local)) {
			url_local = "";
		}
		return url_local;
	}

	/**
	 * 
	 * <br>
	 * Description:设置本地可执行文件路径 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @param url_local
	 */
	public void setUrl_local(String url_local) {
		this.url_local = url_local;
	}

	/**
	 * 
	 * <br>
	 * Description:获取当前页面是否本选中 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @return
	 */
	public String getLocal() {
		if (local == null || "".equals(local)) {
			local = "";
		}
		return local;
	}

	/**
	 * 
	 * <br>
	 * Description:设置当前页面是否本选中 <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @param local
	 */
	public void setLocal(String local) {
		this.local = local;
	}
//	/**
//	 * 
//	 * <br>
//	 * Description: <br>
//	 * Author:陈强峰 <br>
//	 * Date:2012-2-7
//	 * 
//	 * @see com.klspta.common.form.IFormBean#parseFormBean(java.lang.String)
//	 */
//	@Override
//	public IFormBean parseFormBean(String jsonStr) {
//		return null;
//	}


	/**
	 * 
	 * <br>
	 * Description:解析formbean <br>
	 * Author:陈强峰 <br>
	 * Date:2012-2-7
	 * 
	 * @see com.klspta.common.form.IFormBean#parseFormBean(javax.servlet.http.HttpServletRequest)
	 */
//	@Override
//	public IFormBean parseFormBean(HttpServletRequest request) {
//		MenuBean menuBean = new MenuBean();
//		menuBean.setMenuId(request.getParameter("treeId"));
//		menuBean.setMenuName(request.getParameter("menuName"));
//		menuBean.setSort(Integer.parseInt(request.getParameter("sort")));
//		menuBean.setIcon(request.getParameter("icon"));
//		menuBean.setUrl_center(request.getParameter("url_center"));
//		menuBean.setCenter(request.getParameter("center") != null ? "1" : "0");
//		menuBean.setUrl_east(request.getParameter("url_east"));
//		menuBean.setEast(request.getParameter("east") != null ? "1" : "0");
//		menuBean.setUrl_local(request.getParameter("url_local"));
//		menuBean.setLocal(request.getParameter("local") != null ? "1" : "0");
//		menuBean.setHandler(request.getParameter("handler"));
//		return menuBean;
//	}
}
