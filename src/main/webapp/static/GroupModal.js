function fillCreateGroupModal() {
    document.getElementById('itemFucModalTitle').textContent = 'Create group';
    document.getElementById('itemFucSubmit').innerText = "Add";
    fucId = "";
    document.getElementById('itemFucName').value = "";
    $('#fucModalError').css('display', 'none');
    $('#itemFucClear').css('display', 'none');
    fucAction = 'CREATE';
}

function fillFilterGroupModal(name) {
    document.getElementById('itemFucModalTitle').textContent = 'Filter group';
    document.getElementById('itemFucSubmit').innerText = "Find";
    document.getElementById('itemFucName').value = name;
    $('#fucModalError').css('display', 'none');
    $('#itemFucClear').css('display', 'inline-block');
    fucAction = 'FILTER'
}

function fillUpdateGroupModal(id) {
    let room = document.getElementById('group_' + id).children;
    document.getElementById('itemFucModalTitle').textContent = 'Edit group : ' + room[0].textContent;
    document.getElementById('itemFucSubmit').innerText = "Change";
    fucId = id;
    document.getElementById('itemFucName').value = room[0].textContent;
    $('#fucModalError').css('display', 'none');
    $('#itemFucClear').css('display', 'none');
    fucAction = 'UPDATE'
}

function filterGroup() {
    let searchParams = new URLSearchParams(window.location.search);
    searchParams.set("fName", document.getElementById('itemFucName').value);
    window.location.href = "?" + searchParams.toString();
}
