<%--
  Created by Robert Jaremczak
  Date: 10/2/13
  Time: 1:01 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="obp" tagdir="/WEB-INF/tags/obp"%>

<html>
<%@ include file="/WEB-INF/fragments/head.jspf"%>
<body>
<obp:header headline="Open Bridge Platform"/>
<div style="display: table; width: 20em">
    <div class="button" onclick="location.href='<c:url value="/simple/view"/>'">simple view</div>
    <div class="button" onclick="location.href='<c:url value="/simple/manifest"/>'">local manifest</div>
    <div class="button" onclick="location.href='<c:url value="/simple/map"/>'">local position on map</div>
    <div class="button" onclick="location.href='<c:url value="/simple/liveGpsData"/>'">local GPS receiver</div>
    <div class="button" onclick="location.href='<c:url value="/todaySystemLog"/>'">today's system log</div>
    <div class="button" onclick="location.href='<c:url value="/logoff"/>'">logout</div>
</div>
</body>
</html>