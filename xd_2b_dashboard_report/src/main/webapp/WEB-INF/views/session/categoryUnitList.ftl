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
        <link rel="stylesheet" href="${baseOP}/css/dashboard.css?1">
    </head>
    <body>
    <section class="content-header">
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-area-chart"></i>学习监督></a></li>
            <li class="active">${catName}</li>
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
                <h3 class="box-title">${pilotUnitName}列表</h3>
                <!-- /.box-tools -->
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
                                    <th class="text-center" data-sortable="false">${pilotUnitName}</th>
                                    <th class="text-center">学生人数</th>
                                    <th class="text-center">正确率</th>
                                    <th class="text-center">活跃度</th>
                                    <th class="text-center">任务完成度</th>
                                    <th class="text-center">平均学习时长(分钟)</th>
                                    <th class="text-center">预警数量</th>
                                    <th class="text-center">操作</th>
                                </tr>
                            </thead>
                            <tbody>
								<#list listCategoryUnitSessionPercentVO as categoryUnit>
								<#if categoryUnit??>
						        <tr>
						        	<td class="text-center">${categoryUnit_index+1}</td>
						        	<td class="text-center">${categoryUnit.pilotUnitName}</td>
									<td class="text-center">${categoryUnit.studentCount}</td>
									<td class="text-center">${categoryUnit.rightPercent}</td>
									<td class="text-center">${categoryUnit.learnPercent}</td>
									<td class="text-center">${categoryUnit.missionPercent}</td>
									<td class="text-center">${categoryUnit.learnTimePercent}</td>
									<td class="text-center">${categoryUnit.alarmCount}</td>
									<td class="text-center">
									<#if categoryUnit.rightPercent??>
										<button type="button" class="btn btn-primary" onclick="categoryUnitTendencyList(${categoryUnit.catId},${categoryUnit.pilotUnitId});">查看趋势</button>
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
        <script src="${baseOP}/content/ui/global/jQuery/jquery.min.js"></script>
        <!-- Bootstrap 3.3.6 -->

        <script src="${baseOP}/content/ui/global/bootstrap/js/bootstrap.min.js"></script>
        <script src="${baseOP}/content/min/js/supershopui.common.js"></script>
        <script src="${baseOP}/content/plugins/bootstrap-table/bootstrap-table.js"></script>
        <script src="${baseOP}/content/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
        <script src="${baseOP}/js/common.js?${timeStamp}"></script>
        <script src="${baseOP}/js/session/categoryUnit.js"></script>
    </body>
</html>