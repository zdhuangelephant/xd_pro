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
									<div class="row-fluid">
							        	<select id="server" class="medium m-wrap" >
											<option value="">全部</option>
											<#list serverList as server>
												<option value="${server}" <#if serverName == server>selected</#if>>${server}</option>
											</#list>
										</select>&nbsp;
										<select id="hasError" class="medium m-wrap" >
											<option value="">全部</option>
											<option value="true" <#if hasError>selected</#if>>异常</option>
										</select>&nbsp;
										<input type="text" id="lowerTime" placeholder="起始时间" value="${lowerTime}">&nbsp;
										<input type="text" id="upperTime" placeholder="截止时间" value="${upperTime}">&nbsp;
							        	<a href="#" class="btn blue" onclick="toPage('1')">Search</a>
									</div>
									<table class="table table-bordered table-hover table-full-width" >
										<thead>
											<tr>
												<th>ID</th>
												<th>日志时间</th>
												<th>服务器IP</th>
												<th>项目名</th>
												<th>执行点</th>
												<th>服务名</th>
												<th>客户端IP</th>
												<th>是否异常</th>
												<th>异常信息</th>
												<th>日志信息</th>
											</tr>
										</thead>
										<tbody>
										  <#list actionList as model>
												<tr class="" >
													<td >${model.actionId}</td>
													<td >${model.sLogTime}</td>
													<td >${model.localIp}</td>
													<td >${model.projectName}</td>
													<td >${model.excutePoint}</td>
													<td >${model.serverName}</td>
													<td >${model.clientIp}</td>
													<td >${model.hasError}</td>
													<td >${model.errorMessage}</td>
													<td >${model.actionModel}</td>
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
	$( function() {
    $( "#lowerTime" ).datepicker();
    $( "#upperTime" ).datepicker();
  } );
	function toPage(page){
		var server = $("#server").val();
		var hasError = $("#hasError").val();
		var lowerTime = $("#lowerTime").val();
		var upperTime = $("#upperTime").val();
		var url = window.location.pathname+"?projectName=${projectName}&excutePoint=${excutePoint}"
		+ "&serverName="+server
		+ "&hasError="+hasError
		+ "&lowerTime="+lowerTime
		+ "&upperTime="+upperTime
		+ "&page="+page;
		self.location=url;
	}
</script>
</html>