<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="refresh" content="1800">
	<link rel="stylesheet" href="resources/css/style.css" rel="stylesheet"/>
	<title>TUI Quality Metrics dashboard </title>
	<script src="resources/js/jquery-1.8.2.js" ></script>
	<script src="resources/js/custom.js" ></script>
</head>
<body>
	<header class="header">
			<div class="group grid-960">
				<div class="col grid-12">
					<a href="#"><img src="resources/images/tui-logo.png"  alt="TUI" /></a>
					<span class="header-text">TUI Quality Metrics dashboard</span>
				</div>
			</div>
	</header>
	<main class="dashboard">
			<div class="group grid-960">
				<div class="col grid-12">
				<table id="dashboard-report" class="dashboard-report">
					<thead>
						<tr>
							<th>Parameter</th><th>Threshold</th>
								<c:forEach var="entry" items="${metricMap}">
									<th class="${buildStatusMap[entry.key]}">${envName[entry.key]}<br/><br/><b>Last Build Status:<c:choose> <c:when test="${ not empty buildStatusMap[entry.key]}">
									${buildStatusMap[entry.key]}
									
									</c:when>
									<c:otherwise>IN PROGRESS</c:otherwise>
									</c:choose>
									<b/><br/></th>
								</c:forEach>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="entry" items="${keyMap}">
							<tr><td data-category="${metricCategory[entry.key] }">${entry.value}</td>
							<td data-category="${metricCategory[entry.key] }">
								<%-- <c:forEach var="qualityMetricsMap" items="${lstQualityGateMetricsMap}">
									<c:if test="${qualityMetricsMap.get('metric') eq entry.getKey() }">
									 --%>			
											${threshold[entry.key] }
															
									<%-- </c:if>
								</c:forEach> --%>
							</td>
							<c:forEach var="metricsMapEntry" items="${metricMap }">
								<td data-category="${metricCategory[entry.key] }">
									<c:set var="metricsMapList" value="${metricsMapEntry.value}"/>
										<c:forEach var="metricsMap" items="${metricsMapList }">
											    	<c:if test="${metricsMap['key'] eq entry.key }">
													
															${metricsMap["frmt_val"] }
															
																<c:if test="${metricsMap['trend'] eq 1 }"><img src="resources/images/Up.png"  alt="UP" /></c:if>
																<c:if test="${metricsMap['trend'] eq -1 }"><img src="resources/images/Down.png"  alt="UP" /></c:if>
															
															
														
												    </c:if>
											</c:forEach>
								</td>
							</c:forEach>
							</tr>
							
						</c:forEach>
					</tbody>
		 		</table>
			</div>
		</div>
	</main>
		<header class="header">
			
				<div align="center" >
					<span class="header-text" >Zapper Response For Develop/Core Branch</span>
				</div>
			
	</header>
	
			<div class="group grid-12" >

				<table class="dashboard-zapper grid-6" >
					<thead>
						<tr>
							<th>Parameter</th><th>Value</th>
							
						</tr>
						<c:forEach var="entry" items="${zapperResponse}">
							<tr><td>${entry.key}</td><td>${entry.value}</td></tr>
						
						</c:forEach>
					</thead>
				</table>

		</div>
	
	
	<footer class="footer">
				<div class="group grid-960">
				<div class="col grid-12">
					<a id="sonar-link" href="http://cqc.tuitravelplc.com/sessions/new">Sonar</a>
					<span>|</span>
					<a id="jankin-link" href="http://10.241.12.25:8080/jenkins/">Jenkins</a>
				</div>
			</div>
				
			</footer>

</body>
</html>