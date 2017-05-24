<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
	String contextPath = request.getContextPath();
%>
<style type="text/css">
body{
	background-color: #FFF;
}

.errorMsg{
	min-height: 600px;
	background-image: url("<%=contextPath%>/images/sys/404.jpg");
	background-repeat: no-repeat;
	background-position: center 50px;
}

</style>

</head>
<body>
	<div class="errorMsg">
			
	</div>
</body>
</html>