<#include "/common/common.ftl">
<!DOCTYPE html>
<!-- BEGIN BODY -->

<body class="page-header-fixed">

	<!-- BEGIN CONTAINER -->

	<!-- 模态框（Modal） -->
	<div class="modal fade" id="crontMonitorModal" tabindex="-1" role="dialog" aria-labelledby="crontMonitorModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h4 class="modal-title" id="myModalLabel">添加定时监控</h4>
	            </div>
		        <form action="#" class="form-horizontal" id="crontMonitorForm">
		            <div class="modal-body">
		            	<input type="hidden" id="action" name="action"/>
		            	<input type="hidden" id="id" name="id"/>
		            	<div class="control-group">
							<label class="control-label">监控名</label>
							<div class="controls">
								<input name="name" id="name" type="text" placeholder="监控名" class="m-wrap medium" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">调度协议</label>
							<div class="controls">
								<select class="large m-wrap" tabindex="1" name="protocol" id="protocol">
									<option value="HTTP">HTTP</option>
									<option value="HTTPS">HTTPS</option>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">调度地址</label>
							<div class="controls">
								<input type="text" placeholder="url" class="m-wrap medium" name="url" id="url" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">超时(毫秒)</label>
							<div class="controls">
								<input type="text" placeholder="5000" value="5000" class="m-wrap medium" name="timeOut" id="timeOut" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">重试次数</label>
							<div class="controls">
								<input type="text" placeholder="1" value="1" class="m-wrap medium" name="retryTime" id="retryTime" />
							</div>
						</div>
		            </div>
		            <div class="modal-footer">
		                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		                <button type="button" class="btn btn-primary" id="addCrontMonitor">提交更改</button>
		            </div>
		        </form>
	        </div><!-- /.modal-content -->
	    </div><!-- /.modal -->
	</div>
	
	<div class="page-container">
		<!-- BEGIN PAGE -->

		<div class="page-content">

			<!-- BEGIN PAGE CONTAINER-->        

			<div class="container-fluid">

				<!-- BEGIN PAGE HEADER-->

				<div class="row-fluid">

					<div class="span12">


						<!-- BEGIN PAGE TITLE & BREADCRUMB-->

						<h3 class="page-title">

							任务列表

						</h3>

						<ul class="breadcrumb">

							<li>

								<i class="icon-home"></i>

								<a href="index.html">定时监控任务</a> 

								<i class="icon-angle-right"></i>

							</li>

							<li>

								<a href="#">监控中心</a>

								<i class="icon-angle-right"></i>

							</li>

							<li><a href="#">监控点列表</a></li>

						</ul>

						<!-- END PAGE TITLE & BREADCRUMB-->

					</div>

				</div>

				<!-- END PAGE HEADER-->

				<!-- BEGIN PAGE CONTENT-->

				<div class="row-fluid">

					<div class="span12">

						<!-- BEGIN EXAMPLE TABLE PORTLET-->

						<div class="portlet box blue">

							<div class="portlet-title">

								<div class="caption"><i class="icon-edit"></i>任务列表</div>

							</div>

							<div class="portlet-body">

								<div class="clearfix">

									<div class="btn-group">

										<!-- 按钮触发模态框 -->
										<button class="btn btn-primary btn-lg" onClick="addCrontMonitor()">添加监控任务</button>

									</div>
		

								<table class="table table-striped table-hover table-bordered" id="sample_editable_3">
									<thead>
										<tr>
											<th>ID</th>
											<th>监控名</th>
											<th>调度协议</th>
											<th>调度地址</th>
											<th>整体超时</th>
											<th>失败重试次数</th>
											<th>版本号</th>
											<th>创建者</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
									  <#list list as model>
											<tr class="" >
												<td ><a style="cursor: pointer" href="/cront_monitor/monitor_log_list?apiId=${model.id}">${model.id}</a></td>
												<td >${model.name}</td>
												<td >${model.protocol}</td>
												<td ><a href="${model.url}" target="_blank">${model.url}</a></td>
												<td >${model.timeOut}</td>
												<td >${model.retryTime}</td>
												<td >${model.updateTime}</td>
												<td><a  class="edit" href="javascript:;" onClick="editCrontMonitor('${model.id}')">Edit</a></td>
												<td><a  class="delete" href="javascript:;" onClick="deleteCrontMonitor('${model.id}')">Delete</a></td>
											</tr>
									  </#list>
									</tbody>

								</table>

							</div>

						</div>

						<!-- END EXAMPLE TABLE PORTLET-->
						

					</div>

					</div>

				</div>

				<!-- END PAGE CONTENT -->

			<!-- END PAGE CONTAINER-->
		</div>          
                  
		<!-- END PAGE -->
	</div>

	<!-- END CONTAINER -->

<!-- END BODY -->
</body> 
	<script src="${rc.contextPath}/crontab/js/table-editable3.js"></script>
<script>
	function addCrontMonitor() {  
	    $("#crontMonitorForm #action").val('/crontab/doAdd');
	    $('#crontMonitorModal').modal('show');
	}
	function editCrontMonitor(id) {
		$("#crontMonitorForm #action").val('/crontab/doEdit');
		$.ajax({
			url : '/crontab/edit',
			data : {
				id : id
			},
			success : function(data) {
				data=eval('(' + data + ')');
				$("#crontMonitorForm #id").val(data.id);
				$("#crontMonitorForm #name").val(data.name);
				$("#crontMonitorForm #protocol").val(data.protocol);
				$("#crontMonitorForm #url").val(data.url);
				$("#crontMonitorForm #timeOut").val(data.timeOut);
				$("#crontMonitorForm #retryTime").val(data.retryTime);
			    $('#crontMonitorModal').modal('show');
			}
		});
	}
	function deleteCrontMonitor(id) {
		$.ajax({
			url : '/crontab/delete',
			data : {
				id : id
			},
			success : function(data) {
				alert("操作成功");
				window.location.reload();
			}
		});
	}
	$('#addCrontMonitor').click(function(){
		$.ajax({
			url : $("#crontMonitorForm #action").val(),
			data : {
				id : $("#crontMonitorForm #id").val(),
				name : $("#crontMonitorForm #name").val(),
				protocol : $("#crontMonitorForm #protocol").val(),
				url : $("#crontMonitorForm #url").val(),
				timeOut : $("#crontMonitorForm #timeOut").val(),
				retryTime : $("#crontMonitorForm #retryTime").val()
			},
			success : function(data){
				alert("操作成功");
				window.location.reload();
			}
		});
	});
</script>
</html>