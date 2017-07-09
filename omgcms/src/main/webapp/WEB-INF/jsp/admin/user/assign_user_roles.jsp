<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../init.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:message code="label.user.assign.roles" /></title>
</head>
<body>

<div id="page-content">
	
	<section class="content-header" v-show="!loading" style="display: none;">
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
				<a data-tab href="#tabContent1">已分配</a>
			</li>
			<li>
				<a data-tab href="#tabContent2">未分配</a>
			</li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane active" id="tabContent1">
				<p>我是标签1。</p>
			</div>
			<div class="tab-pane" id="tabContent2">
				<p>标签2的内容。</p>
			</div>
		</div>
	</section>

</div>

<script type="text/javascript">
$(function(){
	
	var pageVue = new Vue({
		
		el: '#page-content',
		
		data: {
			loading: true
		},
	
		updated: function(){
			var self = this;
		}
	});
	
	
	pageVue.loading = false;
	
});
</script>

</body>
</html>