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
<script src="${basePath}/thirdparty/vue-i18n/vue-i18n.js"></script>

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
html, body {
	height: 100%;
	background-color: #ecf0f5;
}
</style>

</head>
<body class="skin-blue-light">

	<div class="wrapper">
	    
	    <jsp:include page="../frame/header.jsp" />
	
	    <jsp:include page="../frame/menus.jsp" />
	
	    <div id="content-wrapper" class="content-wrapper">
	    	<sitemesh:write property='body' />
	    </div>
		
		<jsp:include page="../frame/footer.jsp" />
		
	</div>
	
</body>

<script type="text/javascript">
$(function(){
	
	var noteMessgae = "${noteMessgae}";
	var type = "${noteType}";
	
	if($.trim(noteMessgae).length>0){
		if($.trim(type).length==0){
			type = "info";
		}
		
		var url = location.href;
		var newUrl = delParam(url, "messageCode");
		newUrl = delParam(newUrl, "noteType");
		
		var stateObject = {};
		var title = "blank";
		
		history.pushState(stateObject, title, newUrl);
		CMS.Util.showNoticeMessage(type, noteMessgae);
	}
	
	function delParam(url,paramKey){
	    var urlParam = url.substr(url.indexOf("?")+1);
	    var beforeUrl = url.substr(0, url.indexOf("?"));
	    var nextUrl = "";
	    
	    var arr = new Array();
	    if(urlParam != ""){
	        var urlParamArr = urlParam.split("&");
	      
	        for(var i=0;i<urlParamArr.length;i++){
	            var paramArr = urlParamArr[i].split("=");
	            if(paramArr[0]!=paramKey){
	                arr.push(urlParamArr[i]);
	            }
	        }
	    }
	     
	    if(arr.length>0){
	        nextUrl = "?"+arr.join("&");
	    }
	    url = beforeUrl + nextUrl;
	    return url;
	};
	
});
</script>

</html>