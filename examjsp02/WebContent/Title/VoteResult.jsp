<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>설문 조사 결과</title>
<style type="text/css">
b{
	font-size:16pt;
}
</style>
</head>
<body>
	<%@ page import="sp.*, java.util.*"%>
	<%
		VoteDAO dao = new VoteDAO();
	%>
	<%
		request.setCharacterEncoding("UTF-8");
		String sub = request.getParameter("movie");
		String other = request.getParameter("name");
		
		if(!(other.equals(""))){
			dao.InsertForm(other);
			response.sendRedirect("/examjsp02/Title/Vote.jsp");
		}else{
			dao.countPlus(sub);
			response.sendRedirect("/examjsp02/Title/index.jsp");
		}
		
		
 	%>
		
		
</body>
</html>