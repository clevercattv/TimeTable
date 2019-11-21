let days = [];
let lessonTime = [];
let groups = [];
let teachers = [];
let rooms = [];
let lessons;

function buildTimeTable(data) {
    fillLocalData(data);
    buildTableHead(groups);
    buildFilters();
    buildTableBody(days, lessonTime, groups, lessons.slice());
}

function fillLocalData(data) {
    lessons = Array.from(data);
    lessons.forEach(e => {
        days.push(e.day);
        lessonTime.push(e.number);
        groups.push(e.group);
        teachers.push(e.teacher);
        rooms.push(e.room);
    });
    days = [...new Set(days)];
    lessonTime = [...new Set(lessonTime)].sort();
    groups = [...new Set(groups)].sort();
    teachers = [...new Set(teachers)].sort();
    rooms = [...new Set(rooms)].sort();
}

function buildTableHead(fGroups) {
    $("#tableHead tr").remove();
    let headTR = document.createElement('TR');
    headTR.appendChild(buildTH("Day"));
    headTR.appendChild(buildTH("Time"));
    fGroups.forEach(g => headTR.appendChild(buildTH(g)));
    document.getElementById('tableHead').appendChild(headTR);
}

function buildFilters() {
    buildSelectOptionsByNames("dayOfWeek", days);
    buildSelectOptionsByNames("lessonTime", lessonTime);
    buildSelectOptionsByNames("groups", groups);
    buildSelectOptionsByNames("teachers", teachers);
    buildSelectOptionsByNames("rooms", rooms);
}

function buildSelectOptionsByNames(id, names) {
    let select = document.getElementById(id);
    Array.from(names).forEach(e => {
        let option = document.createElement('OPTION');
        option.innerText = e;
        select.appendChild(option);
    });
}

function getSelectedValuesFromSelect(id) {
    return Array.prototype.slice
        .call(document.querySelectorAll('#' + id + ' option:checked'), 0)
        .map(e => e.value);
}

function clearFilter() {
    ['dayOfWeek', 'lessonTime', 'groups', 'teachers', 'rooms'].forEach(e => {
        $('#' + e + ' option').prop('selected', false);
    });
    document.getElementById("lessonName").value = "";
    buildTableHead(groups);
    buildTableBody(days, lessonTime, groups, lessons.slice());
    $('#filterModal').modal('hide');
}

function filterTimeTable() {
    let fDays = getSelectedValuesFromSelect("dayOfWeek");
    let fLessonTime = getSelectedValuesFromSelect("lessonTime");
    let fGroups = getSelectedValuesFromSelect("groups");
    let fTeachers = getSelectedValuesFromSelect("teachers");
    let fRooms = getSelectedValuesFromSelect("rooms");
    let fName = document.getElementById("lessonName").value;
    let filters = [
        {filter: fDays, name: 'day'},
        {filter: fLessonTime, name: 'number'},
        {filter: fGroups, name: 'group'},
        {filter: fTeachers, name: 'teacher'},
        {filter: fRooms, name: 'room'},
    ].filter(e => e.filter.length > 0);
    let fLessons = lessons.slice().filter(l => l.name.toLowerCase().includes(fName.toLowerCase()));
    fLessons = fLessons.filter(l => filters.every(e => e.filter.includes(l[e.name])));
    fGroups = fGroups.length > 0 ? fGroups : new Set(fLessons.map(e => e.group));
    buildTableHead(fGroups);
    buildTableBody(fDays.length > 0 ? fDays : new Set(fLessons.map(e => e.day)),
        fLessonTime.length > 0 ? fLessonTime : new Set(fLessons.map(e => e.number)),
        fGroups,
        fLessons);
    $('#filterModal').modal('hide');
}

function buildTableBody(fDays, fLessonTime, fGroups, fLessons) {
    $("#tableBody tr").remove();
    let tableBody = document.getElementById('tableBody');
    fDays.forEach(d => {
        fLessonTime.forEach(t => {
            let isCreate = false;
            let tr = document.createElement('TR');
            tr.appendChild(buildTD(d, " pos--relative width--1"));
            tr.appendChild(buildTD(t, " width--1"));
            fGroups.forEach(g => {
                let td = document.createElement('TD');
                let lesson;
                fLessons.some((l, index) => {
                    if (l.day === d && l.number === t && l.group === g) {
                        lesson = l;
                        fLessons.splice(index, 1);
                        isCreate = true;
                        return true;
                    }
                    return false;
                });
                if (lesson !== undefined) {
                    td.appendChild(buildDiv(lesson.name));
                    td.appendChild(buildDiv(lesson.teacher));
                    td.appendChild(buildDiv(lesson.room));
                }
                td.scope = "col";
                tr.appendChild(td);
            });
            if (isCreate) {
                tableBody.appendChild(tr)
            }
        })
    });
    if (tableBody.children.length === 0) {
        let tr = document.createElement('TR');
        let td = document.createElement('TD');
        td.setAttribute("colspan", 2 + fDays.length + "");
        td.innerText = "Table empty!";
        tr.appendChild(td);
        tableBody.appendChild(tr);
    }
}


function buildTH(innerText) {
    let th = document.createElement('TH');
    th.innerText = innerText;
    th.scope = "col";
    th.setAttribute("class", "font--weight--500");
    return th;
}

function buildTD(innerText, clazz) {
    let td = document.createElement('TD');
    td.innerText = innerText;
    td.scope = "col";
    td.setAttribute("class", "font--weight--500" + clazz);
    return td;
}

function buildDiv(innerText) {
    let div = document.createElement('DIV');
    div.innerText = innerText;
    div.setAttribute("class", "font--weight--500");
    return div;
}