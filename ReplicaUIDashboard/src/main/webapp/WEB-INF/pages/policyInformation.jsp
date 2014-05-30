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
</head>

<body>
<%@ include file="header.jsp"%>
<div id="content">
	<div class="bookmarked" id="logo"></div>
</div>
<c:if test="${empty param.pid}">
	<h2 id="Policies Available">Policies Available</h2>
</c:if>
<c:if test="${not empty param.pid}">
	<h2 id="Policies Available">View/Change Policy for Asset ID - ${param.assetId}</h2>
</c:if>
<%
String policyId = request.getParameter("pid");
%>
<div id="tableDetailsPolicyInformation">
	<div id="summaryDetailsPolicyInformation">
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
				<c:if test="${not empty param.pid}">
					<th><b>Selected</b></th>
				</c:if>
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
				<c:if test="${not empty param.pid}">
					<td>
						<c:if test="${param.pid == policy.id}">
							<input name="p_selected" type="radio" checked="checked"/>
						</c:if>
						<c:if test="${param.pid != policy.id}">
							<input name="p_selected" type="radio"/>
						</c:if>
					</td>
				</c:if>
			</tr>
		</tbody>
		</c:forEach>
		</table>
		<c:if test="${not empty param.pid}">
			<h5 id="Details of Policy">Details of Policy ID - ${param.pid}</h5>
		</c:if>
		<c:if test="${empty param.pid}">
			<h5 id="Details of Policy">Details of Policy ID - 122</h5>
		</c:if>
		
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
				<c:if test="${not empty param.pid}">
					<th><b>Selected</b></th>
				</c:if>
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
				<c:if test="${not empty param.pid}">
					<td>
						<c:if test="${param.pid == policy.id}">
							<input name="p_selected" type="radio" checked="checked"/>
						</c:if>
						<c:if test="${param.pid != policy.id}">
							<input name="p_selected" type="radio"/>
						</c:if>
					</td>
				</c:if>
			</tr>
		</tbody>
		</c:forEach>
		</table>

	</div>
</div>

</body>
</html>
