//动态生成Excel
var ReportExcel = function(){};
ReportExcel.prototype={
	Init:function(){
		try{
	    	this.oXL = new ActiveXObject("Excel.Application");
	    }catch(err){
	    	Ext.Msg.alert('提示','Excel生成失败，请先确定系统已安装office，并在浏览器的\'工具\' - Internet选项 -安全 - 自定义级别 - ActiveX控件和插件 - 对未标记为可安全执行脚本的ActiveX控件.. 标记为\'启用\'');
	    	return;
	    }
	    this.rows = 0;
	    this.cells = 0;
	    var oWB = this.oXL.Workbooks.Add(); 
		this.oSheet = oWB.ActiveSheet; 
	},
	buildTable:function(tableId, row, cell){
		this.tableId = tableId;
		this.row = row;
		this.cell = cell;
		var curTbl = document.getElementById(this.tableId);
		var sel = document.body.createTextRange(); 
		if(this.rows == 0 || this.cells == 0){
			this.rows = curTbl.rows.length;
			this.cells = curTbl.rows.item(0).cells.length;
		}
		sel.moveToElementText(curTbl); 
		sel.select(); 
		sel.execCommand("Copy"); 
		this.oSheet.Paste(this.oSheet.Range( this.oSheet.Cells(parseInt(this.row),parseInt(this.cell)),this.oSheet.Cells(parseInt(this.rows) + parseInt(this.row),parseInt(this.cells) + parseInt(this.cell))));
	},
	showTable:function(){
		this.oXL.Visible = true;
	},
	getSheet:function(){
		return this.oSheet;
	},
	setSheet:function(sheet){
		this.oSheet = sheet;
	},
	setRows:function(rows){
		this.rows = rows;
	},
	setCells:function(cells){
		this.cells = cells;
	}
}