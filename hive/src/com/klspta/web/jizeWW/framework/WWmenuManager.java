package com.klspta.web.jizeWW.framework;

import java.util.List;
import java.util.Map;
import com.klspta.base.AbstractBaseBean;
import com.klspta.console.ManagerFactory;
import com.klspta.console.menu.MenuBean;
import com.klspta.console.user.User;

public class WWmenuManager extends AbstractBaseBean{
	
	/**
	 * Description:获取菜单样式 <br>
	 * Author:姚建林 <br>
	 * Date:2013-9-17
	 * 
	 * @param user
	 * @param parentId
	 * @param maxLevel，当maxLevel为1时，获取导航栏1级菜单；当maxLevel为0时，获取左侧2、3级菜单
	 * @return
	 * @throws Exception 
	 */
	public String getWWMenuCode(User user, String parentId, int maxLevel) throws Exception
	{
		List<MenuBean> list = ManagerFactory.getMenuManager().getMenuList(user);
		Map<String, List<MenuBean>> menuMap = ManagerFactory.getMenuManager().getMenuMap(list);

		if ("".equals(parentId)){
			parentId = "0";
		}
		list = menuMap.get(parentId);

		if (list == null){
			return "&nbsp;";
		}
		return buildWWMenuCode(list, menuMap, parentId, 1, maxLevel);
	}

	/**
	 * Description:获取菜单样式 递归方法（添加节点） <br>
	 * Author:姚建林 <br>
	 * Date:2013-9-17
	 * 
	 * @param list
	 * @param menuMap
	 * @param parentId
	 * @param menuLevel
	 * @param maxLevel
	 * @return
	 * @throws Exception 
	 */
	private String buildWWMenuCode(List<MenuBean> list, Map<String, List<MenuBean>> menuMap, String parentId, int menuLevel, int maxLevel) throws Exception
	{
		ManagerFactory.getMenuManager().listSort(list);
		StringBuffer menuCode = new StringBuffer();
		if (maxLevel != 0 && maxLevel < menuLevel){
			return "";
		}

		List<MenuBean> childMenuList;
		boolean isLeaf = true;
		for (MenuBean mb : list){
			childMenuList = menuMap.get(mb.getMenuId());
			if (childMenuList != null){
				isLeaf = false;
			}
			if (maxLevel == 0){
				menuCode.append(getWWMenuItemCode(mb, isLeaf, menuLevel).toString());
			} else {
				menuCode.append(getWWNavMenuItemCode(mb, isLeaf)).toString();
			}

			if (!isLeaf){
				menuCode.append(ManagerFactory.getMenuManager().buildMenuCode(childMenuList, menuMap, mb.getMenuId(), menuLevel + 1, maxLevel));
			}
			isLeaf = true;
		}

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
	 * @throws Exception 
	 */
	private StringBuffer getWWMenuItemCode(MenuBean menuBean, boolean isLeaf, int menuLevel) throws Exception
	{
		StringBuffer menuItemCode = new StringBuffer();

		menuItemCode.append("<li class='menu_" + menuLevel);
		menuItemCode.append("' onclick='openPage(\"" );
		menuItemCode.append(menuBean.getUrl_center());
		menuItemCode.append("\")'>");
		
		menuItemCode.append("<img class=\"menuicon\" src=\"../framework/images/menu/");
		menuItemCode.append(menuBean.getIcon());
		menuItemCode.append("\" />&nbsp;&nbsp;");
		menuItemCode.append("<span class=\"childmenutitle\">");
		menuItemCode.append(menuBean.getMenuName());
		menuItemCode.append("</span>");
		
		menuItemCode.append("</li>");
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
	private StringBuffer getWWNavMenuItemCode(MenuBean menuBean, boolean isLeaf)
	{
		StringBuffer menuItemCode = new StringBuffer();
		menuItemCode.append("<li ");
		if (isLeaf){
			menuItemCode.append("onclick='openPage(\"" + menuBean.getUrl_center() + "\")' >");
		}else{
			menuItemCode.append("onclick='clickMenu(this,\"" + menuBean.getMenuId() + "\");return false;' >");
		}
		
		menuItemCode.append("<img class=\"menuseparate\" src=\"../images/menu/split.png\" />&nbsp;");
		menuItemCode.append("<img class=\"menuicon\" src=\"../images/menu/");
		menuItemCode.append(menuBean.getIcon());
		menuItemCode.append("\" />&nbsp;&nbsp;");
		menuItemCode.append("<span class=\"menutitle\">");
		menuItemCode.append(menuBean.getMenuName());
		menuItemCode.append("</span>");
		menuItemCode.append("</li>");
		
		return menuItemCode;
	}
	
	/**
	 * 
	 * <br>Description:获取二级菜单
	 * <br>Author:王雷
	 * <br>Date:2013-9-30
	 */
	public void getChildMenu(){
	    String userid = request.getParameter("userid");
	    User user = null ;
	    String parentId = "";
	    String message = "";
	    try {
	        user = ManagerFactory.getUserManager().getUserWithId(userid);
	        parentId = request.getParameter("parentMenuId");        
	        message = getWWMenuCode(user,parentId,0);  
	        response(message);
        } catch (Exception e) {
            e.printStackTrace();
        }	    
	}
}
