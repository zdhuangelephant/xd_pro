		   <div class="modal fade" id="addFile" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					    <div class="modal-dialog" role="document">
					  <form role="form" method="post" id="form1"    enctype="multipart/form-data">
					      <div class="modal-content">
					        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					          <h4 class="modal-title" id="myModalLabel">上传配置文件</h4>
	     			        </div>				         
					     	 <input type="hidden" class="form-control" name="serverIdB" id="serverIdB">
					        <div class="modal-body">	
					         <div class="form-group">
                                	    <table id="fileTable" class="table table-hover">
                                <thead>
                                    <tr>                         
                                        <th>文件名称</th>                                  
                                        <th>创建时间</th>      
                                        <th>操作</th>                  
                                    </tr>
                                	</thead>                              
									</table>
                                </div>		     
						       <div class="form-group">
				             <label>请上传配置文件</label>  
				                <input name="file" id="file" class="file" type="file" multiple data-min-file-count="1">
				             </div>  		                                                  				          
					        </div>
					        <div class="modal-footer">	   
					          <button type="button" onclick="uploadFile()" id="btn_submit" class="btn btn-primary" data-dismiss="addFile"></span>确定</button>		
					        </div>
					          <input type="hidden" class="form-control" value="${groupId}" id="groupId" name="groupId">     
                              <input type="hidden" class="form-control" value="${groupName}" name="groupName" name="groupName">                                                        
					      </div>
					        </form>       
					    </div>
					  </div>
						
						   <!-- /.row -->
            	
					  
					  <div class="modal fade" id="serverAdd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					    <div class="modal-dialog" role="document">
					      
					      <div class="modal-content">
					      	 <form role="form" method="post" id="form2"  action="/server/doAdd"  >
					        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					          <h4 class="modal-title" id="myModalLabel">关联基础服务</h4>
					        </div>
					       <div class="modal-body">				     
					          <div class="form-group">					           
                                <label>基础服务名称</label>
                                <select id="baseServerIdAdd" class="form-control">
                                	 <#list baseServerList as baseServer>   
                                     	 <option value="${baseServer.baseServerId}">${baseServer.serverName}</option>                                                   
                                     </#list>
                                </select>
					          </div>					          
					        </div>
					         <div class="modal-footer">	   
					          <button type="button"  id="btn_submit" class="btn btn-primary" onclick="serverSubmit()"></span>确定</button>		
					        </div>
					        </form>
					      </div>     
					   </div>
				      </div>
		           </div>
	
	
					  
					 <div class="modal fade" id="host" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					    <div class="modal-dialog" role="document">
					      <div class="modal-content">
					        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					          <h4 class="modal-title" id="myModalLabel">服务新增Host</h4>
					        </div>
					        <div class="modal-body">				     
					          <div class="form-group">					           
                                <label>Host</label>
                                <select id="mac" class="form-control">
                                	 <#list baseNodeList as baseNode>   
                                     	 <option value="${baseNode.mac}">${baseNode.ip}(${baseNode.alias})</option>                                                   
                                     </#list>
                                </select>
					          </div>					          
					        </div>
					        <div class="modal-footer">
					          <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					          <button type="button" onclick="hostAdd()" id="btn_submit" class="btn btn-primary" data-dismiss="modal"></span>新增</button>
					        </div>
					      </div>
					    </div>
					  </div>
					  
					  
					    <div class="modal fade" id="NginxConf" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					    <div class="modal-dialog" role="document">
					      <div class="modal-content">
					       <form role="form" method="post" id="form2"  action="/nginx/saveNginx"  >
					        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					          <h4 class="modal-title" id="myModalLabel">Nginx配置</h4>
					        </div>
					        <div class="modal-body">				     
						         <div class="form-group">
						         <input type="hidden" class="form-control" name="nginxServerId" id="nginxServerId" value="${nginxServerId}">
	                                <label>监控地址</label>
	                                <input class="form-control" name="nginxServerName" id="nginxServerName" value="${nginxServerName}">
	                            </div> 
	 							<div class="form-group">    
	                                <label>监控端口号</label>
	                                <input class="form-control" name="nginxListenPort" id="nginxListenPort" value="${nginxListenPort}">
	                            </div>  
	                            <div class="form-group">
	                                <label>日志</label>
	                                <input class="form-control" name="accessLog" id="accessLog" value="${accessLog}">
	                            </div> 
 								<div class="form-group">
	                                <label>upstreamName</label>
	                                <input class="form-control" name="upstreamName" id="upstreamName" value="${upstreamName}">
	                            </div>  
	                            <div class="form-group">
	                                <label>负载均衡策略是否为ip_hash(不选则指定权重或轮询策略生效)</label>
	                                <input type="checkbox" class="form-control" name="ipHash" id="ipHash">
	                            </div>                                                       				          
					        </div>
					        <div class="modal-footer">
					          <button type="button"  id="btn_submit" class="btn btn-primary" onclick="editServerNginx()" data-dismiss="editServerNginx"></span>保存</button>
					        </div>
					        </form>
					      </div>
					    </div>
					  </div>
					  
					  
					   <div class="modal fade" id="nginxServerGroupModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
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
                                	    <table id="nginxServerGroupTable" class="table table-hover">
                                <thead>
                                    <tr>                                          
                                    </tr>
                                	</thead>                              
									</table>
                                </div>		     
						       <div class="form-group">    		                                                  				          
					        </div>
					        <div class="modal-footer">	   
					          <button type="button" onclick="editNginxServerGroup()" id="btn_submit" class="btn btn-primary" data-dismiss="addFile"></span>确定</button>		
					        </div>
					      </div>
					        </form>       
					    </div>
					  </div>
					 </div>
					 
					 
					 
					  <div class="modal fade" id="editServer" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
					    <div class="modal-dialog" role="document">
					      <div class="modal-content">
					       <form role="form" method="post" id="form2"  action="/nginx/saveNginx"  >
					        <div class="modal-header">
					          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
					          <h4 class="modal-title" id="myModalLabel">配置后置HTTP请求</h4>
					        </div>
					        <input type="hidden" class="form-control" name="requestServerId" id="requestServerId">
					        <div class="modal-body">				     
						         <div class="form-group">
	                                <label>请求地址(多个请求地址用";"分隔)</label>
	                                <input class="form-control" name="" id="requestUrl" value="${requestUrl}">
	                            </div> 		                                            				          
					        </div>
					        <div class="modal-footer">
					          <button type="button"  id="btn_submit" class="btn btn-primary" onclick="editServer()" data-dismiss="editServerNginx"></span>保存</button>
					        </div>
					        </form>
					      </div>
					    </div>
					  </div>