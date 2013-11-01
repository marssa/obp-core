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
            background: #5f859f;
            font-size: 100px;
            height: 280px;
            width: 280px;
            border-radius: 30px;
        }

        .obpButton:hover {
            background-color: #6f9dba;
            cursor: pointer;
        }
    </style>
</head>
<body style="background: white; font-family: sans-serif; color: white">
<table align="center" style="height: 100%; width: 280px; text-align: center">
    <tr>
        <td>
            <div class="obpButton" onclick="location.href='<c:url value="/simple/view"/>'">
                OBP
            </div>
        </td>
    </tr>
</table>
</body>
</html>