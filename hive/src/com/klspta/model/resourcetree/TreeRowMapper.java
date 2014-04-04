package com.klspta.model.resourcetree;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.lob.LobHandler;

/**
 * 
 * <br>Title:将spring jdbcTemplate的查询结果集转换为Object
 * <br>Description:
 * <br>Author:李亚栓
 * <br>Date:2012-8-6
 */
public class TreeRowMapper implements RowMapper<Object> {

    @Override
    public TreeBean mapRow(ResultSet rs, int arg1) throws SQLException {
        TreeBean treeBean=new TreeBean();
        LobHandler lobHandler=(LobHandler)getAp().getBean("oracleLobHandler");
        treeBean.setContent(InputStream2String.transfer(lobHandler.getBlobAsBinaryStream(rs, "content")));
        treeBean.setDescription(rs.getString("description"));
        treeBean.setTree_id(rs.getString("tree_id"));
        treeBean.setTree_name(rs.getString("tree_name"));
        treeBean.setTree_type(rs.getString("tree_type"));
        return treeBean;
    }
    public FileSystemXmlApplicationContext getAp() {
        String classpath="file:"+Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String URI = classpath.substring(0, classpath.lastIndexOf("classes/"))+"conf/applicationContext-base.xml";
        FileSystemXmlApplicationContext ac;
        ac = new FileSystemXmlApplicationContext(URI);
        return ac;
    } 

}
