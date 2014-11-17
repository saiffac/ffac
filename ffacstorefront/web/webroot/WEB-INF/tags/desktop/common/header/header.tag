<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="hideHeaderLinks" required="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/desktop/common/header"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>


<header class="header" id="header">
	<!--header-->

	<div class="header_top">
		<!--header_top-->
		<div class="main">
			<div class="row">
				<div class="col-sm-4">

					<cms:pageSlot position="SiteLogo" var="logo" limit="1">
						<%-- <cms:component component="${logo}" class="siteLogo"  element="div"/> --%>
						<div class="logo pull-left">
							<a href="<c:url value="/Categories/c/categories"/>"><img
								src="${commonResourcePath}/../ffac/images/logo.png" alt=""></a>
						</div>
					</cms:pageSlot>
				</div>
				<ul class="nav clearfix">
				<sec:authorize ifNotGranted="ROLE_ANONYMOUS">
					<c:set var="maxNumberChars" value="25"/>
					<c:if test="${fn:length(user.firstName) gt maxNumberChars}">
						<c:set target="${user}" property="firstName" value="${fn:substring(user.firstName, 0, maxNumberChars)}..."/>
					</c:if>
					<li class="logged_in"><ycommerce:testId code="header_LoggedUser"><spring:theme code="header.welcome" arguments="${user.firstName},${user.lastName}" htmlEscape="true"/></ycommerce:testId></li>
				</sec:authorize>
				<sec:authorize ifAnyGranted="ROLE_ANONYMOUS">
					<li><ycommerce:testId code="header_Login_link"><a href="<c:url value="/login"/>"><spring:theme code="header.link.login"/></a></ycommerce:testId></li>
				</sec:authorize>
				<li><ycommerce:testId code="header_myAccount"><a href="<c:url value="/my-account"/>"><spring:theme code="header.link.account"/></a></ycommerce:testId></li>
				<sec:authorize ifNotGranted="ROLE_ANONYMOUS"><li><ycommerce:testId code="header_signOut"><a href="<c:url value='/logout'/>"><spring:theme code="header.link.logout"/></a></ycommerce:testId></li></sec:authorize>
				</ul>
				<cms:pageSlot position="MiniCart" var="cart" limit="1">
					<cms:component component="${cart}" element="div" class="shop-menu" />
				</cms:pageSlot>
				
			</div>
		</div>
	</div>
	<!--/header_top-->

	<!--banner-->
	<div class="banner">
		<div class="infor-banner">
			<h2>give us a hand</h2>
			<h1>improving lives & livelihood</h1>
			<c:if test="${ cmsPageRequestContextData.page.uid eq 'productGrid' }">
			<a href="#headAnchor" class="btn-buy-gift">buy a gift</a>
			<a href="#ourStory" class="btn-out-story">our story...</a>
			<a href="#scrVision" class="btn-sap-care">SAP cares</a>
			</c:if>
			<c:if test="${ cmsPageRequestContextData.page.uid ne 'productGrid' }">
			<a href="<c:url value="/Categories/c/categories#headAnchor"/>" class="btn-buy-gift">buy a gift</a>
			<a href="<c:url value="/Categories/c/categories#ourStory"/>" class="btn-out-story">our story...</a>
			<a href="<c:url value="/Categories/c/categories#scrVision"/>" class="btn-sap-care">SAP cares</a>
			</c:if>
			
			<p>Buy a gift and help transform someone's life</p>
		</div>
	</div>
	<!--/banner-->

</header>
<%-- 

<c:if test="${uiExperienceOverride and not sessionScope.hideUiExperienceLevelOverridePrompt}">
	<c:url value="/_s/ui-experience?level=" var="clearUiExperienceLevelOverrideUrl"/>
	<c:url value="/_s/ui-experience-level-prompt?hide=true" var="stayOnDesktopStoreUrl"/>
	<div class="backToMobileStore">
		<a href="${clearUiExperienceLevelOverrideUrl}"><span class="greyDot">&lt;</span><spring:theme code="text.swithToMobileStore" /></a>
		<span class="greyDot closeDot"><a href="${stayOnDesktopStoreUrl}">x</a></span>
	</div>
</c:if>

<div id="header" class="clearfix">
	<div class="headerContent ">
		<ul class="nav clearfix">
			<c:if test="${empty hideHeaderLinks}">
				<c:if test="${uiExperienceOverride}">
					<li class="backToMobileLink">
						<c:url value="/_s/ui-experience?level=" var="backToMobileStoreUrl"/>
						<a href="${backToMobileStoreUrl}"><spring:theme code="text.backToMobileStore"/></a>
					</li>
				</c:if>

				<sec:authorize ifNotGranted="ROLE_ANONYMOUS">
					<c:set var="maxNumberChars" value="25"/>
					<c:if test="${fn:length(user.firstName) gt maxNumberChars}">
						<c:set target="${user}" property="firstName" value="${fn:substring(user.firstName, 0, maxNumberChars)}..."/>
					</c:if>
					<li class="logged_in"><ycommerce:testId code="header_LoggedUser"><spring:theme code="header.welcome" arguments="${user.firstName},${user.lastName}" htmlEscape="true"/></ycommerce:testId></li>
				</sec:authorize>
				<sec:authorize ifAnyGranted="ROLE_ANONYMOUS">
					<li><ycommerce:testId code="header_Login_link"><a href="<c:url value="/login"/>"><spring:theme code="header.link.login"/></a></ycommerce:testId></li>
				</sec:authorize>
				<li><ycommerce:testId code="header_myAccount"><a href="<c:url value="/my-account"/>"><spring:theme code="header.link.account"/></a></ycommerce:testId></li>
			</c:if>

			<cms:pageSlot position="TopHeaderSlot" var="component">
				<cms:component component="${component}"/>
			</cms:pageSlot>

			<cms:pageSlot position="HeaderLinks" var="link">
				<cms:component component="${link}" element="li"/>
			</cms:pageSlot>
		
			<c:if test="${empty hideHeaderLinks}">
				<li><a href="<c:url value="/store-finder"/>"><spring:theme code="general.find.a.store" /></a></li>
				<sec:authorize ifNotGranted="ROLE_ANONYMOUS"><li><ycommerce:testId code="header_signOut"><a href="<c:url value='/logout'/>"><spring:theme code="header.link.logout"/></a></ycommerce:testId></li></sec:authorize>
			</c:if>

			<cms:pageSlot position="MiniCart" var="cart" limit="1">
				<cms:component component="${cart}" element="li" class="miniCart" />
			</cms:pageSlot>
		</ul>
	</div>

	<div class="headerContent secondRow">
		<cms:pageSlot position="SearchBox" var="component">
			<cms:component component="${component}"/>
		</cms:pageSlot>
	</div>

	<cms:pageSlot position="SiteLogo" var="logo" limit="1">
		<cms:component component="${logo}" class="siteLogo"  element="div"/>
	</cms:pageSlot>
</div>
 --%>
