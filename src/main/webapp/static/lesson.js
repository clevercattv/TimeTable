function updateModalData(id) {
    // var room = document.getElementById('item_' + id).children;
    // document.getElementById('updateModalTitle').textContent = 'Edit room : ' + room[0].textContent;
    // document.getElementById('updateRoomId').value = id;
    // document.getElementById('updateRoomName').value = room[0].textContent;
    // document.getElementById('updateRoomType').value = room[1].textContent;
    // $('#updateRoomError').css('display','none');
}

function deleteModalData(id) {
    // document.getElementById('deleteModalTitle').textContent = 'Delete room : ' + document.getElementById('item_' + id).children[0].textContent;
    // document.getElementById('deleteRoomId').value = id;
    // console.log({id:id})
    // console.log(document.getElementById('deleteRoomId').value)
    // $('#deleteRoomError').css('display','none');
}

function updateLesson() {
    var id = document.getElementById("updateRoomId").value;
    var name = document.getElementById("updateRoomName").value;
    var type = document.getElementById("updateRoomType").value;
    $.ajax({
        type: 'POST',
        url: '/timetable/lesson/update',
        data: {id: id, name: name, type: type},
        dataType: "text",
        success: function () {

        },
        error: function (xhr, ajaxOptions, thrownError) {

        }
    });
}

function removeItem() {
    var id = document.getElementById('deleteItemId').value;
    $.ajax({
        type: 'POST',
        url: '/timetable/room/delete',
        data: {id: id},
        dataType: "text",
        success: function () {
            $('#deleteItemModal').modal('hide')
            document.getElementById("item_" + id).remove();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            var error = $("#deleteItemError");
            error.css("display","block");
            error.text(xhr.responseText);
        }
    });
}