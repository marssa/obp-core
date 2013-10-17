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
        <table class="dataList">
            <tr>
                <td>Fixing time:</td>
                <td><span id="fixTime">-</span> UTC</td>
            </tr>
            <tr>
                <td>Latitude:</td>
                <td><span id="latitude">-</span> &deg;</td>
            </tr>
            <tr>
                <td>Longitude:</td>
                <td><span id="longitude">-</span> &deg;</td>
            </tr>
            <tr>
                <td>True North course:</td>
                <td><span id="trueNorthCourse">-</span>  &deg;</td>
            </tr>
            <tr>
                <td>Velocity over ground:</td>
                <td><span id="velocityOverGround">-</span> m/s</td>
            </tr>
            <tr>
                <td>Magnetic variation:</td>
                <td><span id="magneticVariation">-</span> m/s</td>
            </tr>
        </table>
        <br>Satellites in view:<br>
        <div id="satellitesInView"></div>
        <script type="text/javascript">
            $(document).ready(function() {
                setInterval("ajaxd()",3000);
                ajaxd();
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
                        $("#trueNorthCourse").text(data.trueNorthCourse);
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