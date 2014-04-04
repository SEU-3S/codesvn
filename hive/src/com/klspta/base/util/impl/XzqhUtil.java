package com.klspta.base.util.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.base.util.api.IXzqhUtil;
import com.klspta.base.util.bean.xzqhutil.XzqhBean;

public class XzqhUtil extends AbstractBaseBean implements IXzqhUtil {

    private static Vector<XzqhBean> xzqhList = new Vector<XzqhBean>();

    private static XzqhUtil instance;

    public static IXzqhUtil getInstance(String key) throws Exception {
        if (!key.equals("NEW WITH UTIL FACTORY!")) {
            throw new Exception("请通过UtilFactory获取实例.");
        }
        if (instance == null) {
            instance = new XzqhUtil();
        }
        return instance;
    }

    /**
     * 
     * <br>Description:类初始化时将所有行政区划bean放入到Vector中。
     * <br>Author:黎春行
     * <br>Date:2012-5-24
     */
    private XzqhUtil() {
        String sql = "select t.* from  CODE_XZQH t order by qt_ctn_code asc";
        List<Map<String, Object>> rs = query(sql, CORE);
        for (int i = 0; i < rs.size(); i++) {
            XzqhBean oneBean = new XzqhBean();
            Map<String, Object> oneMap = rs.get(i);
            oneBean.setCatoncode(String.valueOf(oneMap.get("qt_ctn_code")));
            oneBean.setCatonname(String.valueOf(oneMap.get("na_ctn_name")));
            oneBean.setCatonsimpleName(String.valueOf(oneMap.get("na_ctn_abb")));
            oneBean.setFullname(String.valueOf(oneMap.get("na_full_name")));
            oneBean.setGovname(String.valueOf(oneMap.get("na_gov_name")));
            oneBean.setLandname(String.valueOf(oneMap.get("na_landdp_name")));
            oneBean.setParentcode(String.valueOf(oneMap.get("qt_parent_code")));
            oneBean.setStateflag(String.valueOf(oneMap.get("qt_state_flag")));
            oneBean.setPostalcode(String.valueOf(oneMap.get("qt_postal_code")));
            xzqhList.add(oneBean);
        }
    }

    @Override
    public boolean deleteByCantonCode(String cantonCode) {
        return false;
    }

    @Override
    public List<XzqhBean> getChildListById(String code) {
        List<XzqhBean> childList = new ArrayList<XzqhBean>();
        XzqhBean choseBean;
        for (int i = 0; i < xzqhList.size(); i++) {
            choseBean = xzqhList.get(i);
            if (choseBean.getParentcode() == null)
                continue;
            if (choseBean.getParentcode().equals(code)) {
                childList.add(choseBean);
            }
        }
        return childList;
    }

    @Override
    public XzqhBean getBeanById(String id) {
        for (int i = 0; i < xzqhList.size(); i++) {
            if (xzqhList.get(i).getCatoncode().equals(id)) {
                return xzqhList.get(i);
            }
        }
        return null;
    }

    @Override
    public List<XzqhBean> getProvinceList() {
        List<XzqhBean> proviceList = new ArrayList<XzqhBean>();
        XzqhBean choseBean;
        for (int i = 0; i < xzqhList.size(); i++) {
            choseBean = xzqhList.get(i);
            if (choseBean.getParentcode() == null)
                continue;
            if (choseBean.getParentcode().equals("0")) {
                proviceList.add(choseBean);
            }
        }

        return proviceList;
    }

    @Override
    public boolean save(XzqhBean xzqh) {
        return false;
    }

    @Override
    public String getNameByCode(String code) {
        String result = null;
        for (int i = 0; i < xzqhList.size(); i++) {
            if (xzqhList.get(i).getCatoncode().equals(code)) {
                result = xzqhList.get(i).getCatonname();
                return result;
            }
        }
        return result;
    }

    @Override
    public List<XzqhBean> getChildListByParentId(String code) {
        List<XzqhBean> childList = new ArrayList<XzqhBean>();
        XzqhBean choseBean;
        for (int i = 0; i < xzqhList.size(); i++) {
            choseBean = xzqhList.get(i);
            if (choseBean.getParentcode() == null)
                continue;
            if (choseBean.getParentcode().equals(code)) {
                childList.add(choseBean);
            }
        }
        return childList;
    }

    @Override
    public String generateOptionByList(List<XzqhBean> list) {
        XzqhBean choseBean;
        String msgString = "";
        Vector<Map<String, String>> province = new Vector<Map<String, String>>();

        for (int i = 0; i < list.size(); i++) {
            choseBean = list.get(i);
            Map<String, String> formatMap = new TreeMap<String, String>();
            formatMap.clear();
            formatMap.put("code", choseBean.getCatoncode());
            formatMap.put("name", choseBean.getCatonname());
            province.add(formatMap);
        }

        try {
            msgString = UtilFactory.getJSONUtil().objectToJSON(province);
        } catch (Exception e) {
        	responseException(this, "generateOptionByList","100002", e);
            e.printStackTrace();
        }
        return msgString;
    }

}
