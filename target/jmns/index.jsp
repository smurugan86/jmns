<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link rel="shortcut icon" href="/jmns/designs/images/ms.jpg">
<c:url value="/designs/css/murugan.css" var="cssURL"/>
<link  rel="stylesheet" href= "${cssURL}">
<!-- <link rel="stylesheet" type="text/css" href="/resources/css/murugan.css" /> -->
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>


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
   			<h2>Blogs & Day to Day Activities</h2>
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
	   <li class="active"><a href='/jmns'>LOGIN</a></li>	   
	   <li><a href='/jmns/signup'>SIGNUP</a></li>	  
	</ul>   
   </div>
   
   <h2>Existing Users Login</h2>
   
 <form method="post" action="/jmns/login">
      <table border="0">
        <tr style="padding: 10px 14px 10px;">
          <td class="label" style="padding: 10px 14px 10px;">
            Username
          </td>
          <td>
            <input type="text" name="username" value="${username}" class="box" style="width: 400px;">
          </td>
          <td class="error">
          </td>
        </tr>
<tr>
</tr> 
        <tr style="padding: 10px 14px 10px;">
          <td class="label" style="padding: 10px 14px 10px;">
            Password
          </td>
          <td>
            <input type="password" name="password" value="" class="box" style="width: 400px;">
          </td>
          <td class="error">	    	
          </td>
        </tr>
        
        <tr>
        <td ></td>
        <td class="error" colspan="2">
	    		${login_error}
                               
          </td>
        </tr>
        
         <tr>
        <td>         
        </td>
        <td style="padding: 10px 14px 10px;">
         <!--  <input type="submit" name="Login" > -->
          <button class="btn">Login</button>
        </td>
        </tr>
        
    
        
        

      </table>     
    </form>
    
         
   <%@include file='/designs/page/footer.jsp'%>

    </html>
    