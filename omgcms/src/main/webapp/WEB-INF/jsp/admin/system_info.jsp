<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../init.jsp"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:message code="admin.title" /></title>
</head>
<body>
	
	<section class="content-header">
		<ol class="breadcrumb">
			<li>
				<a href="${basePath}/admin/index.do"><s:message code="label.common.home" /></a>
			</li>
			<li class="active">
				<s:message code="label.index.sysinfo.title" />
			</li>
		</ol>
	</section>

	<section class="section-content">
		<table class="table table-bordered">
			<thead>
				<tr>
					<th><s:message code="label.common.attribute" /></th>
					<th><s:message code="label.common.status" /></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><s:message code="label.sysinfo.os" /></td>
					<td>${systemInfo.osName }</td>
				</tr>
				<tr>
					<td><s:message code="label.sysinfo.jre" /></td>
					<td>${systemInfo.runtimeEnv }</td>
				</tr>
				<tr>
					<td><s:message code="label.sysinfo.jvm" /></td>
					<td>${systemInfo.javaVm}</td>
				</tr>
				<tr>
					<td><s:message code="label.sysinfo.server.max" /></td>
					<td><fmt:formatNumber value="${systemInfo.maxMemory}" pattern="#,#00.0#" /> MB</td>
				</tr>
				<tr>
					<td><s:message code="label.sysinfo.server.used" /></td>
					<td><fmt:formatNumber value="${systemInfo.totalMemory}" pattern="#,#00.0#" /> MB</td>
				</tr>
				<tr>
					<td><s:message code="label.sysinfo.server.free" /></td>
					<td><fmt:formatNumber value="${systemInfo.freeMemory}" pattern="#,#00.0#" /> MB</td>
				</tr>
				<tr>
					<td><s:message code="label.sysinfo.server.info" /></td>
					<td>${systemInfo.serverInfo}</td>
				</tr>
			</tbody>
		</table>
	</section>

</body>
</html>