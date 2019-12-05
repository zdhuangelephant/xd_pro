$(function() {
	var learnType = $("#learnType").val();
	$("#learnType1 option[value='" + learnType + "']").attr("selected", true);

	// Date range picker
	$('#reservation').daterangepicker();
	// Date picker
	$('#datepicker').datepicker({
		language: "zh-CN",
        todayHighlight: true,
        format: 'yyyy-mm-dd',
        autoclose: true,
        startView: 'days',
        maxViewMode:'years',
        minViewMode:'days'
	});
	
	/**
	 * 去往导出预览页面
	 */
	$("#import").click(function() {
		// if (!verify())
		// return;
		$("#saveStudent").attr("disabled", true);
		$.ajax({
			cache : true,
			type : "POST",
			url : "/score/import_learn_record",
			data : $("#form").serialize(),// 通过序列化表单值，创建 URL 编码文本字符串。
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

				} else {
					layer.msg('添加学生失败！', {
						time : 2000, // 2s后自动关闭
						icon : 1
					});
				}
			}
		});
		location.reload();
	});
});

