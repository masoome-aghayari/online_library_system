<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://kit.fontawesome.com/dc1776693c.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="<c:url value="/resources/theme/css/styles/registerStyle.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/theme/css/styles/loginStyle.css"/>">
    <title>login form</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/">
    <button type="submit" class="btn btn-success btn-group btn-home">Home</button>
</form>
<div class="main-block">
    <form:form modelAttribute="user" action="/loginProcess" cssClass="content" method="Post">
        <table class="tableizer-table">
            <thead>
            <tr class="tableizer-firstrow">
                <th colspan="2">Login Form</th>
            </tr>
            </thead>
            <tbody>
            <tr class="error-row">
                <td colspan="2"><div class="login-error"><strong>${message}</strong></div></td>
            </tr>
            <tr>
                <td><form:label path="nationalId" cssStyle="margin-left: 0">National Id:</form:label></td>
                <td><form:input path="nationalId" id="nationalId" class="form-control" required="required"/></td>
            </tr>
            <tr>
                <td><form:label path="password" cssStyle="margin-left: 0">Password:</form:label></td>
                <td><form:password path="password" id="password" name="password" cssClass="form-control pass-input"
                                   placeholder="Enter Password" required="required"/>
                    <i class="far fa-eye btn-show-hide-pwd" data-for="password"></i></td>
            </tr>
            <tr>
                <td colspan="2">
                    <div class="span2">
                        <form:button class="btn btn-primary btn-block button">Login</form:button>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </form:form>
</div>
</body>
<script>

    const allBtnSHP = document.querySelectorAll(".btn-show-hide-pwd");
    allBtnSHP.forEach((button) => {
        const forElement = document.getElementById(button.dataset.for);
        if (forElement && forElement instanceof HTMLInputElement) {
            ["mousedown", "touchstart"].forEach((eventName) => {
                button.addEventListener(eventName, () => {
                    forElement.setAttribute("type", "text");
                });
            });
            ["mouseup", "mouseleave", "touchend", "touchcancel"].forEach((eventName) => {
                button.addEventListener(eventName, (e) => {
                    e.preventDefault();
                    forElement.setAttribute("type", "password");
                });
            });
        }
    });
</script>
</html>