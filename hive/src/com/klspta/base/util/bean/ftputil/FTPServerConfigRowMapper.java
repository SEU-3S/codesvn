package com.klspta.base.util.bean.ftputil;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FTPServerConfigRowMapper implements RowMapper<Object> {

    @Override
    public FTPServerConfigBean mapRow(ResultSet rs, int i) throws SQLException {
        FTPServerConfigBean bean = new FTPServerConfigBean();
        bean.setFtp_description(rs.getString("ftp_description"));
        bean.setFtp_host(rs.getString("ftp_host"));
        bean.setFtp_id(rs.getString("ftp_id"));
        bean.setFtp_password(rs.getString("ftp_password"));
        bean.setFtp_port(rs.getString("ftp_port"));
        bean.setFtp_username(rs.getString("ftp_username"));
        return bean;
    }

}
