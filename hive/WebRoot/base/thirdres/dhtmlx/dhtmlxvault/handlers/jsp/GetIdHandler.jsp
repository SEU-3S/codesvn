<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="com.scand.fileupload.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>

<%
    String id =  request.getSession().getId().toString();
    out.println(id);
    session.setAttribute("FileUpload.Progress."+id,"0");
%>