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

								<a href="index.html">定时监控任务</a> 

								<i class="icon-angle-right"></i>

							</li>

							<li>

								<a href="#">监控中心</a>

								<i class="icon-angle-right"></i>

							</li>

							<li><a href="#">监控日志列表</a></li>

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

								<div class="caption"><i class="icon-edit"></i>日志列表</div>

							</div>

							<div class="portlet-body">

								<div class="clearfix">

									<table class="table table-striped table-hover table-bordered" id="sample_editable_3">
										<thead>
											<tr>
												<th>执行ID</th>
												<th>任务名</th>
												<th>执行结果</th>
												<th>异常信息</th>
												<th>执行时间</th>
											</tr>
										</thead>
										<tbody>
										  <#list list as log>
											<tr class="" >
													<td >${log.id}</td>
													<td >${log.apiName}</td>
													<td >${log.result}</td>
													<td >${log.message}</td>
													<td >${log.createTime}</td>
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
<script>
</script>
</html>