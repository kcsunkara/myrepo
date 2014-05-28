<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

<script defer="defer">
$(document).ready(function() 
{ 
$("#policy_list")
      .tablesorter({widthFixed: true, widgets: ['zebra']})
      .tablesorterPager({container: $("#pager")}); 
} 
);  
</script>
<div id="pager" class="pager">
       <form>
              <img src="../replicauidashboard/image/first.png" class="first"/>
              <img src="../replicauidashboard/image/prev.png" class="prev"/>
              <input type="text" class="pagedisplay"/>
              <img src="../replicauidashboard/image/next.png" class="next"/>
              <img src="../replicauidashboard/image/last.png" class="last"/>
              <select class="pagesize">
                     <option value="250">250 per page</option>
                     </select>
       </form>
</div>
</head>
<body>
<%@ include file="header.jsp"%>
<div id="content">
	<div class="bookmarked" id="logo"></div>
</div>
<h2 id="Policy Information">Policy Information</h2>
<div id="tableDetailsPolicyInformation">
	<div id="summaryDetailsPolicyInformation">
		<table width="100%" class="tablesorter" id="policy_list">
		<thead>
			<tr>
				<th class="header"><b>Policy ID</b></th>
				<th class="header"><b>Name</b></th>
				<th class="header"><b>Customer ID</b></th>
				<th class="header"><b>M5 Check</b></th>
				<th class="header"><b>Active</b></th>
				<th class="header"><b>File System Path</b></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="even">1</td>
				<td class="even">John</td>
				<td class="even">101</td>
				<td class="even">True</td>
				<td class="even">False</td>
				<td class="even">../info1</td>
			</tr>

			<tr>
				<td  class="odd">2</td>
				<td  class="odd">Peter</td>
				<td  class="odd">102</td>
				<td  class="odd">True</td>
				<td  class="odd">False</td>
				<td  class="odd">../web2</td>
			</tr>

			<tr>
				<td class="even">3</td>
				<td class="even">Max</td>
				<td class="even">103</td>
				<td class="even">False</td>
				<td class="even">False</td>
				<td class="even">../web3</td>
			</tr>

			<tr>
				<td class="odd">4</td>
				<td class="odd">Smith</td>
				<td class="odd">104</td>
				<td class="odd">False</td>
				<td class="odd">True</td>
				<td class="odd">../info4</td>
			</tr>
		</tbody>
		</table>
	</div>
</div>

</body>
</html>
