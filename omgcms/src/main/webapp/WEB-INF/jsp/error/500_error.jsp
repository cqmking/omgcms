<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../init.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> <s:message code="label.error.500.title"/> </title>
</head>
<body>
	
	<% Exception ex = (Exception)request.getAttribute("exception"); %>
	<%
	
		out.print(request.getAttributeNames());
		
		if(ex==null){
			out.println("No exception!");
			return;
		}
	%>
	<H2>Exception: <%= ex.getMessage()%></H2>
	<P />
	<pre>
	<% ex.printStackTrace(new java.io.PrintWriter(out)); %>
	</pre>
</body>
</html>