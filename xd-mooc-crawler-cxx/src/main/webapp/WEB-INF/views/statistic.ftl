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
    <title>分析展示</title>
</head>
<style type="text/css">
table tr td {border-style: solid; border-width: 5px;}
</style>
<body>
<script type="text/javascript" src="${baseJsOP}/jquery-2.1.1.min.js"></script>
<div class="main" id="stage">
    <div id="statistic">
	<table>
		<tr><td>CourseId</td><td>TotalCount</td><td>WaitCount</td><td>ProcessingCount</td><td>FinishCount</td></tr>
	</table>
	</div>
</div>
</body>
<script type="text/javascript">
	$(document).ready(function(){
		refreshData();
		setInterval(function(){
			refreshData();
		}, 1000);
	});

	function refreshData() {
		$.ajax({
			url: location.origin + '/statistic_detail',
			success: function(data) {
				$("#statistic").html(data);
			}
		})
	}
</script>
</html>