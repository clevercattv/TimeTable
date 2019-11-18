<%--
  Created by IntelliJ IDEA.
  User: clever
  Date: 16.11.2019
  Time: 17:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="shortcut icon" href="">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/main.css">
    <script src="${pageContext.request.contextPath}/static/JQuery.js"></script>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="${pageContext.request.contextPath}">SoftServeAcademy TimeTable</a>
    <div class="collapse navbar-collapse" id="navbar">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/timetable">Full TimeTable</a>
            </li>
            <li class="nav-item dropdown active">
                <a class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false" style="cursor: pointer">
                    Management
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/lesson">Lesson</a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/teacher">Teacher</a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/group">Group</a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/room">Room</a>
                </div>
            </li>
        </ul>
    </div>
</nav>
<div class="container">
    <div class="row">
        <div class="col-12 mt-3" style="text-align: center">
            <div>
                <div>
                    <div><h1>500</h1></div>
                    <div>Oops, something went wrong..</div>
                    <hr>
                    <div>
                        <p>
                            Go to homepage and try again later.<br>
                            If you think something is broken,<br>
                            report a problem to our email : <br>
                            clevercattv@gmail.com
                        </p>
                    </div>
                    <div>
                        <a href="${pageContext.request.contextPath}" class="btn btn-primary btn-lg active"
                           role="button" aria-pressed="true" style="border-radius: 2em!important">Go to homepage</a>
                        <a href="https://gmail.com/" class="btn btn-secondary btn-lg active"
                           role="button" aria-pressed="true" style="border-radius: 2em!important">
                            Report a problem
                            <em class="material-icons icon--button">email</em>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>
