package com.klspta.console.menu;

import javax.servlet.http.HttpServletRequest;

import com.klspta.base.AbstractBaseBean;
import com.klspta.console.ManagerFactory;

public class MenuAction extends AbstractBaseBean {
	/**
	 * 
	 * <br>
	 * Description:添加和修改菜单接口 <br>
	 * Author:任宝龙 <br>
	 * Date:2012-8-7
	 */
	public void save() {

		try {
			MenuBean menuBean = getMenuBeanByRequest(request);
			MenuBean oldMenuBean = ManagerFactory.getMenuManager()
					.getMenuBeanByMenuId(menuBean.getMenuId());

			if (oldMenuBean == null) {
				// 添加menuBean
				ManagerFactory.getMenuManager().add(menuBean);
			} else {
				// /更新menuBean
				ManagerFactory.getMenuManager().save(menuBean);
			}
			response.getWriter().write("{success:true}");
		} catch (Exception e) {
			responseException(this, "save", "200002", e);
		}

	}

	/**
	 * 
	 * <br>
	 * Description:删除菜单 <br>
	 * Author:任宝龙 <br>
	 * Date:2012-8-7
	 */
	public void deleteMenu() {
		MenuBean menuBean = new MenuBean();
		try {
			menuBean.setMenuId(request.getParameter("treeId"));
			ManagerFactory.getMenuManager().delete(menuBean);
			response.getWriter().write("{success:true}");
		} catch (Exception e) {
			responseException(this, "save", "200002", e);
		}
	}

	/**
	 * 
	 * <br>
	 * Description:保存授权 <br>
	 * Author:任宝龙 <br>
	 * Date:2012-8-7
	 */
	public void saveMenuAuthor() {
		String roleId = request.getParameter("roleId");
		String treeIdList = request.getParameter("treeIdList");
		String[] menuIdList = treeIdList.split(",");
		try {
			ManagerFactory.getMenuManager().saveMenuRole(roleId, menuIdList);
			response("success");
		} catch (Exception e1) {
			responseException(this, "save", "200002", e1);
		}

	}

	/**
	 * 
	 * <br>
	 * Description:封装menuBean <br>
	 * Author:任宝龙 <br>
	 * Date:2012-8-7
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private MenuBean getMenuBeanByRequest(HttpServletRequest request)
			throws Exception {
		MenuBean mb = new MenuBean();
		mb.setMenuId(request.getParameter("id"));
		mb.setParentId(request.getParameter("parentId"));
		String menuType = "";
		if ("0".equals(mb.getParentId()))
			menuType = "1";
		else {
			menuType = ManagerFactory.getMenuManager().getMenuBeanByMenuId(
					mb.getParentId()).getMenuType();
			menuType = ((Integer.parseInt(menuType)) + 1) + "";
		}
		mb.setMenuType(menuType);
		mb.setMenuName(request.getParameter("menuName"));
		mb.setSort(Integer.parseInt(request.getParameter("sort")));
		String iconPath = "";
		try {
			iconPath = new String(request.getParameter("icon").getBytes(
					"ISO-8859-1"), "utf-8");

			if (iconPath != "" && iconPath.indexOf("\\") != -1) {
				mb.setIcon(iconPath.substring(iconPath.lastIndexOf("\\") + 1));
			} else {
				mb.setIcon(iconPath);
			}
		} catch (RuntimeException e) {
			responseException(this, "save", "200002", e);
		}

		mb.setUrl_center(request.getParameter("url_center"));
		mb.setUrl_east(request.getParameter("url_east"));
		mb.setUrl_local(request.getParameter("url_local"));
		mb.setHandler(request.getParameter("handler"));
		return mb;
	}

}
