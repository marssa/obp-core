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
<obp:header headline="Instruments" btnHome="false"/>
<div class="shortButton" onclick="location.href='<c:url value="/simple/more"/>'">back</div>
<table class='tabularData'>
    <c:forEach items="${instruments}" var="instrument">
        <tr>
            <td style="text-align: left">
                <span style="color: white">${instrument.name} (${instrument.local ? "local":"remote"})</span><br>
                <span>status: <b>${instrument.status}</b></span><br>
                <c:if test="${instrument.status ne 'OFF'}">
                    <span>reliability: ${instrument.reliability}</span>
                </c:if>
            </td>
            <td>
                <c:choose>
                    <c:when test="${instrument.status eq 'OPERATIONAL'}">
                        <input class="button" type="button" value="pause" onclick="pauseInstrument('${instrument.id}')">
                    </c:when>
                    <c:when test="${instrument.status eq 'PAUSED'}">
                        <input class="button" type="button" value="resume" onclick="resumeInstrument('${instrument.id}')">
                    </c:when>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
</table>
<script type="text/javascript">
    function pauseInstrument(id) {
        $.ajax({
            type: "GET",
            url: "<c:url value="/secure/instrument/pause"/>"+"?id="+id,
            success: function(data){
                location.reload();
            }
        });
    }

    function resumeInstrument(id) {
        $.ajax({
            type: "GET",
            url: "<c:url value="/secure/instrument/resume"/>"+"?id="+id,
            success: function(data){
                location.reload();
            }
        });
    }
</script>
</body>
</html>