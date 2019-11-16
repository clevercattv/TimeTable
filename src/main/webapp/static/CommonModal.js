var tempDeleteItem;
var fucId;
var fucAction;

function modalAction(create, update, filter) {
    if (fucAction === "CREATE") {
        create();
    } else if (fucAction === "UPDATE") {
        update();
    } else {
        filter();
    }
}

function setSelectFirst(select) {
    select.value = select.children[0].value;
}

function fillDeleteModalData(id, name) {
    document.getElementById('deleteModalTitle').textContent = 'Delete ' + name + ' : ' + document.getElementById(name + "_" + id).children[0].textContent;
    tempDeleteItem = id;
    $('#deleteItemError').css('display', 'none');
}

function removeItem(name,url) {
    $.ajax({
        type: 'POST',
        url: url,
        data: {method: 'DELETE', id: tempDeleteItem},
        dataType: "text",
        success: function () {
            $('#deleteItemModal').modal('hide')
            document.getElementById(name + tempDeleteItem).remove();
        },
        error: function (xhr, ajaxOptions, thrownError) {
            let error = $("#deleteItemError");
            error.css("display", "block");
            error.text(xhr.responseText);
        }
    });
}