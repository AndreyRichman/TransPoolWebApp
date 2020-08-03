// $(document).ready(function(){
//     var textInput = $('#input').val();
//
//     // // SHOW PLUS BUTTON
//     // $("#input").keyup(function() {
//     //     if ($(this).val()) {
//     //         $("#btn").css( "display", "inline-block" );
//     //     }
//     //     else {
//     //         $("#btn").hide();
//     //     }
//     // });
//
//     // SHOW PLUS BUTTON
//     $("#input").change(function() {
//         // if ($(this).val()) {
//             $("#btn").css( "display", "inline-block" );
//         // }
//         // else {
//         //     $("#btn").hide();
//         // }
//     });
//
//
//     $("#btn").click(function () {
//         $(this).hide();
//     });
//
//     // REMOVE ITEM FROM INPUT ONCE IT WAS ADDED
//     $('#btn').click(function(){
//         var selectedOption = $("#input option:selected");
//         var name = selectedOption.text();
//         var id = selectedOption.attr('value'); // .attr("id");
//         // var textInput = $("#input option:selected").text();//  "please";// $('#input').val();
//         // $('<li class="item">').val(id).text(name).prependTo('.list');
//         $('<li class="item">').text(name).prependTo('.list');
//         $('#input').val('');
//
//         // $('.item').click(function() {
//         //     $(this).remove();
//         // });
//
//     });
//
//     // // REMOVE ITEM FROM INPUT ONCE IT WAS ADDED
//     // $('#btn').click(function(){
//     //     var textInput = "please";// $('#input').val();
//     //     $('<li class="item">').text(textInput).prependTo('.list');
//     //     $('#input').val('');
//     //
//     //     $('.item').click(function() {
//     //         $(this).remove();
//     //     });
//     //
//     // });
//
//
//     // // show item in the selected list
//     // $('#input').keypress(function (e) {
//     //     var textInput = "pleasee";// $('#input').val();
//     //     if (e.which == 13) {
//     //         $('<li class="item">').text(textInput).prependTo('.list');
//     //         $('#input').val('');
//     //
//     //         $('.item').click(function() {
//     //             $(this).remove();
//     //         });
//     //
//     //         return false;
//     //     }
//     //
//     // });
// });
//
//
//
// // DROP DOWN LIST
// //TOGGLING NESTED ul
// $(".drop-down .selected a").click(function() {
//     $(".drop-down .options ul").toggle();
// });
//
// //SELECT OPTIONS AND HIDE OPTION AFTER SELECTION
// $(".drop-down .options ul li a").click(function() {
//     var text = $(this).html();
//     $(".drop-down .selected a span").html(text);
//     $(".drop-down .options ul").hide();
// });
//
//
// //HIDE OPTIONS IF CLICKED ANYWHERE ELSE ON PAGE
// $(document).bind('click', function(e) {
//     var $clicked = $(e.target);
//     if (! $clicked.parents().hasClass("drop-down"))
//         $(".drop-down .options ul").hide();
// });
//
//
//
// $("#input").prop("selectedIndex", -1);
//
//
//
//  // TIME START HERE
//
// // TIME ENDS HERE