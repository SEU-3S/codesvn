package com.klspta.model.wyrw;

import org.springframework.stereotype.Component;

import com.klspta.base.AbstractBaseBean;
import com.klspta.web.xiamen.xchc.importwycg.XmResultImp;

/**
 * 
 * <br>Title:任务操作入口
 * <br>Description:
 * <br>Author:陈强峰
 * <br>Date:2012-8-14
 */
@Component
public class WyrwManager extends AbstractBaseBean {

	/**
	 * 添加项目标志位，不同项目调用不同方法
	 */
	public static final int XM_XIAMEN = 1;
	
	
    public WyrwManager() {
    }

    /**
     * 
     * <br>Description:导出成果
     * <br>Author:陈强峰
     * <br>Date:2012-8-14
     */
    public void downResult() {
        ResultExp re = new ResultExp();
        re.request = this.request;
        re.response = this.response;
        re.expResult();
    }

    /**
     * 
     * <br>Description:成果导入（成果回传）
     * <br>Author:陈强峰
     * <br>Date:2012-8-14
     */
    public void uploadResult() {
        AResultImp rs;
        String type = request.getParameter("type");
    	if(type == null || type.equals("")){
    		type = "1";
    	}
    	switch (Integer.parseInt(type)) {
		case XM_XIAMEN:
			rs = new XmResultImp(request);
			break;
		default:
			rs = new ResultImp(request);
			break;
		}
        rs.request = this.request;
        rs.response = this.response;
        rs.saveData();
    }
}
