<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
 <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SB Admin 2 - Register</title>

    <!-- Custom fonts for this template-->
    <link href="/resrouces/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="/resrouces/css/sb-admin-2.min.css" rel="stylesheet">
</head>
<body>
	<div class="log-c">
		<h1>VIC Global</h1>
		<div style="font-size:1.5vmax;text-align:center;margin-bottom:2vmax;">게시물 등록</div>
	<form action="/board/register" method="post">
		<div class="log-i">
			<div>
				<div class="title">title</div>
				<input id="title" name="title" type="text" placeholder="title">
			</div>
			<div >
				<div class="lgin">text area</div>
				<input id="content" name="content" type="text" placeholder="text area">
			</div>
			<div >
				<div class="lgin">writer</div>
				<input id="writer" name="writer" type="text" placeholder="writer">
			</div>
			<div class="log-btn">
				<button>Register</button>
			</div>
		</div>
	 </form>
</body>
</html>