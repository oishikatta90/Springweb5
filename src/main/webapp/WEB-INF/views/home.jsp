<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />

<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>[ SpringWeb5 ]</h1>
<c:if test="${customer != null }">
	${customer.name }(${customer.custid })님 환영합니다.<br>
</c:if>
<ul>
	<c:if test="${customer == null }">
		<li><a href="customer/join">회원가입</a></li>
	<li><a href="customer/login">로그인</a></li>
	</c:if>
	<c:if test="${customer	 != null }">
		<li><a href="customer/logout">로그아웃</a></li>
		<li><a href="customer/update">개인정보 수정</a></li>
	</c:if>
	<li><a href="board/list">게시판</a></li>
</ul>
</body>
</html>
