package com.klspta.base.util.impl;

import it.sauronsoftware.ftp4j.FTPClient;

import java.io.File;
import java.io.InputStream;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.base.util.api.IFtpUtil;

public class FtpUtil extends AbstractBaseBean implements IFtpUtil {
    FTPClient ftp;

    private static FtpUtil ftpUtil;

    private FtpUtil() {

    }

    public static IFtpUtil getInstance(String key) throws Exception {
        if (!key.equals("NEW WITH UTIL FACTORY!")) {
            throw new Exception("请通过UtilFactory获取实例.");
        }
        if (ftpUtil == null) {
        	ftpUtil = new FtpUtil();
        }
        return ftpUtil;
    }

    /**
     * <br>Description: 附件下载
     * <br>Author:李如意
     * <br>Date:2012-7-1
     * @see com.klspta.model.accessory.IFtpUtil#downloadFile(java.lang.String, int, java.lang.String, java.lang.String, java.lang.String)
     */
    public String downloadFile(String ip, int port, String username, String password, String ftpFileId) {
        StringBuffer sb = new StringBuffer("ftp://");
        sb.append(username).append(":").append(password).append("@").append(ip).append(":").append(port).append("/").append(ftpFileId);
        return sb.toString();
    }


    private FTPClient getFtpClient() {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(UtilFactory.getConfigUtil().getConfig("ftp.host"), Integer.parseInt(UtilFactory.getConfigUtil().getConfig("ftp.port")));
            ftpClient.login(UtilFactory.getConfigUtil().getConfig("ftp.username"), UtilFactory.getConfigUtil().getConfig("ftp.password"));
        } catch (Exception e) {
        	responseException(this, "getFtpClient", "100006", e);
            e.printStackTrace();
        }
        return ftpClient;
    }

    /**
     * <br>Description: 附件上传
     * <br>Author:李如意
     * <br>Date:2012-8-16
     * @see com.klspta.base.util.api.IFtpUtil#uploadFile(java.lang.String)
     */
    @Override
    public boolean uploadFile(InputStream input, String ftpFileName) {
    	FTPClient client = getFtpClient();
        try {
            
            client.upload(ftpFileName, input, 0L, 0L, null);
            
        } catch (Exception e) {
            e.printStackTrace();
            responseException(this, "uploadFile", "100003", e);
            return false;
        }finally{
        	try {
				client.disconnect(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        return true;
    }

    /**
     * <br>Description: 附件上传
     * <br>Author:李如意
     * <br>Date:2012-8-17
     * @see com.klspta.base.util.api.IFtpUtil#uploadFile(java.lang.String)
     */
    @Override
    public boolean uploadFile(String file_path) {
    	FTPClient client = getFtpClient();
        try {
            client.upload(new File(file_path));
        } catch (Exception e) {
        	responseException(this, "uploadFile", "100003", e);
            e.printStackTrace();
            return false;
        }finally{
        	try {
				client.disconnect(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        return true;
    }

    /**
     * <br>Description: 附件下载
     * <br>Author:李如意
     * <br>Date:2012-8-17
     * @see com.klspta.base.util.api.IFtpUtil#downloadFile(java.lang.String, java.lang.String)
     */
    public boolean downloadFile(String remoteFileName, String localFilePosition) {
        FTPClient client = getFtpClient();
        try {
            client.download(remoteFileName, new File(localFilePosition));
            client.disconnect(false);
        } catch (Exception e) {
        	responseException(this, "downloadFile", "100007", e);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * <br>Description: 附件删除
     * <br>Author:李如意
     * <br>Date:2012-8-16
     * @see com.klspta.base.util.api.IFtpUtil#deleteFTPFile(java.lang.String)
     */
    public boolean deleteFile(String ftpFileName) {
        FTPClient client = getFtpClient();
        try {
            client.deleteFile(ftpFileName);
            client.disconnect(false);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
