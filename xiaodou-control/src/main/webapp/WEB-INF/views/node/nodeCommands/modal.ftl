
    <!-- /.row -->		  
		    <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		    <div class="modal-dialog" role="document">
		      <div class="modal-content">
		        <div class="modal-header">
		          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
		          <h4 class="modal-title" id="myModalLabel">选择要部署的版本</h4>
		        </div>
		        <div class="modal-body">				     
		          <div class="form-group">					           
                    <label>version</label>
                    <input type="hidden" id="curMac" >
                    <select id="version" class="form-control">
                    	 <#list fileList as file>   
                       	 <option value="${file.name}">${file.name}</option>                                                   
                         </#list>
                    </select>
		          </div>					          
		        </div>
		        <div class="modal-footer">
		          <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		          <button type="button" onclick="addCommand()" id="btn_submit" class="btn btn-primary" data-dismiss="modal"></span>新增</button>
		        </div>
		      </div>
		    </div>
		  </div>
		  
		     <div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		    <div class="modal-dialog" role="document">
		      <div class="modal-content">
		        <div class="modal-header">
		          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
		          <h4 class="modal-title" id="myModalLabel">选择要生成发行版的版本</h4>
		        </div>
		        <div class="modal-body">				     
		          <div class="form-group">					           
                    <label>version</label>
                    <select id="releaseVersion" class="form-control">
                    	 <#list fileListNoRelease as file>   
                       	 <option value="${file.name}">${file.name}</option>                                                   
                         </#list>
                    </select>
		          </div>					          
		        </div>
		        <div class="modal-footer">
		          <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		          <button type="button" onclick="createReleaseVersion()" id="btn_submit" class="btn btn-primary" data-dismiss="modal"></span>生成</button>
		        </div>
		      </div>
		    </div>
		  </div>