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
    <title>search member</title>
</head>
<body>
<form>
    <button type="submit" class="btn btn-success btn-group btn-home" formaction="/admin">Home</button>
    <button type="submit" class="btn btn-success btn-group btn-logout btn-home" formaction="/logout">Logout</button>
</form>
<div class="main-block">
    <form id="search-form" method="Post" class="searchform">
        <table class="tableizer-table">
            <thead>
            <tr class="tableizer-firstrow">
                <th colspan="5">Search Member</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>
                    <label for="firstName"><strong>FirstName:</strong></label>
                    <input id="firstName" value="${member.firstName}" name="firstName" class="form-control"/>
                </td>
                <td>
                    <label for="lastName"><strong>LastName:</strong></label>
                    <input id="lastName" value="${member.lastName}" name="lastName" class="form-control"/>
                </td>
                <td>
                    <label for="nationalId"><strong>National Id:</strong></label>
                    <input id="nationalId" value="${member.nationalId}" name="nationalId" class="form-control"/>
                </td>
                <td>
                    <input type="button" class="btn btn-group btn-primary" onclick="sendToSearch(${pageNumber})"
                           value="Search"/>
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
        let member = createJsonSearchObject();
        const searchRequest = new XMLHttpRequest();
        searchRequest.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                showResult(this.response);
            }
        };
        searchRequest.open("POST", "/admin/members/searchProcess/" + pageNumber, true);
        searchRequest.setRequestHeader("Content-type", "application/json");
        searchRequest.dataType = "json";
        searchRequest.responseType = "json";
        member = JSON.stringify(member);
        searchRequest.send(member);
    }

    function sendToEdit(id) {
        let member = createJsonObject(id);
        const request = new XMLHttpRequest();
        request.open("POST", "/admin/members/editMember", true);
        request.dataType = "json";
        request.responseType = "json";
        member = JSON.stringify(member);
        request.send(member);
    }

    function sendToDelete() {
        let checkedMembers = collectCheckedBooks();
        const request = new XMLHttpRequest();
        request.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                document.forms[0].submit();
            }
        };
        request.open("POST", "/admin/books/deleteBooks/${pageNumber}", true);
        request.setRequestHeader("Content-type", "application/json");
        request.dataType = "json";
        request.responseType = "json";
        checkedMembers = JSON.stringify(checkedMembers);
        request.send(checkedMembers);
    }

    function showResult(members) {
        removePreviousResult();
        if (typeof members === 'undefined' || members == null) {
            removeDeleteButton();
            createResultText();
        } else {
            createResultTable(members);
            if (!isExistsDeleteButton())
                createDeleteButton();
        }
    }

    function createResultText() {
        let resultParagraph = document.createElement('p');
        resultParagraph.setAttribute("class", "result-text");
        resultParagraph.setAttribute("id", "result");
        resultParagraph.innerHTML = "No Result Found";
        document.getElementById("search-result").append(resultParagraph);
    }

    function createResultTable(members) {
        let table = document.createElement('table');
        table.setAttribute("id", "result");
        table.setAttribute("class", "table table-hover table table-bordered table-striped");
        let i;
        table.insertRow(0).outerHTML =
            `<tr class="header">
                <th>Member FirstName</th>
                <th>Member LastName</th>
                <th>NationalId</th>
                <th>MobileNumber</th>
                <th>Email</th>
            </tr>`;
        for (i = 0; i < Object.keys(members).length; i++) {
            const member = members[i];
            table.insertRow(i + 1).outerHTML =
                `<tr>
                    <td id="firstName` + i + `">` + member.firstName + `</td>
                    <td id="lastName` + i + `">` + member.lastName + `</td>
                    <td id="nationalId` + i + `">` + member.nationalId + `</td>
                    <td id="mobileNumber` + i + `">` + member.mobileNumber + `</td>
                    <td id="email` + i + `">` + member.email + `</td>
                    <td hidden id="id` + i + `">` + member.id + `</td>
                    <td>
                        <label class="container"><input type="checkbox" id="` + i + `">
                        <span class="checkmark"></span>
                        </label>
                    </td>
                    <td>
                        <button class="btn btn-group btn-primary" onclick="sendToEdit(` + i + `)">Edit</button>
                    </td>
                </tr>`;
        }
        document.getElementById("search-result").append(table);
    }

    function createJsonSearchObject() {
        return {
            "firstName": document.getElementById("firstName").value,
            "lastName": document.getElementById("lastName").value,
            "nationalId": document.getElementById("nationalId").value
        };
    }

    function createJsonObject(i) {
        return {
            "id": document.getElementById("id" + i).innerHTML,
            "firstName": document.getElementById("firstName" + i).innerHTML,
            "lastName": document.getElementById("lastName" + i).innerHTML,
            "nationalId": document.getElementById("nationalId" + i).innerHTML,
        };
    }

    function removePreviousResult() {
        let previousResult = document.getElementById("result");
        if (typeof (previousResult) != 'undefined' && previousResult != null) {
            document.getElementById("search-result").removeChild(previousResult);
        }
    }

    function isExistsDeleteButton() {
        let btnDelete = document.getElementById("btn-delete");
        return typeof (btnDelete) != 'undefined' && btnDelete != null;
    }

    function removeDeleteButton() {
        if (isExistsDeleteButton())
            document.getElementById("btn-delete").remove();
    }

    function createDeleteButton() {
        const deleteBtn = document.createElement('button');
        deleteBtn.setAttribute("class", "btn btn-group btn-danger btn-delete");
        deleteBtn.setAttribute("id", "btn-delete");
        deleteBtn.setAttribute("onclick", "sendToDelete()");
        deleteBtn.appendChild(document.createTextNode("Delete Selected Items"));
        document.getElementById("delete-btn").append(deleteBtn);
    }

    function collectCheckedBooks() {
        let listOfBooks = [];
        $('input[type=checkbox]').each(function () {
            if (this.checked)
                listOfBooks.push(createJsonObject($(this).attr("id")));
        });
        return listOfBooks;
    }
</script>
</html>