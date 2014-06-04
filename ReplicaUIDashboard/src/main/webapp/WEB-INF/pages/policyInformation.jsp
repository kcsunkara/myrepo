<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Asset Replica Dashboard</title>
<script	type="text/javascript" src="../replicauidashboard/js/jquery-1.3.1.min.js"></script>
<script type="text/javascript" src="../replicauidashboard/js/jquery.tablesorter.pager.js"></script>
<script type="text/javascript" src="../replicauidashboard/js/jquery.tablesorter.js"></script> 
	
<link href="../replicauidashboard/css/search.css" rel="Stylesheet"
	type="text/css">
<link type="text/css" rel="stylesheet" href="css/styles.css" />
<script type="text/javascript" src="js/jquery.pajinate.js"></script>

<script>
function changePolicy(pid) {
	alert("In changePolicy - PID = "+pid);
	//policy_list
	$('#site_list tbody tr').remove();
	$.post('siteInfoByPID.html', {"pid":  pid}, function(response) {
		$.each(response,function(i,item) {
			$('<tr>'+
				'<td>'+response[i].siteId+'</td>'+
				'<td>'+response[i].name+'</td>'+
				'<td>'+response[i].tier+'</td>'+
				'<td>'+response[i].isPrimary+'</td>'+
				'<td>'+response[i].requiredCopies+'</td>'+
				'</tr>').appendTo('#site_list');
		});
	});
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
						<input name="p_selected" type="radio" checked="checked" onclick="changePolicy(${policy.id})"/>
					</c:if>
					<c:if test="${policyId != policy.id}">
						<input name="p_selected" type="radio" onclick="changePolicy(${policy.id})"/>
					</c:if>
				</td>
				</c:if>
				<c:if test="${empty policyId}">
				<td>
					<c:if test="${policy.id == 122}">
						<input name="p_selected" type="radio" checked="checked" onclick="changePolicy(${policy.id})"/>
					</c:if>
					<c:if test="${policy.id != 122}">
						<input name="p_selected" type="radio" onclick="changePolicy(${policy.id})"/>
					</c:if>
				</td>
				</c:if>
			</tr>
		</tbody>
		</c:forEach>
		</table>
		<c:if test="${not empty policyId}">
			<h5 id="Details of Policy">Details of Policy ID - ${policyId}</h5>
		</c:if>
		<c:if test="${empty policyId}">
			<h5 id="Details of Policy">Details of Policy ID - 122</h5>
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
		

	</div>
</div>

</body>
</html>
