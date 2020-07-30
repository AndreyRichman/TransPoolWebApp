$(function () {
    var $users = $('#online-users');

    $.ajax({
        type: 'GET',
        url: '/transpool_war_exploded/UsersList',
        success: function (data) {
            // $users.append('<li>name:'+ 'test' + '</li>' );
            $.each(data, function (i, item) {
                $users.append('<li>'+ item.name +'</li>' )

            })
        }
    });
});