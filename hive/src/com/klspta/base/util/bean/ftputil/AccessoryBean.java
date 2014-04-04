package com.klspta.base.util.bean.ftputil;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;


/**
 * 
 * <br>Title:电子附件基本信息Bean
 * <br>Description:
 * <br>Author:郭
 * <br>Date:2010-11-22
 */
public class AccessoryBean {
    /**
     * 文件编号
     */
    String file_id;

    /**
     * 父文件编号
     */
    String parent_file_id;

    /**
     * 文件类型
     */
    String file_type;

    /**
     * 文件名称
     */
    String file_name;

    /**
     * 保存路径
     */
    String file_path;

    /**
     * 业务主键（与业务关联时使用）
     */
    String yw_guid;

    /**
     * 操作人员编号
     */
    String user_id;

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public String getParent_file_id() {
        return parent_file_id;
    }

    public void setParent_file_id(String parent_file_id) {
        this.parent_file_id = parent_file_id;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getYw_guid() {
        return yw_guid;
    }

    public void setYw_guid(String yw_guid) {
        this.yw_guid = yw_guid;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    
    public static String transfer(List<AccessoryBean> list) {
        List<List<String>> allRows = new ArrayList<List<String>>();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                List <String>oneRow = new ArrayList<String>();
                oneRow.add(list.get(i).getFile_id());
                oneRow.add(list.get(i).getParent_file_id());
                oneRow.add(list.get(i).getFile_name());
                oneRow.add(list.get(i).getFile_path());
                oneRow.add(list.get(i).getFile_type());
                allRows.add(oneRow);
            }
            String tree = JSONArray.fromObject(allRows).toString();
            tree = tree.substring(1, tree.length() - 1);
            return tree;
        } else {
            return "";
        }

    }
}
