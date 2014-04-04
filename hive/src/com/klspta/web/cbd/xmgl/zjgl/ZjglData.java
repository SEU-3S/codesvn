package com.klspta.web.cbd.xmgl.zjgl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.klspta.base.AbstractBaseBean;
/***
 * 
 * <br>Title:资金管理数据库交互类
 * <br>Description:查、跟新等操作
 * <br>Author:朱波海
 * <br>Date:2013-12-26
 */
public class ZjglData extends AbstractBaseBean {
    private  String lr_name[]={"1.1 筹融资金","1.1.1 金融机构贷款","1.1.2 实施主体带资","1.1.3 国有土地收益基金","1.2 出让回笼资金","1.3 其他资金"};
    private  String lr_type[]={"CRZJ","ZRJGDK","SSZTDZ","GYTDSYJJ","CRHLZJ","QTZJ"};
    private  String zc_chaild[]={"总计划审批","贷款审批","实施主体带资审批","国有土地收益基金审批","出让回笼资金审批","其他资金审批","实际支付","已批未付"};
    private  String zc_parent[]={"2.1.1 前期费用","2.1.2 拆迁费用","2.1.3 收储费用","2.1.4 市政费用","2.1.5 财务费用","2.1.6 管理费用","2.2 筹融资金返还","2.3 其他支出"};
    private  String zc_type[]={"QQFY","CQFY","SCFY","SZFY","CWFY","GLFY","CRZJFH","QTZC"};

    public List<Map<String, Object>> getZJGL_ZJLR(String yw_guid ,String year) {
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        String sql = "select * from xmzjgl_lr_view where yw_guid=? and rq=? order by lb desc";
        List<Map<String, Object>> querys = query(sql, YW, new Object[] { yw_guid,year });
      //  String sqlString="  select  yw_guid,lb,status,YSFY,ZJJD,CQYE,YFSDZ,yy,ey,sany,siy,wy,ly,qy,bay,jy,siyue,syy,sey,(cqye+yy+ey+sany+siy+wy+ly+qy+bay+jy+siyue+syy+sey) as LJ,(yy+ey+sany+siy+wy+ly+qy+bay+jy+siyue+syy+sey) as LRSP,ZCSTATUS,SORT from xmzjgl_lr_view where yw_guid=? and (sort='2'or sort='3' or sort='4')";
//       String sq= "select '1.1 筹融资金' as lb,'CRZJ' as status,' " +yw_guid+" ' as yw_guid,sum(YSFY) as YSFY,sum(ZJJD)as ZJJD ,sum(CQYE)as CQYE ,sum(YFSDZ)as YFSDZ,sum(yy)as yy ,sum(ey)as ey,sum(sany)as sany,sum(siy)as siy,sum(wy)as wy,sum(ly)as ly,sum(qy)as qy,sum(bay)as bay,sum(jy)as jy,sum(siyue)as siyue,sum(syy)as syy,sum(sey)as sey,sum((cqye+yy+ey+sany+siy+wy+ly+qy+bay+jy+siyue+syy+sey)) as LJ,sum((yy+ey+sany+siy+wy+ly+qy+bay+jy+siyue+syy+sey)) as LRSP FROM xmzjgl_lr_view where yw_guid=? and rq=? and (sort='2'or sort='3' or sort='4')";
//        List<Map<String, Object>> query = query(sq, YW,new Object[]{yw_guid,year});
//        if(querys.size()==6){
//            list.add(query.get(0));
//            list.add(querys.get(1));
//            list.add(querys.get(2));
//            list.add(querys.get(3));
//            list.add(querys.get(4));
//            list.add(querys.get(5));
//        }
        //20140220 change by 黎春行
        for(int i = querys.size() - 1; i >= 0; i--){
        	list.add(querys.get(i));
        }
       return list;
    }

    public List<Map<String, Object>> getZJGL_father(String yw_guid, String type,String year) {
        int cols[] ={0,0,0,0,0,0,0,0};
        String sql = "select * from  XMZJGL_ZC_view where status=? and yw_guid=?  and rq=? and zcstatus is  null order by  sort";
        List<Map<String, Object>> list = query(sql, YW, new Object[] { type, yw_guid,year });
        if(list.size()==8){
            for(int i=0;i<8;i++){
           cols[i]=Integer.parseInt(list.get(i).get("JL2").toString());
            }
            list.get(0).remove("JL2");
            list.get(0).put("JL2", cols[1]+cols[2]+cols[3]+cols[4]+cols[5]);
            list.get(7).remove("JL2");
            list.get(7).put("JL2", cols[1]+cols[2]+cols[3]+cols[4]+cols[5]-cols[6]);    
           }
        return list;
    }

    public List<Map<String, Object>> getZJGL_child(String yw_guid, String tree_name, String type,String year) {
        String sql = "select * from  XMZJGL_ZC_view where status=? and yw_guid=? and lb=? and rq=? and zcstatus is not null order by  sort ";
        List<Map<String, Object>> list = query(sql, YW, new Object[] { type, yw_guid, tree_name ,year});
        return list;
    }
    
    public void setMX( String yw_guid,String year){
        if(yw_guid!=null&&!yw_guid.equals("")){
        String sql="select yw_guid from XMZJGL_ZC_view where yw_guid=? and rq=?";
        List<Map<String, Object>> query = query(sql, YW,new Object []{yw_guid,year});
        if(query.size()>0){
        }else{
        for(int i=0;i<zc_parent.length;i++){
            for(int j=0;j<zc_chaild.length;j++){
                String sqlString="insert into XMZJGL_ZC (yw_guid,status,lb,lj,sort,rq) values(?,?,?,?,?,?)";
                update(sqlString, YW,new Object[]{yw_guid, zc_type[i],zc_parent[i],zc_chaild[j],(j+1),year});
            }
        }
//        for(int k=0;k<lr_name.length;k++){
//            String sqlString="insert into XMZJGL_LR (yw_guid,status,lb,sort,rq) values(?,?,?,?,?)";
//            update(sqlString, YW,new Object[]{yw_guid,lr_type[k],lr_name[k],(k+1),year});
//        }
        }
        }
    }
    public void saveNode(String tree_name,String type,String yw_guid,String year){
    	if(!(type.equals("ZJLR") || tree_name.startsWith("1") )){
	        for(int j=0;j<zc_chaild.length;j++){
	            String sqlString="insert into XMZJGL_ZC (yw_guid,status,lb,lj,zcstatus,sort ,rq) values(?,?,?,?,?,?,?)";
	            update(sqlString, YW,new Object[]{yw_guid, type,tree_name,zc_chaild[j],type,(j+1),year});
	        }
        }else{
        	String sqlString="insert into XMZJGL_LR (yw_guid,status,lb,rq) values(?,?,?,?)";
            update(sqlString, YW,new Object[]{yw_guid, tree_name,tree_name,year});
        }
    }
    
    public List<Map<String, Object>> getLR_sum(String yw_guid ,String year){
        String sql="select 'Ⅰ.资金流入' as lb,sum(ysfy) as ysfy,sum(lj) as lj,sum(yfsdz)as yfsdz,sum(zjjd) as zjjd,sum(cqye)as cqye ,sum(yy)as yy,sum(ey)as ey,sum(sany)as sany,sum(siy)as siy,sum(wy)as wy,sum(ly)as ly ,sum(qy)as qy ,sum(bay)as bay,sum(jy)as jy,sum(siyue)as siyue,sum(syy)as syy,sum(sey)as sey ,sum(lrsp)as lrsp   from xmzjgl_lr_view t where yw_guid=? and rq=?";
        List<Map<String, Object>> query = query(sql, YW,new Object[]{yw_guid,year});
        return query;
    }
    public List<Map<String, Object>>  getZC_sum(String yw_guid,String year){
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        int  cols[] ={0,0,0,0,0,0,0,0};
        for(int i=1;i<9;i++){
        String sql="select 'Ⅱ.资金支出' as lb,sum(ysfy) as ysfy,'"+zc_chaild[i-1]+"' as lj,sum(jl2) as jl2 ,sum(yfsdz)as yfsdz,sum(zjjd) as zjjd,sum(cqye)as cqye ,sum(yy)as yy,sum(ey)as ey,sum(sany)as sany,sum(siy)as siy,sum(wy)as wy,sum(ly)as ly ,sum(qy)as qy ,sum(bay)as bay,sum(jy)as jy,sum(siyue)as siyue,sum(syy)as syy,sum(sey)as sey ,sum(lrsp)as lrsp   from XMZJGL_ZC_view t where yw_guid=? and sort=? and rq=?";
        List<Map<String, Object>> query = query(sql, YW,new Object[]{yw_guid,i,year});
        list.add(query.get(0));
        cols[i-1]=Integer.parseInt(query.get(0).get("JL2")==null?"0":query.get(0).get("JL2").toString());
        }
        list.get(0).remove("JL2");
        list.get(0).put("JL2", cols[1]+cols[2]+cols[3]+cols[4]+cols[5]);
        list.get(7).remove("JL2");
        list.get(7).put("JL2", cols[1]+cols[2]+cols[3]+cols[4]+cols[5]-cols[6]);
        return list;
    }
    public List<Map<String, Object>>  getZC_YJZC_sum(String yw_guid,String year){
        int cols[] ={0,0,0,0,0,0,0,0};
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        for(int i=1;i<9;i++){
        String sql="select '2.1 一级开发支出' as lb,sum(ysfy) as ysfy,'"+zc_chaild[i-1]+"' as lj,sum(jl2) as jl2 ,sum(yfsdz)as yfsdz,sum(zjjd) as zjjd,sum(cqye)as cqye ,sum(yy)as yy,sum(ey)as ey,sum(sany)as sany,sum(siy)as siy,sum(wy)as wy,sum(ly)as ly ,sum(qy)as qy ,sum(bay)as bay,sum(jy)as jy,sum(siyue)as siyue,sum(syy)as syy,sum(sey)as sey ,sum(lrsp)as lrsp   from XMZJGL_ZC_view t where yw_guid=? and sort=? and (status !='QTZC' and status !='CRZJFH' and rq=?)";
        List<Map<String, Object>> query = query(sql, YW,new Object[]{yw_guid,i,year});
        list.add(query.get(0));
        cols[i-1]=Integer.parseInt(query.get(0).get("JL2")==null?"0":query.get(0).get("JL2").toString());
        }
        list.get(0).remove("JL2");
        list.get(0).put("JL2", cols[1]+cols[2]+cols[3]+cols[4]+cols[5]);
        list.get(7).remove("JL2");
        list.get(7).put("JL2", cols[1]+cols[2]+cols[3]+cols[4]+cols[5]-cols[6]);
        return list;
    }
    
    
}
