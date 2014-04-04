package com.klspta.base.util.bean.ftputil;

import it.sauronsoftware.ftp4j.FTPClient;

public interface IFTPOperation {
    /**
     * 
     * <br>Description:根据FTP服务器ID，返回FTPClient实例
     * <br>Author:郭
     * <br>Date:2010-11-22
     * @param Ftp_Id
     * @return
     */
public FTPClient getFTPClient();
public FTPServerConfigBean getFtpConfig();
}
