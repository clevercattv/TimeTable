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
