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
				<li class="active">
					<a href="your/nice/url">首页</a>
				</li>
				<li>
					<a href="your/nice/url">动态 <span class="label label-badge label-success">4</span></a>
				</li>
				<li>
					<a href="your/nice/url">项目 </a>
				</li>
				<li>
					<a class="dropdown-toggle" data-toggle="dropdown" href="javascript:;"><s:message code="label.common.operation" /> <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li>
							<a href="your/nice/url">任务</a>
						</li>
						<li>
							<a href="your/nice/url">bug</a>
						</li>
						<li>
							<a href="your/nice/url">需求</a>
						</li>
						<li>
							<a href="your/nice/url">用例</a>
						</li>
					</ul>
				</li>
			</ul>
			
			<table class="table table-bordered table-hover">
			<thead>
				<tr>
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
					<td>{{user.userId}}</td>
					<td>{{user.userAccount}}</td>
					<td>{{user.userName}}</td>
					<td>{{user.sex}}</td>
					<td>{{user.age}}</td>
					<td>{{user.email}}</td>
					<td>{{formatDate(user.birthday,"YYYY-MM-DD")}}</td>
					<td>{{formatDate(user.lastLoginDate)}}</td>
					<td>{{formatDate(user.createDate)}}</td>
					<td class="btn-td">
						<button class="btn btn-sm" type="button"><s:message code="label.common.modify"/> </button>
						<button class="btn btn-sm btn-info" type="button" data-custom="#user-detail" data-toggle="modal" data-size="lg"
							data-title="<s:message code="label.user.detail"/>" @click="viewUser(user)">
							<s:message code="label.common.view"/>
						</button>
						<button class="btn btn-sm btn-danger" type="button"><s:message code="label.common.delete"/></button>
					</td>
				</tr>
			</tbody>
		</table>
		
		<cms-pagination :total-pages-num="pageInfo.totalPages" show-pages="5" show-total-count="false" :current-page-num="pageInfo.number+1" />
		
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
						<td class="td-value">{{currentUser.sex}}</td>
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
						<td class="td-value" colspan="3">{{formatDate(currentUser.lastLoginDate)}}</td>
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
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
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
			pageSize: 20
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
			
			viewUser: function(user){
				this.currentUser = user;
			},
			
			getRemotePageData: function(){
				var self = this;
				self.loading = true;
				
				$.ajax({
				   type: "GET",
				   dataType: "json",
				   url: "${basePath}/api/rest/user/list/page-"+(self.pageInfo.number+1)+"/page-size-"+self.pageSize,
				   success: function(data, status){
					   console.debug("OK");
					   self.pageInfo = data;
				   },
				   complete: function (XMLHttpRequest, status) {
					   self.loading = false;
					   self.pageLoading = false;
				   }
				});
			},
			
			formatDate: function(dateTimeStamp, formatString){
				var mDate = moment(dateTimeStamp);
				var _formatString = formatString || 'YYYY-MM-DD HH:mm:ss';
			    return mDate.format(_formatString);
			}
		}
		
	});
	
});
</script>

</body>
</html>