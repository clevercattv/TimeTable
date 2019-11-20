<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<head>
    <title>Managment - rooms</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="icon" href="data:;base64,iVBORw0KGgo=">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/Table.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/main.css">
    <script src="${pageContext.request.contextPath}/static/JQuery.js"></script>
    <script src="${pageContext.request.contextPath}/static/CommonModal.js"></script>
    <script src="${pageContext.request.contextPath}/static/RoomModal.js"></script>
    <style>
        table.table tr th:first-child {
            width: 60px;
        }
        table.table tr th:last-child {
            width: 80px;
        }
    </style>
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
                <a class="nav-link dropdown-toggle pointer" id="navbarDropdown" role="button" data-toggle="dropdown"
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
<div class="container">
    <div class="row">
        <div class="col-12 mt-3">
            <div class="row table-top">
                <div class="col-3">
                    <h2 class="text-white font--weight--700">Rooms</h2>
                </div>
                <div class="col-9 icon--right">
                    <em class="material-icons icon--button mt-1 text-success font--size--2em"
                        data-toggle="modal" data-target="#itemFucModal" onclick="fillCreateRoomModal()">add_box</em>
                    <em class="material-icons icon--button mt-1 text-warning font--size--2em" data-toggle="modal"
                        data-target="#itemFucModal" onclick="fillFilterRoomModal('${param.fName}','${param.fType}')">filter_list</em>
                </div>
            </div>
            <div class="row">
                <table id="itemsTable" class="table table-sm table-striped table-hover">
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
                            <td><a>${room.name}</a></td>
                            <td><a>${room.type}</a></td>
                            <td>
                                <em class="material-icons icon--button" data-toggle="modal"
                                    data-target="#itemFucModal"
                                    onclick="fillUpdateRoomModal(${room.id})">edit</em>
                                <em class="material-icons icon--button color--red" data-toggle="modal"
                                    data-target="#deleteItemModal"
                                    onclick="fillDeleteModalData(${room.id},'room')">delete</em>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${fn:length(response.rooms) le 0}">
                        <tr>
                            <td colspan="3"><a>List is empty!</a></td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </div>
            <!-- Filter/Update/Create MODAL -->
            <div class="modal fade" id="itemFucModal" tabindex="-1" role="dialog" aria-labelledby="itemFucModalTitle"
                 aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="itemFucModalTitle"></h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body d-flex">
                            <div class="row">
                                <div class="form-group col-4">
                                    <label for="itemFucName" class="mr-2 text-dark input--label">Name</label>
                                    <input type="text" class="form-control my-1 mr-2"
                                           id="itemFucName" placeholder="Room name">
                                </div>
                                <div class="form-group col-4">
                                    <label for="itemFucType" class="mr-2 text-dark input--label">Type</label>
                                    <select class="form-control my-1 mr-2" id="itemFucType">
                                        <c:forEach items="${response.types}" var="type">
                                            <option>${type}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group col-4 d-flex align-self-end">
                                    <button onclick="modalAction(createRoom,updateRoom,filterRoom)"
                                            class="btn btn-primary my-1 mr-2" id="itemFucSubmit">Edit
                                    </button>
                                    <button onclick="window.location.href = '?'"
                                            class="btn btn-warning my-1 mr-2" id="itemFucClear">Clear
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div class="alert alert-danger mx-2" role="alert" id="fucModalError"
                             style="display: none"></div>
                    </div>
                </div>
            </div>
            <!-- DELETE MODAL -->
            <div class="modal fade" id="deleteItemModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalTitle"
                 aria-hidden="true">
                <div class="modal-dialog modal-sm" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="deleteModalTitle"></h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body d-flex">
                            <button class="btn btn-danger my-1 mr-2 w-50"
                                    onclick="removeItem('room_','/timetable/room')">OK
                            </button>
                            <button class="btn btn-primary my-1 mr-2 w-50"
                                    data-dismiss="modal" aria-label="Close">Cancel
                            </button>
                        </div>
                        <div class="alert alert-danger mx-2" role="alert" id="deleteItemError"
                             style="display: none"></div>
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
