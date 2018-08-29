<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<c:url value="/designs/css/murugan.css" var="cssURL"/>
<link  rel="stylesheet" href= "${cssURL}">
 <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
 
<!-- <link rel="stylesheet" type="text/css" href="/resources/css/murugan.css" /> -->
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Account Details</title>

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
.Credit{
color:green;
}
.Debit{
color: red;
}
</style>

</head>
<body>

<c:url value="active" var="allRate"/>
<%@include file='/designs/page/header.jsp'%>
   

<div id="wrapper">
   
   <div id="content">
   
   <div id="content-main">
   <h2>My Account Details  : ${range}</h2>
   
	<%@include file='/designs/page/submenu.jsp'%>

		<table border=0 style="width: 100%;">
				<tr class="tabHead" >
  				<td>
  				productName
  				</td>
  				<td>
  				quantity
  				</td>
  				
  				<td>
  				Amount
  				</td>  			
  				</tr>  				
  				<tr class="tabHead">
  				 <form method="get" action="/jmns/accountlist" id="taskSearchform">
  				<td id="tddate">
  				<input type="text" name="productName" value="${productName}">
  				
  				</td>
  				<td>
  				<input type="text" name="quantity" value="${quantity}">
  				</td>
  				<td>
  				<input type="text" name="amount" value="${amount}">
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
				<td class="grid">
				<c:if test='${listValue["productName"] != null}'>
					<a href="/jmns/editrate/${listValue["_id"]}"> ${listValue["productName"]}</a>
				</c:if>				
				</td>
				<td class="grid">
				<c:if test='${listValue["quantity"] != null}'>				
					${listValue["quantity"]}					
				</c:if>				
				</td>
						
  				<td>${listValue["amount"]}</td>    				
  				</tr>
  		   <%}else{ %>	
				<tr>
				<td class="grid">
				<c:if test='${listValue["productName"] != null}'>
					<a href="/jmns/editrate/${listValue["_id"]}"> ${listValue["productName"]}</a>
				</c:if>				
				</td>
				<td class="grid">
				<c:if test='${listValue["quantity"] != null}'>				
					${listValue["quantity"]}					
				</c:if>				
				</td>
							
  				<td>${listValue["amount"]}</td>    				
  				</tr>
  			<%}%>	
			</c:forEach>
				
					
	</c:if>
	
	<c:if test="${empty lists}">
			<tr>
				<td colspan="4">
				    Rate Information Not Found
				</td>		
			</tr>		
	</c:if>
			
	<%-- <c:if test="${error != null}">
	<tr>
	<td colspan="4"></td>
	   ${error}
	</tr>
	</c:if> --%>
</table>
</div>

<div id="content-right"></div>
</div>
	


<%@include file='/designs/page/footer.jsp'%>

	<div id="bottom"></div>
</div>

</body>
</html>