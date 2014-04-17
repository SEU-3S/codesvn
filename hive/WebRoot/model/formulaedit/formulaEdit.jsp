﻿﻿<%@ page language="java" pageEncoding="utf-8"%>

<html>
<%@ include file="/base/include/restRequest.jspf"%>
<head>
</head>
<body>

<script type="text/javascript">
    var _id = "";
    function showContext(id){
        _id = id;    
        var area = document.getElementById("text");
        area.innerHTML = "正在加载。。。。。。";
        putClientCommond("formulaEdit","getFormula");
        putRestParameter("id", id);
        var data = restRequest();
        var area = document.getElementById("text");
        area.value = data;
    }
    
function showProperties(id){
    var table = document.getElementById("tb");
    removeRow(table);
    putClientCommond("formulaEdit","getPropertyList");
    putRestParameter("id", id);
    var data = restRequest();
    var json = eval(data);
    for(var i = 0; i < json.length; i++){
        var row = table.insertRow(table.rows.length);
        col2 = row.insertCell(0);
        col2.innerHTML = "<label for='ale' onClick='addToContext(this.innerHTML)' onmouseover='mouseOver(this)' onmouseout='mouseOut(this)'>" + json[i].PROP_CN + "</label>";
    }
}

function mouseOver(obj){
    obj.style.backgroundColor = "#00FEFF";
}

function mouseOut(obj){
    obj.style.backgroundColor = "#FFFFFF";
}

function removeRow(inputobj){
    if(inputobj==null) return; 
    var child = inputobj.children;
    for(var i = 0; i < child.length; i++){
        inputobj.removeChild(inputobj.children[i]);
    } 
}  
    
function addToContext(str) {
    obj = this.document.getElementById('text');
    obj.focus();
    if (document.selection) {
        var sel = document.selection.createRange();
        sel.text = str;
    } else if (typeof obj.selectionStart == 'number' && typeof obj.selectionEnd == 'number') {
        var startPos = obj.selectionStart,
        endPos = obj.selectionEnd,
        cursorPos = startPos,
        tmpStr = obj.value;
        obj.value = tmpStr.substring(0, startPos) + str + tmpStr.substring(endPos, tmpStr.length);
        cursorPos += str.length;
        obj.selectionStart = obj.selectionEnd = cursorPos;
    } else {
        obj.value += str;
    }
}

function moveEnd(obj){
    obj.focus();
    var len = obj.value.length;
    if (document.selection) {
        var sel = obj.createTextRange();
        sel.moveStart('character',len);
        sel.collapse();
        sel.select();
    } else if (typeof obj.selectionStart == 'number' && typeof obj.selectionEnd == 'number') {
        obj.selectionStart = obj.selectionEnd = len;
    }
}

function verify(){
    window.open('checkFrame.jsp','校验窗口','height=300,width=500,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no'); 
}

function onSave(){
    var obj = this.document.getElementById('text');
    var context = obj.innerHTML;
    context = context.replace(/\+/g, "#_#");
    context = context.replace(/\&/g, "-_-");
    putClientCommond("formulaEdit","updateFormula");
    putRestParameter("id", _id);
    putRestParameter("context", context);
    var data = restRequest();
}
</script>
<table>
    <tr>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        
        <td><input type="button" onclick='addToContext("=")' value='   =   '></td>
        <td><input type="button" onclick='addToContext("<")' value='   <   '></td>
        <td><input type="button" onclick='addToContext(">")' value='   >   '></td>
        <td><input type="button" onclick='addToContext("<>")' value='  <>  '></td>
        <td><input type="button" onclick='addToContext(">=")' value='  >=  '></td>
        <td><input type="button" onclick='addToContext("<=")' value='  <=  '></td>
        <td><input type="button" onclick='addToContext("()")' value='  ()  '></td>
        <td><input type="button" onclick='addToContext("{}")' value='  {}  '></td>
    </tr>
    <tr>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td><input type="button" onclick='addToContext("+")' value='   +   '></td>
        <td><input type="button" onclick='addToContext("-")' value='   -   '></td>
        <td><input type="button" onclick='addToContext("*")' value='   *   '></td>
        <td><input type="button" onclick='addToContext("/")' value='   /   '></td>
        <td><input type="button" onclick='addToContext("if(){}else{}")' value=' if(){} '></td>
        <td><input type="button" onclick='addToContext("result=")' value='result='></td>
    </tr>
    <tr>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td><input type="button" onclick='addToContext("Math.pow(,)")' value='  幂  '></td>
        <td><input type="button" onclick='addToContext("Math.random()")' value=' 随机数 '></td>
        <td><input type="button" onclick='addToContext("Math.ceil()")' value='上舍入 '></td>
        <td><input type="button" onclick='addToContext("Math.floor()")' value=' 下舍入  '></td>
        <td><input type="button" onclick='addToContext("Math.round()")' value=' 取整 '></td>
        <td><input type="button" onclick='addToContext("Math.abs()")' value='绝对值'></td>
    </tr>
    <tr>
        <td colspan=5><table id="tb"></table></td>
        <td colspan=10><textarea id="text" rows=30 cols=70></textarea></td>
    </tr>
    <tr>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td><input type="button" onclick="verify()" value="校验"></td>
        <td><input type="button" onclick="onSave()" value="保存"></td>
        <td><input type="button" onclick="clear()" value="清空"></td>
    </tr>
</table>
</body>
</html>


