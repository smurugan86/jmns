<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<c:url value="/designs/css/murugan.css" var="cssURL"/>
<link  rel="stylesheet" href= "${cssURL}">
 <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
 
<!-- <link rel="stylesheet" type="text/css" href="/resources/css/murugan.css" /> -->
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My Task List</title>
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
tr.datatableodd{
	background: #e7f2ff;
}

/* thead{
background: #FF6690;
display: block;
}

tbody.gridT{
display: block;
    height: 500px;       
    overflow-y: auto;    
    overflow-x: hidden;  
} */

</style>
</head>
<body>

<c:url value="active" var="myTask"/>
<%@include file='/designs/page/header.jsp'%>

<div id="wrapper">
   <div id="content">   
   <div id="content-main">
   <h2>My Task Details  : ${range} </h2>
  
  <%@include file='/designs/page/submenu.jsp'%>

		<table border=0 style="width: 100%; white-space: nowrap; table-layout: fixed;" class="gridT">
		<thead>
				<tr class="tabHead" >
  				<td style="width: 170px;">
  				Date
  				</td>
  				<td style="width: 250px;">
  				Title
  				</td>
  				<td style="width: 250px;">
  				User Story
  				</td>
  				<td>
  				Description
  				</td>
  				</tr>  				
  				<tr class="tabHead">
  				 <form method="get" action="/jmns/tasklist" id="taskSearchform">
  				<td id="tddate">
  				<input type="text" name="date" value="${date}" class="datepicker" id="fromdate">
  				<input type="text" name="endDate" value="${endDate}" class="datepicker" id="todate">
  				<!-- <input type="submit" name="clear" value="clear" > -->
  				</td>
  				<td>
  				<input type="text" name="title" value="${title}">
  				</td>
  				<td>
  				<input type="text" name="userStory" value="${userStory}">
  				</td>
  				<td>
  				<input type="text" name="description" value="${description}">
  				<input type="submit" name="Search" value="Search" form="taskSearchform">
  				</td>
  				</form>
  				</tr>
  			</thead>
  			<c:if test="${not empty lists}">
  			<% int i = 0;%>
			<c:forEach var="listValue" items="${lists}">
			<%  i = i+1; 
			 if ( i % 2 == 0 ){%>				
			 <tr class='datatableodd'>
				<td class="grid"><a href="/jmns/viewtask/${listValue["_id"]}">${listValue["date"]}</a></td>
				<td class="grid"><a href="/jmns/edittask/${listValue["_id"]}">${listValue["title"]}</a></td>
  				<td class="grid">${listValue["userStory"]}</td>
  				<td class="grid">${listValue["description"]}</td>  				
  			</tr>  				
			<%}else{ %>			 		
			 <tr>
				<td class="grid"><a href="/jmns/viewtask/${listValue["_id"]}">${listValue["date"]}</a></td>
				<td class="grid"><a href="/jmns/edittask/${listValue["_id"]}">${listValue["title"]}</a></td>
  				<td class="grid">${listValue["userStory"]}</td>
  				<td class="grid">${listValue["description"]}</td>  				
  			 </tr>  			
			 <%}%>			
				
			</c:forEach>
			
			</c:if>
		<c:if test="${empty lists}">
			<tr>
				<td colspan="4">
				    Task Information Not Found
				</td>	
			</tr>			
		</c:if>
			
		</table>

	

		</div>
		<div id="content-right"></div>
	</div>
	


<%@include file='/designs/page/footer.jsp'%>

	<div id="bottom"></div>
</div>

</body>
</html>