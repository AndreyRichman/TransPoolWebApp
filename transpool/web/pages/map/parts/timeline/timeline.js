
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
function addRedTimelineNodeWithInfo(time, title, pickData, dropData){

    var timelineNode = $("#dummyTimelineNode li.yes-data")[0].cloneNode(true);
    $(timelineNode).find("time").css("color", "#ff571f").text(time);
    $(timelineNode).find("div.station-title").text(title);
    $(timelineNode).find("div.pick-data").text(pickData);
    $(timelineNode).find("div.drop-data").text(dropData);

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

            if (station.pickedUp.length > 0){
                pickingInfo += "Pick-up:" + station.pickedUp.join(",");
            }
            if (station.gettingOff.length > 0){
                leavingInfo += "Drop-off:" + station.gettingOff.join(",");
            }

            addBlueTimelineNodeWithInfo(time, title, pickingInfo, leavingInfo);
        } else {
            addBlueTimelineNode(time, title);
        }
    });
}
function timelineShowMatch(match){
    clearTimeLineList();
    addBlueline();

    match.subRides.forEach(function (subRide) {

        var pickingInfo = subRide.originalRide.rideID + ":Pick you up.";
        var leavingInfo = "";
        var isFirst = true;
        var endTime = "";
        var endStation = "";

        subRide.selectedPartsOfRide.forEach(function (partOfRide) {

            var startTime = partOfRide.startTime;
            endTime = partOfRide.endTime;
            var fromStation = partOfRide.fromStation;
            endStation = partOfRide.toStation;

            if (isFirst){
                addBlueTimelineNodeWithInfo(startTime, fromStation, pickingInfo, leavingInfo);
                isFirst = false;
            } else {
                addBlueTimelineNode(startTime, fromStation);
            }
        });

        leavingInfo = subRide.originalRide.rideID + ":Drops you off.";

        // if(endTime !== match.endTime){
        addRedTimelineNodeWithInfo(endTime, endStation, "", leavingInfo);
        // }

    });
    //var stationsInRide = ride.partStations.partsArray;

    // stationsInRide.forEach(function (station) {
    //     var name = station.station.name;
    //     var capacity = station.takenPlaces + "/" + station.totalPlaces;
    //     var time = station.time;
    //
    //     var title = name + " (" + capacity + ")";
    //     if(station.containsTremists){
    //         var pickingInfo = "";
    //         var leavingInfo = "";
    //
    //         if (station.pickedUp.size > 0){
    //             pickingInfo += "Pick-up:" + station.pickedUp.join(",");
    //         }
    //         if (station.gettingOff.size > 0){
    //             leavingInfo += "Drop-off:" + station.gettingOff.join(",");
    //         }
    //
    //         addBlueTimelineNodeWithInfo(time, title, pickingInfo, leavingInfo);
    //     } else {
    //         addBlueTimelineNode(time, title);
    //     }
    // });


}
function timelineShowTremp(tremp){

    var startStationName = tremp.startStation.name;
    var endStationName = tremp.endStation.name;
    clearTimeLineList();

    var leavingTime = "?";
    var arriveTime = "?";

    var desiredTime = tremp.desiredTime;

    if (tremp.schedule.maxDiffInMinutes > 0){
        desiredTime = desiredTime + "(+/- " + tremp.schedule.maxDiffInMinutes +"m)";
    }

    if (tremp.desiredTimeType === "ARRIVE"){
        arriveTime = desiredTime;
    } else {
        leavingTime = desiredTime;
    }

    addBlueTimelineNode(leavingTime, startStationName);
    addBlueTimelineNode(arriveTime, endStationName);
}


// function addRide(ride) {
//     var rideNode = $("#dummyRide li.Product")[0].cloneNode(true);
//     $(rideNode).find(".Product-name").find(".from-station").text("Andrey!!!");
//     $(rideNode).find(".Product-name").find(".ppk").text("4000000");
//     $("#Rides")[0].appendChild(rideNode);
// }