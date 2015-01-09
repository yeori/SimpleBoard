<%@page import="java.util.List"%>
<%@page import="ism.web.board.model.PostingVO"%>
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
	List<PostingVO> posting = (List<PostingVO>) request.getAttribute("postings");
%>
<% for ( int i =  0 ; i < posting.size() ; i++  ){ %>
<%     PostingVO pos = posting.get(i);                %>
<li><%= pos.getTitle() %>
<%} %>
</body>
</html>