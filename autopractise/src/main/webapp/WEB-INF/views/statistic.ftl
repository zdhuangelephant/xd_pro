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
	<div>
		<button id="importUser">导入学生</button>
		<button id="addUser">添加学生</button>
		<button id="resetAll">重置完成状态</button>
		<button id="debugResetAll">重置全部状态</button>
		<select id="ability">
			<option value = "0">与标准答案一致</option>
			<option value = "2">随机错0~2道</option>
			<option value = "3">随机错0~3道</option>
		</select>
		<button id="changeAllAbility">统一设置能力值</button>
		<select id="userStatus">
			<option value = "-99">全部</option>
			<#list userStatus as status>
				<option value = "${status.code}">${status.desc}</option>
			</#list>
		</select>
		<button id="refreshData">确定</button>
	</div>
    <div id="statistic">
	<table>
		<tr><td>学生ID</td><td>姓名</td><td>学习状态</td><td>操作</td></tr>
	</table>
	</div>
</div>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		$("#importUser").click(function(){
			window.open(location.origin + '/import_user', '_blank');
		})
		$("#addUser").click(function(){
			window.open(location.origin + '/add_user', '_blank');
		})
		$("#resetAll").click(function(){
			$.ajax({
				url: location.origin + '/reset_all',
				success: function(data) {
					refreshData();
					alert(data);
				}
			})
		})
		$("#debugResetAll").click(function(){
			$.ajax({
				url: location.origin + '/debug_reset_all',
				success: function(data) {
					refreshData();
					alert(data);
				}
			})
		})
		$("#changeAllAbility").click(function(){
			var ability = $("#ability").val();
			$.ajax({
				url: location.origin + '/change_all_ability/' + ability,
				success: function(data) {
					refreshData();
					alert(data);
				}
			})
		})
		$("#refreshData").click(function(){
			refreshData();
		})
		refreshData();
		setInterval(function(){
			refreshData();
		}, 3000);
	});

	function refreshData() {
		var userStatus = $("#userStatus").val();
		$.ajax({
			url: location.origin + '/statistic_detail/' + userStatus,
			success: function(data) {
				$("#statistic").html(data);
			}
		})
	}

	function modify(userId) {
		window.open(location.origin + '/modify_user/'+userId, '_blank');
	}

	function deleteUser(userId, userName) {
		var r=confirm("确认要删除"+userName+"吗?");
		if (r==true)
		{
			$.ajax({
				url: location.origin + '/delete_user/' + userId,
				success: function(data) {
					$("#statistic").html(data);
				}
			})
		}
	}

	function detail(userId) {
		window.open(location.origin + '/detail/'+userId, '_blank');
	}

	function report(userId, courseId) {
		window.open(location.origin + '/report/'+userId+'/'+courseId, '_blank');
	}

	function reset(userId) {
		$.ajax({
			url: location.origin + '/reset/' + userId,
			success: function(data) {
				refreshData();
				alert(data);
			}
		})
	}
</script>
</html>