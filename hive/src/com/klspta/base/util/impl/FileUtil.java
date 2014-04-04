package com.klspta.base.util.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.base.util.api.IFileUtil;

public class FileUtil extends AbstractBaseBean  implements IFileUtil {
	
	private FileUtil(){}
	
    private static FileUtil instance;

    /**
     * <br>Description:默认文件上传缓冲区大小为1M，单位为KB
     * <br>Author:郭润沛
     * <br>Date:2011-4-20
     */
    private static int SIZETHRESHOLDKB = 1024;

    /**
     * <br>Description:默认最大上传文件限制为1G，单位为KB
     * <br>Author:郭润沛
     * <br>Date:2011-4-20
     */
    private static int SIZEMAXKB = 1024 * 1024;

    @Override
    public List<String> upload(HttpServletRequest request, int sizeMaxKb, int sizeThresholdKb) throws Exception {
        // 判断是否为文件上传的请求
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            throw new Exception("非文件上传request，不能调用当前方法");
        }
        try {
            String uploadPath = UtilFactory.getConfigUtil().getShapefileTempPathFloder() + UtilFactory.getStrUtil().getGuid() + "//";
            File uploadFile = new File(uploadPath);
            if (!uploadFile.exists()) {
                uploadFile.mkdirs();
            }
            DiskFileItemFactory factory = new DiskFileItemFactory();
            if (sizeThresholdKb <= 0) {
                sizeThresholdKb = SIZETHRESHOLDKB;
            }
            factory.setSizeThreshold(sizeThresholdKb * 1024);
            factory.setRepository(uploadFile);//设置临时目录
            ServletFileUpload upload = new ServletFileUpload(factory);
            if (sizeMaxKb <= 0) {
                sizeMaxKb = SIZEMAXKB;
            }
            upload.setSizeMax(sizeMaxKb * 1024);
            upload.setHeaderEncoding("utf-8");
            List<?> fileItems = upload.parseRequest(request);
            Iterator<?> i = fileItems.iterator();
            List<String> list = new ArrayList<String>();
            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();
                String fileName = fi.getName();
                if (fileName != null) {
                    fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
                    File file = new File(uploadPath + fileName);
                    fi.write(file);
                    list.add(uploadPath + fileName);
                }
            }
            //System.out.println("上传成功，文件保存在：" + uploadPath);
            return list;
        } catch (Exception e) {
        	responseException(this,"upload","100005",e);
            return null;
        } finally {
        }
    }

    public static IFileUtil getInstance(String key) throws Exception {
        if (!key.equals("NEW WITH UTIL FACTORY!")) {
            throw new Exception("请通过UtilFactory获取实例.");
        }
        if (instance == null) {
        	instance = new FileUtil();
        }
        return instance;
    }

    @Override
    public void deleteFile(File file) {
        File[] files = file.listFiles();
        if (files == null || files.length == 0) {
            file.delete();
            return;
        }
        for (File fil : files) {
            deleteFile(fil);
        }
    }

}
