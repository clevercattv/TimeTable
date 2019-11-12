<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/Table.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/main.css">
    <script src="${pageContext.request.contextPath}/static/JQuery.js"></script>
    <script src="${pageContext.request.contextPath}/static/room.js"></script>
    <c:if test="${not empty complete}">
        <script type="text/javascript">
            $(document).ready(function(){
                $("#toastTitle").text("Success");
                $("#toastText").text('${complete}');
                $("#toastMessage").toast('show');
            });
        </script>
    </c:if>
    <c:if test="${not empty error}">
        <script type="text/javascript">
            $(document).ready(function(){
                $("#toastTitle").text("Error");
                $("#toastText").text('${error}');
                $("#toastMessage").toast('show');
            });
        </script>
    </c:if>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="${pageContext.request.contextPath}">SoftServeAcademy TimeTable</a>
    <div class="collapse navbar-collapse" id="navbar">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/timetable">Full TimeTable</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Custom TimeTable</a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">
                    CRUD
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="#">Lesson</a>
                    <a class="dropdown-item" href="#">Teacher</a>
                    <a class="dropdown-item" href="#">Group</a>
                    <a class="dropdown-item" href="#">Room</a>
                </div>
            </li>
        </ul>
    </div>
</nav>
<div class="container">
    <div class="row">
        <div class="col-12 mt-2" style="background: white">
            <div class="row" style="background: #4b5366; border: 1px solid #4b5366; border-radius: 2px;">
                <div class="col-3">
                    <h2 class="text-white" style="font-weight: 700">Rooms</h2>
                </div>
                <div class="col-9" id="addRoom">
                    <form action="${pageContext.request.contextPath}/room" method="post" style="display: flex">
                        <input type="text" class="form-control my-1 mr-2" id="roomName" name="name"
                               placeholder="Room name">
                        <select class="form-control my-1 mr-2" id="roomType" name="type">
                            <c:forEach items="${response.types}" var="type">
                                <option>${type}</option>
                            </c:forEach>
                        </select>
                        <button class="btn btn-primary my-1 mr-2" type="submit">Add</button>
                    </form>
                </div>
            </div>
            <table id="roomsTable" class="table table-sm table-striped table-hover">
                <caption></caption>
                <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Type</th>
                    <th scope="col">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${response.rooms}" var="room">
                    <tr id="room_${room.id}">
                        <td><a href="#">${room.name}</a></td>
                        <td><a href="#">${room.type}</a></td>
                        <td>
                            <em class="material-icons icon--button" data-toggle="modal" data-target="#updateRoomModal"
                                onclick="updateModalData(${room.id})">edit</em>
                            <em class="material-icons icon--button" onclick="removeRoom(${room.id})">delete</em>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- MODAL -->
            <div class="modal fade" id="updateRoomModal" tabindex="-1" role="dialog" aria-labelledby="updateModalTitle"
                 aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="updateModalTitle"></h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body" style="display: flex">
                            <input type="hidden" id="updateRoomId">
                            <input type="text" class="form-control my-1 mr-2" id="updateRoomName"
                                   placeholder="Room name">
                            <select class="form-control my-1 mr-2" id="updateRoomType">
                                <c:forEach items="${response.types}" var="type">
                                    <option>${type}</option>
                                </c:forEach>
                            </select>
                            <button onclick="updateRoom()" class="btn btn-primary my-1 mr-2">Edit</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- TOAST -->
<div  style="position: absolute; bottom: 1vh; right: 1vw;">
    <div id="toastMessage" class="toast" role="alert" aria-live="assertive" aria-atomic="true" data-autohide="false">
        <div class="toast-header">
            <strong class="mr-auto" id="toastTitle">Bootstrap</strong>
            <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="toast-body" id="toastText">
            Hello, world! This is a toast message.
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
