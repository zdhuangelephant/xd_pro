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
        <style>
			.unitPortrait
			{
				width: 156px;
				height: 60px;
			}
		</style>
    </head>
    <body>
    <section class="content-header">
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-warning"></i>人员管理</a></li>
            <li class="active">单位管理</li>
        </ol>
     </section>
     <section class="form-well">
		<div class="row">
			<form id="searchForm" method="post" action="/manage/unit_list">
				<div class="row" style="margin-bottom: 10px;">
					<div class="col-sm-2">
						<input id="role" type="hidden" value="${unitDO.role}"/>
						<select id="role1" name="role" class="filter-status form-control">
							<option value="">全部角色</option>
							<#list listRole as role>
								<option value="${role.id}">${role.roleName}</option>
							</#list>
						</select>
					</div>
					<div class="col-sm-2">
						<input value="${unitDO.unitName}" name="unitName" class="form-control"  placeholder="请输入单位名称"
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
                <h3 class="box-title">单位列表</h3>
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
                                	<th class="text-center">单位名称</th>
                                	<th class="text-center">单位头像</th>
									<th class="text-center">单位角色</th>
									<th class="text-center">操作</th>
                                </tr>
                            </thead>
                            <tbody>
								<#if listUnit??>
								<#list listUnit as unit>
								<#if unit??>
									<tr>
										<td class="text-center">${unit_index+1}</td>
										<td class="text-center">${unit.id}</td>
										<td class="text-center">${unit.unitName}</td>
										<td class="text-center">
											<img width="50px" height="auto" src="${unit.unitPortrait}"></img>
										</td>
										<td class="text-center">${unit.roleName}</td>
										<td class="text-center">
										<!--<a href="javascript:;" class="btn btn-info" onclick="rawDataList(${alarm.id});">
										权限设置</a>-->
										<#if unit.role==2>
										<button class="btn btn-info" data-toggle="modal" data-target="#saveRelate" data-sid="${unit.id}">关联专业</button>
										</#if>
										<a href="javascript:;" class="btn btn-info" onclick="adminList(${unit.id});">
										账号管理</a>
										<button class="btn btn-info" data-toggle="modal" data-target="#update" data-sid="${unit.id}" data-unit_name="${unit.unitName}" data-role="${unit.role}" data-unit_portrait="${unit.unitPortrait}">修改</button>
										<a href="javascript:;" class="btn btn-info" onclick="removeUnit(${unit.id});">
										删除</a>
										</td>
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
<#include "/manage/unit/saveUnit.ftl"/>
<#include "/manage/unit/updateUnit.ftl"/>
<#include "/manage/unit/relateMajor.ftl"/>
<#include "/common/form/file.ftl"> 
        <script src="${baseOP}/content/ui/global/jQuery/jquery.min.js"></script>
        <!-- Bootstrap 3.3.6 -->
        <script src="${baseOP}/content/ui/global/bootstrap/js/bootstrap.min.js"></script>
        <script src="${baseOP}/content/min/js/supershopui.common.js"></script>
        <script src="${baseOP}/content/plugins/bootstrap-table/bootstrap-table.js"></script>
        <script src="${baseOP}/content/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
    	<script src="${baseOP}/content/ui/global/bootstrap-switch/js/bootstrap-switch.js"></script>
        <script src="${baseOP}/js/common.js?${timeStamp}"></script>
        <script src="${baseOP}/js/manage/unit.js?1${timeStamp}"></script>
        
        <script>
			function uploaD(id,scope,size,type){
				fileUploadCallBack(function(url) {
							$("#"+id).attr("src",url);
							$("#callback_"+id).val(url);
						},scope,size,type);
			}
		</script>
		<@fileUpload></@fileUpload>
    </body>
</html>