<%--
  Created by Robert Jaremczak
  Date: 2013-10-27
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="obp" tagdir="/WEB-INF/tags/obp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
    <link rel="stylesheet" href="<c:url value="/styles/obp.css"/>"/>
    <script src="<c:url value="/scripts/jquery-2.0.3.min.js"/>"></script>
    <script src="<c:url value="/scripts/layout.js"/>"></script>
</head>
<body>
<obp:header headline="Defaults" btnHome="false"/>
<div class="shortButton" onclick="location.href='<c:url value="/simple/more"/>'">back</div>
<table class='tabularData'>
    <c:forEach items="${defaultValues}" var="entry">
        <tr>
            <td style="text-align: left">
                <span style="color: white">${entry.key}</span><br>
            </td>
            <td>
                <span style="color: white">${entry.value}</span><br>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>