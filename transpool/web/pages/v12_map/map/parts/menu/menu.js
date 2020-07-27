$('.NavItem').click(function(evt) {
    evt.preventDefault();
    evt.stopPropagation();

    var navItem = $(this),
        allNavItems = $('.NavItem'),
        activeClass = 'NavItem--is-active',
        productLists = $('.ProductList'),
        hiddenClass = 'ProductList--is-hidden';

    // Highlight the correct nav item
    allNavItems.removeClass(activeClass);
    navItem.addClass(activeClass);

    // Hide all product lists, then show the one
    // matching the index of the clicked nav item
    productLists.addClass(hiddenClass);
    productLists.eq(navItem.index())
        .removeClass(hiddenClass);
});

// $("button").click(function(){
//     $("p").append("<b>Appended text</b>");
// });
function addRides(rides){
    clearRideList();
    rides.forEach(function (ride) {
        var rideNode = $("#dummyRide li.Product")[0].cloneNode(true);
        $(rideNode).find(".Product-title").text(ride.rideID);
        var htmlNodeToAdd = "<tr><td>Driver:</td><td>" + ride.driver.user.name + "</td></tr>" +
            "<tr><td>From:</td><td>" + ride.fromStation.name + "</td></tr>" +
            "<tr><td>To:</td><td>" + ride.toStation.name + "</td></tr>" +
            "<tr><td># Stations:</td><td>" + ride.numOfStations + "</td></tr>"
            "<tr><td>PPK:</td><td>" + ride.ppk + "</td></tr>" ;
        // "<tr><td>To:</td><td>" + ride.toStation.name + "</td></tr>" +
        // "<tr><td>To:</td><td>" + ride.toStation.name + "</td></tr>" +
        // "<tr><td>To:</td><td>" + ride.toStation.name + "</td></tr>" +
        $(rideNode).find("#Ride-table").append(htmlNodeToAdd);
        // $(rideNode).attr("item-id", ride.rideID);
        $(rideNode).find(".Product-buyCTA").attr("id", ride.rideID);
        $("#Rides").append(rideNode);
    });
}

function addTremps(tremps){
    clearTrempList();
    tremps.forEach(function (tremp) {
        var trempNode = $("#dummyTremp li.Product")[0].cloneNode(true);
        $(trempNode).find(".Product-title").text(tremp.id);

        var htmlNodeToAdd = "<tr><td>User:</td><td>" + tremp.user.name + "</td></tr>" +
            "<tr><td>From:</td><td>" + tremp.startStation.name + "</td></tr>" +
            "<tr><td>To:</td><td>" + tremp.endStation.name + "</td></tr>" +
            "<tr><td>" + tremp.desiredTimeType + ":</td><td>" + tremp.desiredTime + "</td></tr>"
        "<tr><td># Switches:</td><td>" + tremp.maxNumberOfConnections + "</td></tr>" ;
        // "<tr><td>To:</td><td>" + ride.toStation.name + "</td></tr>" +
        // "<tr><td>To:</td><td>" + ride.toStation.name + "</td></tr>" +
        // "<tr><td>To:</td><td>" + ride.toStation.name + "</td></tr>" +
        $(trempNode).find("#Tremp-table").append(htmlNodeToAdd);
        $("#Tremps").append(trempNode);
    });
}


//TODO: choose correct fields - currently same as tremp
function addMatchesForTremp(matchesForTremp){
    clearMatchesForTrempList();
    matchesForTremp.forEach(function (match) {
        var matchNode = $("#dummyMatch li.Product")[0].cloneNode(true);
        $(matchNode).find(".Product-title").text(match.id);

        var htmlNodeToAdd = "<tr><td>User:</td><td>" + match.user.name + "</td></tr>" +
            "<tr><td>From:</td><td>" + match.startStation.name + "</td></tr>" +
            "<tr><td>To:</td><td>" + match.endStation.name + "</td></tr>" +
            "<tr><td>" + match.desiredTimeType + ":</td><td>" + match.desiredTime + "</td></tr>"
        "<tr><td># Switches:</td><td>" + match.maxNumberOfConnections + "</td></tr>" ;
        // "<tr><td>To:</td><td>" + ride.toStation.name + "</td></tr>" +
        // "<tr><td>To:</td><td>" + ride.toStation.name + "</td></tr>" +
        // "<tr><td>To:</td><td>" + ride.toStation.name + "</td></tr>" +
        $(matchNode).find("#Match-table").append(htmlNodeToAdd);
        $("#MatchesForTremp").append(matchNode);
    });
}

//
// function addRide(ride) {
//     var rideNode = $("#dummyRide li.Product")[0].cloneNode(true);
//     $(rideNode).find(".Product-title").text(ride.rideID);
//
//
//     $(rideNode).find(".Product-name").find(".from-station").text("Andrey!!!");
//     $(rideNode).find(".Product-name").find(".ppk").text("4000000");
//     $("#Rides")[0].appendChild(rideNode);
// }
//
// function addTremp(tremp) {
//     var rideNode = $("#dummyTremp li.Product")[0].cloneNode(true);
//     $("#Tremps")[0].appendChild(rideNode);
// }
//
// function addMatch(match) {
//     var rideNode = $("#dummyMatch li.Product")[0].cloneNode(true);
//     $("#Matches")[0].appendChild(rideNode);
// }

function clearRideList(){
    $("#Rides").find("li").remove();
}

function clearTrempList() {
    $("#Tremps").find("li").remove();
}
function clearMatchesForTrempList(){
    $("#MatchesForTremp").find("li").remove();
}