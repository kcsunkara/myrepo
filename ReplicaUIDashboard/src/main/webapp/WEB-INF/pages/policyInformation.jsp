<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Asset Replica Dashboard</title>
<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script> -->
<script type="text/javascript" src="../replicauidashboard/js/jquery.tablesorter.pager.js"></script>
<script type="text/javascript" src="../replicauidashboard/js/jquery.tablesorter.js"></script> 
	
<link href="../replicauidashboard/css/search.css" rel="Stylesheet"
	type="text/css">
<link type="text/css" rel="stylesheet" href="css/styles.css" />
<script type="text/javascript" src="js/jquery.pajinate.js"></script>

<script src="js/jquery.1.9.1.min.js"></script>
<script src="js/vendor/jquery.ui.widget.js"></script>
<script src="js/jquery.iframe-transport.js"></script>
<script src="js/jquery.fileupload.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<!-- <link href="bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />-->
<link href="css/dropzone.css" type="text/css" rel="stylesheet" />
<script src="js/myuploadfunction.js"></script>


<script>

$(document).ready(function(){
	
	var pid = getParameterByName('pid');
	
	if(pid == null || pid == "") {
		$('#updatePolicyDiv').hide();
	}
	$('#successMsgDiv').hide();
	$('#errorMsgDiv').hide();
	
	$('#successMsgDiv2').hide();
	$('#errorMsgDiv2').hide();
	
});

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

function changePolicy(pid) {
	$('#policyDetails').text("Details of Policy ID - "+pid);
	$('#site_list tbody tr').remove();
	$.post('siteInfoByPID.html', {"pid":pid}, function(response) {
		$.each(response,function(i,item) {
			$('<tr>'+
				'<td>'+response[i].siteId+'</td>'+
				'<td>'+response[i].name+'</td>'+
				'<td>'+response[i].tier+'</td>'+
				'<td>'+response[i].isPrimary+'</td>'+
				'<td>'+response[i].requiredCopies+'</td>'+
				'</tr>').appendTo('#site_list');
		});
		$('input[id="updatePolicyButton"]').prop("disabled", false);
		$('#successMsgDiv').hide();
		$('#errorMsgDiv').hide();
	});
}

function updatePolicy() {
	$('input[id="updatePolicyButton"]').prop("disabled", true);
	var updatePid = $('input[name="p_selected"]:checked').val();
	var assetId = getParameterByName('assetId');
	$.post('updatePolicyForAsset.html', {"pid" : updatePid, "assetId" : assetId}, function(response) {
		if(response == 1) {
			$('#successMsgDiv').show();	
		}
		if(response != 1) {
			$('#errorMsgDiv').show();
		}
	});
}

/* function uploadAsset() {
	$('input[id="uploadAssetButton"]').prop("disabled", true);
	var selectedPid = $('input[name="p_selected"]:checked').val();
	
	alert("In uploadAsset... selectedPid = "+selectedPid);
	
	$.post('./controller/upload.html', {"pid" : selectedPid}, function(response) {
		if(response == 1) {
			$('#successMsgDiv2').show();	
		}
		if(response != 1) {
			$('#errorMsgDiv2').show();
		}
	});
} */
</script>
<script>
   var client = new XMLHttpRequest();
  
   function upload() 
   {
      var file = document.getElementById("fileupload");
     
      /* Create a FormData instance */
      var formData = new FormData();
      /* Add the file */ 
      formData.append("upload", file.files[0]);

      client.open("post", "./controller/upload.html", true);
      client.setRequestHeader("Content-Type", "multipart/form-data");
      client.send(formData);  /* Send to server */ 
   }
     
   /* Check the response status */  
   client.onreadystatechange = function() 
   {
      if (client.readyState == 4 && client.status == 200) 
      {
         alert(client.statusText);
      }
   }
</script>
</head>

<body>
<%@ include file="header.jsp"%>
<div id="content">
	<div class="bookmarked" id="logo"></div>
</div>
<c:if test="${empty policyId}">
	<h2 id="Policies Available">Policies Available</h2>
</c:if>
<c:if test="${not empty policyId}">
	<h2 id="Policies Available">View/Change Policy for Asset ID - ${assetId}</h2>
</c:if>
<div id="tableDetailsPolicyInformation">
	<div id="policyInformationDiv">
		<table width="100%" class="tablesorter" id="policy_list">
		<thead>
			<tr>
				<th><b>Policy ID</b></th>
				<th><b>Name</b></th>
				<th><b>Customer ID</b></th>
				<th><b>Active</b></th>
				<th><b>Encrypted</b></th>
				<th><b>M5 Check</b></th>
				<th><b>File System Path</b></th>
				<th><b>Selected</b></th>
			</tr>
		</thead>
		
		<c:forEach items="${policyList}" var="policy">
		<tbody>
			<tr>
				<td>${policy.id}</td>
				<td>${policy.policyName}</td>
				<td>${policy.customerId}</td>
				<td>${policy.active}</td>
				<td>${policy.encrypt}</td>
				<td>${policy.md5Check}</td>
				<td>${policy.fsPath}</td>
				<c:if test="${not empty policyId}">
				<td>
					<c:if test="${policyId == policy.id}">
						<input name="p_selected" type="radio" checked="checked" value="${policy.id}" onclick="changePolicy(${policy.id})"/>
					</c:if>
					<c:if test="${policyId != policy.id}">
						<input name="p_selected" type="radio" value="${policy.id}" onclick="changePolicy(${policy.id})"/>
					</c:if>
				</td>
				</c:if>
				<c:if test="${empty policyId}">
				<td>
					<c:if test="${policy.id == 122}">
						<input name="p_selected" type="radio" checked="checked" value="${policy.id}" onclick="changePolicy(${policy.id})"/>
					</c:if>
					<c:if test="${policy.id != 122}">
						<input name="p_selected" type="radio" value="${policy.id}" onclick="changePolicy(${policy.id})"/>
					</c:if>
				</td>
				</c:if>
			</tr>
		</tbody>
		</c:forEach>
		</table>
		<c:if test="${not empty policyId}">
			<h5 id="policyDetails">Details of Policy ID - ${policyId}</h5>
		</c:if>
		<c:if test="${empty policyId}">
			<h5 id="policyDetails">Details of Policy ID - 122</h5>
		</c:if>
		
		<table width="100%" class="tablesorter" id="site_list">
		<thead>
			<tr>
				<th><b>SITE ID</b></th>
				<th><b>SITE NAME</b></th>
				<th><b>TIER NAME</b></th>
				<th><b>IS PRIMARY SITE</b></th>
				<th><b>REQUITED COPIES</b></th>
			</tr>
		</thead>
		
		<c:forEach items="${policySiteInfo}" var="policySite">
		<tbody>
			<tr>
				<td>${policySite.siteId}</td>
				<td>${policySite.name}</td>
				<td>${policySite.tier}</td>
				<td>${policySite.isPrimary}</td>
				<td>${policySite.requiredCopies}</td>
			</tr>
		</tbody>
		</c:forEach>
		</table>
		<br/>
		<div id="updatePolicyDiv" align="center">Click here to change the Policy: <input id="updatePolicyButton" type="button" value="Change Policy" size="30" onclick="updatePolicy()"/><div id="successMsgDiv"><h6><i>Policy updated successfully...</i></h6></div>
			<div id="errorMsgDiv"><h6><i>Error occurred while updating the policy...</i></h6></div>		 
		</div>
		
		<div id="uploadAssetDiv" align="center">Click here to upload Asset into the selected Policy:
		<form id="assetUploadForm" action="" method="POST" enctype="multipart/form-data"> 
			<input id="fileupload" type="file" name="fileupload" />
			<input id="uploadAssetButton" type="button" value="Upload Selected Asset" size="30" onclick="upload()" />
			<div id="dropzone" class="fade well">Drop files here</div>
		</form>
			<div id="successMsgDiv2"><h6><i>Asset uploaded successfully...</i></h6></div>
		</div>
		
	</div>
</div>

</body>
</html>
