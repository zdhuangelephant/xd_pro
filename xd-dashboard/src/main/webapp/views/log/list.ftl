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

							监控点列表

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
								<div class="caption"><i class="icon-edit"></i>监控点列表</div>
							</div>
							<div class="portlet-body">
								<div class="clearfix">
									<div class="btn-group">
										<!-- 按钮触发模态框 -->
										<button class="btn btn-primary btn-lg" onClick="addProject()">添加监控点</button>
									</div>
									<div style="float:right;margin-right:0px">
										<!-- 按钮触发模态框 -->
										<a class="btn red" style="float:right" href="/log/wait_project_excution_list">待添加监控点列表</a>
									</div>
									<div class="row-fluid">
									    <div class="span6">
									        <div class="dataTables_filter" id="sample_2_filter">
									        	<input type="text" id="s_projectName" class="m-wrap small" placeholder="项目名" value="${s_projectName}">&nbsp;
									        	<input type="text" id="s_excutePoint" class="m-wrap small" placeholder="执行点" value="${s_excutePoint}">&nbsp;
									        	<a href="#" class="btn blue" onclick="toPage('1')">Search</a>
									        </div>
									    </div>
									</div>
									<table class="table table-bordered table-hover table-full-width" >
										<thead>
											<tr>
												<th>ID</th>
												<th>项目名</th>
												<th>执行点</th>
												<th>监控状态</th>
												<th>当前小时请求次数</th>
												<th>当前小时异常率</th>
												<th>当前小时超时率</th>
												<th>1小时前请求次数</th>
												<th>1小时前异常率</th>
												<th>1小时前超时率</th>
												<th>2小时前请求次数</th>
												<th>2小时前异常率</th>
												<th>2小时前超时率</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody>
										  <#list projectList as model>
												<tr class="" >
													<td ><a style="cursor: pointer" href="/log/project_excution_statistic?projectId=${model.projectId}">${model.projectId}</a></td>
													<td >${model.projectName}</td>
													<td >${model.excutePoint}</td>
													<td <#if model.alarmEventId??>>监控中<#else>style="background-color:red">未监控</#if></td>
													<td <#if model.currHourErrorPercent gt 0 >style="background-color:red"<#else></#if>>${model.currHourTotalCount}</td>
													<td <#if model.currHourErrorPercent gt 0 >style="background-color:red"<#else></#if>>${model.currHourErrorPercent}</td>
											        <td <#if model.currHourOverTimePercent gt 0 >style="background-color:red"<#else></#if>>${model.currHourOverTimePercent}</td>
													<td <#if model.oneHourAgoErrorPercent gt 0 >style="background-color:red"<#else></#if>>${model.oneHourAgoTotalCount}</td>
													<td <#if model.oneHourAgoErrorPercent gt 0 >style="background-color:red"<#else></#if>>${model.oneHourAgoErrorPercent}</td>
													<td <#if model.oneHourAgoOverTimePercent gt 0 >style="background-color:red"<#else></#if>>${model.oneHourAgoOverTimePercent}</td>
													<td <#if model.twoHourAgoErrorPercent gt 0 >style="background-color:red"<#else></#if>>${model.twoHourAgoTotalCount}</td>
													<td <#if model.twoHourAgoErrorPercent gt 0 >style="background-color:red"<#else></#if>>${model.twoHourAgoErrorPercent}</td>
													<td <#if model.twoHourAgoOverTimePercent gt 0 >style="background-color:red"<#else></#if>>${model.twoHourAgoOverTimePercent}</td>
													<td><a  class="edit" href="javascript:;" onClick="editProject('${model.projectId}')">Edit</a>&nbsp; 
													| &nbsp; <a  class="delete" href="javascript:;" onClick="deleteProject('${model.projectId}')">Delete</a>
													</td>
												</tr>
										  </#list>
										</tbody>
									</table>
									<@page totalCount="${totalCount}" pageNo="${pageNo}" totalPage="${totalPage}">
     								</@page>
								</div>
							</div>
						</div>

						<!-- END EXAMPLE TABLE PORTLET-->

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
	function addProject() {  
	    $("#projectForm #action").val('/log/add_project');
	    $('#projectModal').modal('show');
	}
	function editProject(id) {
		$("#projectForm #action").val('/log/edit_project');
		$.ajax({
			url : '/log/get_project',
			data : {
				projectId : id
			},
			success : function(data) {
				data=eval('(' + data + ')');
				var project = data.project;
				var event = data.event;
				if(null != project && typeof(project) != "undefined"){
					$("#projectForm #id").val(project.projectId);
					$("#projectForm #alarmEventId").val(project.alarmEventId);
					$("#projectForm #projectName").val(project.projectName);
					$("#projectForm #excutePoint").val(project.excutePoint);
				}
				if(null != event && typeof(event) != "undefined"){
					$("#projectForm #rate").val(event.rate);
					$("#projectForm #threshold").val(event.threshold);
					$("#projectForm #alarmPolicyId").val(event.alarmPolicyId);
				}
			    $('#projectModal').modal('show');
			}
		});
	}
	function deleteProject(id) {
		$.ajax({
			url : '/log/delete_project',
			data : {
				projectId : id
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
	$('#addProject').click(function(){
		$.ajax({
			url : $("#projectForm #action").val(),
			data : {
				projectId : $("#projectForm #id").val(),
				alarmEventId : $("#projectForm #alarmEventId").val(),
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
	
	function toPage(page){
		var projectName = $("#s_projectName").val();
		var excutePoint = $("#s_excutePoint").val();
		var url = window.location.pathname+"?projectName="+projectName
		+ "&excutePoint="+excutePoint
		+ "&page="+page;
		self.location=url;
	}
</script>
</html>