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

							配置列表

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

							<li><a href="#">配置列表</a></li>

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

								<div class="caption"><i class="icon-edit"></i>配置列表</div>

							</div>

							<div class="portlet-body">

								<div class="clearfix">

									<div class="btn-group">

										<button id="sample_editable_3_new" class="btn green">

										Add New <i class="icon-plus"></i>

										</button>

									</div>
		

								<table class="table table-striped table-hover table-bordered" id="sample_editable_3">
									<thead>
										<tr>
											<th>消息名称</th>
											<th>交换机名称</th>
											<th>优先级</th>
											<th>是否延迟重试(0否1是)</th>
											<th>延迟执行时间(毫秒)</th>
											<th>最大重试次数</th>
											<th>修改</th>
											<th>删除</th>
										</tr>
									</thead>
									<tbody>
									  <#list list as list>
										<tr class="">
											<td id="${list.id}"><a style="cursor: pointer" href="/jmsg/consumersList?messageName=${list.messageName}">${list.messageName}</a></td>
											<td >${list.exchangeName}</td>
											<td>${list.priority}</td>
											<td>${list.useDelayRetry}</td>
											<td>${list.delayTime}</td>
											<td>${list.maxRetryCount}</td>
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

				</div>

				<!-- END PAGE CONTENT -->

			<!-- END PAGE CONTAINER-->
		</div>          
                  
		<!-- END PAGE -->
	</div>

	<!-- END CONTAINER -->

<!-- END BODY -->
</body> 
	<script src="${rc.contextPath}/media/js/table-editable3.js"></script>   
<script>
</script>
</html>