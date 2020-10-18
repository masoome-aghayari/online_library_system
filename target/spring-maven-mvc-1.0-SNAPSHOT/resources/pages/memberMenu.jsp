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
    <title>member menu</title>
</head>
<body>
<div class="main-block">
    <form>
        <div class="span2">
            <button formaction="/admin/members/search/1" class="btn btn-primary btn-block"
                    type="submit">Search Member
            </button>
            <button formaction="/admin/members/borrow" class="btn btn-success btn-block"
                    type="submit">Borrow Book
            </button>
        </div>
    </form>
</div>
</body>
</html>
