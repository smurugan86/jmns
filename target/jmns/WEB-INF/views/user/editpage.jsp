<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<c:url value="/designs/css/murugan.css" var="cssURL"/>
<link  rel="stylesheet" href= "${cssURL}">
<!-- <link rel="stylesheet" type="text/css" href="/resources/css/murugan.css" /> -->
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit User</title>
</head>
<body>
   
    <c:url value="active" var="addUser"/>
 <%@include file='/designs/page/header.jsp'%>
 


  
    <h2>Signup</h2>
    
    <%@include file='/designs/page/submenu.jsp'%>
    
    <form method="post" action="/jmns/updateUser">
      <table border=0 style="width: 100%;">
      		<input type="hidden" name="_id" value="${_id}">
        <tr>
          <td class="label">
            First Name
          </td>
          <td>
            <input type="text" name="firstName" value="${firstName}" style="width: 400px;" maxlength="50">
          </td>
          <td class="error">
	   			${efirstName}            
          </td>
        </tr>

		<tr>
          <td class="label">
            Last Name
          </td>
          <td>
            <input type="text" name="lastName" value="${lastName}" style="width: 400px;" maxlength="50">
          </td>
          <td class="error">
	   		${elastName}            
          </td>
        </tr>
        
        <tr>
          <td class="label">
           Role
          </td>
          <td>
           <select size="1" name="userRole" selected=${userRole}>          		
        		<option value ="USER" ${"USER" == userRole ? 'selected="selected"' : ''} >User</option>
        		<option value ="ADMIN" ${"ADMIN" == userRole ? 'selected="selected"' : ''} >Admin</option>        
		  </select>
          </td>
          <td class="error">  
           ${euserRole}            
          </td>
        </tr>
        
        <tr>
          <td class="label">
           Status
          </td>
          <td>
           <select size="1" name="status" selected=${status}>          		
        		<option value ="ACT" ${"ACT" == status ? 'selected="selected"' : ''} >ACT</option>
        		<option value ="INACT" ${"INACT" == status ? 'selected="selected"' : ''} >INACT</option>        
		  </select>
          </td>
          <td class="error">
            ${estatus}            
          </td>
        </tr>
        
      <tr>
          <td class="label">
            Email
          </td>
          <td>
          <input type="hidden" name="email" value="${email}">
           ${email}
          </td>
          <td class="error">
          
	         ${eemail}
          </td>
        </tr>
     <tr>
          <td class="label">
            
          </td>
          <td>
	    
             <button class="btn">Update</button>
          </td>
        </tr>
        
      </table>

    
    </form>
    
    <%@include file='/designs/page/footer.jsp'%>
    
</body>
</html>