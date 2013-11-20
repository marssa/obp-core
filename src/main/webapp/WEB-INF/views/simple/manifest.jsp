<%--
  Created by Robert Jaremczak
  Date: 2013-10-27
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
<obp:header headline="Manifest" btnHome="false"/>
<h3>&nbsp;&nbsp;&nbsp;${realm.name}</h3>
<table class='tabularData'>
    <c:forEach items="${realm.allAttributeValues}" var="entry">
        <tr>
            <td style="text-align: right">
                <span style="color: white">${entry.key}</span>
            </td>
            <td style="text-align: left">
                ${entry.value}
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>