package com.klspta.web.cbd.xmgl.xmbg;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.apache.poi.hslf.model.Picture;
import org.apache.poi.hslf.model.Shape;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextBox;
import org.apache.poi.hslf.usermodel.RichTextRun;
import org.apache.poi.hslf.usermodel.SlideShow;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.bean.ftputil.AccessoryBean;
import com.klspta.model.accessory.AccessoryOperation;

public class ReportPPT extends AbstractBaseBean {
	private static String  cacheLocation = "C:\\cache";
	public SlideShow ppt = new SlideShow();
	public SlideShow styleppt;

	public ReportPPT() {
		super();
	}
	
	public SlideShow getPPT() throws Exception{
		java.awt.Dimension pgsize = ppt.getPageSize();
		int pgx = pgsize.width;
		int pgy = pgsize.height;
		
		Slide slide = buildSlide();
		setSlideSize(800, 600);
		setPicture(25, 25, 25, 25, "G:\\domain\\src\\com\\klspta\\web\\cbd\\xmgl\\xmbg\\logo.jpg",Picture.JPEG, slide);
		setPicture(10, 10, pgx/2, pgy/2, "G:\\domain\\src\\com\\klspta\\web\\cbd\\xmgl\\xmbg\\图片1.jpg", Picture.JPEG, slide);
		setPicture(pgx/2 + 30, 10, pgx/2, pgy/2, "G:\\domain\\src\\com\\klspta\\web\\cbd\\xmgl\\xmbg\\图片2.jpg", Picture.JPEG, slide);
		setPicture(10, pgy/2 + 30, pgx/2, pgy/2, "G:\\domain\\src\\com\\klspta\\web\\cbd\\xmgl\\xmbg\\图片3.jpg", Picture.JPEG, slide);
		
		
		TextBox txt = new TextBox();
		RichTextRun rt = txt.getTextRun().getRichTextRuns()[0];
		rt.setFontSize(14);
		//rt.setBold(true);
		rt.setAlignment(TextBox.AlignLeft);
		txt.setAnchor(new java.awt.Rectangle(pgx/2 + 30,pgy/2 + 30,pgx/2,pgy/2));
		txt.setText("项目名称：联大商学院项目\r"+
					"开发主体：拟由分中心作为主体实施收储带前期开发。\r"+
					"项目区位：该项目位于朝阳区红领巾桥西南角，延静里中街。\r"+
					"规划情况：项目占地约1.95公顷，规划建设用地面积1.26公顷，规划建筑规模4.41万平方米，主要规划用途为居住及配套用地。\r" + 
					"现状情况：现状建筑规模约2.14万㎡，全部为联大商学院校区。\r"+
					"相关进展：");
		setText(slide, txt);
		return ppt;
	}
	
	public SlideShow buildPPT(String yw_guid, String file_id) throws Exception{
		String sql = "select t.xmname from jc_xiangmu t where t.yw_guid = ?";
		String xmmc = String.valueOf(query(sql, YW, new Object[]{yw_guid}).get(0).get("xmname"));
		String sql2 = "select t.loginname1 from core_projectname t where t.use = 'yes'";
		String xtmc = String.valueOf(query(sql2, CORE, new Object[]{}).get(0).get("loginname1"));
		StringBuffer title1 = new StringBuffer();
		StringBuffer title2 = new StringBuffer();
		title1.append(xtmc);
		title2.append(xmmc).append("卫星遥感图");
		
		java.awt.Dimension pgsize = ppt.getPageSize();
		int pgx = pgsize.width;
		int pgy = pgsize.height;
		
		Slide slide = buildSlide();
		setSlideSize(800, 600);

		TextBox txt1 = new TextBox();
		RichTextRun rt1 = txt1.getTextRun().getRichTextRuns()[0];
		rt1.setFontSize(16);
		rt1.setAlignment(TextBox.AlignLeft);
		txt1.setAnchor(new java.awt.Rectangle(50,25,pgx,30));
		txt1.setText(title1.toString());
		
		TextBox txt2 = new TextBox();
		RichTextRun rt2 = txt2.getTextRun().getRichTextRuns()[0];
		rt2.setFontSize(14);
		rt2.setAlignment(TextBox.AlignCenter);
		txt2.setAnchor(new java.awt.Rectangle(25,50,pgx/2-10,20));
		txt2.setText(title2.toString());
		
		TextBox txt3 = new TextBox();
		RichTextRun rt3 = txt3.getTextRun().getRichTextRuns()[0];
		rt3.setFontSize(14);
		rt3.setAlignment(TextBox.AlignCenter);
		txt3.setAnchor(new java.awt.Rectangle(25,pgy/2+30,pgx/2-10,20));
		txt3.setText(title2.toString());
		
		TextBox txt4 = new TextBox();
		RichTextRun rt4 = txt4.getTextRun().getRichTextRuns()[0];
		rt4.setFontSize(14);
		rt4.setAlignment(TextBox.AlignCenter );
		txt4.setAnchor(new java.awt.Rectangle(pgx/2+25,50,pgx/2-10,20));
		txt4.setText(title2.toString());
		
		setText(slide, txt1);
		setText(slide, txt2);
		setText(slide, txt3);
		setText(slide, txt4);
		setPicture(25, 25, 25, 25, "D:\\domain\\src\\com\\klspta\\web\\cbd\\xmgl\\xmbg\\logo.jpg", Picture.JPEG, slide);

		
		String[] file_ids = file_id.split(",");
		int num = file_ids.length > 3 ? 3 : file_ids.length;
		for(int i = 0; i < num; i++){
			String fileid = file_ids[i];
			if("".equals(fileid) || fileid == null){
				break;
			}
			AccessoryBean bean = new AccessoryBean();
			bean = AccessoryOperation.getInstance().getAccessoryById(fileid);
			String fileName = bean.getFile_name();
			fileName = new String(fileName.getBytes(), "ISO-8859-1");
			String file_Path = cacheLocation + "\\" + AccessoryOperation.getInstance().download(bean, cacheLocation + "\\");
			String classPath = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
	    	String dirpath = classPath.substring(0, classPath.lastIndexOf("WEB-INF/classes"));
	    	dirpath=dirpath+"model/accessory/dzfj/download/";
	    	//file_Path = dirpath + bean.getFile_path();
	    	
	    	
	    	switch (i) {
			case 0:
				setPicture(35, 75, pgx/2-10, pgy/2-50, file_Path, Picture.JPEG, slide);
				break;
			case 1:
				setPicture(pgx/2 + 55, 75, pgx/2-10, pgy/2-50, file_Path, Picture.JPEG, slide);
				break;
			case 2:
				setPicture(35, pgy/2 +55, pgx/2-10, pgy/2-25, file_Path, Picture.JPEG, slide);
				break;
			default:
				break;
			}
		}
		
		StringBuffer description = new StringBuffer();
		description.append("项目名称:").append(xmmc).append("\r");
		description.append("开发主体:").append("\r");
		description.append("项目区位:").append("\r");
		description.append("规划情况:").append("\r");
		description.append("现状情况:").append("\r");
		description.append("相关进展:").append("\r");
		TextBox txt = new TextBox();
		RichTextRun rt = txt.getTextRun().getRichTextRuns()[0];
		rt.setFontSize(14);
		rt.setAlignment(TextBox.AlignLeft);
		txt.setAnchor(new java.awt.Rectangle(pgx/2 + 55,pgy/2 + 55,pgx/2-10,pgy/2-25));
		txt.setText(description.toString());
		setText(slide, txt);
		
		return ppt;
	}
	
	public Slide buildSlide(){
		return ppt.createSlide();
	}
	
	public void setSlideSize(int width, int height){
		ppt.setPageSize(new java.awt.Dimension(width, height));
	}
	
	public void setText(Slide slide, TextBox txt){
		slide.addShape(txt);
	}
	
	public void setPicture(int left, int top, int width, int height, String path, int format,Slide slide){
		//判断path是否存在，不存在时，不添加file
		try {
			int idx = ppt.addPicture(new File(path), format);
			Picture picture = new Picture(idx);
			picture.setAnchor(new java.awt.Rectangle(left, top, width, height));
			slide.addShape(picture);
		} catch (Exception e) {
			System.out.println("文件服务器（ftp、ser-U）没有启动，请启动文件服务器");
		}

	}
	
	public void setPicture(int left, int top, int width, int height, byte[] data, int format,Slide slide) throws Exception{
		int idx = ppt.addPicture(data, format);
		Picture picture = new Picture(idx);
		picture.setAnchor(new java.awt.Rectangle(left, top, width, height));
		slide.addShape(picture);
	}
	
	public Slide getSlide(int i){
		if(ppt == null){
			return null;
		}else{
			Slide[] slide = ppt.getSlides();
			return slide[i];
		}
	}
	
	public TextBox getTextBox(Slide slide, int i){
		Shape[] sh = slide.getShapes();
		if(-1 == i){
			for(int j = 0; j < sh.length; j++){
				if(sh[j] instanceof TextBox){
					return (TextBox)sh[j];
				}
			}
		}else{
			for(int j = i; j < sh.length; j++){
				if(sh[j] instanceof TextBox){
					return (TextBox)sh[j];
				}
			}
		}
		return null;
	}
	
	

}
