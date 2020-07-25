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
function addRide(ride) {
    var rideNode = $("#dummyRide li.Product")[0].cloneNode(true);
    $(rideNode).find(".Product-name").find(".from-station").text("Andrey!!!");
    $(rideNode).find(".Product-name").find(".ppk").text("4000000");
    $("#Rides")[0].appendChild(rideNode);
}

function addTremp(tremp) {
    var rideNode = $("#dummyTremp li.Product")[0].cloneNode(true);
    $("#Tremps")[0].appendChild(rideNode);
}

function addMatch(match) {
    var rideNode = $("#dummyMatch li.Product")[0].cloneNode(true);
    $("#Matches")[0].appendChild(rideNode);
}

function clearRideList(){
    $("#Rides").find("li").remove();
}