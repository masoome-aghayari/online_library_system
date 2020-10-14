<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>register form</title>
    <style type="text/css">
        <%@ include file="styles/registerStyle.css" %>
    </style>
</head>
<body>
<form action="${pageContext.request.contextPath}/">
    <button type="submit" id="home" name="home" class="btn btn-success btn-group" style="margin: 2vh 2vw">Home</button>
</form>
<div class="main-block">
    <form:form>
    </form:form>
</div>
</body>
</html>
