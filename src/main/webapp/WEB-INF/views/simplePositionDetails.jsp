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
    <body style="text-align: left">
    <div style="display: inline-block; width: 80%">
        <div class="button" onclick="location.href='<c:url value="/simple/map"/>'">map</div>
    </div>
        <table class="dataList">
            <tr>
                <td>Fix time:</td>
                <td><span id="fixTime">n/a</span> UTC</td>
            </tr>
            <tr>
                <td>Fix quality:</td>
                <td><span id="fixQuality">n/a</span></td>
            </tr>
            <tr>
                <td>Fix mode:</td>
                <td><span id="fixMode">n/a</span></td>
            </tr>
            <tr>
                <td>Fix type:</td>
                <td><span id="fixType">n/a</span></td>
            </tr>
            <tr>
                <td>Latitude:</td>
                <td><span id="latitude">n/a</span> &deg;</td>
            </tr>
            <tr>
                <td>Longitude:</td>
                <td><span id="longitude">n/a</span> &deg;</td>
            </tr>
            <tr>
                <td>True North course:</td>
                <td><span id="trueNorthCourse">n/a</span> &deg;</td>
            </tr>
            <tr>
                <td>Altitude:</td>
                <td><span id="altitude">n/a</span> m</td>
            </tr>
            <tr>
                <td>Velocity over ground:</td>
                <td><span id="velocityOverGround">n/a</span> m/s</td>
            </tr>
            <tr>
                <td>Magnetic variation:</td>
                <td><span id="magneticVariation">n/a</span> &deg;</td>
            </tr>
            <tr>
                <td>Position DOP:</td>
                <td><span id="pdop">n/a</span></td>
            </tr>
            <tr>
                <td>Horizontal DOP:</td>
                <td><span id="hdop">n/a</span></td>
            </tr>
            <tr>
                <td>Vertical DOP:</td>
                <td><span id="vdop">n/a</span></td>
            </tr>
        </table>
        <br>Satellites in view (effective <span id="numSatellitesInView"></span>):<br>
        <div id="satellitesInView"></div>
        <script type="text/javascript">
            $(document).ready(function() {
                setInterval("ajaxd()",3000);
                ajaxd();
            });

            function ajaxd() {
                $.ajax({
                    type: "GET",
                    url: "<c:url value="/simple/gps/all"/>",
                    data: "user=success",
                    success: function(data){
                        $("#fixTime").fadeOut();
                        $("#fixTime").text(data.fixTime);
                        $("#fixTime").fadeIn();
                        $("#latitude").text(data.latitude);
                        $("#longitude").text(data.longitude);
                        $("#trueNorthCourse").text(data.trueNorthCourse);
                        $("#velocityOverGround").text(data.velocityOverGround);
                        $("#numSatellitesInView").text(data.numSatellitesInView);
                        $("#altitude").text(data.altitude);
                        $("#fixQuality").text(data.fixQuality);
                        $("#fixMode").text(data.fixMode);
                        $("#fixType").text(data.fixType);
                        $("#pdop").text(data.pdop);
                        $("#hdop").text(data.hdop);
                        $("#vdop").text(data.vdop);

                        var html = "<table class='tabularData'>"+
                                "<tr>"+
                                "<th>#</th>"+
                                "<th>sat ID</th>"+
                                "<th>elevation<br>[&deg;]</th>"+
                                "<th>azimuth<br>[&deg;]</th>"+
                                "<th>s/n<br>[dbm]</th>"+
                                "</tr>";
                        for(var i in data.satellitesInView) {
                            var sat = data.satellitesInView[i];
                            html += "<tr>";
                            html += "<td>"+(Number(i)+1)+"</td>";
                            html += "<td>"+sat.id+"</td>";
                            html += "<td>"+sat.elevation+"</td>";
                            html += "<td>"+sat.azimuth+"</td>";
                            html += "<td>"+avoidNaN(sat.snr)+"</td>";
                            html += "</tr>";
                        }
                        html += "</table>"
                        $("#satellitesInView").html(html);
                    }
                });
            }

            function avoidNaN(num) {
                return isNaN(num) ? "n/a" : String(num);
            }
        </script>
    </body>
</html>