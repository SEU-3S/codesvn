package com.klspta.base.util.bean.ftputil;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AccessoryRowMapper implements RowMapper<AccessoryBean> {

    @Override
    public AccessoryBean mapRow(ResultSet rs, int i) throws SQLException {
        AccessoryBean bean=new AccessoryBean();
        bean.setFile_id(rs.getString("file_id"));
        bean.setFile_name(rs.getString("file_name"));
        bean.setFile_path(rs.getString("file_path"));
        bean.setFile_type(rs.getString("file_type"));
        bean.setParent_file_id(rs.getString("parent_file_id"));
        bean.setYw_guid(rs.getString("yw_guid"));
        bean.setUser_id(rs.getString("user_id"));
        return bean;
    }

}
