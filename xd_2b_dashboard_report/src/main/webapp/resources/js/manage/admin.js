$(function(){
	var _role = $("#role").val();
	$("#role1 option[value='" + _role + "']").attr("selected", true);
	
	var _unitId = $("#unitId").val();
	$("#unitId1 option[value='" + _unitId + "']").attr("selected", true);
	
	$('#update').on('show.bs.modal', function(event) {
		var button = $(event.relatedTarget); // Button that
		// triggered
		// the modal
		var adminId = button.data('sid'); // Extract info
		var userName = button.data('user_name');
		//var password = button.data('password');
		var unitId = button.data('unit_id');
		var realName = button.data('real_name');
		var email = button.data('email');
		var telephone = button.data('telephone');
		var childRole = button.data('child_role');
		// from
		// data-* attributes
		var modal = $(this);
		modal.find('.modal-title').text('修改单位信息 - 单位id:' + unitId);
		modal.find('#update_id').val(adminId);
		modal.find('#update_userName').val(userName);
		//modal.find('#update_password').val(password);
		$("#update_unitId option[value='" + unitId + "']").attr("selected", true);
		modal.find('#update_realName').val(realName);
		modal.find('#update_email').val(email);
		modal.find('#update_telephone').val(telephone);
		if(3==$("#update_unitId").find("option:selected").attr("role")){
			$("#update_childRole").show();
			$(":radio[name='childRole'][value='" + childRole + "']").prop("checked", "checked");
		}else{
			$("#update_childRole").hide();
		}
		modal.find("#update_password").val("");
	});
	
	$('#save').on('show.bs.modal', function(event) {
		$("#save_unitId").val($("#unitId").val());
		if(3==$("#save_unitId").find("option:selected").attr("role")){
			$("#save_childRole").show();
		}else{
			$("#save_childRole").hide();
		}
	});
	$("#saveAdmin").click(function(){
		//$(this).attr("disabled", true);
		if (!verifyCon("save")){
			return;
		}
		if(isEmpty($("#save_password").val())){
			$.fn.modalMsg("请填写密码！", "error");
			return;
		}
		if(!verify($("#save_password").val())) return;
		$.ajax({
			cache : true,
			type : "POST",
			url : "/manage/save_admin",
			data : $("#saveForm").serialize(),// 通过序列化表单值，创建 URL 编码文本字符串。
			dataType : "text",
			error : function() {
				$.fn.modalMsg("异常", "error");
			},
			success : function(data) {
				if ("true" == data) {
					location.reload();
				} else {
					$.fn.modalAlert(data, "error");
				}
			}
		});
		//$(this).attr("disabled", false);
	});
	
	$("#save_unitId").change(function() {
		if(3==$("#save_unitId").find("option:selected").attr("role")){
			$("#save_childRole").show();
		}else{
			$("#save_childRole").hide();
		}
	});
	$("#update_unitId").change(function() {
		if(3==$("#update_unitId").find("option:selected").attr("role")){
			$("#update_childRole").show();
		}else{
			$("#update_childRole").hide();
		}
	});
});


function updateAdmin(){
	if (!verifyCon("update"))
		return;
	if(!isEmpty($("#save_password").val())){
		if(!verify($("#save_password").val())) return;
	}
	$.ajax({
		cache : true,
		type : "POST",
		url : "/manage/update_admin",
		data : $("#updateForm").serialize(),// 通过序列化表单值，创建 URL 编码文本字符串。
		dataType : "text",
		error : function() {
			$.fn.modalMsg("异常", "error");
		},
		success : function(data) {
			if ("true" == data) {
				location.reload();
			} else {
				$.fn.modalAlert("更新失败", "error");
			}
		}
	});
}

function removeAdmin(adminId){
	// 询问框
	layer.confirm('确定要删除该账号？', {
		btn : [ '确认', '取消' ]
	// 按钮
	}, function() {
		$.ajax({
			cache : true,
			type : "POST",
			url : "/manage/remove_admin",
			data : {
				"adminId" : adminId
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
			}
		});
	}, function() {
	});
}


var verify = function(password) {
	var flag = true;
	//6-20字母和数字组成
	var reg = /^[0-9A-Za-z]{6,20}$/;
	if (reg.test(password)) {
		flag = true;
	} else {
		$.fn.modalMsg("密码由6-20字母和数字组成，请重新填写~！", "error");
		flag = false;
	};
	return flag;
};

var verifyCon = function(param) {
	var flag = true;
	var realName = $("#" + param + "_userName").val();
	if (isEmpty(realName)) {
		$.fn.modalMsg("请输入用户名，用户名不能为空！", "error");
		return false;
	}
	var telephone = $("#" + param + "_telephone").val();
	var reg = /^0?1[3-8][0-9]\d{8}$/;
	if (isEmpty(telephone)) {
		$.fn.modalMsg("请输入手机号，手机号不能为空！", "error");
		return false;
	}
	if (reg.test(telephone)) {
		flag = true;
	} else {
		$.fn.modalMsg("手机号有误！", "error");
		return false;
	}
	return flag;
};