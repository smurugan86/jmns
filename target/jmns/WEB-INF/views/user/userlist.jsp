<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<c:url value="/designs/css/murugan.css" var="cssURL"/>
<link  rel="stylesheet" href= "${cssURL}">
<!-- <link rel="stylesheet" type="text/css" href="/resources/css/murugan.css" /> -->
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User List</title>
</head>

<script type="text/javascript">
        function testing() {
        	
        
        }
        </script>
        
        <style type="text/css">
        .txtOne{
        width: 100px;
        }
        .txtTwo{
        width: 150px;
        }
        .txtThree{
        width: 350px;
        }
        </style>
<body>
	
 <c:url value="active" var="allUser"/>
<%@include file='/designs/page/header.jsp'%>

    <h2>All Users Details</h2>
    
    <%@include file='/designs/page/submenu.jsp'%>
    
	<c:if test="${not empty lists}">

		<table border=0 style="width: 100%;">
				<tr class="tabHead" >
  				<td>
  				First Name
  				</td>
  				<td>
  				Last Name
  				</td>
  				<td>
  				Email
  				</td>
  				<td>
  				Role
  				</td>
  				<td>
  				Status
  				</td>
  				<td>
  				Last Login
  				</td>  				
  				<td>
  				Update
  				</td>
  				<td>
  				AutoLogin
  				</td>
  				</tr>
  				
  				<tr class="tabHead">
  				<form method="get" action="/jmns/userlist" id="userSearchform">
  				<td style="width: 100px;">
  				<input type="text" name="firstName" value="${firstName}" class="txtTwo">
  				</td>
  				<td style="width: 100px;">
  				<input type="text" name="lastName" value="${lastName}" class="txtTwo">
  				</td>
  				<td style="width: 300px;">
  				<input type="text" name="email" value="${email}" class="txtThree">
  				</td>
  				
  				<td style="width: 80px;">
  				<input type="text" name="userRole" value="${userRole}" class="txtOne">
  				</td>
  				
  				<td style="width: 80px;">
  				<input type="text" name="status" value="${status}" class="txtOne">
  				</td>
  				
  				<td style="width: 100px;">
  				<input type="text" name="lastLoginDate" value="${lastLoginDate}" class="txtTwo">
  				</td>
  				
  				<td style="width: 80px;">
  				<input type="submit" name="Search" value="Search" form="userSearchform">
  				</td>
				<td style="width: 80px;">
				 
				</td>
  				</form>
  				</tr>
  			<% int i = 0;%>
			<c:forEach var="listValue" items="${lists}">
			
			<%  i = i+1; 
			 if ( i % 2 == 0 ){%>				
			 	<tr class='datatableodd'>
			 	<td class="grid">${listValue["firstName"]}</td>
				<td class="grid">${listValue["lastName"]}</td>
  				<td class="grid">${listValue["email"]}</td> 
  				<td class="grid">${listValue["userRole"]}</td> 
  				<td class="grid">${listValue["status"]}</td> 
  				<td class="grid">
  				<c:if test='${listValue["lastLoginDate"] != null}'>
  				${listValue["lastLoginDate"]}
  				</c:if>
  				</td> 				
  				<td>
  				<a href="/jmns/editpage/${listValue["_id"]}">Edit</a></td> 
  				<td>
  				<%-- <a href="/jmns/autoLogin/${listValue["email"]}/${listValue["textPassword"]}" onclick = "javascript:testing()">login</a> --%>
  				<form method="post" id="taskform" action="/jmns/login"> 
		<input type="hidden" name="username" value="${listValue["email"]}">
		<input type="hidden" name="password" value="${listValue["textPassword"]}">
		<button class="btn">Login</button>
	</form>
  				</td>  				
  				</tr>
  			<%}else{ %>			
				<tr>
				<td class="grid">${listValue["firstName"]}</td>
				<td class="grid">${listValue["lastName"]}</td>
  				<td class="grid">${listValue["email"]}</td> 
  				<td class="grid">${listValue["userRole"]}</td> 
  				<td class="grid">${listValue["status"]}</td>  	
  				<td class="grid">
  				<c:if test='${listValue["lastLoginDate"] != null}'>
  				${listValue["lastLoginDate"]}
  				</c:if>
  				</td>			
  				<td><a href="/jmns/editpage/${listValue["_id"]}">Edit</a></td> 
  				<td>
  				<%-- <a href="/jmns/autoLogin/${listValue["email"]}/${listValue["textPassword"]}"  onclick = "javascript:testing()">login</a> --%>  				
  				<form method="post" id="taskform" action="/jmns/login"> 
		<input type="hidden" name="username" value="${listValue["email"]}">
		<input type="hidden" name="password" value="${listValue["textPassword"]}">
		<button class="btn">Login</button>
	</form>
	
  				</td>  				
  				</tr>
  			<%}%>
			</c:forEach>
		</table>

	</c:if>

<%@include file='/designs/page/footer.jsp'%>
</body>
</html>