            <input type="hidden" class="form-control" name="groupServiceType1" id="groupServiceType1" value="${groupServiceType}">
            <input type="hidden" class="form-control" name="groupType1" id="groupType1" value="${groupType}">

             <!-- BEGIN PAGE CONTENT-->
				<div class="row-fluid">
					<div class="span12">
   					 <!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet box blue">
							<div class="portlet-title">
								<div class="caption"><i class="icon-edit"></i>中间件服务列表</div>
							</div>
							<div class="portlet-body">
								<div class="clearfix">
									<div class="btn-group">

										<button id="sample_editable_3_new" class="btn green" onclick="addMiddleServer()">

										新增中间件服务 <i class="icon-plus"></i>
									
										</button>
									</div>
		
								<table class="table table-striped table-hover table-bordered" >
									<thead>
                                    <tr>
                                    	<th  style="display:none">id</th>
                                        <th>服务类型名称</th>                                        
                                        <th>安装命令</th>  
                                        <th>用户名</th>                                     
                                        <th>密码</th>                                                           
                                        <th>程序目录</th>  
                                        <th>程序监控端口号</th> 
                                        <th>执行用户</th>  
                                        <th>RabbitMq消息阻塞数量阈值</th>  
                                        <th>监控状态</th> 
                                       	<th>操作</th>               
                                    </tr>
                                </thead>
                                <tbody>
                                <#list middleServerList as server>
                                    <tr>
                                    	<td  style="display:none"></td>
                                        <td>${server.serverName}</td>
                                        <td>${server.installCommand}</td>
                                        <td>${server.serverUserName}</td>   
                                        <td>${server.serverPassword}</td>
                                        <td>${server.projectDir}</td>
                                        <td>${server.projectPort}</td>
                                        <td>${server.user}</td>
                                        <td>${server.blockMessageCount}</td>
                                        <td>
                                        	<#if server.timingMonitoring==-1>                     
							                    		关闭
							                <#elseif server.timingMonitoring==1>
							                   			开启
							               </#if>
                                        </td>         	
                                        <td>           		 
                                       		 <a style="cursor: pointer" href="/node/nodeMiddleServerList?groupId=${groupId}&serverId=${server.serverId}&serverName=${server.serverName}"><img style="width:auto;height:40px;" src="${baseOP}/image/common/jiedian.png"></img></a>    
                                       		 <a style="cursor: pointer" onclick="editMiddleServer('${server.serverId}','${server.serverType}','${server.installCommand}','${server.serverUserName}','${server.serverPassword}','${server.projectDir}','${server.projectPort}','${server.user}','${server.flumeIp}','${server.flumeRabbitMqIp}','${server.flumeRabbitMqQueueName}','${server.flumeRabbitMqUserName}','${server.flumeRabbitMqPassword}','${server.flumeRabbitMqPort}','${server.flumeRabbitMqExchange}','${server.blockMessageCount}','${server.timingMonitoring}')"><img style="width:auto;height:40px;" src="${baseOP}/image/common/peizhi.png"></img></a>     		 
                                       		 <a style="cursor: pointer" onclick="delMiddleServer('${server.serverId}')"><img style="width:auto;height:40px;" src="${baseOP}/image/common/del.png"></img></a>
                                        </td>
                                    </tr>                              
                                </#list>
                               </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                
                	  
					  <div class="modal fade" style="height:800px; overflow:scroll;" id="middleServerAdd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					    <div class="modal-dialog" role="document">
					      <div class="modal-content">
					      	 <form role="form" method="post" id="form2"  action="/middleServer/doAdd"  >
					        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					          <h4 class="modal-title" id="myModalLabel">新增中间件服务</h4>
					        </div>
					       <div class="modal-body">				     
					          <div class="form-group">					           
                                <label>中间件服务选择</label>
                                <select id="serverType" onchange="change(this)"   class="form-control">
                                     	 <option value="5001">Mysql</option>   
                                     	 <option value="5002">Redis</option> 
                                     	 <option value="5003">Mongo</option>
                                     	 <option value="5004">Flume</option>
                                     	 <option value="5005">RabbitMq</option>
                                     	 <option value="5006">Zookeeper</option>
                                     	 <option value="5007">Jstorm</option>                                                  
                                </select> 
					          </div>
					            <div name="commcon" class="form-group">       
	                                <label>安装SHELL七牛URL</label>
	                                <input class="form-control" name="installCommand" id="installCommand">
	                            </div> 
	                            <div name="commcon" class="form-group">
	                                <label>用户名</label>
	                                <input class="form-control" name="serverUserName" id="serverUserName">
	                            </div> 
	                            <div name="commcon" class="form-group">
	                                <label>密码</label>
	                                <input class="form-control" name="serverPassword" id="serverPassword">
	                            </div>   
	                            <div name="commcon" class="form-group">
	                                <label>程序根目录</label>
	                                <input class="form-control" name="projectDir" id="projectDir">
	                            </div> 	
	                            <div name="commcon" class="form-group">
	                                <label>监控端口号</label>
	                                <input class="form-control" name="projectPort" id="projectPort">
	                            </div> 		 		
	                           <div name="commcon" class="form-group">				    
	                                <label>执行用户</label>
	                                <input class="form-control" name="user" id="user">
	                            </div> 
	                            <div  style="display:none" name="rabbitMq" class="form-group">       
	                                <label>rabbitMq阻塞数量阈值</label>
	                                <input class="form-control" name="blockMessageCount" id="blockMessageCount">
	                            </div> 		
	                             <div style="display:none" name="flume" class="form-group">       
	                                <label>flumeIp</label>
	                                <input class="form-control" name="flumeIp" id="flumeIp">
	                            </div> 		
	                             <div style="display:none" name="flume" class="form-group">       
	                                <label>flumeRabbitMqIp</label>
	                                <input class="form-control" name="flumeRabbitMqIp" id="flumeRabbitMqIp">
	                            </div> 		
	                             <div style="display:none" name="flume" class="form-group">       
	                                <label>flumeRabbitMqQueueName</label>
	                                <input class="form-control" name="flumeRabbitMqQueueName" id="flumeRabbitMqQueueName">
	                            </div> 		
	                             <div style="display:none" name="flume" class="form-group">       
	                                <label>flumeRabbitMq用户名</label>
	                                <input class="form-control" name="flumeRabbitMqUserName" id="flumeRabbitMqUserName">
	                            </div> 		
	                             <div style="display:none" name="flume" class="form-group">       
	                                <label>flumeRabbitMq密码</label>
	                                <input class="form-control" name="flumeRabbitMqPassword" id="flumeRabbitMqPassword">
	                            </div> 
	                            <div style="display:none" name="flume" class="form-group">       
	                                <label>flumeRabbitMq端口号</label>
	                                <input class="form-control" name="flumeRabbitMqPort" id="flumeRabbitMqPort">
	                            </div> 
	                            <div style="display:none" name="flume" class="form-group">       
	                                <label>flumeRabbitMqExchange</label>
	                                <input class="form-control" name="flumeRabbitMqExchange" id="flumeRabbitMqExchange">
	                            </div>		 	
	                             <div name="commcon" class="form-group">       
	                                <label>定时监控</label>
	                                <select id="timingMonitoring" class="form-control">
                                     	 <option value="-1">关闭</option>   
                                     	 <option value="1">开启</option>                                            
                                	</select> 
	                            </div> 				          
					        </div>
					         <div class="modal-footer">	   
					          <button type="button"  id="btn_submit" class="btn btn-primary" onclick="middleServerSubmit()"></span>确定</button>		
					        </div>
					        </form>
					      </div>     
					   </div>
				      </div>
				      
				     <div class="modal fade" style="height:800px; overflow:scroll;" id="middleServerEdit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					    <div class="modal-dialog" role="document">
					      <div class="modal-content">
					      	 <form role="form" method="post" id="form2"  action=""  >
					        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					          <h4 class="modal-title" id="myModalLabel">编辑中间件服务</h4>
					        </div>
					       <div class="modal-body">				     
					          <div class="form-group">	
					           <input type="hidden" class="form-control"  id="serverIdEdit" >     				           
                                <label>中间件服务选择</label>
                                <select id="serverTypeEdit" class="form-control">
                                     	 <option value="5001">Mysql</option>   
                                     	 <option value="5002">Redis</option> 
                                     	 <option value="5003">Mongo</option>
                                     	 <option value="5004">Flume</option>
                                     	 <option value="5005">RabbitMq</option>
                                     	 <option value="5006">Zookeeper</option>
                                     	 <option value="5007">Jstorm</option>                                                  
                                </select> 
					          </div>
					          <div class="form-group">       
	                                <label>安装文件地址</label>
	                                <input class="form-control"  id="installCommandEdit">
	                            </div> 
	                            <div class="form-group">
	                                <label>用户名</label>
	                                <input class="form-control"  id="serverUserNameEdit">
	                            </div> 
	                            <div class="form-group">
	                                <label>密码</label>
	                                <input class="form-control"  id="serverPasswordEdit">
	                            </div>     
	                            <div class="form-group">
	                                <label>程序根目录</label>
	                                <input class="form-control" name="projectDirEdit" id="projectDirEdit">
	                            </div> 	
	                            <div class="form-group">
	                                <label>监控端口号</label>
	                                <input class="form-control" name="projectPortEdit" id="projectPortEdit">
	                            </div> 			
	                           <div class="form-group">				    
	                                <label>执行用户</label>
	                                <input class="form-control"  id="userEdit">
	                            </div> 
	                          <div  style="display:none" name="rabbitMq" class="form-group">       
	                                <label>rabbitMq阻塞数量阈值</label>
	                                <input class="form-control" name="blockMessageCountEdit" id="blockMessageCountEdit">
	                            </div> 		
	                             <div style="display:none" name="flume" class="form-group">       
	                                <label>flumeIp</label>
	                                <input class="form-control" name="flumeIpEdit" id="flumeIpEdit">
	                            </div> 		
	                             <div style="display:none" name="flume" class="form-group">       
	                                <label>flumeRabbitMqIp</label>
	                                <input class="form-control" name="flumeRabbitMqIpEdit" id="flumeRabbitMqIpEdit">
	                            </div> 		
	                             <div style="display:none" name="flume" class="form-group">       
	                                <label>flumeRabbitMqQueueName</label>
	                                <input class="form-control" name="flumeRabbitMqQueueNameEdit" id="flumeRabbitMqQueueNameEdit">
	                            </div> 		
	                             <div style="display:none" name="flume" class="form-group">       
	                                <label>flumeRabbitMq用户名</label>
	                                <input class="form-control" name="flumeRabbitMqUserNameEdit" id="flumeRabbitMqUserNameEdit">
	                            </div> 		
	                             <div style="display:none" name="flume" class="form-group">       
	                                <label>flumeRabbitMq密码</label>
	                                <input class="form-control" name="flumeRabbitMqPasswordEdit" id="flumeRabbitMqPasswordEdit">
	                            </div> 
	                            <div style="display:none" name="flume" class="form-group">       
	                                <label>flumeRabbitMq端口号</label>
	                                <input class="form-control" name="flumeRabbitMqPortEdit" id="flumeRabbitMqPortEdit">
	                            </div> 
	                            <div style="display:none" name="flume" class="form-group">       
	                                <label>flumeRabbitMqExchange</label>
	                                <input class="form-control" name="flumeRabbitMqExchangeEdit" id="flumeRabbitMqExchangeEdit">
	                            </div>		 	
	                            <div class="form-group">       
	                                <label>定时监控</label>
	                                <select id="timingMonitoringEdit" class="form-control">
                                     	 <option value="-1">关闭</option>   
                                     	 <option value="1">开启</option>                                            
                                	</select> 
	                            </div> 					          
					        </div>
					         <div class="modal-footer">	   
					          <button type="button"  id="btn_submit" class="btn btn-primary" onclick="middleServerEditSubmit()"></span>确定</button>		
					        </div>
					        </form>
					      </div>     
					   </div>
				      </div>
		 </div>
  <script>
	function change(obj){
		var serverType=$('#serverType').val();
		init(serverType);
	}

	function init(serverType){
		$("div[name='flume']").hide();
		$("div[name='rabbitMq']").hide();
		if(serverType=="5001"){		
			$('#installCommand').val("http://7xlos1.com1.z0.glb.clouddn.com/ins_mysql_v1.0.0.sh");
			$('#projectDir').val("/var/lib/mysql");
			$('#user').val("root");			
		}else if(serverType=="5002"){
			$('#installCommand').val("http://7xlos1.com1.z0.glb.clouddn.com/ins_redis_v1.0.0.sh");
			$('#projectPort').val("6379");	
			$('#projectDir').val("/usr/local/bin/redis");
			$('#user').val("root");	
		}else if(serverType=="5003"){
			$('#installCommand').val("http://7xlos1.com1.z0.glb.clouddn.com/ins_mongodb_v1.0.0.sh");
			$('#projectPort').val("27017");		
			$('#projectDir').val("/var/lib/mongo");
			$('#user').val("root");	
		}else if(serverType=="5004"){
			$('#installCommand').val("http://7xlos1.com1.z0.glb.clouddn.com/ins_flume_v1.0.0.sh");
			$('#projectPort').val("5870");	
			$('#projectDir').val("/usr/local/flume");
			$('#user').val("root");	
			$('#flumeIp').val("172.26.118.108");
			$('#flumeRabbitMqIp').val("172.26.118.112");
			$('#flumeRabbitMqQueueName').val("FlumeNgMessage");
			$('#flumeRabbitMqUserName').val("rabbitmq");	
			$('#flumeRabbitMqPassword').val("cKY5QceiF7R8NCoBg5yb");
			$('#flumeRabbitMqPort').val("5672");
			$('#flumeRabbitMqExchange').val("FlumeMessageExchanges");	
			$("div[name='flume']").show();	
		}else if(serverType=="5005"){
			$("div[name='rabbitMq']").show();
			$('#installCommand').val("http://7xlos1.com1.z0.glb.clouddn.com/ins_erlang_rabbitmq_v1.0.0.sh");
			$('#projectPort').val("15672");	
			$('#projectDir').val("/usr/lib/rabbitmq");
			$('#user').val("root");			
			$('#blockMessageCount').val("1000");		
		}else if(serverType=="5006"){
			$('#installCommand').val("http://7xlos1.com1.z0.glb.clouddn.com/ins_zookeeper_v1.0.0.sh");
			$('#projectPort').val("2181");	
			$('#projectDir').val("/home/zkjs/zookeeper-3.4.6");
			$('#user').val("root");	
		}else if(serverType=="5007"){
			$('#installCommand').val("http://7xlos1.com1.z0.glb.clouddn.com/ins_jstorm_v1.0.0.sh");
			$('#projectDir').val("/home/zkjs/jstorm-2.1.1");
			$('#user').val("root");
		}
	}
	
    function getNodes(serverId,serverName,warAdress,groupServiceType){
		addNewTabs({
			id:'100809',title: serverName+'节点列表',close: true,url:'/node/list?serverId='+serverId+'&serverName='+serverName+'&warAdress='+warAdress+'&groupServiceType='+groupServiceType}
		);
	}
	
  	function addMiddleServer(){
  		init("5001");
 		$('#middleServerAdd').modal();	
 	}
  
   function middleServerSubmit(){
    	var groupId=$('#groupId').val();
 		var serverType=$('#serverType').val();
 		var installCommand=$('#installCommand').val();
 		var serverUserName=$('#serverUserName').val();
 		var serverPassword=$('#serverPassword').val();
 		var projectDir=$('#projectDir').val();
 		var projectPort=$('#projectPort').val();
 		var user=$('#user').val();
 		var flumeIp=$('#flumeIp').val();
		var flumeRabbitMqIp=$('#flumeRabbitMqIp').val();
		var flumeRabbitMqQueueName=$('#flumeRabbitMqQueueName').val();
		var flumeRabbitMqUserName=$('#flumeRabbitMqUserName').val();	
		var flumeRabbitMqPassword=$('#flumeRabbitMqPassword').val();
		var flumeRabbitMqPort=$('#flumeRabbitMqPort').val();
		var flumeRabbitMqExchange=$('#flumeRabbitMqExchange').val();	
 		var blockMessageCount=$('#blockMessageCount').val();
 		var timingMonitoring=$('#timingMonitoring').val();
 		 $.post("/middleServer/add", {
					 groupId:groupId,
					 serverType:serverType,
					 installCommand:installCommand,
					 serverUserName:serverUserName,
					 serverPassword:serverPassword,
					 projectDir:projectDir,
					 projectPort:projectPort,
					 user:user,
					 flumeIp:flumeIp,
					 flumeRabbitMqIp:flumeRabbitMqIp,
					 flumeRabbitMqQueueName:flumeRabbitMqQueueName,
					 flumeRabbitMqUserName:flumeRabbitMqUserName,
					 flumeRabbitMqPassword:flumeRabbitMqPassword,
					 flumeRabbitMqPort:flumeRabbitMqPort,
					 flumeRabbitMqExchange:flumeRabbitMqExchange,
					 blockMessageCount:blockMessageCount,
					 timingMonitoring:timingMonitoring
 		 	},
            function(data){
            	var u=JSON.parse(data);
                alert("新增成功");
                location.reload();
            });	   
 	}
  
  
   function delMiddleServer(serverId){
     if (!confirm("确认要删除？")) {
            window.event.returnValue = false;
        }else{
    	$.post("/middleServer/del", {serverId:serverId},
	                    function(data){	
	                    	location.reload();                    	
	                    });  
	       }
    }
  
   	function editMiddleServer(serverId,serverType,installCommand,serverUserName,serverPassword,projectDir,projectPort,user,flumeIp,flumeRabbitMqIp,flumeRabbitMqQueueName,flumeRabbitMqUserName,flumeRabbitMqPassword,flumeRabbitMqPort,flumeRabbitMqExchange,blockMessageCount,timingMonitoring){	
   		$('#serverIdEdit').val(serverId);
 		$('#serverTypeEdit').val(serverType);
 		$('#installCommandEdit').val(installCommand);
 		$('#serverUserNameEdit').val(serverUserName);
 		$('#serverPasswordEdit').val(serverPassword);
 		$('#projectDirEdit').val(projectDir);
 		$('#projectPortEdit').val(projectPort);
 		$('#userEdit').val(user);
 		$('#flumeIpEdit').val(flumeIp);
		$('#flumeRabbitMqIpEdit').val(flumeRabbitMqIp);
		$('#flumeRabbitMqQueueNameEdit').val(flumeRabbitMqQueueName);
		$('#flumeRabbitMqUserNameEdit').val(flumeRabbitMqUserName);	
		$('#flumeRabbitMqPasswordEdit').val(flumeRabbitMqPassword);
		$('#flumeRabbitMqPortEdit').val(flumeRabbitMqPort);
		$('#flumeRabbitMqExchangeEdit').val(flumeRabbitMqExchange);	
 		$('#blockMessageCountEdit').val(blockMessageCount);
 		$('#timingMonitoringEdit').val(timingMonitoring);
 		init(serverType);
 		$('#middleServerEdit').modal();	
   	}
   	
   function middleServerEditSubmit(){
 		var serverId=$('#serverIdEdit').val();
 		var serverType=$('#serverTypeEdit').val();
 		var installCommand=$('#installCommandEdit').val();
 		var serverUserName=$('#serverUserNameEdit').val();
 		var serverPassword=$('#serverPasswordEdit').val();
 		var projectDir=$('#projectDirEdit').val();
 		var projectPort=$('#projectPortEdit').val();
 		var baseDir=$('#baseDirEdit').val();
 		var user=$('#userEdit').val();
 		var flumeIp=$('#flumeIpEdit').val("");
		var flumeRabbitMqIp=$('#flumeRabbitMqIpEdit').val("");
		var flumeRabbitMqQueueName=$('#flumeRabbitMqQueueNameEdit').val("");
		var flumeRabbitMqUserName=$('#flumeRabbitMqUserNameEdit').val("");	
		var flumeRabbitMqPassword=$('#flumeRabbitMqPasswordEdit').val("");
		var flumeRabbitMqPort=$('#flumeRabbitMqPortEdit').val("");
		var flumeRabbitMqExchange=$('#flumeRabbitMqExchangeEdit').val("");	
 		var blockMessageCount=$('#blockMessageCountEdit').val();
 		var timingMonitoring=$('#timingMonitoringEdit').val();
 		 $.post("/middleServer/edit", {		
					 serverId:serverId,
					 serverType:serverType,
					 installCommand:installCommand,
					 serverUserName:serverUserName,
					 serverPassword:serverPassword,
					 projectDir:projectDir,
					 projectPort:projectPort,
					 user:user,
					 flumeIp:flumeIp,
					 flumeRabbitMqIp:flumeRabbitMqIp,
					 flumeRabbitMqQueueName:flumeRabbitMqQueueName,
					 flumeRabbitMqUserName:flumeRabbitMqUserName,
					 flumeRabbitMqPassword:flumeRabbitMqPassword,
					 flumeRabbitMqPort:flumeRabbitMqPort,
					 flumeRabbitMqExchange:flumeRabbitMqExchange,
					 blockMessageCount:blockMessageCount,
					 timingMonitoring:timingMonitoring
 		 	},
            function(data){
            	var u=JSON.parse(data);
                alert("修改成功");
                location.reload();
            });	   
 	}
	
   
 
   
   </script>     