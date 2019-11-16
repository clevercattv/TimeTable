function fillCreateRoomModal() {
    document.getElementById('itemFucModalTitle').textContent = 'Create room';
    document.getElementById('itemFucSubmit').innerText = "Add";
    fucId = "";
    document.getElementById('itemFucName').value = "";
    let type = document.getElementById('itemFucType');
    type.value = type.children[0].value;
    $('#fucModalError').css('display', 'none');
    $('#itemFucClear').css('display', 'none');
    fucAction = 'CREATE';
}

function fillFilterRoomModal(name, type) {
    document.getElementById('itemFucModalTitle').textContent = 'Filter rooms';
    document.getElementById('itemFucSubmit').innerText = "Find";
    document.getElementById('itemFucName').value = name;
    document.getElementById('itemFucType').value = type;
    $('#fucModalError').css('display', 'none');
    $('#itemFucClear').css('display', 'inline-block');
    fucAction = 'FILTER'
}

function fillUpdateRoomModal(id) {
    let room = document.getElementById('room_' + id).children;
    document.getElementById('itemFucModalTitle').textContent = 'Edit room : ' + room[0].textContent;
    document.getElementById('itemFucSubmit').innerText = "Change";
    fucId = id;
    document.getElementById('itemFucName').value = room[0].textContent;
    document.getElementById('itemFucType').value = room[1].textContent;
    $('#fucModalError').css('display', 'none');
    $('#itemFucClear').css('display', 'none');
    fucAction = 'UPDATE'
}

function filterRoom() {
    let searchParams = new URLSearchParams(window.location.search);
    searchParams.set("fName", document.getElementById('itemFucName').value);
    searchParams.set("fType", document.getElementById('itemFucType').value);
    window.location.href = "?" + searchParams.toString();
}

function updateRoom() {
    let name = document.getElementById("itemFucName").value;
    let type = document.getElementById("itemFucType").value;
    $.ajax({
        type: 'POST',
        url: '/timetable/room',
        data: {method: 'PUT', id: fucId, name: name, type: type},
        dataType: "text",
        success: function () {
            let room = document.getElementById("room_" + fucId).children;
            room[0].children[0].textContent = name;
            room[1].children[0].textContent = type;
            $('#itemFucModal').modal('hide'); // Hide modal dialog
        },
        error: function (xhr, ajaxOptions, thrownError) {
            let error = $('#fucModalError');
            error.css('display', 'block');
            let text = xhr.responseText;
            if (text.charAt(0) === '['){
                text = text.substring(1, text.length - 1)
            }
            error.text(text);
        }
    });
}

function createRoom() {
    let name = document.getElementById("itemFucName").value;
    let type = document.getElementById("itemFucType").value;
    $.ajax({
        type: 'POST',
        url: '/timetable/room',
        data: {method: 'POST', name: name, type: type},
        dataType: "text",
        success: function () {
            location.reload();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            let error = $('#fucModalError');
            error.css('display', 'block');
            let text = xhr.responseText;
            if (text.charAt(0) === '['){
                text = text.substring(1, text.length - 1)
            }
            error.text(text);
        }
    });
}