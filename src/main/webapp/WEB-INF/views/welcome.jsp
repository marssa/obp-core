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
<div style="display: table; width: 30em">
    <div class="button" onclick="location.href='<c:url value="/liveGpsData"/>'">live GPS data</div>
    <div class="button" onclick="location.href='<c:url value="/logout"/>'">logout</div>
</div>
</body>
</html>