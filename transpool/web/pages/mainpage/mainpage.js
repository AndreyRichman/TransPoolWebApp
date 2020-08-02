var $users = $('#online-users');
var $userslist1 = [];
var $userslist2 = [];
var refreshRate = 2000;

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

        if ($userslist1.includes(username.name) === false ){
            toastr.success("Welcome!: " + username.name);
        }

        //create a new <option> tag with a value in it and
        //appeand it to the #userslist (div with id=userslist) element
        $users.append('<li><a href="#">' + username.name + '</a></li>' );
        console.log($users);
    });

    $userslist1 = $userslist2;
    $userslist2 = [];
}

$(function() {

    //The users list is refreshed automatically every second
    setInterval(ajaxUsersList, refreshRate);
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