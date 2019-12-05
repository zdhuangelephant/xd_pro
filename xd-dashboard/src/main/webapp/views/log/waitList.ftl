<#include "/common/common.ftl">
<!DOCTYPE html>
<!-- BEGIN BODY -->

<body class="page-header-fixed">

	<!-- BEGIN CONTAINER -->

	<!-- 模态框（Modal） -->
	<div class="modal fade" id="projectModal" tabindex="-1" role="dialog" aria-labelledby="projectModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h4 class="modal-title" id="myModalLabel">添加监控点</h4>
	            </div>
		        <form action="#" class="form-horizontal" id="projectForm">
		            <div class="modal-body">
		            	<input type="hidden" id="action" name="action"/>
		            	<input type="hidden" id="id" name="id"/>
		            	<input type="hidden" id="alarmEventId" name="alarmEventId"/>
		            	<div class="control-group">
							<label class="control-label">项目名称</label>
							<div class="controls">
								<input name="projectName" id="projectName" type="text" placeholder="填写项目名称" class="m-wrap medium" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">执行点</label>
							<div class="controls">
								<input name="excutePoint" id="excutePoint" type="text" placeholder="填写执行点方法名" class="m-wrap medium" />
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
		                <button type="button" class="btn btn-primary" id="addProject">提交</button>
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

							待添加监控点列表

						</h3>

						<ul class="breadcrumb">

							<li>

								<i class="icon-home"></i>

								<a href="index.html">日志收集</a> 

								<i class="icon-angle-right"></i>

							</li>

							<li>

								<a href="#">监控中心</a>

								<i class="icon-angle-right"></i>

							</li>

							<li><a href="#">待添加监控点列表</a></li>

						</ul>

						<!-- END PAGE TITLE & BREADCRUMB-->

					</div>

				</div>

				<!-- END PAGE HEADER-->

				<!-- BEGIN PAGE CONTENT-->

				<div class="row-fluid">

					<div class="span12">

						<!-- BEGIN EXAMPLE TABLE PORTLET-->

						<div class="portlet box red">

							<div class="portlet-title">

								<div class="caption"><i class="icon-edit"></i>监控点列表</div>
							</div>

							<div class="portlet-body">

								<div class="clearfix">
								<div class="btn-group">
										<!-- 按钮触发模态框 -->
										<button class="btn btn-primary btn-lg" onClick="addAllProject()">全部添加</button>
									</div>
								<table class="table table-striped table-bordered table-hover table-full-width" id="sample_2">
									<thead>
										<tr>
											<th>项目名</th>
											<th>执行点</th>
											<th>最近10分钟异常率</th>
											<th>最近1小时异常率</th>
											<th>最近3小时异常率</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
									  <#list projectList as model>
											<tr class="" >
												<td >${model.projectName}</td>
												<td >${model.excutePoint}</td>
												<td <#if model.currHourErrorPercent gt 0 >style="background-color:red"<#else></#if>>${model.currHourErrorPercent}</td>
												<td <#if model.oneHourAgoErrorPercent gt 0 >style="background-color:red"<#else></#if>>${model.oneHourAgoErrorPercent}</td>
												<td <#if model.twoHourAgoErrorPercent gt 0 >style="background-color:red"<#else></#if>>${model.twoHourAgoErrorPercent}</td>
												<td><a  class="edit" href="javascript:;" onClick="addProject('${model.projectName}', '${model.excutePoint}')">Add</a></td>
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
	<script src="${rc.contextPath}/media/js/table-editable4.js"></script>   
<script>
	function addProject(projectName, excutePoint) {  
	    $("#projectForm #action").val('/log/add_project');
	    $("#projectForm #projectName").val(projectName);
		$("#projectForm #excutePoint").val(excutePoint);
	    $('#projectModal').modal('show');
	}
	function addAllProject() {  
	    $("#projectForm #action").val('/log/add_all_project');
	    $("#projectForm #projectName").val('All');
		$("#projectForm #excutePoint").val('All');
	    $('#projectModal').modal('show');
	}
	$('#addProject').click(function(){
		$.ajax({
			url : $("#projectForm #action").val(),
			data : {
				projectName : $("#projectForm #projectName").val(),
				excutePoint : $("#projectForm #excutePoint").val(),
				rate : $("#projectForm #rate").val(),
				threshold : $("#projectForm #threshold").val(),
				alarmPolicyId : $("#projectForm #alarmPolicyId").val()
			},
			success : function(data){
				alert("操作成功");
				window.location.reload();
			}
		});
	});
</script>
</html>