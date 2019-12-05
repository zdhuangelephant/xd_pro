<#include "/common/common.ftl">
<!DOCTYPE html>
<!-- BEGIN BODY -->

<body class="page-header-fixed">

	<!-- BEGIN CONTAINER -->

	<!-- 模态框（Modal） -->
	<div class="modal fade" id="eventModal" tabindex="-1" role="dialog" aria-labelledby="eventModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h4 class="modal-title" id="myModalLabel">添加监控点</h4>
	            </div>
		        <form action="#" class="form-horizontal" id="eventForm">
		            <div class="modal-body">
		            	<input type="hidden" id="action" name="action"/>
		            	<input type="hidden" id="alarmEventId" name="alarmEventId"/>
		            	<div class="control-group">
							<label class="control-label">模块名</label>
							<div class="controls">
								<input name="module" id="module" type="text" placeholder="填写模块名" class="m-wrap medium" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">方法名</label>
							<div class="controls">
								<input name="name" id="name" type="text" placeholder="填写方法名" class="m-wrap medium" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">报警频率</label>
							<div class="controls">
								<input name="rate" id="rate" type="text" placeholder="间隔几秒触发报警" class="m-wrap medium" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">报警阈值</label>
							<div class="controls">
								<input name="threshold" id="threshold" type="text" placeholder="多少次调用触发报警" class="m-wrap medium" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">报警策略</label>
							<div class="controls">
								<select name="alarmPolicyId" id="alarmPolicyId" class="small m-wrap" value="7">
									<#list policyList as model>
										<option value="${model.alarmPolicyId}">${model.alarmPolicyName}</option>
									</#list>
								</select>
							</div>
						</div>
		            </div>
		            <div class="modal-footer">
		                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		                <button type="button" class="btn btn-primary" id="addEvent">提交</button>
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

							报警策略列表

						</h3>

						<ul class="breadcrumb">

							<li>

								<i class="icon-home"></i>

								<a href="index.html">报警中心</a> 

								<i class="icon-angle-right"></i>

							</li>

							<li>

								<a href="#">事件中心</a>

								<i class="icon-angle-right"></i>

							</li>

							<li><a href="#">报警事件列表</a></li>

						</ul>

						<!-- END PAGE TITLE & BREADCRUMB-->

					</div>

				</div>

				<!-- END PAGE HEADER-->

				<!-- BEGIN PAGE CONTENT-->

				<div class="row-fluid">

					<div class="span12">

						<!-- BEGIN EXAMPLE TABLE PORTLET-->

						<div class="portlet box blue tabbable">
							<div class="portlet-title">
								<div class="caption"><i class="icon-edit"></i>报警事件列表</div>
							</div>
							<div class="portlet-body">
								<div class="tabbable portlet-tabs">
									<ul class="nav nav-tabs">
										<li><a href="#portlet_tab2" data-toggle="tab">监控事件</a></li>
										<li class="active"><a href="#portlet_tab1" data-toggle="tab">异常事件</a></li>
									</ul>
									<div class="tab-content">
										<div class="tab-pane active" id="portlet_tab1">
											<div class="btn-group">
												<!-- 按钮触发模态框 -->
												<button class="btn btn-primary btn-lg" onClick="addEvent()">添加报警事件</button>
											</div>
											<table class="table table-bordered table-hover table-full-width" id="sample_1">
												<thead>
													<tr>
														<th>ID</th>
														<th>模块名</th>
														<th>事件名</th>
														<th>报警频率</th>
														<th>报警阈值</th>
														<th>报警策略</th>
														<th>操作</th>
													</tr>
												</thead>
												<tbody>
												  <#list excptionEventList as model>
														<tr class="" >
															<td >${model.alarmEventId}</td>
															<td >${model.module}</td>
															<td >${model.name}</td>
															<td >${model.rate}</td>
															<td >${model.threshold}</td>
															<td >${model.alarmPolicyName}</td>
															<td><a  class="edit" href="javascript:;" onClick="editEvent('${model.alarmEventId}')">Edit</a>&nbsp; 
															| &nbsp; <a  class="delete" href="javascript:;" onClick="deleteEvent('${model.alarmEventId}')">Delete</a>
															</td>
														</tr>
												  </#list>
												</tbody>
											</table>
										</div>
										<div class="tab-pane" id="portlet_tab2">
											<table class="table table-bordered table-hover table-full-width" id="sample_2">
												<thead>
													<tr>
														<th>ID</th>
														<th>模块名</th>
														<th>事件名</th>
														<th>报警频率</th>
														<th>报警阈值</th>
														<th>报警策略</th>
														<th>操作</th>
													</tr>
												</thead>
												<tbody>
												  <#list monitorEventList as model>
														<tr class="" >
															<td >${model.alarmEventId}</td>
															<td >${model.module}</td>
															<td >${model.name}</td>
															<td >${model.rate}</td>
															<td >${model.threshold}</td>
															<td >${model.alarmPolicyName}</td>
															<td><a  class="edit" href="javascript:;" onClick="editEvent('${model.alarmEventId}')">Edit</a>
															</td>
														</tr>
												  </#list>
												</tbody>
											</table>
										</div>
									</div>
								</div>
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
	<script src="${rc.contextPath}/media/js/table-editable4.js"></script>   
<script>
	function addEvent() {  
	    $("#eventForm #action").val('/dashboard/alarm_event/add');
	    $('#eventModal').modal('show');
	}
	function editEvent(id) {
		$("#eventForm #action").val('/dashboard/alarm_event/update');
		$.ajax({
			url : '/dashboard/alarm_event/get',
			data : {
				eventId : id
			},
			success : function(data) {
				data=eval('(' + data + ')');
				var event = data.event;
				$("#eventForm #alarmEventId").val(event.alarmEventId);
				$("#eventForm #module").val(event.module),
				$("#eventForm #name").val(event.name),
				$("#eventForm #rate").val(event.rate),
				$("#eventForm #threshold").val(event.threshold),
				$("#eventForm #alarmPolicyId").val(event.alarmPolicyId),
			    $('#eventModal').modal('show');
			}
		});
	}
	function deleteEvent(id) {
		$.ajax({
			url : '/dashboard/alarm_event/delete',
			data : {
				eventId : id
			},
			success : function(data) {
				data=eval('(' + data + ')');
				if(data.retcode == '0') {
					alert("操作成功");
					window.location.reload();
				}else{
					alert("操作失败");
				}
			}
		});
	}
	$('#addEvent').click(function(){
		$.ajax({
			url : $("#eventForm #action").val(),
			type : "POST",
			data : {
				alarmEventId : $("#eventForm #alarmEventId").val(),
				module : $("#eventForm #module").val(),
				name : $("#eventForm #name").val(),
				rate : $("#eventForm #rate").val(),
				threshold : $("#eventForm #threshold").val(),
				alarmPolicyId : $("#eventForm #alarmPolicyId").val()
			},
			success : function(data){
				alert("操作成功");
				window.location.reload();
			}
		});
	});
</script>
</html>