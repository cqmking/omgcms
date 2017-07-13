<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../init.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="${basePath}/thirdparty/zui/lib/datetimepicker/datetimepicker.min.css" rel="stylesheet">
<script src="${basePath}/thirdparty/zui/lib/datetimepicker/datetimepicker.min.js"></script>
<script src="${basePath}/thirdparty/other/jquery.base64.min.js"></script>

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
	
	<section class="section-content" v-show="!loading" style="display: none;">
		<vue-form :state="formstate" class="form-horizontal">
			<h3><s:message code="label.common.basic.info" /></h3>
			<validate class="form-group">
				<label class="col-sm-1"><s:message code="label.user.account" /></label>
				<div class="col-sm-11">
					<input name="account" type="text" v-model.trim="user.userAccount" class="form-control normal" required minlength="2" maxlength="20" placeholder="<s:message code="label.user.account" />">
					<field-messages name="account" class="inline-message">
						<div slot="required" class="alert alert-danger"><s:message code="error.form.field.required" arguments="${cmsUtil.getLocaleMessage('label.user.account')}"/></div>
						<div slot="minlength" class="alert alert-danger"><s:message code="error.form.filed.length" arguments="${cmsUtil.getLocaleMessage('label.user.account')},2,20"/></div>
						<div slot="maxlength" class="alert alert-danger"><s:message code="error.form.filed.length" arguments="${cmsUtil.getLocaleMessage('label.user.account')},2,20"/></div>
					</field-messages>
				</div>
			</validate>
			<validate class="form-group">
				<label class="col-sm-1"><s:message code="label.user.username" /></label>
				<div class="col-sm-11">
					<input name="username" type="text" v-model.trim="user.userName" class="form-control normal" required required minlength="2" maxlength="20" placeholder="<s:message code="label.user.username" />">
					
					<field-messages name="username" class="inline-message">
						<div slot="required" class="alert alert-danger"><s:message code="error.form.field.required" arguments="${cmsUtil.getLocaleMessage('label.user.username')}"/></div>
						<div slot="minlength" class="alert alert-danger"><s:message code="error.form.filed.length" arguments="${cmsUtil.getLocaleMessage('label.user.username')},2,20"/></div>
						<div slot="maxlength" class="alert alert-danger"><s:message code="error.form.filed.length" arguments="${cmsUtil.getLocaleMessage('label.user.username')},2,20"/></div>
					</field-messages>
				</div>
			</validate>
			<validate class="form-group">
				<label class="col-sm-1"><s:message code="label.user.email" /></label>
				<div class="col-sm-11">
					<input name="email" v-model.trim="user.email" class="form-control normal" type="email" required placeholder="<s:message code="label.user.email" />">
					
					<field-messages name="email" class="inline-message">
						<div slot="required" class="alert alert-danger"><s:message code="error.form.field.required" arguments="${cmsUtil.getLocaleMessage('label.user.email')}"/></div>
	       				<div slot="email" class="alert alert-danger"><s:message code="error.form.email.invalid"/></div>
					</field-messages>
				</div>
			</validate>
			<validate class="form-group">
				<label class="col-sm-1"><s:message code="label.user.sex" /></label>
				<div class="col-sm-11">
					<select name="sex" v-model.trim="user.sex" class="form-control normal" placeholder="<s:message code="label.user.sex" />">
						<option value="-1"><s:message code="label.user.sex.unknown" /></option>
						<option value="1"><s:message code="label.user.sex.male" /></option>
						<option value="0"><s:message code="label.user.sex.female" /></option>
					</select>
				</div>
			</validate>
			<validate class="form-group">
				<label class="col-sm-1"><s:message code="label.user.age" /></label>
				<div class="col-sm-11">
					<input name="age" type="number" min="1" max="150" v-model.trim="user.age" class="form-control normal" placeholder="<s:message code="label.user.age" />">
					
					<field-messages name="age" class="inline-message">
						<div slot="min" class="alert alert-danger"><s:message code="error.form.filed.number.between" arguments="${cmsUtil.getLocaleMessage('label.user.age')},1,150"/></div>
						<div slot="max" class="alert alert-danger"><s:message code="error.form.filed.number.between" arguments="${cmsUtil.getLocaleMessage('label.user.age')},1,150"/></div>
					</field-messages>
				</div>
			</validate>
			<validate class="form-group">
				<label class="col-sm-1"><s:message code="label.user.birthday" /></label>
				<div class="col-sm-11">
					<input name="birthday" type="text" v-model.trim="user.birthday" class="form-control form-date birthday" placeholder="<s:message code="label.user.birthday" />">
				</div>
			</validate>
			<validate class="form-group">
				<label class="col-sm-1"><s:message code="label.common.address" /></label>
				<div class="col-sm-11">
					<input name="address" type="text" v-model.trim="user.address" class="form-control normal" maxlength="120" placeholder="<s:message code="label.common.address" />">
				</div>
			</validate>
			<validate class="form-group">
				<label class="col-sm-1"><s:message code="label.user.description" /></label>
				<div class="col-sm-11">
					<input name="description" type="text" v-model="user.description" class="form-control normal" maxlength="500" placeholder="<s:message code="label.user.description" />">
				</div>
			</validate>
			<h3><s:message code="label.common.update.password" /></h3>
			<validate class="form-group">
				<label class="col-sm-1"><s:message code="label.user.new.password" /></label>
				<div class="col-sm-11">
					<input name="newPassword" type="password" v-model.trim="newPassword1" class="form-control normal" minlength="6" maxlength="18" autocomplete="new-password" placeholder="<s:message code="label.user.new.password" />">
					
					<field-messages name="newPassword" class="inline-message">
						<div slot="minlength" class="alert alert-danger"><s:message code="error.form.filed.length" arguments="${cmsUtil.getLocaleMessage('label.user.new.password')},6,18"/></div>
						<div slot="maxlength" class="alert alert-danger"><s:message code="error.form.filed.length" arguments="${cmsUtil.getLocaleMessage('label.user.new.password')},6,18"/></div>
					</field-messages>
				</div>
			</validate>
			<validate class="form-group">
				<label class="col-sm-1"><s:message code="label.user.confirm.password" /></label>
				<div class="col-sm-11">
					<input name="confirmPassword" type="password" v-model.trim="newPassword2" :confirm-password="newPassword1" maxlength="18" class="form-control normal" placeholder="<s:message code="label.user.confirm.password" />">
				
					<field-messages name="confirmPassword" class="inline-message">
						<div slot="confirm-password" class="alert alert-danger"><s:message code="error.form.filed.different.password"/></div>
					</field-messages>
				</div>
			</validate>
			<validate class="form-group">
				<div class="col-sm-offset-1 col-sm-11">
					<button type="button" class="btn btn-primary" @click.prevent="saveUser"><s:message code="label.common.save" /></button>
					<button type="button" class="btn btn-default" @click.prevent="back"><s:message code="label.common.back" /></button>
				</div>
			</validate>
		</vue-form>
	</section>
	
</div>

<script type="text/javascript">

$(function(){
	
	var pageVue = new Vue({
		
		el: '#page-content',
		
		data: {
			loading: false,
			formstate: {},
			userId: '${userId}',
			user: {},
			newPassword1:'',
			newPassword2:''
		},
		
		mounted: function(){
			
			var self = this;
			self.initPage();
			
			// 选择时间和日期
			$(".form-date.birthday").datetimepicker(
			{
				language:  "zh-CN",
			    weekStart: 1,
			    todayBtn:  1,
			    autoclose: 1,
			    todayHighlight: 1,
			    startView: 2,
			    minView: 2,
			    //minuteStep: 5,
			    forceParse: 0,
			    format: "yyyy-mm-dd"
			}).on('changeDate', function(ev){
				var selectDateValue = ev.target.value;
			    self.user.birthday = selectDateValue;
			});
			
		},
		
		methods: {
			
			initPage: function(){
				var self = this;
				if(self.user.sex==null||$.trim(self.user.sex).length==0){
					self.user.sex="-1";	// default value
				}
				self.getRemotePageData();
			},
			
			getRemotePageData: function(){
				var self = this;
				if(self.userId==null || $.trim(self.userId).length==0){
					return;
				}
				
				CMS.Util.sendJsonRequest({
					url: "${basePath}/api/rest/user/userid/"+self.userId,
					method: "GET",
					params: "",
					errorMsgContainer: $(".section-content"),
					success: function(data){
						self.user = data;
						$(".form-date.birthday").val(self.user.birthday);
						$(".form-date.birthday").datetimepicker("update");
					}
					
				});
				
			},
			
			saveUser: function(){
				var self = this;
				
				if(!self.formstate.$valid){
					return;
				}
				
				var saveUserUrl = "${basePath}/api/rest/user/";
				if(self.user.userId){
					saveUserUrl+="update"
				}else{
					saveUserUrl+="create"
				}
				
				if(self.newPassword1==self.newPassword2 && $.trim(self.newPassword1).length > 0){
					saveUserUrl = saveUserUrl + "?password=" + $.base64.encode(self.newPassword1);
				}
				
				CMS.Util.sendJsonRequest({
					url: saveUserUrl,
					method: "POST",
					params: JSON.stringify(self.user),
					errorMsgContainer: $(".section-content"),
					prependError: true,
					success: function(data){
						if(self.user.userId){
							location.href="list.do?messageCode=message.update.success&noteType=success";
						}else{
							location.href="list.do?messageCode=message.create.success&noteType=success";
						}
						// self.initPage();
					},
					complete: function(){
						self.newPassword1='';
						self.newPassword2='';
					}
				});
			},
			
			back: function(){
				location.href="list.do";
			}
			
		}
		
	});
	
});


</script>

</body>
</html>