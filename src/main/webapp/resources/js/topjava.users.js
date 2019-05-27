// $(document).ready(function () {
$(function () {
        makeEditable({
                ajaxUrl: "ajax/admin/users/",
                datatableApi: $("#datatable").DataTable({
                    "paging": false,
                    "info": true,
                    "columns": [
                        {
                            "data": "name"
                        },
                        {
                            "data": "email"
                        },
                        {
                            "data": "roles"
                        },
                        {
                            "data": "enabled"
                        },
                        {
                            "data": "registered"
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
                            "asc"
                        ]
                    ]
                })
            }
        );
    }
);

function editEnable(id) {
    let enable=false;
    function editEnableById(id) {
        $.ajax({
            url: "ajax/admin/users/" + id + "/" + enable,
            type: "PUT"
        }).done(function () {
            updateTable();
            if(enable)
                successNoty("User enable");
            else
                successNoty("User don't enable");
        });
    }

    if (confirm('Are you sure?')) {
        enable = $('#'+id).is(':checked');
        editEnableById(id);
    }
}