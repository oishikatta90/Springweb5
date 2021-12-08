<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	<h2>[ 게시판 ]</h2><br>
	
	<table>
		<tr>
			<td class="white"></td>
			<td class="white" colspan="3"></td>
			<td class="white"><input type="button" value="글쓰기" onclick="location.href='write';"></td>
			<td class="white"><a href="/"><input type="button" value="홈으로"></a>
		</tr>
		<tr>
			<th>번호</th>
			<th style="width:220px">제목</th>
			<th>작성자</th>
			<th>조회수</th>
			<th>등록일</th>
		<tr>
		
		<!-- 게시글 반복 출력 -->
		<c:forEach var="board" items="${boardList }">
			<tr>
				<td class="center">${board.boardnum }</td>
				<td><a href="read?boardnum=${board.boardnum }">${board.title }</a></td>
				<td>${board.id }</td>
				<td>${board.hits }</td>
				<td>${board.inputdate }</td>
			</tr>
		</c:forEach>
	</table>
</div>
</body>
</html>