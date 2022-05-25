<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	
%>
<%@ page import="sp.*, java.util.*"%>
<%
	VoteDAO vdao = new VoteDAO();
	List<VoteDTO> lv = vdao.selectAll();
%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>설문 조사</title>
</head>
<body>
	<h2>좋아하는 영화 설문조사</h2>
	<form action="VoteResult.jsp" method="post" name="resForm">

		<table>
			<tr>
				<td>좋아하는 영화:</td>
				
				<%if(lv != null){
						for (VoteDTO v : lv) {
								if(v!=null){%>
									<tr>
										<td>
											<input type="radio" name="movie" value="<%=v.getItem()%>"><%=v.getItem()%>
											&nbsp;&nbsp;
											<a href="delete.jsp?movieName=<%= v.getItem()%>"><input type="button" value="삭제"/></a>
										</td> 
									</tr>
								<%}
						}
					}else{%>
						
							<h2>항목이 없습니다, 좋아하는 영화를 입력해주세요.</h2>
					<%}%>

			
			<tr>
				<%--<td>직접 입력하기:</td>
				<td><input type="text" name="name" size="20"></td> --%>
				<td>직접 입력하기:<input type="text" name="name" size="20"></td>
			</tr>
			<%--<tr align="center">
				<td><input type="submit" value="전송"></td>
				<td><input type="reset" value="취소"></td>
				<td><a href = "index.jsp"><input type="button" value="뒤로가기"></a></td>
				
			</tr> --%>
			
		</table>
		<div style="margin-left:5px; margin-top:10px;">
			<input type="submit" value="전송">
			<input type="reset" value="취소">
			<a href = "index.jsp"><input type="button" value="뒤로가기"></a>
		</div>
	</form>
</body>
</html>