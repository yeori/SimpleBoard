<%@page import="ism.web.board.model.UserVO"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
</head>
<body>
<%
	UserVO user = (UserVO) session.getAttribute("user");
	if ( user != null) {
%>
<H2><%= user.getNickName() %> 님!!</H2>
<%
	}
%>
<h2>잘 나오냐?</h2>
</body>
</html>
