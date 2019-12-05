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

							节点配置列表

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

							<li><a href="#">配置列表(${messageName})</a></li>

						</ul>

						<!-- END PAGE TITLE & BREADCRUMB-->

					</div>

				</div>

				<!-- END PAGE HEADER-->

				<!-- BEGIN PAGE CONTENT-->
 <input type="hidden" class="form-control" name="messageName" id="messageName" value="${messageName}">
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

										<button id="sample_editable_4_new" class="btn green">

										Add New <i class="icon-plus"></i>

										</button>

									</div>
		

								<table class="table table-striped table-hover table-bordered" id="sample_editable_4">
									<thead>
										<tr>
											<th>消费地址</th>
											<th>超时时间</th>
											<th>修改</th>
											<th>删除</th>
										</tr>
									</thead>
									<tbody>
									  <#list list as list>
										<tr class="">
											<td id="${list.id}">${list.url}</td>
											<td>${list.timeOut}</td>
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
	<script src="${rc.contextPath}/media/js/table-editable4.js"></script>   
<script>
</script>
</html>