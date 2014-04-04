package com.klspta.web.sanya.importSign;

/**
 * 
 * <br>Title:电子签章管理类
 * <br>Description:类功能描述
 * <br>Author:黎春行
 * <br>Date:2013-3-25
 */
public interface ISignManager {

	/**
	 * 
	 * <br>Description:添加签名
	 * <br>Author:黎春行
	 * <br>Date:2013-3-22
	 * @param filePath
	 * @param userId
	 */
	public abstract String setSign(String filePath, String userId);

	/**
	 * 
	 * <br>Description:获取人员签名
	 * <br>Author:黎春行
	 * <br>Date:2013-3-22
	 */
	public abstract String getSign(String userId, String filePath);

}