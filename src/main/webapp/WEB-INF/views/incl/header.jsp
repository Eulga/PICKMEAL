<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header> 
	<h1><a class="logo" href="${pageContext.request.contextPath}/index">밥찡코</a></h1>
	<nav id="gnb">
		<h2 class="hidden">게시판메뉴</h2>
		<ul>
         <li><a href="${pageContext.request.contextPath}/posting/notice">공지사항</a></li>
         <li><a href="${pageContext.request.contextPath}/posting/recommend">식당추천</a></li>
         <!--  -->
         <li><a href="${pageContext.request.contextPath}/posting/together">밥친구</a></li>
      </ul>
	</nav>
	<nav id="snb">
		<h2 class="hidden">유저메뉴</h2>
		<c:if test="${not empty member }">
			<ul>
				<li><span class="navi_nickname">${member.nickName }님 맛점하세요:)</span></li>
				<li class="snbProfileLine">
					<div class="profileImgWrap">
						<img src="/pickmeal/resources/img/profile/nonUser.png"
							alt="프로필아이콘" class="profileImg">
					</div>
					<div class="profileAreaWrap navAlarmTextCom">
						<ul class="profileArea">
							<li><a href="${pageContext.request.contextPath}/member/signOutMember">로그아웃</a></li>
							<li><form action="" method="">마이페이지</form></li>
							<li><a href="${pageContext.request.contextPath}/member/myPage_SJW">마이페이지</a></li>
							<!-- myPage 로 바꿔야 함. -->
						</ul>
					</div>
				</li>
				<li>
					<div class="alarmIconWrap comIconWrap">
						<img src="/pickmeal/resources/img/header/bell_alarm.png"
							alt="알림아이콘" class="alarmImg comIconImg">
						<div class="alarmMark comIconMark">1</div>
					</div>
					<div class="alarmAreaWrap navAlarmTextCom">
						<div class="alarmTitle">알림내역</div>
						<ul class="alarmArea">
							<c:forEach var="alarm" items="${alarms}">
							<c:if test="${alarm.alarmType ne 'C'.charAt(0)}">
								<li data-friend="${alarm.friendId}" data-alarmType="${alarm.alarmType}" data-alarmId="${alarm.id}">
									<c:choose>
										<c:when test="${alarm.alarmType eq 'E'.charAt(0)}">
											<img src="${pageContext.request.contextPath}/resources/img/header/store.png" alt="프로필사진" class="alarmProfileImg">
										</c:when>
										<c:otherwise>
											<img src="${pageContext.request.contextPath}/resources/img/header/store.png" alt="프로필사진" class="alarmProfileImg">
										</c:otherwise>
									</c:choose>
									<span class="alarmTextBold">[${alarm.content}] </span>
									<c:choose>
										<c:when test="${alarm.alarmType eq 'E'.charAt(0)}">
											식당에서 식사는 맛있으셨나요?
										</c:when>
										<c:otherwise>
											님과의 식사는 어떠셨나요?
										</c:otherwise>
									</c:choose>
									<span class="alarmTextClock">2시간전</span>
								</li>
							</c:if>
							</c:forEach>
						</ul>
					</div>
				</li>
				<li>
					<div class="chatIconWrap comIconWrap">
						<a href="${pageContext.request.contextPath}/chat/chatListByIcon">
							<img src="/pickmeal/resources/img/header/chat_alarm.png"
								alt="알림아이콘" class="chatImg comIconImg">
						</a>
						<div class="chatAlarmMark comIconMark">1</div>
					</div>
				</li>
			</ul>
		</c:if>
		<c:if test="${empty member }">
			<ul>
				<li><span class="navi_nickname">오늘은 뭐 먹지?</span></li>
				<li class="snbProfileLine">
					<div class="profileImgWrap">
						<img src="/pickmeal/resources/img/profile/nonUser.png"
							alt="프로필아이콘" class="profileImg">
					</div>
					<div class="profileAreaWrap navAlarmTextCom">
						<ul class="profileArea">
							<li><a href="${pageContext.request.contextPath}/member/viewSignIn">로그인</a></li>
							<li><a href="${pageContext.request.contextPath}/member/viewSignUp">회원가입</a></li>
						</ul>
					</div>
				</li>
			</ul>
		</c:if>
	</nav>
</header>
<%@ include file="/WEB-INF/views/chat/chat_alarm.jsp"%>