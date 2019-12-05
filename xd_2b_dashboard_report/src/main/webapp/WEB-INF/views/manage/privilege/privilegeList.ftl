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

<body class="page-content">

<section class="content-header">
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-circle-o"></i>人员管理</a></li>
            <li class="active">账号管理</li>
        </ol>
     </section>

    <section class="table-well">
        <div class="row">
            <div class="col-md-12">
                <!-- BEGIN SAMPLE TABLE PORTLET-->
                <div class="box">
                    <div class="box-body">
                 
        <div class="box box-success box-solid" style="margin-bottom: 0px;">
            <div class="box-header">
                <h3 class="box-title">权限列表</h3>
                <!-- /.box-tools -->
	    		<div class="box-tools pull-right modal-open">
	            	<a class="btn btn-success" onclick="order();">排序</a>
	    			<a class="btn btn-success" data-toggle="modal" data-target="#save">添加</a>
	            </div>
            </div>
        </div>   
        
    <table id="dnd-example" class="table table-striped table-bordered table-hover treetable">
        <thead>
        <tr>
            <th >权限名</th>
            <th >排序</th>
            <th >是否显示</th>
            <th >icon</th>
            <th style="width: 300px;">操作</th>
        </tr>
        </thead>
        <tbody>
        ${tree}
        </tbody>
    </table>
    
    				</div>
                </div>
                <!-- END SAMPLE TABLE PORTLET-->
            </div>
        </div>
    </section>
<!-- 模态框（Modal） -->
<#include "/manage/privilege/savePrivilege.ftl"/>
<#include "/manage/privilege/updatePrivilege.ftl"/>
<#include "/manage/privilege/savePrivilegeNode.ftl">

<script src="${baseOP}/content/ui/global/jQuery/jquery.min.js"></script>
<script src="${baseJsOP}/jqueryTableTreeView/jquery.treetable.js"></script>
 <!-- Bootstrap 3.3.6 -->
<script src="${baseOP}/content/ui/global/bootstrap/js/bootstrap.min.js"></script>
<script src="${baseOP}/content/min/js/supershopui.common.js"></script>
<script src="${baseOP}/content/plugins/bootstrap-table/bootstrap-table.js"></script>
<script src="${baseOP}/content/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script src="${baseOP}/js/common.js?${timeStamp}"></script>
<script src="${baseOP}/js/manage/privilege.js?${timeStamp}"></script>
<script type="text/javascript">
	$(document).ready(function () {
	    $("#dnd-example").treetable({
	        expandable: true
	    });
	});
</script>
    </body>
</html>

