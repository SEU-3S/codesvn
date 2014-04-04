package com.klspta.web.cbd.qyjc.common;

import java.util.List;
import java.util.Map;


public class ModelFactory {
  
    /****
     * 
     * <br>Description:2年测算展现
     * <br>Author:朱波海
     * <br>Date:2014-1-7
     * @param year
     * @param type
     * @return
     */
    public static String getMoreTab(String [] year, String tabName){
        BuildModel buildModel = new BuildModel();
        DataInteraction interaction = new DataInteraction();
        List<Map<String, Object>> list1 = interaction.getDateList(year[0],tabName);
        List<Map<String, Object>> cont1 = interaction.getCont(year[0], tabName);
        List<Map<String, Object>> list2 = interaction.getDateList(year[1],tabName);
        List<Map<String, Object>> cont2 = interaction.getCont(year[0], tabName);
        String two_year = buildModel.getMode1(list1, list2, year,cont1,cont2);
        return two_year;
    }
  
    
    
    /****
     * 
     * <br>Description:资金管理基本信息
     * <br>Author:朱波海
     * <br>Date:2014-1-6
     * @return
     */
    public static String getZjqk(){
        BuildModel buildModel = new BuildModel();
        DataInteraction interaction = new DataInteraction();
        List<Map<String, Object>> list = interaction.getXzl_Zjqk_xx();
        String table = buildModel.getZjqkTable(list);
        return  table;
    }
    /*****
     * 
     * <br>Description:资金管理年度录入
     * <br>Author:朱波海
     * <br>Date:2014-1-6
     * @return
     */
    public static String getZjqk_nd(String year){
        BuildModel buildModel = new BuildModel();
        DataInteraction interaction = new DataInteraction();
        List<Map<String, Object>> list = interaction.getXzl_Zjqk_pjlm();
        List<Map<String, Object>> cont1 = interaction.getCont(year, "XZLZJQKND_PJLM");
        List<Map<String, Object>> list2 = interaction.getXzl_Zjqk_pjzj();
        List<Map<String, Object>> cont2 = interaction.getCont(year, "XZLZJQKND_PJZJ");
        String table = ((BuildModel) buildModel).getZjqkNd(list,list2,cont1,cont2);
        return  table;
        
    }
   
    } 
    
