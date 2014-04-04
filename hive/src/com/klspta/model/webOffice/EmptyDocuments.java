package com.klspta.model.webOffice;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

/**
 * <br>Description:循环读取空白文档
 * <br>@author: 李如意
 * <br>Date: 2011-07-04
 */
public class EmptyDocuments {

private static EmptyDocuments emptyDocuments = null; 

	public static EmptyDocuments getInstance(){
		if(emptyDocuments == null){
			return new EmptyDocuments();		
		}
		return emptyDocuments;
	}

	public File[] getAllFiles(){
		File[] files=null;
		String classPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath(); 
        String filepath = classPath.substring(0, classPath.lastIndexOf("WEB-INF/classes"));
        File file = new File(filepath + "model/webOffice/documents/emptyDocuments");   
        if (file.isDirectory()) {
            files = file.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    if (name.toLowerCase().endsWith(".doc")) {
                        return true;
                    }
                    return false;
                }
            });
        }
		return files;
	}
		
	public String getJsonList() {
		List list=new ArrayList();
		//循环截取文档名称的序列号以及后缀名 
		for(int i = 0; i < EmptyDocuments.getInstance().getAllFiles().length;i++){
			list.add(EmptyDocuments.getInstance().getAllFiles()[i].getName().substring(2,EmptyDocuments.getInstance().getAllFiles()[i].getName().length()-4 ));
		}		
		List allRows = new ArrayList();		
		for (int i = 0; i < list.size();i++) {
			List oneRow = new ArrayList();
			oneRow.add(i+1);
			oneRow.add(list.get(i));
			oneRow.add(i);
			allRows.add(oneRow);	
		}
		return JSONArray.fromObject(allRows).toString();
	}
	
	public List getJsonList2() {
		List list=new ArrayList();
		//循环截取文档名称的序列号以及后缀名 
		for(int i = 0; i < EmptyDocuments.getInstance().getAllFiles().length;i++){
			list.add(EmptyDocuments.getInstance().getAllFiles()[i].getName().substring(0,EmptyDocuments.getInstance().getAllFiles()[i].getName().length()-4 ));
		}		
		return list;
	}
}
