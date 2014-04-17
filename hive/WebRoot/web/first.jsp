<%@page language="java" pageEncoding="UTF-8"%>
<%@page import="com.klspta.console.user.User"%>
<%@page import="com.klspta.model.projectinfo.ProjectInfo"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%
String name = ProjectInfo.getInstance().PROJECT_NAME;

Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
if (principal instanceof User) {
    name = ((User)principal).getFullName();
} else {
    name = principal.toString();
}
if(name.equals("Administrator")){
    name = "../console";
}else{
	name = ProjectInfo.getInstance().PROJECT_NAME;
}

%>
<script type="text/javascript">
    document.location.href="<%=name%>/main.jsp"
</script>
