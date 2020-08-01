const menuIconEl = $('.menu-icon');
const sidenavEl = $('.sidenav');
const sidenavCloseEl = $('.sidenav__close-icon');

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

function saveLocalCopyOfMapData(data){
    ALL_RIDES = {};
    ALL_TREMPS = {};

    data.allRides.forEach(function (ride) {
        var rideID = ride.rideID;
        ALL_RIDES[rideID] = ride;
    });

    data.allTrempRequests.forEach(function (tremp) {
        var trempID = tremp.rideID;
        ALL_TREMPS[trempID] = tremp;
    });

}

function updateMapWindow(){
    var searchParams = new URLSearchParams(window.location.search);
    var mapIDParam = searchParams.get('mapID');
    $.ajax({
        url: "/transpool_war_exploded/map",
        data: {id: mapIDParam},
        // timeout: 2000,
        method: "GET",
        dataType: "json",
        success: function (data){
            // MAP_OBJ = data;
            saveLocalCopyOfMapData(data);

            initializeMap(data);
            addRides(data.allRides);
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
        url: "/transpool_war_exploded/map",
        data: {id: mapIDParam},
        method: "GET",
        dataType: "json",
        success: function (data){
            saveLocalCopyOfMapData(data);
            addRides(data.allRides);
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
    //timelineShowTremp(ALL_TREMPS[id]);
}

function showMatchRide(idStr){
    var id = parseInt(idStr.split(":")[1]);
    alert(id);
}

function assignRide(idStr){
    var id = parseInt(idStr.split(":")[1]);
    alert(id);
}

function findMatchedForTremp(idStr) {
    var id = idStr.split(":")[1];
    alert(id);
}