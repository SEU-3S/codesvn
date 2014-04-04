package com.klspta.model.legend;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * <br>Title:图例文件
 * <br>Description:获取对应类型的图例文件
 * <br>Author:王雷
 * <br>Date:2011-6-21
 */
public class LegendFiles {

    private static LegendFiles legendFiles = null;
    /**
     * 
     * <br>Description:单例
     * <br>Author:王雷
     * <br>Date:2011-6-21
     * @return
     */
    public static LegendFiles getInstance() {
        if (legendFiles == null) {
            return new LegendFiles();
        }
        return legendFiles;
    }
    /**
     * 
     * <br>Description:获取对应类型的图例文件
     * <br>Author:王雷
     * <br>Date:2011-6-21
     * @param type
     * @return
     */
    public File[] getAllFiles(String type) {
        File[] files = null;
        String classPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
        String filepath = classPath.substring(0, classPath.lastIndexOf("WEB-INF/classes"));
        File file = new File(filepath + "base/fxgis/framework/images/legend/" + type);
        if (file.isDirectory()) {
            files = file.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    if (name.toLowerCase().endsWith(".png")) {
                        return true;
                    }
                    return false;
                }
            });
        }
        
        getAllFoldersName();
        return files;
    }
  
    
    /**
     * <br>Description:获取legend路径下图斑类型文件夹的名称
     * <br>Author:李如意
     * <br>Date:2011-09-14
     */
    public List<String> getAllFoldersName() {
    	List<String> names = new ArrayList<String>();	
        String classPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
        String filepath = classPath.substring(0, classPath.lastIndexOf("WEB-INF/classes"));
        File file = new File(filepath + "base/fxgis/framework/images/legend" );
		if(file.exists()){	
			if (file.isFile()) {
			}else{
					File[] list = file.listFiles();
					for (int i = 0; i < list.length; i++) {
						if(!list[i].getName().equals(".svn")){
							names.add(list[i].getName());
						}
					}
			}	
		}else{
			System.out.println("The directory is not exist!");
		}
		return names;
	}

}
