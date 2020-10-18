<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://kit.fontawesome.com/dc1776693c.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="<c:url value="/resources/theme/css/styles/registerStyle.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/theme/css/styles/addBookStyle.css"/>">
    <title>add Book</title>
</head>
<body onload="checkMessage()">
<form>
    <button type="submit" class="btn btn-success btn-group btn-home" formaction="/admin">Home</button>
    <button type="submit" class="btn btn-success btn-group btn-menu btn-home" formaction="/admin/bookMenu">Book Menu
    </button>
</form>
<div class="main-block">
    <form:form modelAttribute="book" class="content" action="/admin/books/addBook-process" method="POST">
    <table class="tableizer-table">
        <thead>
        <tr class="tableizer-firstrow">
            <th colspan="2">Add Book Form</th>
            <th colspan="2" style="font-size: 18px; color: #00c42d" class="message">${message}</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td colspan="2"><form:errors path="name" cssClass="error left-error"/></td>
            <td colspan="2"><form:errors path="subject" cssClass="error right-error"/></td>
        </tr>
        <tr>
            <td><form:label path="name" class="col-md-4 control-label left-label">Book Name:</form:label></td>
            <td><form:input path="name" placeholder="name" type="text" cssClass="form-control left-input"
                            required="required"/></td>
            <td><form:label path="subject" class="col-md-4 control-label">Subject:</form:label></td>
            <td><form:input path="subject" placeholder="subject" type="text" cssClass="form-control"
                            pattern="^[a-zA-Z]+(([. ][a-zA-Z ])?[a-zA-Z]*)*$" title="Physics and Math"
                            required="required"/>
            </td>
        </tr>
        <tr>
            <td colspan="2"><form:errors path="author.name" cssClass="error left-error"/></td>
            <td colspan="2"><form:errors path="author.family" cssClass="error right-error"/></td>
        </tr>
        <tr>
            <td><form:label path="author.name" class="col-md-4 control-label">Author FirstName:</form:label></td>
            <td><form:input path="author.name" placeholder="author name" type="text" cssClass="form-control"
                            pattern="^[a-zA-Z]+(([a-zA-Z. ])?[a-zA-Z]*)*$" title="Robert C." required="required"/>
            <td><form:label path="author.family" class="col-md-4 control-label">Author Family:</form:label></td>
            <td><form:input path="author.family" placeholder="author family" type="text" cssClass="form-control"
                            pattern="^[a-zA-Z]+(([. ][a-zA-Z ])?[a-zA-Z]*)*$" title="Martin" required="required"/>
        </tr>
        <tr>
            <td colspan="2"><form:errors path="publisher" cssClass="error left-error"/></td>
            <td colspan="2"><form:errors path="isbn" cssClass="error right-error"/></td>
        </tr>
        <tr>
            <td><form:label path="publisher" class="col-md-4 control-label">Publisher:</form:label></td>
            <td><form:input path="publisher" placeholder="publisher" type="text" cssClass="form-control"
                            pattern="^[a-zA-Z0-9]+(([. ][a-zA-Z0-9 ])?[a-zA-Z0-9]*)*$" title="57, 22 bahman, etc "
                            required="required"/>
            <td><form:label path="isbn" class="col-md-4 control-label">ISBN:</form:label></td>
            <td><form:input path="isbn" placeholder="isbn" type="text" cssClass="form-control"
                            onkeypress="return isNumberKey(event)" required="required"/>
            </td>
        </tr>
        <tr>
            <td colspan="2"><form:errors path="numberOfPages" cssClass="error left-error"/></td>
            <td colspan="2"><form:errors path="publishDate" cssClass="error right-error"/></td>
        </tr>
        <tr>
            <td><form:label path="numberOfPages" class="col-md-4 control-label">Number Of Pages:</form:label></td>
            <td><form:input type="number" path="numberOfPages" placeholder="number of pages"
                            cssClass="form-control left-input" onkeypress="return isNumberKey(event)"
                            required=" required"/>
            </td>
            <td><form:label path="publishDate" class="col-md-4 control-label">Publish Date:</form:label></td>
            <td><form:input type="date" path="publishDate" placeholder="publish date" cssClass="form-control"
                            required=" required"/></td>
        </tr>
        <tr>
            <td colspan="2"><form:errors path="ageGroup" cssClass="error left-error"/></td>
            <td colspan="2"><form:errors path="number" cssClass="error right-error"/></td>
        </tr>
        <tr>
            <td><form:label path="ageGroup" class="col-md-4 control-label">Age Group:</form:label></td>
            <td><form:select path="ageGroup" id="role" required="required" class="dropdown">
                <option value="">--</option>
                <c:forEach items="${ageGroups}" var="ageGroup">
                    <option value="${ageGroup}">${ageGroup}</option>
                </c:forEach>
            </form:select></td>
            <td><form:label path="number" class="col-md-4 control-label">Number:</form:label></td>
            <td><form:input type="number" path="number" placeholder="number" class="form-control" required=" required"/>
            </td>
        </tr>
        <tr>
            <td colspan="4">
                <div class="span2">
                    <form:button id="register" class="btn btn-primary btn-block button">
                        Add Book
                    </form:button>
                </div>
            </td>
        </tr>
        </tbody>
    </table>
    </form:form>
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
        let pattern;
        const charCode = event.charCode ? event.charCode : event.keyCode;
        pattern = /[a-zA-Z]+([. ]*[a-zA-Z]+)*$/;
        const isValid = pattern.test(String.fromCharCode(charCode));
        if (!isValid) {
            window.alert("Just English Alphabets Are Allowed");
            return false;
        }
    }

    function checkMessage() {
        const message = "${message}";
        console.log(${message});
        console.log(message);
        if (!message)
            window.alert(message);
    }

</script>
</html>
