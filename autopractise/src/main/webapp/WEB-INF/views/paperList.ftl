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
    <title>试卷列表</title>
</head>
<style type="text/css">
table tr td {border-style: solid; border-width: 5px;}
</style>
<body>
<script type="text/javascript" src="${baseJsOP}/jquery-2.1.1.min.js"></script>
<div>
	<select id="courseId">
		<option value="-1">全部课程</option>
		<#list paperList as paper>
			<option value="${paper.courseId}">${paper.courseName}</option>
		</#list>
	</select>
	<select id="paperStatus">
		<option value = "-99">全部</option>
		<option value = "0">未作答</option>
		<option value = "1">已作答</option>
	</select>
	<button id="search">查看</button>
</div>
<div id="paperList">
	<table>
		<tr><td>试卷ID</td><td>课程</td><td>状态</td><td>操作</td></tr>
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
		var paperStatus = $("#paperStatus").val();
		$.ajax({
			url: location.origin + '/paper_list_detail/'+courseId+'/'+paperStatus,
			success: function(data) {
				$("#paperList").html(data);
			}
		})
	}

	function doTest(paperId) {
		window.open(location.origin + '/do_test/'+paperId, '_blank');
	}

	function deletePaper(paperId) {
		var r=confirm("确认要删除试卷["+paperId+"]吗?");
		if (r==true)
		{
			$.ajax({
				url: location.origin + '/delete_paper/'+paperId,
				success: function(data) {
					refreshData();
					alert(data);
				}
			})
		}
	}

	function userList(paperId) {
		window.open(location.origin + '/paper_report/'+paperId, '_blank');
	}
</script>
</html>