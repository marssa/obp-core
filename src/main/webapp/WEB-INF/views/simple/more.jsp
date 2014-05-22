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
<obp:header headline="More features"/>
<div style="display: table; width: 100%">
    <div class="button" onclick="location.href='<c:url value="/simple/view"/>'">back</div>
    <div class="button" onclick="location.href='<c:url value="/simple/instruments"/>'">list of instruments</div>
    <div class="button" onclick="location.href='<c:url value="/simple/manifest"/>'">local manifest</div>
    <div class="button" onclick="location.href='<c:url value="/simple/defaults"/>'">default values</div>
</div>
<script type="text/javascript">
    function doLayout() {
        var docWidth = $(document).width();
        $("body").css("font-size",docWidth/30+"px");
    }

    $(window).resize(function() {
        doLayout();
    })

    $(function() {
        doLayout();
    })

</script>
</body>
</html>