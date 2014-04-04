package com.klspta.web.xuzhouNW.importSign;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;

public class SignManager extends AbstractBaseBean  implements ISignManager {
	/**
	 * <br>Description: 方法功能描述
	 * <br>Author:黎春行
	 * <br>Date:2013-3-25
	 * @see com.klspta.web.xuzhouNW.importSign.ISignManager#setSign(java.lang.String, java.lang.String)
	 */
	public String  setSign(String filePath, String userId){
		File signFile = new File(filePath);
		Connection conn = getConnection();
		ResultSet resultSet;
		Statement stmt = null;
		int bufferSize;
		try {
			stmt = conn.createStatement();
			InputStream signStream = new FileInputStream(signFile);
			OutputStream out = null;
			BufferedInputStream in = null;
			conn.setAutoCommit(false);
			stmt.executeUpdate("update core_users set sign=empty_blob() where id = '" + userId + "'");
			resultSet = stmt.executeQuery("select * from core_users where id = '" + userId + "'");
			if(resultSet.next()){
				Blob blob = resultSet.getBlob("sign");
				out = ((oracle.sql.BLOB)blob).getBinaryOutputStream();
				bufferSize = ((oracle.sql.BLOB)blob).getBufferSize();
				in = new BufferedInputStream(signStream, bufferSize);
				byte[] b = new byte[bufferSize];
				int count = in.read(b, 0, bufferSize);
				while (count != -1) {
					out.write(b, 0, count);
					count = in.read(b, 0, bufferSize);
				}
				out.close();
				signStream.close();
				in.close();
				conn.commit();
				conn.close();
				return "success";
			}
		} catch (FileNotFoundException e) {
			responseException(this, "setSign", "400002", e);
			e.printStackTrace();
		} catch (SQLException e) {
			responseException(this, "setSign", "400003", e);
			e.printStackTrace();
		} catch (IOException e) {
			responseException(this, "setSign", "400001", e);
			e.printStackTrace();
		}
		
		return "false";
		
	}
	
	/**
	 * <br>Description: 方法功能描述
	 * <br>Author:黎春行
	 * <br>Date:2013-3-25
	 * @see com.klspta.web.xuzhouNW.importSign.ISignManager#getSign(java.lang.String)
	 */
	public String  getSign(String userId, String filePath){
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
				String temporaryFile = filePath + "\\sign\\" + userId + ".jpg";
				if(!new File(temporaryFile).exists()){
					if(!new File(filePath + "\\sign").exists()){
						new File(filePath + "\\sign").mkdir();
					}
					new File(temporaryFile).createNewFile();
				}
				DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File(temporaryFile)));
				dos.flush();
				dos.write(data, 0, size);
				dos.close();
				return temporaryFile;
			}
			
		} catch (SQLException e) {
			responseException(this, "getSign", "400004", e);
			e.printStackTrace();
		} catch (IOException e) {
			responseException(this, "getSign", "400003", e);
			e.printStackTrace();
		}
		return "error";
	}
	
	private Connection getConnection(){
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(UtilFactory.getConfigUtil().getConfig("jdbc.url"), "core", "core");
		} catch (ClassNotFoundException e) {
			responseException(this, "getSign", "400003", e);
			e.printStackTrace();
		} catch (SQLException e) {
			responseException(this, "getSign", "400004", e);
			e.printStackTrace();
		}
		
		return conn;
	}
}
