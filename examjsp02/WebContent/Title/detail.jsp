<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@ page import="sp.*, java.util.*"%>
	<%
		VoteDAO dao = new VoteDAO();
		List<VoteDTO> lv = dao.selectAll();
	%>
	<table>
			<tr>
				<td>좋아하는 영화 결과 보기!!</td>
				
				<%if(lv != null){
						for (VoteDTO v : lv) {%>
							<tr>
								<td>
									<%=v.getItem()%> ___ <%=v.getCount()%>표
								</td> 
							</tr>
						<%}
					}else{%>
						
							<h2>먼저 투표해주세요 ~~</h2>
					<%}%>

		</table>
	
</body>
</html>