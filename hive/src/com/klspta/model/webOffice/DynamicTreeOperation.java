package com.klspta.model.webOffice;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.model.accessory.AccessoryUtil;

public class DynamicTreeOperation extends AbstractBaseBean {
	
	private static DynamicTreeOperation dynamicTree;
	String insertSQL = "insert into ATTA_ACCESSORY (FILE_ID,FILE_NAME,FILE_BH,FILE_YEAR,FILE_TYPE,YW_GUID,USERID) values(?,?,?,?,'file',?,?)"; 
	String deleteSQL = "delete from ATTA_ACCESSORY t where t.yw_guid=? and t.file_id=?";
	String selectSQL = "select t.file_id,t.file_name,t.file_path from ATTA_ACCESSORY t where t.yw_guid=? and t.file_name like'%.doc'  and t.parent_file_id = '0'"; 
	String selectFileNameSQL = "select * from ATTA_ACCESSORY t where t.yw_guid=? and t.file_name like ?";
	String updateNameSQL = "update ATTA_ACCESSORY t  set t.file_name = ? where t.file_id=? ";
	String sql="delete from ATTA_ACCESSORY t where  t.file_id=?";
	String updateSQL = "update ATTA_ACCESSORY t set t.file_bh=? ,t.file_year=? where file_id=?";
	/**
	 * Description:获取实例 <br>
	 * Author:李如意 <br>
	 * Date:2011-11-09
	 * @return
	 */
	public static DynamicTreeOperation getInstance(){	
		if(dynamicTree == null){
			dynamicTree = new DynamicTreeOperation();	
		}
		return dynamicTree;
	}
	
	/**
	 * 
	 * <br>Description: 获取动态树
	 * <br>Author:李如意
	 * <br>DateTime:2012-8-23 下午07:28:00
	 * @param docmap
	 * @return
	 */
	public String getDynamicTree(HashMap docmap) {
		String docName = (String)docmap.get("docName");
		String yw_guid = (String)docmap.get("yw_guid");
		Map map;
		String children="{text: '文书管理',leaf:0,id:'4',type:'dynamic',src :'model/webOffice/weboffice_init.jsp',children:[";
		if(yw_guid != null ){
			List list = query(selectSQL,AbstractBaseBean.CORE, new Object[] {yw_guid});
			for(int i=0;i<list.size();i++){
				map = (Map) list.get(i);
				String file_id = (String) map.get("file_id");
				String docname = (String) map.get("file_name");
				children+="{text:'"+docname+"',leaf:1,id:'"+file_id+"',filter:false,src:'model/webOffice/webOffice_read.jsp?moreFeatures=true&file_id="+file_id+"&file_type=.doc'}";
				if(i!=(list.size()-1)){
					children+=",";
				}
			}
		}
		children+="]}";
		return children;
	}

	public void saveDoc(){
			String docName = UtilFactory.getStrUtil().unescape(request.getParameter("docName"));//中文解码
			String fileid = request.getParameter("file_id");
			String yw_guid = request.getParameter("yw_guid");
			String username = request.getParameter("username");	
			List list = query(selectFileNameSQL,AbstractBaseBean.CORE, new Object[]{yw_guid,"%"+docName+"%"}); 
			response((list.size()+1)+",true");
	}
	
	/**
	 * <br>Description: 文书关闭及删除处理，如果文书直接关闭时，则直接删除临时文件夹下的文件，如果直接进行删除操作，则同步删除ftp服务器中对应的文件
	 * <br>Author:李如意
	 * <br>DateTime:2012-12-6 下午05:17:04
	 * @throws Exception
	 */
	public void deleteDoc() throws Exception {
			String yw_guid = request.getParameter("yw_guid");
			String file_id = request.getParameter("file_id");
			String ftpFileTempPath = request.getParameter("ftpFileTempPath");
			String deletedoc = request.getParameter("deletedoc");
			int i = update(deleteSQL, AbstractBaseBean.CORE, new Object[]{yw_guid,file_id});
			File file = new File(ftpFileTempPath); // 删除临时文件夹下的文件;如果文件路径对应的文件存在，并且是一个文件，则直接删除。
			if (file.exists() && file.isFile()) {
				if (file.delete()) {
					System.out.println("文件：" + file_id + "删除成功！");
				} else {
					System.out.println("文件" + file_id + "删除失败！");
				}
			} 
			if("true".equals(deletedoc)){
				//如果在读取的状态关闭时，则直接删除临时文件夹下的文件，如果读取的状态删除时，则同步删除ftp服务器上的文件
				UtilFactory.getFtpUtil().deleteFile(file_id+".doc");
			}
			response("true");
	}
	
	public void saveBH(){
		String file_bh = request.getParameter("num");
		String year = request.getParameter("year");
		String file_id = request.getParameter("file_id");
		update(updateSQL , CORE,new Object[]{file_bh,year,file_id});
		response("true");
	}	
}
