<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Job Queue Manager</title>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<link href="../replicauidashboard/css/search.css" rel="Stylesheet" type="text/css">
<link type="text/css" rel="stylesheet" href="css/styles.css" />
<script type="text/javascript" src="js/jquery.pajinate.js"></script>
<script>
$(document).ready(function(){
	
	//iterating all hyperlinks if count is 0 then remove hyperlink
	$("#summaryDetailsQueueManager a").each(function() {
		if($(this).text() == 0)
		$(this).addClass("removeLink");
		else
		$(this).removeClass("removeLink");

	});//end of iterating all hyperlinks

	$("#jobDetailesSummary").text("Job Details :").css('font-weight', 'bold').show();
	$("#assetDetailesSummary").hide();
	$('li:odd, .pagecontent > *:odd').css('background-color','none');
	
	$("#jobDate").val($("#jobDate option:first").val());
	
    $('#OK').click(function(){
		var tr ='';
		
		  $('#invalidAssetsTable').hide();  
		$('#queueTable').show(); 
		$('#jobDetailesSummary').show();
		$('#assetDetailesSummary').hide();
		 $('#queueTable2 tbody tr').remove();
		  $('.page_navigation').hide();
		  

		$("#dayType").text($("#jobDate option:selected").text());

       $.post('jobsCountByDay.html', {"jobDate":  $('#jobDate').val()}, function(response) {

		
			$("#md5_error").html(response.md5ErrorCount);
			$("#md5_completed").html(response.md5CompletedCount);
			$("#md5_new").html(response.md5RunningCount);
			$("#md5_total").html(response.md5Total);
			

			$("#copytier1_new").html(response.copyTier1RunningCount);
			$("#copytier1_completed").html(response.copyTier1CompletedCount);
			$("#copytier1_error").html(response.copyTier1ErrorCount);
			$("#copytier1_total").html(response.copytier1Total);
			
			$('#total_running').html(response.totalRunning);
			$('#total_failed').html(response.totalFailed);
			$('#total_completed').html(response.totalCompleted);
			
			$("#final_total").html(response.finalTotal);
			
			//iterating all hyperlinks if count is 0 then remove hyperlink
			$("#summaryDetailsQueueManager a").each(function() {
				if($(this).text() == 0)
				$(this).addClass("removeLink");
				else
				$(this).removeClass("removeLink");

			});//end of iterating all hyperlinks
				
		
	   });//end of post loop
            
    }); // end of OK loop


$("#summaryDetailsQueueManager a").click(function(event) {

  event.preventDefault();
$('.page_navigation').hide();
  $('#invalidAssetsTable').hide();
 
  $('#queueTable').show(); 
  $('#queueTable2 tbody tr').remove();
  

		var str =$(this).attr('id');
		var ret = str.split("_");
		var jobtype = ret[0];
		var jobStatus = ret[1];

		//console.log("jobtype:"+jobtype+"   jobStatus:"+jobStatus+" day:"+$('#jobDate').val()) 
	
 $.post('getJobsByType.html', {"jobType": jobtype,"day": $('#jobDate').val(),
		   "jobStatus": jobStatus}, function(response) {

			   $.each(response,function(i,item) {
  			 
  			    tr= $('<tr class="pagecontent"><td width="5%" id=jobId>'+response[i].jobId+'</td><td width="10%"><div id=jobType1>'+response[i].job_type+'</div></td><td width="12%" style="word-wrap: break-word;">'+response[i].fileName+'</td>'+
                     '<td width="12%" style="word-wrap: break-word;">'+response[i].storageName+'</td><td width="12%" style="word-wrap: break-word;">'+response[i].requestHostName+'</td><td width="12%">'+response[i].start_date+'</td>'+
                     '<td width="12%" id=end_date'+response[i].jobId+'>'+response[i].end_date+'</td><td width="8%"><a href="/replicauidashboard/assetDetails.html?assetId='+response[i].assetId+'&policyId='+response[i].policyId+'&customerName='+response[i].name+'">'+response[i].assetId+'</a></td>'+
                     '<td width="8%" id=error_message'+response[i].jobId+'>'+response[i].code+'</td>'+
                     '<td width="10%" id=status'+response[i].jobId+'>'+response[i].job_status+'</td>'+
                     '<td width="1%"><input type=button value=Resubmit  id='+response[i].jobId+'></input></td></tr>').appendTo('#queueTable2');
  		   				
  			  if(response[i].job_status== 'NEW')
				 {	
					 $("#queueTable td").last().show();
					$("input[type=button]").prop("disabled", false);
				 }else{
					 
					 $("#queueTable td").last().hide();
					 $("#queueTable2 td").last().hide();
					 
				 } 
  		   });
  		   
		  		 if(response.length > 0)
				 {
					  $('.page_navigation').show();
					  
					  $('#paging_container8').pajinate({
							num_page_links_to_display : 15,
							items_per_page : 50
						});
				 }
				 else
				 {
					 $('.page_navigation').hide();
				 }
			
	   });//end of post loop
            
    });
    

$(document).delegate("#queueTable2 input[type='button']", "click", function() {
	var job_id =$(this).attr('id');
	//console.log("Job id:"+job_id) ;
	
		$.post('getJobDetails.html', {"jobId": job_id}, function(response) {
			//alert(response);
		 	 $('#status'+job_id).html(response.job_status);
		 	$('#end_date'+job_id).html(response.end_date);
		 	$('#error_message'+job_id).html(response.error_message);
			$('#'+job_id).attr('disabled', 'disabled'); 
			
			/* var str =$(this).attr('div');
			alert(str); */
			//var id="#summaryDetailsQueueManager a";
			if(($("#jobType1:first").html() == "MD5_LARGE") || ($("#jobType1:first").html() == "MD5_SMALL"))
			{
				var x=parseInt($('#md5_new_count').text());
				var y=parseInt($('#md5_error_count').text());
				var z=parseInt($('#total_running').text());
				var a=parseInt($('#total_failed').text());
				
				if(x>1){
					$('#md5_new_count a').text(x-1);
					$('#md5_error_count a').text(y+1);
					$('#total_running').text(z-1);
					$('#total_failed').text(a+1);
				}
				else{
					$('#md5_new_count').text(x-1).css("font-weight","Bold");
					$('#md5_error_count a').text(y+1);
					$('#total_running').text(z-1);
					$('#total_failed').text(a+1);
				}
			
			}else if(($("#jobType1:first").html() == "COPY_LARGE") || ($("#jobType1:first").html() == "COPY_SMALL"))
			{
				var x=parseInt($('#copytier1_new_count').text());
				var y=parseInt($('#copytier1_error_count').text());
				var z=parseInt($('#total_running').text());
				var a=parseInt($('#total_failed').text());
				if(x>1){
					$('#copytier1_new_count a').text(x-1);
					$('#copytier1_error_count a').text(y+1);
					$('#total_running ').text(z-1);
					$('#total_failed ').text(a+1);
				}
				else{
					$('#copytier1_new_count').text(x-1).css("font-weight","Bold");
					$('#copytier1_error_count a').text(y+1);
					$('#total_running').text(z-1);
					$('#total_failed').text(a+1);
				}	
			}
	 });
	  
});
 

 //added for invalid assets
 $("#invalidAssets").click(function(event) {
   event.preventDefault();
  
   
  $('.page_navigation').hide();
  $('#queueTable2 tbody tr').remove();
  $('#queueTable').hide();
  $('#invalidAssetsTable').show(); 
  $("#jobDetailesSummary").text("Job Details :").hide();
	$("#assetDetailesSummary").text("Invalid Asset Details :").css('font-weight', 'bold').show();
	var assetId=null;
 $.post('invalidAssets.html',function(response) {
			   
  		   $.each(response,function(i,item) {
  			  if(item.id!=assetId){ 
  				assetId=item.id;
  			    tr= $('<tr class="pagecontent"><td width="16%" id=jobId>'+
  			    		'<a href="/replicauidashboard/assetDetails.html?assetId='+item.id+'&policyId='+item.policyId+'&customerName='+item.customerName+'">'
  			    		+item.id+'</a>'+'</td><td width="16%">'+item.name+'</td>'+
  			    '<td width="18%" id=jobId>'+item.custName+'</td><td width="16%">'+item.policyId+'</td><td width="16%">'+item.filesize+'</td>'+
  			   
  			  ' <td width="16%">'+
  			 $.each(item.slNameList,function(j,item1) {
  				item1;
  			  })+
  			  '</td><tr>').appendTo('#queueTable2');
  			  }  
  		   });
  		   
		  		 if(response.length > 0)
				 {
					  $('.page_navigation').show();
					  
					  $('#paging_container8').pajinate({
							num_page_links_to_display : 5,
							items_per_page : 5
						});
				 }
				 else
				 {
					 $('.page_navigation').hide();
				 }
			
	   });//end of post loop
            
    });
 
 //end
     

});
</script>
</head>
<body>
<%@ include file="header.jsp" %>
                    
                      <div id="content">
                        <div class="bookmarked" id="logo">
                        </div>
                      </div>
                      <h2 id="QueueManager">
                      Queue Manager
                      </h2>
            
                      <div id="summary">
                        <h3>
                          &nbsp;Batch Jobs
                          <select id="jobDate">
                       <option style="font-family: verdana ; font-size: 12px" value="1">LAST DAY</option>
                       <option style="font-family: verdana ; font-size: 12px" value="7">WEEK</option>
                        <option style="font-family: verdana ; font-size: 12px" value="31">MONTH</option>
                       </select>
                       <button type="button" name="submit" id="OK"  style="font-family: verdana ; font-size: 12px">OK</button>
                       
                       <lable style="border-left: solid 1px #E3E1DC; padding-left: 10px; padding-right: 10px; font-family: serif; font-weight: bold; font-size:14px;">Invalid Assets #:</lable>
                       <a href="" style="font-size: 14px;  color:white;text-decoration:underline" id="invalidAssets"><b>${invalidAssetCount}</b></a>
                       
                       
                        </h3>
                        
                      </div>
                      <div id="tableDetailsQueueManager">
                       <div id="summaryDetailsQueueManager">
                       <table  cellpadding="3" cellspacing="0" width="100%"  >
                       <tr >
                       <td width="20%" align="center"  style="border-left: 1px solid black; border-top: 1px solid black ; border-bottom: 1px solid black ; background-color: #BDBDBD ; font-size: 12px" ><b>Job Status</b></td>
                      <td width="20%" align="center" style="border-left: 1px solid black; border-top: 1px solid black ; border-bottom: 1px solid black ; background-color: #BDBDBD; font-size: 12px"><b>Running</b></td>
                       <td width="60%" align="center" style="border-left: 1px solid black; border-top: 1px solid black ; border-bottom: 1px solid black; border-right:1px solid black  ; background-color: #BDBDBD; font-size: 12px"><div id="dayType"><b>LASTDAY</b></div>
                       <table width="100%">
                       <tr>
                       <td width="33%" align="center" style="color: red ; #A9BCF5; font-size: 12px" ><b>Failed</b></td>
                       <td width="33%" align="center" style="color: green; font-size: 12px "><b>Complete</b></td>
                       <td width="33%" align="center" style=" font-size: 12px" ><b>Total</b></td>
                       </tr>
                       </table>
                       
                       </tr>
                        <tr >
                       <td width="20%" align="center"  style="border-left: 1px solid black; border-right:1px solid black ;background-color: #BDBDBD; font-size: 12px"><b>MD5</b></td>
                       <td width="20%" align="center"  style=" border-right:1px solid black ; font-size: 12px "><div id="md5_new_count"><a id="md5_new"  href=""><b>${jobDetails.MD5RunningCount}</b></a></div></td>
                       <td width="60%"  style=" border-right:1px solid black ; border-bottom:1px solid black ;background-color: #CEE3F6; font-size: 12px " >
                       <table width="100%">
                       <tr>
                       <td  align="center" width="33%"  ><div id="md5_error_count"> <a id="md5_error" href="" style="color: red; font-size: 12px" ><b> ${jobDetails.MD5ErrorCount}</b></a></div> </td>
                       <td align="center" width="33%" ><a id="md5_completed" href="" style="color: green; font-size: 12px" ><b>${jobDetails.MD5CompletedCount}</b></a> </td>
                       <td align="center" width="33%"><div id="md5_total" style= "font-weight: bold; font-size: 12px ">${md5Total}</div></td>
                       </tr>
                       </table>   
                      
                       </td>
                        
                       </tr>
                        <tr >
                       <td width="20%" align="center"  style="border-left: 1px solid black; border-right:1px solid black ; background-color: #BDBDBD; font-size: 12px"><b>Tier 1 Copy</b></td>
                      <td width="20%" align="center"  style=" border-right:1px solid black ; font-size: 12px "><div id="copytier1_new_count"><a id="copytier1_new" href=""><b>${CopyTier1RunningCount}</b></a></div></td>
                       <td width="60%" align="center"  style=" border-right:1px solid black ;border-bottom:1px solid black ; background-color: #CEE3F6 ; font-size: 12px " >
                       <table width="100%">
                       <tr>
                       <td align="center"  width="33%" ><div id="copytier1_error_count"><a id="copytier1_error" href="" style="color: red; font-size: 12px" ><b>${CopyTier1ErrorCount}</b></a></div></td>
                       <td align="center"  width="33%"><a id="copytier1_completed" href="" style="color: green; font-size: 12px" ><b>${CopyTier1CompletedCount}</b></a></td>
                       <td align="center" width="33%"><div id="copytier1_total" style= "font-weight: bold; font-size: 12px ">${copytier1Total}</div></td>
                       </tr>
                       </table>
                       </td>
                       
                       </tr>
                      
                       </table>
                      </td>
                       </tr>
                       </table>
                       
                       </div>
                       </div>
                      <div style="width: 100%; height: 30px;background-color: rgb(231, 126, 126);" >
                      <table id="queueTable1" >
                      <tr >
                       <td width="20%" align="center"  >All</td>
                       <td width="20%" align="center"   ><div id="total_running">${totalRunning}</div></td>
                       <td width="20%" align="center" ><div id="total_failed">${totalFailed}</div></td>
                       <td width="20%" align="center"><div id="total_completed">${totalCompleted}</div></td>
                       <td width="20%" align="center"><div id="final_total">${finalTotal}</div></td>
                       </tr>
                      </table>
                      </div>
                      
                      <br>
                      <div id="jobDetailesSummary"></div>
                      <div id="assetDetailesSummary"></div>
                      <div id="summary" >
	                      <table id="queueTable" width="100%" >
	                      <tr>
	                      <td width="5%" >Job-ID</td>
	                      <td width="10%" >Type</td>
	                      <td width="12%" >File</td>
	                      <td width="12%" >Storage-name</td>
	                      <td width="12%" >Host Name</td>
	                      <td width="12%" >Start Date</td>
	                      <td width="12%" >End Date</td>
	                      <td width="8%" >Asset-ID</td>
	                      <td width="8%" >Error Message</td>
	                      <td width="10%" >Job Status</td>
	                      <td width="1%" >Action</td>
	                      </tr>
	                      </table>
					    <table id="invalidAssetsTable" width="100%" >
	                      <tr>
	                      <td width="16%" >Asset-ID</td>
	                      <td width="16%" >Name</td>
	                      <td width="18%" >Customer Name</td>
	                      <td width="16%" >PolicyId</td>
	                      <td width="16%" >File Size</td>
	                      <td width="18%" >Storage Name</td>
	                      </tr>
	                      </table>
	                   </div>

                      
		<div id="paging_container8" class="container">
			<form>
			<table id="queueTable2" >
			</table>
			</form>
		<div class="page_navigation"></div>
		
	</div>



</body>
</html>
