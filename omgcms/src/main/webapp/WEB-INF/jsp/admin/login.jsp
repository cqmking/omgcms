<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../init.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:message code="label.login.title" /></title>

<link rel="stylesheet" href="${basePath}/thirdparty/zui/css/zui.min.css">

<script src="${basePath}/thirdparty/zui/lib/jquery/jquery.js"></script>
<script src="${basePath}/thirdparty/zui/js/zui.js"></script>

<link rel="stylesheet" href="${basePath}/css/login.css">


</head>
<body class="admin templatemo-bg-gray">

	<div class="container">
		<div class="col-md-12">
			
			<h1 class="margin-bottom-15"><s:message code="label.login.headmsg"/> </h1>
			
			<form class="form-horizontal templatemo-container templatemo-login-form-1 margin-bottom-30" role="form" 
				action="checkLogin.do" method="post">
				
				<div class="form-group">
					<div class="col-xs-12">
						<div class="control-wrapper">
							<label for="username" class="control-label fa-label"><i class="icon icon-user"></i></label> 
							<input type="text" class="form-control" id="username" name="oc_account" placeholder="<s:message code="label.login.account"/>">
						</div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="col-md-12">
						<div class="control-wrapper">
							<label for="password" class="control-label fa-label"><i class="icon icon-lock"></i></label> 
							<input type="password" class="form-control" id="password" name="oc_password" placeholder="<s:message code="label.login.password"/>">
						</div>
					</div>
				</div>
				<c:if test="${!empty errorCode}">
					<div class="alert alert-danger"><s:message code="${errorCode}"/></div>
				</c:if>
				<div class="form-group">
					<div class="col-md-12">
						<div class="checkbox control-wrapper">
							<label> <input type="checkbox" name="oc_rememberme"> <s:message code="label.login.chbx.rememberme"/> </label>
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-md-12">
						<div class="control-wrapper">
							<input type="submit" value="<s:message code="label.login.btn.submit"/>" class="btn btn-info" > 
							<a href="forgot-password.html" class="text-right pull-right"><s:message code="label.login.forgot.pwd"/></a>
						</div>
					</div>
				</div>
				
				<!-- 
				<hr>
				<div class="form-group">
					<div class="col-md-12">
						<label>Login with: </label>
						<div class="inline-block">
							<a href="#"><i class="fa fa-facebook-square login-with"></i></a> 
							<a href="#"><i class="fa fa-twitter-square login-with"></i></a>
							<a href="#"><i class="fa fa-google-plus-square login-with"></i></a> 
							<a href="#"><i class="fa fa-tumblr-square login-with"></i></a> 
							<a href="#"><i class="fa fa-github-square login-with"></i></a>
						</div>
					</div>
				</div>
				 -->
				 
			</form>
			<div class="text-center">
				<a href="create-account.html" class="templatemo-create-new"><s:message code="label.login.create.new"/> <i class="fa fa-arrow-circle-o-right"></i></a>
			</div>
		</div>
	</div>

</body>
</html>