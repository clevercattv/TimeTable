function updateModalData(id) {
    var room = document.getElementById('room_' + id).children;
    document.getElementById('updateModalTitle').textContent = 'Edit room : ' + room[0].textContent;
    document.getElementById('updateRoomId').value = id;
    document.getElementById('updateRoomName').value = room[0].textContent;
    document.getElementById('updateRoomType').value = room[1].textContent;
}

function updateRoom() {
    var id = document.getElementById("updateRoomId").value;
    var name = document.getElementById("updateRoomName").value;
    var type = document.getElementById("updateRoomType").value;
    $.ajax({
        type: 'POST',
        url: '/timetable/room/update',
        data: {id: id, name: name, type: type},
        dataType: "text",
        success: function () {
            var room = document.getElementById("room_" + id).children;
            room[0].children[0].textContent = name;
            room[1].children[0].textContent = type;
            $('#updateRoomModal').modal('hide'); // Hide modal dialog
            $("#toastTitle").text("Success");
            $("#toastText").text("Successfully updated!");
            $("#toastMessage").toast('show');
        },
        error: function (xhr, ajaxOptions, thrownError) {
            $("#toastTitle").text("Update error message!");
            $("#toastText").text(xhr.responseText);
            $("#toastMessage").toast('show');
        }
    });
}

function removeRoom(id) {
    $.ajax({
        type: 'POST',
        url: '/timetable/room/delete',
        data: {id: id},
        dataType: "text",
        success: function () {
            document.getElementById("room_" + id).remove();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            $("#toastTitle").text("Remove error message!");
            $("#toastText").text(xhr.responseText);
            $("#toastMessage").toast('show');
        }
    });
}