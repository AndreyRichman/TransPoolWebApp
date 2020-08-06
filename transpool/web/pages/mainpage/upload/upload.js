var droppedFiles = false;
var originalfileName = '';
var newfileName = '';
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
        originalfileName = droppedFiles[0]['name'];
        // $('.filename').html(fileName);
        $('.dropzone .upload').hide();
    });

$button.bind('click', function() {
    startUpload();
});

$("input:file").change(function (){
    // fileName = $(this)[0].files[0].name;
    originalfileName = $(this)[0].files[0].name;
    newfileName = $name.val();
    console.log(originalfileName);
    console.log(newfileName);
    file = $(this)[0].files[0];
    // $('.filename').html(fileName);
    $('.dropzone .upload').hide();
});

function startUpload() {

    var formData = new FormData();
    if (newfileName.length === 0)
    {
        formData.append(originalfileName, file);
    }
    else
    {
        formData.append(newfileName, file);
    }


    if (!uploading && originalfileName != '' ) {
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
        uploading = true;
        $button.html('Uploading');
        $dropzone.fadeOut();
        $syncing.addClass('active');
        $done.addClass('active');
        $bar.addClass('active');
        document.getElementById('file-name').style.visibility = "hidden" ;
        timeoutID = window.setTimeout(function (){
            $button.html('Uploading.');
        }, 1000);
        timeoutID = window.setTimeout(function (){
            $button.html('Uploading..');
        }, 2000);
        timeoutID = window.setTimeout(function (){
            $button.html('Uploading...');
        }, 3000);

        timeoutID = window.setTimeout(function (){
            $button.html('Uploading....');
        }, 4000);
        timeoutID = window.setTimeout(function (){
            $("#result").html("<h2 style='text-align: center; color: black'>Map Was Uploaded</h2>");
            $("#upload-btn").hide();
            $(".title").hide();
        }, 5000);
}

function showError(r) {
    if (!uploading && originalfileName != '' ) {
        uploading = true;
        $button.html('Uploading');
        $dropzone.fadeOut();
        $syncing.addClass('active');
        $done.addClass('active');
        $bar.addClass('active');
        document.getElementById('file-name').style.visibility = "hidden";
        $dropzone.fadeOut();
        timeoutID = window.setTimeout(function () {
            $button.html('Uploading.');
        }, 1000);
        timeoutID = window.setTimeout(function () {
            $button.html('Uploading..');
        }, 2000);
        timeoutID = window.setTimeout(function () {
            $button.html('Uploading...');
        }, 3000);

        timeoutID = window.setTimeout(function () {
            $button.html('Uploading....');
        }, 4000);
        timeoutID = window.setTimeout(function () {
            $("#result").html(`<h2 style='text-align: center; color: red'>${r.responseText}</h2>`);
            console.log(r.responseText);
            $("#upload-btn").hide();
            $(".title").hide();
        }, 5000);

    }
}



