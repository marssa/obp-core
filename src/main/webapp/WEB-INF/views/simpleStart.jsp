<%--
  Created by Robert Jaremczak
  Date: 10/2/13
  Time: 1:01 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="obp" tagdir="/WEB-INF/tags/obp"%>

<html>
<head>
    <script src="<c:url value="/scripts/jquery-2.0.3.min.js"/>"></script>
    <style>
        .obpButton {
            text-align: center;
            vertical-align: middle;
            background: #5f859f;
            position: absolute;
        }

        .obpButton:hover {
            background-color: #6f9dba;
            cursor: pointer;
        }
    </style>
</head>
<script>
    var widthUnit, heightUnit;
    var docWidth, docHeight;

    function initMetrics() {
        docWidth = $(document).width();
        docHeight = $(document).height();

        if(docWidth > docHeight) {
            heightUnit = docHeight / 10;
            widthUnit = heightUnit;
        } else {
            widthUnit = docWidth / 10;
            heightUnit = widthUnit;
        }
    }

    function doLayout() {
        initMetrics();

        var btnWidth = widthUnit * 3;
        var btnHeight = heightUnit * 3;

        $("#centralButton")
                .height(btnHeight)
                .width(btnWidth)
                .css("line-height", btnHeight+"px")
                .css("border-radius", btnHeight/8)
                .css("font-size", btnHeight/3)
                .css("left",(docWidth - btnWidth)/2)
                .css("top",(docHeight - btnHeight)/2);
    }

    $(function() {
        doLayout();
    })

    $(window).resize(function() {
        doLayout();
    })

</script>
<body style="background: white; font-family: sans-serif; color: white">
    <div id="centralButton" class="obpButton" onclick="location.href='<c:url value="/simple/view"/>'">
        OBP
    </div>
</table>
</body>
</html>