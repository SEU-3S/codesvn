package com.klspta.web.cbd.yzt.cbjhzhb;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.klspta.base.AbstractBaseBean;


public class CbjhManager extends AbstractBaseBean {
	
    public void update() throws Exception{
    	String xmmc =new String(request.getParameter("key").getBytes("iso-8859-1"), "UTF-8");
    	String index = request.getParameter("vindex");
    	String value = new String(request.getParameter("value").getBytes("iso-8859-1"), "UTF-8");
    	String field = Cbjhzhb.fields[Integer.parseInt(index) - 1][0];
    	//boolean result = new CbjhData().update(xmmc, field, value);
    	CbjhData cbjhData = new CbjhData();
    	cbjhData.setChange(xmmc, field, value);
    	ExecutorService exec = Executors.newCachedThreadPool();
    	exec.execute(cbjhData);
    	exec.shutdown();
    }
	
    String[] str = {"XMLX","XMQX","DKMC","XMWZ","XMQW","XMLB","XMZT","SFDZ","DZBV","XMZTAI",
      		"ZMJ","NYDMJ","QZGDMJ","DCTDMJ","JSYDXJ","JSYDJZ","JSYDSF","JSYDQT","GHDZD","GHJZXJ","GHJZJZ","GHJZSF",
      		"GHJZQT","SXGHNF","SXSQSJ","SXGHTJ","SXYSPF","SXHZPF","SXZDPF","SXCQXK","ZDZMJ","YWCZDZMJ","QYZDMJ","YZDMJ",
      		"JHZDMJ","CQJZMJ","CQCQBL","CQJHMJ","TZJHTZ","TZYWC","YTZXJ","YTZSZX","YTZFZX","YTZQY","JHXJ","JHZCB",
      		"JHSZX","JHFZX","JHQY","ZJHLXJ","ZJHLSZX","ZJHLFZX","ZJHLQY","LJWCKF","XCYSMJ","SFJHWC","JHWCSJ","JHWCMJ",
      		"GHYDXJ","GHYDJZ","GHYDSF","GHYDQT","WCGHJZXJ","WCGHJZJZ","WCGHJZSF","WCGHJZQT","WCKFQBCD","WCKFPX",
      		"LJGYMJ","DNGYMJ","SFJMNGY","JHGYSJ","JHGYMJ","GHYDMJXJ","GHYDMJJZ","GHYDMJSF","GHYDMJQT","GHJZGMXJ",
      		"GHJZGMJZ","GHJZGMSF","GHJZGMQT","SXGYQBCD","SXGYPX","ZYWT","SFXCFW","GDJTX","XZLY","BZ"};
    
    public void modify(){
    	String xmmc = request.getParameter("XMMC");
    	String sql = "";
    	for(int i=0; i< str.length;i++){
    		sql = "update jc_cbjhzhb set "+str[i]+"=? where xmmc = ?";
    		update(sql,YW,new Object[]{request.getParameter(str[i]),xmmc});
    	}
    	response("{success:true}");
    }
}
