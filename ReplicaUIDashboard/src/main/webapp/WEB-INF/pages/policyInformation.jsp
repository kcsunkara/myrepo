<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Asset Replica Dashboard</title>
<script type="text/javascript" src="js/jquery.1.9.1.min.js"></script>
<link href="../replicauidashboard/css/search.css" rel="Stylesheet" type="text/css">
<link type="text/css" rel="stylesheet" href="css/styles.css" />
<script>
$(document).ready(function(){
	
	$('#successMsgDiv').hide();
	$('#errorMsgDiv').hide();
	
	$('#uploadAssetDiv').hide();
	$('#successMsgDiv2').hide();
	$('#errorMsgDiv2').hide();
	$('input[id="uploadAssetButton"]').prop("disabled", true);
	var pid = getParameterByName('pid');
	
	if(pid == null || pid == "") {
		$('#updatePolicyDiv').hide();
		$('#uploadAssetDiv').show();
	}
	
	$("#fileupload_button").change(function() {
		$('input[id="uploadAssetButton"]').prop("disabled", false);
    });
	
	
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

/* function upload() {
	$('input[id="uploadAssetButton"]').prop("disabled", true);
	var selectedPid = $('input[name="p_selected"]:checked').val();
	
	var oMyForm = new FormData();
	oMyForm.append("file", fileupload_button.files[0]);
	
	alert("In upload... selectedPid = "+selectedPid);
	
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
   function upload(){
	   
	  $('input[id="uploadAssetButton"]').prop("disabled", true);
	  $('input[id="fileupload_button"]').prop("disabled", true);
	  $('input[name="p_selected"]').prop("disabled", true);
	  var selectedPid = $('input[name="p_selected"]:checked').val();
	  
	  var myFormData = [];
	  if(typeof FormData == "undefined") {
		  
		  myFormData.push("file", $('#fileupload_button').files[0]); // input[type="file"][multiple]
		  myFormData.push("pid", selectedPid);
	  }
	  else {
		  myFormData = new FormData();
		  myFormData.append("file", fileupload_button.files[0]);
		  myFormData.append("pid", selectedPid);
	  }

	  $.ajax({
	    url: 'upload.html',
	    data: myFormData,
	    dataType: 'text',
	    processData: false,
	    contentType: false,
	    type: 'POST',
	    success: function(data){
	  	  	$('input[id="fileupload_button"]').prop("disabled", false);
	  	  	$('input[name="p_selected"]').prop("disabled", false);
	  	  	$('#successMsgDiv2').text(data);
	  	  	$('#successMsgDiv2').show();
	    },
	    error: function(data){
	    	alert(data);
	    	$('input[id="fileupload_button"]').prop("disabled", false);
	    	$('input[id="uploadAssetButton"]').prop("disabled", false);
	    	$('input[name="p_selected"]').prop("disabled", false);
	  	  	$('#errorMsgDiv2').text(data);
	  	  	$('#errorMsgDiv2').show();
	    }
	    
	  });
	}
  
</script>

<script language="Javascript"> function fileUploadMethod(form, action_url, div_id) { 
    // Create the iframe...     
	var iframe = document.createElement("iframe"); 
    iframe.setAttribute("id", "upload_iframe"); 
    iframe.setAttribute("name", "upload_iframe"); 
    iframe.setAttribute("width", "0"); 
    iframe.setAttribute("height", "0"); 
    iframe.setAttribute("border", "0"); 
    iframe.setAttribute("style", "width: 0; height: 0; border: none;"); 
  
    // Add to document...     
	form.parentNode.appendChild(iframe); 
    window.frames['upload_iframe'].name = "upload_iframe"; 
  
    iframeId = document.getElementById("upload_iframe"); 
  
    // Add event...     
	var eventHandler = function () { 
  
            if (iframeId.detachEvent) iframeId.detachEvent("onload", eventHandler); 
            else iframeId.removeEventListener("load", eventHandler, false); 
  
            // Message from server...             
			if (iframeId.contentDocument) { 
                content = iframeId.contentDocument.body.innerHTML; 
            } else if (iframeId.contentWindow) { 
                content = iframeId.contentWindow.document.body.innerHTML; 
            } else if (iframeId.document) { 
                content = iframeId.document.body.innerHTML; 
            } 
  
            document.getElementById(div_id).innerHTML = content; 
  
            // Del the iframe...             
			setTimeout('iframeId.parentNode.removeChild(iframeId)', 250); 
        } 
  
    if (iframeId.addEventListener) iframeId.addEventListener("load", eventHandler, true); 
    if (iframeId.attachEvent) iframeId.attachEvent("onload", eventHandler); 
  
    // Set properties of form...     form.setAttribute("target", "upload_iframe"); 
    form.setAttribute("action", action_url); 
    form.setAttribute("method", "post"); 
    form.setAttribute("enctype", "multipart/form-data"); 
    form.setAttribute("encoding", "multipart/form-data");
    
    // Submit the form...     
	form.submit(); 
  
    document.getElementById(div_id).innerHTML = "Uploading..."; 
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
					<c:if test="${policy.id == 121}">
						<input name="p_selected" type="radio" checked="checked" value="${policy.id}" onclick="changePolicy(${policy.id})"/>
					</c:if>
					<c:if test="${policy.id != 121}">
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
			<h5 id="policyDetails">Details of Policy ID - 121</h5>
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
		
		<!-- 
		<form id="assetUploadForm" action="javascript:upload()" method="POST" enctype="multipart/form-data"> 
			<input id="fileupload_button" type="file" name="fileupload" />
			<input id="uploadAssetButton" type="button" value="Upload Selected Asset" size="30" onclick="upload()" />
			<input id="uploadAssetButton"  value="Upload Selected Asset" type="submit" />
			<div id="dropzone" class="fade well">Drop files here</div>
			<div id="successMsgDiv2"><h6><i>Asset uploaded successfully...</i></h6></div>
		</form> 
		
		<form id="assetUploadForm"> 
			<input id="fileupload_button" type="file" name="fileupload" /></br> 
			<input id="uploadAssetButton" type="button" value="upload" onClick="fileUploadMethod(this.form,'upload.html','upload'); return false;" > 
			<div id="upload"></div> 
		</form>
		--> 
		
		<input id="fileupload_button" type="file" name="fileupload" />
		<input id="uploadAssetButton" type="button" value="Upload Asset with Selected Policy" size="30" onclick="upload()" />
		<br/>
		<div id="successMsgDiv2"><h6><i>&nbsp;</i></h6></div>
	
		</div>
		
	</div>
</div>

</body>
</html>
