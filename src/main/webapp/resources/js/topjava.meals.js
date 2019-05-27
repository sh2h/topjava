$(function () {
    makeEditable({
        ajaxUrl:"ajax/profile/meals/",
        datatableApi: $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    });
    $("#filter").click(function () {
        isFilter=true;
        updateTable();
    })
    }
);
function updateTableWithFilter(){
    $.get(context.ajaxUrl+"filter",$("#filterForm").serialize(),function (data) {
        context.datatableApi.clear().rows.add(data).draw();
        successNoty("Filtered");
    });
}
function clearFilter() {
   $("input[type=date]").val("");
   $("input[type=time]").val("");
   isFilter=false;
   updateTable();
}

