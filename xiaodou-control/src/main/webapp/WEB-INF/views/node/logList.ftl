<!DOCTYPE html>
<#include "/common/style.ftl">
<head>
<link href="${rc.contextPath}/resources/css/fileCss/css/fileinput.css" media="all" rel="stylesheet" type="text/css" />	
<link href="${rc.contextPath}/resources/css/validator/bootstrapValidator.css" rel="stylesheet"  type="text/css"/>
</head>
<html>
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

							${serverName}日志列表

						</h3>

						<ul class="breadcrumb">

							<li>

								<i class="icon-home"></i>

								${serverName}日志列表


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

								<div class="caption"><i class="icon-edit"></i>${serverName}日志列表</div>

							</div>

							<div class="portlet-body">

								<div class="clearfix">

								<table class="table table-striped table-hover table-bordered">
                                <thead>
                                    <tr>
                                    	<th  style="display:none">id</th>
                                        <th>服务名</th>  
                                        <th>服务器</th>                         
                                        <th>命令名称</th>   
                                        <th>命令状态</th> 
                                        <th>版本号</th> 
                                        <th>创建时间</th>  
                                        <th>创建人</th> 
                                        <th>日志</th>                      
                                    </tr>
                                </thead>
                                <tbody>
                                <#list logList as log>
                                    <tr>
                                    	<td  style="display:none"></td>
                                        <td>${serverName}</td>
                                        <td>${log.alias}</td>
           								<td>${log.commandName}</td>
               						    <td>${log.state}</td>
               						    <td>${log.version}</td>
               						    <td>${log.createTime}</td>     
               						    <td>${log.userName}</td>      		
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
                                <textarea id="msg"  class="comments" rows=25   name=s1   cols=50   onpropertychange= "this.style.posHeight=this.scrollHeight"></textarea>
					          </div>					          
					        </div>
					        <div class="modal-footer">
					          <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					        </div>
					      </div>
					    </div>
					  </div>
            </div>
            <!-- /.container-fluid -->

        </div>
       </div>
        <!-- /#page-wrapper -->
    <input type="hidden" id="serverId" value="${serverId}">
    <input type="hidden" id="serverName" value="${serverName}">
</body>
<script>
 	function getMsg(r){
 		$('#msg').val(r.parentNode.parentNode.children[9].innerHTML)
 		$('#myModal').modal();
 	}
 	
  
</script>

<html>