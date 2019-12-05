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

							配置文件列表

						</h3>

						<ul class="breadcrumb">

							<li>

								<i class="icon-home"></i>

								配置文件列表


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

								<div class="caption"><i class="icon-edit"></i>配置文件列表</div>

							</div>

							<div class="portlet-body">

								<div class="clearfix">

									<div class="btn-group">

										<button id="sample_editable_3_new" class="btn green">
											<a name="mainFile" href="/ftpFile/downAllFile?serverId=${serverId}">
												下载所有正在使用的配置文件		
											</a>	
										</button>
									</div>
									<div style="float:right" class="btn-group">
 										<form role="form" method="post" id="form1"    enctype="multipart/form-data">
										<input type="hidden" id="serverId" name="serverId" value="${serverId}">	
										<label>请上传配置文件:</label> <input name="file" id="file" class="file" type="file" multiple data-min-file-count="1">
										<button  class="btn green" id="btn_submit" data-dismiss="addFile" onclick="uploadFile()">确定<i class="icon-plus"></i>
										</form>
										</button>
									</div>
								<table class="table table-striped table-hover table-bordered">
                                <thead>
                                <tr>
                                    <th>文件名</th>
                                    <th>当前使用的文件真实文件名</th>
                                    <th>创建人</th>
                                    <th>创建时间</th>
                                    <th>历史操作日志</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list ftpFileUseList as ftp>
                                    <tr style="color:red">
                                        <td>${ftp.uniqueFileName}</td>
                                        <td>${ftp.ftpFile.fileName}</td>
                                        <td>${ftp.ftpFile.userName}</td>
                                        <td>${ftp.ftpFile.createTime}</td>
                                        <td><a style="cursor: pointer" onclick="getMsg(this)">查看使用日志</a> </td>   
                                        <td><a name="mainFile" href="/ftpFile/downFile?fileId=${ftp.ftpFile.fileId}">下载</a></td>
                                   	    <td style="display:none">${ftp.ftpFile.msg} </td> 
                                    </tr> 
                                    <#list ftp.ftpFileList as ftpFile>
                                       <#if ftpFile.state==1>       
                                    	<tr style="color:green"> 
                                        <td></td>
                                        <td>${ftpFile.fileName}</td>
                                        <td>${ftpFile.userName}</td>
                                        <td>${ftpFile.createTime}</td>
                                        <td><a style="cursor: pointer" onclick="getMsg(this)">查看使用日志</a> </td>   
                                        <td>     
                                        	<a style="cursor: pointer" onclick="useFile('${ftpFile.fileId}')">使用</a>              
	                                        <a style="cursor: pointer" onclick="delFile('${ftpFile.fileId}')">删除</a>  
	                                        <a href="/ftpFile/downFile?fileId=${ftpFile.fileId}">下载</a>
                                        </td>  
                                        <td style="display:none">${ftpFile.msg} </td> 
                                   		</tr>
                                    <#else>
                                     	<tr > 
                                        <td></td>
                                        <td>${ftpFile.fileName}</td>
                                        <td>${ftpFile.userName}</td>
                                        <td>${ftpFile.createTime}</td>
                                        <td><a style="cursor: pointer" onclick="getMsg(this)">查看使用日志</a> </td>   
                                        <td>                  
	                                        <a style="cursor: pointer" onclick="useFile('${ftpFile.fileId}')">使用</a> 
                                            <a style="cursor: pointer" onclick="delFile('${ftpFile.fileId}')">删除</a>    
                                      	 	<a href="/ftpFile/downFile?fileId=${ftpFile.fileId}">下载</a> 
                                        </td>  
                                        <td style="display:none">${ftpFile.msg} </td> 
                                   		</tr>
                                        </#if>   
                                     </#list>
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
                                <textarea id="msg"  class="comments" rows=25   name=s1   cols=75   onpropertychange= "this.style.posHeight=this.scrollHeight"></textarea>
					          </div>					          
					        </div>
					        <div class="modal-footer">
					          <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					        </div>
					      </div>
					    </div>
					  </div>
                
            <!-- /.row -->          
               </div>
                <!-- /.container-fluid -->
            </div>
            <!-- /#page-wrapper -->
              </div>
            <!-- /#wrapper -->
            </body>
            <script>
              function useFile(fileId){
              		if (!confirm("确认要使用此配置文件？")) {
                        window.event.returnValue = false;
                    } else {
                        $.post("/ftpFile/useFile", {fileId: fileId},
                                function (data) {
                                    var u = JSON.parse(data);
                                    if (u.msg == "success") {
                                        alert("使用成功");
                                    } else {
                                        alert(u.msg);
                                    }
                                    location.reload();
                                });
                    }
              }
              function delFile(fileId){
              	 if (!confirm("确认要删除？")) {
                        window.event.returnValue = false;
                    } else {
                        $.post("/ftpFile/delFile", {fileId: fileId},
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
                function uploadFile(){
				   $.ajax({
				    url: '/ftpFile/addFile',
				    type: 'POST',
				    cache: false,
				    data: new FormData($('#form1')[0]),
				    processData: false,
				    contentType: false,
				    success:function(data){
                    	alert("上传成功");
                    	location.reload();
	                },
	                error:function(e){
	                    location.reload();
	                }
					})
				}
				
				
				
				function downAllFile(){
					var aElements=document.getElementsByName("mainFile");  
					for(var i=0;i<aElements.length;i++){  
			            aElements[i].click();
			            sleep(500); 
			        }  	
				}
				function sleep(d){
  					for(var t = Date.now();Date.now() - t <= d;);
				}
				function getMsg(r){
		 			$('#msg').val(r.parentNode.parentNode.children[6].innerHTML)
		 			$('#myModal').modal();
		 		} 
				
            </script>
        </div>
    </div>
    </html>