var $users = $('#online-users');
var refreshRate = 2000;
// $(function () {
//     $.ajax({
//         type: 'GET',
//         url: '/transpool_war_exploded/UsersList',
//         success: function (data) {
//             $.each(data, function (i, username) {
//                 $users.append('<li><a href="#">' + username.name + '</a></li>' )
//             })
//         }
//     });
// });

function ajaxUsersList() {
    $.ajax({
        url: '/transpool_war_exploded/UsersList',
        success: function(users) {
            refreshUsersList(users);
        }
    });
}

function refreshUsersList(users) {
    //clear all current users
    $users.empty();

    // rebuild the list of users: scan all users and add them to the list of users
    $.each(users || [], function(index, username) {
        console.log("Adding user #" + index + ": " + username);
        //create a new <option> tag with a value in it and
        //appeand it to the #userslist (div with id=userslist) element
        $users.append('<li><a href="#">' + username.name + '</a></li>' )
    });
}

$(function() {

    //The users list is refreshed automatically every second
    setInterval(ajaxUsersList, refreshRate);
});

//TODO:: need to add ONLINE users. right now its just showing who's in RIGHT NOW. need to refresh uppon changes