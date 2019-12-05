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

							服务节点列表

						</h3>

						<ul class="breadcrumb">

							<li>

								<i class="icon-home"></i>

								<a href="index.html">JMSG</a> 

								<i class="icon-angle-right"></i>

							</li>

							<li>

								<a href="#">配置中心</a>

								<i class="icon-angle-right"></i>

							</li>

							<li><a href="#">queue节点列表</a></li>

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

								<div class="caption"><i class="icon-edit"></i>queue节点列表</div>

								<div class="tools">

									<a href="javascript:;" class="collapse"></a>

									<a href="#portlet-config" data-toggle="modal" class="config"></a>

									<a href="javascript:;" class="reload"></a>

									<a href="javascript:;" class="remove"></a>

								</div>

							</div>

							<div class="portlet-body">

								<div class="clearfix">

									<div class="btn-group">

										<button id="sample_editable_2_new" class="btn green">

										Add New <i class="icon-plus"></i>

										</button>

									</div>
		

								<table class="table table-striped table-hover table-bordered" id="sample_editable_2">
									<thead>
										<tr>
											<th>queue名称</th>
											<th>接收消息的数目</th>
											<th>线程数目</th>
											<th>更新时间</th>
											<th>修改</th>
											<th>删除</th>
										</tr>
									</thead>
									<tbody>
									  <#list queueList as list>
										<tr class="">
											<td id="${list.id}">${list.queueName}</td>
											<td>${list.qos}</td>
											<td>${list.parallelCount}</td>
											<td class="center">${list.updateTime}</td>
											<td><a  class="edit" href="javascript:;">Edit</a></td>
											<td><a  class="delete" href="javascript:;">Delete</a></td>
										</tr>
									  </#list>
									</tbody>

								</table>

							</div>

						</div>

						<!-- END EXAMPLE TABLE PORTLET-->

					</div>

				</div>

				<!-- END PAGE CONTENT -->

			</div>

			<!-- END PAGE CONTAINER-->

		</div>

		<!-- END PAGE -->
	</div>

	<!-- END CONTAINER -->

<!-- END BODY -->
</body> 

 
	<script src="${rc.contextPath}/media/js/table-editable2.js"></script>
</html>