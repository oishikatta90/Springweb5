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
	function formCheck() {
		var id = document.getElementById("id");
		var password = document.getElementById("password");
		console.log(id);
		console.log(password);
		debugger;
		if (id.value == "" || password.value == "") {
			alert("ID와 Password를 입력해 주세요");
			return false;
		}

		return true;
	}
</script>
</head>
<body>
	<div class="centerdiv">
		<h2>[ 로그인 ]</h2>
		<form id="loginForm" action="login" method="post"
			onsubmit="return formCheck()">
			<table>
				<tr>
					<td>ID</td>
					<td><input type="text" name="id" id="id"></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="password" name="password" id="password"></td>
				</tr>
				<tr>
					<td class="white"></td>
					<td class="white">
						<div class="errorMsg">${errorMsg }</div>
					</td>
				</tr>
				<tr>
					<td colspan="2" class="center white"><input type="submit"
						value="Login"></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>