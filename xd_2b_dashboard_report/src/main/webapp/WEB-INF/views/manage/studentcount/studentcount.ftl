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
        <!-- bootstrap datepicker -->
  		<link rel="stylesheet" href="${baseOP}/content/plugins/datepicker/datepicker3.css">
        <link href="${baseOP}/css/dashboard.css" rel="stylesheet" />
    </head>
    <body>
    <section class="content-header">
            <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-circle-o"></i>人员管理</a></li>
            <li class="active">学生人数查看</li>
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
                <h3 class="box-title">学生人数列表</h3>
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
		                        	<th class="text-center">${pilotUnitName}id</th>
		                        	<th class="text-center">${pilotUnitName}名称</th>
		                        	<th class="text-center">学生总人数</th>
		                            <th class="text-center">已缴费报名学生人数</th>
		                            <th class="text-center">已缴费报名信息(科次)</th>
		                        </tr>
		                    </thead>
		                    <tbody>
		                    	<#if studentCountVOList??>
								<#list studentCountVOList as vo>
								<#if vo??>
									<tr class="tr-parent">
										<td class="text-center">${vo.pilotUnitId}</td>
										<td class="text-center">${vo.pilotUnitName}</td>
										<td class="text-center">${vo.studentCount}</td>
										<td class="text-center">${vo.payStudentCount}</td>
										<td class="text-center">${vo.payApplyCount}</td>
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
        <script src="${baseOP}/content/ui/global/jQuery/jquery.min.js"></script>
        <!-- Bootstrap 3.3.6 -->
        <script src="${baseOP}/content/ui/global/bootstrap/js/bootstrap.min.js"></script>
        <script src="${baseOP}/content/min/js/supershopui.common.js"></script>
        <script src="${baseOP}/js/common.js?${timeStamp}"></script>
    </body>
</html>