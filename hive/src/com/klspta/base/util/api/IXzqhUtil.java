package com.klspta.base.util.api;

import java.util.List;

import com.klspta.base.util.bean.xzqhutil.XzqhBean;

public interface IXzqhUtil {
    /**
     * 
     * <br>Description:获取省级行政区划
     * <br>Author:黎春行
     * <br>Date:2013-6-16
     * @return
     */
    public List<XzqhBean> getProvinceList();

    /**
     * 
     * <br>Description:根据父级Id获取子级行政区划
     * <br>Author:黎春行
     * <br>Date:2013-6-16
     * @param code
     * @return
     */
    public List<XzqhBean> getChildListById(String code);

    /**
     * 
     * <br>Description:保存/更新行政区划
     * <br>Author:黎春行
     * <br>Date:2013-6-16
     * @param xzqh
     * @return
     */
    boolean save(XzqhBean xzqh);

    /**
     * 
     * <br>Description:删除行政区域
     * <br>Author:黎春行
     * <br>Date:2013-6-16
     * @param cantonCode
     * @return
     */
    boolean deleteByCantonCode(String cantonCode);

    /**
     * 
     * <br>Description:根据行政区划编码获取区划bean。
     * <br>Author:黎春行
     * <br>Date:2013-6-16
     * @return
     */
    XzqhBean getBeanById(String id);

    /**
     * 
     * <br>Description:根据行政区划编码获取区划名称
     * <br>Author:陈强峰
     * <br>Date:2013-6-23
     * @param code
     * @return
     */
    String getNameByCode(String code);

    /**
     * 
     * <br>Description:根据父级Id获取子级行政区划
     * <br>Author:黎春行
     * <br>Date:2012-5-24
     * @param code
     * @return
     */
    public List<XzqhBean> getChildListByParentId(String code);

    /**
     * 
     * <br>Description:将含有行政区划的List转化为字符串。
     * <br>Author:黎春行
     * <br>Date:2012-5-24
     * @param list
     * @return
     */
    String generateOptionByList(List<XzqhBean> list);
}
