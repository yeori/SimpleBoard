<%@page import="ism.web.board.model.UserVO"%>
<%@page import="java.util.List"%>
<%@page import="ism.web.board.db.dao.IUserDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	List<UserVO> users = (List<UserVO>) request.getAttribute("users");
%>

한글 잘 나오나?

<% for ( int i =  0 ; i < users.size() ; i++  ){ %>
<%     UserVO user = users.get(i);                %>
<li><%= user.toString() %>
<%} %>
</body>
</html>