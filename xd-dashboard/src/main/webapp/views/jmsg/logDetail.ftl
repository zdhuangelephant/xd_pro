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

							日志信息

						</h3>

						<ul class="breadcrumb">

							<li>

								<i class="icon-home"></i>

								<a href="index.html">JMSG</a> 

								<i class="icon-angle-right"></i>

							</li>

							<li>
								<a href="#">日志信息</a>
							</li>

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

								<div class="caption"><i class="icon-edit"></i>日志详情</div>

							</div>

							<table class="table table-striped  table-full-width" id="sample_2">
									<tbody>	
										<th>customTag</th>
											<th  width="60%">${messageBody.customTag}</th>     						
										</tr>
									     <th>messageName</th>
											<th  width="60%">${messageBody.messageName}</th>     						
										</tr>
									</tbody>
								</table>

						<!-- END EXAMPLE TABLE PORTLET-->

						</div>
								<#list messageLogList as messageLog >
								<#if messageLog.processResult == -1 >
									<div class="portlet box red">
								<#elseif messageLog.processResult == 0 >
									<div class="portlet box blue">
								</#if>
								<div class="portlet-title">
								
								<div class="caption"><i class="icon-edit"></i>消息第${messageLog_index+1}次处理结果</div>

								</div>
								<table class="table table-striped  table-full-width" id="sample_2">
									<tbody>	
										<th>processTimeSpan</th>
											<th  width="60%">${messageLog.processTimeSpan}</th>     						
										</tr>
										<th>beginProcessTime</th>
											<th  width="60%">${messageLog.beginProcessTime}</th>     						
										</tr>
										<th>endProcessTime</th>
											<th  width="60%">${messageLog.endProcessTime}</th>     						
										</tr>						 
										<th>processLog</th>
											<th   width="60%">${messageLog.processLog}</th>						
										</tr>	
										<th>messageData</th>
											<th  width="60%">${messageBody.messageData}</th>     						
										</tr>	
									</tbody>
								</table>
								</div>
								</#list>

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