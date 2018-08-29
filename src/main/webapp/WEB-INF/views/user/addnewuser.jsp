<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<c:url value="/designs/css/murugan.css" var="cssURL"/>
<link  rel="stylesheet" href= "${cssURL}">
<!-- <link rel="stylesheet" type="text/css" href="/resources/css/murugan.css" /> -->
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add New User</title>
</head>
<body>

<c:url value="active" var="addUser"/>
  <%@include file='/designs/page/header.jsp'%>
       
    <h2>Signup</h2>
    
    <%@include file='/designs/page/submenu.jsp'%>
    
    <form method="post" action="/jmns/saveUser">
      <table border=0 style="width: 100%;">
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
            <input type="text" name="lastName" value=" ${lastName}" style="width: 400px;" maxlength="50">
          </td>
          <td class="error">
	   
            ${elastName}
          </td>
        </tr>
        
      <tr>
          <td class="label">
            Email
          </td>
          <td>
            <input type="text" name="email" value="${email}" style="width: 400px;" maxlength="100">
          </td>
          <td class="error">
	         ${eemail}
          </td>
        </tr>
        
        <tr>
          <td class="label">
            Password
          </td>
          <td>
            <input type="password" name="password" value="${password}" style="width: 400px;" maxlength="50">
          </td>
          <td class="error">
	   ${epassword}
            
          </td>
        </tr>

        <tr>
          <td class="label">
            Verify Password
          </td>
          <td>
            <input type="password" name="verify" value="${verify}" style="width: 400px;" maxlength="50">
          </td>
          <td class="error">
	    ${everify}
            
          </td>
        </tr>
<tr>
          <td class="label">
            
          </td>
          <td>
	    
             <button class="btn">Save</button>
          </td>
        </tr>
        
      </table>

     
    </form>
  
<%@include file='/designs/page/footer.jsp'%>

</body>
</html>