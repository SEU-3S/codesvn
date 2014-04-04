package com.klspta.web.cbd.dtjc.trzqk;

import java.util.Map;


public class TrzqkCalculate extends AbstractTrzqkCalculate {

	public TrzqkCalculate() {
		super();
	}

	@Override
	public void calCulateAll() {
		int minYear = Integer.parseInt(spanYear.get("minyear"));
		int maxYear = Integer.parseInt(spanYear.get("maxyear"));
		for(int year = minYear; year <= maxYear; year++){
			for(int quarter = 1; quarter <= 4; quarter++){
				calCulateBQTZXQ(year, quarter);
				calCulateBQHLCB(year, quarter);
				calCulateZFTDSY(year, quarter);
				calCulateBQHKXQ(year, quarter);
				//第一期的本期融资需求通过录入
				if(year != minYear || quarter != 1){
					calCulateBQRZXQ(year, quarter);
				}
				calCulateFZYE(year, quarter);
				if(year == minYear){
					calCulatecbkrzqk(year, quarter);
				}else {
					calCulatecbkrzqk(minYear, year, quarter);
				}
				calCulateZJFX(year, quarter);
				calCulateBQZMYE(year, quarter);
			}
		}
		setRecord();
	}

	/**
	 * 
	 * <br>Description:计算本期投资需求
	 * <br>Author:黎春行
	 * <br>Date:2013-11-8
	 */
	private void calCulateBQTZXQ(int year, int quarter){
		String fieldName = "bqtzxq";
		Map<String, String> store = cache.get(fieldName);
		Map<String, String> variableA = cache.get("cbhxtz");
		Map<String, String> variableB = cache.get("azftz");
		String key = year + "_" + quarter;
		float cbhxtz = Float.parseFloat(variableA.get(key)== null ? "0" : variableA.get(key));
		float azftz = Float.parseFloat(variableB.get(key)== null ? "0" :variableB.get(key));
		float value = cbhxtz + azftz;
		store.remove(key);
		store.put(key, String.valueOf(value));
		cache.remove(fieldName);
		cache.put(fieldName, store);
	}
	
	/**
	 * 
	 * <br>Description:计算本期回笼成本
	 * <br>Author:黎春行
	 * <br>Date:2013-11-8
	 */
	private void calCulateBQHLCB(int year, int quarter){
		String fieldName = "bqhlcb";
		Map<String, String> store = cache.get(fieldName);
		String key = year + "_" + quarter;
		int cacluQuarter = 0;
		int cacluYear = 0;
		if(quarter < 3){
			cacluYear = year - 1;
			cacluQuarter = quarter + 2; 
		}else{
			cacluYear = year;
			cacluQuarter = quarter - 2;
		}
		String value = new ExtendData().getgdcbTotal(String.valueOf(cacluYear), String.valueOf(cacluQuarter));
		value = value == "null" ? "0" : value;
		store.remove(key);
		store.put(key, value);
		cache.remove(fieldName);
		cache.put(fieldName, store);
	}
	
	/**
	 * 
	 * <br>Description:计算政府土地收益
	 * <br>Author:黎春行
	 * <br>Date:2013-11-8
	 */
	private void calCulateZFTDSY(int year, int quarter){
		String fieldName = "zftdsy";
		Map<String, String> store = cache.get(fieldName);
		String key = year + "_" + quarter;
		int cacluQuarter = 0;
		int cacluYear = 0;
		if(quarter < 3){
			cacluYear = year - 1;
			cacluQuarter = quarter + 2; 
		}else{
			cacluYear = year;
			cacluQuarter = quarter - 2;
		}
		String value = new ExtendData().getgdsyTotal(String.valueOf(cacluYear), String.valueOf(cacluQuarter));
		value = value == "null" ? "0" : value;
		store.remove(key);
		store.put(key, value);
		cache.remove(fieldName);
		cache.put(fieldName, store);
	}
	/**
	 * 
	 * <br>Description:计算本期还款需求
	 * <br>Author:黎春行
	 * <br>Date:2013-11-8
	 */
	private void calCulateBQHKXQ(int year, int quarter){
		String fieldName = "bqhkxq";
		Map<String, String> store = cache.get(fieldName);
		String key = year + "_" + quarter;
		int cacluYear = year - 2;
		String cacluKey = cacluYear + "_" + quarter;
		Map<String, String> bqrzxqMap = cache.get("bqrzxq");
		String value = bqrzxqMap.get(cacluKey) == null ? "0" : bqrzxqMap.get(cacluKey);
		value = value == "null" ? "0" : value;
		store.remove(key);
		store.put(key, value);
		cache.remove(fieldName);
		cache.put(fieldName, store);
		
	}
	
	/**
	 * 
	 * <br>Description:计算储备融资缺口
	 * <br>Author:黎春行
	 * <br>Date:2013-11-8
	 */
	private void calCulatecbkrzqk(int minYear, int year, int quarter){
		//第一年度为：供应规模*抵押率*评估土地价值*（1-融资损失）,以后为 储备融资能力-往期所有融资需求之和
		String fieldName = "cbkrzqk";
		Map<String, String> store = cache.get(fieldName);
		Map<String, String> cbkrz = cache.get("cbkrzqk");
		Map<String, String> rzxq = cache.get("bqrzxq");
		String key = year + "_" + quarter;
		boolean istrue = true;
		float totalrzxq = 0;
		while (istrue) {
			if(minYear < year){
				for(int i = 0; i <= 4; i++){
					String localkey = year + "_" + i;
					totalrzxq += Float.parseFloat(rzxq.get(localkey)== null ? "0":rzxq.get(localkey));
				}
				minYear++;
			}else if(minYear == year){
				for(int i = 0; i < quarter; i++){
					String localkey = year + "_" + i;
					totalrzxq += Float.parseFloat(rzxq.get(localkey)== null ? "0":rzxq.get(localkey));
				}
				istrue = false;
			}
			
		}
		float value = Float.parseFloat(cbkrz.get(key)== null ? "0": cbkrz.get(key) ) - totalrzxq;
		store.remove(key);
		store.put(key, String.valueOf(value));
		cache.remove(fieldName);
		cache.put(fieldName, store);
		
	}
	
	/**
	 * 
	 * <br>Description:计算储备融资缺口，第一年度
	 * <br>Author:黎春行
	 * <br>Date:2013-11-8
	 */
	private void calCulatecbkrzqk(int year, int quarter ){
		//第一年度为：供应规模*抵押率*评估土地价值*（1-融资损失）
		String fieldName = "cbkrzqk";
		Map<String, String> store = cache.get(fieldName);
		Map<String, String> gygm = cache.get("gygm");
		ExtendData extendData = new ExtendData();
		Map<String, String> parameters = extendData.getParameters();
		String key = year + "_" + quarter;
		Float gygmValue = Float.parseFloat(gygm.get(key)== null ? "0":gygm.get(key));
		String dyl = parameters.get("dyl");
		//long value = gygmValue * Float.parseFloat(parameters.get("pgtdjz")) * Long.parseLong(dyl) * (1 - Long.parseLong(parameters.get("rzss")));
		float value =  gygmValue * Float.parseFloat(parameters.get("pgtdjz")) * Float.parseFloat(dyl) * (1 - Float.parseFloat(parameters.get("rzss")));
		store.remove(key);
		store.put(key, String.valueOf(value));
		cache.remove(fieldName);
		cache.put(fieldName, store);
		
	}
	/**
	 * 
	 * <br>Description:计算权益性资金注入
	 * <br>Author:黎春行
	 * <br>Date:2013-11-8
	 */
	private void calCulateQYXZJZR(int year, int quarter){
		
		
	}
	/**
	 * 
	 * <br>Description:计算本期融资需求
	 * <br>Author:黎春行
	 * <br>Date:2013-11-8
	 */
	private void calCulateBQRZXQ(int year, int quarter){
		//本期投资需求 + 本期还款需求 - 本期回笼成本 - 本期权益性资金注入 - 上期账面余额 > 0 时，需要融资，否则为0
		String fieldName = "bqrzxq";
		Map<String, String> store = cache.get(fieldName);
		String key = year + "_" + quarter;
		int cacluQuarter = 0;
		int cacluYear = 0;
		if(quarter == 1){
			cacluYear = year - 1;
			cacluQuarter = 4;
		}else{
			cacluYear = year;
			cacluQuarter = quarter - 1;
		}
		Map<String, String> zmyeMap = cache.get("bqzmye");
		Map<String, String> hlcbMap = cache.get("bqhlcb");
		Map<String, String> qyxzjzrMap = cache.get("qyxzjzr");
		Map<String, String> bqhkxqMap = cache.get("bqhkxq");
		Map<String, String> bqtzxqMap = cache.get("bqtzxq");
		float zmye = Float.parseFloat(zmyeMap.get(cacluYear + "_" + cacluQuarter)== null ? "0":zmyeMap.get(cacluYear + "_" + cacluQuarter));
		float hlcb = Float.parseFloat(hlcbMap.get(key)== null ? "0":hlcbMap.get(key));
		float qyxzj = Float.parseFloat(qyxzjzrMap.get(key)== null ? "0":qyxzjzrMap.get(key));
		float bqhkxq = Float.parseFloat(bqhkxqMap.get(key)== null ? "0":bqhkxqMap.get(key));
		float bqtzxq = Float.parseFloat(bqtzxqMap.get(key)== null ? "0":bqtzxqMap.get(key));
		float value = bqtzxq + bqhkxq - hlcb - qyxzj - zmye;
		if(value < 0){
			value = 0;
		}
		store.remove(key);
		store.put(key, String.valueOf(value));
		cache.remove(fieldName);
		cache.put(fieldName, store);
	}
	
	/**
	 * 
	 * <br>Description:计算负债余额
	 * <br>Author:黎春行
	 * <br>Date:2013-11-8
	 */
	private void calCulateFZYE(int year, int quarter){
		//本期融资需求-本期还款需求+上期负债余额
		String fieldName = "bqrzxq";
		Map<String, String> store = cache.get(fieldName);
		String key = year + "_" + quarter;
		int cacluQuarter = 0;
		int cacluYear = 0;
		if(quarter == 1){
			cacluYear = year - 1;
			cacluQuarter = 4;
		}else{
			cacluYear = year;
			cacluQuarter = quarter - 1;
		}
		Map<String, String> rzxqMap = cache.get("bqrzxq");
		Map<String, String> hkxqMap = cache.get("bqhkxq");
		Map<String, String> fzyeMap = cache.get("fzye");
		float fzye = Float.parseFloat(fzyeMap.get(cacluYear + "_" + cacluQuarter)== null ? "0":fzyeMap.get(cacluYear + "_" + cacluQuarter));
		float rzxq = Float.parseFloat(rzxqMap.get(key)== null ? "0":rzxqMap.get(key));
		float hkxq = Float.parseFloat(hkxqMap.get(key)== null ? "0":hkxqMap.get(key));
		float value = rzxq + fzye - hkxq;
		store.remove(key);
		store.put(key, String.valueOf(value));
		cache.remove(fieldName);
		cache.put(fieldName, store);
	}	
	/**
	 * 
	 * <br>Description:计算资金风险
	 * <br>Author:黎春行
	 * <br>Date:2013-11-8
	 */
	private void calCulateZJFX(int year, int quarter){
		//上期账面余额+本期回笼成本+权益性资金注入-本期还款需求
		String fieldName = "zjfx";
		Map<String, String> store = cache.get(fieldName);
		String key = year + "_" + quarter;
		int cacluQuarter = 0;
		int cacluYear = 0;
		if(quarter == 1){
			cacluYear = year - 1;
			cacluQuarter = 4;
		}else{
			cacluYear = year;
			cacluQuarter = quarter - 1;
		}
		Map<String, String> zmyeMap = cache.get("bqzmye");
		Map<String, String> hlcbMap = cache.get("bqhlcb");
		Map<String, String> qyxzjzrMap = cache.get("qyxzjzr");
		Map<String, String> hkxqMap = cache.get("bqhkxq");
		float zmye = Float.parseFloat(zmyeMap.get(cacluYear + "_" + cacluQuarter)== null ? "0":zmyeMap.get(cacluYear + "_" + cacluQuarter));
		float hlcb = Float.parseFloat(hlcbMap.get(key)== null ? "0":hlcbMap.get(key));
		float qyxzjzr = Float.parseFloat(qyxzjzrMap.get(key)== null ? "0":qyxzjzrMap.get(key));
		float hkxq = Float.parseFloat(hkxqMap.get(key)== null ? "0":hkxqMap.get(key));
		float value = zmye + hlcb + qyxzjzr - hkxq;
		store.remove(key);
		store.put(key, String.valueOf(value));
		cache.remove(fieldName);
		cache.put(fieldName, store);
	}
	/**
	 * 
	 * <br>Description:计算本期账面余额
	 * <br>Author:黎春行
	 * <br>Date:2013-11-8
	 */
	private  void calCulateBQZMYE(int year, int quarter){
		//本期回笼成本-本期投资需求-本期还款需求+权益性资金注入+本期融资需求+上期账面余额
		String fieldName = "bqzmye";
		Map<String, String> store = cache.get(fieldName);
		String key = year + "_" + quarter;
		int cacluQuarter = 0;
		int cacluYear = 0;
		if(quarter == 1){
			cacluYear = year - 1;
			cacluQuarter = 4;
		}else{
			cacluYear = year;
			cacluQuarter = quarter - 1;
		}
		Map<String, String> zmyeMap = cache.get("bqzmye");
		Map<String, String> hlcbMap = cache.get("bqhlcb");
		Map<String, String> qyxzjzrMap = cache.get("qyxzjzr");
		Map<String, String> hkxqMap = cache.get("bqhkxq");
		Map<String, String> tzxqMap = cache.get("bqtzxq");
		Map<String, String> rzxqMap = cache.get("bqrzxq");
		float zmye = Float.parseFloat(zmyeMap.get(cacluYear + "_" + cacluQuarter)== null ? "0":zmyeMap.get(cacluYear + "_" + cacluQuarter));
		float hlcb = Float.parseFloat(hlcbMap.get(key)== null ? "0":hlcbMap.get(key));
		float tzxq = Float.parseFloat(tzxqMap.get(key)== null ? "0":tzxqMap.get(key));
		float qyxzjzr = Float.parseFloat(qyxzjzrMap.get(key)== null ? "0":qyxzjzrMap.get(key));
		float hkxq = Float.parseFloat(hkxqMap.get(key)== null ? "0":hkxqMap.get(key));
		float rzxq = Float.parseFloat(rzxqMap.get(key)== null ? "0":rzxqMap.get(key));
		float value = hlcb - tzxq - hkxq + qyxzjzr + rzxq + zmye;
		store.remove(key);
		store.put(key, String.valueOf(value));
		cache.remove(fieldName);
		cache.put(fieldName, store);
	}
	
}
