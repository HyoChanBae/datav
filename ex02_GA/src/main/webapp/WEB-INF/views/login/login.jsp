<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link type="text/css" rel="stylesheet" href="${path}/resources/css/login.css">
</head>
<style>
	body{
	  height:100%;
	  background-image: url('${path}/resources/img/—Pngtree—nature leaves background_1161551.png');
      background-size:cover; /* 이미지를 화면에 맞게 조절 */
      background-repeat: no-repeat; /* 배경 이미지 반복 없음 */
      padding-left: 100px; 
	}
</style>
<body>
	<div class="log-c">
		<h1>VIC Global</h1>
		<div style="font-size:1.5vmax;text-align:center;margin-bottom:2vmax;">MSTR 웹커스터마이징 테스트</div>
	<form action="/login" method="post">
		<div class="log-i">
			<div>
				<div class="lgin">아이디</div>
				<input id="userid" name="userid" type="text" placeholder=" ID">
			</div>
			<div >
				<div class="lgin">비밀번호</div>
				<input id="userpw" name="userpw" type="password" placeholder=" PW">
			</div>
			</div>
			<div class="log-btn">
				<button>Log in</button>
			</div>
		</div>
	 </form>
</body>
</html>