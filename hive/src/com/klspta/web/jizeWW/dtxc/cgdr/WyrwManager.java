package com.klspta.web.jizeWW.dtxc.cgdr;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.klspta.base.AbstractBaseBean;

/**
 * 
 * <br>Title:任务操作入口
 * <br>Description:
 * <br>Author:陈强峰
 * <br>Date:2012-8-14
 */
@Component
public class WyrwManager extends AbstractBaseBean {

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
        ResultImp rs = new ResultImp();
        rs.request = this.request;
        rs.response = this.response;
        rs.saveData();
    }
    
    /**
     * 
     * <br>Description:TODO 方法功能描述
     * <br>Author:王雷
     * <br>Date:2013-9-17
     */
    public void getSimInfo(){
    	String simInfo = request.getParameter("simInfo");
        if(simInfo!=null && !"".equals(simInfo)){
        	String[] simArray = simInfo.split("@");
        	StringBuffer yw_guidBuffer = new StringBuffer();
        	yw_guidBuffer.append("(");
        	for(int i = 0; i < simArray.length; i++){
        		yw_guidBuffer.append("'").append(simArray[i]).append("',");
        	}
        	yw_guidBuffer.deleteCharAt(yw_guidBuffer.length()-1);
        	yw_guidBuffer.append(")");
        	String simsql = "select t.yw_guid, t.yddw, replace(replace(substr(t.ydsj, 1, instr(t.ydsj, '日') -1),'年','-'),'月','-') ydsj,to_char(to_date(t.hcrq,'yyyy-MM-dd hh24:mi;ss'),'yyyy-MM-dd') hcrq, t.jsqk, t.mj from DC_YDQKDCB t where t.yw_guid in " + yw_guidBuffer.toString();
        	List<Map<String, Object>> simList = query(simsql, YW);
        	if(simList!=null && simList.size()>0){
            	for(Map<String,Object> map:simList){
            	    if(map.get("YDSJ")==null){
            	        map.put("YDSJ", "");
            	    }
            	}
        	}
        	response(simList);
    	}
    }
    
    public static void main(String[] args){
        String aaa="WP120130806160134_4@XC120130806155320@";
        String[] bbb=aaa.split("@");
        for(String ccc:bbb){
            System.out.println(ccc);
        }
        System.out.println(bbb.length);
        
        String a="yw_guid";
        System.out.println(a.toUpperCase());
    }
}
