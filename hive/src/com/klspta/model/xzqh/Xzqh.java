package com.klspta.model.xzqh;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Component;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.base.util.bean.xzqhutil.XzqhBean;

/**
 * 
 * <br>
 * Title:行政区划工具 <br>
 * Description: <br>
 * Author:黎春行 <br>
 * Date:2012-5-23
 */
@Component
public class Xzqh extends AbstractBaseBean {

    /**
     * 
     * <br>Description:获取省级行政区划
     * <br>Author:黎春行
     * <br>Date:2012-5-23
     */
    public void getAllPlace() {
    }

    /**
     * 
     * <br>Description:获取特定区域的行政区划
     * <br>Author:黎春行
     * <br>Date:2012-5-23
     * @throws IOException
     */
    public void getNextPlace() throws IOException {
    }

    /**
     * 
     * <br>Description:根据行政区划编码获取区划名称
     * <br>Author:黎春行
     * <br>Date:2012-6-8
     */
    public void getNameById() {
    }

    /**
     * 
     * <br>Description:获取上一级的行政区划
     * <br>Author:黎春行
     * <br>Date:2012-6-19
     */
    public void getParentNameByChildId() {
    }

    /**
     * 
     * <br>Description:添加行政区信息
     * <br>Author:陈强峰
     * <br>Date:2012-6-19
     */
    public void addXzqh() {
    }
    
    /**
     * 
     * <br>Description:获取邮政编码
     * <br>Author:黎春行
     * <br>Date:2012-6-20
     */
    public void getPostalCodeById() {
	}
}
