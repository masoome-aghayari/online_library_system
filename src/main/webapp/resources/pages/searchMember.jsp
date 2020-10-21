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
        const editRequest = new XMLHttpRequest();
        editRequest.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                console.log(this.responseText);
                console.log(this.response);
                window.alert(this.responseText);
            }
        };
        editRequest.open("POST", "/admin/members/editMember", true);
        editRequest.setRequestHeader("Content-type", "application/json");
        editRequest.dataType = "json";
        editRequest.responseType = "text";
        member = JSON.stringify(member);
        editRequest.send(member);
    }

    function sendToDelete() {
        let checkedMembers = collectCheckedMembers();
        const deleteRequest = new XMLHttpRequest();
        deleteRequest.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                sendToSearch(${pageNumber});
            }
        };
        deleteRequest.open("POST", "/admin/members/deleteMembers", true);
        deleteRequest.setRequestHeader("Content-type", "application/json");
        deleteRequest.dataType = "json";
        deleteRequest.responseType = "json";
        checkedMembers = JSON.stringify(checkedMembers);
        deleteRequest.send(checkedMembers);
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
                <th>FirstName</th>
                <th>LastName</th>
                <th>National Id</th>
                <th>MobileNumber</th>
                <th>Email</th>
            </tr>`;
        for (i = 0; i < Object.keys(members).length; i++) {
            const member = members[i];
            table.insertRow(i + 1).outerHTML =
                `<tr>
                    <td><input id="firstName` + member.id + `" type="text" class="form-control" value="` + member.firstName + `"
                    onkeypress="return isAlphabetKey(event)" required/></td>
                    <td><input id="lastName` + member.id + `" type="text" class="form-control" value="` + member.lastName + `"
                    onkeypress="return isAlphabetKey(event)" required/></td>
                    <td><input id="nationalId` + member.id + `" type="text" class="form-control" value="` + member.nationalId + `"
                    onkeypress="return isNumberKey(event)" required/></td>
                    <td><input id="mobileNumber` + member.id + `" type="text" class="form-control" value="` + member.mobileNumber + `"
                    pattern="9((0[1-3]|5)|(1[0-9])|(2[0-2])|(3(1|[3-9]))|(9[0-1]))[0-9]{7}" title="9197417221"
                    onkeypress="return isNumberKey(event)" required/></td>
                    <td><input id="email` + member.id + `" type="text" class="form-control" value="` + member.email + `"
                    pattern="^[\\w!#$%&'*+/=?\`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?\`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"
                    title="simple@example.com" required/></td>
                    <td hidden id="` + member.id + `">` + member.id + `</td>
                    <td>
                        <label class="container"><input type="checkbox" id="` + member.id + `">
                        <span class="checkmark"></span>
                        </label>
                    </td>
                    <td>
                        <input type="button" class="btn btn-group btn-primary" onclick="sendToEdit(` + member.id + `)" value="Update"/>
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

    function createJsonIntegerObject(id) {
        return {
            "userId": id
        };
    }

    function createJsonObject(id) {
        return {
            "id": id,
            "firstName": document.getElementById("firstName" + id).value,
            "lastName": document.getElementById("lastName" + id).value,
            "nationalId": document.getElementById("nationalId" + id).value,
            "mobileNumber": document.getElementById("mobileNumber" + id).value,
            "email": document.getElementById("email" + id).value
        }
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

    function collectCheckedMembers() {
        let listOfMembers = [];
        $('input[type=checkbox]').each(function () {
            if (this.checked)
                listOfMembers.push(createJsonIntegerObject($(this).attr("id")));
        });
        return listOfMembers;
    }

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
</script>
</html>