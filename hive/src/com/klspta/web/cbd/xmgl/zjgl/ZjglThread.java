package com.klspta.web.cbd.xmgl.zjgl;

import java.util.List;
import java.util.Map;

/*******************************************************************************
 * 
 * <br>
 * Title:资金管理 <br>
 * Description:资金管理线程处理类 <br>
 * Author:朱波海 <br>
 * Date:2013-12-26
 */
public class ZjglThread implements Runnable {

	private String yw_guid = "";
	private String type = "";
	private String year = "";
	private String editor = "";
	private String rolename = "";
	private StringBuffer buffer = new StringBuffer();

	public ZjglThread(String yw_guid, String type, String year,String rolename) {
		this.yw_guid = yw_guid;
		this.type = type;
		this.year = year;
		this.rolename = rolename;
	}

	public ZjglThread(String yw_guid,  String year,String type, String editor,String rolename) {
		this.yw_guid = yw_guid;
		this.type = type;
		this.year = year;
		this.editor = editor;
		this.rolename = rolename;
	}

	@Override
	public void run() {
		TreeManager Manager = new TreeManager();
		List<Map<String, Object>> zc_zjzc = Manager.getZC_tree(this.yw_guid,
				type, this.year);
		if (this.editor.equals("y")) {
			this.buffer = TrFactory.getmodel_editor(zc_zjzc, this.yw_guid, type,
					this.year,this.rolename);
		} else {
			this.buffer = TrFactory.getmodel_view(zc_zjzc, this.yw_guid, type,
					this.year,this.rolename);
		}
	}

	public StringBuffer getBuffer() {
		return this.buffer;
	}

}
