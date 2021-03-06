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

<script src="${basePath}/thirdparty/ztree/js/jquery.ztree.all.min.js"></script>
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
					<div class="panel-body" style="min-height: 300px;">
						<div id="resourceTree" class="zTreeCust ztree"></div>
					</div>
				</div>
			</div>
			<div class="col-md-8" style="padding-right:0;">
				<div v-show="showResActsTable"><s:message code="label.common.sys.res.permissions" />{{ currentResAction.name }}</div>
				<table class="table table-bordered datatable table-hover permission" v-show="showResActsTable">
					<thead>
						<tr>
							<th data-index="check" class="check-all check-btn"><i class="icon-check-empty"></i></th>
							<th><s:message code="label.common.operation" /></th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="(resAction, index) in currentResActions">
							<td data-index="check" :data-id="resAction.resourceActionId" :class="['check-row check-btn', {'checked' : resAction.checked}]"><i class="icon-check-empty"></i></td>
							<td>{{ $t("message.operations."+resAction.actionId) }}</td>
						</tr>
					</tbody>
				</table>
				<div class="tool-bar" v-show="showResActsTable">
					<button class="btn btn-primary" type="button" @click.prevent="saveResourcePermission"><s:message code="label.common.save" /></button>
				</div>
			</div>
		</div>

		</section>

	</div>

<script type="text/javascript">
$(function(){
	
	var i18n = loadLanages("${locale}");
	var zTreeObj = {};
	
	var pageVue = new Vue({
		
		i18n,
		
		el: '#page-content',
		
		data: {
			loading: false,
			currentRole: {},
			roleId: ${roleId},
			resActions: [],
			currentResActions: [],
			currentResAction: {}
		},
		
		computed: {
			showResActsTable: function(){
				var self = this;
				return self.currentResActions.length > 0;
			}
		},
		
		updated: function(){
			var self = this;
			var $dataTable = $("table.datatable.permission").customDatatable({create: true});
		},
		
		mounted: function(){
			var self = this;
			self.init();
			
		},
		
		methods: {
			
			init: function(){
				var self = this;
				self._loadRole();
				self.loadResourceActions();
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
			
			loadResourceActions: function(){
				var self = this;
				CMS.Util.sendJsonRequest({
					url: "${basePath}/api/rest/resource/list/all-in-map",
					method: "GET",
					params: "",
					errorMsgContainer: $(".section-content"),
					success: function(data){
						self.resActions = data;
						// 加载完毕，初始化资源树菜单
						self.loadZTree();
					}
				});
			},
			
			getResourceActionsTreeData: function(){
				var self = this;
				
				var nodes = [];
				
				var count = 1;
				for(var pkey in self.resActions){
					
					var node = {};
					var children = self.resActions[pkey];
					var distinctChildren = [];
					
					for(var k=0; k<children.length; k++){
						
						var childItem = children[k];
						
						if(self._checkResActExist(childItem, distinctChildren)){
							// 如果已经存在，继续下一个
							continue;
						}
						
						var msgKey = childItem.resourceName.replace(/\./g, "_");
						childItem["name"] = self.$t("message." + msgKey);
						childItem["isParent"] = false;
						
						distinctChildren.push(childItem);
					}
					
					node = {
						name: self.$t("message.system." + pkey),
						itemId: count,
						isParent: true,
						children: distinctChildren
					};
					
					nodes.push(node);
					count++;
				}
				
				return nodes;
			},
			
			_checkResActExist: function(item, resActs){
				
				for(var j=0; j< resActs.length; j++){
					var _child = resActs[j];
					if(_child.resourceName == item.resourceName 
							&& _child.resourceType == item.resourceType){
						return true;
					}
				}
				return false;
			},
			
			loadZTree: function(){
				var self = this;
				var nodes = self.getResourceActionsTreeData();
				var setting = {
					callback: {
						onClick: function(event, treeId, node){
							if(self.currentResAction.resourceName == node.resourceName){
								return;
							}
							self.loadSelectedResActs(node.resourceName, function(){
								self.currentResAction = JSON.parse(JSON.stringify(node));
								self.currentResAction["name"] = "-["+self.currentResAction.name+"]";
							});
							
						}
					}
				};
				zTreeObj = $.fn.zTree.init($("#resourceTree"), setting, nodes);
				
			},
			
			loadSelectedResActs: function(resourceName, successCallback){
				var self = this;
				CMS.Util.sendJsonRequest({
					url: "${basePath}/api/rest/resource/list/resourceName",
					method: "GET",
					params: {
						resourceName: resourceName
					},
					errorMsgContainer: $(".section-content"),
					success: function(data){
						$("table.datatable.permission").customDatatable().clear();
						data.forEach(function(resAct){
							resAct["checked"] = false;
						});
						self.currentResActions = data;
						successCallback();
						self.loadResourcePermission(resourceName);
					}
				});
				
			},
			
			// 加载已经设置的资源权限
			loadResourcePermission: function(resourceName){
				var self = this;
				CMS.Util.sendJsonRequest({
					url: "${basePath}/api/rest/resource/loadResourcePermission",
					contentType: "application/x-www-form-urlencoded;charset=UTF-8",
					method: "POST",
					params: {
						roleId: self.currentRole.roleId,
						resourceName: resourceName
					},
					errorMsgContainer: $(".section-content"),
					success: function(data){
						self.markCheckedItems(data);
					}
				});
			},
			
			saveResourcePermission: function(){
				var self = this;
				var resActsIds = $("table.datatable.permission").customDatatable({create: false}).getSelectIds();
				console.log("ID:"+resActsIds);
				
				CMS.Util.sendJsonRequest({
					url: "${basePath}/api/rest/resource/addResourcePermissions",
					contentType: "application/x-www-form-urlencoded;charset=UTF-8",
					method: "POST",
					params: {
						roleId: self.currentRole.roleId,
						resActIds: resActsIds.join(","),
						resourceName: self.currentResAction.resourceName
					},
					errorMsgContainer: $(".section-content"),
					success: function(data){
						//self.currentResActions = data;
						//console.log(data);
						CMS.Util.showNoticeMessage("success", "${cmsUtil.getLocaleMessage('message.update.success')}");
					}
				});
				
			},
			
			markCheckedItems: function(checkedItems){
				var self = this;
				
				self.currentResActions.forEach(function(resAct){
					
					resAct["checked"] = false;
					
					for(var i=0;i<checkedItems.length;i++){
						
						var item = checkedItems[i];
						
						if(resAct.resourceName == item.resourceName 
								&& (resAct.bitwiseValue & item.actionIds) > 0){
							resAct["checked"] = true;
						}
					}
					
				});
			}
			
		}
		
	});
	
});
</script>

</body>
</html>