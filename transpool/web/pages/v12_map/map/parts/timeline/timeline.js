
function clearTimeLineList(){
    $("#timeline-list").find("li").remove();
}

// function clearTimeLineline(){
//     $("#timeline-list").find("li.timeline-line").remove();
// }

function addBlueTimelineNode(time, title){

    var timelineNode = $("#dummyTimelineNode li.no-data")[0].cloneNode(true);
    $(timelineNode).find("time").text(time);
    $(timelineNode).find("div.station-title").text(title);

    $("#timeline-list")[0].appendChild(timelineNode);
}

function addRedTimelineNode(time, title){

    var timelineNode = $("#dummyTimelineNode li.no-data")[0].cloneNode(true);
    $(timelineNode).find("time").css("color", "#ff571f" );
    $(timelineNode).find("time").text(time);
    $(timelineNode).find("div.station-title").text(title);
    $("#timeline-list")[0].appendChild(timelineNode);
}

function addBlueTimelineNodeWithInfo(time, title, pickData, dropData){

    var timelineNode = $("#dummyTimelineNode li.yes-data")[0].cloneNode(true);
    $(timelineNode).find("time").text(time);
    $(timelineNode).find("div.station-title").text(title);
    $(timelineNode).find("div.pick-data").text(pickData);
    $(timelineNode).find("div.drop-data").text(dropData);

    $("#timeline-list")[0].appendChild(timelineNode);
}

function addBlueline(){
    var lineForTimeline = $("#dummyTimelineNode li.timeline-line")[0].cloneNode(true);

    $("#timeline-list")[0].appendChild(lineForTimeline);
}

function timelineShowRide(ride) {
    var stationsInRide = ride.partStations.partsArray;
    clearTimeLineList();
    addBlueline();

    stationsInRide.forEach(function (station) {
        var name = station.station.name;
        var capacity = station.takenPlaces + "/" + station.totalPlaces;
        var time = station.time;

        var title = name + " (" + capacity + ")";
        if(station.containsTremists){
            var pickingInfo = "";
            var leavingInfo = "";

            if (station.pickedUp.size > 0){
                pickingInfo += "Pick-up:" + station.pickedUp.join(",");
            }
            if (station.gettingOff.size > 0){
                leavingInfo += "Drop-off:" + station.gettingOff.join(",");
            }

            addBlueTimelineNodeWithInfo(time, title, pickingInfo, leavingInfo);
        } else {
            addBlueTimelineNode(time, title);
        }
    });
}


// function addRide(ride) {
//     var rideNode = $("#dummyRide li.Product")[0].cloneNode(true);
//     $(rideNode).find(".Product-name").find(".from-station").text("Andrey!!!");
//     $(rideNode).find(".Product-name").find(".ppk").text("4000000");
//     $("#Rides")[0].appendChild(rideNode);
// }