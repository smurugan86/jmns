<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<c:url value="/designs/css/murugan.css" var="cssURL"/>
<link  rel="stylesheet" href= "${cssURL}">
<!-- <link rel="stylesheet" type="text/css" href="/resources/css/murugan.css" /> -->
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Blogs</title>
</head>
<body>
  
 <c:url value="active" var="all2Post"/>
<%@include file='/designs/page/header.jsp'%>
     
  <h2>${post["author"]} Bolg </h2>
 
  <hr>  
  <h2>${post["title"]}</h2> 
  Posted ${post["date"]}<i> By ${post["author"]}</i><br>
  <hr>
${post["post"]}
<p>
    <h4>Post Under : </h4>
   
   
<c:forEach var="tag" items="${post['tags']}">
<a href="/jmns/postlistbytag/${tag}"> ${tag}</a> 
</c:forEach>
  
  <br>
 <h4>Post Comments :</h4> 
   
<c:forEach var="comment" items="${post['comments']}">
	Author: ${comment["author"]}<br>	
    Text : ${comment["body"]}<br>
    Email : ${comment["email"]}<br>
     <hr>     
</c:forEach>

  
  
 <h4> Add Comments </h4> 
  <form action="/jmns/newcomment" method="POST">
        <input type="hidden" name="_id", value="${post["_id"]}">
        ${errors}<br>
        <b>Name</b> (required)<br>
        <input type="text" name="commentName" size="60" value=""><br>
        <b>Email</b> (optional)<br>
        <input type="text" name="commentEmail" size="60" value=""><br>
        <b>Comment</b><br>
        <textarea name="commentBody" cols="60" rows="10"></textarea><br>
        <input type="submit" value="Submit">
    </form>
      
<%@include file='/designs/page/footer.jsp'%>
</body>
</html>