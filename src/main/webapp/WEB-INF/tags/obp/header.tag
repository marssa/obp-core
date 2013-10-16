<%--
  Created by Robert Jaremczak
  Date: 2013-10-16
--%>
<%@ attribute name="headline" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="display: inline-block; clear:both; width: 30em">
    <h1>${headline}</h1>
    <hr/>
    <span class="faded">OBP-${obpConfig.buildId}</span>
    <br/><br/>
</div>
<script type="text/javascript">
    document.title="${headline}"
</script>
