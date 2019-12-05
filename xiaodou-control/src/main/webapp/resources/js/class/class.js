$(function(){
	$('#update').on('show.bs.modal', function(event) {
		var button = $(event.relatedTarget); // Button that
		// triggered
		// the modal
		var classId = button.data('sid'); // Extract info
		var className = button.data('classname');
		var description = button.data('description');
		var studentCount = button.data('student_count');
		// from
		// data-* attributes
		var modal = $(this);
		modal.find('.modal-title').text('修改班级信息 - 班级id:' + classId);
		modal.find('#update_id').val(classId);
		modal.find('#update_className').val(className);
		modal.find('#update_description').val(description);
		if(studentCount>0){
			modal.find('#className').attr("disabled",true);
		}
	});
	$('#relate').on('show.bs.modal', function(event) {
		var button = $(event.relatedTarget); // Button that
		// triggered
		// the modal
		var classId = button.data('sid'); // Extract info
		var adminId = button.data('adminid');
		// from
		// data-* attributes
		var modal = $(this);
		modal.find('#relate_id').val(classId);;
		if(isEmpty(adminId)){
			modal.find("input:radio[name='adminId']").removeAttr('checked');
		}else{
			//modal.find("input[id='"+adminId+"']").attr("checked",true);
			modal.find("input:radio[name='adminId'][id='" + adminId + "']").prop("checked", "checked");
		}
	});
	$("#saveClass").click(function(){
		if(isEmpty($("#save_className").val())){
			$.fn.modalMsg("请填写班级名称", "error");
			return;
		}
		$(this).attr("disabled", true);
		$.ajax({
			cache : true,
			type : "POST",
			url : "/class/save_class",
			data : $("#saveForm").serialize(),// 通过序列化表单值，创建 URL 编码文本字符串。
			async : false,// 是否异步
			dataType : "text",
			error : function() {
				$.fn.modalAlert("请求异常！", "error");
				$(this).attr("disabled", false);
			},
			success : function(data) {
				if ("true" == data) {
					location.reload();
				} else if("false" == data) {
					$.fn.modalAlert("添加失败！", "error");
				} else{
					$.fn.modalAlert(data, "error");
				}
			},
		});
		$(this).attr("disabled", false);
	});
});


function updateClass(){
	if(isEmpty($("#update_className").val())){
		$.fn.modalMsg("请填写班级名称", "error");
		return;
	}
	$.ajax({
		cache : true,
		type : "POST",
		url : "/class/update_class",
		data : $("#updateForm").serialize(),// 通过序列化表单值，创建 URL 编码文本字符串。
		async : false,// 是否异步
		dataType : "text",
		error : function() {
			$.fn.modalMsg("异常", "error");
		},
		success : function(data) {
			if ("true" == data) {
				location.reload();
			} else if("false" == data) {
				$.fn.modalAlert("更新失败！", "error");
			} else{
				$.fn.modalAlert(data, "error");
			}
		},
	});
}

function relateClassAdmin(){
	$.ajax({
		cache : true,
		type : "POST",
		url : "/class/update_class",
		//data : $("#relateForm").serialize(),// 通过序列化表单值，创建 URL 编码文本字符串。
		data :{
			"id":$("#relate_id").val(),
			"adminId":$("input:radio[name='adminId']:checked").val(),
			"adminName":$("input:radio[name='adminId']:checked").next().html()
		},
		async : false,// 是否异步
		dataType : "text",
		error : function() {
			$.fn.modalMsg("异常", "error");
		},
		success : function(data) {
			if ("true" == data) {
				$.fn.modalMsg("修改成功", "success");
				location.reload();
			} else if("false" == data) {
				$.fn.modalAlert("更新失败！", "error");
			} else{
				$.fn.modalAlert(data, "error");
			}
		},
	});

}

function removeClass(classId){
	// 询问框
	layer.confirm('确定要删除该班级？', {
		btn : [ '确认', '取消' ]
	// 按钮
	}, function() {
		$.ajax({
			cache : true,
			type : "POST",
			url : "/class/remove_class",
			data : {
				"classId" : classId
			},
			dataType : "text",
			error : function() {
				$.fn.modalAlert("删除失败！", "error");
			},
			success : function(data) {
				if ("true" == data) {
					location.reload();
				} else {
					$.fn.modalAlert("删除失败！", "error");
				}
			},
		});
	}, function() {
	});
}

function studentList(classId){
	addNewTabs({
		id: "10213",title: '学生管理',close: true,url: '/student/student_list?classId='+classId}
	);
}

function batchDelStudent(classId){
	// 询问框
	layer.confirm('确定要批量删除该班级下的所有学生（无报名课程）？', {
		btn : [ '确认', '取消' ]
	// 按钮
	}, function() {
		$.ajax({
			cache : true,
			type : "POST",
			url : "/student/remove_student_by_classId",
			data : {
				"classId" : classId
			},
			dataType : "text",
			error : function() {
				$.fn.modalAlert("批量删除失败！", "error");
			},
			success : function(data) {
				if(!isEmpty(data)){
					if ("false" == data) {
						$.fn.modalAlert("批量删除失败！", "error");
					} else if("CANOT_BATCH_DEL" == data){
						$.fn.modalAlert("已有报名信息的学生不能被删除，请检查学生的报名情况！", "error");
						location.reload();
					}else {
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