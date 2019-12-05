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
    <!-- Main content -->
    <section class="table-well">
        <div class="row">
            <div class="col-md-12">
                <!-- BEGIN SAMPLE TABLE PORTLET-->
                <div class="box">
                    <div class="box-body">

 		<div class="box box-success box-solid" style="margin-bottom: 0px;">
            <div class="box-header">
                <h3 class="box-title">用户列表</h3>
                <!-- /.box-tools -->
                <div class="box-tools pull-right modal-open">
                	<button class="btn btn-success" onclick="method('table')">导出检测表</button>
                </div>
            </div>
        </div>                   
                         <table class="table-scrollable table table-hover" id="table">
                            <thead>
                                <tr>
							    	<th class="text-center" data-sortable="false">班级</th>
							        <th class="text-center" data-sortable="false">姓名</th>
							        <th class="text-center" data-sortable="false">性别（只填男女）</th>
							        <th class="text-center" data-sortable="false">手机号（11位）</th>
							        <th class="text-center" data-sortable="false">准考证号（12位，可为空）</th>
							    </tr>
                            </thead>
                            <tbody>
								<#if errorExcelList??>
								<#list errorExcelList as student>
								<#if student??>
									<tr class="tr-parent">
										<td class="text-center">${student.className}</td>
										<td class="text-center">${student.realName}</td>
										<td class="text-center">${student.gender}</td>
										<td class="text-center" style="mso-number-format:'\@';">${student.telephone}</td>
										<td class="text-center" style="mso-number-format:'\@';">${student.admissionCardCode}</td>
										<td class="text-center">${student.msg}</td>
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
        <script src="${baseOP}/content/plugins/bootstrap-table/bootstrap-table.js"></script>
        <script src="${baseOP}/content/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
        <script src="${baseOP}/js/common.js?${timeStamp}"></script>
        <script src="${baseOP}/js/jqexcel/excel.js"></script>
    </body>
</html>