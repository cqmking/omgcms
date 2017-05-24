<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="com.omgcms.bean.NavigationItem" %>
<%@ page import="com.omgcms.admin.util.MenusLoaderUtil" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<%
	List<NavigationItem> navigations = MenusLoaderUtil.getNavigationItemList();
%>

<c:set var="navigations" value="<%=navigations %>"/>

<aside class="main-sidebar">
	
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar" style="height: auto;">
        <nav class="menu" data-ride="menu" data-auto="true" style="width: 230px">
			
			<div class="dashboard"><s:message code="admin.menu.head.title" /></div>
			
            <ul class="nav nav-primary">
            	
            	<c:forEach var="navNode" items="${navigations}" varStatus="status">
            		
            		<c:if test="${empty navNode.subNavigations}">
            			<li><a href="${basePath}${navNode.url}"><i class="icon ${navNode.iconClass}"></i> <s:message code="${navNode.code}" /></a></li>
            		</c:if>
            		
            		<c:if test="${!empty navNode.subNavigations}">
	            		<li class="nav-parent">
		                    <a href="javascript:;"><i class="icon ${navNode.iconClass}"></i> <s:message code="${navNode.code}" /></a>
		                    <ul class="nav">
		                    	<c:forEach var="subNode" items="${navNode.subNavigations }" varStatus="subStatus">
		                        	<li><a href="${basePath}${subNode.url}"><i class="icon ${subNode.iconClass}"></i> <s:message code="${subNode.code}" /></a></li>
		                        </c:forEach>
		                    </ul>
		                </li>
	                </c:if>
                	
            	</c:forEach>
               	
            </ul>
        </nav>
    </section>
    <!-- /.sidebar -->
</aside>

<script type="application/javascript">

    $(function(){
		
        $('.menu .nav').on('click', 'li.nav-parent > a', function() {
        	if($(this).parent().find('li > a').size()>0){
        		var url = $(this).parent().find('li > a').eq(0).attr('href');
        		window.location.href = url;
        	}
        });
        
        //By default, will show the first item.
        //if($('.menu .nav li > a').size()>0){
        //	var url = $('.menu .nav li > a').eq(0).attr('href');
        //	window.location.href = url;
        //}

    });
	
</script>