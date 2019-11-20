<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="icon" href="data:;base64,iVBORw0KGgo=">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/Table.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/main.css">
    <script src="${pageContext.request.contextPath}/static/JQuery.js"></script>
    <script src="${pageContext.request.contextPath}/static/TimeTable.js"></script>
    <script>$(document).ready(() => buildTimeTable(JSON.parse('${timeTable}')))</script>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="${pageContext.request.contextPath}">SoftServeAcademy TimeTable</a>
    <div class="collapse navbar-collapse" id="navbar">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/timetable">Full TimeTable</a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">
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
<div class="container-fluid">
    <div class="row mx-2 mt-2">
        <div class="col-12">
            <div class="row table-top">
                <div class="col-3">
                    <h2 class="text-white font--weight--700">Time table</h2>
                </div>
                <div class="col-9 align-self-center text-right">
                    <em class="material-icons icon--button mt-1 text-warning font--size--2em"
                        data-toggle="modal" data-target="#filterModal">filter_list</em>
                </div>
            </div>
            <div class="row">
                <table class="table table-sm table-striped table-hover centered timetable" id="timeTable">
                    <caption></caption>
                    <thead id="tableHead"><tr><th scope="col"></th></tr></thead>
                    <tbody id="tableBody"></tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="modal fade" id="filterModal" tabindex="-1" role="dialog" aria-labelledby="filterModalTitle"
         aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="filterModalTitle">Filter</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body d-flex">
                    <div class="row">
                        <div class="form-group col-4">
                            <label for="lessonName" class="mr-2 text-dark input--label">Lesson</label>
                            <input type="text" class="createRoom form-control my-1 mr-2" id="lessonName" name="name">
                        </div>
                        <div class="form-group col-4">
                            <label for="lessonTime">Time</label>
                            <select multiple class="form-control" id="lessonTime"></select>
                        </div>
                        <div class="form-group col-4">
                            <label for="dayOfWeek">Day of week</label>
                            <select multiple class="form-control" id="dayOfWeek"></select>
                        </div>
                        <div class="form-group col-4">
                            <label for="teachers">Teacher</label>
                            <select multiple class="form-control" id="teachers"></select>
                        </div>
                        <div class="form-group col-4">
                            <label for="groups">Group</label>
                            <select multiple class="form-control" id="groups"></select>
                        </div>
                        <div class="form-group col-4">
                            <label for="rooms">Room</label>
                            <select multiple class="form-control" id="rooms"></select>
                        </div>
                        <div class="form-group col-4 d-flex align-self-end">
                            <button class="btn btn-primary my-1 mr-2 w-100" onclick="filterTimeTable()">Filter</button>
                        </div>
                        <div class="form-group col-4 d-flex align-self-end">
                            <button class="btn btn-warning my-1 mr-2 w-100" onclick="clearFilter()">Clear</button>
                        </div>
                    </div>
                </div>
                <div class="alert alert-danger mx-2" role="alert" id="filterModalError" style="display: none"></div>
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
