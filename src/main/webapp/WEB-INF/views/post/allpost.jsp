<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<c:url value="/designs/css/murugan.css" var="cssURL"/>
<link  rel="stylesheet" href= "${cssURL}">
 <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
 
<!-- <link rel="stylesheet" type="text/css" href="/resources/css/murugan.css" /> -->
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Share Blogs</title>
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

 <c:url value="active" var="all2Post"/>
<%@include file='/designs/page/header.jsp'%>

<div id="wrapper">
   <div id="content">   
   <div id="content-main">
   <h2>All Blogs  ${range}</h2>
  
  <%@include file='/designs/page/submenu.jsp'%>
  
 <br>
 <table border="0" style="width: 100%">
 
  <form method="get" action="/jmns/allPost" id="postSearchform"> 
  
 <!--   <tr style="padding: 10px 14px 10px;">
   <td><b>Blog Search :</b></td>
   <td></td>
   <td></td>
   </tr> -->
   
   <tr  class="tabHead" style="padding: 10px 14px 10px;">   
   	<td>Title</td>
    <td>Blog Msg</td>
    <td>Author</td>
    <td> </td>  
   </tr>
    
    <tr class="tabHead" style="padding: 10px 14px 10px;">
     <td><input type="text" name="titleStr" value="${titleStr}" style="width: 200px;" maxlength="100" ></td> 
    <td><input type="text" name="msgStr" value="${msgStr}" style="width: 200px;" maxlength="100" ></td>
    <td><input type="text" name="author" value="${author}" style="width: 200px;" maxlength="100" ></td>
    <td><input type="submit" class="btn" name="Search" value="Search" form="postSearchform"></td>
    </tr>
  </form>
  
  </table>



  
  <c:if test="${not empty lists}">
  			
	<c:forEach var="post" items="${lists}">
  
  		<h2><a href="/jmns/post/${post["_id"]}">${post["title"]}</a></h2>
   	 		<h4>Posted ${post["date"]} <i>By ${post["author"]}</i></h4>
   	 		<br>  		     
		    <p>${post["post"]}</p>
		    
		    <h5>Comments :</h5>		    	   
		   <c:if test="${post['comments'] != null}">
			   <a href="/jmns/post/${post["_id"]}"> ${post['comments'].size()} </a>
		  </c:if>
		   <c:if test="${post['comments'] == null}">
			   <a href="/jmns/post/${post["_id"]}"> 0 </a>			   
		    </c:if>
		    
		    <c:set var="com" value="${post['comments']}"/>
			

		     <h5>Post Under :</h5>		     
		     <c:forEach var="tag" items="${post['tags']}">
				 <a href="/jmns/postlistbytag/${tag}"> ${tag}</a>
			</c:forEach>

		   
          <hr>
        
  	</c:forEach>
  </c:if>
  
  
  
  
  

		</div>
		<div id="content-right"></div>
	</div>
	


<%@include file='/designs/page/footer.jsp'%>

	<div id="bottom"></div>
</div>

</body>
</html>