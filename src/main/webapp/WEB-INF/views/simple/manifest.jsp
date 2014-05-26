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
    <script src="<c:url value="/scripts/jquery-2.1.1.min.js"/>"></script>
    <script src="<c:url value="/scripts/layout.js"/>"></script>
</head>
<body>
<obp:header headline="Manifest" btnHome="false"/>
<div class="shortButton" onclick="location.href='<c:url value="/simple/more"/>'">back</div>
<table class='tabularData'>
    <tr>
        <td style="text-align: left"><b>attribute</b></td>
        <td style="text-align: left"><b>value</b></td>
        <td style="text-align: left"><b>reliability</b></td>
    </tr>
    <c:forEach items="${realm.allReadouts}" var="entry">
        <tr>
            <td style="text-align: left">
                <span>${entry.instrument.name}.</span>
                <span style="color: white">${entry.name}</span>
            </td>
            <td style="text-align: left">
                ${fn:substring(entry.value,0,12)}
            </td>
            <td style="text-align: left">
                    <small>${entry.reliability}</small>
            </td>
        </tr>
    </c:forEach>
</table>
<br>
<div>
    ${realm.name} (${obpConfig.shortHostInfo})<br>
    ${obpConfig.shortOsInfo}<br>
    ${obpConfig.shortJavaInfo}<br><br>
</div>
</body>
</html>