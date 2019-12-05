$(function(){
	var _role = $("#role").val();
	$("#role1 option[value='" + _role + "']").attr("selected", true);
	
	$('#update').on('show.bs.modal', function(event) {
		var button = $(event.relatedTarget); // Button that
		// triggered
		// the modal
		var unitId = button.data('sid'); // Extract info
		var unitName = button.data('unit_name');
		var role = button.data('role');
		var unitPortrait = button.data('unit_portrait');
		// from
		// data-* attributes
		var modal = $(this);
		modal.find('.modal-title').text('修改单位信息 - 单位id:' + unitId);
		modal.find('#update_id').val(unitId);
		modal.find('#update_unitName').val(unitName);
		modal.find('#update_unitPortrait').attr("src",unitPortrait);
		//modal.find('#update_role').val(role);
		$("#update_role option[value='" + role + "']").attr("selected", true);
	});
	
	$('#save').on('show.bs.modal', function(event) {
		$("#save_role").val($("#role1").val());
	});
	
	$("#saveUnit").click(function(){
		if(isEmpty($("#save_unitName").val())){
			layer.msg('请填写单位名称', {
				time : 2000, // 2s后自动关闭
				icon : 1
			});
			return;
		}
		$(this).attr("disabled", true);
		$.ajax({
			cache : true,
			type : "POST",
			url : "/manage/save_unit",
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
	
	$('#saveRelate').on('show.bs.modal', function(event) {
		var button = $(event.relatedTarget); // Button that
		// triggered
		// the modal
		var unitId = button.data('sid'); // Extract info
		// from
		// data-* attributes
		var modal = $(this);
		modal.find("#unitId").val(unitId);
		$.ajax({
			cache : true,
			type : "POST",
			url : "/manage/get_relate",
			data : {
				"unitId":unitId
			},// 通过序列化表单值，创建 URL 编码文本字符串。
			dataType : "text",
			error : function() {
				layer.msg('添加失败', {
					time : 2000, // 2s后自动关闭
					icon : 1
				});                 
			},
			success : function(data) {
				if (!isEmpty(data)) {
					var jsonData = JSON.parse(data);
					var arr = jsonData.other;
					$("#relateCat input[name='catName']").each(function() {
						var val = $(this).val();
						if(!isEmpty(jsonData.oneself) && val == jsonData.oneself){
							$(this).prop("checked", true);
						}
						else{
							$(this).prop("checked", false);	
							var result = arr.indexOf(val);
							if(result != -1){
								$(this).prop("disabled", true);	
							}else{
								$(this).prop("disabled", false);
							}
						}
					});
				}
			},
		});
		
	});
	
});

function saveRelate(){
	var unitId = $("#unitId").val();
	var catId = $("#relateCat input[name='catName']:checked").val();
	$.ajax({
		cache : true,
		type : "POST",
		url : "/manage/save_relate",
		data : {
			"unitId":unitId,
			"catId":catId
		},// 通过序列化表单值，创建 URL 编码文本字符串。
		dataType : "text",
		error : function() {
			layer.msg('添加失败', {
				time : 2000, // 2s后自动关闭
				icon : 1
			});
		},
		success : function(data) {
			if ("true" == data) {
				location.reload();
			} else {
				$.fn.modalMsg("更新失败，该专业已经被其他院校关联！", "warning");
			}
		},
	});
}

function updateUnit(){
	if(isEmpty($("#update_unitName").val())){
		layer.msg('请填写单位名称', {
			time : 2000, // 2s后自动关闭
			icon : 1
		});
		return;
	}
	$.ajax({
		cache : true,
		type : "POST",
		url : "/manage/update_unit",
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

function removeUnit(unitId){
	// 询问框
	layer.confirm('确定要删除该单位？', {
		btn : [ '确认', '取消' ]
	// 按钮
	}, function() {
		$.ajax({
			cache : true,
			type : "POST",
			url : "/manage/remove_unit",
			data : {
				"unitId" : unitId
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

function adminList(unitId){
	addNewTabs({
		id: "10313",title: '账号管理',close: true,url: '/manage/admin_list?unitId='+Number(unitId)}
	);
}