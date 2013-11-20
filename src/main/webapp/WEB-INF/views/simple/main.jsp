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
            display: table-cell;
            text-align: center;
            vertical-align: middle;
            background: white;
            height: 80px;
            width: 120px;
            border-radius: 10px;
        }

        .obpButton:hover {
            background-color: #c9e8f5;
            cursor: pointer;
        }

        table td {
            padding: 10px;
        }

        .propButton {
            font-size: 20px;
            font-weight: normal;
        }

        .propLabel {
            font-size: 20px;
            font-weight: bold;
        }

        .propValue {
            font-size: 14px;
            font-weight: normal;
        }
    </style>
</head>
<body style="background: #5f859f; font-family: sans-serif; color: black">
<table id="mainTable" align="center" style="height: 100%; width: 100%; text-align: center">
    <tr>
        <td>
            <div class="obpButton" onclick="location.href='<c:url value="/simple/map"/>'">
                <span class="propLabel">Position</span></br>
                <span id="position" class="propValue">13E 23N</span>
            </div>
            <br>
            <div class="obpButton">
                <span class="propLabel">Depth</span></br>
                <span class="propValue">n/a</span>
            </div>
            <br>
            <div class="obpButton">
                <span class="propLabel">Heading</span></br>
                <span id="heading" class="propValue">13E 23N</span>
            </div>
            <br>
            <div class="obpButton" onclick="location.href='<c:url value="/simple/start"/>'">
                <span class="propButton">OBP</span></br>
            </div>
        </td>
        <td>
            <div class="obpButton">
                <span class="propLabel">Wind</span></br>
                <span class="propValue">n/a</span>
            </div>
            <br>
            <div class="obpButton">
                <span class="propLabel">Speed</span></br>
                <span  id="speed" class="propValue">13E 23N</span>
            </div>
            <br>
            <div class="obpButton">
                <span class="propLabel">Aux</span></br>
                <span class="propValue">...</span>
            </div>
            <br>
            <div class="obpButton" onclick="location.href='<c:url value="/simple/manifest"/>'">
                <span class="propButton">Manifest</span></br>
            </div>
        </td>
    </tr>
</table>
<script type="text/javascript">

    function ajaxd() {
        $.ajax({
            type: "GET",
            url: "<c:url value="/simple/viewDataFeed"/>",
            data: "user=success",
            success: function(data){
                $("#position").text(data.position);
                $("#heading").text(data.heading+" °");
                $("#speed").text(data.speed+" kts");
            }
        });
    }

    function avoidNaN(num) {
        return isNaN(num) ? "n/a" : String(num);
    }

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

        var tabWidth = $("#mainTable").width();
        var tabHeight = $("#mainTable").height();
        var cellHeight = tabHeight/4 - 40;
        var cellWidth = tabWidth/2 - 20;

        $(".obpButton")
                .width(cellWidth)
                .height(cellHeight)
                .css("border-radius", cellHeight/8);

        $(".propLabel,.propButton").css("font-size", cellHeight/4);
        $(".propValue").css("font-size", cellHeight/6);
    }

    $(window).resize(function() {
        doLayout();
    })

    $(function() {
        setInterval("ajaxd()",3000);
        ajaxd();
        doLayout();
    })

</script>
</body>
</html>