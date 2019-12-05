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
            <li class="active">学习记录明细</li>
        </ol>
    </section>
    <section class="form-well">
        <div class="row">
			<form method="post" action="/score/learn_record_list">
				<div class="row" style="margin-bottom: 10px;">
					<input type="hidden" value="${scoreDO.productId}" name="productId"/>
					<input type="hidden" value="${scoreDO.studentId}" name="studentId"/>
					<div class="col-sm-2">
						<input type="hidden" id="learnType" value="${learnRecordDTO.learnType}">
						<select id="learnType1" name="learnType" type="text" class="filter-status form-control">
							<option value="">请选择记录类型</option>
							<option value="11">pk做题</option>
							<option value="12">pk解析</option>
							<option value="21">闯关做题</option>
							<option value="22">闯关解析</option>
							<option value="31">修炼</option>
							<option value="41">错题</option>
							<option value="51">每日一练</option>
							<option value="52">每日一练解析</option>
							<option value="61">期末测试</option>
							<option value="62">期末测试解析</option>
							<option value="71">查漏补缺</option>
							<option value="72">查漏补缺解析</option>
							<option value="81">章练习答题</option>
							<option value="91">章总结答题</option>
							<option value="92">章总结解析</option>
						</select>
					</div>
					<!-- Date -->
                    <div class="col-sm-3">
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input value="${learnRecordDTO.recordTime}" name="recordTime" type="text" class="form-control pull-right" id="datepicker" placeholder="日期">
                        </div>
                        <!-- /.input group -->
                    </div>
                    <!-- /.form group -->
					<!-- Date range -->
                    <div class="col-sm-4 hidden" >
                        <div class="input-group">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" class="form-control pull-right" id="reservation">
                        </div>
                        <!-- /.input group -->
                    </div>
                    <!-- /.form group -->
                    
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
                <h3 class="box-title">学习记录明细</h3>
                <div class="box-tools pull-right modal-open">
                	<button class="btn btn-success" data-toggle="modal" data-target="#previewLearnRecord">打印预览</button>
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
                                    <th class="text-center" data-sortable="false">学习行为</th>
                                    <th class="text-center" data-sortable="false">学习内容</th>
                                    <th class="text-center" data-sortable="false">学习日期</th>
                                    <th class="text-center" data-sortable="false">学习时长（分钟）</th>
                                </tr>
                            </thead>
                            <tbody>
								<#if listLearnRecord??>
								<#list listLearnRecord as learnRecord>
								<#if learnRecord??>
									<tr id="st${learnRecord.id}" class="tr-parent">
										<td class="text-center">${learnRecord_index+1}</td>
										<td class="text-center">
											<#if learnRecord.learnType == 11>pk做题
											<#elseif learnRecord.learnType == 12>pk解析
											<#elseif learnRecord.learnType == 21>闯关做题
											<#elseif learnRecord.learnType == 22>闯关解析
											<#elseif learnRecord.learnType == 31>修炼
											<#elseif learnRecord.learnType == 41>错题
											<#elseif learnRecord.learnType == 51>每日一练
											<#elseif learnRecord.learnType == 52>每日一练解析
											<#elseif learnRecord.learnType == 51>每日一练
											<#elseif learnRecord.learnType == 52>每日一练解析
											<#elseif learnRecord.learnType == 61>期末测试
											<#elseif learnRecord.learnType == 62>期末测试解析
											<#elseif learnRecord.learnType == 71>查漏补缺
											<#elseif learnRecord.learnType == 72>查漏补缺解析
											</#if>
										</td>
										<td class="text-center">${learnRecord.learnContent}</td>
										<td class="text-center">${learnRecord.recordTime}</td>
										<td class="text-center">${learnRecord.learnTime}</td>
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
<#include "/score/printLearnRecord.ftl"/>
        <script src="${baseOP}/content/ui/global/jQuery/jquery.min.js"></script>
        <!-- Bootstrap 3.3.6 -->
        <script src="${baseOP}/content/ui/global/bootstrap/js/bootstrap.min.js"></script>
        <script src="${baseOP}/content/min/js/supershopui.common.js"></script>
        <script src="${baseOP}/content/plugins/bootstrap-table/bootstrap-table.js"></script>
        <script src="${baseOP}/content/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
        <!-- date-range-picker -->
		<script src="${baseOP}/content/plugins/daterangepicker/daterangepicker.js"></script>
		<!-- bootstrap datepicker -->
		<script src="${baseOP}/content/plugins/datepicker/bootstrap-datepicker.js"></script>
        <script src="${baseOP}/js/common.js?${timeStamp}"></script>
        <script src="${baseOP}/js/score/learnRecord.js?${timeStamp}"></script>
        <script src="${baseOP}/js/jquery-migrate-1.2.1.min.js"></script>
        <script src="${baseOP}/js/jqprint/jquery.jqprint-0.3.js"></script>
    </body>
</html>