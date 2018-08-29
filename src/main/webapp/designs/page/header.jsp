<%@page import="org.eclipse.jetty.util.StringUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
<link rel="shortcut icon" href="/jmns/designs/images/ms.jpg">
<c:url value="/designs/css/murugan.css" var="cssURL"/>
<c:url value="/designs/css/jquery-ui.css" var="cssTwo"/>
<c:url value="/designs/scripts/jquery-1.9.1.js" var="scriptOne"/>
<c:url value="/designs/scripts/jquery-ui.js" var="scriptTwo"/>

<link  rel="stylesheet" href= "${cssURL}">
<link rel="stylesheet" href="${cssTwo}" />
<script src="${scriptOne}"></script>
<script src="${scriptTwo}"></script>  
   
  <style>
    .datepicker{
      
    }
   
    
    
  </style>
  <script>
  $(function() {
    $( ".datepicker" ).datepicker();
  });
  </script>
  
</head>


<body>



<div id="header">
<!-- <img alt="logo" src="/designs/images/ms.jpg"/> -->
<%-- <img alt="logo" src="<html:rewrite page='/designs/images/ms.jpg'/>" width="100%" height="20%"/> --%>

<c:url value="/designs/images/ms_logo.jpg" var="book.img"/>


 <%
            String userName = null;
 			String email = null;
 			String userRole = null;
 			String userId = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("name")){
                        userName = cookie.getValue();}
                    if (cookie.getName().equals("email")){
                    	email = cookie.getValue();}
                    if (cookie.getName().equals("userRole")){
                    	userRole = cookie.getValue();}
                    if (cookie.getName().equals("_id")){
                    	userId = cookie.getValue();}
                }
            }else{
            	System.out.println("cookies is Empty==============>");
            }
            if (StringUtil.isBlank(userName) || StringUtil.isBlank(email)){
                response.sendRedirect("/jmns");
            }
        %>
	
   <!--  <img src="/jmns/designs/images/MS_Logo.jpg" width="300px" height="40px"/> -->
   
   <table style="width: 100%; background: #5db2ff; color:#ffffff" border=0>
   		<tr>
   			<td>
   			<h2>Blogs & Day to Day Activities</h2>
   			<!-- <img src="/jmns/designs/images/MS_Logo.jpg" width="300px" height="40px"/> --> 
   			</td>
   			<td>
   			 
   			<h4>Welcome : <%=userName %></h4> 
    		
    		</td>
    		<td></td>
   			<td>
   			
   			<a href="/jmns" class="btn">  Logout <!-- <img src="/jmns/designs/images/logout.png" width="35px" height="35px"/> --></a>
   			</td>
   		</tr>
   </table>
</div>
 
 

 <div id="menutwo">     
 
   <c:if test="${myTask != null || addTask != null}">
	  	<c:url value="active" var="task"/>
	  </c:if>
	  <c:if test="${myNote != null || addNote != null}">
	  	<c:url value="active" var="note"/>
	  </c:if>
	  <c:if test="${allUser != null || addUser != null}">
	  	<c:url value="active" var="users"/>
	  </c:if>
	  <c:if test="${myAC != null || addAC != null}">
	  	<c:url value="active" var="acc"/>
	  </c:if>
	  <c:if test="${allCat != null || addCat != null}">
	  	<c:url value="active" var="cat"/>
	  </c:if>
	  <c:if test="${allPost != null || addPost != null}">
	  	<c:url value="active" var="tpost"/>
	  </c:if>
	  <c:if test="${all2Post != null}">
	  	<c:url value="active" var="ttpost"/>
	  </c:if>
	   
	  <ul>
		  <li class="${task}"><a href='/jmns/tasklist'>Task</a></li>
		  <li class="${note}"><a href='/jmns/notelist'>Notes</a></li>
		  
		  <li class="${tpost}"><a href='/jmns/postlist'>My Post</a></li>
		  
		  <li class="${acc}"><a href='/jmns/accountlist'>Accounts</a></li>
		  
		  <li class="${cat}"><a href='/jmns/categorylist'>Categories</a></li>
		  
		  <li class="${ttpost}"><a href='/jmns/allPost'>Blogs</a></li>
		  
		  <% if(userRole!=null && userRole.equalsIgnoreCase("ADMIN")){%>
		  	 <li class="${users}"><a href='/jmns/userlist'>Admin</a></li>
		  	<%--  <li class="${users}"><a href='/jmns/ratelist'>Rate</a></li> --%>
	  <% }%>
	  
	</ul>  
</div>
   

   
</body>
</html>