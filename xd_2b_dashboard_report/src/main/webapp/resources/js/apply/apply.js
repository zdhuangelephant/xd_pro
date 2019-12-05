$(function() {
	var orderStatus = $("#orderStatus").val();
	$("#orderStatus1 option[value='" + orderStatus + "']").attr("selected",
			true);
	var examDate = $("#examDate").val();
	$("#examDate1 option[value='" + examDate + "']").attr("selected", true);
	var catId = $("#catId").val();
	$("#catId1 option[value='" + catId + "']").attr("selected", true);
	var productId = $("#productId").val();
	$("#productId1 option[value='" + productId + "']").attr("selected", true);
	var classId = $("#classId").val();
	$("#classId1 option[value='" + classId + "']").attr("selected", true);

});
function changeCat(catId) {
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
			$.fn.modalMsg("异常", "error");
		},
		success : function(data) {
			if (!isEmpty(data)) {
				// 显示课程操作
				var productJson = JSON.parse(data);
				// alert(productJson[0].name);
				// alert(productJson[0].applyStatus);
				// alert(productJson);
				var html = "<option value=''>全部课程</option>";
				$.each(productJson, function(key, val) {
					html += "<option value='" + val.id + "'>" + val.name
							+ "</option>";
				});
				$("#productId1").html(html);
			}
		}
	});
}

// 批量删除未缴费
function batchDelApply(orderStatus){
	// 询问框
	layer.confirm('确定要批量删除未缴费报名数据？', {
		btn : [ '确认', '取消' ]
	// 按钮
	}, function() {
		$.ajax({
			cache : true,
			type : "POST",
			url : "/apply/remove_apply_by_order_status",
			data : {
				"orderStatus" : orderStatus
			},
			dataType : "text",
			error : function() {
				$.fn.modalAlert("批量删除失败！", "error");
			},
			success : function(data) {
				if(!isEmpty(data)){
					if ("false" == data) {
						$.fn.modalAlert("批量删除失败！", "error");
					} else {
						$.fn.modalAlert("批量删除"+data+"条数据！", "success");
						location.reload();
					}
				}else{
					$.fn.modalAlert("批量删除失败！", "error");
				}
			},
		});
	}, function() {
	});
}