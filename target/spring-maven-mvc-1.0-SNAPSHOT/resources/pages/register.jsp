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
    <title>Registration Form</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/">
    <button type="submit" id="home" name="home" class="btn btn-success btn-group btn-home">Home</button>
</form>
<div class="main-block">
    <form:form modelAttribute="user" class="content" action="register-process" method="POST"
               enctype="multipart/form-data">
        <table class="tableizer-table">
            <thead>
            <tr class="tableizer-firstrow">
                <th colspan="4">Registration Form</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td colspan="2"><form:errors path="firstName" cssClass="error left-error"/></td>
                <td colspan="2"><form:errors path="lastName" cssClass="error right-error"/></td>
            </tr>
            <tr>
                <td>
                    <form:label path="firstName" class="col-md-4 control-label left-label">FirstName:</form:label>
                </td>
                <td>
                    <form:input path="firstName" placeholder="firstName" type="text" class="form-control left-input"
                                onkeypress="return isAlphabetKey(event)" required="required"/>
                </td>
                <td><form:label path="lastName" class="col-md-4 control-label">LastName:</form:label></td>
                <td>
                    <form:input path="lastName" placeholder="lastName" class="form-control"
                                onkeypress="return isAlphabetKey(event)" required="required"/>
                </td>
            </tr>
            <tr>
                <td colspan="2"><form:errors path="nationalId" cssClass="error left-error"/></td>
                <td colspan="2"><form:errors path="mobileNumber" cssClass="error right-error"/></td>
            </tr>
            <tr>
                <td><form:label path="nationalId" class="col-md-4 control-label">National Id:</form:label></td>
                <td>
                    <form:input path="nationalId" placeholder="nationalId" class="form-control left-input"
                                onkeypress="return isNumberKey(event)" required=" required"/>
                </td>
                <td><form:label path="mobileNumber" class="col-md-4 control-label">Mobile Number:</form:label></td>
                <td>
                    <form:input path="mobileNumber" placeholder="mobileNumber" class="form-control"
                                pattern="9((0[1-3]|5)|(1[0-9])|(2[0-2])|(3(1|[3-9]))|(9[0-1]))[0-9]{7}"
                                title="9197417221" onkeypress="return isNumberKey(event)" required="required"/>
                </td>
            </tr>
            <tr>
                <td colspan="2"><form:errors path="email" cssClass="error left-error"/></td>
                <td colspan="2"><form:errors path="gender" cssClass="error right-error"/></td>
            </tr>
            <tr>
                <td><form:label path="email" class="col-md-4 control-label">Email:</form:label></td>
                <td><form:input path="email" placeholder="email" class="form-control left-input"
                                pattern="^[\w!#$%&'*+/=?`{|}~^-]+(?:\.[\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,6}$"
                                title="simple@example.com" required="required"/>
                <td><form:label path="gender" class="col-md-4 control-label">Gender:</form:label></td>
                <td>
                    <form:select path="gender" id="gender" required="required" class="dropdown">
                        <option value="">--</option>
                        <option value="male">male</option>
                        <option value="female">female</option>
                    </form:select>
                </td>
            </tr>
            <tr>
                <td colspan="2"><form:errors path="age" cssClass="error left-error"/></td>
                <td colspan="2"><form:errors path="profilePicture" cssClass="error right-error"/></td>
            </tr>
            <tr>
                <td><form:label path="age" class="col-md-4 control-label">Age:</form:label></td>
                <td>
                    <form:input type="number" path="age" id="age" required="required" class="form-control left-input"/>
                </td>
                <td><label for="image" class="col-md-4 control-label">Choose Image:</label></td>
                <td>
                    <label for="image" class="custom-file-label image"></label>
                    <input id="image" name="file" class="custom-file-input image-input"
                           type="file" required/>
                </td>
            </tr>
            <tr>
                <td colspan="2"><form:errors path="password" cssClass="error left-error"/></td>
                <td colspan="2"><form:errors path="confirmPassword" cssClass="error right-error"/></td>
            </tr>
            <tr>
                <td><form:label path="password" class="col-md-4 control-label">Password:</form:label></td>
                <td>
                    <form:password path="password" id="password" cssClass="form-control left-input pass-input"
                                   placeholder="Enter Password"
                                   pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$"
                                   title="at least one uppercase letter, one digit; 8<length<16" required="required"/>
                    <i class="far fa-eye btn-show-hide-pwd" data-for="password"></i>
                </td>
                <td><form:label path="confirmPassword" class="col-md-4 control-label">Confirm Pass:</form:label></td>
                <td>
                    <form:password path="confirmPassword" placeholder="Re-Enter Password" id="confirmPassword"
                                   cssClass="form-control pass-input" required="required"/>
                    <i class="far fa-eye btn-show-hide-pwd" data-for="confirmPassword" style="margin-left: -3vw;"></i>
                </td>
            </tr>
            <tr>
                <td colspan="4">
                    <div class="span2">
                        <form:button id="register" class="btn btn-primary btn-block button">
                            Register
                        </form:button>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </form:form>
</div>
</body>
<script>
    function isNumberKey(event) {
        const charCode = event.charCode ? event.charCode : event.keyCode;
        const pattern = /[0-9]+$/;
        const isValid = pattern.test(String.fromCharCode(charCode));
        if (!isValid) {
            window.alert("Just Digits Are Allowed");
            return false;
        }
    }

    function isAlphabetKey(event) {
        const charCode = event.charCode ? event.charCode : event.keyCode;
        const pattern = /[a-zA-Z]+([. ]*[a-zA-Z]+)*$/;
        const isValid = pattern.test(String.fromCharCode(charCode));
        if (!isValid) {
            window.alert("Just English Alphabets Are Allowed");
            return false;
        }
    }

    $(".custom-file-input").on("change", function () {
        const fileName = $(this).val().split("\\").pop();
        $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
    });

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