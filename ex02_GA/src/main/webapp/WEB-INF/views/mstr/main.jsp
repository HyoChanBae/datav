<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<div class="card-body">
<div class="table-responsive">
    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
        <thead>
            <tr>
                <th>#��ȣ</th>
                <th>����</th>
                <th>�ۼ���</th>
                <th>�ۼ���</th>
                <th>������</th>
            </tr>
        </thead>
      	<c:forEach items="${menu}" var="menu">
      	<tr>
      		<td><c:out value="${menu}" /></td>
      	</tr>
      	</c:forEach>
    </table>
</div>
</div>
                        
</body>
</html>