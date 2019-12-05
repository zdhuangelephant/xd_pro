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

							日志收集列表

						</h3>

						<ul class="breadcrumb">

							<li>

								<i class="icon-home"></i>

								日志收集列表


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

								<div class="caption"><i class="icon-edit"></i>日志收集列表</div>

							</div>

							<div class="portlet-body">

								<div class="clearfix">

									<div class="btn-group">

										<button id="" class="btn green" onclick="addMonitor()">

										Add New <i class="icon-plus"></i>
									
										</button>
									</div>
		

						<table class="table table-striped table-hover table-bordered">
                            <thead>
                            <tr>
                                <th>id</th>
                                <th>待收集日志路径</th>
                                <th>路径匹配前缀</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list logMonitorList as logMonitor>
                                <tr>
                                    <td>${logMonitor.logMonitorId}</td>
                                    <td>${logMonitor.logPath}</td>
                                    <td>${logMonitor.logPrefix}</td>
                                    <td><a style="cursor: pointer" onclick="editLogMonitor('${logMonitor.logMonitorId}','${logMonitor.logPath}','${logMonitor.logPrefix}')">修改 |</a>
                                        <a style="cursor: pointer" onclick="removeLogMonitorId('${logMonitor.logMonitorId}')">删除</a>
                                   		<a style="cursor: pointer" onclick="relationServerGroup('${logMonitor.logMonitorId}')">关联服务组</a>
                                    </td>
                                    </td>
                                </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            
             <div class="modal fade" id="monitorSubmitAdd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <form role="form" method="post" id="form2"   >
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                                <h4 class="modal-title" id="myModalLabel">新增日志收集器</h4>
                            </div>
                            <div class="modal-body">
                                <div class="form-group">
                                    <label>日志文件路径</label>
                                    <input class="form-control" id="logPath" name="logPath">
                                </div>
                                <div class="form-group">
                                    <label>日志文件前缀</label>
                                    <input class="form-control" id="logPrefix" name="logPrefix">
                                </div>
                                <div class="modal-footer">
                                    <button type="button"  id="btn_submit" class="btn btn-primary" onclick="logMonitorSubmit()">确定</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            
            <!-- /.row -->
            <div class="modal fade" id="logMonitorEdit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                            <h4 class="modal-title" id="myModalLabel">修改日志收集器</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <label>日志文件路径</label>
                                <input class="form-control" id="logPathEdit" name="logPathEdit">
                            </div>
                        </div>
                         <div class="modal-body">
	                        <div class="form-group">
	                            <label>日志文件前缀</label>
	                            <input class="form-control" id="logPrefixEdit" name="logPrefixEdit">
	                        </div>
                         </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                            <button type="button" onclick="doEidtLogMonitor()" id="btn_submit" class="btn btn-primary" data-dismiss="modal"></span>新增</button>
                        </div>
                    </div>
                </div>
            </div>
           
           
           
             <div class="modal fade" style="height:800px; overflow:scroll;" id="logMonitorServerGroupModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					    <div class="modal-dialog" role="document">
					  <form role="form" method="post" id="form3"    enctype="multipart/form-data">
					      <div class="modal-content">
					        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					          <h4 class="modal-title" id="myModalLabel">生效服务组</h4>
	     			        </div>				         
					     	 <input type="hidden" class="form-control" name="serverIdB" id="serverIdB">
					        <div class="modal-body">	
					         <div class="form-group">
                                	    <table id="logMonitorServerGroupTable" class="table table-hover">
                                <thead>
                                    <tr>                                          
                                    </tr>
                                	</thead>                              
									</table>
                                </div>		     
						       <div class="form-group">    		                                                  				          
					        </div>
					        <div class="modal-footer">	   
					          <button type="button" onclick="editLogMonitorServerGroup()" id="btn_submit" class="btn btn-primary" data-dismiss="addFile"></span>确定</button>		
					        </div>
					      </div>
					        </form>       
					    </div>
					  </div>
					 </div>
            <!-- /.container-fluid -->
        </div>
        <!-- /#page-wrapper -->
        <!-- /#wrapper -->
        <input type="hidden" id="logMonitorId" name="logMonitorId">
			<!-- END PAGE CONTAINER-->
	 </div>          
                  
	<!-- END CONTAINER -->
    <input type="hidden" id="serverGroupTypeId" name="serverGroupTypeId">
<!-- END BODY -->
</body> 
        <script>
            function removeLogMonitorId(logMonitorId){
                if (!confirm("确认要删除？")) {
                    window.event.returnValue = false;
                }else{
                    $.post("/logMonitor/remove", {logMonitorId:logMonitorId},
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

            function addMonitor(){
                $('#monitorSubmitAdd').modal();
            }
            function logMonitorSubmit(){
                var logPath=$('#logPath').val();
                var logPrefix=$('#logPrefix').val();
                if(logPath==""||logPrefix==""){
                    alert("不能有为空值");
                    return;
                }
                $.post("/logMonitor/doAdd", {logPath:logPath,logPrefix:logPrefix},
                        function(data){
                            var u=JSON.parse(data);
                            if(u.msg=="success"){
                                alert("新增成功");
                            }else{
                                alert(u.msg);
                            }
                            location.reload();
                        });
            }
            function editLogMonitor(logMonitorId,logPath,logPrefix){
                $("#logMonitorId").val(logMonitorId);
                $("#logPathEdit").val(logPath);
                $("#logPrefixEdit").val(logPrefix);
                $('#logMonitorEdit').modal();
            }
            function doEidtLogMonitor(){
                var logMonitorId=$("#logMonitorId").val();
                var logPath=$("#logPathEdit").val();
                var logPrefix=$("#logPrefixEdit").val();
                if(logPath==""||logPrefix==""){
                    alert("不能有为空值");
                    return;
                }
                $.post("/logMonitor/edit", {logMonitorId:logMonitorId,logPath:logPath,logPrefix:logPrefix},
                        function(data){
                            var u=JSON.parse(data);
                            if(u.msg=="success"){
                                alert("修改成功");
                            }else{
                                alert(u.msg);
                            }
                            location.reload();
                        });
            }
            
            
     function relationServerGroup(logMonitorId) {
      $("#logMonitorId").val(logMonitorId);
      $.post("/logMonitor/relationServerGroup", {logMonitorId:logMonitorId},
                    function(data){
	                    var u=JSON.parse(data);
                    	if(u.msg=='success'){   
                    		var groupList=u.groupList;
                    		var relationList=u.relationList;
                    		$("#logMonitorServerGroupTable tr").empty(); 	
	                    	for(var i=0;i<groupList.length;i++){
	                    	   var flag="0"; 
	                    	   if(relationList!=null){ 
		                    	   for(var j=0;j<relationList.length;j++){
		                    	   	   if(groupList[i].groupId==relationList[j].groupId)
		                    	   	   	flag="1";
		                    	   	 }
		                    	}
	                    	   if(flag=="1"){
								 var row="<tr><td><input name='groupid' type='checkbox' preChecked='checked' checked value="+groupList[i].groupId+"> "+groupList[i].groupName+"("+groupList[i].groupTypeName+")"+"</td></tr>"
								}else{
								 var row="<tr><td><input name='groupid' type='checkbox'  value="+groupList[i].groupId+"> "+groupList[i].groupName+"("+groupList[i].groupTypeName+")"+"</td></tr>"
								}
								$("#logMonitorServerGroupTable tr:last").after(row);  
							}
							$('#logMonitorServerGroupModel').modal();	
                    	}else{
                    		alert(u.msg);
                    		return;
                    	}             	
                    	
                    });	   
    }
    
    function editLogMonitorServerGroup(){
  		var logMonitorId=$('#logMonitorId').val();
 		var needDeleteId="";
 		var needAddId="";
 	   $(":checkbox[name='groupid']").each(function(){
            var id = $(this).val();
            var preChecked = $(this).attr("preChecked");
            if($(this).is(":checked")){
                if(preChecked!="checked"){
                    needAddId = needAddId + id + ";";
                }
            } else {
                if(preChecked=="checked"){
                    needDeleteId = needDeleteId + id + ";";
                }
            }
        });
 		 $.post("/logMonitor/editLogMonitorServerGroup", {logMonitorId:logMonitorId,needAddId:needAddId,needDeleteId:needDeleteId},
                    function(data){
                    	 var u=JSON.parse(data);
	                    	if(u.msg=="success"){
		                        alert("修改成功");
		                        location.reload();
	                        }else{
	                            alert(u.error);    
	                        }           
                    });	   
 	}
 
 

        </script>
    </html>