$(document).ready(allUsers());

//all users
function allUsers() {
    $("#table").empty();
    $.ajax({
        type: 'POST',
        url: '/api/admin/allUsers',
        timeout: 3000,
        success: function (data) {
            console.log(data);
            $.each(data, function (i, user) {
                $("#table").append($('<tr>').append(
                        $('<td>').append($('<span>')).text(user.id),
                        $('<td>').append($('<span>')).text(user.username),
                        $('<td>').append($('<span>')).text(user.firstName),
                        $('<td>').append($('<span>')).text(user.lastName),
                        $('<td>').append($('<span>')).text(user.email),
                        $('<td>').append($('<span>')).text(user.roles),
                        $('<td>').append($('<button>').text("Edit").attr({
                            "type": "button",
                            "class": "btn btn-info edit",
                            "data-toggle": "modal",
                            "data-target": "#editModal",
                        })
                            .data("user", user)),
                        $('<td>').append($('<button>').text("Delete").attr({
                            "type": "button",
                            "class": "btn btn-danger remove",
                            "data-toggle": "modal",
                            "data-target": "#deleteModal",
                        })
                            .data("user", user))
                    )
                );
            });
        }
    });
}

//Edit user
$(document).on("click", ".edit", function () {
    let user = $(this).data('user');

    $('#usernameInput').val(user.username);
    $('#passwordInput').val(user.password);
    $('#firstNameInput').val(user.firstName);
    $('#lastNameInput').val(user.lastName);
    $('#emailInput').val(user.email);
    $('#idInput').val(user.id);
    $('#roleInput').val(user.roles);

});

$(document).on("click", ".editUser", function () {
    let formData = $(".editUserForm").serializeArray();
    $.ajax({
        type: 'POST',
        url: '/api/admin/editUser',
        data: formData,
        timeout: 100,
        success: function () {
            allUsers();
        }
    });
});

//delete user
$(document).on("click", ".remove", function () {
    let user = $(this).data('user');

    $('#username').val(user.username);
    $('#firstName').val(user.firstName);
    $('#lastName').val(user.lastName);
    $('#email').val(user.email);
    $('#id').val(user.id);

    $(document).on("click", ".removeUser", function () {
        $.ajax({
            type: 'POST',
            url: '/api/admin/removeUser',
            data: {id: $('#id').val()},
            timeout: 100,
            success: function () {
                allUsers();
            }
        });
    });
})

//Add user
$('.addUser').click(function () {
    $('#usersTable').trigger('click');
    let formData = $(".addUserForm").serializeArray();
    $.ajax({
        type: 'POST',
        url: '/api/admin/addUser',
        data: formData,
        timeout: 100,
        success: function () {
            allUsers()
        }
    })
});

//Users page
$(document).ready(getUser());
function getUser() {
    $("#userTable").empty();
    $.ajax({
        type: 'POST',
        url: '/api/user/getUser',
        timeout: 3000,
        error: function() {
            $('#blockMenuForUser').hide();
        },
        success: function (data) {
            console.log(data);
            $.each(data, function (i, user) {
                if(user.roles == "USER") {
                    $('#userMenu').trigger('click');
                    $('#userPage').trigger('click');
                    $('#blockMenuForAdmin').hide();
                }
                $("#userTable").append($('<tr>').append(
                        $('<td>').append($('<span>')).text(user.id),
                        $('<td>').append($('<span>')).text(user.username),
                        $('<td>').append($('<span>')).text(user.firstName),
                        $('<td>').append($('<span>')).text(user.lastName),
                        $('<td>').append($('<span>')).text(user.email),
                        $('<td>').append($('<span>')).text(user.roles),
                    )
                );
            });
        }
    });
}