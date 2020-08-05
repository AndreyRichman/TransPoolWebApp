var $users = $('#os');
var $userslist1 = [];
var $userslist2 = [];
var refreshRate = 5000;
var PRIVATE_NOTIFICATIONS_TIME_INTERVAL = 2000;
var PUBLIC_NOTIFICATIONS_TIME_INTERVAL = 6000;

$(function () {
    $.ajax({
        type: 'GET',
        url: '/transpool_war_exploded/UsersList',
        success: function (data) {
            $.each(data, function (i, username) {
                $users.append('<li><a href="#">' + username.name + '</a></li>' )
                $userslist1.push(username.name);
            })
        }
    });
});

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
        // console.log("Adding user # " + index + " : " + username.name);
        $userslist2.push(username.name);

        // if ($userslist1.includes(username.name) === false ){
        //     toastr.success("Welcome!: " + username.name);
        // }

        //create a new <option> tag with a value in it and
        //appeand it to the #userslist (div with id=userslist) element
        $users.append('<li><a href="#">' + username.name + '</a></li>' );
        console.log($users);
    });

    $userslist1 = $userslist2;
    $userslist2 = [];
}

function getPublicNotifications(){
    $.ajax({
        url: "/transpool_war_exploded/notifications",
        method: "GET",
        data: {notificationType: "PUBLIC"},
        dataType: "json",
        success: function (data){
            data.forEach(function (publicMessage) {
                toastr.warning(publicMessage);
            });
        }
    });
}

function getPrivateNotifications(){
    $.ajax({
        url: "/transpool_war_exploded/notifications",
        method: "GET",
        data: {notificationType: "PRIVATE"},
        dataType: "json",
        success: function (data){
            data.forEach(function (publicMessage) {
                toastr.success(publicMessage);
            });
        }
    });
}

$(function() {

    //The users list is refreshed automatically every second
    setInterval(ajaxUsersList, refreshRate);

    setInterval(getPrivateNotifications, PRIVATE_NOTIFICATIONS_TIME_INTERVAL);
    setInterval(getPublicNotifications, PUBLIC_NOTIFICATIONS_TIME_INTERVAL);
});


// window.addEventListener("beforeunload", function (e) {
//     console.log("im here");
//     $.ajax({
//         url: '/transpool_war_exploded/logout',
//         success: function(users) {
//             refreshUsersList(users);
//         }
//     });
// });

//TODO::