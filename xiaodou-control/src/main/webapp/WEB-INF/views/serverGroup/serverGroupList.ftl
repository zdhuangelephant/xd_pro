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

							 服务组管理

						</h3>

						<ul class="breadcrumb">

							<li>

								<i class="icon-home"></i>

								  服务组管理

							</li>


						</ul>

						<!-- END PAGE TITLE & BREADCRUMB-->

					</div>

				</div>

				<div class="btn-group">

					<button id="" class="btn green" onclick="addServerGroup()">

					 新增应用服务组 <i class="icon-plus"></i>
				
					</button>
					<button id="" class="btn red" onclick="addMiddleServerGroup()">

					 新增中间件服务组 <i class="icon-plus"></i>
				
					</button>
				</div>
				<!-- END PAGE HEADER-->
				<!-- BEGIN PAGE CONTENT-->

				<div class="row-fluid">

					<div class="span12">

						<!-- BEGIN EXAMPLE TABLE PORTLET-->

						<div class="portlet box blue">
 						<#list serverGroupTypeList as serverGroupType>
							<div class="portlet-title">

								<div class="caption"><i class="icon-edit"></i>${serverGroupType.serverGroupTypeName}</div>

							</div>

							<div class="portlet-body">

								<div class="clearfix">				
            <!-- /.row -->	
                            <table class="table table-striped table-hover table-bordered">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>服务组名称</th>
                                    <th>类型</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list serverGroupType.serverGroupList as serverGroup>
                                    <tr>
                                        <td>${serverGroup.groupId}</td>
                                        <td>${serverGroup.groupName}</td>
                                         <td><#if serverGroup.type==1>                     
	                                         		 应用服务
	                                        <#elseif serverGroup.type==2>
	                                       			中间件服务
                                            </#if>
                                        </td>
                                        <td><a style="cursor: pointer"
                                               onclick="serverGroupEdit('${serverGroup.groupId}','${serverGroup.groupName}','${serverGroup.groupType}','${serverGroup.groupServiceType}','${serverGroup.type}')">修改
                                            |</a>
                                            <a style="cursor: pointer"
                                               onclick="removeServerGroup('${serverGroup.groupId}')">删除</a>
                                        </td>
                                        </td>
                                    </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>
                   </#list>
                </div>
   
            <!-- /.row -->
            
                
                <div class="modal fade" style="height:800px; overflow:scroll;" id="serverGroupAdd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <form role="form" method="post" id="form2" action="/server/doAdd">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                            aria-hidden="true">×</span></button>
                                    <h4 class="modal-title" id="myModalLabel">新增ServerGroup</h4>
                                </div>
                                <input type="hidden" id="type" name="type">
                                <div class="modal-body">
                                    <div class="form-group">
                                        <label>请输入服务组名称</label>
                                        <input class="form-control" id="groupName" name="groupName">
                                    </div>
                                    <div class="form-group">
                                        <label>请选择服务组类型</label>
                                        <select id="groupType" class="form-control">
                                            <#list serverGroupTypeList as serverGroupType>
                                                <option value="${serverGroupType.serverGroupTypeId}">
                                                    ${serverGroupType.serverGroupTypeName}
                                                </option>
                                            </#list>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label>请选择服务组环境类型</label>
                                        <select class="form-control" name="groupServiceType" id="groupServiceType">
                                            <option value="1">Native</option>
                                            <option value="2">Online</option>
                                        </select>
                                    </div>
                                  
                                    <div class="form-group">
                                        <label>请选择关联的主机</label>
                                        <div class="checkbox">
                                            <table id="baseNodeTable" class="table table-hover">
                                                <thead>
                                                <tr>
                                                </tr>
                                                </thead>
                                            </table>
                                        </div>
                                    </div>
                                    
                                      <div class="form-group">
                                        <label>请选择关联的服务</label>
                                        <div class="checkbox">
                                            <table id="baseServerTable" class="table table-hover">
                                                <thead>
                                                <tr>
                                                </tr>
                                                </thead>
                                            </table>
                                        </div>
                                    </div>
                                    
                                    <div class="modal-footer">
                                        <button type="button" id="btn_submit" class="btn btn-primary"
                                                onclick="serverGroupSubmit()"></span>确定
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                
                <div class="modal fade" style="height:800px; overflow:scroll;" id="serverGroupEdit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                    aria-hidden="true">×</span></button>
                            <h4 class="modal-title" id="myModalLabel">修改ServerGroup</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <label>请输入服务组名称</label>
                                <input class="form-control" id="groupNameEdit" name="groupNameEdit">
                            </div>
                            <div class="form-group">
                                <label>请选择服务组类型</label>
                                <select id="groupTypeEdit" class="form-control">
                                    <#list serverGroupTypeList as serverGroupType>
                                        <option value="${serverGroupType.serverGroupTypeId}">
                                            ${serverGroupType.serverGroupTypeName}
                                        </option>
                                    </#list>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>请选择服务组环境类型</label>
                                <select class="form-control" name="groupServiceTypeEdit" id="groupServiceTypeEdit">
                                    <option value="1">Native</option>
                                    <option value="2">Online</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>请选择关联的主机</label>
                                <div class="checkbox">
                                    <table id="baseNodeTableEdit" class="table table-hover">
                                        <thead>
                                        <tr>
                                        </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                             <div class="form-group">
                                <label>请选择关联的服务</label>
                                <div class="checkbox">
                                    <table id="baseServerTableEdit" class="table table-hover">
                                        <thead>
                                        <tr>
                                        </tr>
                                        </thead>
                                    </table>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                <button type="button" onclick="doEidtserverGroup()" id="btn_submit"
                                        class="btn btn-primary" data-dismiss="modal"></span>修改
                                </button>
                            </div>
                        </div>
                    </div>
                </div>        
            <input type="hidden" id="groupId" name="groupId">              
   			 <input type="hidden" id="serverGroupTypeId" name="serverGroupTypeId">
			<!-- END PAGE CONTAINER-->
	 </div>          
                  
	<!-- END CONTAINER -->

<!-- END BODY -->
</body> 


            <script>
                function removeServerGroup(groupId) {
                    if (!confirm("确认要删除？")) {
                        window.event.returnValue = false;
                    } else {
                        $.post("/serverGroup/remove", {groupId: groupId},
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

                function addServerGroup() {
                    $.post("/serverGroup/baseData", {},
                            function (data) {
                                var u = JSON.parse(data);
                                var baseNodeList = u.baseNodeList;
                                var baseServerList = u.baseServerList;
                                $("#baseNodeTable tr").empty();
                                $("#baseServerTable tr").empty();
                                for (var i = 0; i < baseNodeList.length; i++) {
                                    var row = "<tr><td><input name='baseNodeCheckBox' type='checkbox'   value=" + baseNodeList[i].mac + ">" + baseNodeList[i].ip + "(" + baseNodeList[i].alias + ")" + "</td></tr>"
                                    $("#baseNodeTable tr:last").after(row);
                                }
                                for (var i = 0; i < baseServerList.length; i++) {
                                    var row = "<tr><td><input name='baseServerCheckBox' type='checkbox'   value=" + baseServerList[i].baseServerId + ">" + baseServerList[i].serverName + "</td></tr>"
                                    $("#baseServerTable tr:last").after(row);
                                }
                                $("#type").val("1");
                                $('#serverGroupAdd').modal();
                            });

                }      
                   
                 function addMiddleServerGroup() {
                    $.post("/serverGroup/baseData", {},
                            function (data) {
                                var u = JSON.parse(data);
                                var baseNodeList = u.baseNodeList;
                                var baseServerList = u.baseServerList;
                                $("#baseNodeTable tr").empty();
                                $("#baseServerTable tr").empty();
                                for (var i = 0; i < baseNodeList.length; i++) {
                                    var row = "<tr><td><input name='baseNodeCheckBox' type='checkbox'   value=" + baseNodeList[i].mac + ">" + baseNodeList[i].ip + "(" + baseNodeList[i].alias + ")" + "</td></tr>"
                                    $("#baseNodeTable tr:last").after(row);
                                }   
                                $("#type").val("2");
                                $('#serverGroupAdd').modal();
                            });

                }
                          
                function serverGroupSubmit() {
                    var groupName = $('#groupName').val();
                    var groupType = $('#groupType').val();
                    var groupServiceType = $('#groupServiceType').val();
                    var type=$("#type").val();
                    var baseServerNeedAdd = "";
                    $('input[name="baseServerCheckBox"]:checked').each(function () {
                        baseServerNeedAdd += $(this).val() + ";";
                    });

                    var baseNodeNeedAdd = "";
                    $('input[name="baseNodeCheckBox"]:checked').each(function () {
                        baseNodeNeedAdd += $(this).val() + ";";
                    });

                    if (groupName == "") {
                        alert("服务组名称不能为空");
                        return;
                    }
                    $.post("/serverGroup/doAdd", {
                                groupName: groupName,
                                groupType: groupType,
                                groupServiceType: groupServiceType,
                                baseNodeNeedAdd: baseNodeNeedAdd,
                                baseServerNeedAdd: baseServerNeedAdd,
                                type:type
                            },
                            function (data) {
                                var u = JSON.parse(data);
                                if (u.msg == "success") {
                                    alert("新增成功");
                                } else {
                                    alert(u.msg);
                                }
                                location.reload();
                            });
                }
                function serverGroupEdit(groupId, groupName, groupType,groupServiceTypeEdit,type) {
                    $("#groupId").val(groupId);
                    $("#groupNameEdit").val(groupName);
                    $("#groupTypeEdit").val(groupType);
                    $("#groupServiceTypeEdit").val(groupServiceTypeEdit);   
                    $.post("/serverGroup/baseData", {groupId:groupId},
                            function (data) {
                                var u = JSON.parse(data);
                                var baseNodeList = u.baseNodeList;
                                var baseServerList = u.baseServerList;
                                var serverList = u.serverList;
                                var nodeList = u.nodeList;
                                $("#baseNodeTableEdit tr").empty();
                                $("#baseServerTableEdit tr").empty();
                                for (var i = 0; i < baseNodeList.length; i++) {
                                    var flag="0";  
                                    for (var j = 0; j < nodeList.length; j++) {
                                        if (baseNodeList[i].mac == nodeList[j].mac) {
                                            flag = "1";
                                        }
                                    }
                                    if (flag == "1") {
                                        var row = "<tr><td><input name='baseNodeCheckBoxEdit' type='checkbox' preChecked='checked' checked value=" + baseNodeList[i].mac + ">" + baseNodeList[i].ip + "(" + baseNodeList[i].alias + ")" + "</td></tr>"
                                    } else {
                                        var row = "<tr><td><input name='baseNodeCheckBoxEdit' type='checkbox'   value=" + baseNodeList[i].mac + ">" + baseNodeList[i].ip + "(" + baseNodeList[i].alias + ")" + "</td></tr>"
                                    }
                                    $("#baseNodeTableEdit tr:last").after(row);
                                }
								if(type!="2"){
	                                for (var i = 0; i < baseServerList.length; i++) {
	                                	 var flag="0";  
	                                    for (var j = 0; j < serverList.length; j++) {
	                                        if (serverList[j].baseServerId == baseServerList[i].baseServerId) {
	                                            flag = "1";
	                                        }
	                                    }
	                                    if (flag == "1") {
	                                        var row = "<tr><td><input name='baseServerCheckBoxEdit' type='checkbox' preChecked='checked' checked value=" + baseServerList[i].baseServerId + ">" + baseServerList[i].serverName + "</td></tr>"
	                                    } else {
	                                        var row = "<tr><td><input name='baseServerCheckBoxEdit' type='checkbox'   value=" + baseServerList[i].baseServerId + ">" + baseServerList[i].serverName + "</td></tr>"
	                                    }
	
	                                    $("#baseServerTableEdit tr:last").after(row);
	                                }
	                           }
                                $('#serverGroupEdit').modal();
                            });
                }
                function doEidtserverGroup() {
                    var groupName = $('#groupNameEdit').val();
                    var groupType = $('#groupTypeEdit').val();
                    var groupId = $('#groupId').val();
                    var groupServiceType = $('#groupServiceTypeEdit').val();
                    if (groupName == "") {
                        alert("服务组名称不能为空");
                        return;
                    }
                    var baseServerNeedAdd = "";
                    var baseServerNeedDelete = "";
                    $(":checkbox[name='baseServerCheckBoxEdit']").each(function () {
                        var id = $(this).val();
                        var preChecked = $(this).attr("preChecked");
                        if ($(this).is(":checked")) {
                            if (preChecked != "checked") {
                                baseServerNeedAdd = baseServerNeedAdd + id + ";";
                            }
                        } else {
                            if (preChecked == "checked") {
                                baseServerNeedDelete = baseServerNeedDelete + id + ";";
                            }
                        }
                    });
                    var baseNodeNeedAdd = "";
                    var baseNodeNeedDelete = "";
                    $(":checkbox[name='baseNodeCheckBoxEdit']").each(function () {
                        var id = $(this).val();
                        var preChecked = $(this).attr("preChecked");
                        if ($(this).is(":checked")) {
                            if (preChecked != "checked") {
                                baseNodeNeedAdd = baseNodeNeedAdd + id + ";";
                            }
                        } else {
                            if (preChecked == "checked") {
                                baseNodeNeedDelete = baseNodeNeedDelete + id + ";";
                            }
                        }
                    });

                    $.post("/serverGroup/edit", {
                    			groupId:groupId,
                                groupName: groupName,
                                groupType: groupType,
                                groupServiceType: groupServiceType,
                                baseNodeNeedAdd: baseNodeNeedAdd,
                                baseNodeNeedDelete: baseNodeNeedDelete,
                                baseServerNeedAdd: baseServerNeedAdd,
                                baseServerNeedDelete: baseServerNeedDelete
                            },
                            function (data) {
                                var u = JSON.parse(data);
                                if (u.msg == "success") {
                                    alert("修改成功");
                                } else {
                                    alert(u.msg);
                                }
                                location.reload();
                            });
                }

            </script>
        </div>
    </div>
    </html>