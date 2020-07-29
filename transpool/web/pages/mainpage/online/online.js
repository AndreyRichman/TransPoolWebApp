// how many list items to display = max-height of the container div
var items = 5,
    height = $('ul li').outerHeight() * items;
$('.list-container').css('max-height', + height);

// Control how to close the list
$(document).mouseup(function (e){
    var triggerElem = $('.list-container, label');
    if (!triggerElem.is(e.target)) {
        $('#trigger').prop('checked', false);
    };
});