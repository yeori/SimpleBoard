<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원가입</title>
<jsp:include page="/WEB-INF/jsp/include/common-js.jsp"></jsp:include>
<script type="text/javascript">
$(document).ready ( function(){
	
	var userid = $("#userid").val().trim();
	var pass = $("#password").val().trim();
	
	if ( userid.length() == 0 ) {
		alert ( "아이디 제대로 입력해라");
	}
	
	if ( pass.length() == 0 ) {
		alert ("패스워드가 엉망이다");
	}
	
	$.post( '/join.json', $('#frm').serialize(), function(response){
		if ( response.success) {
			alert ( '회원 가입 성공');
		} else {
			alert ( '회원 가입 실패 : ' + response.cause);
		}
	});
});
</script>
</head>
<body>
<form id="frm">
<div> 아이디 : <input type="text" id="userid"></div>
<div> 비밀번호 : <input type="password" id="password"></div>
<div> <input type="button" id="btn" value="가입하기"></div>
</form>
</body>
</html>