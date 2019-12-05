$(function() {
		// Date picker
		$('#datepicker1').datepicker({
			language: "zh-CN",
	        todayHighlight: true,
	        format: 'yyyy-mm-dd',
	        autoclose: true,
	        startView: '0',
	        maxViewMode:'years',
	        minViewMode:'days'
		});
		$('#datepicker2').datepicker({
			language: "zh-CN",
	        todayHighlight: true,
	        format: 'yyyy-mm-dd',
	        autoclose: true,
	        startView: '0',
	        maxViewMode:'years',
	        minViewMode:'days'
		});
		
		$("#syncType1").val($("#syncType").val());
		
		
		
		var goEasySubscribekey = $("#goEasySubscribekey").val();
		//alert(goEasySubscribekey);
		var goEasy = new GoEasy({
			appkey : goEasySubscribekey
		});
		goEasy.subscribe({
			channel : "update_save_student_channel",
			onMessage : function(message) {
				$("#student").html(message.content);
			},
			onSuccess : function() {
				// alert("Channel订阅成功。");
			},
			onFailed : function(error) {
				// alert("Channel订阅失败, 错误编码：" + error.code + " 错误信息：" +
				// error.content)
			}
		});
		goEasy.subscribe({
			channel : "update_save_apply_channel",
			onMessage : function(message) {
				$("#apply").html(message.content);
			}
		});
		goEasy.subscribe({
			channel : "update_save_admissionCardCode_channel",
			onMessage : function(message) {
				$("#admissionCardCode").html(message.content);
			}
		});
		
	});



function updateStaticInfo(){
	if(isEmpty($("#examDate").val())){
		$.fn.modalMsg("请填写考期!", "error");
		return;
	}
	$.ajax({
		cache : true,
		type : "POST",
		url : "/manage/update_static_info",
		data : $("#updateForm").serialize(),// 通过序列化表单值，创建 URL 编码文本字符串。
		dataType : "text",
		error : function() {
			modalMsg(2);
		},
		success : function(data) {
			if ("true" == data) {
				$.fn.modalMsg("更新成功!", "success");
				location.reload();
			} else {
				$.fn.modalMsg("更新失败!", "error");
			}
		}
	});
}

function quartzStudent(){
	$("#quartzStudent").attr("disabled", true);
	$.get("/quartz/student",function(data,status){
		if("success"==status && !isEmpty(data)){
			if("true"==data){
				modalAlert(1);
				location.reload();
			}else{
				$.fn.modalAlert(data, "error");
				$("#student").html(data);
			}
		}else{
			modalAlert(2);
		}
		$("#quartzStudent").attr("disabled", false);
	});
}

function quartzApply(){
	$("#quartzApply").attr("disabled", true);
	$.get("/quartz/apply",function(data,status){
		if("success"==status && !isEmpty(data)){
			if("true"==data){
				modalAlert(1);
				location.reload();
			}else{
				$.fn.modalAlert(data, "error");
				$("#apply").html(data);
			}
		}else{
			modalAlert(2);
		}
		$("#quartzApply").attr("disabled", false);
	});

}

function quartzCard(){
	$("#quartzCard").attr("disabled", true);
	$.get("/quartz/admission_card_code",function(data,status){
		if("success"==status && !isEmpty(data)){
			if("true"==data){
				modalAlert(1);
				location.reload();
//				$("#quartzCard").attr("disabled", false);
//				$.getScript("/resources/content/plugins/bootstrap-table/bootstrap-table.js");
//				$.getScript("/resources/content/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js");
//				getTabel('/manage/list_sync_log',{});
			}else{
				$.fn.modalAlert(data, "error");
				$("#admissionCardCode").html(data);
			}
		}else{
			modalAlert(2);
		}
		$("#quartzCard").attr("disabled", false);
	});
}

function change(syncType){
	location.href="/manage/static_info?syncType="+syncType;
}

/**
 * 
 * @param url
 */
function getTabel(url,reqJson){
	$("#table").remove();
	var html ="" ;
	html += '<table class="table-scrollable table table-hover" id="table" ';
	html += ' data-toggle="table" data-pagination="true" data-advanced-search="true" ';
	html += ' data-id-table="advancedTable" data-row-style="rowStyle"> ';
	html += ' <thead><tr> ';
	html += '            	<th class="text-center">同步类型</th>';
	html += '            	<th class="text-center">同步时间</th>';
	html += '            	<th class="text-center">执行人</th>';
	html += '                 <th class="text-center">操作结果</th>';
	html += '            </tr>';
	html += '        </thead>';
	$.ajax({
		cache : false,
		type : "POST",
		url : url,
		data : reqJson,// 通过序列化表单值，创建
		// URL 编码文本字符串。
		async : false,// 是否异步
		dataType : "html",
		error : function() {
			$.fn.modalMsg("异常", "error");
		},
		success : function(data) {
			if (!isEmpty(data)) {
				var tableJson = JSON.parse(data);
				if(isEmpty(tableJson) && tableJson.length==0)
					return;
				$.each(tableJson,function(key, val) {
					//
					html += '<tr class="tr-parent"><td class="text-center">';
					if(val.syncType == 1)
						html += "同步学生";
					if(val.syncType == 2)
						html += "同步课程";
					if(val.syncType == 3)
						html += "同步准考证号";
					html += '</td>';
					html += '<td class="text-center">'+val.syncTime+'</td>';
					html += '<td class="text-center">'+val.syncAdminName+'</td>';
					html += '<td class="text-center">';
					if(val.syncResult == '0')
						html += '已完成'+val.completeCount+'/'+val.totalCount+'个';
					else if(val.syncResult == '1' && val.totalCount > 0)
						html += '<a class="btn btn-info" href="/manage/download_excel?syncType='+val.syncType+'&syncId='+val.syncId+'&syncTime='+val.syncTime+'>文件下载</a>';
					else
						html += '无数据';
					html += '</td></tr>';
				});
				
			} 
		}
	});
	html += '</tbody> </table>';
	$(".fixed-table-body").append(html);
}

function batchScoreByApply(){
	$("#batchScoreByApply").attr("disabled", true);
	$.get("/manage/batch_score_by_apply",function(data,status){
		if("success"==status && !isEmpty(data)){
			if("true"==data){
				modalAlert(1);
			}else{
				$.fn.modalAlert(data, "warning");
			}
		}else{
			modalAlert(2);
		}
		$("#batchScoreByApply").attr("disabled", false);
	});

}