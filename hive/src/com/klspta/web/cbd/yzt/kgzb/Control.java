package com.klspta.web.cbd.yzt.kgzb;
/*****
 * 
 * @author zhubohai
 *
 */
public class Control {
public StringBuffer getTable(String type){
	StringBuffer buffer = new StringBuffer();
	BuilTable builTable = new BuilTable();
	StringBuffer title = builTable.getTitle();
	buffer.append("<table width='1400' align='center' id='oldTable'>");
	buffer.append(title);
	DataInteraction interaction = new DataInteraction();
	StringBuffer date = interaction.getDate(type);
	buffer.append(date);
	buffer.append("</table>");
	return buffer;
}


}
