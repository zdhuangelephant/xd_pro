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

							基础服务列表

						</h3>

						<ul class="breadcrumb">

							<li>

								<i class="icon-home"></i>

								基础服务列表


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

								<div class="caption"><i class="icon-edit"></i>基础服务列表</div>

							</div>

							<div class="portlet-body">

								<div class="clearfix">

									<div class="btn-group">

										<button id="sample_editable_3_new" class="btn green" onclick="addBaseServer()">

										Add New <i class="icon-plus"></i>
									
										</button>
									</div>
		

								<table class="table table-striped table-hover table-bordered">
                                <thead>
                                    <tr>
                                    	<th>id</th>
                                        <th>服务名称</th>                                        
                                        <th>基础目录</th>  
                                        <th>tomcat端口号</th>                                     
                                        <th>War包地址</th>                                                           
                                        <th>SH命令执行用户</th>                            
                                        <th>操作</th>                                                     
                                    </tr>
                                </thead>
                                <tbody>
                                <#list baseServerList as server>
                                    <tr>
                                        <td>${server.baseServerId}</td>
                                        <td>${server.serverName}</td>
                                        <td>${server.baseDir}</td>
                                        <td>${server.tomcatPort}</td>   
                                        <td>${server.warAdress}</td>
                                        <td>${server.user}</td>
           								<td><a style="cursor: pointer" onclick="editBaseServer('${server.baseServerId}','${server.serverName}','${server.warAdress}','${server.baseDir}','${server.tomcatPort}','${server.user}')">修改</a>
               						    	 <a style="cursor: pointer" onclick="delBaseServer('${server.baseServerId}')">删除</a>	
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
                  <div class="modal fade" id="baseServerEdit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					    <div class="modal-dialog" role="document">
					      <div class="modal-content">
					        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					          <h4 class="modal-title" id="myModalLabel">Server</h4>
					        </div>
					        <div class="modal-body">				     
						         <div class="form-group">
						         <input type="hidden" class="form-control" name="baseServerId" id="baseServerId">
	                                <label>请输入服务名称</label>
	                                <input class="form-control" name="serverName" id="serverName">
	                            </div> 
	                            <div class="form-group">
	                                <label>请输入基础目录</label>
	                                <input class="form-control" name="baseDir" id="baseDir"> 
	                            </div>
	                            <div class="form-group">
	                                <label>请输入端口号</label>
	                                <input class="form-control" name="tomcatPort" id="tomcatPort"> 
	                            </div>                                               
	                             <div class="form-group">
	                                <label>请输入WAR包位置</label>
	                                <input class="form-control" name="warAdress" id="warAdress"> 
	                            </div>        
	                             <div class="form-group">
                               		 <label>请输入SHELL执行用户</label>
                                <input class="form-control" name="user" id="user"> 
                           		 </div>                                             				          
					        </div>
					        <div class="modal-footer">
					          <button type="button" onclick="eidtBaseServerById()" id="btn_submit" class="btn btn-primary" data-dismiss="myModal"></span>修改</button>
					        </div>
					      </div>
					    </div>
					  </div>		
					  
					    <div class="modal fade" id="baseServerAdd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					    <div class="modal-dialog" role="document">
					      
					      <div class="modal-content">
					      	 <form role="form" method="post" id="form2"  action="/server/doAdd"  >
					        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					          <h4 class="modal-title" id="myModalLabel">新增BaseServer</h4>
					        </div>
					        <div class="modal-body">				          
                            <div class="form-group">
                                <label>请输入服务名称</label>
                                <input class="form-control" id="serverNameAdd" name="serverNameAdd">
                            </div>                          
                             <div class="form-group">
                                <label>请输入基础目录BASEDIR</label>
                                <input class="form-control" id="baseDirAdd" name="baseDirAdd">
                            </div>  
                             <div class="form-group">
                                <label>请输入TOMCAT端口号</label>
                                <input class="form-control" id="tomcatPortAdd" name="tomcatPortAdd">
                            </div>                          
                             <div class="form-group">
                                <label>请输入WAR包位置</label>
                                <input class="form-control" id="warAdressAdd" name="warAdressAdd"> 
                            </div>  
                             <div class="form-group">
                                <label>请输入SHELL执行用户</label>
                                <input class="form-control" id="userAdd" name="userAdd"> 
                            </div>  
					         <div class="modal-footer">	   
					          <button type="button"  id="btn_submit" class="btn btn-primary" onclick="baseServerSubmit()"></span>确定</button>		
					        </div>
					        </form>
					      </div>     
						</div>
				    </div>
            <!-- /.container-fluid -->
		</div>
			<!-- END PAGE CONTAINER-->
	 </div>          
                  
	<!-- END CONTAINER -->
    <input type="hidden" id="serverGroupTypeId" name="serverGroupTypeId">
<!-- END BODY -->
</body> 

<script> 	
	function addBaseServer(){
 		$('#baseServerAdd').modal();	
 	}
 	
 	function baseServerSubmit(){
 		var serverName=$('#serverNameAdd').val();
 		var warAdress=$('#warAdressAdd').val();
 		var baseDir=$('#baseDirAdd').val();	
 		var tomcatPort=$('#tomcatPortAdd').val();	
 		var user=$('#userAdd').val();	
 		 $.post("/baseServer/doAdd", {serverName:serverName,warAdress:warAdress,baseDir:baseDir,tomcatPort:tomcatPort,user:user},
                    function(data){
                    	var u=JSON.parse(data);
                        if(u.msg=="success"){
		                     alert("新增成功");
		                 }else{
		                   alert(u.msg);
		                   return;
		                 }
                        location.reload();
                    });	   
 	}

 function editBaseServer(baseServerId,serverName,warAdress,baseDir,tomcatPort,user){
		$('#baseServerId').val(baseServerId);
 		$('#serverName').val(serverName);
 		$('#warAdress').val(warAdress);	
 		$('#baseDir').val(baseDir);	
 		$('#tomcatPort').val(tomcatPort);
 		$('#user').val(user);
 		$('#baseServerEdit').modal();	
 	}

function eidtBaseServerById(){
 		var baseServerId=$('#baseServerId').val();
 		var serverName=$('#serverName').val();
 		var warAdress=$('#warAdress').val();
 		var baseDir=$('#baseDir').val();	
 		var tomcatPort=$('#tomcatPort').val();	
 		var user=$('#user').val();	
 		 $.post("/baseServer/edit", {baseServerId:baseServerId,serverName:serverName,warAdress:warAdress,baseDir:baseDir,tomcatPort:tomcatPort,user:user},
                    function(data){
                    	var u=JSON.parse(data);
                        if(u.msg=="success"){
		                     alert("修改成功");
		                 }else{
		                   alert(u.msg);
		                 }
                        location.reload()
                    });	   
 		}

 

 	function delBaseServer(baseServerId){
     if (!confirm("确认要删除？")) {
            window.event.returnValue = false;
        }else{
    		$.post("/baseServer/remove", {baseServerId:baseServerId},
	                    function(data){	
	                    	var u=JSON.parse(data);
                        	if(u.msg=="success"){
		                    	 alert("删除成功");
		                 	}else{
		                  		 alert(u.msg);
		                	 }
                        	location.reload();                    	
	                    });  
	        }
  	  }
  	  
  	  
  	 
  
</script>

<html>