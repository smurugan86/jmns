<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<c:url value="/designs/css/murugan.css" var="cssURL"/>
<link  rel="stylesheet" href= "${cssURL}">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add New Category</title>
</head>
<body>
   
   <c:url value="active" var="addCat"/>
  <%@include file='/designs/page/header.jsp'%>
  
 
    <h2>Category</h2>
    
    <%@include file='/designs/page/submenu.jsp'%>
    
    <form method="post" action="/jmns/savecategory" id="taskform">
      <table border=0 style="width: 100%;">
        <tr>
          <td class="label">
            Category Name
          </td>
          <td>            
            <input type="text" name="categoryName" value="${categoryName}" style="width: 300px;" maxlength="50">
          </td>
          <td class="error">	   
           ${ecategoryName}
          </td>
        </tr>

		<tr>
          <td class="label">
            Category Type
          </td>
          <td>
          <!--   <input type="text" name="categoryType" value=""> -->
             <select size="1" name="categoryType">
             	<option value ="">Select</option>
        		<option value ="Credit">Credit</option>
        		<option value ="Debit">Debit</option>        
			</select>
          </td>
          <td class="error">
	    		${ecategoryType}           
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