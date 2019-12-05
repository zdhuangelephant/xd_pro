$(function() {

	$("#savePassword").click(function() {
		var currPassword = $("#currPassword").val();
		var newPassword = $("#newPassword").val();
		var confirmPassword = $("#confirmPassword").val();
		
		if(isEmpty(newPassword) || isEmpty(confirmPassword) || isEmpty(currPassword)){
			layer.msg('密码不可以为空', {
				time : 2000, // 2s后自动关闭
				icon : 1
			});
			return ;
		}
		
			$.ajax({
				cache : true,
				type : "POST",
				url : "/admin/checkPwd",
				data : {'currPassword':currPassword},
				dataType : "text",
				error : function() {
					layer.msg('当前密码输入有误，请重新输入', {
						time : 2000, // 2s后自动关闭
						icon : 1
					});
				},
				success : function(data) {
					if(notEmpty(data) && "true" == data){
						var newPassword = $("#newPassword").val();
						var confirmPassword = $("#confirmPassword").val();
						
						if(newPassword != confirmPassword){
							layer.msg('两次输入的密码不一致，请重新输入', {
								time : 2000, // 2s后自动关闭
								icon : 1
							});
							return ;
						}
						$.ajax({
							cache : true,
							type : "POST",
							url : "/admin/do_reset_password",
							data : $("#savePasswordForm").serialize(),
							dataType : "text",
							error : function() {
								layer.msg('修改失败', {
									time : 2000, // 2s后自动关闭
									icon : 1
								});
							},
							success : function(data) {
								if (notEmpty(data) && "true" == data) {
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
					}else{
						layer.msg('当前密码输入有误，请重新输入', {
							time : 2000, // 2s后自动关闭
							icon : 1
						});
						return ;
					}
					}
			});
	
	});

});