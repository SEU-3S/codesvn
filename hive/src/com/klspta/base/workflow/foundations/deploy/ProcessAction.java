package com.klspta.base.workflow.foundations.deploy;

import java.io.File;
import java.io.IOException;
import java.util.List;
import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.base.workflow.foundations.IWorkflowOp;
import com.klspta.base.workflow.foundations.WorkflowOp;


public class ProcessAction extends AbstractBaseBean {

	/**
	 * <br>
	 * Title:上传工作流ZIP文件 <br>
	 * Description: <br>
	 * Author:王峰 <br>
	 * Date:2011-6-30
	 */
	public void  uploadFile(){
		File delFile = null;
		File newFolder = null;
		String filePath="";
		try {
			List<String> list;
			list = UtilFactory.getFileUtil().upload(request, 0, 0);
			filePath = list.get(0);
			String n = filePath.substring(0, filePath.lastIndexOf("."));
			newFolder = new File(n);
			String m=n.substring(0,filePath.lastIndexOf("//"+newFolder.getName()));
			System.out.print(m);
			delFile=new File(m);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		IWorkflowOp work = WorkflowOp.getInstance();
		try {
			work.deploy(filePath.replaceAll("//", "/"));
			response.getWriter().write("{success:true,info:'上传成功！'}");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.getWriter().write(
						"{success:false,info:'" + e.getMessage() + "'}");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			if(delFile!=null)
			DelFile.getInstance().deleteAllFile(delFile);
		}
	}

	/**
	 * <br>
	 * Title:根据deploymentId删除工作流信息 <br>
	 * Description: <br>
	 * Author:王峰 <br>
	 * Date:2011-6-30
	 */
	public void delProcess(){
		IWorkflowOp work = WorkflowOp.getInstance();
		String id = request.getParameter("deploymentId");
		try {
			work.delete(id);
			response.getWriter().write("true");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.getWriter().write(e.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
	}

}
