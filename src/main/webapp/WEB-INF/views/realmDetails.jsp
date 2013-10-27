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
    <meta http-equiv="refresh" content="5"/>
</head>
<body>
<obp:header headline="Realm details" btnHome="true"/>
<c:forEach items="${realm.bodies}" var="body">
    <h3>${body.name} (local: ${body.local})</h3>
    <table class='tabularData'>
        <tr>
            <td>name</td>
            <td>${body.name}</td>
        </tr>
        <tr>
            <td>UUID</td>
            <td>${body.uuid}</td>
        </tr>
        <tr>
            <td>description</td>
            <td>${body.description}</td>
        </tr>
        <c:forEach items="${body.instruments}" var="instrument">
            <tr>
                <td colspan="2" style="text-align: left"><span style="color: white">${instrument.name}</span>
                    <br>UUID: ${instrument.uuid}<br>${instrument.description}</td>
            </tr>
            <tr>
                <td>status</td>
                <td>${instrument.status}</td>
            </tr>
            <c:if test="${instrument.working}">
                <c:forEach items="${instrument.attributeEntries}" var="entry">
                    <tr>
                        <td>${entry.key}</td>
                        <td>${entry.value}</td>
                    </tr>
                </c:forEach>
            </c:if>
            </tr>
        </c:forEach>
    </table>
</c:forEach>
</body>
</html>