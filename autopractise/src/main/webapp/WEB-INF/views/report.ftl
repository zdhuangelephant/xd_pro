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
    <title>${userInfo.name}成绩单</title>
</head>
<style type="text/css">
table tr td {border-style: solid; border-width: 5px;}
</style>
<body>
<script type="text/javascript" src="${baseJsOP}/jquery-2.1.1.min.js"></script>
	<div>
		<select id="courseId">
			<option value="-1">全部课程</option>
			<#list userCourseList as course>
				<option value="${course.courseId}" <#if courseId == course.courseId>selected="selected"</#if>>${course.courseName}</option>
			</#list>
		</select>
		<button id="search">查看</button>
	</div>
    <div id="report">
		<table>
			<tr><td>课程ID</td><td>课程名称</td><td>练习阶段</td><td>练习次数</td><td>练习状态</td><td>试卷ID</td><td>成绩</td><td>完成时间</td></tr>
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
		var courseId = $("#courseId").val();
		$.ajax({
			url: location.origin + '/report_detail/${userInfo.userId}/' + courseId,
			success: function(data) {
				$("#report").html(data);
			}
		})
	}
	function doTest(paperId) {
		window.open(location.origin + '/do_test/'+paperId, '_blank');
	}
</script>
</html>