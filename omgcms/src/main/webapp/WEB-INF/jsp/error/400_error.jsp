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
}

</style>

</head>
<body>
	<div class="errorMsg">
		访问地址格式不正确！
	</div>
</body>
</html>