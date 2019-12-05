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

								 主机管理

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

								<div class="caption"><i class="icon-edit"></i>主机列表</div>

							</div>

							<div class="portlet-body">

								<div class="clearfix">

								<table class="table table-striped table-hover table-bordered" id="">
									<thead>
                                    <tr>
                                    	<th  style="display:none">id</th>
                                        <th>HOST</th> 
                                        <th>MAC</th> 
                                        <th>机器名</th>   
                                        <th>机器状态</th>
                                        <th>DOCKER状态</th>   
                                        <th>上次交互时间</th>
                                        <th>Nginx自动配置日志</th>                               
                                        <th>操作</th>                                                     
                                    </tr>
                                </thead>
                                <tbody>
                                <#list nodeList as node>
                                    <tr>
                                    	<td  style="display:none"></td>
                                        <td>${node.ip}</td>
                                        <td>${node.mac}</td>
                                        <td>${node.alias}</td>
                                         <#if node.status==1>                     
	                                        <td style="color:green">
	                                        	<button type="button" class="btn red" onclick="delNode('${node.mac}')">已审核|删除</button>
	                                        </td>
	                                        <#else>
	                                         <td style="color:red">
	                                      	    <button type="button" class="btn blue" onclick="Approval('${node.mac}')">待审核</button>|
	                                      	    <button type="button" class="btn blue" onclick="delNode('${node.mac}')">删除</button>
                                        	 </td>
                                        </#if>
                                         <#if node.dockerStatus==1>                     
	                                        <td style="color:green">
	                                        	<button type="button" class="btn blue" onclick="dockerDetail('${node.mac}','${node.alias}')">docker运行正常</button>
	                                        </td>
	                                        <#elseif node.dockerStatus==2>
	                                        <td style="color:red">docker异常停止</td>
	                                        <#elseif node.dockerStatus==0>
	                                        <td style="color:purple">无docker服务</td>
	                                        <#else>
	                                        <td style="color:blue">未获取状态</td>
                                        </#if>
                                        <td>${node.time}</td>
                                        <td>
   						   				 	<button type="button" class="btn blue" onclick="getNginxLog('${node.mac}','${node.alias}')">查看NGINX日志</button>
   						   				 </td>
           								<td><a style="cursor: pointer" onclick="editHost('${node.mac}')">修改</a>
               						    </td>
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
					          <h4 class="modal-title" id="myModalLabel">修改机器名</h4>
					        </div>
					        <div class="modal-body">				     
					          <div class="form-group">					           
                                <label>机器名</label>
                                <input type="hidden" class="form-control" name="mac" id="mac">
                                <input class="form-control" name="alias" id="alias">
					          </div>					          
					        </div>
					        <div class="modal-footer">
					          <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					          <button type="button" onclick="eidtAlias()" id="btn_submit" class="btn btn-primary" data-dismiss="modal"></span>新增</button>
					        </div>
					      </div>
					    </div>
					  </div>
         	   </div>
			       <!-- /#wrapper -->
    <input type="hidden" id="serverId" value="${serverId}">
    <input type="hidden" id="serverName" value="${serverName}">

			</div>
			<!-- END PAGE CONTAINER-->
		</div>          
                  
	<!-- END CONTAINER -->

<!-- END BODY -->
</body> 
<script>
 	function editHost(mac){
 	    $("#mac").val(mac);
 		$('#myModal').modal();
 	}
 	function eidtAlias(){
 		var mac=$("#mac").val();
 		var alias=$("#alias").val();
 		$.post("/baseNode/editAlias", {mac:mac,alias:alias},
                    function(data){
                    	var u=JSON.parse(data);                  
                        location.reload();
                    });	   
 	}
 	
 	function dockerDetail(mac,alias){
		addNewTabs({
			id:'10013',title: alias+'Docker详情',close: true,url:'/docker/dockerDetail?mac='+mac}
		);
	}
 	
 	function getNginxLog(mac,alias){
		addNewTabs({
			id:'10012',title: alias+'日志',close: true,url:'/nginxLog/getNginxLog?mac='+mac+'&alias='+alias}
		);
	}
	
	function Approval(mac){
		 if (!confirm("确认要批准此服务器进入系统？")) {
                        window.event.returnValue = false;
                    } else {
                        $.post("/baseNode/approval", {mac: mac},
                                function (data) {
                                    var u = JSON.parse(data);
                                    if (u.msg == "success") {
                                        alert("审核成功");
                                    } else {
                                        alert(u.msg);
                                    }
                                    location.reload();
                                });
                    }
	}
	function delNode(mac){
		if (!confirm("确认删除此服务器？")) {
                        window.event.returnValue = false;
                    } else {
                        $.post("/baseNode/delBaseNode", {mac: mac},
                                function (data) {
                                    var u = JSON.parse(data);
                                    if (u.msg == "success") {
                                        alert("删除成功");
                                    } else {
                                        alert(u.msg);
                                    }
                                    location.reload();
                                });
                    }
	}

  
</script>

<html>