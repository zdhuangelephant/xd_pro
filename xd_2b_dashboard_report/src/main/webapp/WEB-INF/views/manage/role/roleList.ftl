<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
    <!--<![endif]-->
    <!-- BEGIN HEAD -->
    <head>
        <meta charset="utf-8" />
        <#include "/common/title.ftl"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />
        <!-- Bootstrap 3.3.6 -->
        <link rel="stylesheet" href="${baseOP}/content/ui/global/bootstrap/css/bootstrap.min.css">
        <!-- Font Awesome -->
        <link href="${baseOP}/content/ui/global/font-awesome/css/font-awesome.css" rel="stylesheet" />
        <!-- Theme style -->
        <link rel="stylesheet" href="${baseOP}/content/adminlte/dist/css/AdminLTE.css">
        <link rel="stylesheet" href="${baseOP}/content/adminlte/dist/css/skins/_all-skins.min.css">
        <link href="${baseOP}/content/min/css/supershopui.common.min.css" rel="stylesheet"/>
        <link href="${baseOP}/content/plugins/bootstrap-table/bootstrap-table.css" rel="stylesheet" />
        <link href="${baseOP}/content/ui/global/bootstrap-switch/css/bootstrap-switch.css" rel="stylesheet" />
        <link rel="stylesheet" href="${baseOP}/css/dashboard.css?1">
        <link href="${baseJsOP}/jqueryTableTreeView/jquery.treetable.css" rel="stylesheet" type="text/css" />
		<link href="${baseJsOP}/jqueryTableTreeView/jquery.treetable.theme.default.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
    <section class="content-header">
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-circle-o"></i>人员管理</a></li>
            <li class="active">角色管理</li>
        </ol>
     </section>
    <!-- Main content -->
    <section class="table-well">
        <div class="row">
            <div class="col-md-12">
                <!-- BEGIN SAMPLE TABLE PORTLET-->
                <div class="box">
                    <div class="box-body">
						
        <div class="box box-success box-solid" style="margin-bottom: 0px;">
            <div class="box-header">
                <h3 class="box-title">角色列表</h3>
                <!-- /.box-tools -->
	    		<div class="box-tools pull-right modal-open">
	            	<button class="btn btn-success" data-toggle="modal" data-target="#save">添加</button>
	            </div>
            </div>
        </div>                   
                        <table class="table-scrollable table table-hover" id="table"
                               data-toggle="table"
                               data-pagination="true"
                               data-advanced-search="true"
                               data-id-table="advancedTable"
                               data-row-style="rowStyle">
                            <thead>
                                <tr>
                                	<th class="text-center" data-sortable="true">序号</th>
									<th class="text-center">ID</th>
                                	<th class="text-center">角色名称</th>
									<th class="text-center">角色描述</th>
									<th class="text-center">状态</th>
									<th class="text-center">操作</th>
                                </tr>
                            </thead>
                            <tbody>
								<#if listRole??>
								<#list listRole as role>
								<#if role??>
									<tr>
										<td class="text-center">${role_index+1}</td>
										<td class="text-center">${role.id}</td>
										<td class="text-center">${role.roleName}</td>
										<td class="text-center">${role.description}</td>
										<td class="text-center">
										<#if role.validStatus == 0>
											有效
										<#elseif role.validStatus == 1>
											无效
										</#if>
										</td>
										<td class="text-center">
										<button class="btn btn-info" data-toggle="modal" data-target="#set" data-sid="${role.id}" >
										权限设置</button>
										<a href="javascript:;" class="btn btn-info" onclick="unitList(${role.id});">
										单位管理</a>
										<button class="btn btn-info" data-toggle="modal" data-target="#update" data-sid="${role.id}" data-role_name="${role.roleName}" data-description="${role.description}" data-valid_status="${role.validStatus}">修改</button>
										<#if role.id != 4>
										<a href="javascript:;" class="btn btn-info" onclick="removeRole(${role.id});">
										删除</a>
										</#if>
										</td>
									</tr>
								</#if>
								</#list>
								</#if>
								</tbody>
						</table>
                    </div>
                </div>
                <!-- END SAMPLE TABLE PORTLET-->
            </div>
        </div>
    </section>
    
<!-- 模态框（Modal） -->
<#include "/manage/role/saveRole.ftl"/>
<#include "/manage/role/updateRole.ftl"/>
<#include "/manage/role/setRole.ftl"/>
        <script src="${baseOP}/content/ui/global/jQuery/jquery.min.js"></script>
        <script src="${baseJsOP}/jqueryTableTreeView/jquery.treetable.js"></script>
        <!-- Bootstrap 3.3.6 -->
        <script src="${baseOP}/content/ui/global/bootstrap/js/bootstrap.min.js"></script>
        <script src="${baseOP}/content/min/js/supershopui.common.js"></script>
        <script src="${baseOP}/content/plugins/bootstrap-table/bootstrap-table.js"></script>
        <script src="${baseOP}/content/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
        <script src="${baseOP}/js/common.js?${timeStamp}"></script>
        <script src="${baseOP}/js/manage/role.js?${timeStamp}"></script>
        <script type="text/javascript">
		    $(document).ready(function () {
		        $("#dnd-example").treetable({
		            expandable: true
		        });
		        checkChange();
		    });
		</script>
    </body>
</html>