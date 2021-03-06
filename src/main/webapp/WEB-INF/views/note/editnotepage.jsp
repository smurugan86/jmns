<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<c:url value="/designs/css/murugan.css" var="cssURL"/>
<link  rel="stylesheet" href= "${cssURL}">
<!-- <link rel="stylesheet" type="text/css" href="/resources/css/murugan.css" /> -->
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Notes</title>
</head>
<body>
  
<c:url value="active" var="addNote"/> 
<%@include file='/designs/page/header.jsp'%>
     
    <h2>Edit Note</h2>
    <%@include file='/designs/page/submenu.jsp'%>
    
    <form method="post" id="taskform" action="/jmns/updateNote"> 
      <table border=0 style="width: 100%;">
      		<input type="hidden" name="_id" value="${_id}">
        <tr>
          <td class="label">
            Date
          </td>
          <td>
            <input type="text" name="date" value="${date}" class="datepicker">
          </td>
          <td class="error">
	   ${edate}
            
          </td>
        </tr>

		<tr>
          <td class="label">
           Title
          </td>
          <td>
            <input type="text" name="title" value="${title}" style="width: 300px;" maxlength="100">
          </td>
          <td class="error">
	         ${etitle}     
          </td>
        </tr>
             
        <tr>
          <td class="label">
            Description
          </td>
          <td>         
            <textarea tabindex="103" rows="4" maxlength="32000" form="taskform" name="description"> ${description}  </textarea>       
          </td>
          <td class="error">
	         ${edescription}
          </td>
        </tr>
         <tr>
          <td class="label">
            
          </td>
          <td>	    
            <button class="btn">Update</button><p>	    	
          </td>
        </tr>
        
         <tr>
          <td class="label">
            
          </td>
          <td>	    
            <a href="/jmns/deletenote/${_id}" class="btn">Delete</a>	    	
          </td>
        </tr>
        
      </table>

     
    </form>
    
    
<%@include file='/designs/page/footer.jsp'%>
</body>
</html>