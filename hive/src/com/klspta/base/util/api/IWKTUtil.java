/**
 * Create Date:2009-7-29
 */
package com.klspta.base.util.api;

import com.klspta.base.wkt.Polygon;

public interface IWKTUtil {

    /**
     * <br>Description:JSON格式面转WKT对象
     * <br>Author:陈强峰
     * <br>Date:2013-6-5
     * @param string
     * @return
     */
    Polygon stringToWKTObject(String string);
}
