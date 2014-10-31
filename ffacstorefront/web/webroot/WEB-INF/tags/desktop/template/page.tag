<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true"%>
<%@ attribute name="pageTitle" required="false" rtexprvalue="true"%>
<%@ attribute name="pageCss" required="false" fragment="true"%>
<%@ attribute name="pageScripts" required="false" fragment="true"%>
<%@ attribute name="hideHeaderLinks" required="false"%>

<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/desktop/common/header"%>
<%@ taglib prefix="footer" tagdir="/WEB-INF/tags/desktop/common/footer"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<template:master pageTitle="${pageTitle}">

	<jsp:attribute name="pageCss">
		<jsp:invoke fragment="pageCss" />
	</jsp:attribute>

	<jsp:attribute name="pageScripts">
		<jsp:invoke fragment="pageScripts" />
	</jsp:attribute>

	<jsp:body>

		<div id="page" data-currency-iso-code="${currentCurrency.isocode}">
			<spring:theme code="text.skipToContent" var="skipToContent" />
			<a href="#skip-to-content" class="skiptocontent" data-role="none">${skipToContent}</a>
			<spring:theme code="text.skipToNavigation" var="skipToNavigation" />
			<a href="#skiptonavigation" class="skiptonavigation" data-role="none">${skipToNavigation}</a>
			<header:header hideHeaderLinks="${hideHeaderLinks}" />
			<a id="skiptonavigation"></a>
			
			<div id="content" class="main clearfix">
						
<%-- <c:out value="${cmsPageRequestContextData.page.uid}"></c:out> <br> --%>
<%-- <c:out value="${cmsPageRequestContextData.page.defaultPage}"></c:out> <br> --%>


<c:if test="${ cmsPageRequestContextData.page.uid eq 'productGrid' }">			
 <!--banner-small-->
  	<%-- <div class="banner-small">
    	<div class="banner-item">
        	<img src="${commonResourcePath}/../ffac/images/banner-small1.jpg" alt="">
            <div class="info-item">
            	<p><a href="#">An evening in support <br/>of social entrepreneurship</a></p>
            </div>
        </div>
        <div class="banner-item">
        	<img src="${commonResourcePath}/../ffac//images/banner-small2.jpg" alt="">
            <div class="info-item">
            	<p><a href="#">Celebrating our 25th<br/>anniversary in style</a></p>
            </div>
        </div>
    </div> --%>
     <!--/banner-small-->
 <div class="top-main">
      <h3>MANY THANKS TO OUR SPONSORS</h3>
      <div class="logo-company">
      <a href="http://www.sap.com"><img src="${commonResourcePath}/../ffac/images/logo-sharp.png" alt=""></a> 
      <a href="http://www.sai-it.com"><img src="${commonResourcePath}/../ffac/images/logo-sai.png" alt=""></a>
      <a href="http://www.hybris.com"><img src="${commonResourcePath}/../ffac/images/logo-hybris.png" alt=""></a></div>
      <h3 id="headAnchor"> buy a handmade item &amp; help the community</h3>
<p>We're reminded that there are many whom are less fortunate than ourselves.<br>
Buy a lovely handmade product for yourself or for your loved ones, and in turn help support the community's craft and their livelihoods.<br>
We can make a difference in their lives, for a better world.</p>
    </div>
    
    </c:if>
    
    <nav:topNavigation />
			<header:bottomHeader />
			<a id="skip-to-content"></a>
				<jsp:doBody />
			</div>
			
			<footer:footer />
		</div>

	</jsp:body>

</template:master>

<c:if test="${ cmsPageRequestContextData.page.uid eq 'productGrid' }">
<script>
 $(document).ready(function(){
	var $topBtn = $('#backTop');
	var topBtnHeight = $topBtn.height();
	var BOTTOM = 20;
	var PIXEL = 'px';
	
	var idlePoint = $('.footer-bottom').offset().top;
	var showPoint = $('#headAnchor').offset().top;
	var windowH = $(window).height();
	$(window).scroll(
			function(){
				var srollPos = $(window).scrollTop();
				fPos = srollPos;
				
				if(srollPos < showPoint) {
					if($topBtn.css('display') != 'none') {
						$topBtn.fadeOut();
						$topBtn.css('display','none');
					}
				}
				else
					if($topBtn.css('display')=='none') {
						$topBtn.fadeIn();
						$topBtn.css('bottom', BOTTOM + PIXEL);
					}
					else
						if(srollPos + windowH > idlePoint)
								$topBtn.css('top', idlePoint -  srollPos - topBtnHeight - BOTTOM  + PIXEL);
						else
								$topBtn.css('bottom', BOTTOM + PIXEL);
			}
			);});
</script>
</c:if>
