<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML>
<html>
<head>
<title><sitemesh:write property='title' /></title>

<link rel="stylesheet" href="${basePath}/thirdparty/zui/css/zui.min.css">

<script src="${basePath}/thirdparty/zui/lib/jquery/jquery.js"></script>
<script src="${basePath}/thirdparty/zui/js/zui.js"></script>

<script src="${basePath}/thirdparty/vue/vue.js"></script>
<script src="${basePath}/thirdparty/vue-form/vue-form.min.js"></script>

<script src="${basePath}/thirdparty/moment/moment.min.js"></script>

<script src="${basePath}/js/common/lang/${defaultLocale}.js"></script>
<c:if test="${!empty locale}">
	<script src="${basePath}/js/common/lang/${locale}.js"></script>
</c:if>

<script src="${basePath}/js/component.js"></script>
<script src="${basePath}/js/main.js"></script>

<link rel="stylesheet" href="${basePath}/css/main.css">
<link rel="stylesheet" href="${basePath}/css/skin-blue-light.css">

<sitemesh:write property='head' />

<style type="text/css">
html, body{
	height: 100%;
	background-color: #ecf0f5;
}
</style>

</head>

<body class="skin-blue-light">
	<sitemesh:write property='body' />
</body>


</html>