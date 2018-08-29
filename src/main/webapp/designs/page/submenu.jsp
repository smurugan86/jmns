<%@page import="org.eclipse.jetty.util.StringUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>

<head>
<c:url value="/designs/css/murugan.css" var="cssURL"/>
<link  rel="stylesheet" href= "${cssURL}">
</head>


<body>

 
 <div id="cssmenu">      
  <ul>
	 <c:if test="${myTask != null || addTask != null}">
		<li class="${myTask}"><a href='/jmns/tasklist'>My Task</a></li>	   
	   <li class="${addTask}"><a href='/jmns/addTask'>Add Task</a></li>		
	 </c:if>
	
	 <c:if test="${myNote != null || addNote != null}">
		<li class="${myNote}"><a href='/jmns/notelist'>My Notes</a></li>	   
	    <li class="${addNote}"><a href='/jmns/addNote'>Add Note</a></li>	
	 </c:if>
	 
	  <c:if test="${allUser != null || addUser != null}">
		<li class="${allUser}"><a href='/jmns/userlist'>All Users</a></li>	   	   
	    <li class="${addUser}"><a href='/jmns/addUser'>Add Users</a></li>
	 </c:if>
	 
	  <c:if test="${myAC != null || addAC != null}">
		<li class="${myAC}"><a href='/jmns/accountlist'>Accounts</a></li>
	    <li class="${addAC}"><a href='/jmns/addAccount'>Add Account</a></li>	
	 </c:if>
	 
	 <c:if test="${allPost != null || addPost != null}">	  	
	  	<li class="${allPost}"><a href='/jmns/postlist'>Post</a></li>	
	   <li class="${addPost}"><a href='/jmns/addpost'>Add Post</a></li>	
	 </c:if>
	  
	  
	  <c:if test="${allCat != null || addCat != null}">
	   <li class="${allCat}"><a href='/jmns/categorylist'>Categories</a></li>	
	   <li class="${addCat}"><a href='/jmns/addcategory'>Add Category</a></li>	
	 </c:if>
	 
	  <c:if test="${allRate != null || addRate != null}">
		<li class="${allRate}"><a href='/jmns/ratelist'>Rates</a></li>
	    <li class="${addRate}"><a href='/jmns/addRate'>Add Rate</a></li>	
	 </c:if>
	 
	 
 </ul>  
</div>
   
</body>
</html>