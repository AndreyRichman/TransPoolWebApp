var droppedFiles = false;
var fileName = '';
var file;
var $dropzone = $('.dropzone');
var $button = $('.upload-btn');
var uploading = false;
var $syncing = $('.syncing');
var $done = $('.done');
var $bar = $('.bar');
var $name = $('#file-name');
var timeOut;


$dropzone.on('drag dragstart dragend dragover dragenter dragleave drop', function(e) {
    e.preventDefault();
    e.stopPropagation();
})
    .on('dragover dragenter', function() {
        $dropzone.addClass('is-dragover');
    })
    .on('dragleave dragend drop', function() {
        $dropzone.removeClass('is-dragover');
    })
    .on('drop', function(e) {
        droppedFiles = e.originalEvent.dataTransfer.files;
        fileName = droppedFiles[0]['name'];
        $('.filename').html(fileName);
        $('.dropzone .upload').hide();
    });

$button.bind('click', function() {
    startUpload();
});

$("input:file").change(function (){
    // fileName = $(this)[0].files[0].name;
    $name.value = $(this)[0].files[0].name;
    fileName = $name.val();
    console.log(fileName);
    file = $(this)[0].files[0];
    $('.filename').html(fileName);
    $('.dropzone .upload').hide();
});

function startUpload() {

    var formData = new FormData();
    formData.append(fileName, file);

    if (!uploading) {
        $.ajax({
            type: 'POST',
            url: '/transpool_war_exploded/map',
            data: formData,
            processData: false,
            contentType: false,
            timeout: 4000,
            success: function (r) {
                showDone(r);

            },
            error: function (r) {
                showError(r);
            }
        })
    }



}

function showDone() {
    if (!uploading && fileName != '' ) {
        uploading = true;
        $button.html('Uploading...');
        $dropzone.fadeOut();
        $syncing.addClass('active');
        $done.addClass('active');
        $bar.addClass('active');
        timeoutID = window.setTimeout(function (){}, 3200);
        $("#result").html("<h2 style='text-align: center; color: green'>Upload succedded</h2>");
        $("#upload-btn").hide();
        $(".title").hide();
    }


}

function showError(r) {
    $dropzone.fadeOut();
    $("#result").html(`<h2 style='text-align: center; color: red'>${r.responseText}</h2>`);
    console.log(r.responseText);
    $("#upload-btn").hide();
    $(".title").hide();
}



