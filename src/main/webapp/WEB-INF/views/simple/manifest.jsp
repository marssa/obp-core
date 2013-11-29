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
    <link rel="stylesheet" href="<c:url value="/styles/obp.css"/>"/>
    <script src="<c:url value="/scripts/jquery-2.0.3.min.js"/>"></script>
    <meta http-equiv="refresh" content="3"/>
</head>
<body>
<obp:header headline="Manifest" btnHome="false"/>
<br>
<div id="backButton" style="width: 300px; text-align: center; font-size: 1em">
    <div class="shortButton" onclick="location.href='<c:url value="/simple/view"/>'">back</div>
</div>
<h3>&nbsp;&nbsp;local instance name: ${realm.name}</h3>
<table class='tabularData'>
    <tr>
        <td style="text-align: left"><b>attribute</b></td>
        <td style="text-align: left"><b>value</b></td>
        <td style="text-align: left"><b>reliability</b></td>
    </tr>
    <c:forEach items="${realm.attributeInfos}" var="entry">
        <tr>
            <td style="text-align: left">
                <span>${entry.instrument.name}.</span>
                <span style="color: white">${entry.name}</span>
            </td>
            <td style="text-align: left">
                ${fn:substring(entry.value,0,30)}
            </td>
            <td style="text-align: left">
                    <small>${entry.reliability}</small>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>