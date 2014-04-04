package com.klspta.console.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.klspta.base.AbstractBaseBean;
import com.klspta.console.role.Role;
import com.klspta.console.user.User;

public class MenuManager extends AbstractBaseBean
{
	private static MenuManager instance = new MenuManager();

	private Map<String, MenuBean> menuBeanMap;
	private Map<String, List<MenuBean>> roleMenuMap;

	private MenuManager()
	{
		flush();
	}

	public static MenuManager getInstance(String key) throws Exception
	{
		if ("NEW WITH MANAGER FACTORY!".equals(key))
		{ 
		    instance.flush();
		    instance.flush();
			return instance;
		} else
		{
			throw new Exception("请从ManagerFacoory获取工具.");
		}
	}

	/**
	 * 
	 * <br>
	 * Description:初始化和刷新 <br>
	 * Author:任宝龙 <br>
	 * Date:2012-5-30
	 */
	private void flush()
	{
		String sql = "select t.description,t.handler,t.icon,t.menuId,t.menuName,t.menuType,t.parentId,t.sort,t.url_center,t.center,t.url_east,t.east,t.url_local,t.local,t.checked from core_menu t where t.flag='1' order by  t.menutype,t.sort";
		menuBeanMap = new HashMap<String, MenuBean>();
		List<Map<String, Object>> list = query(sql, CORE);
		for (Map<String, Object> map : list)
		{
			MenuBean menu = new MenuBean(map);
			menuBeanMap.put(menu.getMenuId(), menu);

		}
		buildRoleMenuMap();
	}

	/**
	 * 
	 * <br>
	 * Description:添加菜单 <br>
	 * Author:任宝龙 <br>
	 * Date:2012-5-30
	 * 
	 * @param menuBean
	 */
	public void add(MenuBean menuBean)
	{
		String sql = "insert into core_menu (menuid,menuname,menutype, parentid, sort,flag) values (?, ?,?, ?, ?,'1')";
		Object[] args = { menuBean.getMenuId(), menuBean.getMenuName(), menuBean.getMenuType(), menuBean.getParentId(), menuBean.getSort() };
		if (update(sql, CORE, args) != -1)
		{
			flush();
		}
	}

	/**
	 * 
	 * <br>
	 * Description: 删除菜单 <br>
	 * Author:任宝龙 <br>
	 * Date:2012-5-30
	 * 
	 * @param menuBean
	 */
	public void delete(MenuBean menuBean)
	{
		StringBuffer sqlBuffer = new StringBuffer("delete core_menu t where  t.menuid in (");
		List<MenuBean> list = getChildMenuList(menuBean);
		Object[] args = new Object[list.size()];
		MenuBean mb;
		for (int i = 0; i < list.size(); i++)
		{
			mb = list.get(i);
			sqlBuffer.append("?,");
			args[i] = mb.getMenuId();
		}
		String sql = sqlBuffer.substring(0, sqlBuffer.length() - 1) + ")";

		update(sql, CORE, args);
		flush();
	}

	/**
	 * 
	 * <br>
	 * Description:插入menu <br>
	 * Author:任宝龙 <br>
	 * Date:2012-5-30
	 * 
	 * @param menuBean
	 */
	public void save(MenuBean menuBean)
	{
		String sql = "update core_menu set menuname =?, sort =?,icon = ?,url_center = ?, handler = ?,url_east =?,center=?,east=?,url_local=?,local=? where menuid = ?";
		Object[] args = { menuBean.getMenuName(), menuBean.getSort(), menuBean.getIcon(), menuBean.getUrl_center(), menuBean.getHandler(), menuBean.getUrl_east(), menuBean.getCenter(), menuBean.getEast(), menuBean.getUrl_local(), menuBean.getLocal(), menuBean.getMenuId() };
		update(sql, CORE, args);
		flush();
	}

	/**
	 * 
	 * <br>
	 * Description:保存授权记录 <br>
	 * Author:任宝龙 <br>
	 * Date:2012-6-12
	 * 
	 * @param roleId
	 * @param menuIdList
	 */
	public void saveMenuRole(String roleId, String[] menuIdList)
	{
		String sql = "delete from  map_role_menu t where t.roleid=?";
		Object[] args = { roleId };
		update(sql, CORE, args);
		args = new Object[menuIdList.length + 1];
		args[0] = roleId;
		StringBuffer sqlInsert = new StringBuffer("insert into  map_role_menu(roleid, menuid)(select ?,t.menuid from core_menu t where t.menuid in(");
		for (int i = 0; i < menuIdList.length; i++)
		{
			sqlInsert.append("?,");
			args[i + 1] = menuIdList[i];
		}
		String sql2 = sqlInsert.substring(0, sqlInsert.length() - 1) + "))";
		update(sql2, CORE, args);

		flush();
	}

	/**
	 * 
	 * <br>
	 * Description:根据Id获取menu <br>
	 * Author:任宝龙 <br>
	 * Date:2012-5-30
	 * 
	 * @param menuId
	 * @return
	 */
	public MenuBean getMenuBeanByMenuId(String menuId)
	{
		return menuBeanMap.get(menuId);
	}

	/**
	 * 
	 * <br>
	 * Description:根据roleId获取菜单列表 <br>
	 * Author:任宝龙 <br>
	 * Date:2012-6-12
	 * 
	 * @param roleID
	 * @return
	 */
	public List<MenuBean> getMenuListByRoleId(String roleID)
	{
		return roleMenuMap.get(roleID);
	}

	/**
	 * 
	 * <br>
	 * Description:建立角色和菜单map <br>
	 * Author:任宝龙 <br>
	 * Date:2012-6-12
	 */
	private void buildRoleMenuMap()
	{
		String sql = "select t.roleid,t.menuid from map_role_menu t";
		List<Map<String, Object>> list = query(sql, CORE);
		roleMenuMap = new HashMap<String, List<MenuBean>>();
		for (Map<String, Object> map : list)
		{
			String roleId = (String) map.get("roleid");
			String menuId = (String) map.get("menuid");
			MenuBean menuBean = getMenuBeanByMenuId(menuId);
			if (menuBean == null)
				continue;
			if (!roleMenuMap.containsKey(roleId))
			{
				roleMenuMap.put(roleId, new ArrayList<MenuBean>());
			}
			roleMenuMap.get(roleId).add(menuBean);
		}
	}

	/**
	 * 
	 * <br>
	 * Description:根据menuBean获取所有子菜单列表 <br>
	 * Author:任宝龙 <br>
	 * Date:2012-6-14
	 * 
	 * @param menuBean
	 * @return
	 */
	private List<MenuBean> getChildMenuList(MenuBean menuBean)
	{
		List<MenuBean> menuList = new ArrayList<MenuBean>();
		menuList.addAll(menuBeanMap.values());
		Map<String, List<MenuBean>> map = getMenuMap(menuList);
		List<MenuBean> list = new ArrayList<MenuBean>();
		list.add(menuBean);
		if (map.containsKey(menuBean.getMenuId()))
		{
			addChildMenu(list, map, menuBean);
		}
		return list;
	}

	/**
	 * 
	 * <br>
	 * Description:根据menuBean获取所有子菜单列表 的递归方法 <br>
	 * Author:任宝龙 <br>
	 * Date:2012-6-14
	 * 
	 * @param list
	 * @param menuMap
	 * @param menuBean
	 */
	private void addChildMenu(List<MenuBean> list, Map<String, List<MenuBean>> menuMap, MenuBean menuBean)
	{
		List<MenuBean> childList = menuMap.get(menuBean.getMenuId());
		list.addAll(childList);
		for (MenuBean mb : childList)
		{
			if (menuMap.containsKey(mb.getMenuId()))
			{
				addChildMenu(list, menuMap, mb);
			}
		}
	}

	/**
	 * 
	 * <br>
	 * Description:获取用户获取 对应角色的所有菜单的并集 <br>
	 * Author:任宝龙 <br>
	 * Date:2012-6-14
	 * 
	 * @param userBean
	 * @return
	 */
	public  List<MenuBean> getMenuList(User userBean)
	{
		if (userBean == null)
			return null;
		List<Role> roleList = userBean.getRoleList();
		if (roleList == null)
			return null;
		List<MenuBean> menuList = new ArrayList<MenuBean>();
		List<MenuBean> tempList;
		for (Role role : roleList)
		{
			if(role==null)
				continue;
			tempList = getMenuListByRoleId(role.getRoleid());
			if (tempList != null)
			{
				tempList.removeAll(menuList);
				menuList.addAll(tempList);
			}
		}
		return menuList;
	}

	/**
	 * 
	 * <br>
	 * Description:根据List 构建菜单结构 <br>
	 * Author:任宝龙 <br>
	 * Date:2012-6-14
	 * 
	 * @param list
	 * @return
	 */
	public Map<String, List<MenuBean>> getMenuMap(List<MenuBean> list)
	{
		Map<String, List<MenuBean>> menuMap = new HashMap<String, List<MenuBean>>();

		String menuKey = "";
		// 建立菜单结构
		for (MenuBean mb : list)
		{

			menuKey = mb.getParentId();
			if (!menuMap.containsKey(menuKey))
			{
				menuMap.put(menuKey, new ArrayList<MenuBean>());
			}
			menuMap.get(menuKey).add(mb);
		}

		return menuMap;
	}

	/**
	 * 
	 * <br>
	 * Description:获取所有菜单信息的json <br>
	 * Author:任宝龙 <br>
	 * Date:2012-6-14
	 * 
	 * @return
	 */
	public String getExtMenuTreeByMenuBeanList()
	{
		List<MenuBean> menuList = new ArrayList<MenuBean>();
		menuList.addAll(menuBeanMap.values());

		Map<String, List<MenuBean>> menuMap = getMenuMap(menuList);

		List<MenuBean> rootList = menuMap.get("0");
		String jsonCode = ddExtMenuItemJsonCoreWithoutChecked(rootList, menuMap);

		return jsonCode;
	}

	/**
	 * 
	 * <br>
	 * Description: 获取所有菜单信息的json 的递归方法 <br>
	 * Author:任宝龙 <br>
	 * Date:2012-6-14
	 * 
	 * @param list
	 * @param menuMap
	 * @return
	 */
	private String ddExtMenuItemJsonCoreWithoutChecked(List<MenuBean> list, Map<String, List<MenuBean>> menuMap)
	{
		listSort(list);
		StringBuffer extJsonCode = new StringBuffer("[");
		List<MenuBean> childList = null;
		for (MenuBean mb : list)
		{
			if (extJsonCode.length() > 1)
			{
				extJsonCode.append(",");
			}
			extJsonCode.append("\n{text:'");// text
			extJsonCode.append(mb.getMenuName());
			extJsonCode.append("(");
			extJsonCode.append(mb.getSort());

			extJsonCode.append(")',leaf:"); // leaf

			childList = menuMap.get(mb.getMenuId());

			extJsonCode.append(childList == null ? "1,parentId:'" + mb.getParentId() + "'" : "0");
			extJsonCode.append(",id:'");// id
			extJsonCode.append(mb.getMenuId());
			extJsonCode.append("'");

			if (childList != null)
			{
				extJsonCode.append(",children:");// children
				extJsonCode.append(ddExtMenuItemJsonCoreWithoutChecked(childList, menuMap));
			}
			extJsonCode.append("}");
		}
		extJsonCode.append("]");
		return extJsonCode.toString();
	}

	/**
	 * 
	 * <br>
	 * Description:根据角色id回去 菜单的信息 带checked <br>
	 * Author:任宝龙 <br>
	 * Date:2012-6-14
	 * 
	 * @param roleId
	 * @return
	 */
	public String getExtMenuTreeWithCheckByRoleId(String roleId)
	{
		List<MenuBean> list = roleMenuMap.get(roleId);

		List<MenuBean> roleMenuList = new ArrayList<MenuBean>();
		Set<MenuBean> set = new HashSet<MenuBean>();
		for (MenuBean mb : menuBeanMap.values())
		{
			mb.setChecked("false");
			set.add(mb);
		}
		if (list != null)
		{
			for (MenuBean mb : list)
			{
				mb.setChecked("true");
				set.add(mb);
			}
		}
		roleMenuList.addAll(set);

		Map<String, List<MenuBean>> menuMap = getMenuMap(roleMenuList);

		List<MenuBean> rootList = menuMap.get("0");
		String jsonCore = addExtMenuItemJsonCore(rootList, menuMap, "false");
		return jsonCore;
	}

	/**
	 * 
	 * <br>
	 * Description:添加带checked的menu属的节点 <br>
	 * Author:任宝龙 <br>
	 * Date:2012-5-28
	 * 
	 * @param list
	 * @param menuMap
	 * @param checked
	 * @return
	 */
	private String addExtMenuItemJsonCore(List<MenuBean> list, Map<String, List<MenuBean>> menuMap, String checked)
	{
		StringBuffer extJsonCore = new StringBuffer("[");
		listSort(list);
		for (MenuBean mb : list)
		{
			if (extJsonCore.length() > 1)
			{
				extJsonCore.append(",");
			}
			extJsonCore.append("\n{text:'");// text
			extJsonCore.append(mb.getMenuName());
			extJsonCore.append("(");
			extJsonCore.append(mb.getSort());

			extJsonCore.append(")',checked:"); // checked
			String checkedsStr = "true";
			if ("false".equals(checked))
			{
				checkedsStr = "true".equals(mb.getChecked()) ? "true" : "false";
			}
			extJsonCore.append(checkedsStr);

			extJsonCore.append(",leaf:"); // leaf

			List<MenuBean> childList = menuMap.get(mb.getMenuId());

			extJsonCore.append(childList == null ? "1" : "0");
			extJsonCore.append(",id:'");// id
			extJsonCore.append(mb.getMenuId() + "'");
			if (!"0".equals(mb.getParentId()))
			{
				extJsonCore.append(",parentId:'");
				extJsonCore.append(mb.getParentId());
				extJsonCore.append("'");
			}

			if (childList != null)
			{
				extJsonCore.append(",children:");// children
				extJsonCore.append(addExtMenuItemJsonCore(childList, menuMap, checked));
			}
			extJsonCore.append("}");
		}
		extJsonCore.append("]");
		return extJsonCore.toString();
	}

	/**
	 * 
	 * <br>
	 * Description:获取菜单样式代码的码 <br>
	 * Author:任宝龙 <br>
	 * Date:2012-6-14
	 * 
	 * @param user
	 * @param parentId
	 * @param maxLevel
	 * @return
	 */
	public String getMenuCode(User user, String parentId, int maxLevel)
	{
		List<MenuBean> list = getMenuList(user);
		Map<String, List<MenuBean>> menuMap = getMenuMap(list);

		if ("".equals(parentId))
		{
			parentId = "0";
		}
		list = menuMap.get(parentId);

		if (list == null)
			return "&nbsp;";
		return buildMenuCode(list, menuMap, parentId, 1, maxLevel);

	}

	/**
	 * 
	 * <br>
	 * Description:获取菜单样式代码的码 的递归方法（添加节点） <br>
	 * Author:任宝龙 <br>
	 * Date:2012-6-14
	 * 
	 * @param list
	 * @param menuMap
	 * @param parentId
	 * @param menuLevel
	 * @param maxLevel
	 * @return
	 */
	public String buildMenuCode(List<MenuBean> list, Map<String, List<MenuBean>> menuMap, String parentId, int menuLevel, int maxLevel)
	{
		listSort(list);
		StringBuffer menuCode = new StringBuffer();
		if (maxLevel != 0 && maxLevel < menuLevel)
			return "";
		menuCode.append("<span id='menu");
		menuCode.append(parentId);
		menuCode.append("_cm' class='menuDiv_");
		menuCode.append(menuLevel);
		menuCode.append("' ");
		menuCode.append("name='menu");
		menuCode.append(menuLevel);
		menuCode.append("'>");

		List<MenuBean> childMenuList;
		boolean isLeaf = true;
		for (MenuBean mb : list)
		{
			childMenuList = menuMap.get(mb.getMenuId());
			if (childMenuList != null)
			{
				isLeaf = false;
			}
			if (maxLevel == 0)
			{
				menuCode.append(getMenuItemCode(mb, isLeaf, menuLevel).toString());
			} else
			{
				menuCode.append(getNavMenuItemCode(mb, isLeaf)).toString();
			}

			if (!isLeaf)
			{
				menuCode.append(buildMenuCode(childMenuList, menuMap, mb.getMenuId(), menuLevel + 1, maxLevel));
			}
			isLeaf = true;

		}
		menuCode.append("</span>");

		return menuCode.toString();
	}

	/**
	 * 
	 * <br>
	 * Description:菜单模板 <br>
	 * Author:任宝龙 <br>
	 * Date:2012-6-12
	 * 
	 * @param menuBean
	 * @param isLeaf
	 * @param menuLevel
	 * @return
	 */
	private StringBuffer getMenuItemCode(MenuBean menuBean, boolean isLeaf, int menuLevel)
	{
		StringBuffer menuItemCode = new StringBuffer();
		String mouseOver = "red";
		String mouseOut = "black";

		if (menuLevel == 1)
		{
			mouseOver = getIconPath(menuBean.getIcon());// "#0B6DDA";//
														// "#46A3FF";
			mouseOut = menuBean.getIcon();// "#0C4B8E";
		}

		menuItemCode.append("<div class='menu_" + menuLevel);
		if (!isLeaf)
		{
			menuItemCode.append("' onclick='openMenu(\"");
			menuItemCode.append( menuBean.getMenuId());
			menuItemCode.append( "\")'");
		} else
		{
			menuItemCode.append("' onclick='openPage(\"" );
			menuItemCode.append(menuBean.getUrl_center());
			menuItemCode.append("\")'");
		}

		String iconPath = menuBean.getIcon();
		if (!isLeaf && menuLevel != 1)
		{
			iconPath = getIconPath(iconPath);
		}

		menuItemCode.append(" onmouseover=\" mouserMenuMoveOnOrOut(this,'" + mouseOver + "')\"");
		menuItemCode.append(" onmouseout=\" mouserMenuMoveOnOrOut(this,'" + mouseOut + "')\"");
		menuItemCode.append(">");
		if (menuLevel == 1)
		{
			if (iconPath != "")
				menuItemCode.append("<img id='img_" + menuBean.getMenuId() + "' class='img_" + menuLevel + "'  src='../images/menu/" + iconPath + "'/>");
			else
			{
				menuItemCode.append("<span class='worldStyle_" + menuLevel + "'>");
				menuItemCode.append(menuBean.getMenuName());
				menuItemCode.append("</span>");
			}
		} else
		{
			menuItemCode.append("<img id='img_" + menuBean.getMenuId() + "' class='img_" + menuLevel + "'  src='../images/left/" + iconPath + "'/>");
			menuItemCode.append("<span class='worldStyle_" + menuLevel + "'>");
			menuItemCode.append(menuBean.getMenuName());
			menuItemCode.append("</span>");
		}
		if (isLeaf)
		{
			menuItemCode.append("<img id='sel_" + menuBean.getUrl_center() + "' class='selectImg_" + menuLevel + "' src='../images/left/selectImg.png' />");
		}
		menuItemCode.append("</div>");
		return menuItemCode;

	}

	/**
	 * 
	 * <br>
	 * Description:导航栏模板 <br>
	 * Author:任宝龙 <br>
	 * Date:2012-6-12
	 * 
	 * @param menuBean
	 * @param isLeaf
	 * @return
	 */
	private StringBuffer getNavMenuItemCode(MenuBean menuBean, boolean isLeaf)
	{
		StringBuffer menuItemCode = new StringBuffer();
		menuItemCode.append("<span ");
		if (isLeaf)
		{
			menuItemCode.append("onclick='openPage(this,\"" + menuBean.getUrl_center() + "\")' ");
		} 
		else
		{
			menuItemCode.append("onclick='clickMenu(this,\"" + menuBean.getMenuId() + "\");return false;' ");
		}

		menuItemCode.append(" onmouseover=\"mouserNavMoveOnOrOut(this,'red',true)\" ");
		menuItemCode.append(" onmouseout=\"mouserNavMoveOnOrOut(this,'#444444',false)\" >");
		menuItemCode.append(menuBean.getMenuName());
		menuItemCode.append("</span>");

		return menuItemCode;
	}
	
	public String getMenuOACode(User user, String parentId, int maxLevel)
	{
		List<MenuBean> list = getMenuList(user);
	
		Map<String, List<MenuBean>> menuMap = getMenuMap(list);
	
		if ("".equals(parentId))
		{
			parentId = "0";
		}
		list = menuMap.get(parentId);

		if (list == null)
			return "&nbsp;";
		return buildMenuOACode(list, menuMap, parentId, 1, maxLevel);
	}
	
	private String buildMenuOACode(List<MenuBean> list, Map<String, List<MenuBean>> menuMap, String parentId, int menuLevel, int maxLevel)
	{
		listSort(list);
		StringBuffer menuCode = new StringBuffer();
		if (maxLevel != 0 && maxLevel < menuLevel)
			return "";

		menuCode.append("<span id='menu");
		menuCode.append(parentId);
		menuCode.append("_cm' class='menuDiv_");
		menuCode.append("1");
		menuCode.append("' ");
		menuCode.append("name='menu");
		menuCode.append(menuLevel);
		menuCode.append("'>");

		List<MenuBean> childMenuList;
		boolean isLeaf = true;
	
		for (MenuBean mb : list)
		{
			childMenuList = menuMap.get(mb.getMenuId());
			if (childMenuList != null)
			{
				isLeaf = false;
			}

			menuCode.append(getMenuOAItemCode(mb, isLeaf, menuLevel).toString());

			if (!isLeaf)
			{
				menuCode.append(buildMenuOACode(childMenuList, menuMap, mb.getMenuId(), menuLevel + 1, maxLevel));
			}
			isLeaf = true;
		}
		if(menuLevel==2)
		{
			menuCode.append("<div id='"+parentId+"_bottom' class='bottomline'></div>");
		}
		menuCode.append("</span>");

		return menuCode.toString();
	}

	/**
	 * 
	 * <br>
	 * Description:菜单模板 <br>
	 * Author:任宝龙 <br>
	 * Date:2012-6-12
	 * 
	 * @param menuBean
	 * @param isLeaf
	 * @param menuLevel
	 * @return
	 */
	private StringBuffer getMenuOAItemCode(MenuBean menuBean, boolean isLeaf, int menuLevel)
	{
		StringBuffer menuItemCode = new StringBuffer();
		String mouseOver = "red";
		String mouseOut = "black";

		if (menuLevel == 1)
		{
			mouseOver = getIconPath(menuBean.getIcon());// "#0B6DDA";//
			mouseOut = menuBean.getIcon();// "#0C4B8E";
		}
		menuItemCode.append("<div class='menu_" + menuLevel);
		if (!isLeaf)
		{
			menuItemCode.append("' onclick='openMenu(\"");
			menuItemCode.append( menuBean.getMenuId());
			menuItemCode.append( "\")'");
		} else
		{
			menuItemCode.append("' onclick='openPage(\"" );
			menuItemCode.append(menuBean.getUrl_center());
			menuItemCode.append("\")'");
		}

		String iconPath = menuBean.getIcon();
		if (!isLeaf && menuLevel == 2)
		{
			iconPath = getIconPath(iconPath);
		}

		menuItemCode.append(" onmouseover=\" mouserMenuMoveOnOrOut(this,'" + mouseOver + "')\"");
		menuItemCode.append(" onmouseout=\" mouserMenuMoveOnOrOut(this,'" + mouseOut + "')\"");
		menuItemCode.append(">");
		
		menuItemCode.append("<img id='img_" + menuBean.getMenuId() + "' class='img_" + menuLevel + "'  src='../images/left/" + iconPath + "'/>");
		menuItemCode.append("<span class='worldStyle_" + menuLevel + "'>");
		menuItemCode.append(menuBean.getMenuName());
		menuItemCode.append("</span>");
		if (menuLevel == 3)
		{
			menuItemCode.append("<span class='line'></span>");
		}	
		menuItemCode.append("</div>");
		return menuItemCode;
	}

	/**
	 * 
	 * <br>
	 * Description: 根据sort list排序 <br>
	 * Author:任宝龙 <br>
	 * Date:2012-6-12
	 * 
	 * @param list
	 */
	public void listSort(List<MenuBean> list)
	{
		MenuBean mbA;
		MenuBean mbB;
		for (int i = 0; i < list.size() - 1; i++)
		{
			for (int j = i + 1; j < list.size(); j++)
			{
				mbA = list.get(i);
				mbB = list.get(j);
				if (mbA.getSort() > mbB.getSort())
				{
					list.set(i, mbB);
					list.set(j, mbA);
				}
			}
		}
	}

	/**
	 * 
	 * <br>
	 * Description: 获取非叶子节点的图标路径 <br>
	 * Author:任宝龙 <br>
	 * Date:2012-6-12
	 * 
	 * @param iconPath
	 * @return
	 */
	public String getIconPath(String iconPath)
	{
		if (iconPath != null && !("".equals(iconPath)))
		{
			int index = iconPath.lastIndexOf('.');
			return iconPath.substring(0, index) + "_hc" + iconPath.substring(index);
		}
		return "";
	}

	/**
	 * 
	 * <br>
	 * Description:获取前台右边显示的页面编号 <br>
	 * Author:王雷 <br>
	 * Date:2011-5-11
	 * 
	 * @return
	 */
	public String getPerConfig()
	{
		String sql = "select t.menuid from core_menu t where t.flag='1' and t.east='1'";
		List<Map<String, Object>> list = query(sql, AbstractBaseBean.CORE);
		StringBuffer sb = new StringBuffer();
		if (list != null && list.size() > 0)
		{
			for (int i = 0; i < list.size(); i++)
			{
				sb.append(list.get(i) + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 
	 * <br>
	 * Description:获取前台中间显示的页面编号 <br>
	 * Author:王雷 <br>
	 * Date:2011-5-11
	 * 
	 * @return
	 */
	public String getTabConfig()
	{
		String sql = "select t.menuid from core_menu t where t.flag='1' and  t.center='1'";
		List<Map<String, Object>> list = query(sql, AbstractBaseBean.CORE);
		StringBuffer sb = new StringBuffer();
		if (list != null && list.size() > 0)
		{
			for (int i = 0; i < list.size(); i++)
			{
				sb.append(list.get(i) + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 
	 * <br>
	 * Description:获取前台本地显示的页面编号 <br>
	 * Author:王雷 <br>
	 * Date:2011-5-11
	 * 
	 * @return
	 */
	public String getLocalConfig()
	{
		String sql = "select t.menuid from core_menu t where t.flag='1' and t.local='1'";
		List<Map<String, Object>> list = query(sql, AbstractBaseBean.CORE);
		StringBuffer sb = new StringBuffer();
		if (list != null && list.size() > 0)
		{
			for (int i = 0; i < list.size(); i++)
			{
				sb.append(list.get(i) + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}
}
