<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<c:url value="/designs/css/murugan.css" var="cssURL"/>
<link  rel="stylesheet" href= "${cssURL}">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Rate</title>
</head>
<body>

<c:url value="active" var="addRate"/>  
<%@include file='/designs/page/header.jsp'%>
   
  
    <h2>Edit Account</h2>
    
    <%@include file='/designs/page/submenu.jsp'%>
    
    <form method="post" id="taskform" action="/jmns/updateRate"> 
      <table border=0 style="width: 100%;">
      		<input type="hidden" name="_id" value="${_id}">
      		<input type="hidden" name="userId" value="${userId}">
      		<input type="hidden" name="updatedBy" value="${userName}">
        <tr>
          <td class="label">
            productName
          </td>
          <td>
            <input type="text" name="productName" value="${productName}">
            
             <select size="1" name="categoryName"  id="selectBox" > 
            	<option value =""> Select </option>
	            <c:if test="${not empty lists}">
	            	<c:forEach var="listValue" items="${lists}">
	            		<option value ="${listValue["name"]}"  ${listValue["name"] == categoryName ? 'selected="selected"' : ''} > ${listValue["name"]} </option>
	            	</c:forEach>
	            </c:if>                    		     
			</select>
			
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
	    	<a href="/jmns/deleterate/${_id}" class="btn">Delete</a>
          </td>
        </tr>
      </table>

     
    </form>
    
    
<%@include file='/designs/page/footer.jsp'%>
</body>
</html>