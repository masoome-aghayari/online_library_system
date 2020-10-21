<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://kit.fontawesome.com/dc1776693c.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="<c:url value="/resources/theme/css/styles/registerStyle.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/theme/css/styles/addBookStyle.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/theme/css/styles/defaultButtonsStyle.css"/>">
    <link rel="stylesheet" href="<c:url value="/resources/theme/css/styles/searchBookStyle.css"/>">
    <title>lending book</title>
</head>
<body>
<form>
    <button type="submit" class="btn btn-success btn-group btn-home" formaction="/admin">Home</button>
    <button type="submit" class="btn btn-success btn-group btn-menu btn-home" formaction="/admin/books/bookMenu">Book
        Menu
    </button>
    <button type="submit" class="btn btn-success btn-group btn-logout btn-home" formaction="/logout">Logout</button>
</form>
<div class="main-block">
    <form id="search-form" method="Post" class="searchform">
        <table class="tableizer-table">
            <thead>
            <tr class="tableizer-firstrow">
                <th colspan="5">Lend Book</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>
                    <label for="nationalId"><strong>Member NationalId:</strong></label>
                    <input id="nationalId" value="${lendItem.member.nationalId}" name="nationalId" class="form-control"/>
                </td>
                <td>
                    <label for="isbn"><strong>Book ISBN:</strong></label>
                    <input id="isbn" value="${lendItem.book.isbn}" name="isbn" class="form-control"/>
                </td>
                <td>
                    <input type="button" class="btn btn-group btn-primary" onclick="sendToLend()" value="Search"/>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</body>
</html>
