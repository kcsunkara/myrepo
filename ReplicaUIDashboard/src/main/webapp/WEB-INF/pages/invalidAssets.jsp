<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Asset Replica Dashboard</title>
<META http-equiv="Content-Type" content="text/html;charset=UTF-8">
<script type="text/javascript" src="../replicauidashboard/js/jquery-1.3.1.min.js"></script>
<script type="text/javascript" src="../replicauidashboard/js/jquery.tablesorter.pager.js"></script>

<script type="text/javascript" src="../replicauidashboard/js/jquery.tablesorter.js"></script> 
<link href="../replicauidashboard/css/search.css" rel="Stylesheet" type="text/css">

<style>

tr:nth-child(odd){
    background-color:#CCC
}
tr:nth-child(even){
    background-color:#FFF
}
#summary {
	/*background-image: url('/replicauidashboard/image/Details.jpg');*/
	height: 20px;
	width: 30%;
	color: #F8FBEF;
	border-top-left-radius: 4px;
	border-top-right-radius: 4px;
	margin-left: 2px;
	font-size: 14pt;
	background-color: #107299;
}​
  </style>

</head>
<body>
 <%@ include file="header.jsp"%>
<div id="content">

		<div width="10px" style="text:align-center" id="summary" style="background-color: #107299;">
					<span style="margin-left:50px">Name</span> 
			<span style="margin-left:150px">Path</span>
		</div>
        

<table width="30%">
	 <c:forEach items="${invalidAssetsList1}" var="assets">
		<tr class="alt_content">
			<td width="15px" style="border-spacing: 2px;;font-size: 12px ;font-family: verdana">
			
			<a href="/replicauidashboard/assetDetails.html?assetId=${assets.id }&policyId=${assets.policyId}&customerName=${assets.customerName}">${assets.id}</a></td>
			<td width="15px" style="border-spacing: 2px;;font-size: 12px;font-family: verdana">${assets.name}</td>
		</tr>
	</c:forEach>
</table>

</div>

	

</body>
</html>