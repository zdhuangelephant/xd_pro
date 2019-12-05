$(function() {
	var status = $("#status").val();
	$("#status1 option[value='" + status + "']").attr("selected", true);

	// Date picker
	$('#orderTime').datepicker({
		language : "zh-CN",
		todayHighlight : true,
		format : 'yyyy-mm-dd',
		autoclose : true,
		startView : 'day',
		maxViewMode : 'years',
		minViewMode : 'days'
	});
	$('#payTime').datepicker({
		language : "zh-CN",
		todayHighlight : true,
		format : 'yyyy-mm-dd',
		autoclose : true,
		startView : 'day',
		maxViewMode : 'years',
		minViewMode : 'days'
	});

});

function closeOrder(orderId, orderNumber) {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/order/close_order",
		data : {
			"orderId" : orderId,
			"orderNumber" : orderNumber
		},
		dataType : "text",
		error : function() {
			$.fn.modalMsg("异常！", "error");
		},
		success : function(data) {
			if ("true" == data) {
				location.reload();
			} else {
				$.fn.modalAlert("关闭订单失败！", "error");
			}
		},
	});
}
function removeOrder(orderId, type) {
	// 询问框
	layer.confirm('确定要删除该订单？', {
		btn : [ '确认', '取消' ]
	// 按钮
	}, function() {
		$.ajax({
			cache : true,
			type : "POST",
			url : "/order/remove_order",
			data : {
				"orderId" : orderId
			},
			dataType : "text",
			error : function() {
				$.fn.modalMsg("异常！", "error");
			},
			success : function(data) {
				if ("true" == data) {
					if (type == 1)
						location.reload();
					if (type == 2)
						history.go(-1);
				} else {
					$.fn.modalAlert("关闭订单失败", "error");
				}
			},
		});
	}, function() {
	});
}

function saveOrder() {
	$("#saveOrder").attr("disabled", true);
	var studentCount = $("#studentCount").val();
	var applyCount = $("#applyCount").val();
	var totalAmount = $("#totalAmount").val();
	var applyIds = $("#applyIds").val();
	$.ajax({
		cache : false,
		type : "POST",
		url : "/order/save_order",
		data : {
			"applyIds" : applyIds,
			"studentCount" : studentCount,
			"applyCount" : applyCount,
			"totalAmount" : totalAmount
		},// 通过序列化表单值，创建
		// URL 编码文本字符串。
		async : false,// 是否异步
		dataType : "text",
		error : function() {
			$.fn.modalMsg("异常", "error");
		},
		success : function(data) {
			if (notEmpty(data)) {
//				// 询问框
//				layer.confirm('下单成功，请尽快确认报名！', {
//					btn : [ '确认', '取消' ]
//				// 按钮
//				}, function() {
//					//defaultPay(data);
//					location.href = "/order/order_detail?orderNumber="
//						+ data;
//				}, function() {
//					//$("#saveOrder").removeAttr("disabled");
//					location.href = "/order/order_detail?orderNumber="
//						+ data;
//				});
				location.href = "/order/order_detail?orderNumber="
					+ data;
			} else {
				$.fn.modalAlert("下单失败！", "error");
				$("#saveOrder").attr("disabled", false);
			}
		}
	});
}

function defaultApply(orderNumber){
	// 询问框
	layer.confirm('确认报名后，将无法更改报名信息！', {
		btn : [ '确认', '取消' ]
	// 按钮
	}, function() {
		defaultPay(orderNumber);
	}, function() {
		
	});
}

function defaultPay(orderNumber) {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/order/default_pay",
		data : {
			"orderNumber" : orderNumber
		},
		dataType : "text",
		error : function() {
			$.fn.modalMsg("异常！", "error");
		},
		success : function(data) {
			if ("true" == data) {
				// location.reload();
				location.href = "/order/order_detail?orderNumber="
						+ orderNumber;
			} else {
				$.fn.modalAlert("支付失败！", "error");
			}
		},
	});

}