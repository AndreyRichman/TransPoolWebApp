var $table = $('#table-hover');

$(function () {
    $.ajax({
        url: '../../../transaction',
        success: function (data) {
            $.each(data.walet.transactions, function (i, item) {
                $table.append('<tr>');
                $table.append('<td class="text-left">'+ item.actionType +'</td>' );
                $table.append('<td class="text-left">'+ item.date +'</td>' );
                $table.append('<td class="text-left">'+ item.user +'</td>' );
                $table.append('<td class="text-left">'+ item.amount  +'</td>' );
                $table.append('<td class="text-left">'+ item.beforeBalance +'</td>' );
                $table.append('<td class="text-left">'+ item.afterBalance +'</td>' );
                $table.append('</tr>');
            })
        }
    });
});


