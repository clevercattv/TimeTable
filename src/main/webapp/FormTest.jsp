<%--
  Created by IntelliJ IDEA.
  User: clever
  Date: 07.11.2019
  Time: 14:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
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
<div class="container">
    <div class="row">
        <div class="col-2">
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
        <div class="col-10">
            <div class="row">
                <table class="table" style="text-align:center">
                    <thead class="thead-light">
                    <tr>
                        <th scope="col">DAY</th>
                        <th scope="col">Time</th>
                        <c:forEach items="${timeTable.groups}" var="group">
                            <th scope="col"><c:out value="${group}"/></th>
                        </c:forEach>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${timeTable.dayOfWeek}" var="day">
                        <c:forEach items="${timeTable.lessonTime}" var="time">
                            <tr>
                                <th scope="col" style="position: relative">
                                    <div>
                                        <c:out value="${day}"/>
                                    </div>
                                </th>
                                <th>${time}</th>
                                <c:forEach items="${timeTable.groups}" var="group">
                                    <c:set var="isNotFindLesson" scope="page" value="${true}"/>
                                    <c:forEach items="${timeTable.lessons}" var="lesson">
                                        <c:if test="${day eq lesson.day and time eq lesson.number and group eq lesson.group}">
                                            <th>
                                                    <%--<div>${lesson.lessonTime}</div>--%>
                                                    <%--<div>${lesson.day}</div>--%>
                                                <div>${lesson.name}</div>
                                                <div>${lesson.teacher}</div>
                                                    <%--<div>${lesson.group}</div>--%>
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
            </div>
        </div>
    </div>

</div>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>
