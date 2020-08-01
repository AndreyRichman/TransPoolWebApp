var MAP_ID = null;

$(function () {

  var searchParams = new URLSearchParams(window.location.search);
  var mapIDParam = searchParams.get('mapID');
  MAP_ID = parseInt(mapIDParam);
});


function validate(){
  var addStation = document.getElementById("addStation").value;
  var removeStation = document.getElementById("removeStation").value;
  var day = document.getElementById("day").value;
  var time = document.getElementById("time").value;
  var ppk = document.getElementById("ppk").value;
  var capacity = document.getElementById("capacity").value;
  var ppk = document.getElementById("ppk").value;
  var reputable = document.getElementById("reputable").value;
  var path = document.getElementById("path").value;
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

$("#add-ride-btn").click(function () {
  var time = $("#time").val();
  var ppk = $("#ppk").val();
  if (!ppk) {
    ppk = 50;
  }
  var capacity = $("#capacity").val();
  if (!capacity) {
    capacity = 5;
  }
  var schedule = $("select#schedule.add-input").val();

  var selectedStations = $("ol#selected-stations-list li").map(function() {
    return $(this).text();
  }).toArray().reverse();

  var day = $("#day").val();

  var searchParams = new URLSearchParams(window.location.search);
  var mapIDParam = parseInt(searchParams.get('mapID'));

  requestData = {
    time: time,
    day: day,
    ppk: ppk,
    capacity: capacity,
    schedule: schedule,
    stations: selectedStations,
    mapID: mapIDParam
  };

  alert("sending request with data" + JSON.stringify(requestData));
  $.ajax({
    url: "/transpool_war_exploded/ride",
    // timeout: 2000,
    method: "POST",
    data: requestData,
    dataType: "json",
    success: function (data){
      alert(JSON.stringify(data));
    },
    error: function (data) {
      alert(JSON.stringify(data));
    }
  });
});

function validate2(){

}
var ALL_STATIONS_DICT = {};
function getAllStations() {

  var data = {mapID: MAP_ID};

  $.ajax({
    url: "/transpool_war_exploded/stations",
    method: "GET",
    data: data,
    dataType: "json",
    success: function (stationsData){
      ALL_STATIONS_DICT = {};

      stationsData.forEach(function (station) {
        var stationName = station.name;
        var accessableStations = station.reachableStations;

        ALL_STATIONS_DICT[stationName] = accessableStations;

        updateStationsList(ALL_STATIONS_DICT);
      });
      // alert("success!")
    },
    error: function (data) {
      alert("error getting stations!");
    }
  });
}

// function getCurrentlySelectedStation() {
//   return $("ol#selected-stations-list li")[0].value;
// }

// function removeAlreadySelectedStations(listOfStationsToClean){

  // var alreadySelectedStations = [];
  // var stationsLI = $("ol#selected-stations-list li").each(function () {
  //   alreadySelectedStations.push($(this).val());
  // });


// }

function clearOptionStationsList(){
  $("select.stations-list option").remove();
}

function updateStationsOptionListWith(listOfStations){
  clearOptionStationsList();
  listOfStations.forEach(function (stationName) {
    $("select.stations-list").append("<option>" + stationName + "</option>");
    // $('<option></option>').text(stationName).prependTo('.stations-list');

  });
}

function setStationListAccordingToLastStation(dictOfAllStations){
  var addedStations = $("ol#selected-stations-list li").map(function() {
    return $(this).text();
  }).toArray();

  var lastSelectedStation = addedStations[0];

  var reachableFromStation = dictOfAllStations[lastSelectedStation];
  var stationsToShow = reachableFromStation.filter(n => !addedStations.includes(n));

  updateStationsOptionListWith(stationsToShow);
}

function setAllStationsInList(dictOfAllStations){
  var allStations = Object.keys(dictOfAllStations);
  updateStationsOptionListWith(allStations);
}

function updateStationsList(dictOfAllStations){
  var numOfSelected = $("ol#selected-stations-list li").length;

  if (numOfSelected > 0){
    setStationListAccordingToLastStation(dictOfAllStations);
  }
  else {
    setAllStationsInList(dictOfAllStations);
  }
}

$(function () {
  getAllStations();
});



// STATIONS JS START


$("#input").prop("selectedIndex", -1);

$(document).ready(function(){
  // var textInput = $('#input').val();

  // // SHOW PLUS BUTTON
  // $("#input").keyup(function() {
  //     if ($(this).val()) {
  //         $("#btn").css( "display", "inline-block" );
  //     }
  //     else {
  //         $("#btn").hide();
  //     }
  // });

  // SHOW PLUS BUTTON
  // $("#input").change(function() {
  //   // if ($(this).val()) {
  //   $("#btn").css( "display", "inline-block" );
  //   // }
  //   // else {
  //   //     $("#btn").hide();
  //   // }
  // });


  // $("#btn").click(function () {
  //   $(this).hide();
  // });

  // REMOVE ITEM FROM INPUT ONCE IT WAS ADDED
  $('#btn').click(function(){
    var selectedOption = $("#input option:selected");
    var name = selectedOption.text();
    if(name){
      $('<li class="item">').text(name).prependTo('.list');
    }
    $('#input').val('');

    updateStationsList(ALL_STATIONS_DICT);

  });

  // // REMOVE ITEM FROM INPUT ONCE IT WAS ADDED
  // $('#btn').click(function(){
  //     var textInput = "please";// $('#input').val();
  //     $('<li class="item">').text(textInput).prependTo('.list');
  //     $('#input').val('');
  //
  //     $('.item').click(function() {
  //         $(this).remove();
  //     });
  //
  // });


  // // show item in the selected list
  // $('#input').keypress(function (e) {
  //     var textInput = "pleasee";// $('#input').val();
  //     if (e.which == 13) {
  //         $('<li class="item">').text(textInput).prependTo('.list');
  //         $('#input').val('');
  //
  //         $('.item').click(function() {
  //             $(this).remove();
  //         });
  //
  //         return false;
  //     }
  //
  // });
});



// DROP DOWN LIST
//TOGGLING NESTED ul
$(".drop-down .selected a").click(function() {
  $(".drop-down .options ul").toggle();
});

//SELECT OPTIONS AND HIDE OPTION AFTER SELECTION
$(".drop-down .options ul li a").click(function() {
  var text = $(this).html();
  $(".drop-down .selected a span").html(text);
  $(".drop-down .options ul").hide();
});


//HIDE OPTIONS IF CLICKED ANYWHERE ELSE ON PAGE
$(document).bind('click', function(e) {
  var $clicked = $(e.target);
  if (! $clicked.parents().hasClass("drop-down"))
    $(".drop-down .options ul").hide();
});


// STATIONS JS END