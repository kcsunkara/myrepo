<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Policy Information</title>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<link href="../replicauidashboard/css/search.css" rel="Stylesheet"
	type="text/css">
<link type="text/css" rel="stylesheet" href="css/styles.css" />
<script type="text/javascript" src="js/jquery.pajinate.js"></script>
<script>
</script>
</head>
<body>
<%@ include file="header.jsp"%>
<div id="content">
	<div class="bookmarked" id="logo"></div>
</div>
<h2 id="Policy Information">Policy Information</h2>
<div id="tableDetailsPolicyInformation">
	<div id="summaryDetailsPolicyInformation">
		<table cellpadding="3" cellspacing="0" width="100%">


			<tr>
				<th id="policyTableColumn1"><b>Policy ID</b></th>
				<th id="policyTableColumn1"><b>Name</b></th>
				<th id="policyTableColumn1"><b>Customer ID</b></th>
				<th id="policyTableColumn1"><b>M5 Check</b></th>
				<th id="policyTableColumn1"><b>Active</b></th>
				<th id="policyTableColumn1"><b>File System Path</b></th>
			</tr>

			<tr>
				<td id="policyTableColumn2">1</td>
				<td id="policyTableColumn2">John</td>
				<td id="policyTableColumn2">101</td>
				<td id="policyTableColumn2">True</td>
				<td id="policyTableColumn2">False</td>
				<td id="policyTableColumn2">../info</td>
			</tr>

			<tr>
				<td id="policyTableColumn2">2</td>
				<td id="policyTableColumn2">Peter</td>
				<td id="policyTableColumn2">102</td>
				<td id="policyTableColumn2">True</td>
				<td id="policyTableColumn2">False</td>
				<td id="policyTableColumn2">../web</td>
			</tr>

			<tr>
				<td id="policyTableColumn2">3</td>
				<td id="policyTableColumn2">Max</td>
				<td id="policyTableColumn2">103</td>
				<td id="policyTableColumn2">False</td>
				<td id="policyTableColumn2">False</td>
				<td id="policyTableColumn2">../web</td>
			</tr>

			<tr>
				<td id="policyTableColumn2">4</td>
				<td id="policyTableColumn2">Smith</td>
				<td id="policyTableColumn2">104</td>
				<td id="policyTableColumn2">False</td>
				<td id="policyTableColumn2">True</td>
				<td id="policyTableColumn2">../info</td>
			</tr>

		</table>
	</div>
</div>

</body>
</html>
