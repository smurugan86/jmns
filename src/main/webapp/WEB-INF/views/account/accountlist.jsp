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
td.grid {
    /* white-space: nowrap;
    width: 125px; */
    overflow: hidden;    
    height: 25px;
}
</style>

</head>
<body>

<c:url value="active" var="myAC"/>
<%@include file='/designs/page/header.jsp'%>
   

<div id="wrapper">
   
   <div id="content">
   
   <div id="content-main">
   <h2>My Account Details  : ${range}</h2>
   
	<%@include file='/designs/page/submenu.jsp'%>

		<table border=0 style="width: 100%;">
				<tr class="tabHead" >
  				<td style="width: 140px;">
  				Date
  				</td>
  				<td style="width: 120px;">
  				Account Type
  				</td>
  				<td style="width: 150px;">
  				Category
  				</td>
  				<td>
  				Description
  				</td>
  				<td>
  				Amount
  				</td>  			
  				</tr>  				
  				<tr class="tabHead">
  				 <form method="get" action="/jmns/accountlist" id="taskSearchform">
  				<td id="tddate">
  				<input type="text" name="date" value="${date}" class="datepicker" id="fromdate">
  				<input type="text" name="endDate" value="${endDate}" class="datepicker" id="todate">
  				<!-- <input type="submit" name="clear" value="clear" > -->
  				</td>
  				<td>
  				<select size="1" name="accountType" selected=${accountType}>
  					<option value ="">Select</option>
        			<option value ="Credit">Credit</option>
        			<option value ="Debit">Debit</option>        
		 		 </select>
  				</td>
  				
  				<td>
  				<input type="text" name="categoryName" value="${categoryName}">
  				</td>
  				
  				<td>
  				<input type="text" name="description" value="${description}">
  				</td>
  				<td>
  				<input type="text" name="amount" value="${amount}" style="width: 100px;">
  				<input type="submit" name="Search" value="Search" form="taskSearchform">
  				<a href="/jmns/accExport" form="taskSearchform">Export</a>
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
				<c:if test='${listValue["date"] != null}'>
					${listValue["date"]}
				</c:if>				
				</td>
				<td class="grid">
				<c:if test='${listValue["accountType"] != null}'>				
					${listValue["accountType"]}					
				</c:if>				
				</td>
				<td class="grid">
				<c:if test='${listValue["categoryName"] != null}'>
					${listValue["categoryName"]}
				</c:if>				
				</td>				
				<td class="grid">
				<c:if test='${listValue["description"] != null}'>
					<a href="/jmns/editaccount/${listValue["_id"]}">${listValue["description"]}</a>				
				</c:if>	
				 </td>			
  				<td class="grid">${listValue["amount"]}</td>    				
  				</tr>
  		   <%}else{ %>	
				<tr>
				<td class="grid">
				<c:if test='${listValue["date"] != null}'>
					${listValue["date"]}
				</c:if>				
				</td>
				<td class="grid">
				<c:if test='${listValue["accountType"] != null}'>				
					${listValue["accountType"]}					
				</c:if>				
				</td>
				<td class="grid">
				<c:if test='${listValue["categoryName"] != null}'>
					${listValue["categoryName"]}
				</c:if>				
				</td>				
				<td class="grid">
				<c:if test='${listValue["description"] != null}'>
					<a href="/jmns/editaccount/${listValue["_id"]}">${listValue["description"]}</a>				
				</c:if>	
				 </td>			
  				<td class="grid">${listValue["amount"]}</td>    				
  				</tr>
  			<%}%>	
			</c:forEach>
				
			<tr class="tabHead">
			<td colspan="3" >
			</td>
			<td>
			   Total Credit
			</td>
			<td class="Credit">
			   ${credit}
			</td>
			</tr>
			
			<tr class="tabHead">
			<td colspan="3">
			</td>
			<td>
			   Total Debit
			</td>
			<td class="Debit">
			   ${debit}
			</td>
			</tr>
			
			
			<tr class="tabHead">
			<td colspan="3">
			</td>
			<td>
			   Balance
			</td>
			<td class="Credit">
			   ${balance}
			</td>
			</tr>			
	</c:if>
	
	<c:if test="${empty lists}">
			<tr>
				<td colspan="4">
				    Account Information Not Found
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