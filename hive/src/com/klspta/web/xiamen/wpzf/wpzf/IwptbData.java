package com.klspta.web.xiamen.wpzf.wpzf;

import java.util.List;
import java.util.Map;

/**
 * 
 * <br>Title:年度卫片执法卫片管理类
 * <br>Description:TODO 类功能描述
 * <br>Author:黎春行
 * <br>Date:2013-11-20
 */
public interface IwptbData {
	
	/**
	 * 
	 * <br>Description:获取卫片中土地变更调查为违法的数据
	 * <br>Author:黎春行
	 * <br>Date:2013-11-21
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getWFlist(String userId, String keyword);
	
	/**
	 * 
	 * <br>Description:获取卫片中土地变更调查为合法的数据
	 * <br>Author:黎春行
	 * <br>Date:2013-11-21
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getHFlist(String userId, String keyword);
	
	/**
	 * 
	 * <br>Description:获取卫片中土地变更调查为未处理的数据
	 * <br>Author:黎春行
	 * <br>Date:2013-11-21
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getWCLlist(String userId, String keyword);
	
	
	public List<Map<String,Object>> getWpxf(String keyword);
	public List<Map<String,Object>> getWpsb(String keyword);
}
