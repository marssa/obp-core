<%--
  Created by Robert Jaremczak
  Date: 2013-10-16
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="obp" tagdir="/WEB-INF/tags/obp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%@ include file="/WEB-INF/fragments/head.jspf"%>
<body>
<obp:header headline="Authentication"/>
<form name='f' action="<c:url value='/j_spring_security_check' />" method='POST'>

    <table>
        <tr>
            <td><input class="inputField" type='text' name='j_username' value='' placeholder="user name"></td>
        </tr>
        <tr>
            <td><input class="inputField" type='password' name='j_password' placeholder="password"></td>
        </tr>
        <tr>
            <td style="text-align: right" >
                <br>
                <input class="button" name="submit" type="submit" value="  login  "/>
            </td>
        </tr>
    </table>

</form>
</body>
</html>