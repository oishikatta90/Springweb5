<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="../resources/css/default.css">
</head>
<body>
	<div class="centerdiv">
		<h2>[ 글내용 ]</h2>
		<table>
			<tr>
				<th style="width: 100px;">작성자</th>
				<td style="width: 600px;">${board.id }</td>
			</tr>
			<tr>
				<th>조회수</th>
				<td>${board.hits }
			</tr>
			<tr>
				<th>제목</th>
				<td>${board.title }
			</tr>
			<tr>
				<th>내용</th>
				<td>${board.content }
			</tr>
			<tr>
				<th>첨부파일</th>
				<td>${board.originalfile }
			</tr>
		</table>
		<div id="navigator">
		<c:if test="${customer.custid == board.id }">
			<a href="javascript:deleteCheck(${board.boardnum })">삭제</a>
			<a href="edit?boardnum=${board.boardnum }">수정</a>
			<a href="list">게시판으로</a>
		</c:if>
		</div>
	</div>
</body>
</html>