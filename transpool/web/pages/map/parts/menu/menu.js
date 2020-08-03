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

function addCardOfNotAssignedTremp(tremp){
    var trempNode = $("#dummyTremp li.Product")[0].cloneNode(true);
    $(trempNode).find(".Product-title").text(tremp.id);

    // alert(JSON.stringify(tremp));

    var htmlNodeToAdd =  "<tr><td>User:</td><td>" + tremp.user.name + "</td></tr>" +

        "<tr><td>From:</td><td>" + tremp.startStation.name + "</td></tr>" +
        "<tr><td>To:</td><td>" + tremp.endStation.name + "</td></tr>" +
        "<tr><td>Want to:</td><td>" + tremp.desiredTimeType + "</td></tr>" +
        "<tr><td>At:</td><td>" + tremp.desiredTime + "</td></tr>" +
        "<tr><td>Max Swaps:</td><td>" + tremp.maxNumberOfConnections + "</td></tr>" +
        "<tr><td>Status:</td><td>NOT-ASSIGNED</td></tr>" ;

    $(trempNode).find("#Tremp-table").append(htmlNodeToAdd);
    $(trempNode).find(".Product-buyCTA").attr("id", "tremp-s:" +tremp.id);
    $("#Tremps").append(trempNode);
}

function removeTrempCard(tremp){
    var id = tremp.id;

    $("ul#Tremps li div.Product-title:contains(" + id + ")").parent().remove();
}

function addCardOfAssignedTremp(tremp){
    var trempNode = $("#dummyAssignedTremp li.Product")[0].cloneNode(true);
    $(trempNode).find(".Product-title").text(tremp.id);

    var htmlNodeToAdd =  "<tr><td>User:</td><td>" + tremp.user.name + "</td></tr>" +

        "<tr><td>From:</td><td>" + tremp.startStation.name + "</td></tr>" +
        "<tr><td>To:</td><td>" + tremp.endStation.name + "</td></tr>" +
        "<tr><td>Want to:</td><td>" + tremp.desiredTimeType + "</td></tr>" +
        "<tr><td>At:</td><td>" + tremp.desiredTime + "</td></tr>" +
        "<tr><td>Max Swaps:</td><td>" + tremp.maxNumberOfConnections + "</td></tr>" +
        "<tr><td>Status:</td><td>ASSIGNED</td></tr>" ;

    $(trempNode).find("#Assigned-Tremp-table").append(htmlNodeToAdd);
    $(trempNode).find(".Product-buyCTA").attr("id", "tremp-s:" +tremp.id).attr("value", tremp.matchID);
    $("#Tremps").append(trempNode);
}

function updateRidesForTrempCards(matches, trempID) {
    clearMatchesForTrempList();

    var LAST_FETCHED_MATCHES = {};

    matches.forEach(function (match) {
        LAST_FETCHED_MATCHES[match.id] = match;

        var matchNode = $("#dummyMatch li.Product")[0].cloneNode(true);


        $(matchNode).find(".Product-title").text(match.id);
        var htmlNodeToAdd =  "<tr><td>Pickup Time:</td><td>" + match.startTime + "</td></tr>" +
            "<tr><td>Arrive Time:</td><td>" + match.endTime + "</td></tr>" +
            "<tr><td>Swaps:</td><td>" + match.stationSwaps + "</td></tr>" +
            "<tr><td>AVG Fuel:</td><td>" + match.averageFuel + "</td></tr>" +
            "<tr><td>AVG Rank:</td><td>" + match.averageRank + "</td></tr>";


        $(matchNode).find("#Match-table").append(htmlNodeToAdd);
        $(matchNode).find("span.Product-price").text(match.totalPrice);
        $(matchNode).find("button.Product-buyCTA.first-btn").attr("id", "match-s:" + match.id).attr("value", trempID);
        $(matchNode).find("button.Product-buyCTA.second-btn").attr("id", "match-a:" + match.id).attr("value", trempID);
        $("#MatchesForTremp").append(matchNode);

    });

    return LAST_FETCHED_MATCHES;
}

function updateTrempsCards(tremps){
    clearTrempList();
    tremps.forEach(function (tremp) {

        if (tremp.rideAssigned){
            addCardOfAssignedTremp(tremp);  //TODO add Assigned Tremp Card
        } else {
            addCardOfNotAssignedTremp(tremp);
        }
    });


}
function updateRidesList(rides){
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
        $(rideNode).find(".Product-buyCTA").attr("id", "ride-s:" +ride.rideID);
        $("#Rides").append(rideNode);
    });
}

// function addTremps(tremps){
//     clearTrempList();
//     tremps.forEach(function (tremp) {
//         var trempNode = $("#dummyTremp li.Product")[0].cloneNode(true);
//         $(trempNode).find(".Product-title").text(tremp.id);
//
//         var htmlNodeToAdd = "<tr><td>User:</td><td>" + tremp.user.name + "</td></tr>" +
//             "<tr><td>From:</td><td>" + tremp.startStation.name + "</td></tr>" +
//             "<tr><td>To:</td><td>" + tremp.endStation.name + "</td></tr>" +
//             "<tr><td>" + tremp.desiredTimeType + ":</td><td>" + tremp.desiredTime + "</td></tr>"
//         "<tr><td># Switches:</td><td>" + tremp.maxNumberOfConnections + "</td></tr>" ;
//         // "<tr><td>To:</td><td>" + ride.toStation.name + "</td></tr>" +
//         // "<tr><td>To:</td><td>" + ride.toStation.name + "</td></tr>" +
//         // "<tr><td>To:</td><td>" + ride.toStation.name + "</td></tr>" +
//         $(trempNode).find("#Tremp-table").append(htmlNodeToAdd);
//         $(trempNode).find(".first-btn").attr("id", "tremp-s:" +ride.rideID);
//         $(trempNode).find(".second-btn").attr("id", "tremp-f:" +ride.rideID);
//
//         $("#Tremps").append(trempNode);
//     });
// }


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
        $(matchNode).find(".first-btn").attr("id", "match-s:" +ride.rideID);
        $(matchNode).find(".second-btn").attr("id", "match-f:" +ride.rideID);
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