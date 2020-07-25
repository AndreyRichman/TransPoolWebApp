
function clearTimeLineList(){
    $("#timeline-list").find("li").remove();
}

// function clearTimeLineline(){
//     $("#timeline-list").find("li.timeline-line").remove();
// }

function addBlueTimelineNode(node){

    var timelineNode = $("#dummyTimelineNode li.no-data")[0].cloneNode(true);

    $("#timeline-list")[0].appendChild(timelineNode);
}

function addRedTimelineNode(node){

    var timelineNode = $("#dummyTimelineNode li.no-data")[0].cloneNode(true);
    $(timelineNode).find("time").css("color", "#ff571f" );
    $("#timeline-list")[0].appendChild(timelineNode);
}

function addBlueTimelineNodeWithInfo(node){

    var timelineNode = $("#dummyTimelineNode li.yes-data")[0].cloneNode(true);

    $("#timeline-list")[0].appendChild(timelineNode);
}

function addBlueline(){
    var lineForTimeline = $("#dummyTimelineNode li.timeline-line")[0].cloneNode(true);

    $("#timeline-list")[0].appendChild(lineForTimeline);
}


// function addRide(ride) {
//     var rideNode = $("#dummyRide li.Product")[0].cloneNode(true);
//     $(rideNode).find(".Product-name").find(".from-station").text("Andrey!!!");
//     $(rideNode).find(".Product-name").find(".ppk").text("4000000");
//     $("#Rides")[0].appendChild(rideNode);
// }