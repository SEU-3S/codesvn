package com.klspta.base.util.api;

import com.klspta.base.wkt.Point;

/**
 * <br>Title:坐标转换工具类
 * <br>Description:对坐标进行转换
 * <br>Author:陈强峰
 * <br>Date:2013-6-5
 */
public interface ICoordinateChangeUtil {
    static final String GPS84_TO_BALIN80 = "GPS84_3212.34567,12012.34567 PLAIN80_40345678.90,4034567.89";

    static final String BL84_TO_BL80 = "BL84_32.1234567,120.1234567";

    static final String BL80_TO_PLAIN80 = "BL80_32.1234567,120.1234567 PLAIN80_40345678.90,4034567.89";

    static final String PLAIN80_TO_BL80 = "PLAIN80_40345678.90,4034567.89";

    static final String PLAIN84_TO_PLAIN80 = "PLAIN84_ PLAIN80_40345678.90,4034567.89";

    static final String GRID = "GRID";//格网法坐标偏移

    /**
     * <br>Description:坐标转换
     * <br>Author:陈强峰
     * <br>Date:2013-6-5
     * @param point
     * @param changetype 转换坐标(ICoordinateUtil常量)
     * @return
     */
    Point changePoint(Point point, String changetype);
}
