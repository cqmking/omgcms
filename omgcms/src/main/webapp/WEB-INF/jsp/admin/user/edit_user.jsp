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

<style type="text/css">
h3{
	font-size: 14px;
	padding-left: 10px;
}

label.col-sm-1 {
	font-weight: normal;
}

.col-sm-11{
	width: inherit;
}

input.normal {
	width: 300px;
}

</style>

</head>

<body>

<div id="page-content">
	
	<div class="loading-div" v-show="loading">
		<i class="icon icon-spin icon-spinner-indicator"></i>
	 </div>
	
	<section class="content-header" v-show="!loading">
		<ol class="breadcrumb">
			<li>
				<a href="${basePath}/admin/index.do"><s:message code="label.common.home" /></a>
			</li>
			<li>
				<a href="${basePath}/admin/user/list.do"><s:message code="label.user.management" /></a>
			</li>
			<li class="active">
				<c:if test="${empty userId}">
					<s:message code="label.user.create" />
				</c:if>
				<c:if test="${!empty userId}">
					<s:message code="label.user.update" />
				</c:if>
			</li>
		</ol>
	</section>
	
	<section class="section-content" v-show="!loading">
		<form class="form-horizontal">
			<h3><s:message code="label.common.basic.info" /></h3>
			<div class="form-group">
				<label class="col-sm-1">账号</label>
				<div class="col-sm-11">
					<input type="text" v-model="user.userAccount" class="form-control normal" placeholder="电子邮件/手机号/用户名">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-1">用户名</label>
				<div class="col-sm-11">
					<input type="text" v-model="user.userName" class="form-control normal" placeholder="用户名">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-1">邮箱</label>
				<div class="col-sm-11">
					<input type="text" v-model="user.email" class="form-control normal" placeholder="密码">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-1">性别</label>
				<div class="col-sm-11">
					<input type="text" v-model="user.sex" class="form-control normal" placeholder="密码">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-1">年龄</label>
				<div class="col-sm-11">
					<input type="text" v-model="user.age" class="form-control normal" placeholder="密码">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-1">生日</label>
				<div class="col-sm-11">
					<input type="text" v-model="user.birthday" class="form-control normal" placeholder="密码">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-1">地址</label>
				<div class="col-sm-11">
					<input type="text" v-model="user.address" class="form-control normal" placeholder="密码">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-1">备注</label>
				<div class="col-sm-11">
					<input type="text" v-model="user.description" class="form-control normal" placeholder="密码">
				</div>
			</div>
			<h3><s:message code="label.common.update.password" /></h3>
			<div class="form-group">
				<label class="col-sm-1">新密码</label>
				<div class="col-sm-11">
					<input type="password" class="form-control normal" placeholder="新密码">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-1">确认新密码</label>
				<div class="col-sm-11">
					<input type="password" class="form-control normal" placeholder="确认新密码">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-11">
					<button type="button" class="btn btn-primary">保存</button>
					<button type="button" class="btn btn-default">返回</button>
				</div>
			</div>
		</form>
	</section>
	
</div>

<script type="text/javascript">

$(function(){
	
	var pageVue = new Vue({
		
		el: '#page-content',
		
		data: {
			loading: true,
			userId:${userId},
			user:{}
		},
		
		created: function () {
			var self = this;
			self.initPage();
		},
		
		methods: {
			
			initPage: function(){
				var self = this;
				self.getRemotePageData();
			},
			
			getRemotePageData: function(){
				var self = this;
				self.loading = true;
				
				$.ajax({
				   type: "GET",
				   dataType: "json",
				   url: "${basePath}/api/rest/user/userid/"+self.userId,
				   success: function(data, status){
					   if(data=="null"){
						   
					   }else{
						   self.user = data;
					   }
					   
				   },
				   error: function (XMLHttpRequest, textStatus, errorThrown) {
					    // 通常 textStatus 和 errorThrown 之中
					    // 只有一个会包含信息
					    // 调用本次AJAX请求时传递的options参数
					    if(XMLHttpRequest.responseJSON){
					    	var errorMsg = XMLHttpRequest.responseJSON.message;
					    	$(".section-content").html(errorMsg);
					    }
					    
				   },
				   complete: function (XMLHttpRequest, status) {
					   self.loading = false;
				   }
				});
			}
		}
		
	});
	
});

</script>

</body>
</html>