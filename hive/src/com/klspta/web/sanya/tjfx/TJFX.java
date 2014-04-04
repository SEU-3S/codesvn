package com.klspta.web.sanya.tjfx;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.jdom.Content;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import com.klspta.base.AbstractBaseBean;

/**
 * <br>
 * Title:统计分析 <br>
 * Description:统计分析处理类 <br>
 * Author:赵伟 <br>
 * Date:2013-9-15
 */
public class TJFX extends AbstractBaseBean{
	
	public void init(String xml){
		if(xml.equals("xf_clzt.xml")){
			generateXfZt();
		}else if(xml.equals("zh_clzt.xml")){
			generateZhZt();
		}else if(xml.equals("zh_djzl.xml")){
			generateZhdjfl();
		}
	}
	/**
	 * <br>
	 * Description:生成信访统计状态统计数据 <br>
	 * Author:赵伟 <br>
	 * Date:2013-9-15
	 */
	public void generateXfZt() {
		String sql = "select (select count(*) from xfajdjb t where t.blzt='已处理')as count1,(select count(*) from xfajdjb t where t.blzt='未处理')as count2 from dual";
		List<Map<String, Object>> result =query(sql, YW);
		
		String classPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
        String filePath = classPath.substring(0,classPath.lastIndexOf("WEB-INF/classes"));
        String xmlPath = filePath+"web/sanya/tjfx/chartxml/xf_clzt.xml";
        SAXBuilder builder = new SAXBuilder();
        org.jdom.Document doc = null;
        try {
            doc = builder.build(new java.io.File(xmlPath));
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element root = doc.getRootElement();
        List<Element> e1=root.getChild("charts").getChild("chart").getChild("data").getChild("series").getChildren();
        for(int i=0;i<2;i++){
        	String name=e1.get(i).getAttributeValue("name");
        	if(name.equals("未处理")){
        		e1.get(i).setAttribute("y", result.get(0).get("count2").toString());
        	}else{
        		e1.get(i).setAttribute("y", result.get(0).get("count1").toString());
        	}
        }
        XMLOutputter outer = new XMLOutputter();
        try {
            outer.output(doc, new FileOutputStream(xmlPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void generateZhZt(){
		String sql = "select (select count(*) from wjspdjb t where t.blqk='已处理')as count1,(select count(*) from wjspdjb t where t.blqk='未处理')as count2 from dual";
		List<Map<String, Object>> result =query(sql, YW);
		
		String classPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
        String filePath = classPath.substring(0,classPath.lastIndexOf("WEB-INF/classes"));
        String xmlPath = filePath+"web/sanya/tjfx/chartxml/zh_clzt.xml";
        SAXBuilder builder = new SAXBuilder();
        org.jdom.Document doc = null;
        try {
            doc = builder.build(new java.io.File(xmlPath));
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element root = doc.getRootElement();
        List<Element> e1=root.getChild("charts").getChild("chart").getChild("data").getChild("series").getChildren();
        for(int i=0;i<2;i++){
        	String name=e1.get(i).getAttributeValue("name");
        	if(name.equals("未处理")){
        		e1.get(i).setAttribute("y", result.get(0).get("count2").toString());
        	}else{
        		e1.get(i).setAttribute("y", result.get(0).get("count1").toString());
        	}
        }
        XMLOutputter outer = new XMLOutputter();
        try {
            outer.output(doc, new FileOutputStream(xmlPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void generateZhdjfl(){
		String[] type=new String[]{"市委市政府","省国土环境资源厅","省国土环境监察总队","三亚环境资源局","其他"};
		String sql="select ";
		for(int i=0;i<type.length;i++){
			if(i==0){
				sql+="(select count(*) from wjspdjb t where t.wjlx='"+type[i]+"')"+type[i]+"";
			}else{
				sql+=",(select count(*) from wjspdjb t where t.wjlx='"+type[i]+"')"+type[i]+"";
			}
		}
		sql+=" from dual";
		
		List<Map<String, Object>> result =query(sql, YW);
		
		String classPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
        String filePath = classPath.substring(0,classPath.lastIndexOf("WEB-INF/classes"));
        String xmlPath = filePath+"web/sanya/tjfx/chartxml/zh_djzl.xml";
        SAXBuilder builder = new SAXBuilder();
        org.jdom.Document doc = null;
        try {
            doc = builder.build(new java.io.File(xmlPath));
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element root = doc.getRootElement();
        List<Element> e1=root.getChild("charts").getChild("chart").getChild("data").getChild("series").getChildren();
        for(int i=0;i<type.length;i++){
        	e1.get(0).setAttribute("y", result.get(0).get(type[0]).toString());
        }
        XMLOutputter outer = new XMLOutputter();
        try {
            outer.output(doc, new FileOutputStream(xmlPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
