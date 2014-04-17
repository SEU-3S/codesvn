﻿﻿<%@ page language="java" pageEncoding="utf-8"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>模块列表</title>
    <%@ include file="/base/include/restRequest.jspf"%>
<script>
function showFormulaList(obj){
    var name = obj.childNodes[0].data;
    parent.formulalist.showList(name);
}

function initData(){
    putClientCommond("formulaEdit","getModelList");
    var data = restRequest();
    var json = eval(data);
    var table = document.getElementById("tb");
    for(var i = 0; i < json.length; i++){
        var row = table.insertRow(table.rows.length);
        col1 = row.insertCell(0);
        col1.innerHTML = "<label for='ale' onClick='showFormulaList(this)' onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>" + json[i].FORMULA_NAME + "</label>";
        col2 = row.insertCell(0);
        col2.innerHTML = "<label for='ale' onClick='showFormulaList(this)' onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>" + json[i].YW_GUID + "</label>";
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
<body onload="initData()">
    <table id="tb"></table>
</body>
</html>


