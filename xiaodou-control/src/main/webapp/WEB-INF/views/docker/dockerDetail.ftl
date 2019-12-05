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

							${alias}Docker列表

						</h3>

						<ul class="breadcrumb">

							<li>

								<i class="icon-home"></i>

									Docker管理列表


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

								<div class="caption"><i class="icon-edit"></i>Docker管理列表</div>

							</div>

							<div class="portlet-body">

								<div class="clearfix">

								<table id="iTable" class="table table-striped table-hover table-bordered">          
                                <thead>
                                    <tr>
                                    	<th  style="display:none">id</th>
                                        <th>Id</th>  
                                        <th>mac</th>                  
                                        <th>应用名称</th>  
                                        <th>状态</th> 
                                        <th>创建时间</th> 
                                        <th>上次连接时间</th>                                     
                						<th>指令</th> 
                                    </tr>
                                </thead>
                                <tbody>
                                <#list pList as project>
                                    <tr>
                                    	<td  style="display:none"></td>
                                    	<td>${project.projectId}</td>
                                    	<td>${project.mac}</td>
                                        <td>${project.projectName}</td>
               						    <#if project.state==1>                     
	                                        <td style="color:green">服务运行正常</td>
	                                        <#elseif project.state==2>
	                                        <td style="color:red">服务异常停止</td>	            
                                        </#if>
               						    <td>${project.createTime}</td>
               						    <td>${project.updateTime}</td>			   
               						    <td><a style="cursor: pointer" onclick="delProject('${project.projectId}')">废弃应用删除</a> 
               						    </td>          	              						    
                                    </tr>                              
                                </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <!-- /.row -->
                <!-- /.row -->	
   				 <div class="col-lg-12">  
                        <ol style="height:40px" class="breadcrumb">
                            <li style="cursor:pointer" class="active">
                                <i class="fa fa-edit"></i> 容器列表</li>
                               	  <div style="float:right">
                                 <!--	<button type="button" onclick="addMonitor()" class="btn btn-sm btn-warning"> 添加收集器</button> -->         
                            </div>
                        </ol>
                    </div>
                <div class="row">
                     <div class="col-lg-12">
                        <div class="table-responsive">
                            <table id="cTable" class="table table-hover">
                                <thead>
                                    <tr>
                                    	<th  style="display:none">id</th>
                                        <th>Id</th>  
                                        <th>(CONTAINERID  IMAGE  COMMAND  CREATED  STATUS  PORTS  NAMES)</th> 
                                        <th>操作</th>  
                                        <th>操作日志</th>         
                                    </tr>
                                </thead>
                                <tbody>
                                <#list cList as c>
                                    <tr>
                                    	<td  style="display:none"></td>
                                    	<td>${c.id}</td>
                                        <td>${c.details}</td>
                                        <td>
											<a style="cursor: pointer" onclick="dockerCommand('${mac}','${c.id}','11','镜像启动','')" >启动</a>
											<a style="cursor: pointer" onclick="dockerCommand('${mac}','${c.id}','12','镜像停止','')">停止</a>
											<a style="cursor: pointer" onclick="dockerCommand('${mac}','${c.id}','13','镜像移除','')">移除</a>
   						   			    </td>
   						    			<td><a style="cursor: pointer" href="/log/getLog?mac=${mac}&serverId=${c.id}&serverName=Docker操作日志">操作日志</a>
   						    			</td>
                                    </tr>                              
                                    </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <!-- /.row -->
                
                 <!-- /.row -->		
                 <div class="col-lg-12">  
                        <ol style="height:40px" class="breadcrumb">
                            <li style="cursor:pointer" class="active">
                                <i class="fa fa-edit"></i> 镜像列表</li>
                                	  <div style="float:right">
                                <!--	<button type="button" onclick="addMonitor()" class="btn btn-sm btn-warning"> 添加收集器</button> -->         
                            </div>
                        </ol>
                    </div>
                <div class="row">
                     <div class="col-lg-12">
                        <div class="table-responsive">
                            <table id="iTable" class="table table-hover">
                                <thead>
                                    <tr>
                                    	<th  style="display:none">id</th>
                                        <th style="width:5%">Id</th>  
                                        <th>RepoTags</th>                  
                                        <th>VirtualSize</th>  
                                        <th>Vendor</th> 
                                        <th>Name</th> 
                                         <!--<th>License</th>  --> 
                                        <th>BuildDate</th> 
                						<th>Size</th> 
                						<th>指令</th> 
                						<th style="width:10%">启动指令</th> 
                                    </tr>
                                </thead>
                                <tbody>
                                <#list iList as image>
                                    <tr>
                                    	<td  style="display:none"></td>
                                    	<td>${image.id}</td>
                                    	<td>${image.repoTags}</td>
                                        <td>${image.virtualSize}</td>            
               						    <td>${image.labels.vendor}</td>
               						    <td>${image.labels.name}</td>
               						    <!-- <td>${image.labels.license}</td> --> 
               						    <td>${image.labels.buildDate}</td>
               						    <td>${image.size}</td>   
               						    <td>${image.startCommand}</td>
               						    <td><a style="cursor: pointer" onclick="getMsg('${image.startCommand}','${image.id}')">编辑</a> 
               						        <a style="cursor: pointer" onclick="dockerCommand('${mac}','${image.id}','10','创建容器','${image.startCommand}')">执行</a>
               						        <a style="cursor: pointer" href="/log/getLog?mac=${mac}&serverId=${image.id}&serverName=Docker操作日志">操作日志</a>
               						    </td>          	              						    
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
					          <h4 class="modal-title" id="myModalLabel">指令</h4>
					        </div>
					        <div class="modal-body">				     
					          <div class="form-group">					           
                                <textarea id="msg"  class="comments" rows=10   name=s1   cols=27   onpropertychange= "this.style.posHeight=this.scrollHeight"></textarea>
					          </div>					          
					        </div>
					        <div class="modal-footer"> 
					          <button type="button" onclick="eidtImageCommand('${mac}')" id="btn_submit" class="btn btn-primary" data-dismiss="modal"></span>保存</button>    
					          <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					        </div>
					      </div>
					    </div>
					  </div>
            </div>
            <!-- /.container-fluid -->

        </div>
       </div>
     </div>
        <!-- /#page-wrapper -->
    <input type="hidden" id="serverName" value="${serverName}">
    <input type="hidden" id="imageId" value="">
</body>
<script>
 	function getMsg(r,id){
 		$('#msg').val(r);
 		$('#imageId').val(id);
 		$('#myModal').modal();
 	}
    function dockerCommand(mac,serverId,commandId,commandName,commandInfo){         
		    	$.post("/node/addCommand", {serverId:serverId,commandId:commandId,commandName:commandName,mac:mac,version:"",commandInfo:commandInfo},
		                    function(data){
		                    	var u=JSON.parse(data);
	                       		 alert("命令新增成功");
	                       			 location.reload();
		                    });
		 }
   function eidtImageCommand(mac){
 		var startCommand=$("#msg").val();
 		var imageId=$("#imageId").val();
 		$.post("/docker/eidtImageCommand", {mac:mac,startCommand:startCommand,imageId:imageId},
                    function(data){
                    	var u=JSON.parse(data);                  
                        location.reload();
                    });	   
 	}
 	function delProject(projectId){
 	     if (!confirm("确认要删除？")) {
            window.event.returnValue = false;
        }else{
    	$.post("/docker/delProject", {projectId:projectId},
                    function(data){
                    	var u=JSON.parse(data);                  
                        location.reload();
                    });	   
	        }
 	  
 	}
 </script>
  
</script>

<html>