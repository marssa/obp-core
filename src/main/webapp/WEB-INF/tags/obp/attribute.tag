<%--
  Created by Robert Jaremczak
  Date: 2013-11-29
--%>
<%@ attribute name="name" required="true" %>
<%@ attribute name="value" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tr>
    <td>${name}</td>
    <td style="color:white">
        <c:choose>
            <c:when test="${empty value}">n/a</c:when>
            <c:otherwise>${value}</c:otherwise>
        </c:choose>
    </td>
</tr>
