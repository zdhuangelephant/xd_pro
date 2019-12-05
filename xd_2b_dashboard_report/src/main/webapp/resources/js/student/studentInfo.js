$(function() {
			$('#update').on('show.bs.modal', function(event) {
						var button = $(event.relatedTarget);
						var id = button.data('sid');
						var realName = button.data('real_name');
						var telephone = button.data('telephone');
						var admissionCardCode = button
								.data('admission_card_code');
						var pilotUnitName = button.data('pilot_unit_name');

						var modal = $(this);
						modal.find('.modal-title').text('修改学生信息 - 学生id:' + id);
						modal.find('#id').text(id);
						modal.find('#realName').text(realName);
						modal.find('#telephone').val(telephone);
						modal.find('#telephone_name').text(telephone);
						modal.find('#admissionCardCode').val(admissionCardCode);
						modal.find('#admissionCardCode_name')
								.text(admissionCardCode);
						modal.find('#pilotUnitName').text(pilotUnitName);
					});
		});
// 修改学生手机号码
function updateStudentInfo() {
	var oldTelephone = $("#telephone_name").text();
	var telephone = $("#regTelephone").val();
	if (!verifyTelephone(telephone))
		return;
	if (oldTelephone == telephone) {
		$.fn.modalMsg("新手机号与旧手机号相同", "warning");
		return;
	}
	$.ajax({
				cache : true,
				type : "POST",
				url : "/student/update_student_telephone",
				data : $("#updateForm").serialize(),// 通过序列化表单值，创建 URL 编码文本字符串。
				dataType : "text",
				error : function(msg) {
					$.fn.modalMsg(msg, "error");
				},
				success : function(data) {
					if ("true" == data) {
						$.fn.modalMsg("修改手机号成功！", "success");
						location.reload();
					} else {
						$.fn.modalMsg(data, "error");
					}
				}
				,
			});
}
function verifyTelephone(telephone) {
	var flag = true;
	if (isEmpty(telephone)) {
		$.fn.modalMsg("新手机号不能为空！", "warning");
		return false;
	}
	var reg = /^0?1[3-8][0-9]\d{8}$/;
	if (!reg.test(telephone)) {
		$.fn.modalMsg("请输入正确的11位手机号码！", "warning");
		return false;
	}
	return flag;
}
// 检查输入合法性
function checkForm() {
	var flag = true;
	var telephone = $.trim($("#telephone").val());
	var admissionCardCode = $.trim($("#admissionCardCode").val());
	if (isEmpty(telephone) && isEmpty(admissionCardCode)) {
		$.fn.modalAlert("手机号码和准考证号码至少输入一项！", "warning");
		return false;
	}
	var reg = /^0?1[3-8][0-9]\d{8}$/;
	if (notEmpty(telephone)) {
		if (reg.test(telephone)) {
			flag = true;
		} else {
			$.fn.modalMsg("请输入正确的11位手机号码！", "warning");
			return false;
		}
	}
	var reg1 = /^\d{12}$/;
	if (notEmpty(admissionCardCode)) {
		if (reg1.test(admissionCardCode)) {
			flag = true;
		} else {
			$.fn.modalMsg("请输入正确的12位自学考试准考证号码！", "warning");
			return false;
		}
	}
	return flag;
}

// 重置用户头像
function resetStudentPortrait(userId, telephone) {
	if (!verifyTelephone(telephone))
		return;
	// 询问框
	layer.confirm('您确定要重置该学生的头像吗？', {
		btn : ['确认', '取消']
			// 按钮
		}, function() {
		$.ajax({
					cache : true,
					type : "POST",
					url : "/student/reset_student_portrait",
					data : {
						'userId' : userId,
						'telephone' : telephone
					},
					dataType : "text",
					error : function(msg) {
						$.fn.modalMsg(msg, "error");
					},
					success : function(data) {
						if ("true" == data) {
							location.reload();
						} else {
							$.fn.modalMsg(data, "warning");
						}
					}
				});
	}, function() {
		// layer.msg('也可以这样', {
		// time: 20000, //20s后自动关闭
		// btn: ['明白了', '知道了']
		// });
	});
}
