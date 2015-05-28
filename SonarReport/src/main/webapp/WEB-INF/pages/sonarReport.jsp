<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" href="resources/css/style.css" rel="stylesheet"/>
	<title>TUI Quality Metrics dashboard </title>
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
				<table class="dashboard-report">
					<thead>
						<tr>
							<th>Parameter</th><th>Threshold</th>
								<c:forEach var="key" items="${metricMap.keySet()}">
									<th >${key}<br/><br/><b>Last Build Status: ${buildStatusMap.get(key)}<b/><br/></th>
								</c:forEach>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="entry" items="${keyMap.entrySet()}">
							<tr><td>${entry.getValue()}</td>
							<td>
								<c:forEach var="qualityMetricsMap" items="${lstQualityGateMetricsMap}">
									<c:if test="${qualityMetricsMap.get('metric') eq entry.getKey() }">
														
											${qualityMetricsMap.get("error") }
															
									</c:if>
								</c:forEach>
							</td>
							<c:forEach var="metricsMapEntry" items="${metricMap.entrySet() }">
								<td>
									<c:set var="metricsMapList" value="${metricsMapEntry.getValue()}"/>
										<c:forEach var="metricsMap" items="${metricsMapList }">
											    	<c:if test="${metricsMap.get('key') eq entry.getKey() }">
													
															${metricsMap.get("frmt_val") }
															<c:choose>
																<c:when test="${metricsMap.get('trend') eq 1 or metricsMap.get('trend') eq 0}"><img src="resources/images/Up.png"  alt="UP" /></c:when>
																<c:otherwise><img src="resources/images/Down.png"  alt="UP" /></c:otherwise>
															</c:choose>
															
														
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
			
				<div class="col grid-7" align="center" >
					<span class="header-text" >Zapper Response For Develop/Core Branch</span>
				</div>
			
	</header>
	
			<div class="group grid-7" >
				<div class="grid-7" align="center">
				<table class="dashboard-report" >
					<thead>
						<tr>
							<th>Parameter</th><th>Value</th>
							
						</tr>
						<c:forEach var="entry" items="${zapperResponse}">
							<tr><td>${entry.getKey()}</td><td>${entry.getValue()}</td></tr>
						
						</c:forEach>
					</thead>
				</table>
			</div>
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