var baseurl = location.protocol + "//" + location.host;

/** *********************************************************************************************************************** */
$(function() {
	
	$("#addBtn").bind('click',function() {
				$(".content").html($('#editor').val());
				
				function check(checkid, focusid, msg) {
					var panelValue = $("#" + checkid).val();
					if (!panelValue || typeof (panelValue) == 'undefined'
							|| panelValue == 'undefined'
							|| panelValue == 'null' || panelValue.length == 0) {
						alert(msg);
						$('#' + focusid).focus();
						return false;
					}  
					return panelValue;
				}
				var title = check("title", "title", "请输入标题.");
				if (!title)
					return;
				var subtitle = check("subtitle", "subtitle", "请选择副标题.");
				if (!subtitle)
					return;
				var activityTime = check("activityTime", "activityTime", "请选择活动时间.");
				if (!activityTime)
					return;
				var deadlineTime = check("deadlineTime", "deadlineTime", "请截至时间.");
				if (!deadlineTime)
					return;
				var activityPlace = check("activityPlace", "activityPlace",
						"请选择活动地点.");
				if (!activityPlace)
					return;
				var tutorId = check("tutorId", "tutorId",
						"请选择活动相关老师.");
				if (!tutorId)
					return;
				var content = $("#storeContent").html();
				var contentImage = $("#pic").val();
				
				$.ajax({
					url : baseurl + '/activity/doAdd',
					data : {
						'title' : title,
						'contentImage' : contentImage,
						'subtitle' : subtitle,
						'activityTime' : activityTime,
						'deadlineTime' : deadlineTime,
						'activityPlace' : activityPlace,
						'tutorId' : tutorId,
						'content' : content
					},
					type : 'post',
					dataType : 'json',
					success : function(res) {
						if (res.retCode == '0') {
							alert("保存成功");
							
						} else {
							alert(res.retDesc);
						}
						//window.location.href = '/activity/list';
						//location.reload();
						//window.parent.location.href='/activity/list';
					}
				});
	});
	
	
	$("#updateBtn").bind('click',function() {
		var id = $("#primaryId").val();
		$(".content").html($('#editor').val());
		
		function check(checkid, focusid, msg) {
			var panelValue = $("#" + checkid).val();
			if (!panelValue || typeof (panelValue) == 'undefined'
					|| panelValue == 'undefined'
					|| panelValue == 'null' || panelValue.length == 0) {
				alert(msg);
				$('#' + focusid).focus();
				return false;
			}  
			return panelValue;
		}
		var title = check("title", "title", "请输入标题.");
		if (!title)
			return;
		var subtitle = check("subtitle", "subtitle", "请选择副标题.");
		if (!subtitle)
			return;
		var activityTime = check("activityTime", "activityTime", "请选择活动时间.");
		if (!activityTime)
			return;
		var deadlineTime = check("deadlineTime", "deadlineTime", "请截至时间.");
		if (!deadlineTime)
			return;
		var activityPlace = check("activityPlace", "activityPlace",
				"请选择活动地点.");
		if (!activityPlace)
			return;
		var tutorId = check("tutorId", "tutorId",
				"请选择活动相关老师.");
		if (!tutorId)
			return;
		var content = $("#storeContent").html();
		var contentImage = $("#pic").val();
		
		$.ajax({
			url : baseurl + '/activity/doEdit',
			data : {
				'id' : id,
				'title' : title,
				'contentImage' : contentImage,
				'subtitle' : subtitle,
				'activityTime' : activityTime,
				'deadlineTime' : deadlineTime,
				'activityPlace' : activityPlace,
				'tutorId' : tutorId,
				'content' : content
			},
			type : 'post',
			dataType : 'json',
			success : function(res) {
				if (res.retCode == '0') {
					alert("修改成功");
					//action = "doEdit";
				} else {
					alert(res.retDesc);
				}
				//window.location.href = '/activity/list';
				//location.reload();
				//window.parent.location.href='/activity/list';
			}
		});
});
	
	var editor = new Simditor({
		textarea : $('#editor'),
		toolbar : [ 'title', 'bold', 'italic', 'underline', 'strikethrough',
				'color', 'table', 'link', 'hr', 'ol', 'html' ],
		toolbarFloat : true,
		toolbarFloatOffset : 2
	});
	
	function out() {
		this.initRight = function() {
			$(".rightMain").children().hide();
			$(".richText-box").show();
			$(".richText").addClass('on');
			
			$("#editor").val($(".content").html());
			$(".simditor-body").html($(".content").html());
		}
	}

	var _out = new out();
	_out.initRight();
	window.mainjs = _out;

});