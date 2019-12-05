var TableEditable = function () {

    return {

        //main function to initiate the module
        init: function () {
            function restoreRow(oTable, nRow) {
                var aData = oTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);

                for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
                    oTable.fnUpdate(aData[i], nRow, i, false);
                }

                oTable.fnDraw();
            }

            function editRow(oTable, nRow) {
                var aData = oTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);
                jqTds[1].innerHTML = '<input type="text" class="m-wrap small" value="' + aData[1] + '">';
                jqTds[2].innerHTML = '<input type="text" class="m-wrap small" value="' + aData[2] + '">';
                jqTds[3].innerHTML = '<input type="text" class="m-wrap small" value="' + aData[3] + '">';
                jqTds[4].innerHTML = '<input type="text" class="m-wrap small" value="' + aData[4] + '">';
                jqTds[5].innerHTML = '<input type="text" class="m-wrap small" value="' + aData[5] + '">';
                jqTds[6].innerHTML = '<input type="text" class="m-wrap small" value="' + aData[6] + '">';
                jqTds[7].innerHTML = '<input type="text" class="m-wrap small" value="' + aData[7] + '">';
                jqTds[8].innerHTML = '<input type="text" class="m-wrap small" value="' + aData[8] + '">';
                jqTds[9].innerHTML = '<input type="text" class="m-wrap small" value="' + aData[9] + '">';
                jqTds[10].innerHTML = '<input type="text" class="m-wrap small" value="' + aData[10] + '">';
                jqTds[11].innerHTML = '<input type="text" class="m-wrap small" value="' + aData[11] + '">';
                jqTds[12].innerHTML = '<a class="edit" href="">Save</a>';
                jqTds[13].innerHTML = '<a class="cancel" href="">Cancel</a>';
            }
            
            function addNewRow(oTable, nRow) {
                var aData = oTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);
                jqTds[0].innerHTML = '<input type="text" class="m-wrap small" value="' + aData[0] + '">';
                jqTds[1].innerHTML = '<input type="text" class="m-wrap small" value="' + aData[1] + '">';
                jqTds[2].innerHTML = '<input type="text" class="m-wrap small" value="' + aData[2] + '">';
                jqTds[3].innerHTML = '<input type="text" class="m-wrap small" value="' + aData[3] + '">';
                jqTds[4].innerHTML = '<input type="text" class="m-wrap small" value="' + aData[4] + '">';
                jqTds[5].innerHTML = '<input type="text" class="m-wrap small" value="' + aData[5] + '">';
                jqTds[6].innerHTML = '<input type="text" class="m-wrap small" value="' + aData[6] + '">';
                jqTds[7].innerHTML = '<input type="text" class="m-wrap small" value="' + aData[7] + '">';
                jqTds[8].innerHTML = '<input type="text" class="m-wrap small" value="' + aData[8] + '">';
                jqTds[9].innerHTML = '<input type="text" class="m-wrap small" value="' + aData[9] + '">';
                jqTds[10].innerHTML = '<input type="text" class="m-wrap small" value="' + aData[10] + '">';
                jqTds[11].innerHTML = '<input type="text" class="m-wrap small" value="' + aData[11] + '">';
                jqTds[12].innerHTML = '<a class="editAdd" href="">Save</a>';
                jqTds[13].innerHTML = '<a class="cancel" href="">Cancel</a>';
            }
            
            function saveRow(oTable, nRow) {
                var jqInputs = $('input', nRow);
                oTable.fnUpdate(jqInputs[0].value, nRow, 1, false);
                oTable.fnUpdate(jqInputs[1].value, nRow, 2, false);
                oTable.fnUpdate(jqInputs[2].value, nRow, 3, false);
                oTable.fnUpdate(jqInputs[3].value, nRow, 4, false);
                oTable.fnUpdate(jqInputs[4].value, nRow, 5, false);
                oTable.fnUpdate(jqInputs[5].value, nRow, 6, false);
                oTable.fnUpdate(jqInputs[6].value, nRow, 7, false);
                oTable.fnUpdate(jqInputs[7].value, nRow, 8, false);
                oTable.fnUpdate(jqInputs[8].value, nRow, 9, false);
                oTable.fnUpdate(jqInputs[9].value, nRow, 10, false);
                oTable.fnUpdate(jqInputs[10].value, nRow, 11, false);
                oTable.fnUpdate(jqInputs[11].value, nRow, 12, false);
                oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow,13, false);
                oTable.fnUpdate('<a class="delete" href="">Delete</a>', nRow, 14, false);
                oTable.fnDraw();
                $.post("/crontab/doAdd", {id:nRow.cells[0].id,
				                			messageName:nRow.cells[0].innerText,
				                			exchangeName:jqInputs[0].value,
                							priority:jqInputs[1].value,
                							useDelayRetry:jqInputs[2].value,
                							delayTime:jqInputs[3].value,
                							maxRetryCount:jqInputs[4].value
                						},
	                    function(data){
	                        var u=JSON.parse(data);   
	                        if(u.msg=="true"){
	                          alert("成功");
	                          location.reload();
	                        }else{
	                          alert(u.error);
	                        }
	                    });
            }
            
            
            
            function saveNewRow(oTable, nRow) {
                var jqInputs = $('input', nRow);
                oTable.fnUpdate(jqInputs[0].value, nRow, 0, false);
                oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
                oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
                oTable.fnUpdate(jqInputs[3].value, nRow, 3, false);
                oTable.fnUpdate(jqInputs[4].value, nRow, 4, false);
                oTable.fnUpdate(jqInputs[5].value, nRow, 5, false);
                oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow,6, false);
                oTable.fnUpdate('<a class="delete" href="">Delete</a>', nRow, 7, false);
                oTable.fnDraw();
                $.post("/jmsg/configAdd", {id:nRow.cells[0].id,
				                			messageName:jqInputs[0].value,
				                			exchangeName:jqInputs[1].value,
                							priority:jqInputs[2].value,
                							useDelayRetry:jqInputs[3].value,
                							delayTime:jqInputs[4].value,
                							maxRetryCount:jqInputs[5].value
                						},
	                    function(data){
	                        var u=JSON.parse(data);   
	                        if(u.msg=="true"){
	                          alert("成功");
	                        }else{
	                          alert(u.error);
	                        }
	                    });
            }

            function cancelEditRow(oTable, nRow) {
                var jqInputs = $('input', nRow);
                oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
                oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
                oTable.fnUpdate(jqInputs[3].value, nRow, 3, false);
                oTable.fnUpdate(jqInputs[4].value, nRow, 4, false);
                oTable.fnUpdate(jqInputs[5].value, nRow, 5, false);
                oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 6, false);
                oTable.fnDraw();
            }

            var oTable = $('#sample_editable_3').dataTable({
                "aLengthMenu": [
                    [5, 15, 20, -1],
                    [5, 15, 20, "All"] // change per page values here
                ],
                // set the initial value
                "iDisplayLength": 5,
                "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
                "sPaginationType": "bootstrap",
                "oLanguage": {
                    "sLengthMenu": "_MENU_ records per page",
                    "oPaginate": {
                        "sPrevious": "Prev",
                        "sNext": "Next"
                    }
                },
                "aoColumnDefs": [{
                        'bSortable': false,
                        'aTargets': [0]
                    }
                ]
            });

            jQuery('#sample_editable_3_wrapper .dataTables_filter input').addClass("m-wrap medium"); // modify table search input
            jQuery('#sample_editable_3_wrapper .dataTables_length select').addClass("m-wrap small"); // modify table per page dropdown
            jQuery('#sample_editable_3_wrapper .dataTables_length select').select2({
                showSearchInput : false //hide search box with special css class
            }); // initialzie select2 dropdown

            var nEditing = null;

            $('#sample_editable_3_new').click(function (e) {
                e.preventDefault();
                var aiNew = oTable.fnAddData(['', '', '', '','','',
                        '<a class="edit" href="">Edit</a>', '<a class="cancel" data-mode="new" href="">Cancel</a>'
                ]);
                var nRow = oTable.fnGetNodes(aiNew[0]);
                addNewRow(oTable, nRow);
                nEditing = nRow;
            });

            $('#sample_editable_3 a.delete').live('click', function (e) {
                e.preventDefault();

                if (confirm("Are you sure to delete this row ?") == false) {
                    return;
                }

                var nRow = $(this).parents('tr')[0];
                oTable.fnDeleteRow(nRow);
                $.post("/jmsg/configDel", {id:nRow.cells[0].id},
	                    function(data){
	                        var u=JSON.parse(data);   
	                        if(u.msg=="true"){
	                          alert("成功");
	                          location.reload();
	                        }else{
	                          alert(u.error);
	                        }
	                    });
            });

            $('#sample_editable_3 a.cancel').live('click', function (e) {
                e.preventDefault();
                if ($(this).attr("data-mode") == "new") {
                    var nRow = $(this).parents('tr')[0];
                    oTable.fnDeleteRow(nRow);
                } else {
                    restoreRow(oTable, nEditing);
                    nEditing = null;
                }
            });
            
            $('#sample_editable_3 a.editAdd').live('click', function (e) {
                e.preventDefault();

                /* Get the row as a parent of the link that was clicked on */
                var nRow = $(this).parents('tr')[0];

                if (nEditing !== null && nEditing != nRow) {
                    /* Currently editing - but not this row - restore the old before continuing to edit mode */
                    restoreRow(oTable, nEditing);
                    editRow(oTable, nRow);
                    nEditing = nRow;
                } else if (nEditing == nRow && this.innerHTML == "Save") {
                    /* Editing this row and want to save it */
                	saveNewRow(oTable, nEditing);
                    nEditing = null;    
                } else {
                    /* No edit in progress - let's start one */
                    editRow(oTable, nRow);
                    nEditing = nRow;
                }
            });
            
            
            $('#sample_editable_3 a.edit').live('click', function (e) {
                e.preventDefault();

                /* Get the row as a parent of the link that was clicked on */
                var nRow = $(this).parents('tr')[0];

                if (nEditing !== null && nEditing != nRow) {
                    /* Currently editing - but not this row - restore the old before continuing to edit mode */
                    restoreRow(oTable, nEditing);
                    editRow(oTable, nRow);
                    nEditing = nRow;
                } else if (nEditing == nRow && this.innerHTML == "Save") {
                    /* Editing this row and want to save it */
                    saveRow(oTable, nEditing);
                    nEditing = null;    
                } else {
                    /* No edit in progress - let's start one */
                    editRow(oTable, nRow);
                    nEditing = nRow;
                }
            });
        }

    };

}();