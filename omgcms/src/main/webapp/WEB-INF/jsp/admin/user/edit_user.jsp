<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../init.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:if test="${empty userId}">
	<title><s:message code="label.user.create" /></title>
</c:if>
<c:if test="${!empty userId}">
	<title><s:message code="label.user.update" /></title>
</c:if>
</head>

<body>

<div id="page-content">

	<section class="content-header">
		<ol class="breadcrumb">
			<li>
				<a href="${basePath}/admin/index.do"><s:message code="label.common.home" /></a>
			</li>
			<li class="active">
				<s:message code="label.user.management" />
			</li>
		</ol>
	</section>
	
	<section class="section-content">
	Content [${user.userName}]
	</section>
	
</div>

</body>
</html>