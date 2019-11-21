function modalTitleAndButtonText(title, button) {
    document.getElementById('itemFucModalTitle').textContent = title;
    document.getElementById('itemFucSubmit').innerText = button;
}

function fillCreateLessonModal() {
    modalTitleAndButtonText('Create lesson','Add');
    document.getElementById('itemFucName').value = "";
    setSelectFirst(document.getElementById('itemFucNumber'));
    setSelectFirst(document.getElementById('itemFucDayOfWeek'));
    setSelectFirst(document.getElementById('itemFucTeacher'));
    setSelectFirst(document.getElementById('itemFucGroup'));
    setSelectFirst(document.getElementById('itemFucRoom'));
    $('#fucModalError').css('display', 'none');
    $('#itemFucClear').css('display', 'none');
    fucAction = 'CREATE';
}

function setSelectFirst(select) {
    select.value = select.children[0].value;
}

function fillFilterLessonModal(filter) {
    modalTitleAndButtonText('Filter lessons','Find');
    Object.keys(filter)
        .forEach(function eachKey(key) {
            document.getElementById(key).value = filter[key];
        });
    $('#fucModalError').css('display', 'none');
    $('#itemFucClear').css('display', 'inline-block');
    fucAction = 'FILTER'
}

function fillUpdateLessonModal(id) {
    let lesson = document.getElementById('lesson_' + id).children;
    modalTitleAndButtonText('Edit lesson : ' + lesson[0].textContent,'Change');
    fucId = id;
    document.getElementById('itemFucName').value = lesson[0].textContent;
    document.getElementById('itemFucNumber').value = lesson[1].textContent;
    document.getElementById('itemFucDayOfWeek').value = lesson[2].textContent;
    selectOptionByInnerText(document.getElementById('itemFucTeacher'), lesson[3].innerText);
    selectOptionByInnerText(document.getElementById('itemFucGroup'), lesson[4].innerText);
    selectOptionByInnerText(document.getElementById('itemFucRoom'), lesson[5].innerText);
    $('#fucModalError').css('display', 'none');
    $('#itemFucClear').css('display', 'none');
    fucAction = 'UPDATE'
}

function selectOptionByInnerText(select, innerText) {
    if (innerText !== undefined && innerText.length > 0) {
        select.value = Array.from(select.children).find(e => e.innerText === innerText).value;
    } else {
        select.value = "";
    }
}

function filterLesson() {
    let searchParams = new URLSearchParams(window.location.search);
    searchParams.set("fName", document.getElementById('itemFucName').value);
    searchParams.set("fNumber", document.getElementById('itemFucNumber').value);
    searchParams.set("fDay", document.getElementById('itemFucDayOfWeek').value);
    searchParams.set("fTeacher", document.getElementById('itemFucTeacher').value);
    searchParams.set("fGroup", document.getElementById('itemFucGroup').value);
    searchParams.set("fRoom", document.getElementById('itemFucRoom').value);
    window.location.href = "?" + searchParams.toString();
}
