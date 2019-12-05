$(function() {
	var examDate = $("#examDate").val();
	$("#examDate1 option[value='" + examDate + "']").attr("selected", true);
	var catId = $("#catId").val();
	$("#catId1 option[value='" + catId + "']").attr("selected", true);
	var productId= $("#productId").val();
	$("#productId1 option[value='" + productId + "']").attr("selected", true);
	var classId = $("#classId").val();
	$("#classId1 option[value='" + classId + "']").attr("selected", true);
	var pilotUnitId = $("#pilotUnitId").val();
	$("#pilotUnitId1 option[value='" + pilotUnitId + "']").attr("selected", true);
	
	

});

function changeCat(catId){
	// info
	// 查看专业下面的课程
	$.ajax({
			cache : false,
			type : "POST",
			url : "/product/product_list",
			data : {
				"catId" : catId
			},// 通过序列化表单值，创建
			// URL 编码文本字符串。
			async : false,// 是否异步
			dataType : "html",
			error : function() {
				layer.msg('异常！', {
					time : 2000, // 2s后自动关闭
					icon : 1
				});
			},
			success : function(data) {
				if (!isEmpty(data)) {
					// 显示课程操作
					var productJson = JSON.parse(data);
					//alert(productJson[0].name);
					// alert(productJson[0].applyStatus);
					// alert(productJson);
					var html = "<option value=''>全部课程</option>";
					$.each(productJson, function(key,
							val) {
						html += "<option value='"
								+ val.id + "'>"
								+ val.name
								+ "</option>";
					});
					$("#productId1").html(html);
				} 
			}
		});
}

function exportScoreList(){
	location.href = "/score/export_score_list?scoreDO="+$("#searchForm").serialize();
}

function exportTransferScoreList(){
	location.href = "/score/export_transfer_score_list?scoreDO="+$("#searchForm").serialize();
}


/*******************2017/10/22批量导入平时成绩*****************/

$('#initImportStudent').click(function(){
	var v =  $("#sizeoflist").val();
	if(!isEmpty(v)){
		$("#importScore").show();
		$("#importScore").addClass("in");
	}else {
		layer.msg('没有学生成绩记录，无法操作导入成绩', {
			time : 2000, // 2s后自动关闭
			icon : 1
		});
	}
	
});

$("#importStudentClose").click(function(){
	$("#importScore").hide();
	$("#importScore").removeClass("in");
});

function uploaD(id, scope, size, type) {
	fileUploadCallBack(function(url) {
		if (isEmpty(url))
			return;
		$("#detection_url").val(url);
		$("#download").hide();
		$("#" + id).hide();
		$("#upload_fileName").html(
				$(window.frames[0].document).find('.fileInfo').find("span")
						.text());
		$("#upload_file").hide();
		$("#upload").show();
		$("#step2").addClass("active");
		$("#step1").removeClass("active");
	}, scope, size, type);
}

function method1() {
	$("#download").hide();
	$("#upload").show();
	$("#step2").addClass("active");
	$("#step1").removeClass("active");
	location.href = "/score/download_excel";
}

function detection() {
	App.blockUI({
        target: '#importScore'
    });
	$.post("/score/detection_excel_url", {
		"url" : $("#detection_url").val()
	}, function(data) {
		App.unblockUI('#importScore');
		if (!isEmpty(data)) {
			$("#detection_msg").html(data);
			$("#upload").hide();
			$("#detection").show();
			$("#step3").addClass("active");
			$("#step2").removeClass("active");
			if(isEmpty($("#verifyCount")) || isEmpty($("#errorCount"))){
				$("#importButton").hide();
			}else{
				if($("#verifyCount").text() !=0 ){
					$("#importButton").show();
					$("#verifyButton").show();
				}else{
					$("#importButton").hide();
					$("#verifyButton").hide();
				}
				
				if($("#errorCount").text()!=0){
					$("#createErrorExcelButton").show();
				}
					
				if($("#verifyCount").text()==0 && $("#errorCount").text()==0){
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

//预览文件检测结果
function createErrorExcel() {
	location.href = "/score/create_error_excel?url="+ encodeURI($("#detection_url").val());
//	$.fn.modalOpen({
//		id : "Form",
//		title : '预览文件检测结果',
//		url : '/student/create_error_excel?url='
//				+ encodeURI($("#detection_url").val()),
//		width : "570px",
//		height : "380px",
//		callBack : function(iframeId) {
//		}
//	});
};


function importScoreList() {
	App.blockUI({
        target: '#importScore'
    });
	$.post("/score/import_score", {
		"url" : $("#detection_url").val()
		/*"classId" : $("#classId1 option:selected").val(),
		"className":$("#classId1 option:selected").text()*/
	}, function(data) {
		App.unblockUI('#importScore');
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

function toPageSize(pageSize){
	App.blockUI({
        target: '.pace-done'
    });
	$("#pageSize").val(pageSize);
	$("#searchForm").submit();
}

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

