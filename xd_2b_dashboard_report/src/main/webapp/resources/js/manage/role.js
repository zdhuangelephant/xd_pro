$(function(){
	
	$('#update').on('show.bs.modal', function(event) {
		var button = $(event.relatedTarget); // Button that
		// triggered
		// the modal
		var roleId = button.data('sid'); // Extract info
		var roleName = button.data('role_name');
		var description = button.data('description');
		var validStatus = button.data('valid_status');
		// from
		// data-* attributes
		var modal = $(this);
		modal.find('.modal-title').text('修改角色信息 - 角色id:' + roleId);
		modal.find('#update_id').val(roleId);
		modal.find('#update_roleName').val(roleName);
		modal.find('#update_description').val(description);
		if(validStatus==0)
			$("#validStatus1").attr("checked", true);
		else
			$("#validStatus2").attr("checked", true);
	});
	
	$("#saveRole").click(function(){
		if(isEmpty($("#save_roleName").val())){
			layer.msg('请填写角色名称', {
				time : 2000, // 2s后自动关闭
				icon : 1
			});
			return;
		}
		$(this).attr("disabled", true);
		$.ajax({
			cache : true,
			type : "POST",
			url : "/manage/save_role",
			data : $("#saveForm").serialize(),// 通过序列化表单值，创建 URL 编码文本字符串。
			dataType : "text",
			error : function() {
				layer.msg('添加失败', {
					time : 2000, // 2s后自动关闭
					icon : 1
				});
				$(this).attr("disabled", false);
			},
			success : function(data) {
				if ("true" == data) {
					location.reload();
				} else {
					layer.msg('添加失败', {
						time : 2000, // 2s后自动关闭
						icon : 1
					});
					$(this).attr("disabled", false);
				}
			},
		});
		
	});
	
	$('#set').on('show.bs.modal', function(event) {
		var button = $(event.relatedTarget); // Button that
		// triggered
		// the modal
		var roleId = button.data('sid'); // Extract info
		// from
		// data-* attributes
		var modal = $(this);
		modal.find('.modal-title').text('修改角色权限 - 角色id:' + roleId);
		var table = getRolePrivilegeTable(roleId);
		modal.find('#tbody').html(table);
		modal.find('#roleId').val(roleId);
	});
});

function getRolePrivilegeTable(roleId){
	var table = null;
	$.ajax({
		cache : true,
		type : "POST",
		async: false,
		url : "/manage/get_role_privilege_table",
		data : {
			"roleId":roleId
		},// 通过序列化表单值，创建 URL 编码文本字符串。
		dataType : "text",
		error : function() {
			layer.msg('设置失败', {
				time : 2000, // 2s后自动关闭
				icon : 1
			});
		},
		success : function(data) {
			if (!isEmpty(data)) {
				//location.reload();
				table = data;
			} else {
				layer.msg('设置失败', {
					time : 2000, // 2s后自动关闭
					icon : 1
				});
			}
		}
	});
	return table;
}

function updateRole(){
	if(isEmpty($("#update_roleName").val())){
		layer.msg('请填写角色名称', {
			time : 2000, // 2s后自动关闭
			icon : 1
		});
		return;
	}
	$.ajax({
		cache : true,
		type : "POST",
		url : "/manage/update_role",
		data : $("#updateForm").serialize(),// 通过序列化表单值，创建 URL 编码文本字符串。
		dataType : "text",
		error : function() {
			layer.msg('更新失败', {
				time : 2000, // 2s后自动关闭
				icon : 1
			});
		},
		success : function(data) {
			if ("true" == data) {
				location.reload();
			} else {
				layer.msg('更新失败', {
					time : 2000, // 2s后自动关闭
					icon : 1
				});
			}
		},
	});
}

function removeRole(roleId){
	// 询问框
	layer.confirm('确定要删除该角色？', {
		btn : [ '确认', '取消' ]
	// 按钮
	}, function() {
		$.ajax({
			cache : true,
			type : "POST",
			url : "/manage/remove_role",
			data : {
				"roleId" : roleId
			},
			dataType : "text",
			error : function() {
				layer.msg('删除失败', {
					time : 2000, // 2s后自动关闭
					icon : 1
				});
			},
			success : function(data) {
				if ("true" == data) {
					location.reload();
				} else {
					layer.msg('删除失败', {
						time : 2000, // 2s后自动关闭
						icon : 1
					});
				}
			},
		});
	}, function() {
	});
}

function unitList(roleId){
	addNewTabs({
		id: "10312",title: '单位管理',close: true,url: '/manage/unit_list?role='+roleId}
	);
}

function unitList(roleId){
	addNewTabs({
		id: "10312",title: '单位管理',close: true,url: '/manage/unit_list?role='+roleId}
	);
}


function checkChange(){
    var needDeleteId = "";
    var needAddId = "";
    $(":checkbox[name='menuid']").each(function(){
        //var preChecked = $(this).attr("prechecked");
        var id = $(this).attr("id");
        if(this.checked){
           // if(preChecked==""){
                needAddId = needAddId + id + ";";
           // }
        } else {
           // if(preChecked=="checked"){
                needDeleteId = needDeleteId + id + ";";
           // }
        }
    });
    $("#needDeleteId").val(needDeleteId);
    $("#needAddId").val(needAddId);
}

function checknode(obj) {
    var chk = $("input[type='checkbox']");
    var count = chk.length;
    var num = chk.index(obj);
    var level_top = level_bottom = chk.eq(num).attr('level');
    for (var i = num; i >= 0; i--) {
        var le = chk.eq(i).attr('level');
        if (eval(le) < eval(level_top)) {
            chk.eq(i).prop("checked", true);
            var level_top = level_top - 1;
        }
    }
    for (var j = num + 1; j < count; j++) {
        var le = chk.eq(j).attr('level');
        if (chk.eq(num).prop("checked") == true) {
            if (eval(le) > eval(level_bottom)) chk.eq(j).prop("checked", true);
            else if (eval(le) == eval(level_bottom)) break;
        }
        else {
            if (eval(le) > eval(level_bottom)) chk.eq(j).prop("checked", false);
            else if (eval(le) == eval(level_bottom)) break;
        }
    }
    checkChange();
}


function setPrivilege(){
	checkChange();
	$.ajax({
		cache : true,
		type : "POST",
		url : "/manage/set_privilege",
		data : $("#setForm").serialize(),// 通过序列化表单值，创建 URL 编码文本字符串。
		dataType : "text",
		error : function() {
			layer.msg('设置失败（参数）', {
				time : 2000, // 2s后自动关闭
				icon : 1
			});
		},
		success : function(data) {
			if ("true" == data) {
				location.reload();
			} else {
				layer.msg('设置失败', {
					time : 2000, // 2s后自动关闭
					icon : 1
				});
			}
		},
	});
}