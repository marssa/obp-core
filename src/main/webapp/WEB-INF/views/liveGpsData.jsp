<%--
  Created by Robert Jaremczak
  Date: 10/2/13
  Time: 1:01 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>OBP - Live GPS Data</title>
        <link rel="stylesheet" href="<c:url value="/styles/obp.css"/>"/>
        <script src="<c:url value="/scripts/jquery-2.0.3.min.js"/>"></script>
    </head>
    <script type="text/javascript">
        $(document).ready(function() {
            setInterval("ajaxd()",3000);
        });

        function ajaxd() {
            $.ajax({
                type: "GET",
                url: "<c:url value="/api/gps/position"/>",
                data: "user=success",
                success: function(data){
                    $("#fixTime").fadeOut();
                    $("#latitude").text(data.latitude);
                    $("#longitude").text(data.longitude);
                    $("#fixTime").text(data.fixTime);
                    $("#fixTime").fadeIn();
                }
            });
        }    </script>
    <body>
        <h1>Live GPS Data</h1>
        <hr/>
        <h3 style="color:lightblue">OBP version pre-0.1 (build: 20131008173501)</h3>
        <br/><br/><br/>
        <table>
            <tr>
                <td>Latitude</td>
                <td><span class="liveData" id="latitude">-</span></td>
            </tr>
            <tr>
                <td>Longitude</td>
                <td><span class="liveData" id="longitude">-</span></td>
            </tr>
            <tr>
                <td>Fixing time</td>
                <td><span class="liveData" id="fixTime">-</span></td>
            </tr>
        </table>
    </body>
</html>