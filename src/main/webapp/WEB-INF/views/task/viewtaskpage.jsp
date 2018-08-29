<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<c:url value="/designs/css/murugan.css" var="cssURL"/>
<link  rel="stylesheet" href= "${cssURL}">
<!-- <link rel="stylesheet" type="text/css" href="/resources/css/murugan.css" /> -->
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Task</title>
</head>
<body>
  
<c:url value="active" var="addTask"/> 
<%@include file='/designs/page/header.jsp'%>
     
  <h2>Task</h2>
  
  <%@include file='/designs/page/submenu.jsp'%>
  
  <hr>  
  <label class="labelRed">Title : </label> <a href="/jmns/edittask/${_id}"> ${title} </a> <br>
  <label class="labelRed">Date  : </label> ${date} <br>  
  <label class="labelRed">User Story  : </label> ${userStory} <br> <hr>
  <label class="labelRed">Description : </label><p class="view">${description}</p> <br>   
  <hr>
   
   
    
<%@include file='/designs/page/footer.jsp'%>
</body>
</html>