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
        <link href="${baseOP}/css/dashboard.css?12${timeStamp}" rel="stylesheet" />
        <link rel="stylesheet" href="${baseOP}/js/jqueryTreeView/jquery.treeview.css"/>
        
	    <!-- ace styles -->
	    <link href="${baseOP}/css/ace.css?${timeStamp}" rel="stylesheet" />
    </head>
    <body>
    <section class="content-header">
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-users"></i>首页</a></li>
            <li class="active">汇总统计详情</li>
        </ol>
    </section>
    <!-- Main content -->
    <section class="form-well">
        <div class="row">
            <div class="col-md-12">
                <!-- BEGIN SAMPLE TABLE PORTLET-->
                <div class="box">
                    <div class="box-body">
						
			 		<div class="box box-success box-solid" style="margin-bottom: 0px;">
			            <div class="box-header">
			                <h3 class="box-title">汇总统计详情</h3>
			                <!-- /.box-tools -->
			                <div class="box-tools pull-right modal-open">
			                	<button class="btn btn-success" onclick="exportScoreList();">下载汇总表</button>
			                </div>
			            </div>
			        </div>    
                    
                        <table class="table-scrollable table table-hover" id="table"
                               data-toggle="table"
                               data-advanced-search="true"
                               data-id-table="advancedTable"
                               data-row-style="rowStyle">
                            <thead>
                                <tr>
                                    <th data-sortable="false">学校</th>
                                    <th data-sortable="false">已报名学生</th>
                                    <th data-sortable="false">已缴费科次</th>
                                    <th data-sortable="false">无学习记录科次</th>
                                    <th data-sortable="false">学习使用率(%)</th>
                                    <th data-sortable="false">及格率(%)</th>
                                    <th data-sortable="false">100分</th>
                                    <th data-sortable="false">(100-80]分</th>
                                    <th data-sortable="false">(80-60]分</th>
                                    <th data-sortable="false">(60-0)分</th>
                                    <th data-sortable="false">0分</th>
                                </tr>
                            </thead>
                            <tbody>
								<#if learnSummaryPage??>
									<#list learnSummaryPage.result as summary>
											<#if summary??>
												<#if summary.totalStudents != 0 && summary.totalSubjectsAndStus != 0 >
													<tr class="tr-parent zdh" >
														<td style="text-align:left;">${summary.pilotUnitName}</td>
														<td class="text-center">${summary.totalStudents}</td>
														<td class="text-center">${summary.totalSubjectsAndStus}</td>
														<td class="text-center">${summary.learnNoneCounts}</td>
														<td class="text-center">${summary.learnPercent?string("#.##")}</td>
														<td class="text-center">${summary.passPercent?string("#.##")}</td>
														<td class="text-center">${summary.levelFullCreditApplyCounts}</td>
														<td class="text-center">${summary.levelExcellentApplyCounts}</td>
														<td class="text-center">${summary.levelGoodApplyCounts}</td>
														<td class="text-center">${summary.levelGeneralApplyCounts}</td>
														<td class="text-center">${summary.levelPoorApplyCounts}</td>
													</tr>
												</#if>
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
		<input type="hidden" value='${jsonData}' id="jsonData">

        <script src="${baseOP}/content/ui/global/jQuery/jquery.min.js"></script>
        <!-- Bootstrap 3.3.6 -->
        <script src="${baseOP}/content/ui/global/bootstrap/js/bootstrap.min.js"></script>
        <script src="${baseOP}/content/min/js/supershopui.common.js"></script>
        <script src="${baseOP}/content/plugins/bootstrap-table/bootstrap-table.js?1233"></script>
        <script src="${baseOP}/content/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
        <script src="${baseOP}/js/common.js?1${timeStamp}"></script>
        <script src="${baseOP}/js/student/student.js?112223${timeStamp}"></script>
        <script src="${baseOP}/js/jquery.twbsPagination.js" type="text/javascript"></script>
        <script src="${baseOP}/js/jqexcel/excel.js?1"></script>
		<!-- 模态框（Modal） -->
		<#include "/student/saveStudent.ftl"/>
		<#include "/student/updateStudent.ftl"/>
		<#include "/student/apply.ftl"/>
		<#include "/student/importStudent.ftl"/>
		<#include "/common/form/file.ftl"> 
		<#include "/student/importStudentTable.ftl">
		<#include "/student/batchApply.ftl">
    </body>
    <script type="text/javascript">
    function exportScoreList(){
    	var zdh = $('.zdh')
    	console.log(zdh.length);
    	if(zdh.length <= 0){
    		layer.msg('没有数据可供下载!', {
    			time : 2000, // 2s后自动关闭
    			icon : 3
    		});
    		return false;
    	}else{
	    	location.href = "/dashboard/export_dashboard_list";
    	} 
    }
    </script>
	<@fileUpload></@fileUpload>
</html>