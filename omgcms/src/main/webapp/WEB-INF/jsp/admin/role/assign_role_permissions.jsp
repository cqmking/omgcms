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
				
			},
			
			goToAssignedPage: function(index){
				var self = this;
				self.loadAssignedUsers(index);	
			},
			
			
			loadAssignedUsers: function(index){
				
				var self = this;
				if(!self.roleId){
					return;
				}
				var url = "${basePath}/api/rest/role/assigned-user-list/roleid-" + self.roleId + "/page-" + index + "/page-size-"+self.pageSize;
				
				CMS.Util.sendJsonRequest({
					url: url,
					method: "GET",
					errorMsgContainer: $(".section-content"),
					prependError: false,
					success: function(data){
						self.assignedUserPage = data;
						
					}
				});
				
			}
			
		}
		
	});
	
});
</script>

</body>
</html>