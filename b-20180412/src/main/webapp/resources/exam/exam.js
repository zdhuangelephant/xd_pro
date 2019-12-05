$(function() {
	$("#classId1").val($("#classId").val());
});
function getLocalTime(nS) {
	alert(nS);
	var date = new Date(nS);
	var Y = date.getFullYear() + '-';
	var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date
			.getMonth() + 1)
			+ '-';
	var D = date.getDate() + ' ';
	return Y + M + D;
}
var applyFlag = 0;
var studentFlag = 0;
var verify = function(param) {
	var flag = true;
	var realName = $("#" + param + "_realName").val();
	if (isEmpty(realName)) {
		$.fn.modalMsg("请输入学生姓名，学生姓名不能为空！", "error");
		return false;
	}
	var telephone = $("#" + param + "_telephone").val();
	var reg = /^0?1[3|4|5|8][0-9]\d{8}$/;
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

	var admissionCardCode = $("#" + param + "_admissionCardCode").val();
	var reg1 = /^\d{12}$/;
	if (!isEmpty(admissionCardCode)) {
		if (reg1.test(admissionCardCode)) {
			flag = true;
		} else {
			$.fn.modalMsg("准考证号有误！应填写12位数字。", "error");
			return false;
		}
	}
	return flag;
};

function uploaD(id, scope, size, type) {
	fileUploadCallBack(function(url) {
		if (isEmpty(url))
			return;
		$("#detection_url").val(url);
		$("#download").hide();
		$("#" + id).hide();
		$("#upload_file").hide();
		$("#upload").show();
		$("#step2").addClass("active");
		$("#step1").removeClass("active");
	}, scope, size, type);
}
function method1(id) {
	$("#download").hide();
	$("#upload").show();
	$("#step2").addClass("active");
	$("#step1").removeClass("active");
	location.href = "/question/download_excel";
}

function detection(obj) {
	$(obj).attr("disabled", true);
	$("#backStep").attr("disabled", true);

	layui.use('layer', function() {
		var layer = layui.layer;
		var index = layer.load(0, {
			shade : false
		}); // 0代表加载的风格，支持0-2

		$.post("/question/detection_excel_url", {
			"url" : $("#detection_url").val(),
			"chooseCourseId" : $("#batchImport").attr("data-course")
		}, function(data) {
			layer.close(index);
			$(obj).attr("disabled", false);
			$("#backStep").attr("disabled", false);
			if (!isEmpty(data)) {
				$("#detection_msg").html(data);
				$("#upload").hide();
				$("#detection").show();
				$("#step3").addClass("active");
				$("#step2").removeClass("active");
				if ($("#verifyCount").text() != 0) {
					$("#importButton").show();
				} else {
					$("#importButton").hide();
				}
				if ($("#errorCount").text() != 0) {
					$("#createErrorExcelButton").show();
					$("#verifyButton").show();
				} else {
					$("#createErrorExcelButton").hide();
					$("#verifyButton").hide();
				}
			} else {
				$.fn.modalMsg("添加失败", "error");
			}
		});
	});
}

function back_detection() {
	$("#download").show();
	$("#upload").hide();
	$("#step1").addClass("active");
	$("#step2").removeClass("active");
}

// 预览文件检测结果
function createErrorExcel() {
	location.href = "/question/create_error_excel?url="
			+ encodeURI($("#detection_url").val()) + "&chooseCourseId="
			+ $("#batchImport").attr("data-course");
	// $.fn.modalOpen({
	// id : "Form",
	// title : '预览文件检测结果',
	// url : '/student/create_error_excel?url='
	// + encodeURI($("#detection_url").val()),
	// width : "570px",
	// height : "380px",
	// callBack : function(iframeId) {
	// }
	// });
};

$.fn.modalOpen = function(options) {
	var defaults = {
		id : null,
		title : '系统窗口',
		width : "100px",
		height : "100px",
		url : '',
		shade : 0.3,
		btn : [ '确认', '关闭' ],
		btnclass : [ 'btn btn-primary', 'btn btn-danger' ],
		callBack : null
	};
	var options = $.extend(defaults, options);
	var _width = top.$(window).width() > parseInt(options.width.replace('px',
			'')) ? options.width : top.$(window).width() + 'px';
	var _height = top.$(window).height() > parseInt(options.height.replace(
			'px', '')) ? options.height : top.$(window).height() + 'px';
	layer.open({
		id : options.id,
		type : 2,
		shade : options.shade,
		title : options.title,
		fix : false,
		area : [ _width, _height ],
		content : options.url,
		btn : options.btn,
		btnclass : options.btnclass,
		yes : function() {
			options.callBack(options.id);
		},
		cancel : function() {
			return true;
		}
	});
};

function importStudent(obj) {
	$(obj).attr("disabled", true);
	$("#backStep2").attr("disabled", true);

	layui.use('layer', function() {
		var layer = layui.layer;
		var index = layer.load(0, {
			shade : false
		}); // 0代表加载的风格，支持0-2
		$.post("/question/import_questions", {
			"url" : $("#detection_url").val(),
			"chooseCourseId" : $("#batchImport").attr("data-course")
		}, function(data) {
			layer.close(index);
			$(obj).attr("disabled", false);
			$("#backStep2").attr("disabled", false);
			if (!isEmpty(data) && "-1" != data) {
				if ("0" == data) {
					$("#import_msg").html("导入失败");
					$("#success_msg").hide();
				} else {
					$("#im_success_count").html(data);
				}
				$("#detection").hide();
				$("#success").show();
				$("#step4").addClass("active");
				$("#step3").removeClass("active");
			} else {
				layer.msg('添加失败', {
					time : 2000, // 2s后自动关闭
					icon : 1
				});
			}
		});
	});

}
function back_importStudent() {
	$("#upload").show();
	$("#detection").hide();
	$("#step2").addClass("active");
	$("#step3").removeClass("active");
}
