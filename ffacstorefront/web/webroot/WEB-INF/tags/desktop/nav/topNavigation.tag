<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>

<%-- <div id="nav_main" class="nav_main">
	<cms:pageSlot position="NavigationBar" var="component">
		<cms:component component="${component}"/>
	</cms:pageSlot>
</div> --%>



<div id="tabAnchor" class="category-tab col-sm-12">
	<ul class="nav nav-tabs">
	  <li <c:if test="${categoryName == 'Categories'}">class="active"</c:if>><a href="<c:url value="/Categories/c/categories#headAnchor"/>" data-toggle="tab">ALL</a></li>
	  <li <c:if test="${categoryName == 'Silver'}">class="active"</c:if>><a href="<c:url value="/Categories/Silver/c/153000#headAnchor"/>" data-toggle="tab">SAP SILVER BALL</a></li>
	  <li <c:if test="${categoryName == 'ForHer'}">class="active"</c:if>><a href="<c:url value="/Categories/ForHer/c/151000#headAnchor"/>" data-toggle="tab">FOR HER</a></li>
	  <li <c:if test="${categoryName == 'ForHim'}">class="active"</c:if>><a href="<c:url value="/Categories/ForHim/c/150000#headAnchor"/>" data-toggle="tab">FOR HIM</a></li>
	  <li <c:if test="${categoryName == 'Gifts'}">class="active"</c:if>><a href="<c:url value="/Categories/Gifts/c/152000#headAnchor"/>" data-toggle="tab">GIFTS</a></li>
	</ul>
	<div class="clear"></div>
</div>
