package com.klspta.web.cbd.qyjc;

import java.util.List;
import java.util.Map;

import com.klspta.base.AbstractBaseBean;
import com.klspta.base.util.UtilFactory;
import com.klspta.model.CBDReport.CBDReportManager;
import com.klspta.model.CBDReport.tablestyle.ITableStyle;
import com.klspta.web.cbd.qyjc.common.BuildModel;
import com.klspta.web.cbd.qyjc.common.DataInteraction;
import com.klspta.web.cbd.qyjc.common.ModelFactory;
import com.klspta.web.cbd.yzt.jc.report.TableStyleEditRow;
import com.klspta.web.cbd.yzt.zrb.ZrbData;

public class QyjcManager extends AbstractBaseBean {
		private static QyjcManager qyjcManager;

	    public static QyjcManager getInstcne() {
	        if (qyjcManager == null) {
	        	qyjcManager = new QyjcManager();
	        }
	        return qyjcManager;
	    }

	    public String getList() {
	        String sql = "select t.bh,t.xzlmc,t.kfs,t.wygs,t.tzf,t.sq,t.cpdw,t.cplx,t.cylx,t.rzqy,t.kpsj,t.ysxkz,t.cbcs,t.lc,t.bzcg,t.wq,t.cn,t.gd,t.gs,t.dt,t.gdcw,t.tcwzj,t.syl,t.qt from xzlxx t";
	        List<Map<String, Object>> list = query(sql, YW);
	        StringBuffer result = new StringBuffer(
	                "<table id='XZLZJ' width='3000' border='1' cellpadding='1' cellspacing='0'>" +
	                "<tr class='tr01' ><td id='0_0' height='10' width='50' colspan='1' rowspan='2' class='tr01'>编号</td>" +
	                "<td id='0_1' height='10' width='100' colspan='1' rowspan='2' class='tr01'>写字楼名称</td>" +
	                "<td id='0_2' height='10' width='100' colspan='1' rowspan='2' class='tr01'>开发商</td>" +
	                "<td id='0_3' height='10' width='100' colspan='1' rowspan='2' class='tr01'>物业公司</td>" +
	                "<td id='0_4' height='10' width='100' colspan='1' rowspan='2' class='tr01'>投资方</td>" +
	                "<td id='0_4' height='10' width='100' colspan='1' rowspan='2' class='tr01'>商圈</td>" +
	                "<td id='0_5' height='10' width='100' colspan='3' rowspan='1' class='tr01'>产品</td>" +
	                "<td id='0_6' height='10' width='100' colspan='14' rowspan='1' class='tr01'>项目</td>" +
	                "<td id='0_7' height='10' width='300' colspan='1' rowspan='2' class='tr01'>其他</td>" +
	                "<td id='0_7' height='10' width='100' colspan='1' rowspan='2' class='tr01'>操作</td>" +
	                "</tr><tr class='trtotal' ><td id='1_0' height='10' width='100' colspan='1' rowspan='1' class='tr01'>产品定位</td>" +
	                "<td id='1_1' height='10' width='100' colspan='1' rowspan='1' class='tr01'>产品类型</td>" +
	                "<td id='1_2' height='10' width='100' colspan='1' rowspan='1' class='tr01'>产业类型</td>" +
	                "<td id='1_3' height='10' width='100' colspan='1' rowspan='1' class='tr01'>入驻企业</td>" +
	                "<td id='1_4' height='10' width='100' colspan='1' rowspan='1' class='tr01'>开盘时间</td>" +
	                "<td id='1_5' height='10' width='100' colspan='1' rowspan='1' class='tr01'>预售许可证</td>" +
	                "<td id='1_6' height='10' width='100' colspan='1' rowspan='1' class='tr01'>成本测算</td>" +
	                "<td id='1_7' height='10' width='100' colspan='1' rowspan='1' class='tr01'>楼层</td>" +
	                "<td id='1_8' height='10' width='100' colspan='1' rowspan='1' class='tr01'>标准层高（米）</td>" +
	                "<td id='1_9' height='10' width='100' colspan='1' rowspan='1' class='tr01'>外墙</td>" +
	                "<td id='1_10' height='10' width='100' colspan='1' rowspan='1' class='tr01'>采暖</td>" +
	                "<td id='1_11' height='10' width='100' colspan='1' rowspan='1' class='tr01'>供电</td>" +
	                "<td id='1_12' height='10' width='100' colspan='1' rowspan='1' class='tr01'>供水</td>" +
	                "<td id='1_13' height='10' width='100' colspan='1' rowspan='1' class='tr01'>电梯</td>" +
	                "<td id='1_14' height='10' width='100' colspan='1' rowspan='1' class='tr01'>固定车位（个）</td>" +
	                "<td id='1_15' height='10' width='150' colspan='1' rowspan='1' class='tr01'>停车位租价（元/月·个）</td>" +
	                "<td id='1_16' height='10' width='100' colspan='1' rowspan='1' class='tr01'>使用率</td></tr>  ");
	        result.append("<tr id='newRow' class='tr02' style='display:none;'>"
	        +"<td  class='td1'><input  id='bh'/ ></td>"
	        +"<td  class='td1'><input  id='xzlmc' /></td>"
	        +"<td  class='td1'><input  id='kfs' /></td>"
	        +"<td  class='td1'><input  id='wygs' /></td>"
	        +"<td  class='td1'><input  id='tzf' /></td>"
	        +"<td  class='td1'><input  id='sq' /></td>"
	        +"<td  class='td1'><input  id='cpdw' /></td>"
	        +"<td  class='td1'><input  id='cplx' /></td>"
	        +"<td  class='td1'><input  id='cylx' /></td>"
	        +"<td  class='td1'><input  id='rzqy' /></td>"
	        +"<td  class='td1'><input  id='kpsj' /></td>"
	        
	        +"<td  class='td1'><input  id='ysxkz'/ ></td>"
	        +"<td  class='td1'><input  id='cbcs' /></td>"
	        +"<td  class='td1'><input  id='lc' /></td>"
	        +"<td  class='td1'><input  id='bzcg' /></td>"
	        +"<td  class='td1'><input  id='wq' /></td>"
	        +"<td  class='td1'><input  id='cn' /></td>"
	        +"<td  class='td1'><input  id='gd' /></td>"
	        +"<td  class='td1'><input  id='gs' /></td>"
	        +"<td  class='td1'><input  id='dt' /></td>"
	        +"<td  class='td1'><input  id='gdcw'/ ></td>"
	        
	        +"<td  class='td1'><input  id='tcwzj' /></td>"
	        +"<td  class='td1'><input  id='syl' /></td>"
	        +"<td  class='td1'><input  id='qt' /></td>" 
	        +"<td  class='td1'><a href='javascript:save()'>保存</a>&nbsp;&nbsp;<a href='javascript:cancel()'>取消</a></td></tr> ");
	        
	        for (int i = 0; i < list.size(); i++) {
	            String bh = (String) (list.get(i)).get("bh");
	            String xzlmc = (String) (list.get(i)).get("xzlmc");
	            String kfs = (String) (list.get(i)).get("kfs");
	            String wygs = (String) (list.get(i)).get("wygs");
	            String tzf = (String) (list.get(i)).get("tzf");
	            String sq = (String) (list.get(i)).get("sq");
	            String cpdw = (String) (list.get(i)).get("cpdw");
	            String cplx = (String) (list.get(i)).get("cplx");
	            String cylx = (String) (list.get(i)).get("cylx");
	            String rzqy = (String) (list.get(i)).get("rzqy");
	            String kpsj = (String) (list.get(i)).get("kpsj");
	            		
	            String ysxkz = (String) (list.get(i)).get("ysxkz");
	            String cbcs = (String) (list.get(i)).get("cbcs");
	            String lc = (String) (list.get(i)).get("lc");
	            String bzcg = (String) (list.get(i)).get("bzcg");
	            String wq = (String) (list.get(i)).get("wq");
	            String cn = (String) (list.get(i)).get("cn");
	            String gd = (String) (list.get(i)).get("gd");
	            String gs = (String) (list.get(i)).get("gs");
	            String dt = (String) (list.get(i)).get("dt");
	            String gdcw = (String) (list.get(i)).get("gdcw");
	            
	            String tcwzj = (String) (list.get(i)).get("tcwzj");
	            String syl = (String) (list.get(i)).get("syl");
	            String qt = (String) (list.get(i)).get("qt");
	            
	            result.append("<tr id=row" + i + " onclick='showMap(this); return false;' ondblclick='editMap(this); return false;'><td class='tr02' onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>" 
	            		    + bh + "</td><td class='tr02' onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>"
	                        + xzlmc + "</td><td class='tr02' onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>" 
	                        + kfs + "</td><td class='tr02' onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>" 
	                        + wygs+ "</td><td class='tr02' onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>"
	                        + tzf + "</td><td class='tr02' onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>"
	                        + sq + "</td><td class='tr02' onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>"
	                        + cpdw + "</td><td class='tr02' onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>" 
	                        + cplx + "</td><td class='tr02' onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>" 
	                        + cylx+ "</td><td class='tr02' onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>"
	                        
	                        + rzqy + "</td><td class='tr02' onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>"
	                        + kpsj + "</td><td class='tr02' onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>" 
	                        + ysxkz + "</td><td class='tr02' onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>" 
	                        + cbcs+ "</td><td class='tr02' onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>"
	                        + lc + "</td><td class='tr02' onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>"
	                        + bzcg + "</td><td class='tr02' onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>" 
	                        + wq + "</td><td class='tr02' onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>" 
	                        + cn+ "</td><td class='tr02' onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>"
	                        
	                        + gd + "</td><td class='tr02' onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>"
	                        + gs + "</td><td class='tr02' onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>" 
	                        + dt + "</td><td class='tr02' onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>" 
	                        + gdcw+ "</td><td class='tr02' onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>"
	                        + tcwzj + "</td><td class='tr02' onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>"
	                        + syl + "</td><td class='tr02' onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>" 
	                        + qt + "</td><td><a href='javascript:modify(" + i
	                        + ")'>修改</a>&nbsp;&nbsp;<a href=\"javascript:del('" + bh
	                        + "')\">删除</a></td></tr>");
	        }
	        result.append("</table>");
	        return result.toString().replaceAll("null", "-").replaceAll("\r\n", " ; ");
	    }
		public void del() {
	        String bh = request.getParameter("bh");
	        String[] bhs = bh.split(",");
	        for(int i=0; i< bhs.length;i++){
	        	if(bhs[i]!=null && !"".equals(bhs[i])){
	        		String sql = "delete from xzlxx t where bh='" + bhs[i] + "'";
	        		this.update(sql, YW);
	        	}
	        }
	        response("{success:true}");
	    }
		public void saveZJXX(){
		      String bh = request.getParameter("bh");
		      String xzlmc = request.getParameter("xzlmc");
		      String kfs = request.getParameter("kfs");
		      String wygs = request.getParameter("wygs");
		      String tzf = request.getParameter("tzf");
		      String sq = request.getParameter("sq");
		      String cpdw = request.getParameter("cpdw");
		      String cplx = request.getParameter("cplx");
		      String cylx = request.getParameter("cylx");
		      
		      String rzqy = request.getParameter("rzqy");
		      String kpsj = request.getParameter("kpsj");
		      String ysxkz = request.getParameter("ysxkz");
		      String cbcs = request.getParameter("cbcs");
		      String lc = request.getParameter("lc");
		      String bzcg = request.getParameter("bzcg");
		      String wq = request.getParameter("wq");
		      String cn = request.getParameter("cn");
		      String gd = request.getParameter("gd");
		      String gs = request.getParameter("gs");
		      String dt = request.getParameter("dt");
		      String gdcw = request.getParameter("gdcw");
		      String tcwzj = request.getParameter("tcwzj");
		      String syl = request.getParameter("syl");
		      String qt = request.getParameter("qt");
		      
		      bh=UtilFactory.getStrUtil().unescape(bh);
		      xzlmc=UtilFactory.getStrUtil().unescape(xzlmc);
		      kfs=UtilFactory.getStrUtil().unescape(kfs);
		      wygs=UtilFactory.getStrUtil().unescape(wygs);
		      tzf=UtilFactory.getStrUtil().unescape(tzf);
		      cpdw=UtilFactory.getStrUtil().unescape(cpdw);
		      cplx=UtilFactory.getStrUtil().unescape(cplx);
		      cylx=UtilFactory.getStrUtil().unescape(cylx);
		      
		      rzqy=UtilFactory.getStrUtil().unescape(rzqy);
		      kpsj=UtilFactory.getStrUtil().unescape(kpsj);
		      ysxkz=UtilFactory.getStrUtil().unescape(ysxkz);
		      cbcs=UtilFactory.getStrUtil().unescape(cbcs);
		      lc=UtilFactory.getStrUtil().unescape(lc);
		      bzcg=UtilFactory.getStrUtil().unescape(bzcg);
		      wq=UtilFactory.getStrUtil().unescape(wq);
		      cn=UtilFactory.getStrUtil().unescape(cn);
		      gd=UtilFactory.getStrUtil().unescape(gd);
		      gs=UtilFactory.getStrUtil().unescape(gs);
		      
		      dt=UtilFactory.getStrUtil().unescape(dt);
		      gdcw=UtilFactory.getStrUtil().unescape(gdcw);
		      tcwzj=UtilFactory.getStrUtil().unescape(tcwzj);
		      syl=UtilFactory.getStrUtil().unescape(syl);
		      qt=UtilFactory.getStrUtil().unescape(qt);
		      String sql = "select bh from xzlxx where bh=?";
		      List<Map<String,Object>> list = query(sql, YW,new Object[]{bh});
		      int i = 0;
		      if(list.size()>0){
		    	  String update ="update xzlxx set xzlmc='"+xzlmc+"',kfs='"+kfs+"',wygs='"+wygs+"',tzf='"+tzf+"',sq='"+sq+"',cpdw='"
  				+cpdw+"',cplx='"+cplx+"',cylx='"+cylx+"',rzqy='"+rzqy+"',kpsj='"+kpsj+"',ysxkz='"+ysxkz+"',cbcs='"
  				+cbcs+"',lc='"+lc+"',bzcg='"+bzcg+"',wq='"+wq+"',cn='"+cn+"',gd='"+gd+"',gs='"
  				+gs+"',dt='"+dt+"',gdcw='"+gdcw+"',tcwzj='"+tcwzj+"',syl='"+syl+"',qt='"+qt+"' where bh = '"+bh+"'";
		    	  i = update(update, YW);
		      }else{ 
			      String insertString="insert into xzlxx (bh,xzlmc,kfs,wygs,tzf,sq,cpdw,cplx,cylx,rzqy,kpsj,ysxkz,cbcs,lc,bzcg,wq,cn,gd,gs,dt,gdcw,tcwzj,syl,qt )values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			      i = update(insertString, YW,new Object[]{bh,xzlmc,kfs,wygs,tzf,sq,cpdw,cplx,cylx,rzqy,kpsj,ysxkz,cbcs,lc,bzcg,wq,cn,gd,gs,dt,gdcw,tcwzj,syl,qt});
		      }
		      if(i>0){
		         response("{success:true}");
		      }else{
		          response("{success:false}");
		      }
		  }
		
		public void updateZJXX(){
		      String bh = request.getParameter("bh");
		      String xzlmc = request.getParameter("xzlmc");
		      String kfs = request.getParameter("kfs");
		      String wygs = request.getParameter("wygs");
		      String tzf = request.getParameter("tzf");
		      String sq = request.getParameter("sq");
		      String cpdw = request.getParameter("cpdw");
		      String cplx = request.getParameter("cplx");
		      String cylx = request.getParameter("cylx");
		      
		      String rzqy = request.getParameter("rzqy");
		      String kpsj = request.getParameter("kpsj");
		      String ysxkz = request.getParameter("ysxkz");
		      String cbcs = request.getParameter("cbcs");
		      String lc = request.getParameter("lc");
		      String bzcg = request.getParameter("bzcg");
		      String wq = request.getParameter("wq");
		      String cn = request.getParameter("cn");
		      String gd = request.getParameter("gd");
		      String gs = request.getParameter("gs");
		      String dt = request.getParameter("dt");
		      String gdcw = request.getParameter("gdcw");
		      String tcwzj = request.getParameter("tcwzj");
		      String syl = request.getParameter("syl");
		      String qt = request.getParameter("qt");
		      
		      bh=UtilFactory.getStrUtil().unescape(bh);
		      xzlmc=UtilFactory.getStrUtil().unescape(xzlmc);
		      kfs=UtilFactory.getStrUtil().unescape(kfs);
		      wygs=UtilFactory.getStrUtil().unescape(wygs);
		      tzf=UtilFactory.getStrUtil().unescape(tzf);
		      cpdw=UtilFactory.getStrUtil().unescape(cpdw);
		      cplx=UtilFactory.getStrUtil().unescape(cplx);
		      cylx=UtilFactory.getStrUtil().unescape(cylx);
		      
		      rzqy=UtilFactory.getStrUtil().unescape(rzqy);
		      kpsj=UtilFactory.getStrUtil().unescape(kpsj);
		      ysxkz=UtilFactory.getStrUtil().unescape(ysxkz);
		      cbcs=UtilFactory.getStrUtil().unescape(cbcs);
		      lc=UtilFactory.getStrUtil().unescape(lc);
		      bzcg=UtilFactory.getStrUtil().unescape(bzcg);
		      wq=UtilFactory.getStrUtil().unescape(wq);
		      cn=UtilFactory.getStrUtil().unescape(cn);
		      gd=UtilFactory.getStrUtil().unescape(gd);
		      gs=UtilFactory.getStrUtil().unescape(gs);
		      
		      dt=UtilFactory.getStrUtil().unescape(dt);
		      gdcw=UtilFactory.getStrUtil().unescape(gdcw);
		      tcwzj=UtilFactory.getStrUtil().unescape(tcwzj);
		      syl=UtilFactory.getStrUtil().unescape(syl);
		      qt=UtilFactory.getStrUtil().unescape(qt);
		      
		      String update ="update xzlxx set xzlmc='"+xzlmc+"',kfs='"+kfs+"',wygs='"+wygs+"',tzf='"+tzf+"',sq='"+sq+"',cpdw='"
				+cpdw+"',cplx='"+cplx+"',cylx='"+cylx+"',rzqy='"+rzqy+"',kpsj='"+kpsj+"',ysxkz='"+ysxkz+"',cbcs='"
				+cbcs+"',lc='"+lc+"',bzcg='"+bzcg+"',wq='"+wq+"',cn='"+cn+"',gd='"+gd+"',gs='"
				+gs+"',dt='"+dt+"',gdcw='"+gdcw+"',tcwzj='"+tcwzj+"',syl='"+syl+"',qt='"+qt+"' where bh = '"+bh+"'";
		      int i = update(update, YW);  
		      if(i>0){
		         response("{success:true}");
		      }else{
		          response("{success:false}");
		      }
		  }

    ///////////////////////////////////////////////////////////////////

    /****
     * 
     * <br>Description:资金管理基本信息保存
     * <br>Author:朱波海
     * <br>Date:2014-1-6
     */
    public void Save_ZjqkXX() {
        String date_id_cols_value = request.getParameter("date_id_cols_value");
      //  date_id_cols_value = UtilFactory.getStrUtil().unescape(date_id_cols_value);
        if (date_id_cols_value != null && !date_id_cols_value.equals("")) {
            String[] split = date_id_cols_value.split("@");
            for (int i = 0; i < split.length; i++) {
                String[] split2 = split[i].split("_");
                //解析
               String val= UtilFactory.getStrUtil().unescape(split2[2] );
                String update = "update XZLZJQK set " + split2[1] + "='" + val + "'  where yw_guid=? ";
                update(update, YW, new Object[] { split2[0] });
            }
            response("success");
        }
    }

    /*****
     * 
     * <br>Description:资金管理按年度保存
     * <br>Author:朱波海
     * <br>Date:2014-1-6
     */
    public void Save_Zjqk() {
        String datepjlm_id_cols_value = request.getParameter("datepjlm_id_cols_value");
        String datepjzj_id_cols_value = request.getParameter("datepjzj_id_cols_value");
        String year = request.getParameter("year");
        if (datepjlm_id_cols_value != null && !datepjlm_id_cols_value.equals("")) {
            String[] split = datepjlm_id_cols_value.split("@");
            for (int i = 0; i < split.length; i++) {
                String[] split2 = split[i].split("_");
                String update = "update XZLZJQKND_PJLM set " + split2[1] + "='" + split2[2]
                        + "'  where yw_guid=? and rq=?";
                update(update, YW, new Object[] { split2[0], year });
            }
        }
        if (datepjzj_id_cols_value != null && !datepjzj_id_cols_value.equals("")) {
            String[] split = datepjzj_id_cols_value.split("@");
            for (int i = 0; i < split.length; i++) {
                String[] split2 = split[i].split("_");
                String update = "update XZLZJQKND_PJZJ set " + split2[1] + "='" + split2[2]
                        + "'  where yw_guid=? and rq=?";
                update(update, YW, new Object[] { split2[0], year });
            }
        }
        response("success");
    }

    /****
     * 
     * <br>Description:
     * <br>Author:朱波海
     * <br>Date:2014-1-7
     */
    public void getTable() {
        String year = request.getParameter("year");
        String sqlString = "select *  from XZLXX t";
        List<Map<String, Object>> list = query(sqlString, YW);
        List<Map<String, Object>> query1 = null;
        List<Map<String, Object>> query2 = null;
        String que1 = "select * from XZLXX t,XZLZJQKND_PJZJ t2 where t.yw_guid=t2.yw_guid and t2.rq=?";
        query1 = query(que1, YW, new Object[] { year });
        if (query1.size() != list.size()) {
            String sqldiff = "select  distinct t2.yw_guid from xzlxx t2 where t2.yw_guid  not in (select yw_guid from XZLZJQKND_PJZJ where rq=? )";
            List<Map<String, Object>> query = query(sqldiff, YW, new Object[] { year });
            if (query.size() > 0) {
            	StringBuffer insert = new StringBuffer("insert all ");
                for (int i = 0; i < query.size(); i++) {
//                    String insert = "insert into XZLZJQKND_PJZJ (yw_guid,rq) values(?,?)";
//                    update(insert, YW, new Object[] { list.get(i).get("yw_guid"), year });
                	insert.append("into XZLZJQKND_PJZJ (yw_guid,rq) values('").append(list.get(i).get("yw_guid")).append("','").append(year).append("') ");
                }
                insert.append("select 'a','b' from dual");
                update(insert.toString(), YW);
                String sql2 = "select * from XZLXX t,XZLZJQKND_PJZJ t2 where t2.yw_guid=t.yw_guid and t2.rq=?";
                query1 = query(sql2, YW, new Object[] { year });
            }
        }
        String que2 = "select * from XZLXX t,XZLZJQKND_PJLM t2 where t.yw_guid=t2.yw_guid and t2.rq=?";
        query2 = query(que2, YW, new Object[] { year });
        if (query2.size() != list.size()) {
            String sqldiff = "select  distinct t2.yw_guid from xzlxx t2 where t2.yw_guid  not in (select yw_guid from XZLZJQKND_PJLM where rq=? )";
            List<Map<String, Object>> query = query(sqldiff, YW, new Object[] { year });
            if (query.size() > 0) {
            	StringBuffer insert = new StringBuffer("insert all ");
                for (int i = 0; i < query.size(); i++) {
                	insert.append("into XZLZJQKND_PJLM (yw_guid,rq) values('").append(list.get(i).get("yw_guid")).append("','").append(year).append("') ");
                }
                insert.append("select 'a','b' from dual");
                update(insert.toString(), YW);
                String sql = "select * from XZLXX t,XZLZJQKND_PJLM t2 where t2.yw_guid=t.yw_guid and t2.rq=?";
                query2 = query(sql, YW, new Object[] { year });
            }
        }
        BuildModel buildModel = new BuildModel();
        DataInteraction interaction = new DataInteraction();
        List<Map<String, Object>> cont1 = interaction.getCont(year, "XZLZJQKND_PJLM");
        List<Map<String, Object>> cont2 = interaction.getCont(year, "XZLZJQKND_PJZJ");
        String table = buildModel.getZjqkNd(query2, query1,cont1,cont2);
        response(table);


    }

    /****
     * 
     * <br>Description:查询两年的数据
     * <br>Author:朱波海
     * <br>Date:2014-1-7
     */
    public void getXzl_ND() {
        String year1 = request.getParameter("year1");
        String year2 = request.getParameter("year2");
        String tabName = request.getParameter("tabName");
        String[] year = { year1, year2 };
        String ta = new ModelFactory().getMoreTab(year, tabName);
        response(ta);

    }
    
    /****
     * 
     * <br>Description:获取改造政策研究
     * <br>Author:李国明
     * <br>Date:2014-1-13
     */
    public List<Map<String,Object>> getGZZCYJ() {
        String sql = "select * from gzzcyj ";
        return query(sql, YW);
    }

    /***
     * 
     * <br>Description:修改改造政策研究
     * <br>Author:李国明
     * <br>Date:2014-1-13
     */
    public void updateGZZYJ(){
    	try{
	    	String bqhs = request.getParameter("bqhs");
	    	String bbqfwhjmj = request.getParameter("bbqfwhjmj");
	    	String bqgm = request.getParameter("bqgm");
	    	String kxccrgm = request.getParameter("kxccrgm");
	    	String zhbzf = request.getParameter("zhbzf");
	    	String azfscdj = request.getParameter("azfscdj");
	    	String azfzhjadj = request.getParameter("azfzhjadj");
	    	String azfgddj = request.getParameter("azfgddj");
	    	String bqpgdj = request.getParameter("bqpgdj");
	    	String azfsj = request.getParameter("azfsj");
	    	String azfdjxs = request.getParameter("azfdjxs");
	    	String fzzbqgm = request.getParameter("fzzbqgm");
	    	String fkxccrgm = request.getParameter("fkxccrgm");
	    	String fzzfwbqdj = request.getParameter("fzzfwbqdj");
	    	String zzd = request.getParameter("zzd");
	    	String jsydtjxs = request.getParameter("jsydtjxs");
	    	String zkcrjsyd = request.getParameter("zkcrjsyd");
	    	String rjl = request.getParameter("rjl");
	    	String zkcrjzgm = request.getParameter("zkcrjzgm");
	    	String mdj = request.getParameter("mdj");
	    	String yjcjj = request.getParameter("yjcjj");
	    	String sql = "update gzzcyj set bqhs=?,bbqfwhjmj=?,bqgm=?,kxccrgm=?,zhbzf=?" +
	    			",azfscdj=?,azfzhjadj=?,azfgddj=?,bqpgdj=?,azfsj=?,azfdjxs=?,fzzbqgm=?,fkxccrgm=?,fzzfwbqdj=?,"+
	    			"zzd=?,jsydtjxs=?,zkcrjsyd=?,rjl=?,zkcrjzgm=?,mdj=?,yjcjj=?";
	    	update(sql, YW,new Object[]{bqhs,bbqfwhjmj,bqgm,kxccrgm,zhbzf,azfscdj,azfzhjadj,azfgddj,bqpgdj,azfsj,azfdjxs,fzzbqgm,fkxccrgm,fzzfwbqdj,zzd,jsydtjxs,zkcrjsyd,rjl,zkcrjzgm,mdj,yjcjj});
	    	response("true");
    	}catch (Exception e) {
    		response("false");
			// TODO: handle exception
		}
    }
    
    
    /**
     * 
     * <br>Description:写字楼上图
     * <br>Author:李国明
     * <br>Date:2013-12-10
     * @throws Exception 
     */
    public void drawXzl() throws Exception{
    	String tbbh = request.getParameter("xzlmc");
    	String polygon = request.getParameter("polygon");
    	if (tbbh != null) {
    		tbbh = UtilFactory.getStrUtil().unescape(tbbh);
    	}else{
    		response("{error:not primary}");
    	}
    	boolean draw = XzlxxData.getInstance().recordGIS(tbbh, polygon);
    	response(String.valueOf(draw)); 
    }
    
    /**
     * 
     * <br>Description:二手房上图
     * <br>Author:李国明
     * <br>Date:2013-12-10
     * @throws Exception 
     */
    public void drawEsf() throws Exception{
    	String tbbh = request.getParameter("xqmc");
    	String polygon = request.getParameter("polygon");
    	if (tbbh != null) {
    		tbbh = UtilFactory.getStrUtil().unescape(tbbh);
    	}else{
    		response("{error:not primary}");
    	}
    	boolean draw = EsfData.getInstance().recordGIS(tbbh, polygon);
    	response(String.valueOf(draw)); 
    }
    
    /**
     * 写字楼信息查询
     * 李国明
     * 
     */
    public void getReport(){
    	String keyword = request.getParameter("keyword");
    	keyword = UtilFactory.getStrUtil().unescape(keyword);
		ITableStyle its = new TableStyleEditRow();
		response(String.valueOf(new CBDReportManager().getReport("XZLZJ",
					new Object[] { "%" + keyword + "%" }, its)));
    }
    
    public void getXZLMC(){
    	StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.XZLMC as xzlmc from xzlxx t");
		List<Map<String, Object>> list = query(sqlBuffer.toString(), YW);
		response(list);
    }
}
