<#include "/common/common.ftl">
<!DOCTYPE html>
<!-- BEGIN BODY -->

<body class="page-header-fixed">

	<!-- BEGIN CONTAINER -->

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

									<table class="table table-striped table-hover table-bordered" id="sample_editable_3">
										<thead>
											<tr>
												<th>执行ID</th>
												<th>执行者节点名称</th>
												<th>数据版本号</th>
												<th>调度状态</th>
												<th>调度时间</th>
												<th>调度耗时</th>
												<th>调度结果</th>
												<th>重试次数</th>
											</tr>
										</thead>
										<tbody>
										  <#list list as model>
											<#list model.logList as log>
												<tr class="" >
													<#if log_index == 0>
														<td rowspan="${model.size}">${model.excutorId}</td>
													</#if>
														<td >${log.contextName}</td>
														<td >${log.dataVersion}</td>
														<td >${log.crontStatus}</td>
														<td >${log.crontTime}</td>
														<td >${log.crontCost}</td>
														<td >${log.crontResult}</td>
														<td >${log.crontRetry}</td>
												</tr>
											</#list>											
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
<script>
</script>
</html>