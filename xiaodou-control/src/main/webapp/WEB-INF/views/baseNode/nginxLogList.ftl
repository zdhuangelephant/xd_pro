<#include "/common/style.ftl">
<!DOCTYPE html>
<!-- BEGIN BODY -->
<body class="page-header-fixed">

	<!-- BEGIN CONTAINER -->
	
		<div class="page-content">

			<!-- BEGIN PAGE CONTAINER-->  
			<div class="container-fluid">
				<!-- BEGIN PAGE HEADER-->

				<div class="row-fluid">

					<div class="span12">


						<!-- BEGIN PAGE TITLE & BREADCRUMB-->

						<h3 class="page-title">

							 主机管理

						</h3>

						<ul class="breadcrumb">

							<li>

								<i class="icon-home"></i>

								主机列表

								<i class="icon-angle-right"></i>

							</li>

							<li>
								<a href="#">NGINX日志信息</a>
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

								<div class="clearfix">


								<table class="table table-striped table-hover table-bordered" id="logTable">
									<thead>
                                    <tr>
                                    	<th  style="display:none">id</th>
                                        <th>nginx部署状态</th>  
                                        <th>nginx自动部署时间</th>                         
                                        <th>日志</th>                      
                                    </tr>
                                </thead>
                                <tbody>
                                <#list nginxLogList as log>
                                    <tr>
                                    	<td  style="display:none"></td>
               						     <#if log.state==1>                     
	                                        <td style="color:green">正常</td>
	                                        <#elseif log.state==2>
	                                        <td style="color:red">异常</td>
	                                        <#elseif log.state==0>
	                                        <td style="color:purple">异常</td>
	                                        <#else>
	                                        <td style="color:blue">未获取状态</td>
                                        </#if>
               						    <td>${log.createTime}</td>          		
               						    <td><a style="cursor: pointer" onclick="getMsg(this)">查看日志</a> </td>         
               						    <td style="display:none">${log.msg} </td> 
                                    </tr>                              
                                    </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <!-- /.row -->
					  <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					    <div class="modal-dialog" role="document">
					      <div class="modal-content">
					        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					          <h4 class="modal-title" id="myModalLabel">日志</h4>
					        </div>
					        <div class="modal-body">				     
					          <div class="form-group">					           
                                <textarea id="msg"  class="comments" rows=25   name=s1   cols=27   onpropertychange= "this.style.posHeight=this.scrollHeight"></textarea>
					          </div>					          
					        </div>
					        <div class="modal-footer">
					          <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					        </div>
					      </div>
					    </div>
					  </div>
			 	<input type="hidden" id="serverId" value="${serverId}">
			    <input type="hidden" id="serverName" value="${serverName}">
			</div>
		</div>
			<!-- END PAGE CONTAINER-->
	 </div>          
                  
	<!-- END CONTAINER -->

<!-- END BODY -->
</body> 
<script>
 	function getMsg(r){
 		$('#msg').val(r.parentNode.parentNode.children[4].innerHTML)
 		$('#myModal').modal();
 	}
 	
  
</script>

<html>