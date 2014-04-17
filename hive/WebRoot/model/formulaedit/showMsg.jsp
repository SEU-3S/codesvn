<%@ page language="java" pageEncoding="utf-8"%>
<%

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<meta http-equiv="X-UA-Compatible" content="IE=7">
<html>
  <head>
      <title>公式校验</title>
      <script>
      var isError = false;
      function showMsg(e){
          var btn = document.getElementById("btn");
          msgarea.innerHTML = e;
          isError = true;
      }
      
      function timerCheck(){
          setTimeout("setRight();",3000);
      }
      
      function setRight(){
          if(!isError){
              msgarea.innerHTML = "公式正确！";
          }
      }
      </script>
  </head>
  <body onload="timerCheck()">
      <label id="msgarea" style="font-size:20pt">正在校验。。。。</label>
  </body>
</html>
