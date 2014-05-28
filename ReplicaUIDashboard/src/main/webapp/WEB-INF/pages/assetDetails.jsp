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
<META http-equiv="Content-Type" content="text/html;charset=UTF-8">
<link href="../replicauidashboard/css/search.css" rel="Stylesheet"
	type="text/css">
</head>

<body>
	<%@ include file="header.jsp"%>
	<div id="smContainerAssetDetails">
		<div id="content">
			<div class="bookmarked" id="logo"></div>
		</div>
		<h2 id="assetColor">Asset Details</h2>
		<div id="summary">
			<h3>&nbsp;Summary</h3>
		</div>
		<div id="tableDetails">
			<table id="summaryDetails" cellpadding="5" cellspacing="0"
				width="100%">
				<c:forEach items="${assetDetails}" var="assetDetail" begin="0"
					end="0">
					<tr>
						<td width="33%" align="left">
							<table cellpadding="0" cellspacing="0" width="100%">
								<tr>
									<td id="assetId" align="left">Asset ID:</td>
									<td id="assetIdDetails" align="left"><b
										id="assetDetailsColor"> ${assetDetail.id} </b></td>
								</tr>
							</table>
						</td>
						<td width="33%" align="left">
							<table cellspacing="0" width="100%">
								<tr>
									<td id="customer" align="left">Customer:</td>
									<td id="customerName" align="left"><b id="customerColor">
											${assetDetail.customerName} </b></td>
								</tr>
							</table>
						
					</tr>
					<tr>
						<td width="33%" align="left">
							<table cellpadding="0" cellspacing="0" width="100%">
								<tr>
									<td id="customer" align="left">Policy ID:</td>
									<td id="customerName" align="left"><b id="customerColor">
											${assetDetail.policyId} </b></td>
								</tr>
							</table>
						</td>
						<td width="34%" align="left">
							<table cellpadding="0" cellspacing="0" width="100%">
								<tr>
									<td id="repository" align="left">File Size:</td>
									<td id="repositoryDetails" align="left"><b
										id="repositoryColor"> <c:set var="assetfilesize"
												value='${assetDetail.filesize}' /> <c:if
												test="${assetfilesize > 1073741824 }">
												<fmt:formatNumber type="number" pattern="##"
													value="${assetDetail.filesize/1073741824 }" />
                                    GB
                                  </c:if> <c:if
												test="${ (assetDetail.filesize < 1073741824) and (assetDetail.filesize >= 1048576 )}">
												<fmt:formatNumber type="number" pattern="##"
													value="${assetDetail.filesize/1048576 }" />
                                    MB
                                  </c:if> <c:if
												test="${ (assetDetail.filesize < 1048576) and (assetDetail.filesize >= 1024 )}">
												<fmt:formatNumber type="number" pattern="##"
													value="${assetDetail.filesize/1024 }" />
                                    KB
                                  </c:if> <c:if
												test="${ (assetDetail.filesize < 1024) }">
												<fmt:formatNumber type="number" pattern="##"
													value="${assetDetail.filesize }" />
                                    B
                                  </c:if>
									</b></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td width="33%" align="left">
							<table cellpadding="0" cellspacing="0" width="100%">
								<tr>
									<td id="assetId" align="left">Name:</td>
									<td id="assetIdDetails" align="left"><b
										id="assetDetailsColor"> ${assetDetail.name} </b></td>
								</tr>
							</table>
						</td>
						<td width="33%" align="left">
							<table cellpadding="0" cellspacing="0" width="100%">
								<tr>
									<td id="customer" align="left">Date Created:</td>
									<td id="customerName" align="left"><b id="customerColor">
											<fmt:formatDate value="${assetDetail.createdDate}" pattern="MM/dd/yyyy HH:mm:ss" />
									</b></td>
								</tr>
							</table>
						</td>
						<td width="34%" align="left">
							<table cellpadding="0" cellspacing="0" width="100%">
								<tr>
									<td id="repository" align="left">File MD-5:</td>
									<td id="repositoryDetails" align="left"><b
										id="repositoryColor"> ${assetDetail.assetMD5} </b></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td width="33%" align="left">
							<table cellpadding="0" cellspacing="0" width="100%">
								<tr>
									<td id="customer" align="left">Date Deleted:</td>
									<td id="customerName" align="left"><b id="customerColor">
											<fmt:formatDate value="${assetDetail.deleteDate}"
												pattern="MM/dd/yyyy HH:mm:ss" />
									</b></td>
								</tr>
							</table>
						</td>
						<td width="34%" align="left">
							<table cellpadding="0" cellspacing="0" width="100%">
								<tr>
									<td id="repository" align="left">User MD-5:</td>
									<td id="repositoryDetails" align="left"><b
										id="repositoryColor"> ${assetDetail.userMD5} </b></td>
								</tr>
							</table>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>




		<div style="float: left; width: 100%; clear: both;">
			<table id="summaryDetails" cellpadding="5" cellspacing="0"
				width="100%">
				<c:forEach items="${assetDetails}" var="assetDetail">
					<tr>
						<td width="33%" align="left">
							<table cellpadding="0" cellspacing="0" width="100%">
								<tr>
									<td id="assetId" width="100%" align="left">View
										Asset-Instance:</td>
								</tr>
							</table>
						</td>
						<td width="67%" align="left">
							<table cellpadding="0" cellspacing="0" width="100%">
								<tr>
									<td width="50%" align="left">
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td id="customer" align="left">Storage-ID:</td>
												<td id="customerName" align="left"><b
													id="customerColor"> <a id="storageId">${assetDetail.storageLocationId}</a>
												</b></td>
											</tr>
										</table>
									</td>
									<td width="50%" align="left">
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td id="repository" align="left">Date Created:</td>
												<td id="repositoryDetails" align="left"><b
													id="repositoryColor"> <fmt:formatDate
															value="${assetDetail.createDate}" pattern="MM/dd/yyyy HH:mm:ss" />
												</b></td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td width="33%" align="left">
							<table cellpadding="0" cellspacing="0" width="100%">
								<tr>
									<td id="viewAssetName" width="100%" align="left"><b
										id="assetDetailsColor" style="margin-left: 10px;">
											${assetDetail.storageLocationName} </b></td>
								</tr>
							</table>
						</td>
						<td width="67%" align="left">
							<table cellpadding="0" cellspacing="0" width="100%">
								<tr>
									<td width="50%" align="left">&nbsp;</td>
									<td width="50%" align="left">
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td id="repository" align="left">Check Date:</td>
												<td id="repositoryDetails" align="left"><b
													id="repositoryColor"> <fmt:formatDate
															value="${assetDetail.lastCheck}" pattern="MM/dd/yyyy HH:mm:ss" />
												</b></td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>

					</tr>
					<tr>
						<td width="33%" align="left">&nbsp;</td>
						<td width="67%" align="left">
							<table cellpadding="0" cellspacing="0" width="100%">
								<tr>
									<td width="50%" align="left">
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td id="customer" align="left">Folder Path:</td>
												<td id="customerName" align="left"><b
													id="customerColor"> ${assetDetail.fsPath} </b></td>
											</tr>
										</table>
									</td>
									<td width="50%" align="left">
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td id="repository" align="left">Date Deleted:</td>

												<td id="repositoryDetails" align="left"><b
													id="repositoryColor"> <fmt:formatDate
															value="${assetDetail.purgeDate}" pattern="MM/dd/yyyy HH:mm:ss" />
												</b></td>

											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td width="33%" align="left">&nbsp;</td>
						<td width="67%" align="left">
							<table cellpadding="0" cellspacing="0" width="100%">
								<tr>
									<td width="50%" align="left">
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td id="customer" align="left">File Name:</td>
												<td id="customerName" align="left"><b
													id="customerColor"> ${assetDetail.filename} </b></td>
											</tr>
										</table>
									</td>
									<td width="50%" align="left">
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td id="repository" align="left">Encrypted:</td>
												<td id="repositoryDetails" align="left"><b
													id="repositoryColor"> <c:if
															test="${assetDetail.encrypted == 'false'}"> N </c:if> <c:if
															test="${assetDetail.encrypted == 'true'}"> Y </c:if>
												</b></td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td width="33%" align="left"
							style="border-bottom: 1px solid; border-bottom-color: #424242; padding-left: 10px;">&nbsp;</td>
						<td width="67%" align="left"
							style="border-bottom: 1px solid; border-bottom-color: #424242;">
							<table cellpadding="0" cellspacing="0" width="100%">
								<tr>
									<td width="50%" align="left">
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td id="repository" align="left">File MD-5:</td>
												<td id="repositoryDetails" align="left"><b
													id="customerColor"> ${assetDetail.locationMD5} </b></td>
											</tr>
										</table>
									</td>
									<td width="50%" align="left">
										<table cellpadding="0" cellspacing="0" width="100%">
											<tr>
												<td id="repository" align="left">Status:</td>
												<td id="repositoryDetails" align="left"><b
													id="repositoryColor"> ${assetDetail.value} </b></td>
											</tr>
										</table>
									</td>
								</tr>
							</table>
						</td>
					</tr>

				</c:forEach>
			</table>
		</div>
		<br>
	</div>
</body>
</html>
