$(function() {

	// $("#tab_10213", window.parent.document).addClass('active');
	// alert(window.parent.document.getElementById('tab_10213').className);
	// window.parent.document.getElementById('tab_10213').className = "tab-pane
	// active";
	// $("#tab_tab_20214", window.parent.document).click();

	$("#classId1").val($("#classId").val());
	$(".tr-child td").removeClass("text-center");

	$('#update').on('show.bs.modal', function(event) {
		var button = $(event.relatedTarget); // Button that
		// triggered
		// the modal
		var studentId = button.data('sid'); // Extract info
		var status = button.data('status');
		var classId;
		var realName, gender, telephone, identificationCardCode, admissionCardCode;
		var modal = $(this);
		$.ajax({
			cache : true,
			type : "POST",
			url : "/student/get_student",
			data : {
				"studentId" : studentId
			},
			dataType : "html",
			error : function() {
				modalMsg(4);
			},
			success : function(data) {
				if (!isEmpty(data)) {
					// 显示课程操作
					var studentJson = JSON.parse(data);
					classId = studentJson.classId;
					realName = studentJson.realName;
					gender = studentJson.gender;
					telephone = studentJson.telephone;
					identificationCardCode = studentJson.identificationCardCode;
					admissionCardCode = studentJson.admissionCardCode;
					// from
					// data-* attributes

					// modal.find('.modal-title').text('修改班级信息
					// - 班级id:' + classId);
					modal.find('#update_id').val(studentId);
					modal.find('#update_classId').val(classId);
					$("#update_classId1 option[value='" + classId + "']").attr(
							"selected", true);
					modal.find('#update_realName').val(realName);
					modal.find('#update_gender').val(gender);
					$("#update_gender1 option[value='" + gender + "']").attr(
							"selected", true);
					modal.find('#update_telephone').val(telephone);
					modal.find('#update_admissionCardCode')
							.val(admissionCardCode);
					if (status == 1) {
						modal.find('#update_classId1').prop("disabled", true);
						modal.find('#update_realName').prop("disabled", true);
						modal.find('#update_gender1').prop("disabled", true);
						// modal.find('#update_telephone').prop("disabled",true);
						// modal.find('#update_classId1').prop("readonly",
						// true);
						// modal.find('#update_realName').prop("readonly",
						// true);
						// modal.find('#update_gender1').prop("readonly", true);
						modal.find('#update_telephone').prop("readonly", true);
					}
				} else {
					$.fn.modalAlert("查找失败！", "error");
				}
			}
		});

	});

	$('#save').on('show.bs.modal', function(event) {
				// from
				// data-* attributes
				var modal = $(this);
				var classId = $("#classId1").val();
				if (!isEmpty(classId)) {
					modal.find("#save_classId").val(classId);
				}
			});

	$('#apply').on('show.bs.modal', function(event) {
				var button = $(event.relatedTarget); // Button that
				// triggered
				// the modal
				var student_id = button.data('sid'); // Extract info
				// from
				// data-* attributes
				var modal = $(this);
				// modal.find('.modal-title').text('新增课程 - 学生id:' + student_id);
				modal.find("#selectStudentId").val(student_id);
				modal.find("#catId").val("");
				modal.find("#form-group-course").html("");
			});

	// $('#importStudent').on('show.bs.modal', function(event) {
	// var selectClassId = $("#classId1").val();
	// if(isEmpty(selectClassId)){
	// alert(selectClassId);
	// $("#importStudentClose").click();
	// alert("123");
	// }
	// });

	$('#initImportStudent').click(function() {
				var selectClassId = $("#classId1").val();
				if (isEmpty(selectClassId)) {
					$.fn.modalAlert("请选择班级先！", "error");
				} else {
					$("#importStudent").show();
					$("#importStudent").addClass("in");
				}
			});

	$("#importStudentClose").click(function() {
				$("#importStudent").hide();
				$("#importStudent").removeClass("in");
			});

	$('#initBatchApply').click(function() {
				var $batchStudent = $("input[name='batchStudent']:checked");
				// 收集学生id
				if ($batchStudent.length == 0) {
					$.fn.modalAlert("未选取学生，请选取！", "error");
					return;
				} else {
					$("#batchApply").show();
					$("#batchApply").addClass("in");
				}
			});

	$("#batchApplyClose").click(function() {
				$("#batchApply").hide();
				$("#batchApply").removeClass("in");
			});

	$("#catId").change(function() {
		var student_id = $("#selectStudentId").val(); // Extract
		// info
		// 查看专业下面的课程
		$.ajax({
			cache : false,
			type : "POST",
			url : "/product/student_product_list",
			data : {
				"catId" : $(this).val(),
				"studentId" : student_id
			},// 通过序列化表单值，创建
			// URL 编码文本字符串。
			async : false,// 是否异步
			dataType : "html",
			// contentType :
			// "application/x-www-form-urlencoded;
			// charset=utf-8",
			error : function() {
				$.fn.modalMsg("异常", "error");
			},
			success : function(data) {
				if (!isEmpty(data)) {
					// 显示课程操作
					var productJson = JSON.parse(data);
					// alert(productJson[0].name);
					// alert(productJson[0].applyStatus);
					// alert(productJson);
					var html = "<label for='class' class='form-control-label'>课程:</label> <br>";
					$.each(productJson, function(key, val) {
						if (val.applyStatus == 0)
							html += "<input id='" + val.id + "' examdate='"
									+ val.examDate + "' courseCode='"
									+ val.courseCode + "' productname ='"
									+ val.name + "' originalAmount ='"
									+ val.originalAmount
									+ "' name='productName' value='" + val.id
									+ "' type='checkbox'>" + val.name + " <br>";
						else if (val.applyStatus == 1)
							html += val.name + " <br>";
					});
					// ---
					$("#apply").find("#form-group-course").html(html);
				} else {
					$("#apply").find("#form-group-course").html("");
				}
			}
		});
	});

	$("#checkAll").click(function() {
		var checkAllLength = $("#checkAll:checked").length;
		if (checkAllLength == 1)
			$('input[name="batchStudent"]').prop("checked", true);
		else
			$('input[name="batchStudent"]').prop("checked", false);
			// var $subBox = $("input[name='batchStudent']");
			// var length = $subBox.length;
			// var checkLength = $("input[name='batchStudent']:checked").length;
			// if(length == checkLength)
			// $('input[name="batchStudent"]').prop("checked",false);
			// else
			// $('input[name="batchStudent"]').prop("checked",true);
		});

	$("#batch_catId").change(function() {
		// info
		// 查看专业下面的课程
		$.ajax({
			cache : false,
			type : "POST",
			url : "/product/product_list",
			data : {
				"catId" : $(this).val()
			},// 通过序列化表单值，创建
			// URL 编码文本字符串。
			async : false,// 是否异步
			dataType : "html",
			error : function() {
				$.fn.modalMsg("异常", "error");
			},
			success : function(data) {
				if (!isEmpty(data)) {
					// 显示课程操作
					var productJson = JSON.parse(data);
					var html = "<label for='class' class='form-control-label'>课程:</label> <br>";
					$.each(productJson, function(key, val) {
								html += "<input id='" + val.id + "' examdate='"
										+ val.examDate + "' courseCode='"
										+ val.courseCode + "' productname ='"
										+ val.name + "' originalAmount ='"
										+ val.originalAmount
										+ "' name='batchProductName' value='"
										+ val.id + "' type='radio'>" + val.name
										+ " <br>";
							});
					$("#batch-form-group-course").html(html);
				} else {
				}
			}
		});
	});

	$("#batchSaveApply").click(function() {
		var $checked = $("input[name='batchProductName']:checked");
		var productId = $checked.val();
		if (isEmpty(productId)) {
			$.fn.modalMsg("未选取课程，请选取！", "error");
			return;
		}
		// 收集学生id
		var $batchStudent = $("input[name='batchStudent']:checked");
		if ($batchStudent.length == 0) {
			$.fn.modalMsg("未选取学生，请选取！", "error");
			return;
		}
		var studentIdArr = new Array();
		var totalCount = 0;
		$batchStudent.each(function() {
					studentIdArr.push($(this).val());
					totalCount++;
				});
		var catId = $("#batch_catId option:selected").val();
		var catName = $("#batch_catId option:selected").text();
		var productName = $checked.attr("productname");
		var examDate = $checked.attr("examdate");
		var productCode = $checked.attr("courseCode");
		var originalAmount = $checked.attr("originalAmount");
		App.blockUI({
					target : '#batchApply'
				});
		$.ajax({
			cache : false,
			type : "POST",
			url : "/apply/batch_save_apply",
			data : {
				"productCode" : productCode,
				"originalAmount" : originalAmount,
				"productName" : productName,
				"catId" : catId,
				"catName" : catName,
				"examDate" : examDate,
				"productId" : productId,
				"studentIds" : studentIdArr.join(",")
			},// 通过序列化表单值，创建
			// URL 编码文本字符串。
			async : false,// 是否异步
			dataType : "html",
			error : function() {
				$.fn.modalMsg("异常", "error");
			},
			success : function(data) {
				App.unblockUI('#batchApply');
				if (!isEmpty(data)) {
					var json = JSON.parse(data);
					if (json.error == 0) {
						$.fn.modalAlert("报课失败。报名课程的课程代码重复。", "error");
						return;
					}
					var saveCount = json.saveCount;
					var catRepetCount = json.catRepetCount;
					// 显示课程操作
					var msg = "报名总人数" + totalCount + ",报名成功人数" + saveCount
							+ "。";
					if (totalCount == saveCount) {
						$.fn.modalAlert(msg, "success");
						location.reload();
					}
					if (totalCount > saveCount && saveCount != 0) {
						if (catRepetCount > 0) {
							msg += "报名专业有误！同一学生在一个考期内只能报名一个专业下的课程。可在学生列表中点击“查看”，查询该学生的报课记录。";
						}
						$.fn.modalAlert(msg, "warning");
					}
					if (saveCount == 0) {
						if (catRepetCount > 0) {
							msg += "报名专业有误！同一学生在一个考期内只能报名一个专业下的课程。可在学生列表中点击“查看”，查询该学生的报课记录。";
						}
						$.fn.modalAlert(msg, "error");
					}

				}
			}
		});
	});

	$("#saveApply").click(function() {
		$(this).attr("disabled", true);
		var checkCourse = $("#form-group-course input[name='productName']:checked")
				.serialize();
		if (!checkCourse) {
			$(this).attr("disabled", false);
			$.fn.modalMsg("没有可提交选项！", "error");
		} else {
			var catId = $("#catId option:selected").val();
			var catName = $("#catId option:selected").text();
			var successArr = new Array();
			var failArr = new Array();
			App.blockUI({
						target : '#apply'
					});
			$("#form-group-course input[name='productName']:checked").each(
					function() {
						// alert("sucess");
						var productId = $(this).attr("id");
						var productName = $(this).attr("productname");
						var examDate = $(this).attr("examdate");
						var productCode = $(this).attr("courseCode");
						var originalAmount = $(this).attr("originalAmount");
						$.ajax({
									cache : true,
									type : "POST",
									url : "/apply/save_apply",
									data : {
										"productId" : productId,
										"productCode" : productCode,
										"originalAmount" : originalAmount,
										"productName" : productName,
										"catId" : catId,
										"catName" : catName,
										"examDate" : examDate,
										"studentId" : $("#selectStudentId")
												.val()
									},// 通过序列化表单值，创建
									// URL 编码文本字符串。
									async : false,// 是否异步
									dataType : "text",
									error : function() {
										layer.msg('异常！', {
													time : 2000, // 2s后自动关闭
													icon : 1
												});
									},
									success : function(data) {
										if ("true" == data) {
											successArr.push(productName);
											return true;
										} else if ("false" == data) {
											failArr.push(productName);
											return true;
										} else {
											$(this).attr("disabled", false);
											$.fn.modalAlert(data, "error");
											App.unblockUI('#apply');
											return false;
										}
									}
								});
					});
			var successMsg = "";
			var failMsg = "";
			$.each(successArr, function(key, val) {
						successMsg = successMsg + val + ",";
					});
			$.each(failArr, function(key, val) {
						failMsg = failMsg + val + ",";
					});
			if (!isEmpty(failArr)) {
				App.unblockUI('#apply');
				$(this).attr("disabled", false);
				$.fn.modalAlert("报名" + failArr + "失败!", "error");
			}
			if (!isEmpty(successArr)) {
				$(this).attr("disabled", false);
				$.fn.modalAlert('报名 ' + successArr + ' 成功', "success");
				location.reload();
			}
			// location.reload();
			$(this).attr("disabled", false);
		}
	});

	$("#saveStudent").click(function() {
				if (!verify("save"))
					return;
				$("#saveStudent").attr("disabled", true);
				$.ajax({
							cache : true,
							type : "POST",
							url : "/student/save_student",
							data : $("#saveForm").serialize(),// 通过序列化表单值，创建
							// URL 编码文本字符串。
							async : false,// 是否异步
							dataType : "text",
							error : function() {
								$.fn.modalMsg("异常", "error");
							},
							success : function(data) {
								if ("true" == data) {
									location.reload();
								} else if ("false" == data) {
									$.fn.modalAlert("添加学生失败！", "error");
								} else {
									$.fn.modalAlert(data, "error");
								}
							}
						});
				$("#saveStudent").attr("disabled", false);
			});

	$("#updateStudent").click(function() {
				if (!verify("update"))
					return;
				$("#updateStudent").attr("disabled", true);
				$.ajax({
							cache : true,
							type : "POST",
							url : "/student/update_student",
							data : $("#updateForm").serialize(),
							async : false,// 是否异步
							dataType : "text",
							error : function() {
								$.fn.modalAlert("请求异常！", "error");
							},
							success : function(data) {
								if ("true" == data) {
									location.reload();
								} else if ("false" == data) {
									$.fn.modalAlert("编辑学生失败！", "error");
								} else {
									$.fn.modalAlert(data, "error");
								}
							}
						});
				$("#updateStudent").attr("disabled", false);
			});

});
function getLocalTime(nS) {
	alert(nS);
	var date = new Date(nS);
	var Y = date.getFullYear() + '-';
	var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date
			.getMonth()
			+ 1)
			+ '-';
	var D = date.getDate() + ' ';
	return Y + M + D;
}
var applyFlag = 0;
function removeApply(applyId) {
	// 询问框
	layer.confirm('确定要删除该报名信息？', {
		btn : ['确认', '取消']
			// 按钮
		}, function() {
		if (applyFlag == 1) {
			// $(".layui-layer-btn0").attr("href", "javascript:volid(0);");
			$.fn.modalMsg("正在删除中。。。", "error");
			return;
		}
		applyFlag = 1;
		$.ajax({
					cache : true,
					type : "POST",
					url : "/apply/remove_apply",
					data : {
						"applyId" : applyId
					},
					dataType : "text",
					error : function() {
						$.fn.modalMsg("删除失败", "error");
					},
					success : function(data) {
						if ("true" == data) {
							// location.href = "/student/student_list";
							location.reload();
						} else {
							$.fn.modalMsg("删除失败", "error");
							applyFlag = 0;
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
var studentFlag = 0;
function removeStudent(studentId, classId) {
	// 询问框
	layer.confirm('确定要删除该学生？', {
		btn : ['确认', '取消']
			// 按钮
		}, function() {
		if (studentFlag == 1) {
			// $(".layui-layer-btn0").attr("href", "javascript:volid(0);");
			$.fn.modalMsg("正在删除中。。。", "error");
			return;
		}
		studentFlag = 1;
		$.ajax({
					cache : true,
					type : "POST",
					url : "/student/remove_student",
					data : {
						"studentId" : studentId,
						"classId" : classId
					},
					dataType : "text",
					error : function() {
						$.fn.modalMsg("删除失败", "error");
					},
					success : function(data) {
						if ("true" == data) {
							location.reload();
						} else {
							// $("#alert").show();
							$.fn.modalMsg("删除失败", "error");
							studentFlag = 0;
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
var verify = function(param) {
	var flag = true;
	var realName = $("#" + param + "_realName").val();
	if (isEmpty(realName)) {
		$.fn.modalMsg("请输入学生姓名，学生姓名不能为空！", "error");
		return false;
	}
	var telephone = $("#" + param + "_telephone").val();
	var reg = /^0?1[3-8][0-9]\d{8}$/;
	if (isEmpty(telephone)) {
		$.fn.modalMsg("请输入手机号，手机号不能为空！", "error");
		// document.getElementById('admissionCardCode').focus;
		return false;
	}
	if (reg.test(telephone)) {
		flag = true;
	} else {
		$.fn.modalMsg("手机号有误！", "error");
		return false;
	}
	// 校验身份证号
	// if (!checkCard())
	// return false;
	// var admissionCardCode = $("#admissionCardCode").val();
	// if (admissionCardCode === '') {
	// layer.msg('请输入准考证号，准考证号不能为空！', {
	// time : 2000, // 2s后自动关闭
	// icon : 1
	// });
	// document.getElementById('admissionCardCode').focus;
	// return false;
	// }

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
				$("#upload_fileName").html($(window.frames[0].document)
						.find('.fileInfo').find("span").text());
				$("#upload_file").hide();
				$("#upload").show();
				$("#step2").addClass("active");
				$("#step1").removeClass("active");
			}, scope, size, type);
}
function method1(id) {
	// method(id);

	$("#download").hide();
	$("#upload").show();
	$("#step2").addClass("active");
	$("#step1").removeClass("active");
	location.href = "/student/download_excel";
	// $.get("/student/download_excel",function(data,status){
	// alert("Data: " + data + "nStatus: " + status);
	// });
}

function detection() {
	App.blockUI({
				target : '#importStudent'
			});
	$.post("/student/detection_excel_url", {
				"url" : $("#detection_url").val()
			}, function(data) {
				App.unblockUI('#importStudent');
				if (!isEmpty(data)) {
					$("#detection_msg").html(data);
					$("#upload").hide();
					$("#detection").show();
					$("#step3").addClass("active");
					$("#step2").removeClass("active");
					if (isEmpty($("#verifyCount")) || isEmpty($("#errorCount"))) {
						$("#importButton").hide();
						$("#createErrorExcelButton").hide();
						$("#verifyButton").hide();
					} else {
						$("#createErrorExcelButton").hide();
						if ($("#verifyCount").text() > 0) {
							$("#importButton").show();
							$("#verifyButton").show();
						} else {
							$("#importButton").hide();
							$("#verifyButton").hide();
						}
						if ($("#errorCount").text() > 0) {
							$("#createErrorExcelButton").show();
						}

						if ($("#verifyCount").text() == 0
								&& $("#errorCount").text() == 0) {
							$("#createErrorExcelButton").hide();
						}
					}
				} else {
					$.fn.modalMsg("添加失败", "error");
				}
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
	location.href = "/student/create_error_excel?url="
			+ encodeURI($("#detection_url").val());
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
		btn : ['确认', '关闭'],
		btnclass : ['btn btn-primary', 'btn btn-danger'],
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
				area : [_width, _height],
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

function importStudent() {
	App.blockUI({
				target : '#importStudent'
			});
	$.post("/student/import_student", {
				"url" : $("#detection_url").val(),
				"classId" : $("#classId1 option:selected").val(),
				"className" : $("#classId1 option:selected").text()
			}, function(data) {
				App.unblockUI('#importStudent');
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
}
function back_importStudent() {
	$("#upload").show();
	$("#detection").hide();
	$("#step2").addClass("active");
	$("#step3").removeClass("active");
}

function toPageSize(pageSize) {
	App.blockUI({
				target : '.pace-done'
			});
	$("#pageSize").val(pageSize);
	$("#searchForm").submit();
}
