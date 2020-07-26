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
MAP_OBJ = null;

function initializeMap(mapData){
//     mapData.forEach()
// }
//
// $(function () {
    // initial node data
    // var nodes2 = [
    //     {title: "Tel Aviv", id: 0, x: 50, y: 50},
    //     {title: "Bat Yam", id: 1, x: 50, y: 200},
    //     {title: "Jerusalem", id: 2, x: 300, y: 300},
    //     {title: "Haifa", id: 3, x: 400, y: 50}
    // ];
    // var nodes

    var nodes = [
        {title: "Tel Aviv",  x_coord: 1, y_coord: 1},
        {title: "Bat Yam", x_coord: 1, y_coord: 4},
        {title: "Jerusalem", x_coord: 4, y_coord: 4},
        {title: "Haifa", x_coord: 4, y_coord: 1}

    ];
    // var edges2 = [
    //     {source: nodes[1], target: nodes[0]},
    //     {source: nodes[0], target: nodes[2]},
    //     {source: nodes[2], target: nodes[1]},
    //     {source: {title: "Haifa", id: 2, x: 400, y: 50}, target: {title: "Tel Aviv", id: 0, x: 50, y: 50}}
    //
    //
    // ];
    var edges = [
        {source: {x_coord: 1, y_coord: 1}, target: {x_coord: 1, y_coord: 4}},
        {source: {x_coord: 1, y_coord: 4}, target: {x_coord: 4, y_coord: 4}},
        {source: {x_coord: 4, y_coord: 4}, target: {x_coord: 4, y_coord: 1}},
        {source: {x_coord: 4, y_coord: 1}, target: {x_coord: 1, y_coord: 1}},
    ];

    createGraph(6, 6, nodes, edges);
    colorEdgesInGreen(edges);
}




$(function () {
    $.ajax({
        url: "/transpool_war_exploded/map",
        timeout: 2000,
        method: "POST",
        dataType: "json",
        success: function (data){
            MAP_OBJ = data;
            initializeMap(data);
            },
        error: function (data) {
            alert(data);
        }
        });
    });
