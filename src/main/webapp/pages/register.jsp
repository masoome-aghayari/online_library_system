<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://kit.fontawesome.com/dc1776693c.js" crossorigin="anonymous"></script>
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
    <form:form modelAttribute="user" class="content" action="register-process" id="main-form" method="POST">
        <div>
            <h3>Registration Form</h3>
        </div>

        <div class="form-group field">
            <form:label path="firstName" class="col-md-4 control-label">FirstName:</form:label>
            <form:input path="firstName" id="firstName" name="firstName" placeholder="firstName" type="text"
                        class="form-control" onkeypress="return isAlphabetKey(event)" required="required"/>
            <form:errors path="firstName" cssClass="error"/>
        </div>

        <div class="form-group field">
            <form:label path="lastName" class="col-md-4 control-label">LastName:</form:label>
            <form:input path="lastName" id="lastName" name="lastName" placeholder="lastName" class="form-control"
                        onkeypress="return isAlphabetKey(event)" required="required"/>
            <form:errors path="lastName" cssClass="error"/>
        </div>

        <div class="form-group field">
            <form:label path="nationalId" class="col-md-4 control-label">National Id:</form:label>
            <form:input path="nationalId" placeholder="nationalId" id="nationalId" class="form-control"
                        required="required"/>
            <form:errors path="nationalId" cssClass="error"/>
        </div>

        <div class="form-group field">
            <form:label path="mobileNumber" class="col-md-4 control-label">Mobile Number:</form:label>
            <form:input path="mobileNumber" placeholder="mobileNumber" id="mobileNumber"
                        pattern="9((0[1-3]|5)|(1[0-9])|(2[0-2])|(3(1|[3-9]))|(9[0-1]))[0-9]{7}"
                        title="simple@example.com" class="form-control" required="required"/>
            <form:errors path="nationalId" cssClass="error"/>
        </div>

        <div class="form-group field">
            <form:label path="email" class="col-md-4 control-label">Email:</form:label>
            <form:input path="email" placeholder="email" id="email"
                        pattern="^[\w!#$%&'*+/=?`{|}~^-]+(?:\.[\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,6}$"
                        title="simple@example.com" class="form-control" required="required"/>
            <form:errors path="email" cssClass="error"/>
        </div>
        <div class="form-group field">
            <form:label path="gender" class="col-md-4 control-label">Gender:</form:label>
            <form:select path="gender" id="gender" required="required" class="dropdown">
                <option value="">--</option>
                    <option value="male">male</option>
                    <option value="female">female</option>
            </form:select>
            <form:errors path="gender" cssClass="error"/>
        </div>
        <div class="form-group field">
            <form:label path="age" class="col-md-4 control-label">Age:</form:label>
            <form:input type="number" path="age" id="age" required="required" class="form-control"/>
            <form:errors path="age" cssClass="error"/>
        </div>

        <div class="form-group field">
            <form:label path="password" class="col-md-4 control-label">Password:</form:label>
            <form:password path="password" id="password" name="password" class="form-control"
                           pattern="\\A(?=.*[A-Z])(?=.*\\d)[a-zA-Z0-9]{8,16}\\z"
                           title="at least one uppercase letter, one digit; 8<length<16" placeholder="Enter Password"
                           required="required"/>
            <i class="far fa-eye btn-show-hide-pwd" data-for="password"></i>
            <form:errors path="password" cssClass="error" style="margin-left: 1.6vw"/>
        </div>
        <div class="form-group field">
            <form:password path="confirmPassword" placeholder="Re-Enter Password"
                           id="confirmPassword" class="form-control" required="required" cssStyle="margin-left: 5vw"/>
            <i class="far fa-eye btn-show-hide-pwd" data-for="confirmPassword"></i>
            <form:errors path="confirmPassword" cssClass="error" style="margin-left: 1.6vw"/>
        </div>

        <div class="span2">
            <form:button id="register" name="register" class="btn btn-primary btn-block button">Register</form:button>
        </div>
    </form:form>
</div>
</body>
<script>

    function isNumberKey(evt) {
        const charCode = (evt.which) ? evt.which : evt.keyCode;
        const isNumeric = (charCode > 47 && charCode < 58);
        if (!isNumeric) {
            window.alert("Just Digits Are Allowed");
            return isNumeric;
        }
    }

    function isAlphabetKey(evt) {
        const charCode = evt.keyCode;
        const isAlphabet = ((charCode >= 65 && charCode <= 90) || (charCode >= 97 && charCode <= 122) || charCode === 8 || charCode === 32);
        if (!isAlphabet) {
            window.alert("Just Alphabets Are Allowed");
            return false;
        }
    }

    function isAllowedCharacter(evt) {
        const charCode = (evt.which) ? evt.which : evt.keyCode;
        const isValidKey = ((charCode > 64 && charCode < 91) ||
            (charCode > 97 && charCode < 123) ||
            (charCode > 47 && charCode < 58));
        if (!isValidKey) {
            window.alert("Just Alphabets And Numbers Are Allowed");
            return false;
        }
    }

    function removeRequired(form) {
        $.each(form, function (key, value) {
            if (value.hasAttribute("required")) {
                value.removeAttribute("required");
            }
        });
    }

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
