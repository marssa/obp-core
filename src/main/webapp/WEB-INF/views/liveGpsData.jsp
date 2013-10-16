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
        <obp:header headline="Live GPS data"/>
        <table>
            <tr>
                <td>Fixing time</td>
                <td><span class="liveData" id="fixTime">-</span> UTC</td>
            </tr>
            <tr>
                <td>Latitude</td>
                <td><span class="liveData" id="latitude">-</span> &deg;</td>
            </tr>
            <tr>
                <td>Longitude</td>
                <td><span class="liveData" id="longitude">-</span> &deg;</td>
            </tr>
            <tr>
                <td>True North heading</td>
                <td><span class="liveData" id="trueNorthHeading">-</span>  &deg;</td>
            </tr>
            <tr>
                <td>Velocity over ground</td>
                <td><span class="liveData" id="velocityOverGround">-</span> m/s</td>
            </tr>
            <tr>
                <td colspan="2">
                    Satellites in view<br/>
                    <div id="satellitesInView">
                    </div>
                </td>
            </tr>
        </table>
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
                        $("#fixTime").text(data.fixTime);
                        $("#fixTime").fadeIn();
                        $("#latitude").text(data.latitude);
                        $("#longitude").text(data.longitude);
                        $("#trueNorthHeading").text(data.trueNorthHeading);
                        $("#velocityOverGround").text(data.velocityOverGround);

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
                            html += "<td>"+sat.snr+"</td>";
                            html += "</tr>";
                        }
                        html += "</table>"
                        $("#satellitesInView").html(html);
                    }
                });
            }
        </script>
    </body>
</html>