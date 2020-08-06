const menuIconEl = $('.menu-icon');
const sidenavEl = $('.sidenav');
const sidenavCloseEl = $('.sidenav__close-icon');

var PRIVATE_NOTIFICATIONS_TIME_INTERVAL = 8000;
var PUBLIC_NOTIFICATIONS_TIME_INTERVAL = 6000;

// Add and remove provided class names
function toggleClassName(el, className) {
    if (el.hasClass(className)) {
        el.removeClass(className);
    } else {
        el.addClass(className);
    }
}

// Open the side nav on click
menuIconEl.on('click', function() {
    toggleClassName(sidenavEl, 'active');
});

// Close the side nav on click
sidenavCloseEl.on('click', function() {
    toggleClassName(sidenavEl, 'active');
});
// MAP_OBJ = null;

function initializeMap(mapData){
    var nodes = [];

    mapData.allStations.forEach(function (station) {
        nodes.push({title: station.name, x_coord: station.coordinate.x, y_coord: station.coordinate.y});
    });

    var edges = [];
    mapData.allRoads.forEach(function (road) {
        edges.push({source: {x_coord: road.fromCoordinate.x, y_coord: road.fromCoordinate.y},
            target: {x_coord: road.toCoordinate.x, y_coord: road.toCoordinate.y}});
    });

    createGraph(mapData.width, mapData.height, nodes, edges);
    colorEdgesInGreen(edges);
}

// function initRides(rides){
//     rides.forEach(function (ride) {
//
//     });
// }

var ALL_RIDES = {};
var ALL_TREMPS = {};
var LAST_FETCHED_MATCHES = {};
// var ALL_ASSIGNED_MATCHES = {};

function saveLocalCopyOfMapData(data){
    ALL_RIDES = {};
    ALL_TREMPS = {};

    data.allRides.forEach(function (ride) {
        var rideID = ride.rideID;
        ALL_RIDES[rideID] = ride;
    });

    data.allTrempRequests.forEach(function (tremp) {
        var trempID = tremp.id;
        ALL_TREMPS[trempID] = tremp;
    });

}

function updateMapWindow(){
    var searchParams = new URLSearchParams(window.location.search);
    var mapIDParam = searchParams.get('mapID');
    $.ajax({
        url: "../../map",
        data: {id: mapIDParam},
        // timeout: 2000,
        method: "GET",
        dataType: "json",
        success: function (data){
            // MAP_OBJ = data;
            saveLocalCopyOfMapData(data);

            initializeMap(data);
            updateRidesList(data.allRides);
            updateTrempsCards(data.allTrempRequests);
        },
        error: function (data) {
            alert(data);
        }
    });
}


$(function () {
   updateMapWindow();
});

function updateRideAndTrempInfo(){
    var searchParams = new URLSearchParams(window.location.search);
    var mapIDParam = searchParams.get('mapID');
    $.ajax({
        url: "../../../map",
        data: {id: mapIDParam},
        method: "GET",
        dataType: "json",
        success: function (data){
            saveLocalCopyOfMapData(data);
            updateRidesList(data.allRides);
            updateTrempsCards(data.allTrempRequests);
        },
        error: function (data) {
            alert(data);
        }
    });
}

function showRide(idStr){
    var id = parseInt(idStr.split(":")[1]);
    timelineShowRide(ALL_RIDES[id]);
    graphShowRide(ALL_RIDES[id]);
}

function showTremp(idStr){
    var id = parseInt(idStr.split(":")[1]);
    timelineShowTremp(ALL_TREMPS[id]);
    graphShowTremp(ALL_TREMPS[id]);
}

function showAssigendTremp(idOfTremp) {
    var id = parseInt(idOfTremp.split(":")[1]);
    var selectedRide = ALL_TREMPS[id].selectedRide;

    graphShowTremp(ALL_TREMPS[id]);
    timelineShowMatch(selectedRide);
    graphShowMatch(selectedRide);

}

function showMatchRide(idStr){
    var id = parseInt(idStr.split(":")[1]);
    timelineShowMatch(LAST_FETCHED_MATCHES[id]);
    graphShowMatch(LAST_FETCHED_MATCHES[id]);
    // alert(id);
}

function assignRide(matchIdStr, requestID){
    var matchID = parseInt(matchIdStr.split(":")[1]);
    var searchParams = new URLSearchParams(window.location.search);
    var mapIDParam = searchParams.get('mapID');

    // ALL_ASSIGNED_MATCHES[matchID] = LAST_FETCHED_MATCHES[matchID];

    $.ajax({
        url: "../../../tremp",
        data: {mapID: mapIDParam, trempID: requestID, matchID: matchID},
        method: "GET",
        dataType: "json",
        success: function (data){

            //DELETE TREMPCARD
            // removeTrempCard(ALL_TREMPS[requestID]);

            // addCardOfAssignedTremp(ALL_TREMPS[requestID]);

            updateRideAndTrempInfo();

            //ADD MATCHED TREMPCARD
            $("#nav-tremps").click();

        },
        error: function (data) {
            alert(JSON.stringify(data));
        }
    });

    // assignMatchToTremp(LAST_FETCHED_MATCHES[matchID], ALL_TREMPS[requestID]);

}

function findMatchedForTremp(idStr) {
    var id = idStr.split(":")[1];
    var searchParams = new URLSearchParams(window.location.search);
    var mapIDParam = searchParams.get('mapID');
    $.ajax({
        url: "../../../ride",
        data: {mapID: mapIDParam, trempID: id},
        //method: "GET",
        dataType: "json",
        success: function (data){
            // alert(JSON.stringify(data));
            // MAP_OBJ = data;
            // saveLocalCopyOfMapData(data);

            // initializeMap(data);
            // updateRidesList(data.allRides);
            // updateTrempsCards(data.allTrempRequests);
            LAST_FETCHED_MATCHES = updateRidesForTrempCards(data, id);
            $("#nav-matches").click();

        },
        error: function (data) {
            alert(JSON.stringify(data));
        }
    });


    // alert(id);
}


function getPublicNotifications(){
    $.ajax({
        url: "notifications",
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
        url: "notifications",
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

    setInterval(getPrivateNotifications, PRIVATE_NOTIFICATIONS_TIME_INTERVAL);
    setInterval(getPublicNotifications, PUBLIC_NOTIFICATIONS_TIME_INTERVAL);
});