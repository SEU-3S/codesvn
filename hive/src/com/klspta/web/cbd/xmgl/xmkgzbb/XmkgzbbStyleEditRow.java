package com.klspta.web.cbd.xmgl.xmkgzbb;

import com.klspta.model.CBDReport.bean.TDBean;
import com.klspta.model.CBDReport.bean.TRBean;
import com.klspta.model.CBDReport.tablestyle.TableStyleDefaultEdit;

/**
 * 
 * <br>Title:报表编辑类
 * <br>Description:增加行点击事件触发（单击：showMap(); 双击：editMap();）
 * <br>Author:黎春行
 * <br>Date:2013-12-17
 */
public class XmkgzbbStyleEditRow extends TableStyleDefaultEdit {

	@Override
	public String getTR1(TRBean trBean) {
		return "<tr class='#TRCSS'  onclick='deletedk(this); return false;'>";
	}

	@Override
	public String getTR2(TRBean trBean) {
		return "</tr>";
	}

    @Override
    public String getTD1(TDBean tdBean) {
        if(tdBean.getEditable() != null && tdBean.getEditable().equals("true")){
            return "<td id='#TDINDEX' ondblclick='send(this.id)' onmouseover='mouseOver(this)' onmouseout='mouseOut(this)' height='#HEIGHT' width='#WIDTH' colspan='#COLSPAN' rowspan='#ROWPAN' class='#STYLE'><div id='0' style='display:none;width:10'></div>#TEXT</td>";
        }else{
            return "<td id='#TDINDEX' height='#HEIGHT' width='#WIDTH' colspan='#COLSPAN' rowspan='#ROWPAN' class='#STYLE'>#TEXT</td>";
        }
    }

}
