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
        <obp:header headline="Today's system log" btnHome="true"/>
        <table class='tabularData'>
            <tr>
                <th>#</th>
                <th>timestamp</th>
                <th>level</th>
                <th>message</th>
            </tr>
            <c:forEach items="${logEntries}" var="entry">
                <tr>
                    <td>${entry.id}</td>
                    <td>${entry.timestamp}</td>
                    <td>${entry.level}</td>
                    <td>${entry.message}</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>