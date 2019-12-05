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
    <title>${userInfo.name}课程详情</title>
</head>
<style type="text/css">
table tr td {border-style: solid; border-width: 5px;}
</style>
<body>
<script type="text/javascript" src="${baseJsOP}/jquery-2.1.1.min.js"></script>
	<div>
		<select id="courseStatus">
			<option value="-99">全部</option>
			<#list userCourseStatus as status>
				<option value="${status.code}">${status.desc}</option>
			</#list>
		</select>
		<button id="search">查看</button>
	</div>
    <div id="detail">
		<table>
			<tr><td>课程ID</td><td>课程名称</td><td>课程类型</td><td>课程状态</td><td>操作</td></tr>
		</table>
	</div>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		$("#search").click(function(){
			refreshData();
		})
		refreshData();
	});

	function refreshData() {
		var status = $("#courseStatus").val();
		$.ajax({
			url: location.origin + '/detail_detail/${userInfo.userId}/'+status,
			success: function(data) {
				$("#detail").html(data);
			}
		})
	}

	function report(userId, courseId) {
		window.open(location.origin + '/report/'+userId+'/'+courseId, '_blank');
	}

	function doTest(paperId) {
		window.open(location.origin + '/do_test/'+paperId, '_blank');
	}
</script>
</html>