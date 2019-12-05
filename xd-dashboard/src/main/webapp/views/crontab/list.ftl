<#include "/common/common.ftl">
<!DOCTYPE html>
<!-- BEGIN BODY -->

<body class="page-header-fixed">

	<!-- BEGIN CONTAINER -->

	<!-- 模态框（Modal） -->
	<div class="modal fade" id="crontModal" tabindex="-1" role="dialog" aria-labelledby="crontModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h4 class="modal-title" id="myModalLabel">添加任务</h4>
	            </div>
		        <form action="#" class="form-horizontal" id="crontForm">
		            <div class="modal-body">
		            	<input type="hidden" id="action" name="action"/>
		            	<input type="hidden" id="id" name="id"/>
		            	<div class="control-group">
							<label class="control-label">业务类型</label>
							<div class="controls">
								<select class="large m-wrap" tabindex="1" name="businessCode" id="businessCode">
									<#list businessTypeList as businessType>
										<option value="${businessType.code}">${businessType.desc}</option>
									</#list>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">调度策略</label>
							<div class="controls">
								<input name="crontExpression" id="crontExpression" type="text" placeholder="* * * * * ?" class="m-wrap medium" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">调度协议</label>
							<div class="controls">
								<select class="large m-wrap" tabindex="1" name="crontProtocol" id="crontProtocol">
									<option value="HTTP">HTTP</option>
									<option value="HTTPS">HTTPS</option>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">参数格式</label>
							<div class="controls">
								<select class="large m-wrap" tabindex="1" name="dataProtocol" id="dataProtocol">
									<option value="normal">normal</option>
									<option value="json">json</option>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">调度地址</label>
							<div class="controls">
								<input type="text" placeholder="url" class="m-wrap medium" name="crontTarget" id="crontTarget" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">整体超时(毫秒)</label>
							<div class="controls">
								<input type="text" placeholder="20000" value="20000" class="m-wrap medium" name="crontTimeOut" id="crontTimeOut" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">失败重试次数</label>
							<div class="controls">
								<input type="text" placeholder="3" value="3" class="m-wrap medium" name="crontRetryTimes" id="crontRetryTimes" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">协议超时(毫秒)</label>
							<div class="controls">
								<input type="text" placeholder="5000" value="5000" class="m-wrap medium" name="protocolTimeOut" id="protocolTimeOut" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">协议重试次数</label>
							<div class="controls">
								<input type="text" placeholder="3" value="3" class="m-wrap medium" name="protocolRetryTimes" id="protocolRetryTimes" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">启用结果格式校验</label>
							<div class="controls">
								<select class="large m-wrap" tabindex="1" name="protocolStructCheck" id="protocolStructCheck" >
									<option value="1">是</option>
									<option value="0">否</option>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">是否启用</label>
							<div class="controls">
								<select class="large m-wrap" tabindex="1" name="inUse" id="inUse" >
									<option value="1">是</option>
									<option value="0">否</option>
								</select>
							</div>
						</div>
		            </div>
		            <div class="modal-footer">
		                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		                <button type="button" class="btn btn-primary" id="addCront">提交更改</button>
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

								<a href="index.html">定时任务</a> 

								<i class="icon-angle-right"></i>

							</li>

							<li>

								<a href="#">任务中心</a>

								<i class="icon-angle-right"></i>

							</li>

							<li><a href="#">任务列表</a></li>

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
										<button class="btn btn-primary btn-lg" onClick="addCront()">添加定时任务</button>

									</div>
		

								<table class="table table-striped table-hover table-bordered" id="sample_editable_3">
									<thead>
										<tr>
											<th>ID</th>
											<th>业务模块</th>
											<th>调度策略</th>
											<th>调度协议</th>
											<th>调度地址</th>
											<th>整体超时</th>
											<th>失败重试次数</th>
											<th>启用结果格式校验</th>
											<th>版本号</th>
											<th>启用状态</th>
											<th>创建时间</th>
											<th>更新时间</th>
											<th>所属用户组</th>
											<th>创建者</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
									  <#list list as model>
											<tr class="" >
												<td ><a style="cursor: pointer" href="/crontab/schedule_list?configId=${model.id}">${model.id}</a></td>
												<td >${model.businessType}</td>
												<td >${model.crontExpression}</td>
												<td >${model.crontProtocol}</td>
												<td >${model.crontTarget}</td>
												<td >${model.crontTimeOut}</td>
												<td >${model.crontRetryTime}</td>
												<td ><#if model.protocolStructCheck == 1>启用中<#else><font style="color:red">未启用</font></#if></td>
												<td >${model.version}</td>
												<td ><#if model.inUse == 1>启用<#else><font style="color:red">停用</font></#if></td>
												<td >${model.createTime}</td>
												<td >${model.updateTime}</td>
												<td >${model.userGroup}</td>
												<td >${model.owner}</td>
												<td><a  class="edit" href="javascript:;" onClick="editCront('${model.id}')">Edit</a></td>
												<td><a  class="delete" href="javascript:;" onClick="deleteCront('${model.id}')">Delete</a></td>
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
	function addCront() {  
	    $("#crontForm #action").val('/crontab/doAdd');
	    $('#crontModal').modal('show');
	}
	function editCront(id) {
		$("#crontForm #action").val('/crontab/doEdit');
		$.ajax({
			url : '/crontab/edit',
			data : {
				id : id
			},
			success : function(data) {
				data=eval('(' + data + ')');
				$("#crontForm #id").val(data.configId);
				$("#crontForm #businessCode").val(data.businessCode);
				$("#crontForm #crontExpression").val(data.crontExpression);
				$("#crontForm #crontProtocol").val(data.crontProtocol);
				$("#crontForm #dataProtocol").val(data.protocolConfigModel.dataProtocol);
				$("#crontForm #crontTarget").val(data.crontTarget);
				$("#crontForm #crontTimeOut").val(data.crontTimeOut);
				$("#crontForm #crontRetryTimes").val(data.crontRetryTime);
				$("#crontForm #protocolTimeOut").val(data.protocolTimeOut);
				$("#crontForm #protocolRetryTimes").val(data.protocolRetryTimes);
				$("#crontForm #protocolStructCheck").val(data.protocolStructCheck);
				$("#crontForm #inUse").val(data.inUse);
			    $('#crontModal').modal('show');
			}
		});
	}
	function deleteCront(id) {
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
	$('#addCront').click(function(){
		$.ajax({
			url : $("#crontForm #action").val(),
			data : {
				id : $("#crontForm #id").val(),
				businessCode : $("#crontForm #businessCode").val(),
				crontExpression : $("#crontForm #crontExpression").val(),
				crontProtocol : $("#crontForm #crontProtocol").val(),
				dataProtocol : $("#crontForm #dataProtocol").val(),
				crontTarget : $("#crontForm #crontTarget").val(),
				crontTimeOut : $("#crontForm #crontTimeOut").val(),
				crontRetryTimes : $("#crontForm #crontRetryTimes").val(),
				protocolTimeOut : $("#crontForm #protocolTimeOut").val(),
				protocolRetryTimes : $("#crontForm #protocolRetryTimes").val(),
				protocolStructCheck : $("#crontForm #protocolStructCheck").val(),
				inUse : $("#crontForm #inUse").val()
			},
			success : function(data){
				alert("操作成功");
				window.location.reload();
			}
		});
	});
</script>
</html>