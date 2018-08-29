<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<c:url value="/designs/css/murugan.css" var="cssURL"/>
<link  rel="stylesheet" href= "${cssURL}">
<!-- <link rel="stylesheet" type="text/css" href="/resources/css/murugan.css" /> -->
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Signup</title>
</head>
<body>

<%
//Clean Cookie
		Cookie loginCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("email") || cookie.getName().equals("name") || 
                		cookie.getName().equals("_id")) {
                    loginCookie = cookie;
                    break;
                }               
            }
        }
        if (loginCookie != null) {
            loginCookie.setMaxAge(0);
            response.addCookie(loginCookie);
        }
 %>
 
<div id="header">
		  <table style="width: 100%; background: #5db2ff; color:#ffffff" border=0>
   		<tr>
   			<td>
   			<h2>MS Task App</h2>
   			<!-- <img src="/jmns/designs/images/MS_Logo.jpg" width="300px" height="40px"/> --> 
   			</td>
   			<td>
   			 
   			<h3></h3> 
    		
    		</td>
    		<td></td>
   			<td>
   			
   			
   			</td>
   		</tr>
   </table>
</div>

   
<div id="menutwo">      
   <ul>
	   <li><a href='/jmns'>LOGIN</a></li>	   	   	  
	   <li class="active"><a href='/jmns/addUser'>SIGNUP</a></li>	   
	</ul>   
   </div>
   
    <h2>New User Signup</h2>
    <form method="post" action="/jmns/saveUser">
      <table>
        <tr>
          <td class="label">
            First Name
          </td>
          <td>
            <input type="text" name="firstName" value="" style="width: 400px;" maxlength="50">
          </td>
          <td class="error">
	   
            
          </td>
        </tr>

		<tr>
          <td class="label">
            Last Name
          </td>
          <td>
            <input type="text" name="lastName" value="" style="width: 400px;" maxlength="50">
          </td>
          <td class="error">
	   
            
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
	         
          </td>
        </tr>
        
        <tr>
          <td class="label">
            Password
          </td>
          <td>
            <input type="password" name="password" value="" style="width: 400px;" maxlength="50">
          </td>
          <td class="error">
	   
            
          </td>
        </tr>

        <tr>
          <td class="label">
            Verify Password
          </td>
          <td>
            <input type="password" name="verify" value="" style="width: 400px;" maxlength="50">
          </td>
          <td class="error">
	    
            
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