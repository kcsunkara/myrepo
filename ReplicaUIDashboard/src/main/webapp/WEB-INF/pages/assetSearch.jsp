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
<title>Asset Replica Dashboard</title>
<META http-equiv="Content-Type" content="text/html;charset=UTF-8">
<script type="text/javascript" src="../replicauidashboard/js/jquery-1.3.1.min.js"></script>
<script type="text/javascript" src="../replicauidashboard/js/jquery.tablesorter.pager.js"></script>

<script type="text/javascript" src="../replicauidashboard/js/jquery.tablesorter.js"></script> 
<link href="../replicauidashboard/css/search.css" rel="Stylesheet" type="text/css">

</head>
<script type="text/javascript">
       
function validateForm()
{
var y=document.forms["myForm"]["assetId"].value;
var z=document.forms["myForm"]["name"].value;
var w=document.forms["myForm"]["path"].value;

if ((y==null || y=="" )&&(z==null || z=="")&&(w==null || w=="") )
  {
       
       document.getElementById("result").style.display = 'block'; 
       return false;
  }
else
{ 
if( (isNaN(y)))
{
       document.getElementById("result1").style.display = 'block'; 
       document.getElementById("result").style.display = 'none';
       return false;
       }
       else{
       document.getElementById("result").style.display = 'none';
       }
       
}
}

function validateid()
{
validateForm();      
var x=document.forms["myForm"]["assetId"].value;

if (isNaN(x))
  {
       document.getElementById("result1").style.display = 'block'; 
       
       return false;
       }
else
{      
       document.getElementById("result1").style.display = 'none';
}
}

function clearAll()
{
	
		document.getElementById("result1").style.display = 'none'; 
		document.forms["myForm"]["assetId"].value='';
		document.forms["myForm"]["name"].value='';
		document.forms["myForm"]["path"].value='';
		return false;
		
}



</script> 


<body>
       <%@ include file="header.jsp"%>
       
              <div id="content">
              </div>

              <div id="search">
                     <h2>Search</h2>
              </div>
              <div id="searchBar">
                     <form action="/replicauidashboard/searchAsset.html" method="post" name="myForm"  onsubmit ="return validateForm();">
                           <table id="searchBarTable">
                                  <tr>

                                         <td><b><a id="searchBarTable1">Asset ID </a> <input type="text" name="assetId" onchange="return validateid();"  placeholder="Number Only" value="${param.assetId}"  /></b></td>
                                         <td><b><a id="searchBarTable3">Name </a><input type="text"
                                                name="name" value="${fn:replace(param.name,'"','&quot;')}"  onchange="return validateForm();"/></b></td>
                                         <td><b><a id="searchBarTable4">Path </a><input type="text"
                                                name="path" value="${fn:replace(param.path,'"','&quot;')}"  onchange="return validateForm();"/></b></td>
                                         <td><button type="submit" name="submit">Search</button></td>
                                         <td><button type="reset" name="ClearAll" value="" onclick="return clearAll();" >Clear All</button></td>
                                  </tr>

                           </table>
                     </form>
                     <div id="result" > * Please Enter Atleast One Of The Search Criteria </div>
                     <div id="result1"> * Please Enter Asset ID Number only </div>
                     

              </div>
              <br>

<c:if test="${empty assets}">
<c:if test="${!empty param.assetId || !empty param.name || !empty param.path}">
<p id="result2" >No Records found</p>
</c:if>
</c:if>
                     <c:if test="${not empty assets}">
                           <c:if test="${fn:length(assets) > 0}">
                                  <table id="insured_list" class="tablesorter">
                                         <thead>
                                                <tr>
                                                       <th><b>Name</b></th>
                                                       <th><b>Path</b></th>
                                                       <th><b>Size</b></th>
                                                       <th><b>MD5</b></th>
                                                       <th><b>Policy</b></th>
                                                       <th><b>Customer</b></th>
                                                       <th><b>Date</b></th>
                                                       <th><b>Status</b></th>
                                                </tr>
                                         </thead>

<tbody>
                                         <c:forEach items="${assets}" var="asset">
                                                
                                                       <tr>
                                                              <td><b><a href="/replicauidashboard/assetDetails.html?assetId=${asset.id }&policyId=${asset.policyId}&customerName=${asset.customerName}" title="${asset.name}"> <c:set var="assetname"
                                                                                  value='${asset.name}' /> <c:if
                                                                                  test="${fn:length(assetname) > 30}">${fn:substring(assetname, 0, 27)}...</c:if>
                                                                           <c:if test="${fn:length(assetname) <= 30}">${asset.name}</c:if></a></b></td>
															<td><a title="${asset.fsPath}" >
                                                                           <c:set var="asset_fspath" value='${asset.fsPath}' /> <c:if
                                                                                  test="${fn:length(asset_fspath) > 30}">${fn:substring(asset_fspath, 0, 27)}...</c:if></a>
                                                                           <c:if test="${fn:length(asset_fspath) <= 30}">${asset.fsPath}</c:if>
                                                              </td>
                                                              
                                                              <td>
                                                                           <c:set var="assetfilesize" value='${asset.filesize}' />
                                                                             <c:if test="${assetfilesize > 1073741824 }"> 
                                          <fmt:formatNumber type="number" pattern="##" value="${asset.filesize/1073741824 }" />  GB </c:if> 
                                          <c:if test="${ (asset.filesize < 1073741824) and (asset.filesize >= 1048576 )}">
                                                                     <fmt:formatNumber type="number" pattern="##" value="${asset.filesize/1048576 }" /> MB </c:if>
                                         <c:if test="${ (asset.filesize < 1048576) and (asset.filesize >= 1024 )}">
                                                                     <fmt:formatNumber type="number" pattern="##" value="${asset.filesize/1024 }" /> KB </c:if>
                                         <c:if test="${ (asset.filesize < 1024) }">
                                                                     <fmt:formatNumber type="number" pattern="##" value="${asset.filesize }" /> B </c:if>
                                                              </td>

                                                              <td><a title="${asset.assetMD5}">
                                                                           <c:set var="asset_md5" value='${asset.assetMD5}' /> <c:if
                                                                                  test="${fn:length(asset_md5) > 10}">${fn:substring(asset_md5, 0, 7)}...</c:if></a>
                                                                           <c:if test="${fn:length(asset_md5) <= 10}">${asset.assetMD5}</c:if>
                                                              </td>

                                                              <td><a title="${asset.policyId}">${asset.policyId} </a></td>

                                                              <td><a title="${asset.customerName}"
                                                              > <c:set var="assetcustomerName"
                                                                                  value='${asset.customerName}' /> <c:if
                                                                                  test="${fn:length(assetcustomerName) > 10}">${fn:substring(assetcustomerName, 0, 7)}...</c:if></a>
                                                                           <c:if test="${fn:length(assetcustomerName) <= 10}">${asset.customerName}</c:if>
                                                              </td>

                                                              <td><fmt:formatDate value="${asset.createdDate}" pattern="MM/dd/yyyy HH:mm:ss"/></td>

                                                              <td><a title="${asset.deleteDate}"
                                                                     > <c:set
                                                                                  var="asset_delete_date" value='${asset.deleteDate}' />
                                                                           <c:if test="${(asset_delete_date) == null}">Active</c:if> <c:if
                                                                                  test="${(asset_delete_date) != null}">Deleted</c:if>
                                                              </a></td>

                                                       </tr>
                                                
                         
                                         </c:forEach>
                                         </tbody>
                                  </table>

                           </c:if>
                     </c:if>
                     <c:if test="${fn:length(assets) > 250}">

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
<script defer="defer">
       $(document).ready(function() 
    { 
        $("#insured_list")
              .tablesorter({widthFixed: true, widgets: ['zebra']})
              .tablesorterPager({container: $("#pager")}); 
    } 
       );  
       
</script>
</c:if>

       <c:if test="${fn:length(assets) <= 250}">
       <script defer="defer">
       $(document).ready(function() 
    { 
        $("#insured_list")
              .tablesorter({widthFixed: true, widgets: ['zebra']});
              
    } 
       );  
       
</script>     
              
</c:if>
       
</body>
</html>