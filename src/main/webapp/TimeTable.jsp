<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/Table.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/main.css">
    <script src="${pageContext.request.contextPath}/static/JQuery.js"></script>
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
                    CRUD
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
                    <h2 class="text-white" style="font-weight: 700">Time table</h2>
                </div>
                <div class="col-9" style="align-self: center; text-align: right">
                    <em class="material-icons icon--button mt-1 text-warning" style="font-size: 2em;">filter_list</em>
                </div>
            </div>
            <div class="row">
                <table  class="table table-sm table-striped table-hover" id="timeTable" style="background: white">
                    <caption></caption>
                    <thead id="tableHead">
                    <tr>
                        <th scope="col">DAY</th>
                        <th scope="col">Time</th>
                        <c:forEach items="${timeTable.groups}" var="group">
                            <th scope="col"><c:out value="${group}"/></th>
                        </c:forEach>
                    </tr>
                    </thead>
                    <tbody id="tableBody">
                    <c:forEach items="${timeTable.dayOfWeek}" var="day">
                        <c:forEach items="${timeTable.lessonTime}" var="time">
                            <tr>
                                <th scope="col" style="position: relative; width: 1%">${day}</th>
                                <th scope="col" style="width: 1%">${time}</th>
                                <c:forEach items="${timeTable.groups}" var="group">
                                    <c:set var="isNotFindLesson" scope="page" value="${true}"/>
                                    <c:forEach items="${timeTable.lessons}" var="lesson">
                                        <c:if test="${day eq lesson.day and time eq lesson.number and group eq lesson.group}">
                                            <th>
                                                <div>${lesson.name}</div>
                                                <div>${lesson.teacher}</div>
                                                <div>${lesson.room}</div>
                                            </th>
                                            <c:set var="isNotFindLesson" scope="page" value="${false}"/>
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${isNotFindLesson}">
                                        <th></th>
                                    </c:if>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                    </tbody>
                </table>
                <script>
                    $(document).ready(function () {
                        Array.from(document.getElementById("tableBody").children)
                            .forEach(function (value) {
                                var childrens = value.children;
                                for (var i = 2; i < childrens.length; i++) {
                                    if (childrens[i].innerHTML.length > 0) {
                                        return;
                                    }
                                }
                                value.parentNode.removeChild(value);
                            })
                        let data = ${timeTableJson};
                        let days = [];
                        let time = [];
                        let groups = [];
                        Array.from(data.lessons).forEach(e => {
                            days.push(e.day);
                            time.push(e.number);
                            groups.push(e.group);
                        })
                        days = [...new Set(days)];
                        time = [...new Set(time)];
                        groups = [...new Set(groups)];
                        console.log(data);
                        console.log({days,time,groups});

                        let tableHead = document.getElementById('tableHead');
                        var tr = document.createElement('TR');
                        tableHead.appendChild(tr);
                        var thDay = document.createElement('TD');
                        thDay.scope="col";
                        thDay.innerText = "Day";
                        var thTime = document.createElement('TD');
                        thTime.innerText = "Time";
                        thTime.scope="col";

                    // <tr>
                    //     <th scope="col">DAY</th>
                    //         <th scope="col">Time</th>
                            <%--<c:forEach items="${timeTable.groups}" var="group">--%>
                            <%--<th scope="col"><c:out value="${group}"/></th>--%>
                            <%--</c:forEach>--%>
                            // </tr>
                        let tableBody = document.getElementById('tableBody');
                        days.forEach(d => {
                            time.forEach(t => {
                                groups.forEach(g => {

                                })
                            })
                        })
                        for (var i = 0; i < 3; i++) {
                            var tr = document.createElement('TR');
                            tableBody.appendChild(tr);

                            for (var j = 0; j < 4; j++) {
                                var td = document.createElement('TD');
                                td.width = '75';
                                td.appendChild(document.createTextNode("Cell " + i + "," + j));
                                tr.appendChild(td);
                            }
                        }
                        myTableDiv.appendChild(table);

                    })
                </script>
            </div>
        </div>
        <div>
            <form> <%-- todo fill form data --%>
                <div class="form-group">
                    <label for="dayOfWeek">Day of week</label>
                    <select multiple class="form-control" id="dayOfWeek">
                        <c:forEach items="${filter.dayOfWeek}" var="day">
                            <option>${day}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="groups">Group</label>
                    <select multiple class="form-control" id="groups">
                        <c:forEach items="${filter.groups}" var="group">
                            <option>${group}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="teachers">Teacher</label>
                    <select multiple class="form-control" id="teachers">
                        <c:forEach items="${filter.teachers}" var="teacher">
                            <option>${teacher}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="rooms">Room</label>
                    <select multiple class="form-control" id="rooms">
                        <c:forEach items="${filter.rooms}" var="room">
                            <option>${room}</option>
                        </c:forEach>
                    </select>
                </div>
                <button class="btn btn-primary my-1 mr-2" type="submit" style="width: 100%">Filter</button>
            </form>
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
