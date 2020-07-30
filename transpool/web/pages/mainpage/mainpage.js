$(function () {
    var $users = $('#online-users');

    $.ajax({
        type: 'GET',
        url: '/transpool_war_exploded/UsersList',
        success: function (data) {
            $.each(data, function (i, item) {
                $users.append('<li><a href="#">' + item.name + '</a></li>' )
            })
        }
    });
});