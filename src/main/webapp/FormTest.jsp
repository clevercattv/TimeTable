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
    <style>
        .vericaltext {
            width: 1px;
            word-wrap: break-word;
            white-space: pre-wrap;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-2">
            Selectable options
        </div>
        <div class="col-10">
            <ul class="nav nav-tabs" id="myTab" role="tablist">
                <li class="nav-item">
                    <a class="nav-link active" id="today-tab" data-toggle="tab" href="#today"
                       role="tab" aria-controls="today" aria-selected="true">Today</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="tomorrow-tab" data-toggle="tab" href="#tomorrow"
                       role="tab" aria-controls="tomorrow" aria-selected="false">Tomorrow</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="contact-tab" data-toggle="tab" href="#week"
                       role="tab" aria-controls="week" aria-selected="false">For a week</a>
                </li>
            </ul>
            <div class="tab-content" id="myTabContent">
                <div class="tab-pane fade show active" id="today" role="tabpanel" aria-labelledby="today-tab">test1
                </div>
                <div class="tab-pane fade" id="tomorrow" role="tabpanel" aria-labelledby="tomorrow-tab">test2</div>
                <div class="tab-pane fade" id="week" role="tabpanel" aria-labelledby="contact-tab">test3</div>
            </div>
            <div class="row">
                <%--${timeTable}--%>
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
                    <c:forEach items="${timeTable.dayOfWeeks}" var="day">
                        <tr>
                            <th rowspan="${fn:length(timeTable.lessonTime)}" scope="col" style="position: relative">
                                <div class="vericaltext" style="position: absolute; left: 45%">
                                    <c:out value="${day}"/>
                                </div>
                            </th>
                            <%--<th>${timeTable.lessonTime[0]}</th>--%>
                            <c:forEach items="${timeTable.groups}" var="group">
                                <c:set var="isNotFindLesson" scope="page" value="${true}"/>
                                <c:forEach items="${timeTable.lessons}" var="lesson">
                                    <c:if test="${group eq lesson.group}">
                                        <th>${lesson.name}</th>
                                        <c:set var="isNotFindLesson" scope="page" value="${false}"/>
                                        <%-- todo delete lesson from lessons --%>
                                    </c:if>
                                </c:forEach>
                                <c:if test="${isNotFindLesson}">
                                    <th>space <br> ${group} <br> ${isNotFindLesson}</th>
                                </c:if>
                            </c:forEach>
                        </tr>
                        <c:forEach items="${timeTable.lessonTime}" begin="1" var="time">
                            <tr>
                                <th><c:out value="${time}"/></th>
                                <c:forEach items="${timeTable.groups}" var="group">
                                    <th><c:out value="${group}"/></th>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                    </c:forEach>

                    <%--<tr>--%>
                    <%--<th rowspan="4" scope="col" style="position: relative">--%>
                    <%--<div class="vericaltext" style="position: absolute; left: 45%">Monday</div>--%>
                    <%--</th>--%>
                    <%--<th>Monday</th>--%>
                    <%--<th>Tuesday</th>--%>
                    <%--<th>Wednesday</th>--%>
                    <%--<th>Thursday</th>--%>
                    <%--<th>Friday</th>--%>
                    <%--<th>Friday</th>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                    <%--<th>Monday</th>--%>
                    <%--<th>Tuesday</th>--%>
                    <%--<th>Wednesday</th>--%>
                    <%--<th>Thursday</th>--%>
                    <%--<th>Friday</th>--%>
                    <%--<th>Friday</th>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                    <%--<th>Monday</th>--%>
                    <%--<th>Tuesday</th>--%>
                    <%--<th>Wednesday</th>--%>
                    <%--<th>Thursday</th>--%>
                    <%--<th>Friday</th>--%>
                    <%--<th>Friday</th>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                    <%--<th>Monday</th>--%>
                    <%--<th>Tuesday</th>--%>
                    <%--<th>Wednesday</th>--%>
                    <%--<th>Thursday</th>--%>
                    <%--<th>Friday</th>--%>
                    <%--</tr>--%>
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
