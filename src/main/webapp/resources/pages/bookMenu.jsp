<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="<c:url value="/resources/theme/css/styles/homeStyle.css"/>">
    <title>book menu</title>
    <style>
        body {
            background: url(/resources/theme/css/pictures/bookMenu_background.jpg) no-repeat fixed;
            background-size: cover;
            background-position-y: bottom;
            background-position-x: right;
        }

        .main-block {
            margin: 25vh 12vw;
        }

        .btn-addBook {
            border: none;
            background-color: #f30054;
        }

        .btn-searchBook {
            border: none;
            background-color: #00c42d;
        }
    </style>
</head>
<body>
<div class="main-block">
    <form>
        <div class="span2">
            <button formaction="/admin/books/addBook" class="btn btn-primary btn-block btn-addBook"
                    type="submit">Add New Book
            </button>
            <button formaction="/admin/books/search/1" class="btn btn-success btn-block btn-searchBook"
                    type="submit">Search Book
            </button>
        </div>
    </form>
</div>
</body>
</html>