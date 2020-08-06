function validate(){
  var fromStation = document.getElementById("fromStation").value;
  var destStation = document.getElementById("destStation").value;
  var day = document.getElementById("day").value;
  var time = document.getElementById("time").value;
  var arrOrDept = document.getElementById("arrOrDept").value;
  var deviation = document.getElementById("deviation").value;
  var error_message = document.getElementById("error_message");

  error_message.style.padding = "10px";

  var text;
  if(name.length < 5){
    text = "Please Enter valid Name";
    error_message.innerHTML = text;
    return false;
  }
  if(subject.length < 10){
    text = "Please Enter Correct Subject";
    error_message.innerHTML = text;
    return false;
  }
  if(isNaN(phone) || phone.length != 10){
    text = "Please Enter valid Phone Number";
    error_message.innerHTML = text;
    return false;
  }
  if(email.indexOf("@") == -1 || email.length < 6){
    text = "Please Enter valid Email";
    error_message.innerHTML = text;
    return false;
  }
  if(message.length <= 140){
    text = "Please Enter More Than 140 Characters";
    error_message.innerHTML = text;
    return false;
  }
  alert("Form Submitted Successfully!");
  return true;
}

var MAP_ID = null;

$(function () {

  var searchParams = new URLSearchParams(window.location.search);
  var mapIDParam = searchParams.get('mapID');
  MAP_ID = parseInt(mapIDParam);
});


$("#start-station").change(function () {

  var selectedStart = $("#start-station option:selected").text();

  updateEndStations(ALL_STATIONS_NAMES);
  removeStationFromEndList(selectedStart);
});
// $('#btn').click(function(){
//   var selectedOption = $("#input option:selected");
//   var name = selectedOption.text();
//   if(name){
//     $('<li class="item">').text(name).prependTo('.list');
//   }
//   $('#input').val('');
//
//   updateStationsList(ALL_STATIONS_DICT);
//
// });

function removeStationFromEndList(station){
  $("select#end-station option:contains('" + station + "')").remove();
}


function updateStartStations(listOfStations){
  $("select#start-station option").remove();
  $("select#start-station").append("<option disabled selected>Select Start Station</option>");

  listOfStations.forEach(function (stationName) {
    $("select#start-station").append("<option>" + stationName + "</option>");
  });
}

function updateEndStations(listOfStations){
  $("select#end-station option").remove();
  $("select#end-station").append("<option disabled selected>Select End Station</option>");

  listOfStations.forEach(function (stationName) {
    $("select#end-station").append("<option>" + stationName + "</option>");

  });
}
function updateStationsOptionListWith(listOfStations){
  updateStartStations(listOfStations);
  updateEndStations(listOfStations);



}


var ALL_STATIONS_NAMES = [];
function getAllStations() {

  var data = {mapID: MAP_ID};

  $.ajax({
    url: "../../../stations",
    method: "GET",
    data: data,
    dataType: "json",
    success: function (stationsData){
      // ALL_STATIONS_DICT = {};

      ALL_STATIONS_NAMES = [];
      stationsData.forEach(function (station) {
        var stationName = station.name;
        // var accessableStations = station.reachableStations;

        ALL_STATIONS_NAMES.push(stationName);

        // ALL_STATIONS_DICT[stationName] = accessableStations;
        // var allStations = Object.keys(ALL_STATIONS_DICT);

      });
      updateStationsOptionListWith(ALL_STATIONS_NAMES);
      // alert("success!")
    },
    error: function (data) {
      alert("error getting stations!");
    }
  });
}

$(document).ready(function(){
  $("#add-ride-btn").click(function () {
    var modal1 = $('#close', window.parent.document);
    modal1.click();

  });

});
// $("#myform").submit(function () {
//
//   alert("asdasdasd");
//   return false;
// });

$("#myform").submit(function () {
  var time = $("#time").val();
  var startStation = $("#start-station option:selected").text();
  var endStation = $("#end-station option:selected").text();
  var day = $("#day").val();
  var departOrArrive = $("#arrive-depart option:selected").text();
  var diffMintues = $("#deviation").val();
  var connections = $("#max-switches").val();

  var searchParams = new URLSearchParams(window.location.search);
  var mapIDParam = parseInt(searchParams.get('mapID'));

  var requestData = {
    time: time,
    day: day,
    startStation: startStation,
    endStation: endStation,
    departOrArrive: departOrArrive,
    diffMintues: diffMintues,
    mapID: mapIDParam,
    connections: connections
  };

  // alert("sending request with data" + JSON.stringify(requestData));
  $.ajax({
    url: "../../../tremp",
    method: "POST",
    data: requestData,
    dataType: "json",
    success: function (data){
      $("div#visibleHandler").css("visibility", "hidden").css("display", "none");
      $("div#showMe").css("visibility", "visible").css("display", "inline-block");
      // alert(JSON.stringify(data));
    },
    error: function (data) {
      // alert(JSON.stringify(data));
    }
  });

  return false;
});



$(function () {
  getAllStations();
});
