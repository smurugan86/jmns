<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<c:url value="/designs/css/murugan.css" var="cssURL"/>
<link  rel="stylesheet" href= "${cssURL}">
 <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
 
<!-- <link rel="stylesheet" type="text/css" href="/resources/css/murugan.css" /> -->
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Category List</title>
</head>
<body>

 <c:url value="active" var="allCat"/>
<%@include file='/designs/page/header.jsp'%>
   
   
<div id="wrapper">
   
   <div id="content">
   
   <div id="content-main">
   <h2>Category Details</h2>
   
	<%@include file='/designs/page/submenu.jsp'%>

		<table border=0 style="width: 100%;">
				<tr class="tabHead" >
  				<td>
  				 Category Name
  				</td>
  				<td>
  				 Category Type
  				</td>
  				  	<td>
  				  	</td>		
  				</tr>  				
  				<tr class="tabHead">
  				 <form method="get" action="/jmns/accountlist" id="taskSearchform">
  				<td>
  				<input type="text" name="date" value="${name}">
  				</td>
  				<td>
  				<select size="1" name="accountType" selected=${type}>
  					<option value ="">Select</option>
        			<option value ="Credit">Credit</option>
        			<option value ="Debit">Debit</option>        
		 		 </select>
  				</td>  				
  				<td>  				
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
			 <td>
				<c:if test='${listValue["name"] != null}'>
					${listValue["name"]}
				</c:if>				
				</td>
				<td>
				<c:if test='${listValue["type"] != null}'>
					${listValue["type"]}
				</c:if>				
				</td>							
				<td>
					
				 </td>			  					
  				</tr>
			
			<%}else{ %>			
				<tr>
				<td>
				<c:if test='${listValue["name"] != null}'>
					${listValue["name"]}
				</c:if>				
				</td>
				<td>
				<c:if test='${listValue["type"] != null}'>
					${listValue["type"]}
				</c:if>				
				</td>							
				<td>
					
				 </td>			  					
  				</tr>
  				
  				 <%}%>	
			</c:forEach>	
			
	</c:if>
	
	<c:if test="${error != null}">
	<tr>
	<td colspan="4"></td>
	   ${error}
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