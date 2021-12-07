<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글쓰기</title>
<link rel="stylesheet" type="text/css"
	href="../resources/css/default.css">
<script type="text/javascript">
	function formCheck() {
		var title = document.getElementById("title");
		var content = document.getElementById("content");

		if(title.value.length < 1) {
				alert('제목을 입력해주세요!');
				return false;
			}
		if(content.value.length < 1) {
			alert('내용을 입력해주세요!');
			}
		return true;
		}
</script>
</head>
<body>
	<div class="centerdiv">
		<h2>[ 글쓰기 ]</h2>
		<form id="writeForm" action="write" method="post" onsubmit="return formCheck();">
			<table>
				<tr>
					<th>제목</th>
					<td><input type="text" name="title" id="title"
						style="width: 400px"></td>
				</tr>
				<tr>
					<th>내용</th>
					<td><textarea name="content" id="content"
							style="width: 400px; height: 200px; resize: none"></textarea></td>
				</tr>
				<tr>
					<th>파일첨부</th>
					<td><input type="file" name="upload" size="30"></td>
				</tr>
				<tr>
					<td colspan="2" class="white center"><input type="submit"
						value="저장">
				</tr>
			</table>
		</form>
	</div>
</body>
</html>