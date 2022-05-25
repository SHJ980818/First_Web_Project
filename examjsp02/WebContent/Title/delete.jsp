<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="sp.*"%>
<meta charset="UTF-8">
   <%
   	VoteDAO dao = new VoteDAO();
    String s = request.getParameter("movieName");
    
    dao.itemdelete(s);
    response.sendRedirect("Vote.jsp");
   %>
   
  