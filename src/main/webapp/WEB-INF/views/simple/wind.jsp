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
    <link rel="stylesheet" href="<c:url value="/styles/obp.css"/>"/>
    <script src="<c:url value="/scripts/jquery-2.0.3.min.js"/>"></script>
    <meta http-equiv="refresh" content="3"/>
</head>
<body>
<div id="backButton" style="width: 300px; text-align: center; font-size: 1em">
    <div class="shortButton" onclick="location.href='<c:url value="/simple/view"/>'">back</div>
</div>
<table class='tabularData' style="width: 300px">
    <obp:attribute name="true wind speed" value=""/>
    <obp:attribute name="true wind angle" value=""/>
    <obp:attribute name="rel. wind speed" value="${speed}"/>
    <obp:attribute name="rel. wind angle" value="${angle}"/>
    <obp:attribute name="wind temperature" value="${temperature}"/>
</table>
</body>
</html>