<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../init.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<c:if test="${empty roleId}">
	<title><s:message code="label.role.create" /></title>
</c:if>
<c:if test="${!empty roleId}">
	<title><s:message code="label.role.update" /></title>
</c:if>

<script src="${basePath}/thirdparty/validform/Validform_v5.3.2_min.js"></script>

<style type="text/css">
h3{
	font-size: 14px;
	padding-left: 10px;
}

label.col-sm-1 {
	font-weight: normal;
	min-width: 100px;
}

input.normal {
	width: 300px;
}

.col-sm-11{
	width: inherit;
}

.form-control{
	display: inline-block;
}

</style>

</head>

<body>

<div id="page-content">
	
	<section class="content-header">
		<ol class="breadcrumb">
			<li>
				<a href="${basePath}/admin/index.do"><s:message code="label.common.home" /></a>
			</li>
			<li>
				<a href="${basePath}/admin/role/list.do"><s:message code="label.role.management" /></a>
			</li>
			<li class="active">
				<c:if test="${empty roleId}">
					<s:message code="label.role.create" />
				</c:if>
				<c:if test="${!empty roleId}">
					<s:message code="label.role.update" />
				</c:if>
			</li>
		</ol>
	</section>
	
	<section class="section-content" v-show="!loading" style="display: none;">
		<form class="form-horizontal edit-form">
			<h3><s:message code="label.common.basic.info" /></h3>
			<div class="form-group">
				<label class="col-sm-1"><s:message code="label.common.code" /></label>
				<div class="col-sm-11">
					<input name="roleKey" datatype="s2-20" type="text" v-model.trim="role.roleKey" 
						nullmsg="<s:message code="error.form.field.required" arguments="${cmsUtil.getLocaleMessage('label.common.code')}"/>" 
						errormsg="<s:message code="error.form.filed.length" arguments="${cmsUtil.getLocaleMessage('label.common.code')},2,20"/>" 
						sucmsg=" " class="form-control normal" maxlength="20" placeholder="<s:message code="label.common.code" />">
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-sm-1"><s:message code="label.common.name" /></label>
				<div class="col-sm-11">
					<input name="roleName" type="text" v-model.trim="role.name" class="form-control normal" placeholder="<s:message code="label.common.name" />"
						sucmsg=" " datatype="s2-20" nullmsg="<s:message code="error.form.field.required" arguments="${cmsUtil.getLocaleMessage('label.common.name')}"/>" 
						errormsg="<s:message code="error.form.filed.length" arguments="${cmsUtil.getLocaleMessage('label.common.name')},2,20"/>" >
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-sm-1"><s:message code="label.common.description" /></label>
				<div class="col-sm-11" style="width: 70%;">
					<textarea rows="3" name="description" v-model.trim="role.description" class="form-control normal" placeholder="<s:message code="label.common.description" />"></textarea>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-11">
					<button type="button" class="btn btn-primary" @click.prevent="saveRole"><s:message code="label.common.save" /></button>
					<button type="button" class="btn btn-default" @click.prevent="back"><s:message code="label.common.back" /></button>
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
			loading: false,
			roleId: '${roleId}',
			role: {}
		},
		
		mounted: function(){
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
				if(self.roleId==null || $.trim(self.roleId).length==0){
					return;
				}
				
				
				CMS.Util.sendJsonRequest({
					url: "${basePath}/api/rest/role/roleid/"+self.roleId,
					method: "GET",
					params: "",
					errorMsgContainer: $(".section-content"),
					success: function(data){
						self.role = data;
					}
				});
				
			},
			
			saveRole: function(){
				var self = this;
				
				if(!formValidator.check(false)){
					// Not pass the validation
					return;
				}
				
				var saveUrl = "${basePath}/api/rest/role/";
				if(self.role.roleId){
					saveUrl+="update"
				}else{
					saveUrl+="create"
				}
				
				CMS.Util.sendJsonRequest({
					url: saveUrl,
					method: "POST",
					params: JSON.stringify(self.role),
					errorMsgContainer: $(".section-content"),
					prependError: true,
					success: function(data){
						if(self.role.roleId){
							location.href="list.do?messageCode=message.update.success&noteType=success";
						}else{
							location.href="list.do?messageCode=message.create.success&noteType=success";
						}
					}
				});
			},
			
			back: function(){
				location.href="list.do";
			}
			
		}
		
	});
	
	
	var formValidator = $(".edit-form").Validform({
		 tiptype:3,
		 showAllError: true
	});
	
});


</script>

</body>
</html>