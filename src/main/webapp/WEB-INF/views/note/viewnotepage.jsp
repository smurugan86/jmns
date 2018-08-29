<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<c:url value="/designs/css/murugan.css" var="cssURL"/>
<link  rel="stylesheet" href= "${cssURL}">
<!-- <link rel="stylesheet" type="text/css" href="/resources/css/murugan.css" /> -->
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Note</title>
</head>
<body>
  
<c:url value="active" var="addNote"/> 
<%@include file='/designs/page/header.jsp'%>
     
  <h2>Note</h2>
  
  <%@include file='/designs/page/submenu.jsp'%>
  
  <hr>
  <label class="labelRed">Title : </label> <a href="/jmns/editnote/${_id}"> ${title} </a> <br> 
  <label class="labelRed">Date  : </label> ${date} <br>  <hr> 
  <label class="labelRed">Description : </label><p>${description}</p> <br>   
  <hr>
        
<%@include file='/designs/page/footer.jsp'%>
</body>
</html>