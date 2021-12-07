<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ID 중복확인</title>
<script type="text/javascript">
	function formCheck() {
		var searchId = document.getElementById("searchId");

		if (searchId.value.length < 3) {
			alert('검색할 ID를 3자 이상 입력하세요');
			return false;
		}

		return true;
	}

	function idSelected(id) {
		opener.document.getElementById("custid").value = id;
		this.close();
	}
</script>
</head>
<body>
	<h2>[ ID 중복확인 ]</h2>

	<!-- 검색 폼 -->
	<form action="idcheck" method="post" onsubmit="return formCheck();">
		<input type="text" name="searchId" id="searchId"> <input
			type="submit" value="검색">
	</form>

	<c:if test="${search }">
		<c:if test="${searchResult == null }">
			<p>${searchId }:사용할 수 있는 ID 입니다.</p>
			<p>
				<input type="button" value="ID 사용하기"
					onclick="idSelected('${searchId}')">
		</c:if>
		<c:if test="${searchResult != null }">
			<p>${searchId }:이미 사용중인인 ID 입니다.
		</c:if>
	</c:if>
</body>
</html>