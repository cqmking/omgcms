<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../init.jsp"%>

<!DOCTYPE HTML>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:message code="label.user.management" /></title>
</head>
<body>
<div id="page-content">
	
	<div class="loading-div" v-show="pageLoading">
		<i class="icon icon-spin icon-spinner-indicator"></i>
	 </div>
	
	<section class="content-header" v-show="!loading" style="display: none;">
		<ol class="breadcrumb">
			<li>
				<a href="${basePath}/admin/index.do"><s:message code="label.common.home" /></a>
			</li>
			<li class="active">
				<s:message code="label.user.management" />
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
					<li>
						<a href="javascript:;"><s:message code="label.common.delete" /></a>
					</li>
				</ul>
			</li>
		</ul>
		
		<table class="table table-bordered datatable table-hover">
			<thead>
				<tr>
					<th data-index="check" class="check-all check-btn"><i class="icon-check-empty"></i></th>
					<th><s:message code="label.user.userid" /></th>
					<th><s:message code="label.user.account" /></th>
					<th><s:message code="label.user.username" /></th>
					<th><s:message code="label.user.sex" /></th>
					<th><s:message code="label.user.age" /></th>
					<th><s:message code="label.user.email" /></th>
					<th><s:message code="label.user.birthday" /></th>
					<th><s:message code="label.user.lastLoginDate" /></th>
					<th><s:message code="label.common.createDate" /></th>
					<th><s:message code="label.common.operation" /></th>
				</tr>
			</thead>
			<tbody>
				<tr v-for="(user, index) in pageInfo.content">
					<td data-index="check" class="check-row check-btn"><i class="icon-check-empty"></i></td>
					<td>{{user.userId}}</td>
					<td>{{user.userAccount}}</td>
					<td>{{user.userName}}</td>
					<td>{{getSexText(user.sex)}}</td>
					<td>{{user.age}}</td>
					<td>{{user.email}}</td>
					<td>{{formatDate(user.birthday,"YYYY-MM-DD")}}</td>
					<td>{{getLoginDateTime(user.lastLoginDate)}}</td>
					<td>{{formatDate(user.createDate)}}</td>
					<td class="btn-td">
						<a class="btn btn-sm" :href="'edit.do?userId='+user.userId"><s:message code="label.common.modify"/></a>
						<button class="btn btn-sm btn-info" type="button" data-custom="#user-detail" data-toggle="modal" data-size="lg"
							data-title="<s:message code="label.user.detail"/>" @click.prevent="viewUser(user)">
							<s:message code="label.common.view"/>
						</button>
						<button class="btn btn-sm btn-danger" type="button" @click.prevent="deleteUser(user)"><s:message code="label.common.delete"/></button>
					</td>
				</tr>
			</tbody>
		</table>
		
		<cms-pagination :total-pages-num="pageInfo.totalPages" show-pages="5" show-total-count="false" :current-page-num="pageInfo.number+1" @change="goToPage"/>
		
	</section>
	
	<div class="modal-contents hide">
		<div id="user-detail">
			<table class="table table-bordered">
				<tbody>
					<tr>
						<td class="td-label"><s:message code="label.user.userid" /></td>
						<td class="td-value">{{currentUser.userId}}</td>
						<td class="td-label"><s:message code="label.user.account" /></td>
						<td class="td-value">{{currentUser.userAccount}}</td>
					</tr>
					<tr>
						<td class="td-label"><s:message code="label.user.username" /></td>
						<td class="td-value">{{currentUser.userName}}</td>
						<td class="td-label"><s:message code="label.user.sex" /></td>
						<td class="td-value">{{getSexText(currentUser.sex)}}</td>
					</tr>
					<tr>
						<td class="td-label"><s:message code="label.user.age" /></td>
						<td class="td-value">{{currentUser.age}}</td>
						<td class="td-label"><s:message code="label.user.email" /></td>
						<td class="td-value">{{currentUser.email}}</td>
					</tr>
					<tr>
						<td class="td-label"><s:message code="label.user.birthday" /></td>
						<td class="td-value">{{formatDate(currentUser.birthday,"YYYY-MM-DD")}}</td>
						<td class="td-label"><s:message code="label.common.createDate" /></td>
						<td class="td-value">{{formatDate(currentUser.createDate)}}</td>
					</tr>
					<tr>
						<td class="td-label"><s:message code="label.user.lastLoginDate" /></td>
						<td class="td-value" colspan="3">{{getLoginDateTime(currentUser.lastLoginDate)}}</td>
					</tr>
					<tr>
						<td class="td-label"><s:message code="label.common.address" /></td>
						<td class="td-value" colspan="3">{{currentUser.address}}</td>
					</tr>
					<tr>
						<td class="td-label"><s:message code="label.user.description" /></td>
						<td class="td-value" colspan="3">{{currentUser.description}}</td>
					</tr>
				</tbody>
			</table>
			<div class="cust-modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal"><s:message code="label.common.close" /></button>
			</div>
		</div>
	</div>
	
</div>



<script type="text/javascript">
$(function(){
	
	var pageVue = new Vue({
		
		el: '#page-content',
		
		data: {
			loading: true,
			pageLoading: true,
			pageInfo:{
				content:[],
				totalPages: 0,
				totalElements: 0,
				number: 0
			},
			currentUser:{},
			pageSize: 5
		},
		
		created: function () {
			var self = this;
			self.initPage();
		},
		
		updated: function(){
			$("table.datatable").customDatatable();
		},
		
		methods: {
			
			initPage: function(){
				var self = this;
				self.getRemotePageData();
			},
			
			viewUser: function(user){
				this.currentUser = user;
			},
			
			goToPage: function(index){
				
				var self = this;
				self.loading = true;
				self.pageLoading = true;
				
				var getUserListUrl = "${basePath}/api/rest/user/list/page-" + index + "/page-size-"+self.pageSize;
				
				CMS.Util.sendJsonRequest({
					url: getUserListUrl,
					method: "GET",
					errorMsgContainer: $(".section-content"),
					prependError: false,
					success: function(data){
						self.pageInfo = data;
					},
					complete: function(){
						self.loading = false;
						self.pageLoading = false;
					}
				});
				
			},
			
			getRemotePageData: function(){
				var self = this;
				self.loading = true;
				
				var getUserListUrl = "${basePath}/api/rest/user/list/page-"+(self.pageInfo.number+1)+"/page-size-"+self.pageSize;

				CMS.Util.sendJsonRequest({
					url: getUserListUrl,
					method: "GET",
					errorMsgContainer: $(".section-content"),
					prependError: false,
					success: function(data){
						self.pageInfo = data;
					},
					complete: function(){
						self.loading = false;
						self.pageLoading = false;
					}
				});
				
			},
			
			formatDate: function(dateTimeStamp, formatString){
				if(dateTimeStamp==null){
					return '';
				}
				var formatDate = CMS.Util.formatDate(dateTimeStamp, formatString);
			    return formatDate;
			},
			
			getLoginDateTime: function(dateTimeStamp, formatString){
				var self = this;
				if(dateTimeStamp==null){
					return '<s:message code="label.user.never.login" />';
				}
				return self.formatDate(dateTimeStamp, formatString);
			},
			
			getSexText: function(key){
				var sexText = {
						'-1': '<s:message code="label.user.sex.unknown" />',
						'1': '<s:message code="label.user.sex.male" />',
						'0': '<s:message code="label.user.sex.female" />',
				}
				return sexText[key];
			},
			
			deleteUser: function(user){
				
				var dialog = CMS.Dialog.show({
					title: '提示信息',
					custom: '确认删除？',
					toolbar: [{
						label: '确定',
						cssClass: 'btn-primary',
						callback: function(){
							_deleteUserFromServer();
							dialog.close();
						}
					},
					{
						label: '取消',
						callback: function(){
							dialog.close();
						}
					}]
				});
				
				var _deleteUserFromServer = function(){
					
					var deleteUserUrl = "${basePath}/api/rest/user/delete/"+user.userId;
					
					CMS.Util.sendJsonRequest({
						url: deleteUserUrl,
						method: "DELETE",
						errorMsgContainer: $(".section-content"),
						prependError: true,
						success: function(data){
							location.href="list.do?messageCode=message.delete.success&noteType=success";
						},
						complete: function(){
							self.loading = false;
						}
					});
				}
				
				
			}
			
			
		}
		
	});
	
});
</script>

</body>
</html>