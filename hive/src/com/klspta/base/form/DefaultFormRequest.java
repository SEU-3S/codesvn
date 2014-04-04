package com.klspta.base.form;

import org.springframework.stereotype.Component;

/**
 * 
 * <br>
 * Title:表单操作默认实现类 <br>
 * Description:表单操作默认实现类 <br>
 * Author:王瑛博 <br>
 * Date:2011-7-19
 */
@Component
public class DefaultFormRequest extends AbsDefaultFormRequest {

	/**
	 * 
	 * <br>
	 * Description:调用默认的保存实现方法，如果默认的不能满足要求，可自行实现 <br>
	 * Author:王瑛博 <br>
	 * Date:2011-7-19
	 * 
	 * @see com.klspta.common.form.AbsDefaultFormRequest#save()
	 */
	public String save() throws Exception {
		return AbsDefaultFormRequest.USE_DEFAULT_SAVE_IMPL;
	}

	/**
	 * 
	 * <br>
	 * Description:调用默认的数据查询实现方法，如果默认的不能满足要求，可自行实现 <br>
	 * Author:王瑛博 <br>
	 * Date:2011-7-19
	 * 
	 * @see com.klspta.common.form.AbsDefaultFormRequest#query()
	 */
	public String query() throws Exception {
		return AbsDefaultFormRequest.USE_DEFAULT_QUERY_IMPL;
	}
}