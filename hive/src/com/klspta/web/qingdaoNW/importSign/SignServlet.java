package com.klspta.web.qingdaoNW.importSign;



import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.klspta.base.util.UtilFactory;
//http://127.0.0.1:8080/reduce/signservice?userId=6b283e8593b6e256ba402baef46d14dd
public class SignServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String userId = req.getParameter("userId");
		Connection conn = getConnection();
		try {
			Statement stmt = conn.createStatement();
			String sql = "select * from core_users where id='" + userId + "'";
			ResultSet resultSet = stmt.executeQuery(sql);
			if(resultSet.next()){
				Blob blob = (Blob)resultSet.getBlob("sign");
				InputStream signStream = blob.getBinaryStream();
				long nLen = blob.length();
				int nSize = (int)nLen;
				byte[] data = new byte[nSize];
				int size = -1;
				size = signStream.read(data, 0, data.length);
				ServletOutputStream sout = resp.getOutputStream();
				if(size > 0){
					sout.write(data, 0, size);
				}
				stmt.close();
				conn.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	
	
	private Connection getConnection(){
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(UtilFactory.getConfigUtil().getConfig("jdbc.url"), "core", "core");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}

}
