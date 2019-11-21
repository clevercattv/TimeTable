var tempDeleteItem;
var fucId;
var fucAction;

const modalAction = (create, update, filter) => {
    if (fucAction === "CREATE") {
        create();
    } else if (fucAction === "UPDATE") {
        update();
    } else {
        filter();
    }
};

const fillDeleteModalData = (id, name) => {
    document.getElementById('deleteModalTitle').textContent = 'Delete ' + name + ' : ' + document.getElementById(name + "_" + id).children[0].textContent;
    tempDeleteItem = id;
    $('#deleteItemError').css('display', 'none');
};

const ajaxRequest = (type, url, data, success, errorId = "#fucModalError") => {
    $.ajax({
        type: type,
        url: url,
        data: JSON.stringify(data),
        dataType: "text",
        success: success,
        error: function (xhr, ajaxOptions, thrownError) {
            let error = $(errorId);
            error.css('display', 'block');
            let text = xhr.responseText;
            if (text.charAt(0) === '[') {
                text = text.substring(1, text.length - 1)
            }
            error.text(text);
        }
    });
};


const updateLesson = () => {
    let name = document.getElementById("itemFucName").value;
    let number = document.getElementById("itemFucNumber").value;
    let day = document.getElementById("itemFucDayOfWeek").value;
    let teacher = document.getElementById("itemFucTeacher");
    let group = document.getElementById("itemFucGroup");
    let room = document.getElementById("itemFucRoom");
    ajaxRequest("PUT", "lesson",
        {
            id: fucId, name: name, number: number, day: day,
            teacher: {id: teacher.value}, group: {id: group.value}, room: {id: room.value}
        },
        () => {
            let lesson = document.getElementById("lesson_" + fucId).children;
            lesson[0].children[0].textContent = name;
            lesson[1].children[0].textContent = number;
            lesson[2].children[0].textContent = day;
            lesson[3].children[0].textContent = Array.from(teacher.children).find(e => e.value === teacher.value).innerText;
            lesson[4].children[0].textContent = Array.from(group.children).find(e => e.value === group.value).innerText;
            lesson[5].children[0].textContent = Array.from(room.children).find(e => e.value === room.value).innerText;
            $('#itemFucModal').modal('hide'); // Hide modal dialog
        })
};

const createLesson = () => {
    ajaxRequest("POST", "lesson",
        {
            name: document.getElementById("itemFucName").value,
            number: document.getElementById("itemFucNumber").value,
            day: document.getElementById("itemFucDayOfWeek").value,
            teacher: {id: document.getElementById("itemFucTeacher").value},
            group: {id: document.getElementById("itemFucGroup").value},
            room: {id: document.getElementById("itemFucRoom").value}
        }, () => location.reload())
};

const updateTeacher = () => {
    let name = document.getElementById("itemFucName").value;
    let type = document.getElementById("itemFucType").value;
    ajaxRequest("PUT", "teacher", {id: fucId, fullName: name, type: type}, () => {
        let room = document.getElementById("teacher_" + fucId).children;
        room[0].children[0].textContent = name;
        room[1].children[0].textContent = type;
        $('#itemFucModal').modal('hide'); // Hide modal dialog
    })
};

const createTeacher = () => {
    ajaxRequest("POST", "teacher",
        {
            fullName: document.getElementById("itemFucName").value,
            type: document.getElementById("itemFucType").value
        }, () => location.reload())
};

const updateGroup = () => {
    let name = document.getElementById("itemFucName").value;
    ajaxRequest("PUT", "group", {id: fucId, name: name}, () => {
        document.getElementById("group_" + fucId).children[0].children[0].textContent = name;
        $('#itemFucModal').modal('hide'); // Hide modal dialog
    })
};

const createGroup = () => {
    let name = document.getElementById("itemFucName").value;
    ajaxRequest("POST", "group", {name: name}, () => {
        location.reload()
    })
};


const updateRoom = () => {
    let name = document.getElementById("itemFucName").value;
    let type = document.getElementById("itemFucType").value;
    ajaxRequest("PUT", "room", {id: fucId, name: name, type: type}, () => {
        let room = document.getElementById("room_" + fucId).children;
        room[0].children[0].textContent = name;
        room[1].children[0].textContent = type;
        $('#itemFucModal').modal('hide'); // Hide modal dialog
    })
};

const createRoom = () => {
    ajaxRequest("POST", "room",
        {
            name: document.getElementById("itemFucName").value,
            type: document.getElementById("itemFucType").value
        },
        () => location.reload())
};

const removeItem = (name, url) => {
    ajaxRequest("DELETE", url, {id: tempDeleteItem}, () => {
        $('#deleteItemModal').modal('hide');
        document.getElementById(name + tempDeleteItem).remove();
    }, "#deleteItemError")
};