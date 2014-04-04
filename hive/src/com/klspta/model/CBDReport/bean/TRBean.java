package com.klspta.model.CBDReport.bean;

import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class TRBean {
    
    private String cssStyle = "";
    
	private Vector<TDBean> tdMaps = new Vector<TDBean>();
	private Vector<TDBean> staticTdMaps = new Vector<TDBean>();
	public void addTDBean(TDBean tdb) {
	    addTDBean(tdb, ThirdBean.DATA_TYPE_STATIC);
	}
	
	public void addTDBean(TDBean tdb, String type) {
        tdMaps.add(tdb);
        if(type.equals(ThirdBean.DATA_TYPE_STATIC)){
            staticTdMaps.add(tdb);
        }
    }
	
	public void addTDBeans(Map<String, Object> beans, String width, String height, String editable){
	    if(beans != null){
	        Iterator<Map.Entry<String,Object>> iter = beans.entrySet().iterator();
	        while (iter.hasNext()) {
	            Map.Entry<String, Object> entry = (Map.Entry<String, Object>)iter.next();
	            String val = null2Str(entry.getValue());
	            tdMaps.add(new TDBean(val, width, height, editable));
	        }
	    }
	}
	
    @SuppressWarnings("unchecked")
    public TRBean copyStatic(){
        TRBean trBean = new TRBean();
        trBean.tdMaps = (Vector<TDBean>)staticTdMaps.clone();
        trBean.cssStyle = this.cssStyle;
        return trBean;
    }
	
	public Vector<TDBean> getTDBeans(){
	    return tdMaps;
	}

    public String getCssStyle() {
        return cssStyle;
    }

    public void setCssStyle(String cssStyle) {
        this.cssStyle = cssStyle;
    }
    
    private String null2Str(Object s){
        if(s == null){
            return "";
        }else{
            return s.toString();
        }
    }
}
