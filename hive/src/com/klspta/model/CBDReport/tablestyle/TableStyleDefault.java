package com.klspta.model.CBDReport.tablestyle;

import com.klspta.model.CBDReport.bean.TDBean;
import com.klspta.model.CBDReport.bean.TRBean;

public class TableStyleDefault extends ITableStyle {

    @Override
    public String getTD1(TDBean tdBean) {
        return "<td id='#TDINDEX' height='#HEIGHT' width='#WIDTH' colspan='#COLSPAN' rowspan='#ROWPAN' class='#STYLE'>#TEXT</td>";
    }

    @Override
    public String getTD2(TDBean tdBean) {
        return "</td>";
    }

    @Override
    public String getTR1(TRBean trBean) {
        return "<tr class='#TRCSS'>";
    }

    @Override
    public String getTR2(TRBean trBean) {
        return "</tr>";
    }

    @Override
    public String getTable1() {
        return "<table id='#REPORTNAME' width='#TABLEWIDTH' border=\"1\" cellpadding=\"1\" cellspacing=\"0\">";
    }

    @Override
    public String getTable2() {
        return "</table>";
    }
}
