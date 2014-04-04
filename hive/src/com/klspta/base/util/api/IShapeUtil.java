/**
 * Create Date:2009-7-29
 */
package com.klspta.base.util.api;

import com.klspta.base.util.bean.shapeutil.DbfParameters;
import com.klspta.base.util.bean.shapeutil.ShpParameters;

public interface IShapeUtil {

    /**
     * 
     * <br>Description:导出shp文件（包含dbf）
     * <br>Author:王瑛博
     * <br>Date:2011-10-19
     * @param parameters
     * @return
     */
    public ShpParameters expShpFile(ShpParameters parameters, String name);

    /**
     * 
     * <br>Description:解析dbf文件
     * <br>Author:王瑛博
     * <br>Date:2011-10-19
     * @param parameters
     * @return
     */
    public DbfParameters parseDbfFile(DbfParameters parameters);

    /**
     * 
     * <br>Description:解析shp文件
     * <br>Author:王瑛博
     * <br>Date:2011-10-19
     * @param parameters
     * @return
     */
    public ShpParameters impShpFile(ShpParameters parameters);
}
