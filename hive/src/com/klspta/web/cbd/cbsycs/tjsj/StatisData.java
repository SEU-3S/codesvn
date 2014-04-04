package com.klspta.web.cbd.cbsycs.tjsj;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import com.klspta.base.AbstractBaseBean;

public class StatisData extends AbstractBaseBean {
    /**
     * 
     * <br>Description:绑定数据源
     * <br>Author:王雷
     * <br>Date:2013-8-22
     */
    public void bindData(){
       String[] array = new String[9];
       for(int i=0;i<array.length;i++){
           array[i]=(i+2013)+"";
       }
       
       String sql1="select  sum(k.征收户数) hs,1000 as kz,sum(k.完成开发地量) dl,sum(k.完成开发规模) gm from  v_开发体量 k where k.年度 = ?";
       List<Object> allList = new ArrayList<Object>();
       List<Map<String,Object>> list1 = null;
       for(int i=0;i<array.length;i++){
           list1 = query(sql1,YW,new Object[]{array[i]});
           allList.add(list1);
       }
              
       String sql2="select  sum(a.开工及购房量) kgl,sum(a.投资) touz, (sum(a.使用量)+sum(a.安置房存量)) ksygm, sum(a.使用量) syl,sum(a.安置房存量) azfcl from v_安置房 a where a.年度=?";
       List<Map<String,Object>> list2 = null;
       for(int i=0;i<array.length;i++){
           list2 = query(sql2,YW,new Object[]{array[i]});
           allList.add(list2);
       }       
             
       String sql3="select  sum(g.供应规模+g.储备库库存) jdkcrgm,sum(g.供应规模) gygm,sum(g.储备库库存) cbkc,sum(g.储备库融资能力) cbkrznl,'' cbkzrznl from v_供地体量 g where g.年度=?";
       List<Map<String,Object>> list3 = null;
       for(int i=0;i<array.length;i++){
           list3 = query(sql3,YW,new Object[]{array[i]});
           allList.add(list3);
       } 
       
       String sql4="select (sum(t.bqtzxq)+sum(t.bqhkxq)) ndzc,(sum(t.bqhlcb)+sum(t.bqrzxq)+sum(t.qyxzjzr)) ndsr, '' qyxzjgm,sum(t.bqtzxq) ndtzxq,sum(t.bqhlcb) ndhlcb,sum(t.zftdsy) zftdsy,sum(t.bqrzxq) ndrzxq,sum(t.bqhkxq) ndhkxq,sum(t.bqrzxq) zwzjsygm,(sum(t.bqtzxq)-sum(t.bqrzxq)) zyzjsygm,sum(t.qyxzjzr) qyxzjzr,sum(t.fzye) fzye,'' cbkrzqk,sum(t.bqzmye) ndzmye from plan投融资情况 t where t.nd=?";
       List<Map<String,Object>> list4 = null;
       for(int i=0;i<array.length;i++){
           list4 = query(sql4,YW,new Object[]{array[i]});
           allList.add(list4);
       }       
            
       response(JSONArray.fromObject(allList).toString());   
    }
    
    
}
