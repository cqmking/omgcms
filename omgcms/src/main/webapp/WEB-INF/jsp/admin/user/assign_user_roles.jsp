<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../init.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:message code="label.user.assign.roles" /></title>

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
				<a href="${basePath}/admin/user/index.do"><s:message code="label.user.management" /></a>
			</li>
			<li class="active">
				<s:message code="label.user.assign.roles" />
			</li>
		</ol>
	</section>

	<section class="section-content" v-show="!loading" style="display: none;">
		<ul class="nav nav-tabs">
			<li class="active">
				<a data-tab href="#tabContent1" @click="showTabOne"><s:message code="label.common.assigned"/></a>
			</li>
			<li>
				<a data-tab href="#tabContent2" @click="showTabTwo"><s:message code="label.common.available"/></a>
			</li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane active" id="tabContent1">
				<div class="tab-content" v-show="!isNoAssign">
					<button :class="['btn btn-primary', {'disabled': !isSelectedAssign}]" type="button" 
						@click.prevent="unassignRoles"><s:message code="label.common.remove.select.roles"/></button>
					<table class="table table-bordered datatable table-hover">
						<thead>
							<tr>
								<th data-index="check" class="check-all check-btn"><i class="icon-check-empty"></i></th>
								<th style="width: 100px;"><s:message code="label.common.id" /></th>
								<th style="width: 220px;"><s:message code="label.common.code" /></th>
								<th><s:message code="label.common.name" /></th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="(role, index) in assignedRolePage.content">
								<td data-index="check" :data-id="role.roleId" class="check-row check-btn"><i class="icon-check-empty"></i></td>
								<td>{{role.roleId}}</td>
								<td>{{role.roleKey}}</td>
								<td>{{role.name}}</td>
							</tr>
						</tbody>
					</table>
					<cms-pagination :total-pages-num="assignedRolePage.totalPages" show-pages="5" show-total-count="false" 
						:current-page-num="assignedRolePage.number+1" @change="goToAssignedPage"/>
				</div>
				
				
				<div v-show="isNoAssign"><s:message code="label.common.not.assign.roles" /></div>
			</div>
			<div class="tab-pane" id="tabContent2">
				<div class="tab-content" v-show="!isNoUnassign">
					<button :class="['btn btn-primary', {'disabled': !isSelectedUnassign}]" type="button" 
						@click.prevent="assignRoles"><s:message code="label.common.assign.select.roles"/></button>
					<table class="table table-bordered datatable table-hover">
						<thead>
							<tr>
								<th data-index="check" class="check-all check-btn"><i class="icon-check-empty"></i></th>
								<th style="width: 100px;"><s:message code="label.common.id" /></th>
								<th style="width: 220px;"><s:message code="label.common.code" /></th>
								<th><s:message code="label.common.name" /></th>
							</tr>
						</thead>
						<tbody>
							<tr v-for="(role, index) in unassignedRolePage.content">
								<td data-index="check" :data-id="role.roleId" class="check-row check-btn"><i class="icon-check-empty"></i></td>
								<td>{{role.roleId}}</td>
								<td>{{role.roleKey}}</td>
								<td>{{role.name}}</td>
							</tr>
						</tbody>
					</table>
					<cms-pagination :total-pages-num="unassignedRolePage.totalPages" show-pages="5" show-total-count="false" 
						:current-page-num="unassignedRolePage.number+1" @change="goToUnassignedPage"/>
				</div>
				<div v-show="isNoUnassign"><s:message code="label.common.no.available.roles" /></div>
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
			assignedRolePage: {
				content:[],
				totalPages: 0,
				totalElements: 0,
				number: 0
			},
			unassignedRolePage: {
				content:[],
				totalPages: 0,
				totalElements: 0,
				number: 0
			},
			isSelectedAssign: false,
			isSelectedUnassign: false,
			currentUser: {},
			pageSize: 20,
			userId: ${userId}
			
		},
		
		computed: {
			isNoAssign: function(){
				var self = this;
				return self.assignedRolePage.content.length == 0;
			},
			isNoUnassign: function(){
				var self = this;
				return self.unassignedRolePage.content.length == 0;
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
				self.loadAssignedRoles(1);
			},
			
			_loadUser: function(){
				var self = this;
				if(!self.userId){
					console.warn("self.userid is invalid");
					return;
				}
				CMS.Util.sendJsonRequest({
					url: "${basePath}/api/rest/user/userid/"+self.userId,
					method: "GET",
					params: "",
					errorMsgContainer: $(".section-content"),
					success: function(data){
						self.currentUser = data;
					}
				});
				
			},
			
			goToAssignedPage: function(index){
				var self = this;
				self.loadAssignedRoles(index);	
			},
			
			goToUnassignedPage: function(index){
				var self = this;
				self.loadUnassignedRoles(index);	
			},
			
			showTabOne: function(){
				var self = this;
				//The 1st page as default
				self.loadAssignedRoles(1);		
			},
			
			showTabTwo: function(){
				var self = this;
				//The 1st page as default
				self.loadUnassignedRoles(1); 	
			},
			
			loadUnassignedRoles: function(index){
				
				var self = this;
				if(!self.userId){
					return;
				}
				var url = "${basePath}/api/rest/user/unassigned-user-role-list/userid-" + self.userId + "/page-" + index + "/page-size-"+self.pageSize;
				
				CMS.Util.sendJsonRequest({
					url: url,
					method: "GET",
					errorMsgContainer: $(".section-content"),
					prependError: false,
					success: function(data){
						self.unassignedRolePage = data;
					}
				});
			},
			
			loadAssignedRoles: function(index){
				
				var self = this;
				if(!self.userId){
					return;
				}
				var url = "${basePath}/api/rest/user/user-role-list/userid-" + self.userId + "/page-" + index + "/page-size-"+self.pageSize;
				
				CMS.Util.sendJsonRequest({
					url: url,
					method: "GET",
					errorMsgContainer: $(".section-content"),
					prependError: false,
					success: function(data){
						self.assignedRolePage = data;
						
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
					
					var _url = "${basePath}/api/rest/user/remove-user-roles/userid-" + self.userId + "/" + selectedIds.join(",");
					
					CMS.Util.sendJsonRequest({
						url: _url,
						method: "DELETE",
						errorMsgContainer: $(".section-content"),
						prependError: true,
						success: function(data){
							CMS.Util.showNoticeMessage("success", "${cmsUtil.getLocaleMessage('label.common.remove.success')}");
							self.loadAssignedRoles(1);
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
					custom: '${cmsUtil.getLocaleMessage("label.common.remove.confirm")}',
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
				
					
				var _url = "${basePath}/api/rest/user/add-user-roles/userid-" + self.userId + "/" + selectedIds.join(",");
				
				CMS.Util.sendJsonRequest({
					url: _url,
					method: "DELETE",
					errorMsgContainer: $(".section-content"),
					prependError: true,
					success: function(data){
						CMS.Util.showNoticeMessage("success", "${cmsUtil.getLocaleMessage('label.common.add.success')}");
						self.loadUnassignedRoles(1);
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