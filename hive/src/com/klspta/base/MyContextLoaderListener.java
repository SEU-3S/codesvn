package com.klspta.base;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.web.context.ContextLoaderListener;

import com.klspta.base.datasource.XMLContext;
import com.klspta.base.util.UtilFactory;

public class MyContextLoaderListener extends ContextLoaderListener{
    static{
        String s = UtilFactory.getConfigUtil().getApppath();
        String jdbc = "applicationContext-jdbc.xml";
        String data = "localdatabase.properties";
        String jdbcFilePath = s + "com/klspta/base/datasource/";
        System.out.println("build database properties");
        File jdbcFile = new File(jdbcFilePath + jdbc);
        File dataFile = new File(jdbcFilePath + data);
        
        String driver = UtilFactory.getConfigUtil().getConfig("jdbc.driverClassName");
        String ulr = UtilFactory.getConfigUtil().getConfig("jdbc.url");
        String username =UtilFactory.getConfigUtil().getConfig("jdbc.username");
        String pwd = UtilFactory.getConfigUtil().getConfig("jdbc.password");
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs =null;
        FileWriter fw = null;
        FileWriter fww = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(ulr, username, pwd);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from core_datasource"); 
            fw = new FileWriter(jdbcFile);  
            fww =new FileWriter(dataFile);  
            String head = XMLContext.head1.replaceAll("#JDBCFILEPATH", jdbcFilePath);
            fw.write(head);
            String temp = "";  
            while(rs.next()){
                fw.write(XMLContext.bean1.replaceAll("#Name", rs.getString("sourcename").toUpperCase()));
                fw.write(XMLContext.bean2.replaceAll("#Name", rs.getString("sourcename").toUpperCase()));
                temp = XMLContext.jdbc;
                temp = temp.replaceAll("#Name", rs.getString("sourcename").toUpperCase());
                temp = temp.replaceAll("#ClassName", rs.getString("driverClassName"));
                temp = temp.replaceAll("#url", rs.getString("url"));
                temp = temp.replaceAll("#username", rs.getString("username"));
                String password = rs.getString("password");
                if(password==null){
                    password = "";
                }
                temp = temp.replaceAll("#password", password);
                fww.write(temp);
            }
            if(UtilFactory.getConfigUtil().getConfig("USERWORKFLOW").equals("true")){
                fw.write(XMLContext.workflow);
            }else{
                System.out.println("工作流模块未加载（config.properties中USERWORKFLOW为关闭状态）。");
            }
            fw.write(XMLContext.head2);
        }catch (IOException e) {  
            e.printStackTrace();  
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if(fw!=null){
                    fw.close();  
                }
                if(fww!=null){
                    fww.close();  
                }
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    

}
