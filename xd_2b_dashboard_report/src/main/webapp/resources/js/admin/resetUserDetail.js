$(function() {

	$("#saveAdmin").click(function() {
		$.ajax({
			cache : true,
			type : "POST",
			url : "/admin/do_reset_user_detail",
			data : $("#saveForm").serialize(),
			dataType : "text",
			error : function() {
				layer.msg('修改失败', {
					time : 2000, // 2s后自动关闭
					icon : 1
				});
			},
			success : function(data) {
				if (!isEmpty(data) && "true" == data) {
					window.parent.location.href="/admin/loginOut";
					//location.reload();
				} else {
					layer.msg('修改失败', {
						time : 2000, // 2s后自动关闭
						icon : 1
					});
				}
			},
		});
	});

});