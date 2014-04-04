package com.klspta.web.xiamen.dxgl;

import java.util.List;
import java.util.Map;

/**
 * 
 * <br>Title:TODO 短信数据查询操作接口
 * <br>Description:TODO 实现对短信数据的查询
 * <br>Author:姚建林
 * <br>Date:2014-1-5
 */
public interface IdxData {

	/**
	 * 
	 * <br>Description:TODO 根据条件查询所有数据
	 * <br>Author:姚建林
	 * <br>Date:2014-1-5
	 * @param where 查询条件
	 * @return
	 */
	public List<Map<String, Object>> getAllList (String where);
	
	/**
	 * 
	 * <br>Description:TODO 根据条件和userId查询数据
	 * <br>Author:姚建林
	 * <br>Date:2014-1-5
	 * @param userId 数据所有人Id
	 * @param where 查询条件
	 * @return
	 */
	public List<Map<String, Object>> getOwnerList (String userId,String where);
}
