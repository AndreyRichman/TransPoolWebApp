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



$(function () {
    $.ajax({
        url: "/transpool_war_exploded/map",
        timeout: 2000,
        method: "POST",
        dataType: "json",
        success: function (data){
            // MAP_OBJ = data;
            initializeMap(data);
            // initRides(data.allRides);
            addRides(data.allRides);
            },
        error: function (data) {
            alert(data);
        }
        });
    });

function clickedRide(id){
    alert(id);
}