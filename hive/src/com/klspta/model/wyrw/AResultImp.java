package com.klspta.model.wyrw;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

/**
 * 
 * <br>Title:抽象类,动态巡查核查成果导入导出管理类
 * <br>Description: 用来实现和平板的交互，动态实现巡查核查成果的导入和导出
 * <br>Author:黎春行
 * <br>Date:2013-12-2
 */
public abstract class AResultImp extends AbstractBaseBean {
	
	public String tempPath;
	public String userid;
	
	public AResultImp(HttpServletRequest request) {
		tempPath = getTemp();
		this.userid = request.getParameter("userid");
	}

	public abstract void saveData();
	
	public abstract void expResult();
	
    /**
     * 
     * <br>
     * Description:获取临时路径 <br>
     * Author:陈强峰 <br>
     * Date:2012-2-24
     * 
     * @return
     */
    private String getTemp() {
        String temppath = "";
        temppath = UtilFactory.getConfigUtil().getShapefileTempPathFloder();
        return temppath;
    }
    
    /**
     * 
     * <br>Description:临时文件
     * <br>Author:陈强峰
     * <br>Date:2012-7-4
     * @return
     */
    public String tempFile() {
        try {
            List<String> list = UtilFactory.getFileUtil().upload(request, 0, 0);
            return list.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 
     * <br>Description:删除附件信息
     * <br>Author:陈强峰
     * <br>Date:2012-7-29
     * @param rwbh
     */
    public void delAcc(String rwbh) {
        String sql = "select file_id,file_path from atta_accessory where yw_guid=? ";
        List<Map<String, Object>> list = query(sql, CORE, new Object[] { rwbh });
        for (int i = 0; i < list.size(); i++) {
            String ftpFileName = list.get(i).get("file_path").toString();
            String fileId = list.get(i).get("file_id").toString();
            UtilFactory.getFtpUtil().deleteFile(ftpFileName);
            sql = "delete from atta_accessory where file_id=?";
            update(sql, CORE, new Object[] { fileId });
        }
    }

}
