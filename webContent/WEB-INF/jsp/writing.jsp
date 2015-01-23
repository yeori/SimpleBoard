<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글쓰기</title>
<jsp:include page="/WEB-INF/jsp/include/common-js.jsp"></jsp:include>
<script type="text/javascript">
(function(){
	var $ = jQuery;
	var ctxpath = ctxpath || '<%= pageContext.getServletContext().getContextPath() %>';
	
	function checkParams ( title, content) {
		// FIXME 일단 생략
	}
	
	function processNewPost() {
		var title = $('title').val();
		var content = $('content').val();
		checkParams ( title, content);
		
		$.post ( ctxpath + '/write.json', $('#frm').serialize(), function(json){
			if ( json.success) {
				document.location.href = ctxpath + json.nextUrl;
			} else {
				$('#error-panel').html (ERR_MSG[json.ecode].msg);
			}
		});
	}
	
	$(document).ready( function(){
		$('#btnPost').click ( processNewPost) ;
	});
})();
</script>
</head>
<body>
<div id="error-panel"></div>
<form name="frm" id="frm">
<div>제목 <input type="text" name="title" id="title" size="20"></div>
<div><textarea name="content" id="content"></textarea></div>
<input type="button" id="btnPost" value="글쓰기">
</form>
</body>
</html>