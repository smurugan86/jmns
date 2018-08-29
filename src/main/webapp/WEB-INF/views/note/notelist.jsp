<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<c:url value="/designs/css/murugan.css" var="cssURL"/>
<link  rel="stylesheet" href= "${cssURL}">
 <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
 
<!-- <link rel="stylesheet" type="text/css" href="/resources/css/murugan.css" /> -->
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My Note List</title>
<style type="text/css">
#fromdate{
float:left;
	width: 85px;
}
#todate{
float:left;
	width: 85px;
}
#tddate{
	width: 200px;
}
td.grid {
    /* white-space: nowrap;
    width: 125px; */
    overflow: hidden;    
    height: 25px;
}
</style>
</head>
<body>

 <c:url value="active" var="myNote"/>
<%@include file='/designs/page/header.jsp'%>

<div id="wrapper">
   <div id="content">   
   <div id="content-main">
   <h2>My Notes  : ${range}</h2>
  
  <%@include file='/designs/page/submenu.jsp'%>
  
		<table border=0 style="width: 100%; white-space: nowrap; table-layout: fixed;">
				<tr class="tabHead" >
  				<td style="width: 200px;">
  				Date
  				</td>
  				<td style="width: 400px;">
  				Title
  				</td>  				
  				<td>
  				Description
  				</td>
  				</tr>  				
  				<tr class="tabHead">
  				 <form method="get" action="/jmns/notelist" id="taskSearchform">
  				<td id="tddate">
  				<input type="text" name="date" value="${date}" class="datepicker" id="fromdate">
  				<input type="text" name="endDate" value="${endDate}" class="datepicker" id="todate">
  				<!-- <input type="submit" name="clear" value="clear" > -->
  				</td>
  				<td>
  				<input type="text" name="title" value="${title}">
  				</td>  				
  				<td>
  				<input type="text" name="description" value="${description}">
  				<input type="submit" name="Search" value="Search" form="taskSearchform">
  				</td>
  				</form>
  				</tr>
  			
  			<c:if test="${not empty lists}">
  			<% int i = 0;%>	
			<c:forEach var="listValue" items="${lists}">
			<%  i = i+1; 
			 if ( i % 2 == 0 ){%>				
				 <tr class='datatableodd'>
				<td><a href="/jmns/viewnote/${listValue["_id"]}">${listValue["date"]}</a></td>
				<td><a href="/jmns/editnote/${listValue["_id"]}">${listValue["title"]}</a></td>  				
  				<td class="grid">${listValue["description"]}</td>  				
  				</tr>
			<%}else{ %>		
				<tr>
				<td><a href="/jmns/viewnote/${listValue["_id"]}">${listValue["date"]}</a></td>
				<td><a href="/jmns/editnote/${listValue["_id"]}">${listValue["title"]}</a></td>  				
  				<td class="grid">${listValue["description"]}</td>  				
  				</tr>
  			 <%}%>		
			</c:forEach>
			</c:if>
		<c:if test="${empty lists}">
			<tr>
				<td colspan="4">
				    Notes Information Not Found
				</td>	
			</tr>			
		</c:if>
			
		</table>

	

		</div>
		<div id="content-right"></div>
		
		<a href="/jmns/export">export</a>
		
	</div>
	


<%@include file='/designs/page/footer.jsp'%>

	<div id="bottom"></div>
</div>

</body>
</html>