$(function() {
	// Date picker
	$('#beginDate').datepicker({
		language : "zh-CN",
		todayHighlight : true,
		format : 'yyyy-mm-dd',
		autoclose : true,
		startView : 'day',
		maxViewMode : 'years',
		minViewMode : 'days'
	});
	$('#endDate').datepicker({
		language : "zh-CN",
		todayHighlight : true,
		format : 'yyyy-mm-dd',
		autoclose : true,
		startView : 'day',
		maxViewMode : 'years',
		minViewMode : 'days'
	});

});

function checkForm(){
	var telephone = $("#mobile").val();
	var reg = /^0?1[3-8][0-9]\d{8}$/;
	if (isEmpty(telephone)) {
		$.fn.modalMsg("请输入手机号，手机号不能为空！", "warning");
		return false;
	}
	if (reg.test(telephone)) {
		flag = true;
	} else {
		$.fn.modalMsg("请输入正确的11位手机号码！", "warning");
		return false;
	}
	if(notEmpty($("#beginDate").val()) && notEmpty($("#beginDate").val())) {
//		if($("#beginDate").val() == ""){
//			$.fn.modalAlert("请选择开始日期！", "warning");
//			return false;
//		}
//		if($("#endDate").val() == ""){
//			$.fn.modalAlert("请选择结束日期！", "warning");
//			return false;
//		}
	    var starttime = $('#beginDate').val();
		var endtime = $('#endDate').val();
	    var start = new Date(starttime.replace("-", "/").replace("-", "/"));
	    var end = new Date(endtime.replace("-", "/").replace("-", "/"));
	    if (end < start) {
	    	$.fn.modalAlert("结束日期不能小于开始日期！", "warning");
	        return false;
	    }
	}
	return true;
}


function exportSmsLogList(){
	location.href = "/sms/sms_log/export_sms_log_list?smsLogDO="+$("#searchForm").serialize();
}

