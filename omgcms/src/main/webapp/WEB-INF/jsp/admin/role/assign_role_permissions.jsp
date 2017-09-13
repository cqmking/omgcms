<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../init.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:message code="label.role.assign.permissions" /></title>

<style type="text/css">

.tab-content{
	padding-top: 10px;
}

.datatable{
	margin-top: 10px;
}

</style>

<script src="${basePath}/thirdparty/ztree/js/jquery.ztree.core.min.js"></script>
<link href="${basePath}/thirdparty/ztree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">

</head>
<body>

<div id="page-content">
	
	<section class="content-header">
		<ol class="breadcrumb">
			<li>
				<a href="${basePath}/admin/index.do"><s:message code="label.common.home" /></a>
			</li>
			<li>
				<a href="${basePath}/admin/role/index.do"><s:message code="label.role.management" /></a>
			</li>
			<li class="active">
				<s:message code="label.role.assign.permissions" />
			</li>
		</ol>
	</section>

	<section class="section-content" v-show="!loading" style="display: none;">

		<h5 style="margin-bottom: 10px;"><s:message code="label.common.current.role" />：{{currentRole.name}}</h5>

		<div class="container-fluid" style="padding: 0;">
			<div class="col-md-4" style="padding-left:0;">
				<div class="panel">
					<div class="panel-heading"><s:message code="label.common.sys.res" /></div>
					<div class="panel-body">内容,tree</div>
				</div>
			</div>
			<div class="col-md-8" style="padding-right:0;">
				<div class="panel">
					<div class="panel-heading"><s:message code="label.common.sys.res.permissions" /></div>
					<div class="panel-body">内容,list, check items</div>
				</div>
			</div>
		</div>

		</section>

	</div>

<script type="text/javascript">
$(function(){
	
	var pageVue = new Vue({
		
		el: '#page-content',
		
		data: {
			loading: false,
			currentRole: {},
			pageSize: 20,
			roleId: ${roleId}
			
		},
		
		updated: function(){
			var self = this;
			
		},
		
		mounted: function(){
			var self = this;
			self.init();
		},
		
		methods: {
			
			init: function(){
				var self = this;
				self._loadRole();
			},
			
			_loadRole: function(){
				var self = this;
				if(!self.roleId){
					console.warn("self.roleId is invalid");
					return;
				}
				CMS.Util.sendJsonRequest({
					url: "${basePath}/api/rest/role/roleid/"+self.roleId,
					method: "GET",
					params: "",
					errorMsgContainer: $(".section-content"),
					success: function(data){
						self.currentRole = data;
					}
				});
				
			}
			
			
			
		}
		
	});
	
});
</script>

</body>
</html>