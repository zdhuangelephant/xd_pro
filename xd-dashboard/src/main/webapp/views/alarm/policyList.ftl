<#include "/common/common.ftl">
<!DOCTYPE html>
<!-- BEGIN BODY -->

<body class="page-header-fixed">

	<!-- BEGIN CONTAINER -->

	<!-- 模态框（Modal） -->
	<div class="modal fade" id="policyModal" tabindex="-1" role="dialog" aria-labelledby="policyModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h4 class="modal-title" id="myModalLabel">添加监控点</h4>
	            </div>
		        <form action="#" class="form-horizontal" id="policyForm">
		            <div class="modal-body">
		            	<input type="hidden" id="action" name="action"/>
		            	<input type="hidden" id="alarmPolicyId" name="alarmPolicyId"/>
		            	<div class="control-group">
							<label class="control-label">策略名</label>
							<div class="controls">
								<input name="alarmPolicyName" id="alarmPolicyName" type="text" placeholder="填写项目名称" class="m-wrap medium" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">开始时间</label>
							<div class="controls">
								<select name="starttime" id="starttime" class="small m-wrap" value="7">
									<option value="1">AM 1:00</option>
									<option value="2">AM 2:00</option>
									<option value="3">AM 3:00</option>
								    <option value="4">AM 4:00</option>
								    <option value="5">AM 5:00</option>
									<option value="6">AM 6:00</option>
									<option value="7">AM 7:00</option>
									<option value="8">AM 8:00</option>
									<option value="9">AM 9:00</option>
									<option value="10">AM 10:00</option>
									<option value="11">AM 11:00</option>
									<option value="12">AM 12:00</option>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">结束时间</label>
							<div class="controls">
								<select name="endtime" id="endtime" class="small m-wrap" value="23">
									<option value="24">PM 12:00</option>
									<option value="23">PM 11:00</option>
									<option value="22">PM 10:00</option>
									<option value="21">PM 9:00</option>
									<option value="20">PM 8:00</option>
									<option value="19">PM 7:00</option>
									<option value="18">PM 6:00</option>
									<option value="17">PM 5:00</option>
									<option value="16">PM 4:00</option>
									<option value="15">PM 3:00</option>
									<option value="14">PM 2:00</option>
									<option value="13">PM 1:00</option>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">短信接收者</label>
							<div class="controls">
								<input name="message" id="message" type="text" placeholder="号码间用;分隔" class="m-wrap medium" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">邮件接收者</label>
							<div class="controls">
								<input name="mail" id="mail" type="text" placeholder="邮箱间用;分隔" class="m-wrap medium" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">钉钉机器人</label>
							<div class="controls">
								<input name="dingURL" id="dingURL" type="text" placeholder="机器人用;分隔" class="m-wrap medium" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">所属分组</label>
							<div class="controls">
								<input name="group" id="group" type="text" value="server" class="m-wrap medium" />
							</div>
						</div>
		            </div>
		            <div class="modal-footer">
		                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		                <button type="button" class="btn btn-primary" id="addPolicy">提交</button>
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

								<a href="#">策略中心</a>

								<i class="icon-angle-right"></i>

							</li>

							<li><a href="#">报警策略列表</a></li>

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

								<div class="caption"><i class="icon-edit"></i>报警策略列表</div>
							</div>

							<div class="portlet-body">

								<div class="clearfix">
								
								<div class="btn-group">
									<!-- 按钮触发模态框 -->
									<button class="btn btn-primary btn-lg" onClick="addPolicy()">添加报警策略</button>
								</div>

								<table class="table table-bordered table-hover table-full-width" id="sample_2">
									<thead>
										<tr>
											<th>ID</th>
											<th>策略名</th>
											<th>开始时间</th>
											<th>结束时间</th>
											<th>短信接收人</th>
											<th>邮件接收人</th>
											<th>钉钉机器人</th>
											<th>所属分组</th>
											<th>最后更新时间</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
									  <#list policyList as model>
											<tr class="" >
												<td >${model.alarmPolicyId}</td>
												<td >${model.alarmPolicyName}</td>
												<td >${model.starttime}</td>
												<td >${model.endtime}</td>
												<td >${model.message}</td>
												<td >${model.mail}</td>
												<td >${model.dingURL}</td>
												<td >${model.group}</td>
												<td >${model.updateTime?date}</td>
												<td><a  class="edit" href="javascript:;" onClick="editPolicy('${model.alarmPolicyId}')">Edit</a>&nbsp; 
												| &nbsp; <a  class="delete" href="javascript:;" onClick="deletePolicy('${model.alarmPolicyId}')">Delete</a>
												</td>
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
	function addPolicy() {  
	    $("#policyForm #action").val('/dashboard/alarmpolicy/add');
	    $('#policyModal').modal('show');
	}
	function editPolicy(id) {
		$("#policyForm #action").val('/dashboard/alarmpolicy/update');
		$.ajax({
			url : '/dashboard/alarmpolicy/get',
			data : {
				policyId : id
			},
			success : function(data) {
				data=eval('(' + data + ')');
				var policy = data.policy;
				$("#policyForm #alarmPolicyId").val(policy.alarmPolicyId);
				$("#policyForm #alarmPolicyName").val(policy.alarmPolicyName),
				$("#policyForm #starttime").val(policy.starttime),
				$("#policyForm #endtime").val(policy.endtime),
				$("#policyForm #message").val(policy.message),
				$("#policyForm #mail").val(policy.mail),
				$("#policyForm #dingURL").val(policy.dingURL),
				$("#policyForm #group").val(policy.group),
			    $('#policyModal').modal('show');
			}
		});
	}
	function deletePolicy(id) {
		$.ajax({
			url : '/dashboard/alarmpolicy/delete',
			data : {
				policyId : id
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
	$('#addPolicy').click(function(){
		$.ajax({
			url : $("#policyForm #action").val(),
			type : "POST",
			data : {
				alarmPolicyId : $("#policyForm #alarmPolicyId").val(),
				alarmPolicyName : $("#policyForm #alarmPolicyName").val(),
				starttime : $("#policyForm #starttime").val(),
				endtime : $("#policyForm #endtime").val(),
				message : $("#policyForm #message").val(),
				mail : $("#policyForm #mail").val(),
				dingURL : $("#policyForm #dingURL").val(),
				group : $("#policyForm #group").val()
			},
			success : function(data){
				alert("操作成功");
				window.location.reload();
			}
		});
	});
</script>
</html>