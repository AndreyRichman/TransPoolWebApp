var droppedFiles = false;
var fileName = '';
var file;
var $dropzone = $('.dropzone');
var $button = $('.upload-btn');
var uploading = false;
var $syncing = $('.syncing');
var $done = $('.done');
var $bar = $('.bar');
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
    fileName = $(this)[0].files[0].name;
    file = $(this)[0].files[0];
    $('.filename').html(fileName);
    $('.dropzone .upload').hide();
});

function startUpload() {

    var formData = new FormData();
    formData.append("Matan kochavi", file);

    $.ajax({
        type: 'POST',
        url: '/transpool_war_exploded/map',
        data: formData,
        processData: false,
        contentType: false,
        timeout: 4000,
        success: function (r) {
            $("#result").text(r);
        },
        error: function () {
            alert('error');
        }
    })

    if (!uploading && fileName != '' ) {
        uploading = true;
        $button.html('Uploading...');
        $dropzone.fadeOut();
        $syncing.addClass('active');
        $done.addClass('active');
        $bar.addClass('active');
        timeoutID = window.setTimeout(showDone, 3200);
    }
}

function showDone() {
    $button.html('Done');
}

