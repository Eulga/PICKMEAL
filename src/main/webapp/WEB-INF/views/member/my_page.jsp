<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/views/incl/link.jsp"%>
<link href="${pageContext.request.contextPath}/resources/css/member/my_page.css" rel="stylesheet" type="text/css">
<title>밥찡코 - 마이페이지</title>
</head>
<body>
	<div id="my_page_header">
        <h2>마이페이지</h2>
    </div>
	<nav id="mp_nav">
	    <ul>
	        <li id="myInfo"><a href="#">내 정보</a></li>
	        <li id="myPostings"><a href="${pageContext.request.contextPath}/member/myPostings">내 게시글</a></li>
	        <li id="myComments"><a href="${pageContext.request.contextPath}/member/myComments">내 댓글</a></li>
	        <li id="myFavorite"><a href="${pageContext.request.contextPath}/member/findAllFavoriteRestaurant">찜 식당</a></li>
	        <li id="myVisited"><a href="${pageContext.request.contextPath}/member/findAllVisitedRestaurant">내가 간 식당</a></li>
	        <li id="myCoupon"><a href="${pageContext.request.contextPath}/member/findAllCoupon">쿠폰</a></li>
	        <li id=""><a href="#">업적</a></li>
	    </ul>
	</nav>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		$('#${here}').addClass('on');
	})
</script>
</html>

