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
        <!--表单相关-->
    	<link rel="stylesheet" href="${baseOP}/content/plugins/daterangepicker/daterangepicker.css">
        <!-- bootstrap datepicker -->
  		<link rel="stylesheet" href="${baseOP}/content/plugins/datepicker/datepicker3.css">
  		<link rel="stylesheet" href="${baseOP}/css/dashboard.css?1">
    </head>
    <body>
    <section class="content-header">
        <ol class="breadcrumb">
            <li><a href="javascript:;"><i class="fa fa-list-alt"></i>成绩管理</a></li>
            <li class="active">成绩详情</li>
        </ol>
    </section>
    <!-- Main content -->
    <section class="table-well"">
    	<div class="row">
	    	<div class="col-md-3 col-sm-3 col-xs-12" align="center">
	    		<img src="${scoreDO.studentPortrait}" width="auto" height="120px"></img>
	    		<!--<img src="${baseOP}/image/common/portrait.jpg" width="auto" height="120px"/>-->
	    	</div>
    		<div class="col-md-3 col-sm-3 col-xs-12">
                <ul class="nav nav-stacked">
                    <li><a href="javascript:;">姓名 ：${scoreDO.studentName}</a></li>
                    <li><a href="javascript:;">考期 ：${scoreDO.examDate}</a></li>
                    <li><a href="javascript:;">结课时间 ：${scoreDO.endTime}</a></li>
                </ul>
    		</div>
    		<div class="col-md-3 col-sm-3 col-xs-12">
                <ul class="nav nav-stacked">
                    <li><a href="javascript:;">准考证号 ：${scoreDO.admissionCardCode}</a></li>
                    <li><a href="javascript:;">专业代码 ：${scoreDO.catCode}</a></li>
                    <li><a href="javascript:;">课程代码 ：${scoreDO.productCode}</a></li>
                </ul>
	    	</div>
    		
    		<div class="col-md-3 col-sm-3 col-xs-12">
                <ul class="nav nav-stacked">
                    <li><a href="javascript:;">${pilotUnitName} ：${scoreDO.pilotUnitName}</a></li>
                    <li><a href="javascript:;">专业名称 ：${scoreDO.catName}</a></li>
                    <li><a href="javascript:;">课程名称 ：${scoreDO.productName}</a></li>
                </ul>
	    	</div>
    	</div>
        <div class="row">
            <div class="col-md-12">
                <!-- BEGIN SAMPLE TABLE PORTLET-->
                <div class="box">
                    <div class="box-body">
                    
   		<div class="box box-success box-solid" style="margin-bottom: 0px;">
            <div class="box-header">
                <h3 class="box-title">成绩详情</h3>
            </div>
        </div> 
                        <table class="table-scrollable table table-hover" id="table"
                               data-toggle="table"
                               >
                            <thead>
                                <tr>
                                	<th class="text-center"></th>
                                	<th class="text-center"></th>
                                	<th class="text-center">占比</th>
                                    <th class="text-center">评分内容</th>
                                    <th class="text-center">权重</th>
                                    <th class="text-center">得分</th>
                                    <th class="text-center"></th>
                                    <th class="text-center">成绩</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<#include "/score/scoreDetailStatic.ftl"/>
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
    </body>
</html>