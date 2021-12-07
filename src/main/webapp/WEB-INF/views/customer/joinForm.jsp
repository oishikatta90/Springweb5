<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="../resources/css/default.css">
<script type="text/javascript">
	function formCheck() {
		//유효성 검사
		//ID는 3~10자리 입력 PASSWORD 3~10 비밀번호와 비밀번호 확인 일치
		//이름은 필수입력
		return true;

		}
	function idcheckOpen() {
		window.open("idcheck", "newwin", "top=200, left=400, width=400, height=300, resizeable=no");
		}

</script>
</head>
<body>
	<div class="centerdiv">
		<h1>[ 회원가입 ]</h1>
		<form id="joinForm" action="join" method="post" onsubmit="return formCheck()">
		<table>
			<tr>
				<th>ID</th>
				<td>
					<input type="text" name="custid" id="custid" placeholder="id 중복확인 이용">
					<input type="button" value="ID 중복확인" onclick="idcheckOpen()">
				</td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td>
					<input type="password" name="password" id="password" placeholder="비밀번호 입력">
					<input type="password" name="password2" id="password2" placeholder="비밀번호 다시 입력">
				</td>
			</tr>
			<tr>
				<th>이름</th>
				<td>
					<input type="text" name="name" id="name" placeholder="이름 입력">
				</td>
			</tr>
			<tr>
				<th>이메일</th>
				<td>
					<input type="text" name="email" id="email" placeholder="이메일 입력">
				</td>
			</tr>
			<tr>
				<th>성별</th>
				<td>
					<input type="radio" name="gender" value="male" checked> 남성
					<input type="radio" name="gender" value="female"> 여성
				</td>
			</tr>
			<tr>
				<th>주민번호</th>
				<td>
					<input type="text" name="ssn" placeholder="주민번호 입력">
				</td>
			</tr>
			<tr>
				<th>주소</th>
				<td>
					<input type="text" name="address" placeholder="주소 입력" style="width:300px;">
				</td>
			</tr>
		</table>
		<br>
		<input type="submit" value="회원가입">
		<input type="reset" value="리셋	">
		</form>
	</div>
</body>
</html>