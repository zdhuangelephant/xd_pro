<#include "/common/layout.ftl" /> <@htmlHead title="首页">
<script src="${JS_PATH}artdialog/artDialog.js?skin=blue"></script>
<script src="${JS_PATH}artdialog/plugins/iframeTools.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".click").click(function() {
			$(".tip").fadeIn(200);
		});

		$(".tiptop a").click(function() {
			$(".tip").fadeOut(200);
		});

		$(".sure").click(function() {
			$(".tip").fadeOut(100);
		});

		$(".cancel").click(function() {
			$(".tip").fadeOut(100);
		});

	});
</script>
</@htmlHead> <@htmlBody bodyclass="page-content">
<div class="page-header">
	<h1>
		机器人列表 <small> <i class="ace-icon fa fa-angle-double-right"></i>
			机器人
		</small>
	</h1>
</div>

<form class="form-inline" style="margin-bottom: 10px;">
	<a class="btn btn-primary pull-right"
		style="border: 0px; width: 90px; margin-left: 10px;"
		onclick="addRobot()">添加机器人</a>
</form>

<table class="table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th class="center" style="width: 50px;">ID</th>
			<th>昵称</th>
			<th>UniqueId</th>
			<th>创建时间</th>
			<th style="width: 250px;">操作</th>
		</tr>
	</thead>
	<tbody>
		<#list allRobot.result as robot>
		<tr>
			<td>${robot.id}</td>
			<td>${robot.nickName}</td>
			<td>${robot.xdUniqueId}</td>
			<td>${robot.createTime}</td>

			<td><a onclick="editChallengeRobot('${robot.id}')">编辑</a>
				<a style="padding: 5px;">|</a> <a
				onclick="delChallengeRobot('${robot.id}','该条信息')">删除</a></td>
		</tr>
		</#list>
	</tbody>
</table>
<@page totalCount="${allRobot.totalCount}"
pageNo="${allRobot.pageNo}"
totalPage="${allRobot.totalPage}" url=""> </@page>
<script>
	/**
	 * 编辑机器人
	 * @param catId
	 */
	function editChallengeRobot(catId) {
		art.dialog.open('/robot/robotEdit?id=' + catId, {
			title : '编辑机器人',
			width : 400,
			ok : function() {
				var iframe = this.iframe.contentWindow;
				if (!iframe.document.body) {
					alert('iframe还没加载完毕呢')
					return false;
				};
				var form = iframe.document.getElementById('editForm');
				form.submit();
				return false;
			},
			cancelVal : '关闭',
			cancel : true,
			close : function() {
				location.reload();
			}
		});
	}

	
	/**
	 * 添加任务
	 */
	function addRobot() {
		art.dialog.open('/robot/robotAdd', {
			title : '添加机器人',
			width : 350,
			height : 400,
			ok : function() {
				var iframe = this.iframe.contentWindow;
				if (!iframe.document.body) {
					alert('iframe还没加载完毕呢')
					return false;
				}
				;
				var form = iframe.document.getElementById('addForm');
				form.submit();
				return false;
			},
			cancelVal : '关闭',
			cancel : true,
			close : function() {
				location.reload();
			}
		});
	}

	/**
	 * 删除机器人
	 * @param catId
	 */
	function delChallengeRobot(id, catName) {
		if (confirm("确认要删除" + catName + ",该机器人下挂在的所有记录将会一同被删除?")) {
			$.post("/robot/robotDelete", {
				id : id
			}, function(data) {
				if (data.retCode == 0) {
					alert("删除失败");
				} else {
					alert("删除成功");
				}
				location.reload();
			});
		}
	}

	function toPage(page) {
		var url = window.location.pathname + "?page=" + page;
		self.location = url;
	}
</script>

</@htmlBody>
