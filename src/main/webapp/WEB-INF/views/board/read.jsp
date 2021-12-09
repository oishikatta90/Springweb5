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
<script type="text/javascript">
	function deleteCheck(boardnum) {
		if (confirm("정말 삭제하시겠습니까?")) {
			location.href = 'delete?boardnum=' + boardnum;
			}
		}
	function replyFormCheck() {
		var replytxt = document.getElementById("replytxt");
		if (replytxt.value.length < 1) {
			alert('리플 내용을 입력해 주세요!');
			return false;
		}
		return true;
	}
	function replyEditForm(replynum, boardnum, replytext) {
		//해당 리플번호를 붙여 생성한 div 태그에 접근 
		var div = document.getElementById("div" + replynum);
		var str = '<form name="editForm' + replynum + '"action = "replyEdit" method ="post">';
		str += '<input type = "hidden" name="replynum" value="'+ replynum +'">';
		str += '<input type = "hidden" name="boardnum" value="'+ boardnum +'">';
		str += '<input type = "text" name="text" value="'+ replytext +'" style="width:530px;">';
		str += '<a href="javascript:replyEdit(document.editForm' + replynum
				+ ')">저장</a>';
		str += '<a href="javascript:replyEditCancel(document.getElementById(\'div'
				+ replynum + '\'))">취소</a>';
		str += '</form>';	
		div.innerHTML = str;
	}

	function replyEdit(form) {
		if (confirm('댓글을 수정하시겠습니까?')) {
			form.submit();
			}
	}

	function replyEditCancel(div) {
		div.innerHTML = '';
	}

	function replyDelete(replynum, boardnum) {
		location.href = 'replyDelete?replynum=' + replynum + '&boardnum='
				+ boardnum;
	}
</script>
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
				<a href="list">게시판</a>
			</c:if>
		</div>
		<br>
		<!--  리플 작성 폼 -->
		<form id="replyForm" action="replyWrite" method="post"
			onsubmit="return replyFormCheck()">
			리플내용 <input type="hidden" name="boardnum" value="${board.boardnum }">
			<input type="text" name="text" id="replytxt" style="width: 500px;">
			<input type="submit" value="확인">
		</form>

		<table class="reply">
			<c:forEach var="reply" items="${replyList }">
				<tr>
					<td class="replyid"><b>${reply.id }</b></td>
					<td class="replytext"><b>${reply.text }</b></td>
					<td class="replybutton"><c:if
							test="${customer.custid == reply.id}">
							<a
								href="javascript:replyEditForm('${reply.replynum }', '${reply.boardnum }', '${reply.text }')">수정</a>
						</c:if></td>
					<td class="replybutton"><c:if
							test="${customer.custid == reply.id}">
							<a
								href="javascript:replyDelete(${reply.replynum }, ${board.boardnum })">삭제</a>
						</c:if></td>
				</tr>
				<tr>
					<td class="white" colspan="4"><div id="div${reply.replynum }"></div></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>