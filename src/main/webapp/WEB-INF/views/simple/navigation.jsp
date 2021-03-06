<%--
  Created by Robert Jaremczak
  Date: 2013-11-29
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="obp" tagdir="/WEB-INF/tags/obp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
    <link rel="stylesheet" href="<c:url value="/styles/obp.css"/>"/>
    <script src="<c:url value="/scripts/jquery-2.1.1.min.js"/>"></script>
    <script src="<c:url value="/scripts/layout.js"/>"></script>
    <meta http-equiv="refresh" content="3"/>
</head>
<body>
<div class="shortButton" onclick="location.href='<c:url value="/simple/view"/>'">back</div>
<table class='tabularData'>
    <obp:attribute name="position" value="${position}"/>
    <obp:attribute name="SOG" value="${sog}"/>
    <obp:attribute name="COG" value="${cog}"/>
    <obp:attribute name="ETA" value=""/>
    <obp:attribute name="waypoint info" value=""/>
    <obp:attribute name="waypoint arrival" value=""/>
    <obp:attribute name="speed" value=""/>
    <obp:attribute name="magnetic heading" value=""/>
    <obp:attribute name="depth" value=""/>
    <obp:attribute name="sea temp." value=""/>
    <obp:attribute name="current" value=""/>
    <obp:attribute name="UMG" value=""/>
</table>
</body>
</html>