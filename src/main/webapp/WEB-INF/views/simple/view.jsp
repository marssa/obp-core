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

        .clickable:hover {
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
            font-size: 13px;
            font-weight: normal;
        }
    </style>
</head>
<body style="background: navy; font-family: sans-serif; color: black">
<table id="mainTable" align="center" style="height: 100%; width: 100%; text-align: center">
    <tr>
        <td>
            <div class="obpButton clickable" onclick="location.href='<c:url value="/simple/navigation"/>'">
                <span class="propLabel">SOG</span></br>
                <span id="sog" class="propValue">n/a</span>
            </div>
            <br>
            <div class="obpButton clickable" style="" onclick="location.href='<c:url value="/simple/map"/>'" >
                <span class="propLabel">Position</span></br>
                <span id="position" class="propValue">n/a</span>
            </div>
            <br>
            <div class="obpButton">
                <span class="propLabel">Depth</span></br>
                <span id="depth" class="propValue">n/a</span>
            </div>
            <br>
            <div class="obpButton">
                <span class="propLabel">Date</span></br>
                <span id="dateTime" class="propValue">n/a</span>
            </div>
        </td>
        <td>
            <div class="obpButton clickable" onclick="location.href='<c:url value="/simple/navigation"/>'">
                <span class="propLabel">COG</span></br>
                <span id="cog" class="propValue">n/a</span>
            </div>
            <br>
            <div class="obpButton clickable" onclick="location.href='<c:url value="/simple/wind"/>'">
                <span class="propLabel">Wind</span></br>
                <span id="wind" class="propValue">n/a</span>
            </div>
            <br>
            <div class="obpButton">
                <span class="propLabel">Water</span></br>
                <span id="water" class="propValue">n/a</span>
            </div>
            <br>
            <div class="obpButton clickable" onclick="location.href='<c:url value="/simple/more"/>'">
                <span class="propLabel">More...</span></br>
                <span class="propValue">available features</span>
            </div>
        </td>
    </tr>
</table>
<script type="text/javascript">

    function showValue(elementId, readoutView) {
        var element = $(elementId);
        element.text(readoutView.value);
        if(!readoutView.local) {
            element.css('color','green');
        }
        if(readoutView.reliability == 'MANUAL') {
            element.css('color','blue');
        } else if(readoutView.reliability == 'LOW') {
            element.css('color','red');
        } else if(readoutView.reliability == 'UNDEFINED') {
            element.css('color','lightgrey');
        } else {
            element.css('color','black');
        }
    }

    function ajaxd() {
        $.ajax({
            type: "GET",
            url: "<c:url value="/simple/viewDataFeed"/>",
            data: "user=success",
            success: function(data){
                showValue("#position",data.position);
                showValue("#cog",data.cog);
                showValue("#sog",data.sog);
                showValue("#water",data.water);
                showValue("#wind",data.wind);
                showValue("#depth",data.depth);
                showValue("#dateTime",data.dateTime);
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