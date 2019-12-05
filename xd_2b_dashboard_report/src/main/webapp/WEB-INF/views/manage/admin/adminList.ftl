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
    </head>
    <body>
    <section class="content-header">
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-circle-o"></i>人员管理</a></li>
            <li class="active">账号管理</li>
        </ol>
     </section>
     <section class="form-well">
		<div class="row">
			<form id="searchForm" method="post" action="/manage/admin_list">
				<input id="pageNo" name="pageNo" type="hidden" value="1"/>
					<div class="row" style="margin-bottom: 10px;">
						<div class="col-sm-2">
							<input id="role" type="hidden" value="${adminDO.role}"/>
							<select id="role1" name="role" class="filter-status form-control">
								<option value="">全部角色</option>
								<#list listRole as role>
									<option value="${role.id}">${role.roleName}</option>
								</#list>
							</select>
						</div>
						<div class="col-sm-2">
							<input id="unitId" type="hidden" value="${adminDO.unitId}"/>
							<select id="unitId1" name="unitId" class="filter-status form-control">
								<option value="">全部单位</option>
								<#list listUnit as unit>
									<option value="${unit.id}">${unit.unitName}</option>
								</#list>
							</select>
						</div>
						
						<div class="col-sm-2">
							<input value="${adminDO.userName}" name="userName"  class="form-control" placeholder="请输入用户名"
								type="text">
						</div>
					</div>
					<div class="row" style="margin-bottom: 10px;">
						<div class="col-sm-2">
							<input value="${adminDO.realName}" name="realName"  class="form-control" placeholder="请输入姓名"
								type="text">
						</div>
						<div class="col-sm-2">
							<input value="${adminDO.email}" name="email"  class="form-control" placeholder="请输入邮箱"
								type="text">
						</div>
						<div class="col-sm-2">
							<input value="${adminDO.telephone}" name="telephone"  class="form-control" placeholder="输入手机号"
								type="text">
						</div>
						<div class="col-sm-2">
							<button type="submit" class="btn btn-default">
								<span class="entypo-search"></span>&nbsp;&nbsp;搜索
							</button>
						</div>
					</div>
				</form>
		</div>
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
                <h3 class="box-title">账号列表</h3>
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
                                	<th class="text-center">用户名</th>
                                	<th class="text-center">所属单位</th>
									<th class="text-center">姓名</th>
									<th class="text-center">邮箱</th>
									<th class="text-center">手机号</th>
									<th class="text-center">操作</th>
                                </tr>
                            </thead>
                            <tbody>
								<#if listAdmin??>
								<#list listAdmin as admin>
								<#if admin??>
									<tr>
										<td class="text-center">${admin_index+1}</td>
										<td class="text-center">${admin.id}</td>
										<td class="text-center">${admin.userName}</td>
										<td class="text-center">${admin.unitName}</td>
										<td class="text-center">${admin.realName}</td>
										<td class="text-center">${admin.email}</td>
										<td class="text-center">${admin.telephone}</td>
										<td class="text-center">
										<!--<a href="javascript:;" class="btn btn-info" onclick="rawDataList(${alarm.id});">
										权限设置</a>-->
										<button class="btn btn-info" data-toggle="modal" data-target="#update" 
											data-sid="${admin.id}" data-user_name="${admin.userName}" data-password="${admin.password}" 
											data-unit_id="${admin.unitId}" data-real_name="${admin.realName}"
											data-email="${admin.email}" data-telephone="${admin.telephone}" data-child_role=${admin.childRole}>修改</button>
										<a href="javascript:;" class="btn btn-info" onclick="removeAdmin(${admin.id});">删除</a>
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
<#include "/manage/admin/saveAdmin.ftl"/>
<#include "/manage/admin/updateAdmin.ftl"/>
        <script src="${baseOP}/content/ui/global/jQuery/jquery.min.js"></script>
        <!-- Bootstrap 3.3.6 -->
        <script src="${baseOP}/content/ui/global/bootstrap/js/bootstrap.min.js"></script>
        <script src="${baseOP}/content/min/js/supershopui.common.js"></script>
        <script src="${baseOP}/content/plugins/bootstrap-table/bootstrap-table.js"></script>
        <script src="${baseOP}/content/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
    	<script src="${baseOP}/content/ui/global/bootstrap-switch/js/bootstrap-switch.js"></script>
        <script src="${baseOP}/js/common.js?${timeStamp}"></script>
        <script src="${baseOP}/js/manage/admin.js?1341${timeStamp}"></script>
    </body>
</html>