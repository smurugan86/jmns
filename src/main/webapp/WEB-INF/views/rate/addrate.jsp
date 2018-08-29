<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<c:url value="/designs/css/murugan.css" var="cssURL"/>
<link  rel="stylesheet" href= "${cssURL}">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Rate</title>
 
</head>
<body>

   <c:url value="active" var="addRate"/> 
  <%@include file='/designs/page/header.jsp'%>
   
 
   
    <h2>Rate</h2>
    
    <%@include file='/designs/page/submenu.jsp'%>
    
    <form method="post" action="/jmns/saveRate" id="taskform">
      <table border=0 style="width: 100%;">
        <tr>
          <td class="label">
            productName
          </td>
          <td>
           <input type="hidden" name="userId" value="${userId}">
           
           <input type="hidden" name="createdBy" value="${userName}">
           <input type="hidden" name="updatedBy" value="${userName}">
           
            <input type="text" name="productName" value="${productName}">
          </td>
          <td class="error">
	   ${eproductName}
            
          </td>
        </tr>

		<tr>
          <td class="label">
           quantity
          </td>
          <td>
           <input type="text" name="quantity" value="${quantity}">
          </td>
          <td class="error">
	   ${equantity}
            
          </td>
        </tr>
        
       
       

 		<tr>
          <td class="label">
            Amount
          </td>
          <td>
            <input type="text" name="amount" value="${amount}">
          </td>
          <td class="error">
	         ${eamount}
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
    </body>
    
    <%@include file='/designs/page/footer.jsp'%>
</html>