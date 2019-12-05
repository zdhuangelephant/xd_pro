<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>SuperUI echarts图表</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="${baseOP}/content/ui/global/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link href="${baseOP}/content/ui/global/font-awesome/css/font-awesome.css" rel="stylesheet" />
    <!-- Theme style -->
    <link rel="stylesheet" href="${baseOP}/content/adminlte/dist/css/AdminLTE.css">
    <link rel="stylesheet" href="${baseOP}/content/adminlte/dist/css/skins/_all-skins.min.css">
    <link href="${baseOP}/content/min/css/supershopui.common.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="${baseOP}/css/dashboard.css">
</head>
<body>
    <section class="content-header">
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-area-chart"></i>学习监督</a></li>
            <li class="active">专业列表</li>
        </ol>
    </section>
    <!-- Main content -->
<#list listCategorySessionPercentVO as category>
<section class="table-well">
 <div class="row">
	 <div class="col-md-12">
		 <div class="box box-success box-solid">
	        <div class="box-header with-border">
 				<a class="btn" onclick="collapse(${category.id});" href="#${category.id}" data-parent="#section">
	            <h3 class="box-title">${category.catName} - ${category.chiefUnitName}</h3>
 				</a>
                <div class="box-tools pull-right box-tools-details">
                <#if adminUser.role==3>
                	<a class="btn" onclick="categoryUnitTendencyList(${category.catId},${adminUser.unitId})" href="javascript:;">查看详情>></a>
                <#else>
                    <a class="btn" onclick="categoryUnitList(${category.catId}, '${pilotUnitName}');" href="javascript:;">查看详情>></a>
                </#if>
                </div>
                <!-- /.box-tools -->
	        </div>
		 </div>
	 </div>
 </div>
 <div class="row panel-collapse collapse in" id="${category.id}">
    <div id="main_${category.id}" name="main" style="width: 70%; height: 400px;" class="col-md-3 col-sm-6 col-xs-12 main">
    </div>
    <!-- /.col -->
    <div class="col-md-3 col-sm-6 col-xs-12" style="width:30%">
            <!-- /.box-header -->
                <ul class="nav nav-stacked">
                	<input type="hidden" value="${category.learnPercent}" id="learnPercent_main_${category.id}"/>
                    <li><a href="javascript:volid(0);">活跃度 ：<span class="pull-right">${category.learnPercent}%</span></a></li>
                    <input type="hidden" value="${category.missionPercent}" id="missionPercent_main_${category.id}"/>
                    <li><a href="javascript:volid(0);">任务完成度 ：<span class="pull-right">${category.missionPercent}%</span></a></li>
                    <input type="hidden" value="${category.rightPercent}" id="rightPercent_main_${category.id}"/>
                    <li><a href="javascript:volid(0);">平均正确率：<span class="pull-right">${category.rightPercent}%</span></a></li>
                    <input type="hidden" value="${category.learnTimePercent}" id="learnTimePercent_main_${category.id}"/>
                    <li><a href="javascript:volid(0);">平均学习时长： <span class="pull-right">${category.learnTimePercent}分钟</span></a></li>
                    <li><a href="javascript:volid(0);">预警数量：<span class="pull-right">${category.alarmCount}个</span></a></li>
                    <li><a href="javascript:volid(0);">当期考生人数：<span class="pull-right">${category.studentCount}人</span></a></li>
                </ul>
        <!-- /.box -->
    </div>
    <!-- /.col -->
</div>                       
</section>
</#list>

    <!-- jQuery 2.2.3 -->
    <script src="${baseOP}/content/ui/global/jQuery/jquery.min.js"></script>
    <!-- Bootstrap 3.3.6 -->
    <script src="${baseOP}/content/ui/global/bootstrap/js/bootstrap.min.js"></script>
    <script src="${baseOP}/content/min/js/supershopui.common.js?1"></script>
	<script src="${baseOP}/js/echarts.min.js" charset="utf-8"></script>
	<script src="${baseOP}/js/session/category.js?12${timeStamp}"></script>
</body>
</html>
