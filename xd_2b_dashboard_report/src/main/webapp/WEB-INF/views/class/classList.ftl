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
        <link href="${baseOP}/css/dashboard.css?1" rel="stylesheet" />
    </head>
    <body>
    <section class="content-header">
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-users"></i>学籍管理</a></li>
                <li class="active">班级管理</li>
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
                <h3 class="box-title">班级列表</h3>
                <!-- /.box-tools -->
                <#if adminUser.childRole !=1>
                <div class="box-tools pull-right modal-open">
                	<button class="btn btn-success pull-right" data-toggle="modal" data-target="#save">添加班级</button>
                </div>
                </#if>
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
                                    <th data-field="idx" data-sortable="true">序号</th>
                                    <th data-field="className" data-sortable="false">班级名称</th>
                                    <th data-field="description" data-sortable="false">备注</th>
                                    <th data-field="studentCount" data-sortable="false">学生数</th>
                                    <th data-field="state" data-sortable="false">操作</th>
                                </tr>
                            </thead>
                            <tbody>
								<#list listClass as class>
								<#if class??>
						        <tr>
						        	<td class="text-center">${class_index+1}</td>
									<td>${class.className}</td>
									<td>${class.description}</td>
									<td class="text-center">${class.studentCount}</td>
									<td>
										<#if adminUser.childRole !=1>
											<button data-toggle="modal" data-target="#update" data-sid="${class.id}" data-classname="${class.className}" data-description="${class.description}" data-student_count="${class.studentCount}" type="button" class="btn btn-info">编辑</button>
											<#if class.studentCount?is_number && class.studentCount == 0>
												<button onclick="removeClass(${class.id});" type="button" class="btn btn-danger">删除</button>
											</#if>
										</#if>
										<a href="javascript:;" onclick="studentList(${class.id});">
										<button type="button" class="btn btn-primary">
										学生管理</button></a>
										<#if adminUser.childRole !=1>
										<button data-toggle="modal" data-target="#relate" data-sid="${class.id}" data-adminid="${class.adminId}" type="button" class="btn btn-info">关联班级管理员</button>
										</#if>
										<#if class.batchDel == 1>
										<button onclick="batchDelStudent(${class.id});" type="button" class="btn btn-info">批量刪除学生</button>
										</#if>
									</td>
						        </tr>
						        </#if>
						        </#list>
							</tbody>
                        </table>
                    </div>
                </div>
                <!-- END SAMPLE TABLE PORTLET-->
            </div>
        </div>
    </section>
<!-- 模态框（Modal） -->
<#include "/class/saveClass.ftl"/>
<#include "/class/updateClass.ftl"/>
<#include "/class/relateClass.ftl"/>
        <script src="${baseOP}/content/ui/global/jQuery/jquery.min.js"></script>
        <!-- Bootstrap 3.3.6 -->

        <script src="${baseOP}/content/ui/global/bootstrap/js/bootstrap.min.js"></script>
        <script src="${baseOP}/content/min/js/supershopui.common.js"></script>
        <script src="${baseOP}/content/plugins/bootstrap-table/bootstrap-table.js"></script>
        <script src="${baseOP}/content/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
        <script src="${baseOP}/js/common.js?${timeStamp}"></script>
        <script src="${baseOP}/js/class/class.js?${timeStamp}"></script>
    </body>
</html>