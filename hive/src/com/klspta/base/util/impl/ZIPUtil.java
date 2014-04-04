package com.klspta.base.util.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.api.IZIPUtil;

public final class ZIPUtil extends AbstractBaseBean implements IZIPUtil {

    private ZIPUtil() {
    }

    public static IZIPUtil getInstance(String key) throws Exception {
        if (!key.equals("NEW WITH UTIL FACTORY!")) {
            throw new Exception("请从UtilFacory获取工具实例.");
        }
        return new ZIPUtil();
    }

    public void zip(String filePath, String srcPathName) {
        File file = new File(filePath);
        file.delete();
        File srcdir = new File(srcPathName);
        Project prj = new Project();
        Zip zip = new Zip();
        zip.setProject(prj);
        zip.setDestFile(file);
        FileSet fileSet = new FileSet();
        fileSet.setProject(prj);
        fileSet.setDir(srcdir);
        zip.addFileset(fileSet);
        zip.execute();
    }

    @Override
    public void unZip(String zipFileName, String destDir) {
        try {
            ZipFile zipFile = new ZipFile(zipFileName);
            Enumeration<?> e = zipFile.getEntries();
            ZipEntry zipEntry = null;
            File fD = new File(destDir);
            if (!fD.exists()) {
                fD.mkdir();
            }
            while (e.hasMoreElements()) {
                zipEntry = (ZipEntry) e.nextElement();
                String entryName = zipEntry.getName();
                String names[] = entryName.split("/");
                int length = names.length;
                String path = destDir;
                for (int v = 0; v < length; v++) {
                    if (v < length - 1) {
                        path += "/" + names[v];
                        new File(path).mkdir();
                    } else {
                        if (entryName.endsWith("/")) {
                            new File(destDir + "/" + entryName).mkdir();
                        } else {
                            InputStream in = zipFile.getInputStream(zipEntry);
                            OutputStream os = new FileOutputStream(new File(destDir + "/" + entryName));
                            byte[] buf = new byte[1024];
                            int len;
                            while ((len = in.read(buf)) > 0) {
                                os.write(buf, 0, len);
                            }
                            in.close();
                            os.close();
                        }
                    }
                }
            }
            zipFile.close();

        } catch (Exception ex) {
        	responseException(this, "unZip", "100011", ex);
            ex.printStackTrace();
        }
    }
}
