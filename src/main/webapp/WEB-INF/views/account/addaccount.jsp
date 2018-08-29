<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<c:url value="/designs/css/murugan.css" var="cssURL"/>
<link  rel="stylesheet" href= "${cssURL}">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My Account</title>


<script type="text/javascript">

   function changeFunc() {
    var selectBox = document.getElementById("selectBox");
    var selectedValue = selectBox.options[selectBox.selectedIndex].value;
    alert(selectedValue);
   }

   
  
  /*  $category = $('#selectBox');

   $category.change (
       function() {
    	   alert("hhh");
           $.ajax({
               type: "GET",
               url: "/jmns/accountTypes",
               data: {category: $category.attr("selectedIndex") },
               success: function(data){
                   $("#state").html(data)
               }
           });
       }
   ); */

   
   $("select#selectBox").change(function(){
	   alert("hhh");
       $.getJSON("/jmns/accountTypes",{countryCode: $(this).val()}, function(j){
            var options = '';
            for (var i = 0; i < j.length; i++) {
              options += '<option value="' + j[i].name + '">' + j[i].name + '</option>';
            }
            $("select#state").html(options);
          });
      });
   
   
   
   function Ajax(){
	   var xmlHttp;
	       try{    
	           xmlHttp=new XMLHttpRequest();// Firefox, Opera 8.0+, Safari
	       }catch (e){
	           try{
	               xmlHttp=new ActiveXObject("Msxml2.XMLHTTP"); // Internet Explorer
	           }catch (e){
	               try{
	                   xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
	               }catch (e){
	                   alert("No AJAX!?");
	                   return false;
	               }
	           }
	       }
	       xmlHttp.onreadystatechange=function(){
	           document.getElementById('ReloadList').innerHTML=xmlHttp.responseText;
	           setTimeout('Ajax()',10000);
	       }
	       xmlHttp.open("GET","reloadlist.html",true);
	       xmlHttp.send(null);
	   }
	   window.onload=function(){
	       setTimeout('Ajax()',10000);
	   }
	   
	   
	   
	   function loadXMLDoc()
	   {
		   alert("hhh");
	   var xmlhttp;
	   var keys=document.dummy.sele.value
	   //var urls="/jmns/accountTypes?ok="+keys
	   var urls="http://localhost:4040/jmns/accountTypes"
	   if (window.XMLHttpRequest)
	   {// code for IE7+, Firefox, Chrome, Opera, Safari
	   xmlhttp=new XMLHttpRequest();
	   }
	   else
	   {// code for IE6, IE5
	   xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	   }
	   xmlhttp.onreadystatechange=function()
	   {
	   if (xmlhttp.readyState==4)
	   {
	   var some=xmlhttp.responseXML.documentElement;
	   alert(some);
	   document.getElementById("a").innerHTML=some.getElementsByTagName("clists")[0].childNodes[0].nodeValue;
	   document.getElementById("b").innerHTML=some.getElementsByTagName("clists")[0].childNodes[0].nodeValue;
	   document.getElementById("c").innerHTML=some.getElementsByTagName("clists")[0].childNodes[0].nodeValue;
	   }
	   }
	   xmlhttp.open("GET",urls,true);
	   xmlhttp.send();
	   }
	   

  </script>
  
</head>
<body>

   <c:url value="active" var="addAC"/> 
  <%@include file='/designs/page/header.jsp'%>
   
 
   
    <h2>Task</h2>
    
    <%@include file='/designs/page/submenu.jsp'%>
    
    <form method="post" action="/jmns/saveAccount" id="taskform">
      <table border=0 style="width: 100%;">
        <tr>
          <td class="label">
            Date
          </td>
          <td>
           <input type="hidden" name="userId" value="${userId}">
           
           <input type="hidden" name="createdBy" value="${userName}">
           <input type="hidden" name="updatedBy" value="${userName}">
           
            <input type="text" name="date" value="${date}" class="datepicker">
          </td>
          <td class="error">
	   ${edate}
            
          </td>
        </tr>

		<tr>
          <td class="label">
            Account Type
          </td>
          <td>
          <select size="1" name="accountType" selected=${accountType}>  
          		<option value =""> Select </option>        		
        		<option value ="Credit" ${"Credit" == accountType ? 'selected="selected"' : ''} >Credit</option>
        		<option value ="Debit" ${"Debit" == accountType ? 'selected="selected"' : ''} >Debit</option>        
		  </select>
          </td>
          <td class="error">
	   ${eaccountType}
            
          </td>
        </tr>
        
       
       <tr>
          <td class="label">
            Category
          </td>
          <td>
          <!-- onchange="changeFunc();" -->
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
          ${ecategoryName}     
          </td>
        </tr>
         
      <tr>
          <td class="label">
            Description
          </td>
          <td>
            <!-- <input type="text" name="description" value=""> -->
            <textarea tabindex="103" rows="4" maxlength="32000" form="taskform" name="description">  ${description}</textarea>
          </td>
          <td class="error">
	         ${edescription}
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