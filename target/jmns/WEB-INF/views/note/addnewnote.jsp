<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<c:url value="/designs/css/murugan.css" var="cssURL"/>
<link  rel="stylesheet" href= "${cssURL}">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add New Notes</title>
</head>
<body>
   
    <c:url value="active" var="addNote"/>
  <%@include file='/designs/page/header.jsp'%>
  
   
    <h2>Notes</h2>
    
    <%@include file='/designs/page/submenu.jsp'%>
    
    <form method="post" action="/jmns/saveNote" id="taskform">
      <table border=0 style="width: 100%;"> 
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
            <textarea tabindex="103" rows="4" maxlength="32000" form="taskform" name="description">${description}  </textarea>
          </td>
          <td class="error">
	         ${edescription}
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