package com.klspta.web.cbd.dtjc;

import java.util.Map;

import com.klspta.web.cbd.dtjc.tjbb.Gdtl;
import com.klspta.web.cbd.dtjc.tjbb.Kftl;
/**
 * 
 * <br>Title:实施时序线程
 * <br>Description:解决前台反应较慢问题，采用异步形式
 * <br>Author:黎春行
 * <br>Date:2013-11-7
 */
public class TjbbThread implements Runnable {
	private String formName = "";
	private Map<String, String> setValues= null;
	private Map<String, String> conditions = null;

	public TjbbThread(String formName, Map<String, String> setValues,
			Map<String, String> conditions) {
		super();
		this.formName = formName;
		this.setValues = setValues;
		this.conditions = conditions;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		TjbbData tjbbData = new TjbbData();
		tjbbData.changeQuarter(formName, setValues, conditions);
		String oldYear = setValues.get("nd");
		String oldQuarter = setValues.get("jd");
		String newYear = conditions.get("nd");
		String newQuarter = conditions.get("jd");
		//System.out.println(oldYear + "--" + oldQuarter + "-" + newYear + "-" + newQuarter);
		if(formName.equals("hx_gdtl")){
			Gdtl gdtl = new Gdtl();
			gdtl.updateTj(oldYear, oldQuarter);
			gdtl.updateTj(newYear, newQuarter);
		}else if(formName.equals("hx_kftl")){
			Kftl kftl = new Kftl();
			kftl.updateTj(oldYear, oldQuarter);
			kftl.updateTj(newYear, newQuarter);
		}
		
		
		Thread.currentThread().interrupt();
	}

}
