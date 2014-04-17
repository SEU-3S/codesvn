﻿﻿<%@ page language="java" pageEncoding="utf-8"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>模块列表</title>
    <%@ include file="/base/include/restRequest.jspf"%>
<script>
function showFormulaContext(obj){
    var name = obj.childNodes[0].data;
    parent.formulaedit.showProperties(name);
    parent.formulaedit.showContext(name);
}

function removeRow(inputobj){
    if(inputobj==null) return; 
    var child = inputobj.children;
    for(var i = 0; i < child.length; i++){
        inputobj.removeChild(inputobj.children[i]);
    } 
}  

function showList(id){
    var table = document.getElementById("tb");
    removeRow(table);
    putClientCommond("formulaEdit","getFormulaList");
    putRestParameter("id", id);
    var data = restRequest();
    var json = eval(data);
    var table = document.getElementById("tb");
    for(var i = 0; i < json.length; i++){
        var row = table.insertRow(table.rows.length);
        col = row.insertCell(0);
        col.innerHTML = "<label for='ale' onClick='showFormulaContext(this)' onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>" + json[i].FORMULA_ID + "</label>";
    }
}

function mouseOver(obj){
    obj.style.backgroundColor = "#00FEFF";
}

function mouseOut(obj){
    obj.style.backgroundColor = "#FFFFFF";
}

</script>
</head>
<body >
	<table id="tb"></table>
</body>
</html>


