package com.klspta.web.xiamen.wpzf.tdbgdc;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * <br>Title:土地变更调查数据管理类
 * <br>Description:实现对土地变更调查数据的删、改、查
 * <br>Author:黎春行
 * <br>Date:2013-11-18
 */
public interface ItdbgdcData {
	/**
	 * 
	 * <br>Description:获取待核查-疑似违法数据
	 * <br>Author:黎春行
	 * <br>Date:2013-11-18
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getDhcWF(String userId, String keyword);
	
	/**
	 * 
	 * <br>Description:获取待核查-合法数据
	 * <br>Author:黎春行
	 * <br>Date:2013-11-18
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getDhcHF(String userId, String keyword);

	
	/**
	 * 
	 * <br>Description:获取列表
	 * <br>Author:黎春行
	 * <br>Date:2013-11-18
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getList(String where);
	
	
	
	public List<Map<String, Object>> getWFList(String where);
	
	/**
	 * 
	 * <br>Description:添加现场核查情况
	 * <br>Author:黎春行
	 * <br>Date:2013-11-18
	 * @param yw_guid
	 * @param value
	 * @return
	 */
	public String setxchcqk(String yw_guid, String value);
	
	/**
	 * 
	 * <br>Description:添加处理情况
	 * <br>Author:黎春行
	 * <br>Date:2013-11-18
	 * @param yw_guid
	 * @param value
	 * @return
	 */
	public String setClqk(String yw_guid, String value);
	
	/**
	 * 
	 * <br>Description:修改压盖分析情况
	 * <br>Author:黎春行
	 * <br>Date:2013-11-19
	 * @param yw_guid
	 * @param value
	 * @return
	 */
	public String changeFxqk(String yw_guid, String value);
	
	public List<Map<String, Object>> getBGList(String where);

}
