<!--2015/7/8-->
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="full-screen" content="yes"/>
    <meta name="screen-orientation" content="portrait"/>
    <meta name="x5-fullscreen" content="true"/>
    <meta name="360-fullscreen" content="true"/>
    <title>学生列表</title>
</head>
<style type="text/css">
table tr td {border-style: solid; border-width: 5px;}
</style>
<body>
<script type="text/javascript" src="${baseJsOP}/jquery-2.1.1.min.js"></script>
<div class="main" id="stage">
	<input type="hidden" id="action" value="${action}"/>
	<input type="hidden" id="userId" value="${user.userId}"/>
	<div>
		姓名:
		<input type="text" id="name" value="${user.name}"/>
	</div>
	<div>
		能力值:
		<select id="ability">
			<option value = "0" <#if user.ability == 0>check=true</#if>>与标准答案一致</option>
			<option value = "2" <#if user.ability == 2>check=true</#if>>随机错0~2道</option>
			<option value = "3" <#if user.ability == 3>check=true</#if>>随机错0~3道</option>
		</select>
	</div>
	<div>
		学号:
		<input type="text" id="userName" value="${user.userName}"/>
	</div>
	<div>
		密码:
		<input type="password" id="passwd" value="${user.passwd}"/>
	</div>
	<button id="finish">完成</button>
</div>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		var action = $("#action").val();
		$("#finish").click(function(){
			$.ajax({
				url: action,
				type: "post",
				data: {
					userId: $("#userId").val(),
					name: $("#name").val(),
					ability: $("#ability").val(),
					userName: $("#userName").val(),
					passwd: $("#passwd").val()
				},
				success: function(data) {
					alert(data);
				}
			})
		})
	});
</script>
</html>