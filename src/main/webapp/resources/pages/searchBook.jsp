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
    <title>search book</title>
</head>
<body>
<form>
    <button type="submit" class="btn btn-success btn-group btn-home" formaction="/admin">Home</button>
    <button type="submit" class="btn btn-success btn-group btn-menu btn-home" formaction="/admin/bookMenu">Book Menu
    </button>
    <button type="submit" class="btn btn-success btn-group btn-logout btn-home" formaction="/logout">Logout</button>
</form>
<div class="main-block">
    <form action="${pageContext.request.contextPath}/admin/books/searchProcess/1" id="search-form" method="Post"
          class="searchform">
        <table class="tableizer-table">
            <thead>
            <tr class="tableizer-firstrow">
                <th colspan="5">Search Book</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>
                    <label for="name"><strong>Name:</strong></label>
                    <input id="name" value="${book.name}" name="name" class="form-control"/>
                </td>
                <td>
                    <label for="isbn"><strong>ISBN:</strong></label>
                    <input id="isbn" value="${book.isbn}" name="isbn" class="form-control"/>
                </td>
                <td>
                    <label for="author.name"><strong>Author Name:</strong></label>
                    <input id="author.name" value="${book.author.name}" name="author.name" class="form-control"/>
                </td>
                <td>
                    <label for="author.family"><strong>Author Family:</strong></label>
                    <input id="author.family" value="${book.author.family}" name="author.family" class="form-control"/>
                </td>
                <td>
                    <button class="btn btn-group btn-primary" onclick="sendToSearch(${pageNumber})">Search</button>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
    <div id="search-result"></div>
    <div id="delete-btn"></div>
    <div class="pagination"></div>
</div>
</body>
<script>
    function sendToSearch(pageNumber) {
        let book = createJsonSearchObject();
        const request = new XMLHttpRequest();
        request.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                createResultTable(this.response);
            }
        };
        request.open("POST", "/admin/books/searchProcess/" + pageNumber, true);
        request.setRequestHeader("Content-type", "application/json");
        request.dataType = "json";
        request.responseType = "json";
        book = JSON.stringify(book);
        request.send(book);
    }

    function createResultTable(books) {
        let table = document.createElement('table');
        table.setAttribute("id", "result-table");
        table.setAttribute("class", "table table-hover table table-bordered table-striped");
        let i;
        table.insertRow(0).outerHTML =
            `<tr class="header">
                <th>Book Name</th>
                <th>Author Name</th>
                <th>Author Family</th>
                <th>ISBN</th>
            </tr>`;
        for (i = 0; i < Object.keys(books).length; i++) {
            const book = books[i];
            table.insertRow(i + 1).outerHTML =
                `<tr>
                    <td id="name` + i + `">` + book.name + `</td>
                    <td id="author.name` + i + `">` + book.author.name + `</td>
                    <td id="author.family` + i + `">` + book.author.family + `</td>
                    <td id="isbn` + i + `">` + book.isbn + `</td>
                </tr>`;
        }
        const deleteBtn = document.createElement('button');
        deleteBtn.setAttribute("class", "btn btn-group btn-danger btn-delete");
        deleteBtn.setAttribute("id", "btn-delete");
        deleteBtn.setAttribute("onclick", "sendToDelete()");
        deleteBtn.appendChild(document.createTextNode("Delete Selected Items"));
        document.getElementById("search-result").append(table);
        document.getElementById("delete-btn").append(deleteBtn);
    }

    function sendToDelete() {
        let listOfBooks = [];
        $('input[type=checkbox]').each(function () {
            if (this.checked)
                listOfBooks.push(createJsonObject($(this).attr("id")));
        });
        const request = new XMLHttpRequest();
        request.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                document.forms[0].submit();
            }
        };
        request.open("POST", "/admin/books/deleteBooks/${pageNumber}", true);
        request.setRequestHeader("Content-type", "application/json");
        request.dataType = "json";
        request.responseType = "text";
        listOfBooks = JSON.stringify(listOfBooks);
        request.send(listOfBooks);
    }

    function createJsonSearchObject() {
        return {
            "name": document.getElementById("name").value,
            "isbn": document.getElementById("isbn").value,
            "author": {
                "name": document.getElementById("author.name").value,
                "family": document.getElementById("author.family").value,
            }
        };
    }

    function createJsonObject(i) {
        return {
            "name": document.getElementById("name" + i).innerHTML,
            "isbn": document.getElementById("isbn" + i).innerHTML,
            "author": {
                "name": document.getElementById("author.name" + i).innerHTML,
                "family": document.getElementById("author.family" + i).innerHTML,
            }
        };
    }
</script>
</html>