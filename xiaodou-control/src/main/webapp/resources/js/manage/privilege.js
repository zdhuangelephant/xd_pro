$(function(){
	
	$('#save').on('show.bs.modal', function(event) {
		// triggered
		// the modal
		// from
		// data-* attributes
		var modal = $(this);
		var tree = getSelectPrivilegeTree(0,0);
		modal.find("option[value=0]").nextAll().remove();
		modal.find('#save_parent_id').append(tree);
	});
	$('#saveNode').on('show.bs.modal', function(event) {
		var button = $(event.relatedTarget); // Button that
		// triggered
		// the modal
		var privilegeId = button.data('sid'); // Extract info
		// from
		// data-* attributes
		var modal = $(this);
		modal.find('.modal-title').text('添加子权限信息 - 权限id:' + privilegeId);
		var tree = getSelectPrivilegeTree(privilegeId,1);
		modal.find("option[value=0]").nextAll().remove();
		modal.find('#save_node_parent_id').append(tree);
	});
	$('#update').on('show.bs.modal', function(event) {
		var button = $(event.relatedTarget); // Button that
		// triggered
		// the modal
		var privilegeId = button.data('sid'); // Extract info
		var display = button.data("display");
		var name = button.data("name");
		var url = button.data("url");
		var icon = button.data("icon");
		// from
		// data-* attributes
		var modal = $(this);
		modal.find('.modal-title').text('修改权限信息 - 权限id:' + privilegeId);
		var tree = getSelectPrivilegeTree(privilegeId,0);
		modal.find("option[value=0]").nextAll().remove();
		modal.find('#update_parent_id').append(tree);
		$("input:radio[name='display'][value="+display+"]").attr('checked','true');
		modal.find('#update_name').val(name);
		modal.find('#update_url').val(url);
		modal.find('#update_icon').val(icon);
		modal.find('#update_id').val(privilegeId);
	});
});

function getSelectPrivilegeTree(privilegeId,type){
	var tree = null;
	$.ajax({
		cache : true,
		type : "POST",
		async: false,
		url : "/manage/get_select_privilege_tree",
		data : {
			"privilegeId":privilegeId,
			"type":type
		},// 通过序列化表单值，创建 URL 编码文本字符串。
		dataType : "text",
		error : function() {
			$.fn.modalMsg("异常", "error");
		},
		success : function(data) {
			if (!isEmpty(data)) {
				//location.reload();
				tree = data;
			} else {
				$.fn.modalAlert("添加失败", "error");
			}
		}
	});
	return tree;
}

function order(){
    var orders = "";
    $("input[name='sortOrder']").each(function(){
        var id = $(this).attr("id");
        var value = $(this).val();
        var preValue = $(this).attr("prevalue");
        if(value!=preValue){
            orders = orders + id + ":" + value + ";";
        };
    });
    if(!isEmpty(orders)){
        $.post("/manage/sort_privilege",{"orders":orders},
                function(data){
		        	if ("true" == data) {
		        		$.fn.modalMsg("排序成功", "success");
						location.reload();
					} else {
						$.fn.modalMsg("排序成功", "error");
					};
                });
    };
}

function removePrivilege(privilegeId){
	// 询问框
	layer.confirm('确定要删除该该权限？', {
		btn : [ '确认', '取消' ]
	// 按钮
	}, function() {
		$.ajax({
			cache : true,
			type : "POST",
			url : "/manage/remove_privilege",
			data : {
				"privilegeId" : privilegeId
			},
			dataType : "text",
			error : function() {
				$.fn.modalMsg("异常", "error");
			},
			success : function(data) {
				if ("true" == data) {
					location.reload();
				} else {
					$.fn.modalAlert("删除失败", "error");
				}
			},
		});
	}, function() {
	});
}

function savePrivilege(formId){
	$("#"+formId+"Button").attr("disabled", true);
	$.post("/manage/save_privilege",$("#"+formId).serialize(),
            function(data){
				if ("true" == data) {
					$.fn.modalMsg("添加成功", "success");
					location.reload();
				} else {
					$.fn.modalAlert("添加失败", "success");
					$("#"+formId+"Button").attr("disabled", false);
				}
            });
}

function updatePrivilege(formId){
	$("#"+formId+"Button").attr("disabled", true);
	$.post("/manage/update_privilege",$("#"+formId).serialize(),
            function(data){
				if ("true" == data) {
					$.fn.modalMsg("添加成功", "success");
					location.reload();
				} else {
					$.fn.modalAlert("添加失败", "success");
					$("#"+formId+"Button").attr("disabled", false);
				}
            });
}