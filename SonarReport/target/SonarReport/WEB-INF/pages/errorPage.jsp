<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
	
	<c:choose>
		<c:when test="${ fn:contains(exception.message,'404 Not Found')}"><br/><br/><h2 align="center" style="color:#fff, font-size: 20Px">Unable to display quality metrics  dashboard at this moment, please try after sometime.</h2><font></font></c:when>
		<c:otherwise ><br/><br/><h2 align="center" style="color:#fff, font-size: 20Px"><font color="red">Authorization failed.</font><h2/></c:otherwise>
	</c:choose>
</body>
</html>