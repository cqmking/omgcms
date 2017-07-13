<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../init.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:message code="label.role.assign.users" /></title>

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
				<s:message code="label.role.assign.users" />
			</li>
		</ol>
	</section>

	<section class="section-content" v-show="!loading" style="display: none;">
		<ul class="nav nav-tabs">
			<li class="active">
				<a data-tab href="#tabContent1" @click="showTabOne">已分配</a>
			</li>
			<li>
				<a data-tab href="#tabContent2" @click="showTabTwo">可用的</a>
			</li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane active" id="tabContent1">
				<div class="tab-content" v-show="!isNoAssign">
					<button :class="['btn btn-primary', {'disabled': !isSelectedAssign}]" type="button" @click.prevent="unassignRoles">移除所选用户</button>
					<table class="table table-bordered datatable table-hover">
						<thead>
							<tr>
								<th data-index="check" class="check-all check-btn"><i class="icon-check-empty"></i></th>
								<th style="width: 100px;"><s:message code="label.common.id" /></th>
								<th style="width: 220px;"><s:message code="label.user.account" /></th>
								<th><s:message code="label.user.username" /></th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="(user, index) in assignedUserPage.content">
								<td data-index="check" :data-id="user.userId" class="check-row check-btn"><i class="icon-check-empty"></i></td>
								<td>{{user.userId}}</td>
								<td>{{user.userAccount}}</td>
								<td>{{user.userName}}</td>
							</tr>
						</tbody>
					</table>
					<cms-pagination :total-pages-num="assignedUserPage.totalPages" show-pages="5" show-total-count="false" 
						:current-page-num="assignedUserPage.number+1" @change="goToAssignedPage"/>
				</div>
				
				
				<div v-show="isNoAssign">未分配角色</div>
			</div>
			<div class="tab-pane" id="tabContent2">
				<div class="tab-content" v-show="!isNoUnassign">
					<button :class="['btn btn-primary', {'disabled': !isSelectedUnassign}]" type="button" @click.prevent="assignRoles">分配所选用户</button>
					<table class="table table-bordered datatable table-hover">
						<thead>
							<tr>
								<th data-index="check" class="check-all check-btn"><i class="icon-check-empty"></i></th>
								<th style="width: 100px;"><s:message code="label.common.id" /></th>
								<th style="width: 220px;"><s:message code="label.user.account" /></th>
								<th><s:message code="label.user.username" /></th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="(user, index) in unassignedUserPage.content">
								<td data-index="check" :data-id="user.userId" class="check-row check-btn"><i class="icon-check-empty"></i></td>
								<td>{{user.userId}}</td>
								<td>{{user.userAccount}}</td>
								<td>{{user.userName}}</td>
							</tr>
						</tbody>
					</table>
					<cms-pagination :total-pages-num="unassignedUserPage.totalPages" show-pages="5" show-total-count="false" 
						:current-page-num="unassignedUserPage.number+1" @change="goToUnassignedPage"/>
				</div>
				<div v-show="isNoUnassign">没有可分配的用户</div>
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
			assignedUserPage: {
				content:[],
				totalPages: 0,
				totalElements: 0,
				number: 0
			},
			unassignedUserPage: {
				content:[],
				totalPages: 0,
				totalElements: 0,
				number: 0
			},
			isSelectedAssign: false,
			isSelectedUnassign: false,
			currentRole: {},
			pageSize: 20,
			roleId: ${roleId}
			
		},
		
		computed: {
			isNoAssign: function(){
				var self = this;
				return self.assignedUserPage.content.length == 0;
			},
			isNoUnassign: function(){
				var self = this;
				return self.unassignedUserPage.content.length == 0;
			}
		},
	
		updated: function(){
			var self = this;
			
			if($("#tabContent1").hasClass("active")){
				
				var $dataTable = $("#tabContent1 table.datatable").customDatatable({
					create: true,
					onChange: function(){
						// 选项发生变化时调用
						var selectedIds = $dataTable.getSelectIds();
						self.isSelectedAssign = (selectedIds.length > 0);
					}
				});
				
			}else if($("#tabContent2").hasClass("active")){
				
				var $dataTable = $("#tabContent2 table.datatable").customDatatable({
					create: true,
					onChange: function(){
						// 选项发生变化时调用
						var selectedIds = $dataTable.getSelectIds();
						self.isSelectedUnassign = (selectedIds.length > 0);
					}
				});
			}
			
		},
		
		mounted: function(){
			var self = this;
			self.init();
		},
		
		methods: {
			
			init: function(){
				var self = this;
				self._loadUser();
				self.loadAssignedUsers(1);
			},
			
			_loadUser: function(){
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
			
			goToUnassignedPage: function(index){
				var self = this;
				self.loadUnassignedUsers(index);	
			},
			
			showTabOne: function(){
				var self = this;
				//The 1st page as default
				self.loadAssignedUsers(1);		
			},
			
			showTabTwo: function(){
				var self = this;
				//The 1st page as default
				self.loadUnassignedUsers(1); 	
			},
			
			loadUnassignedUsers: function(index){
				
				var self = this;
				if(!self.roleId){
					return;
				}
				var url = "${basePath}/api/rest/role/unassigned-user-list/roleid-" + self.roleId + "/page-" + index + "/page-size-"+self.pageSize;
				
				CMS.Util.sendJsonRequest({
					url: url,
					method: "GET",
					errorMsgContainer: $(".section-content"),
					prependError: false,
					success: function(data){
						self.unassignedUserPage = data;
					}
				});
				
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
				
				
			},
			
			unassignRoles: function(){
				
				var self = this;
				
				var selectedIds = $("#tabContent1 table.datatable").customDatatable({create: false}).getSelectIds();
				
				if(selectedIds.length <= 0){
					return;
				}
				
				var _removeRolesFromUser = function(){
					
					var _url = "${basePath}/api/rest/user/remove-user-roles/userid-" + self.roleId + "/" + selectedIds.join(",");
					
					CMS.Util.sendJsonRequest({
						url: _url,
						method: "DELETE",
						errorMsgContainer: $(".section-content"),
						prependError: true,
						success: function(data){
							CMS.Util.showNoticeMessage("success", "移除成功!");
							self.loadAssignedUsers(1);
							$("#tabContent1 table.datatable").customDatatable().clear();
							self.isSelectedAssign = false;
						},
						complete: function(){
							;
						}
					});
					
				};
				
				var dialog = CMS.Dialog.show({
					title: '${cmsUtil.getLocaleMessage("label.common.notice")}',
					custom: '${cmsUtil.getLocaleMessage("label.common.delete.confirm")}',
					toolbar: [{
						label: '${cmsUtil.getLocaleMessage("label.common.ok")}',
						cssClass: 'btn-primary',
						callback: function(){
							_removeRolesFromUser();
							dialog.close();
						}
					},
					{
						label: '${cmsUtil.getLocaleMessage("label.common.cancel")}',
						callback: function(){
							dialog.close();
						}
					}]
				});
				
			},
			
			assignRoles: function(){
				
				var self = this;
				
				var selectedIds = $("#tabContent2 table.datatable").customDatatable({create: false}).getSelectIds();
				
				if(selectedIds.length <= 0){
					return;
				}
				
					
				var _url = "${basePath}/api/rest/user/add-user-roles/userid-" + self.roleId + "/" + selectedIds.join(",");
				
				CMS.Util.sendJsonRequest({
					url: _url,
					method: "DELETE",
					errorMsgContainer: $(".section-content"),
					prependError: true,
					success: function(data){
						CMS.Util.showNoticeMessage("success", "添加成功!");
						self.loadUnassignedUsers(1);
						$("#tabContent2 table.datatable").customDatatable().clear();
						self.isSelectedUnassign = false;
					},
					complete: function(){
						;
					}
				});
				
			}
		}
		
	});
	
});
</script>

</body>
</html>