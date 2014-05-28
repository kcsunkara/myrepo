<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %> 
  
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META http-equiv="Content-Type" content="text/html;charset=UTF-8">



<link href="../replicauidashboard/css/search.css" rel="Stylesheet" type="text/css">
<script type="text/javascript"></script>
<script src="js/jquery-1.9.1.js"></script>
<script src="js/jquery-ui.js"></script>
 <link rel="stylesheet" href="../replicauidashboard/css/customerDetails.css">
 <link rel="stylesheet" href="../replicauidashboard/css/jquery-ui.css">
 <!-- <script type="text/javascript" src="js/jquery.pajinate.js"></script> -->
 <style type="text/css">
 .displayText{
 display: none;}
 </style>
 
<script type="text/javascript">
$(function () 
        {   
	
	
                        $('#startDate, #endDate').datepicker({
                                        beforeShow: validateRage,
                                        dateFormat: "yy/mm/dd",
                                        firstDay: 1, 
                                        changeFirstDay: false
                        });

        });

        function validateRage(input) { 
        	var dateProp= '<spring:message code="dateRange"/>';
        	var dateRange=parseInt(dateProp);
        	
                        if (input.id === "startDate") {
                                        dateMin = -dateRange;
                                        if ($("#endDate").datepicker("getDate") != null) {
                                                        dateMax = $("#endDate").datepicker("getDate");
                                                        dateMin = -1 * Math.round(((new Date) - dateMax)/ 1000 / 60 / 60 / 24) - dateRange;
                                        }
                                        else {
                                                        dateMax = new Date; 
                                        }                      
                        }
                        else if (input.id === "endDate") {
                                        dateMax = new Date; 
                                        if ($("#startDate").datepicker("getDate") != null) {
                                                        dateMin = $("#startDate").datepicker("getDate");
                                        }
                                        else {
                                                        dateMin = - dateRange; 
                                        } 
                        }
                        return {
                                        minDate: dateMin, 
                                        maxDate: dateMax
                        };     
        }

	
	$(document).ready(function(){
		
		
		 $('#submit').attr('disabled', true); 
		 $("#startDate").change(function(){
			 if($("customerName").val()!="noSelection" && $("[name='endDate']").val()!="")
				    $('#submit').attr('disabled', false); 
			  });
		$("#endDate").change(function(){
			if($("customerName").val()!="noSelection" && $("[name='startDate']").val()!="")
				 $('#submit').attr('disabled', false); 
			 });
		$("#customerName").change(function(){
			 if($("[name='startDate']").val()!="" && $("[name='endDate']").val()!="" )
				 {
				if($("#customerName").val()!='noSelection'){
				    $('#submit').attr('disabled', false);
				}else{
					 $('#submit').attr('disabled', true);
				}
				 }
			  });
		
		$("#customerTable input[type='button']").on('click',function(event) {
			 
			event.preventDefault();
			 		 
			var custId=$('#customerName').val();
			var startDate=$("[name='startDate']").val();
			var endDate=$("[name='endDate']").val();
			 $('#queueTable2 tbody tr').remove();
			 $('#totalCount tbody tr').remove();
			  
			 startDate+=" 00:00";
			endDate+=" 00:00";
	if(custId != 'noSelection' && startDate!="" && endDate !="" )
	{
		 $("a").removeClass("export");
		 $('#submit').attr('disabled', 'disabled'); 
				  
		 $("#txtLoad").css('display', 'block');
		 $("#noRecords").css('display', 'none');
            
		$.post('getDetailedUsageReport.html', {"custId":custId,"startDate":startDate,"endDate":endDate}, function(response) {
			 var html ="";
			 var cell ="";
			 var html1="";
			 var cell1="";

			 $('#queueTable > tbody > tr').remove();
          	html1='<tr>';
          	html1+='<td width="15%">Day</td>';
          	$.each(response[0], function (counter, nodeName) {
          		cell1='<td width="15%">'+ nodeName +'</td>';
          		html1+=cell1;
          	});
          	html1+='</tr>';
          	$(html1).appendTo('#queueTable');
          	
          	
			if(response[1] !=null)
			{ 
			 $.each(response[1], function (currDate, daywiseMap) {
                 html = '<tr class="pagecontent"><td width="15%">'+currDate+'</td>';
				  $.each(daywiseMap, function (name, size) {
					  
 	          		  	 cell = '<td width="15%">' + size+'</td>';
                        html +=cell;
                     });
				  $("#txtLoad").css('display', 'none');
                     html += "</tr>";
		
			 $(html).appendTo('#queueTable2');
			 
			
	 		 }); 
			 var html ="";
			 $('#totalCount > tbody > tr').remove();
			 html += '<tr class="pagecontent"><td width="15%" style="background-color: black; font-size:15px; !important;color: yellow;">Total Cost</td>';
			 $.each(response[2], function (locationName1, totalCost) { 
			 html += '<td width="15%" style="background-color: black; font-size:15px; !important;color: yellow;"> $ '+totalCost+'</td>';
			 });
			 html += "</tr>";
			 $(html).appendTo('#totalCount');  
			 var html ="";
		}else{
			 $("#txtLoad").css('display', 'none');
			 $("#noRecords").css('display', 'block');
			
		}
			
	});
	}else if(custId != 'noSelection' && (startDate==="" || endDate==="")){
		alert('Please choose the dates');
	 }else if(custId === 'noSelection' && (startDate==="" || endDate===""))
	 {
		alert('Please choose the customer name from drop down ');
	 }
	 
	 $('#exportToCSV a').attr('href','./downloadCSV.html?custId='+custId+'&startDate='+startDate+'&endDate='+endDate);
 });
		
		
		
	});
		
		
	
  </script>

</head>
<body>
<div style="font-size:16px; !important">
  <%@ include file="header.jsp"%>
  </div>
       
              <div id="content">
              </div>

              <div>
                    <h3 style="margin-left:120px; font-size:20px">Detailed Usage Report</h3>
              </div>
              		 <div id="customerTable">
                           <table>
                                  <tr ><td style="font-size:15px; !important">Customer:</td>
                                         <td width="80%">
                                         <select name="custId" id="customerName">
                                         	<option value="noSelection">Select Customer </option>
                                         	<c:forEach items="${customerDetails}" var="customerDetails">
                                         		<option value="${customerDetails.id}" id="${customerDetails.id}" <c:if test="${customerDetails.name eq param.custName}">selected</c:if>>${customerDetails.name}</option>
                                         	</c:forEach>
                                         </select>
                                         </td>
                                  </tr>

                           </table>
                           
                     	<table id="showSubmit">
                     	<tr> 
                     		<td><span style="font-size:15px; !important">Start Date:</span> <input type="text" readonly="true" name="startDate" id="startDate">&nbsp;</td>
                     	 	<td><span style="font-size:15px; !important">End Date: </span> <input type="text" readonly="true" name="endDate" id="endDate">
                     	 	<input type=button  value="Submit" id="submit" ></input></td>
                     	 </tr>
                      </table>
                      <div style=" margin-left:1100px;margin-top:-60px ; disable:true" id="exportToCSV">
                      		<span style="font-size:15px; margin-bottom:1000px">EXPORT TO CSV</span>
                      		<span>
                      			<a href="" class="export" ><img src="/replicauidashboard/image/images.jpg" alt="CSV_Download" width="42" height="42" style="background-size:30px 30px;background-repeat:no-repeat;padding-top:10px;background-repeat:no-repeat; border:none;"></a>
                      		</span>
                      </div>
                     </div>   
                    
                     <div id="dvData"> 
                     <div id="summary">
                     
                     <table id="queueTable" style="margin-top:30px;"  >
                     
                     </table>  
                     </div >
                       <div style="display:none" id="txtLoad">
                     	<span style="text-align:center; font-size:30px; margin-left:480px;margin-top:10px"><img width="30%" height="30%"src="/replicauidashboard/image/loading_22.gif" /></span>
                     </div>
                     <div style="display:none" id="noRecords">
                     	<span style="text-align:center; margin-top:10px;font-size:15px;"> No Records Found</span>
                     </div>
                     
    
                     <div id="paging_container8" class="container">
                     
                      <table id="queueTable2" >
                     </table>
                     
                     <div class="page_navigation"></div>
                     </div>
                     <table id="totalCount" width="100%">
                    
                     </table> 
                     </div>
                           
</body>
</html>