<#include "/common/common.ftl">
<!DOCTYPE html>
<!-- BEGIN BODY -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
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

								<div class="caption"><i class="icon-edit"></i>日志列表</div>

							</div>


							<div class="portlet-body">
								<div class="row-fluid">
									<input type="text" id="datepicker" placeholder="查询日期" value="${datepicker}">&nbsp;
									<input type="text" id="messageName" placeholder="MessageName" value="${messageName}">&nbsp;
									<input type="text" id="customTag" placeholder="CustomTag" value="${customTag}">&nbsp;
									<input type="text" id="messageData" placeholder="messageData" value="${messageData}">&nbsp;
									<select id="result" value="${result}">
									  <option value ="">全部</option>
									  <option <#if result == 0>selected</#if> value ="0">Success</option>
									  <option <#if result == -1>selected</#if> value="-1">Error</option>
									  <option <#if result == 1>selected</#if> value="1">Fail</option>
									</select>&nbsp;
						        	<a href="#" class="btn blue" onclick="toPage('1')">Search</a>
								</div>
								<table class="table table-striped table-bordered table-hover table-full-width">
									<thead>
										<tr>
											<th>消息ID</th>
											<th>唯一Tag</th>
											<th class="hidden-480">消息名</th>
											<th class="hidden-480">消息长度</th>
											<th class="hidden-480">消费结果</th>
     										<th class="hidden-480">开始处理时间</th>
     						
										</tr>
									</thead>
									<tbody>
									  <#list list as list>
										<tr>
											<td><a style="cursor: pointer" href="/jmsg/logDetail?messageId=${list.messageId}&surfix=${surfix}">${list.messageId}</a></td>
											<td>${list.customTag}</td>
											<td class="hidden-480">${list.messageName}</td>
											<td class="hidden-480">${list.messageSize}</td>
											<td class="hidden-480"><#if list.result == 0>success
											<#elseif list.result ==1 ><a href="#" onclick="reSendMessage('${list.messageId}')">fail(重发)</a>
											<#elseif list.result ==-1 ><a href="#" onclick="reSendMessage('${list.messageId}')">error(重发)</a></#if>
											</td>
											<td class="hidden-480">${list.beginProcessTime}</td>
										</tr>
										 </#list>										
									</tbody>
								</table>
								<@page totalCount="${totalCount}" pageNo="${pageNo}" totalPage="${totalPage}">
     								</@page>

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
  $( function() {
    $( "#datepicker" ).datepicker();
  } );
	function toPage(page){
		var datepicker = $("#datepicker").val();
		var messageName = $("#messageName").val();
		var messageData = $("#messageData").val();
		var customTag = $("#customTag").val();
		var result = $("#result").val();
		var url = "/jmsg/logList?datepicker="+datepicker
		+ "&messageName="+messageName
		+ "&messageData="+messageData
		+ "&customTag="+customTag
		+ "&result="+result
		+ "&pageNo="+page;
		self.location=url;
	}
	
	function reSendMessage(messageId){
	var surfix = $("#datepicker").val();
	 $.post("/jmsg/reSendMessage", {messageId:messageId,surfix:surfix},
                    function(data){
                    	 var u=JSON.parse(data);
	                    	if(u.msg=="true"){
		                        alert("重发成功");
	                        }else{
	                            alert(u.msg);    
	                        }           
                    });	  
	}
  </script>
<script>
</script>
</html>