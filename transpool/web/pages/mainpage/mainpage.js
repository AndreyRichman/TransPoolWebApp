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

//TODO:: need to add ONLINE users. right now its just showing who's in RIGHT NOW. need to refresh uppon changes