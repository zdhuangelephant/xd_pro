            <input type="hidden" class="form-control" name="groupServiceType1" id="groupServiceType1" value="${groupServiceType}">
            <input type="hidden" class="form-control" name="groupType1" id="groupType1" value="${groupType}">

             <!-- BEGIN PAGE CONTENT-->
				<div class="row-fluid">
					<div class="span12">
   					 <!-- BEGIN EXAMPLE TABLE PORTLET-->
						<div class="portlet box blue">
							<div class="portlet-title">
								<div class="caption"><i class="icon-edit"></i>服务列表</div>
							</div>
							<div class="portlet-body">
								<div class="clearfix">
									<div class="btn-group">

										<button id="sample_editable_3_new" class="btn green" onclick="addServer()">

										关联新服务 <i class="icon-plus"></i>
									
										</button>
									</div>
		
								<table class="table table-striped table-hover table-bordered" >
									<thead>
                                    <tr>
                                    	<th  style="display:none">id</th>
                                        <th>服务名称</th>                                        
                                        <th>基础目录</th>  
                                        <th>tomcat端口号</th>                                     
                                        <th>War包地址</th>                                                           
                                        <th>SH命令执行用户</th>    
                                       	<th>操作</th>   
                                       	<th>Nginx配置</th>                       
                                    </tr>
                                </thead>
                                <tbody>
                                <#list serverList as server>
                                    <tr>
                                    	<td  style="display:none"></td>
                                        <td>${server.serverName}</td>
                                        <td>${server.baseDir}</td>
                                        <td>${server.tomcatPort}</td>   
                                        <td>${server.warAdress}</td>
                                        <td>${server.user}</td>
                                        <td>              		 
                                       		 <a style="cursor: pointer" href="/node/list?serverId=${server.serverId}&serverName=${server.serverName}&warAdress=${server.warAdress}&groupServiceType=${groupServiceType}"><img style="width:auto;height:40px;" src="${baseOP}/image/common/jiedian.png"></img></a>         		 
                                       		 <a style="cursor: pointer" onclick="addConf('${server.serverId}','${server.serverName}')"><img style="width:auto;height:40px;" src="${baseOP}/image/common/peizhi.png"></img></a>
                                       		 <a style="cursor: pointer" onclick="addRequestUrl('${server.serverId}','${server.requestUrl}')"><img style="width:auto;height:40px;" src="${baseOP}/image/common/hook.png"></img></a>
                                       		 <a style="cursor: pointer" onclick="delServer('${server.baseServerId}','${server.groupId}')"><img style="width:auto;height:40px;" src="${baseOP}/image/common/del.png"></img></a>
                                        </td>
                                        <td><a style="cursor: pointer" onclick="editServerNginxConf('${server.serverId}')">配置Nginx</a> 
                                        <a style="cursor: pointer" onclick="nginxServerGroup('${server.serverId}')">生效服务组</a>
                                        </td>
                                    </tr>                              
                                </#list>
                               </tbody>
                            </table>
                        </div>
                    </div>
                </div>
  <script>
	  //产生随机数函数
	function RndNum(n){
	    var rnd="";
	    for(var i=0;i<n;i++)
	        rnd+=Math.floor(Math.random()*10);
	    return rnd;
	}
     function getNodes(serverId,serverName,warAdress,groupServiceType){
        var tId=RndNum(11);
		addNewTabs({
			id:tId,title: serverName+'节点列表',close: true,url:'/node/list?serverId='+serverId+'&serverName='+serverName+'&warAdress='+warAdress+'&groupServiceType='+groupServiceType}
		);
	}
  
    function addRequestUrl(serverId,requestUrl){
  		$('#requestUrl').val(requestUrl);
  		$('#requestServerId').val(serverId);
  		$('#editServer').modal();
    }
   	
   	function editServer(){
   		var requestUrl=$('#requestUrl').val();
   		var serverId=$('#requestServerId').val();
   		$.post("/server/editServer", {serverId:serverId,requestUrl:requestUrl},
                    function(data){
                    	var u=JSON.parse(data);
                        alert("成功");
                        location.reload();
                    });	 
   	}
   
   	function addConf(serverId,serverName){
   	    var tId=RndNum(11);
		addNewTabs({
			id:tId,title: serverName+'配置文件管理',close: true,url:'/ftpFile/list?serverId='+serverId}
		);
	}
	function addServer(){
 		$('#serverAdd').modal();	
 	}
 	function serverSubmit(){
 		var baseServerId=$('#baseServerIdAdd').val();
 		if(baseServerId==null||baseServerId==""){
 			alert("基础服务不能为空");
 			return;
 		}
 		var groupId=$('#groupId').val();
 		 $.post("/serverGroupDetail/relationServer", {baseServerId:baseServerId,groupId:groupId},
                    function(data){
                    	var u=JSON.parse(data);
                        alert("关联成功");
                        location.reload();
                    });	   
 	}
 	
   function delServer(baseServerId,groupId){
     if (!confirm("确认要删除？")) {
            window.event.returnValue = false;
        }else{
    	$.post("/serverGroupDetail/delRelationServer", {baseServerId:baseServerId,groupId:groupId},
	                    function(data){	
	                    	location.reload();                    	
	                    });  
	       }
    }
    function confAdd(serverId){
    	$('#serverIdB').val(serverId);
 		$.post("/server/getFile", {serverId:serverId},
                    function(data){
                    	var u=JSON.parse(data);
                    	var fileArray=u.fileList;
						 var fileNameArray=u.fileNameList;
                    	$("#fileTable tr").empty();
                    	for(var j=0;j<fileNameArray.length;j++){
                    		var row="<tr><td><h4>文件:"+fileNameArray[j]+"</h4></td><td></td><td></td><td></td></tr>";  		
                    		$("#fileTable tr:last").after(row); 
	                    	for(var i=0;i<fileArray.length;i++){  
	                    		if(fileNameArray[j]==fileArray[i].uniqueFileName){
	                    	    	var strdate2 = timeStamp2String(fileArray[i].createTime);
	                    	    	if(fileArray[i].state=="1"){
										var row="<tr><td>"+fileArray[i].uniqueFileName+"</td><td>"+strdate2+"</td><td>使用中|删除</td><td><a href=/server/downFile?fileName="+fileArray[i].name+"&serverId="+serverId+">下载</a></td></tr>";
									}else{
										var row="<tr><td>"+fileArray[i].uniqueFileName+"</td><td>"+strdate2+"</td><td><a onclick=fileUse("+fileArray[i].fileId+")>立即使用</a>|<a onclick=fileDel("+fileArray[i].fileId+")>删除</a></td><td><a href=/server/downFile?fileName="+fileArray[i].name+"&serverId="+serverId+">下载</a></td></tr>";
									}
									$("#fileTable tr:last").after(row);  
								}
							}
						}
                        $('#addFile').modal(); 
                    });	  
 	}
function timeStamp2String (time){
        var datetime = new Date();
         datetime.setTime(time);
         var year = datetime.getFullYear();
         var month = datetime.getMonth() + 1;
         var date = datetime.getDate();
         var hour = datetime.getHours();
         var minute = datetime.getMinutes();
         var second = datetime.getSeconds();
         var mseconds = datetime.getMilliseconds();
         return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second+"."+mseconds;
};
 function editServerNginxConf(nginxServerId){
 	 $('#nginxServerId').val(nginxServerId);
 	 $.post("/nginx/getNginx", {nginxServerId:nginxServerId},
                    function(data){
                    	var u=JSON.parse(data);
                    	if(u.msg=='success'){
                    		if(u.nginx!=null){
		                    	$('#nginxServerName').val(u.nginx.nginxServerName);
		                    	$('#nginxListenPort').val(u.nginx.nginxListenPort);
		                    	$('#accessLog').val(u.nginx.accessLog);
		                    	$('#upstreamName').val(u.nginx.upstreamName);
		                    		if(u.nginx.ipHash=="1"){
		                    			$("#ipHash").attr("checked",'true');
		                    		}                    	
		                    }else{
		                    	$('#nginxServerName').val("");
		                    	$('#nginxListenPort').val("");
		                    	$('#accessLog').val("");
		                    	$('#upstreamName').val("");
		                    	$("#ipHash").attr("checked",'false');
		                    }
	                    	$('#NginxConf').modal();	
                    	}else{
                    		alert(u.msg);
                    		return;
                    	}
                    });	   
 }
 function editServerNginx(){	
    var nginxServerId=$('#nginxServerId').val();
    var ipHash=0;
    if($('#ipHash').is(':checked')) {
		ipHash=1;
	}
 	$.post("/nginx/addNginx", {nginxServerId:nginxServerId,
 								nginxServerName:$('#nginxServerName').val(),
 								nginxListenPort:$('#nginxListenPort').val(),
 								accessLog:$('#accessLog').val(),
 								upstreamName:$('#upstreamName').val(),
 								ipHash:ipHash},
                    function(data){
                    	var u=JSON.parse(data);
                    	if(u.msg=='success'){
	                    	alert("保存成功");
	                    	location.reload();
                    	}else{
                    		alert(u.msg);
                    		return;
                    	}
                    });	  
 }
 
 
 
  function nginxServerGroup(nginxServerId) {
      $('#nginxServerId').val(nginxServerId);
      $.post("/nginx/listWithNginxServerId", {nginxServerId:nginxServerId},
                    function(data){
	                    var u=JSON.parse(data);
                    	if(u.msg=='success'){   
                    		var groupList=u.groupList;
                    		var relationList=u.relationList;
                    		$("#nginxServerGroupTable tr").empty(); 	
	                    	for(var i=0;i<groupList.length;i++){
	                    	   var flag="0"; 
	                    	   if(relationList!=null){ 
		                    	   for(var j=0;j<relationList.length;j++){
		                    	   	   if(groupList[i].groupId==relationList[j].serverGroupId)
		                    	   	   	flag="1";
		                    	   	 }
		                    	}
	                    	   if(flag=="1"){
								 var row="<tr><td><input name='groupid' type='checkbox' preChecked='checked' checked value="+groupList[i].groupId+"> "+groupList[i].groupName+"</td></tr>"
								}else{
								 var row="<tr><td><input name='groupid' type='checkbox'  value="+groupList[i].groupId+"> "+groupList[i].groupName+"</td></tr>"
								}
								$("#nginxServerGroupTable tr:last").after(row);  
							}
							$('#nginxServerGroupModel').modal();	
                    	}else{
                    		alert(u.msg);
                    		return;
                    	}             	
                    	
                    });	   
    }
 
       
  function editNginxServerGroup(){
  		var nginxServerId=$('#nginxServerId').val();
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
 		 $.post("/nginx/editNginxServerGroup", {nginxServerId:nginxServerId,needAddId:needAddId,needDeleteId:needDeleteId},
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
 
 
 
 function eidtServerById(){
 		var serverId=$('#serverId').val();
 		var serverName=$('#serverName').val();
 		var warAdress=$('#warAdress').val();
 		var baseDir=$('#baseDir').val();	
 		var tomcatPort=$('#tomcatPort').val();	
 		var user=$('#user').val();	
 		 $.post("/server/eidtServerById", {serverId:serverId,serverName:serverName,warAdress:warAdress,baseDir:baseDir,tomcatPort:tomcatPort,user:user},
                    function(data){
                    	var u=JSON.parse(data);
                        alert("修改成功");
                        location.reload();
                    });	   
 	}

 	
    function getNodeList(serverId,serverName){
	    	$.post("/node/list", {serverId:serverId,serverName:serverName},
	                    function(data){
	                    	
	                    });
    }

    function uploadFile(){
	   $.ajax({
	    url: '/server/addFile',
	    type: 'POST',
	    cache: false,
	    data: new FormData($('#form1')[0]),
	    processData: false,
	    contentType: false
	}).done(function(res) {
		   alert("上传成功");
		   location.reload();
	}).fail(function(res) {});
			alert(res);
	}
	 function delGroup(groupId){
     if (!confirm("确认要删除？")) {
            window.event.returnValue = false;
        }else{
    	$.post("/serverGroup/del", {groupId:groupId},
	                    function(data){	 
	                    	 alert("删除成功");
                            window.location.href="/"          	                 	
	                    });  
	        }
    }
    
    function fileUse(fileId){
    	$.post("/server/fileUse", {fileId:fileId},
	                    function(data){	 
	                    	 alert("使用成功");    	                 	
	                    });  
    }
    function fileDel(fileId){
    	if (!confirm("确认要删除？")) {
            window.event.returnValue = false;
        }else{
    	$.post("/server/fileDel", {fileId:fileId},
	                    function(data){	 
	                    	 alert("删除成功");    	                 	
	                    });  
	    }
    }
   </script>     