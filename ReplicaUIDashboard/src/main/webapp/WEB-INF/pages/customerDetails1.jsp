<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
       pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META http-equiv="Content-Type" content="text/html;charset=UTF-8">
<script type="text/javascript" src="../replicauidashboard/js/jquery-1.3.1.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

<script type="text/javascript" src="../replicauidashboard/js/jquery.tablesorter.js"></script> 
<link href="../replicauidashboard/css/search.css" rel="Stylesheet" type="text/css">
<script type="text/javascript">

$(document).ready(function(){
	
/* 	$("#customerTable input[type='button']").on('click',function(event) {
		alert('bollo'); */
		
	 $("#customerTable input[type='button']").on('click',function(event) {
	
	//$(document).delegate("#customerTable input[type='button']", "click", function() { 
		 event.preventDefault();
	
		//var custName = $('#customerName').val();
		 $('#customerTable2 tbody tr').remove();
		 var customerName="";
		 var email="";
		 
		$.post('displayCustomerDetails.html', {"custName": $('#customerName').val()}, function(response) {
			if(response.customer.name===null || response.customer.name==="")
			{
				customerName=" ";
				
			}else{customerName=response.customer.name;}
			
			if(response.customer.email===null || response.customer.email==="")
			{
				email=" ";
			}else{email=response.customer.email;}
		
			tr= $('<tr><td ><h3>Total Assets#: </h3></td><td>'+response.totalAssets+'</td><td><h3>Total Size (GB):</h3></td> <td>'
					+(Math.round(response.totalSizeOfAssets)/(1024*1024*1024)).toFixed(2)+'</td></tr>' +
					'<tr><td style="background-color: rgba(76, 16, 153, 0.83);color: #F1F3EB;" height="1em"><h3>Customer Details: </h3></td><td style="background-color: rgba(76, 16, 153, 0.83);color: #F1F3EB; "></td>'+ 
					'<td style="background-color: rgba(76, 16, 153, 0.83);color: #F1F3EB;"></td><td style="background-color: rgba(76, 16, 153, 0.83);color: #F1F3EB;"></td><td style="background-color: rgba(76, 16, 153, 0.83);color: #F1F3EB;"></td><td style="background-color: rgba(76, 16, 153, 0.83);color: #F1F3EB;"></td><tr >'
					+'<td width="16%" style="color: #6E6E6E; background-color: #CEE3F6;">Customer Name:</td><td  width="16%" style="background-color: #CEE3F6;"><b>'+customerName+'</b></td>'
			 		+'<tr><td  width="16%" style="color: #6E6E6E;background-color: #CEE3F6;">Email:</td><td  width="16%" style="background-color: #CEE3F6;"><b>'+email+'</b></td>'+
			 		'</tr>'
			 		+'<tr><td> <h4>Policy Details: </h4></tr>').appendTo('#customerTable2');
			 		//tr= $('').appendTo('#customerTable2');
			
			$.each(response.customer.policyList,function(i,item) {
				//tr=$('<tr><td colspan="1">&nbsp;</td></tr>').appendTo('#customerTable2');
				tr=$('<tr><td style="color: #6E6E6E;"><b>Policy ID</b></td><td><b>'+item.id+'</b></td><td style="color: #6E6E6E;"><b>Policy Name</b></td><td><b>'+item.policy_name+'</b></td></tr><tr><td colspan="5">&nbsp;</td></tr>'+
				'').appendTo('#customerTable2');
						$.each(item.policySitesList,function(j,item1) {
						tr=$('<tr><td style="font-size:14px;color: #6E6E6E;height="10em""><b>Site Name:</b></td>'+
						'<td><b>'+item1.site.name+'</b></td>'+ 
						'<td style="font-size:14px;color: #6E6E6E;"><b>Site Copies:</b></td>'+
						'<td><b>'+item1.number_copies+'</b></td></tr><tr>').appendTo('#customerTable2');
						});
						tr=$('<tr><td colspan="5">&nbsp;</td></tr>').appendTo('#customerTable2');
				 tr=$('<tr><td  width="16%"   style="border-bottom: 1px solid; border-bottom-color: #424242;"></td><td  width="16%"   style="border-bottom: 1px solid; border-bottom-color: #424242;"></td>'+
				'<td  width="16%"   style="border-bottom: 1px solid; border-bottom-color: #424242;"></td><td  width="16%"   style="border-bottom: 1px solid; border-bottom-color: #424242;"></td>'+
				'<td  width="16%"   style="border-bottom: 1px solid; border-bottom-color: #424242;"></td><td  width="16%"   style="border-bottom: 1px solid; border-bottom-color: #424242;"></td>'+
				'</tr><td  width="16%" ></td> <tr></tr>').appendTo('#customerTable2');
			});
			
			  /* //$.each(response,function(i,item) {
				   $('#customerTable2 tbody tr').remove();
				 tr= $('<tr><td width="1%">Customer Name:</td><td width="1%">'+response.customer.name+'</td>'+
					'<td width="1%">Owner:</td><td width="1%">'+response.customer.owner_contact+'</td><td>Business Unit</td><td>'+response.customer.allocation_bu_id+'</td>'
				 ).appendTo('#customerTable2');
			// });  */
		});
		
	});
});
</script>

</head>
<script type="text/javascript"></script>

<body>
       <%@ include file="header.jsp"%>
       
              <div id="content">
              </div>

              <div>
                    <h2 id="assetColor">Customer usage Summary</h2>
              </div>
              <div >	
              			
                           <table id="customerTable" >
                                  <tr ><td>Customer:</td>
                                         <td width="50%">
                                         <select name="custName" id="customerName">
                                         	<option value="noSelection">Select Customer &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</option>
                                         	<c:forEach items="${customerDetails}" var="customerDetails">
                                         		<option value="${customerDetails.id}" id="${customerDetails.id}" <c:if test="${customerDetails.name eq param.custName}">selected</c:if>>${customerDetails.name}</option>
                                         	</c:forEach>
                                         </select>
                                         </td>
                                        <td><input type=button  value="Submit" ></input> </td> 
                                  </tr>

                           </table>
                           
                           
                  </div>
                  <div>
                  	<table id="customerTable2" width="100%"  cellspacing="0">
					</table>
                  </div>
              
       
</body>
</html>