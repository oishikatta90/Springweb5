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
<script type="text/javascript">
	function pagingFormSubmit(currentPage) {
		var form = document.getElementById("pagingForm");
		var page = document.getElementById("page");

		page.value = currentPage;
		form.submit();
		}
</script>
</head>
<body>
<div class="centerdiv">
	<h2>[ 게시판 ]</h2><br>
	
	<table>
		<tr>
			<td class="white">전체 : ${navi.totalRecordsCount }</td>
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
	<br>
	<br>
		<!-- 페이지 이동 부분 -->
	<div id="navigator">
		
		<a href="javascript:pagingFormSubmit(${navi.currentPage - navi.pagePerGroup })">◁◁</a>
		&nbsp;&nbsp;
		<a href="javascript:pagingFormSubmit(${navi.currentPage - 1})">◀</a>
		&nbsp;&nbsp;
		
		<!-- 페이지 수 만큼 반복 -->
		<c:forEach var="counter" begin="${navi.startPageGroup }" end="${navi.endPageGroup }">
			<c:if test="${counter == navi.currentPage }"><b></c:if> 
				<a href="javascript:pagingFormSubmit(${counter })">&nbsp;${counter }&nbsp;</a>
			<c:if test="${counter == navi.currentPage }"><b></c:if> 		
		</c:forEach>
		&nbsp;&nbsp;
		<a href="javascript:pagingFormSubmit(${navi.currentPage + 1})">▶</a>
		&nbsp;&nbsp;
		<a href="javascript:pagingFormSubmit(${navi.currentPage + navi.pagePerGroup })">▷▷</a>
	</div>
	<br><br>
	<form id="pagingForm" method="get" action="list">
		<input type="hidden" name="page" id="page">
		제목 : <input type="text" name="searchText" value="${searchText }">
		<input type="button" onclick="pagingFormSubmit(1)" value="검색">
	</form>
	</div>
</body>
</html>