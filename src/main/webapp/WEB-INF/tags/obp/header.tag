<%--
  Created by Robert Jaremczak
  Date: 2013-10-16
--%>
<%@ attribute name="headline" required="true" %>
<%@ attribute name="btnHome" required="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="display: inline-block; clear:both; width: 30em; margin-bottom: 5px">
    <div style="float: left">
        <h1>${headline}</h1><br>
        <span class="faded" style="font-size: 80%">OBP-${obpConfig.buildId}</span>
    </div>
    <c:if test="${not empty btnHome && btnHome}">
        <div style="float: right; text-align: center">
            <div class="toolbtn" onclick="location.href='<c:url value="/"/>'">&#9634;</div>
            <div class="toolbtn_title">home</div>
        </div>
    </c:if>
</div>
<script type="text/javascript">
    document.title="${headline}"
</script>
