package com.klspta.model.resourcetree;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;

/**
 * <br>Title:资源树操作类
 * <br>Description:资源树构建
 * <br>Author:李亚栓
 * <br>Date:2012-08-06
 */
public class TreeOperation extends AbstractBaseBean  implements ITreeOperation {

    private static TreeOperation treeOperation ;

    private TreeOperation() {
    }

    public static TreeOperation getInstance() {
        if (treeOperation == null) {
            treeOperation = new TreeOperation();
        }
        return treeOperation;
    }
    /**
     * <br>构建资源树
     * <br>Author:李亚栓
     * <br>Date:2012-08-06
     * @param tree_name 所要构建资源树的名称
     * @return TreeBean
     */
	@Override
    public TreeBean getTree(HashMap<String, String> map) {
        String zfjcType = map.get("zfjcType").toString();
        String tree_name = TreeOperation.getInstance().getTreeNameByYwType(zfjcType);
        String yw_guid=map.get("yw_guid");
        String sql = "select * from core_resourcetree where tree_name=?";
        Object[] args_tree_name = { tree_name };
        List<Object> resultList = query(sql, AbstractBaseBean.CORE, args_tree_name, new TreeRowMapper());
        
        if(resultList.size() ==1){
        	TreeBean treeBean = (TreeBean) resultList.get(0);
        	String tree = treeBean.getContent();//调用方法把InputStream转成String
        	int i = tree.indexOf("dynamic"); //获得dynamic的位置  
        	while (i != -1) { //如果存在dynamic,以下开始进行替换操作
        		int m = tree.lastIndexOf("{", i); //获得dynamic所在节点的"{"位置
        		int n = tree.indexOf("}", i); //获得dynamic所在节点的"}"位置	
        		String dynamic = tree.substring(m, n + 1); //截取dynamic内容
        		
        		// 以下开始利用反射得到将要替换的节点内容
        		int a = tree.indexOf("class", i); //获得class所在位置
        		int b = tree.indexOf("'", a); //获得class所在节点第一个"'"的位置
        		int c = tree.indexOf("'", b + 1); //获得class所在节点第二个"'"的位置
        		String className = tree.substring(b + 1, c); //截取class内容
        		String replace_str = null;
        		try {
        			Class cls = Class.forName(className);
        			Class[] cargs = new Class[1];
        			cargs[0] = HashMap.class;
        			Method method = cls.getMethod("getDynamicTree", cargs);
        			replace_str = (String) method.invoke(cls.newInstance(), map);
        			
        		} catch (ClassNotFoundException e) {
        			System.out.println("没有找到反射所需的类");
        			e.printStackTrace();
        		} catch (NoSuchMethodException e) {
        			System.out.println("类中没有所需方法");
        			e.printStackTrace();
        		} catch (IllegalAccessException e) {
        			e.printStackTrace();
        		} catch (InvocationTargetException e) {
        			e.printStackTrace();
        		} catch (InstantiationException e) {
        			e.printStackTrace();
        		}
        		// 反射结束
        		
        		tree = tree.replace(dynamic, replace_str); //替换树种的dynamic部分
        		treeBean.setContent(tree); //重新对content赋值
        		i = tree.indexOf("dynamic", i + 1);
        	}
        	String treeStr=treeBean.getContent();
        	/**
        	if(zfjcType.equals("7")){
        		if(ywHead.equals("XC")){
        			treeStr=treeStr+",{text:'动态巡查成果',leaf:0,id:'5'," +
        			"children:[" +
        			"{text:'实地巡查情况表',leaf:1,id:'501',src:'web/jinan/dtxc/xccg/xckcqk.jsp'}," +
        			"{text:'巡查位置查看',leaf:1,id:'502',src:'base/fxgis/framework/gisViewFrame.jsp?flag=1'}]}";
        		}else if(ywHead.equals("XF")){
        			treeStr=treeStr+",{text:'信访事项',leaf:0,id:'5',children:[{text:'信访案件登记表',leaf:1,id:'501',src:'web/jinan/xfaj/xzxfaj/xfajdjb.jsp?jdbcname=YWTemplate&'}]}";
        		}else if(ywHead.equals("WP")){
        			
        		}
        	}
        	*/
        	treeBean.setContent(treeStr); //重新对content赋值
        	return treeBean;
        }
        return null;
    }


    /**
     * <br>Description:根据业务类型，获得资源树名称
     * <br>Author:李亚栓
     * <br>Date:2012-08-06
     * @param yw_type
     * @return
     */
    
    public String getTreeNameByYwType(String yw_type) {
        String sql = "select t.child_name from public_code t where t.id='RESOURCETREE' and t.child_id=? and t.in_flag='1'";
        Object[] args = { yw_type };
        List<Map<String, Object>> resultList = query(sql, AbstractBaseBean.YW, args);
        return String.valueOf(resultList.get(0).get("child_name"));

    }
    /**
     * <br>Description:获取workflow树的参数
     * <br>Author:李亚栓
     * <br>Date:2012-8-6
     * @param yw_type
     * @return
     */
    public String getWFTreeName(String wfid, String nodename) {
    	if(wfid ==null || nodename ==null){
    	return null;	
    	}
    	else{             
    		String sql = "select treename from MAP_WORKFLOW_TREE where wfid=? and nodename=?";
    		Object[] args = { wfid,nodename };
    		List<Map<String, Object>> list=query(sql,CORE,args);
    		String treename=null;
    		if(list.size()>0){
    			Map<String, Object> map=list.get(0);
    			if(map.get("treename")!=null){	 
    				treename=(String)map.get("treename");
    			} 
    		}       
    		return treename;
    	}
    }
    /**
     * <br>Description:获取workflow树的参数
     * <br>Author:李亚栓
     * <br>Date:2012-8-6
     * @param yw_type
     * @return
     */
    public String[] getWFTree(String wfid, String nodename) {
        String sql = "select nodeids from map_workflow_tree where wfid=? and nodename=?";
        Object[] args = { wfid,nodename };
        List<Map<String, Object>> list=query(sql,CORE,args);
        String[] nodes=null;
        if(list.size()>0){
	        Map<String, Object> map=list.get(0);
	        if(map.get("nodeids")!=null){	 
	        	String nodeids=(String)map.get("nodeids");
	        	nodes=nodeids.split(",");
	        }
        }       
        return nodes;
    }
}
