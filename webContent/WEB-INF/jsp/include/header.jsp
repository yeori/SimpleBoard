<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set  var="user" value="${sessionScope.user }"/>
<c:set var="ctxPath" value="${pageContext.request.contextPath }"/>
<a href="${ctxPath }">홈으로</a> | <c:if test="${empty user}"><a href="${ctxPath}/login">로그인</a></c:if>
<c:if test="${not empty user}"><a href="${ctxPath}/logout">로그아웃</a></c:if>
| <a href="${ctxPath }/write">글쓰기</a> | <a href="${ctxPath }/postings">글보기</a> 
