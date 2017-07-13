<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../init.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:message code="label.role.management" /></title>

</head>
<body>
<div id="page-content">
	
	<section class="content-header">
		<ol class="breadcrumb">
			<li>
				<a href="${basePath}/admin/index.do"><s:message code="label.common.home" /></a>
			</li>
			<li class="active">
				<s:message code="label.role.management" />
			</li>
		</ol>
	</section>
	
	<section class="section-content" v-show="!loading" style="display: none;">
			
		<ul class="nav nav-primary">
			<li>
				<a class="dropdown-toggle" data-toggle="dropdown" href="javascript:;"><s:message code="label.common.operation" /> <span class="caret"></span></a>
				<ul class="dropdown-menu">
					<li>
						<a href="add.do"><s:message code="label.common.add" /></a>
					</li>
					<li v-show="showBatchDelete">
						<a href="javascript:;" @click.prevent="deleteSelectedRole"><s:message code="label.common.delete" /></a>
					</li>
				</ul>
			</li>
		</ul>
		
		<table class="table table-bordered datatable table-hover">
			<thead>
				<tr>
					<th data-index="check" class="check-all check-btn"><i class="icon-check-empty"></i></th>
					<th><s:message code="label.common.id" /></th>
					<th><s:message code="label.common.name" /></th>
					<th><s:message code="label.common.code" /></th>
					<th><s:message code="label.common.description" /></th>
					<th style="width: 200px;"><s:message code="label.common.operation" /></th>
				</tr>
			</thead>
			<tbody>
				<tr v-for="(role, index) in pageInfo.content">
					<td data-index="check" :data-id="role.roleId" class="check-row check-btn"><i class="icon-check-empty"></i></td>
					<td>{{role.roleId}}</td>
					<td>{{role.name}}</td>
					<td>{{role.roleKey}}</td>
					<td>{{role.description}}</td>
					<td class="btn-td">
						<div class="btn-group">
						  <button type="button" class="btn btn-sm dropdown-toggle" data-toggle="dropdown">
						    <s:message code="label.common.operation" /> <span class="caret"></span>
						  </button>
						  <ul class="dropdown-menu pull-right" role="menu">
						    <li><a :href="'edit.do?roleId='+role.roleId"><s:message code="label.common.modify"/></a></li>
						    <li><a href="javascript:;" @click.prevent="deleteObject(role)"><s:message code="label.common.delete"/></a></li>
						    <li><a href="javascript:;" @click.prevent="assignUsers(role)"><s:message code="label.user.assign.roles"/></a></li>
						  </ul>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		
		<cms-pagination :total-pages-num="pageInfo.totalPages" show-pages="5" show-total-count="false" :current-page-num="pageInfo.number+1" @change="goToPage"/>
		
	</section>
	
</div>



<script type="text/javascript">
$(function(){
	
	
	var pageVue = new Vue({
		
		el: '#page-content',
		
		data: {
			loading: false,
			showBatchDelete: false,
			pageInfo:{
				content:[],
				totalPages: 0,
				totalElements: 0,
				number: 0
			},
			pageSize: 10
		},
		
		mounted: function () {
			var self = this;
			self.initPage();
		},
		
		updated: function(){
			var self = this;
			var $dataTable = $("table.datatable").customDatatable({
				create: true,
				onChange: function(){
					var selectedIds = $dataTable.getSelectIds();
					self.showBatchDelete = (selectedIds.length > 0);
				}
			});
		},
		
		methods: {
			
			initPage: function(){
				var self = this;
				self.getRemotePageData();
			},
			
			goToPage: function(index){
				
				var self = this;
				
				var getListUrl = "${basePath}/api/rest/role/list/page-" + index + "/page-size-"+self.pageSize;
				
				CMS.Util.sendJsonRequest({
					url: getListUrl,
					method: "GET",
					errorMsgContainer: $(".section-content"),
					prependError: false,
					success: function(data){
						self.pageInfo = data;
					}
				
				});
				
			},
			
			getRemotePageData: function(){
				var self = this;
				
				var getListUrl = "${basePath}/api/rest/role/list/page-"+(self.pageInfo.number+1)+"/page-size-"+self.pageSize;

				CMS.Util.sendJsonRequest({
					url: getListUrl,
					method: "GET",
					errorMsgContainer: $(".section-content"),
					prependError: false,
					success: function(data){
						self.pageInfo = data;
					}
				
				});
				
			},
			
			deleteSelectedRole: function(){
				var $dataTable = $("table.datatable").customDatatable({create: false});
				var selectedIds = $dataTable.getSelectIds();
				
				if(selectedIds.length <= 0){
					return;
				}
				
				var dialog = CMS.Dialog.show({
					title: '${cmsUtil.getLocaleMessage("label.common.notice")}',
					custom: '${cmsUtil.getLocaleMessage("label.common.delete.confirm")}',
					toolbar: [{
						label: '${cmsUtil.getLocaleMessage("label.common.ok")}',
						cssClass: 'btn-primary',
						callback: function(){
							_deleteFromServer();
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
				
				var _deleteFromServer = function(){
					
					var deleteUrl = "${basePath}/api/rest/role/batchDelete/"+selectedIds.join(",");
					
					CMS.Util.sendJsonRequest({
						url: deleteUrl,
						method: "DELETE",
						errorMsgContainer: $(".section-content"),
						prependError: true,
						success: function(data){
							location.href="list.do?messageCode=message.delete.success&noteType=success";
						}
					
					});
				}
			},
			
			deleteObject: function(role){
				
				var dialog = CMS.Dialog.show({
					title: '${cmsUtil.getLocaleMessage("label.common.notice")}',
					custom: '${cmsUtil.getLocaleMessage("label.common.delete.confirm")}',
					toolbar: [{
						label: '${cmsUtil.getLocaleMessage("label.common.ok")}',
						cssClass: 'btn-primary',
						callback: function(){
							_deleteOneFromServer();
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
				
				var _deleteOneFromServer = function(){
					
					var deleteOneUrl = "${basePath}/api/rest/role/delete/"+role.roleId;
					
					CMS.Util.sendJsonRequest({
						url: deleteOneUrl,
						method: "DELETE",
						errorMsgContainer: $(".section-content"),
						prependError: true,
						success: function(data){
							location.href="list.do?messageCode=message.delete.success&noteType=success";
						}
					
					});
				}
				
				
			},
			
			assignUsers: function(role){
				location.href="assign_role_users.do?roleId="+role.roleId;
			}
			
			
		}
		
	});
	
});
</script>

</body>
</html>